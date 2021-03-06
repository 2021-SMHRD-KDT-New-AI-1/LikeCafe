package com.hjh.likecafe;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class detailPage extends AppCompatActivity {
    Toolbar toolbar;
    DrawerLayout drawerLayout;

    ListView lv_reviewList;
    List<ReviewVO> data;
    RequestQueue requestQueue;

    CafeReviewAdapter adapter;

    TextView tv_detailName, tv_detailAddress, tv_detailBusiness, tv_detailTel, tv_detailSns;
    TextView tv_space, tv_parking, tv_tableNum, tv_floor, tv_detailKeyword;
    ImageView img_detailZzim, img_cafeMainImage;
    TextView tv_detailZzimCnt;
    Button btn_review;

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

        // ????????? ??????
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        // ???????????? ?????????
        mContext = this;

        // ?????? ?????? ????????? ??????
        // ??? ?????????
        tv_detailName = findViewById(R.id.tv_detailName);
        tv_detailAddress = findViewById(R.id.tv_detailAddress);
        tv_detailBusiness = findViewById(R.id.tv_detailBusiness);
        tv_detailTel = findViewById(R.id.tv_detailTel);
        tv_detailSns = findViewById(R.id.tv_detailSns);

        img_cafeMainImage = findViewById(R.id.img_cafeMainImage);

        img_detailZzim = findViewById(R.id.img_detailZzim);
        tv_detailZzimCnt = findViewById(R.id.tv_detailZzimCnt);

        tv_space = findViewById(R.id.tv_space);
        tv_parking = findViewById(R.id.tv_parking);
        tv_tableNum = findViewById(R.id.tv_tableNum);
        tv_floor = findViewById(R.id.tv_floor);
        tv_detailKeyword = findViewById(R.id.tv_detailKeyword);

        btn_review = findViewById(R.id.btn_review);

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
        String cafe_image = getIntent().getStringExtra("cafe_image");
        Bitmap cafe_image_bitmap = StringToBitmap(cafe_image);
        cafe_image_bitmap = Bitmap.createScaledBitmap(cafe_image_bitmap, 413,276,true);

        PreferenceManager.setBoolean(mContext, "zzimSel", zzimSel);
        PreferenceManager.setInt(mContext, "zzimCnt", zzimCnt);

        lv_reviewList = findViewById(R.id.lv_reviewList);
        data = new ArrayList<ReviewVO>();

        adapter = new CafeReviewAdapter(getApplicationContext(), R.layout.detailreview_list, data); //ReviewAdapter(getApplicationContext(), R.layout.review_list, data);
        lv_reviewList.setAdapter(adapter);

        getCafeReview(cafe_id);
        adapter.notifyDataSetChanged();


        // ?????? ????????? ??? ????????????
        tv_detailName.setText(cafe_name);
        tv_detailAddress.setText(address);
        tv_detailBusiness.setText(business_hour + holiday);
        tv_detailTel.setText(tel);
        tv_detailSns.setText(sns);

        img_cafeMainImage.setImageBitmap(cafe_image_bitmap);

        String textKeyword = "";
        for (String keyword:keywords) {
            if(keyword.equals("????????? ??????") || keyword.equals("?????????") || keyword.equals("??? ???") || keyword.equals("????????? ??????")) {
                textKeyword += "";
            } else {
                textKeyword += "#" + keyword + " ";
            }
        }

        getDetailInfo(cafe_id);
        tv_detailKeyword.setText(textKeyword);

        String mem_id = PreferenceManager.getString(this, "mem_id");
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
                    // ??? ???????????? ???????????? ??????
                    zzimDelete(mem_id, cafe_id);
                } else {
                    // ??? ????????? ???????????? ??????
                    zzimInsert(mem_id, cafe_id);
                }
            }
        });

        // ?????? ?????? ?????? ??????
        btn_review.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(detailPage.this, reviewPage.class);
                intent.putExtra("cafe_id", cafe_id);
                intent.putExtra("cafe_image", cafe_image);
                intent.putExtra("cafe_name", cafe_name);
                intent.putExtra("cafe_address", address);
                startActivity(intent);
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
                        // ?????? ?????? ?????? ?????? ?????????~~!!
                        try {
                            JSONObject jsonObject = (JSONObject) (new JSONArray(response).get(0));
                            String status = jsonObject.getString("status");
                            img_detailZzim.setImageResource(R.drawable.zzimsel);
                            int zc = PreferenceManager.getInt(mContext, "zzimCnt");
                            tv_detailZzimCnt.setText(String.valueOf(zc+1));
                            PreferenceManager.setBoolean(mContext, "zzimSel", true);
                            PreferenceManager.setInt(mContext, "zzimCnt", zc + 1);

                            Toast.makeText(getApplicationContext(), "??? ??????!", Toast.LENGTH_SHORT).show();

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
                        // ?????? ?????? ?????? ?????? ?????????~~!!
                        try {
                            JSONObject jsonObject = (JSONObject) (new JSONArray(response).get(0));
                            String status = jsonObject.getString("status");
                            img_detailZzim.setImageResource(R.drawable.zzim);
                            int zc = PreferenceManager.getInt(mContext, "zzimCnt");
                            tv_detailZzimCnt.setText(String.valueOf(zc - 1));
                            PreferenceManager.setBoolean(mContext, "zzimSel", false);
                            PreferenceManager.setInt(mContext, "zzimCnt", zc - 1);

                            Toast.makeText(getApplicationContext(), "??? ??????!", Toast.LENGTH_SHORT).show();


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
                        // ?????? ?????? ?????? ?????? ?????????~~!!
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

    public void getCafeReview(int cafe_id) {
        String url = "http://172.30.1.8:3003/Review/SelectByCafeId";
        StringRequest request = new StringRequest(
                Request.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // ?????? ?????? ?????? ?????? ?????????~~!!
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = (JSONObject) jsonArray.get(i);

                                int review_id = jsonObject.getInt("review_id");
                                int cafe_id = jsonObject.getInt("cafe_id");
                                String cafe_name = " ";
                                String mem_id = jsonObject.getString("mem_id");
                                String nick = jsonObject.getString("nick");
                                Double star = jsonObject.getDouble("star");
                                String content = jsonObject.getString("content");
                                String review_image = jsonObject.getString("review_image");
                                Bitmap imageBitmap = StringToBitmap(review_image);

                                ReviewVO vo = new ReviewVO(review_id, cafe_id, cafe_name, mem_id, star, content, imageBitmap);
                                vo.setNick(nick);

                                Log.d("Review item"+i+">>", vo.toString());
                                data.add(vo);
                                adapter.notifyDataSetChanged();
                            }

                            int totalHeight = 0;
                            Log.d("adapter.getCount : ", String.valueOf(adapter.getCount()));
                            for (int i = 0; i < adapter.getCount(); i++) {
                                View listItem = adapter.getView(i, null, lv_reviewList);
                                listItem.measure(0, 0);
                                totalHeight += listItem.getMeasuredHeight();
                                Log.d("totalHeight : ", String.valueOf(totalHeight));
                            }
                            ViewGroup.LayoutParams params = lv_reviewList.getLayoutParams();
                            params.height = totalHeight + (lv_reviewList.getDividerHeight() * (adapter.getCount() - 1));
                            lv_reviewList.setLayoutParams(params);
                            lv_reviewList.requestLayout();



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

                return params;
            }
        };
        requestQueue.add(request);
    }

    public static Bitmap StringToBitmap(String encodedString) {
        if(encodedString.equals("????????? ?????????")) {
            return null;
        }
        try {
            byte[] encodeByte = Base64.decode(encodedString, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        } catch (Exception e) {
            e.getMessage();
            return null;
        }
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:{ // ???????????? ?????? ????????? ???
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }


}