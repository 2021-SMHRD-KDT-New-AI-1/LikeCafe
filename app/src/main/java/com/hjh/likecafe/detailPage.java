package com.hjh.likecafe;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class detailPage extends AppCompatActivity {
    Toolbar toolbar;
    DrawerLayout drawerLayout;

    RequestQueue requestQueue;

    TextView tv_detailName, tv_detailAddress, tv_detailBusiness, tv_detailTel, tv_detailSns;
    TextView tv_space, tv_parking, tv_tableNum, tv_floor, tv_detailKeyword;
    ImageView img_detailZzim;
    TextView tv_detailZzimCnt;

    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_page);

        toolbar = findViewById(R.id.toolbar);
        drawerLayout = findViewById(R.id.drawer_layout);

        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.menu);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                menuItem.setChecked(true);
                drawerLayout.closeDrawers();

                int id = menuItem.getItemId();
                String title = menuItem.getTitle().toString();

                if(id == R.id.NV_home){
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                }
                else if(id == R.id.NV_wish){
                    Intent intent = new Intent(getApplicationContext(), zzimlist.class);
                    startActivity(intent);
                }
                else if(id == R.id.NV_review){
                    Intent intent = new Intent(getApplicationContext(), review.class);
                    startActivity(intent);
                }else if(id == R.id.NV_edit){
                    Intent intent = new Intent(getApplicationContext(), memberInfoModify.class);
                    startActivity(intent);
                }

                return true;
            }
        });

        mContext = this;

        // 서버와 통신
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        // 카페 상세 페이지 생성
        // 뷰 초기화
        tv_detailName = findViewById(R.id.tv_detailName);
        tv_detailAddress = findViewById(R.id.tv_detailAddress);
        tv_detailBusiness = findViewById(R.id.tv_detailBusiness);
        tv_detailTel = findViewById(R.id.tv_detailTel);
        tv_detailSns = findViewById(R.id.tv_detailSns);

        img_detailZzim = findViewById(R.id.img_detailZzim);
        tv_detailZzimCnt = findViewById(R.id.tv_detailZzimCnt);

        tv_space = findViewById(R.id.tv_space);
        tv_parking = findViewById(R.id.tv_parking);
        tv_tableNum = findViewById(R.id.tv_tableNum);
        tv_floor = findViewById(R.id.tv_floor);
        tv_detailKeyword = findViewById(R.id.tv_detailKeyword);

        int cafe_id = getIntent().getIntExtra("cafe_id",0);
        String cafe_name = getIntent().getStringExtra("cafe_name");
        int zzimCnt = getIntent().getIntExtra("zzimCnt", 0);
        boolean zzimSel = getIntent().getBooleanExtra("zzimSel", false);
        String address = getIntent().getStringExtra("address");
        String business_hour = getIntent().getStringExtra("business_hour");
        String holiday = getIntent().getStringExtra("holiday");
        holiday = " / "+ holiday;
        String tel = getIntent().getStringExtra("tel");
        String sns = getIntent().getStringExtra("sns");
        String[] keywords = getIntent().getStringArrayExtra("keywords");

        PreferenceManager.setBoolean(mContext, "zzimSel", zzimSel);
        PreferenceManager.setInt(mContext, "zzimCnt", zzimCnt);

        // 뷰에 받아온 값 설정하기
        tv_detailName.setText(cafe_name);
        tv_detailAddress.setText(address);
        tv_detailBusiness.setText(business_hour + holiday);
        tv_detailTel.setText(tel);
        tv_detailSns.setText(sns);

        String textKeyword = "";
        for (String keyword:keywords) {
            if(keyword.equals("좌석간 거리") || keyword.equals("주차장") || keyword.equals("층 수") || keyword.equals("테이블 개수")) {
                textKeyword += "";
            } else {
                textKeyword += "#" + keyword + " ";
            }
        }

        getDetailInfo(cafe_id);
        tv_detailKeyword.setText(textKeyword);

        if(zzimSel) {
            img_detailZzim.setImageResource(R.drawable.zzimsel);
        } else {
            img_detailZzim.setImageResource(R.drawable.zzim);
        }
        tv_detailZzimCnt.setText(String.valueOf(PreferenceManager.getInt(mContext, "zzimCnt")));
        img_detailZzim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(PreferenceManager.getBoolean(mContext, "zzimSel")) {
                    // 찜 목록에서 삭제하는 기능
                    zzimDelete("test", cafe_id);
                } else {
                    // 찜 목록에 추가하는 기능
                    zzimInsert("test", cafe_id);
                }


            }
        });






    }

    public void zzimInsert(String mem_id, int cafe_id) {
        String url = "http://172.30.1.8:3003/Zzim/Insert";
        StringRequest request = new StringRequest(
                Request.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // 응답 성공 응답 성공 이야후~~!!
                        try {
                            JSONObject jsonObject = (JSONObject) (new JSONArray(response).get(0));
                            String status = jsonObject.getString("status");
                            img_detailZzim.setImageResource(R.drawable.zzimsel);
                            int zc = PreferenceManager.getInt(mContext, "zzimCnt");
                            tv_detailZzimCnt.setText(String.valueOf(zc+1));
                            PreferenceManager.setBoolean(mContext, "zzimSel", true);
                            PreferenceManager.setInt(mContext, "zzimCnt", zc + 1);

                            Toast.makeText(getApplicationContext(), "찜 추가!", Toast.LENGTH_SHORT).show();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("cafe_id", String.valueOf(cafe_id));
                params.put("mem_id", mem_id);

                return params;
            }
        };
        requestQueue.add(request);
    }

    public void zzimDelete(String mem_id, int cafe_id) {
        String url = "http://172.30.1.8:3003/Zzim/Delete";
        StringRequest request = new StringRequest(
                Request.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // 응답 성공 응답 성공 이야후~~!!
                        try {
                            JSONObject jsonObject = (JSONObject) (new JSONArray(response).get(0));
                            String status = jsonObject.getString("status");
                            img_detailZzim.setImageResource(R.drawable.zzim);
                            int zc = PreferenceManager.getInt(mContext, "zzimCnt");
                            tv_detailZzimCnt.setText(String.valueOf(zc - 1));
                            PreferenceManager.setBoolean(mContext, "zzimSel", false);
                            PreferenceManager.setInt(mContext, "zzimCnt", zc - 1);

                            Toast.makeText(getApplicationContext(), "찜 삭제!", Toast.LENGTH_SHORT).show();


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("cafe_id", String.valueOf(cafe_id));
                params.put("mem_id", mem_id);

                return params;
            }
        };
        requestQueue.add(request);
    }


    public void getDetailInfo(int cafe_id){
        String url = "http://172.30.1.8:3003/Detail/DetailInfo";
        StringRequest request = new StringRequest(
                Request.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // 응답 성공 응답 성공 이야후~~!!
                        try {
                            JSONObject jsonObject = (JSONObject) (new JSONArray(response)).get(0);
                            String space = jsonObject.getString("space");
                            String parking = jsonObject.getString("parking");
                            String tableNum = jsonObject.getString("tableNum");
                            String floor = jsonObject.getString("floor");

                            tv_space.setText(space);
                            tv_parking.setText(parking);
                            tv_tableNum.setText(tableNum);
                            tv_floor.setText(floor);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("cafe_id", String.valueOf(cafe_id));

                return params;
            }
        };
        requestQueue.add(request);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:{ // 뒤로가기 버튼 눌렀을 때
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }


}