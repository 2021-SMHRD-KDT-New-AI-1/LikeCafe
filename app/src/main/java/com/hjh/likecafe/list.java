package com.hjh.likecafe;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

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

public class list extends AppCompatActivity {
    ListView lv_cafe;
    TextView tv_detailResult, tv_theme;
    RequestQueue requestQueue;
    List<CafeVO> data;
    Toolbar toolbar;
    DrawerLayout drawerLayout;

    CafeAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        lv_cafe = findViewById(R.id.lv_cafe);
        data = new ArrayList<CafeVO>();
        toolbar = findViewById(R.id.toolbar);
        drawerLayout = findViewById(R.id.drawer_layout);
        setSupportActionBar(toolbar);



//        tv_theme = findViewById(R.id.tv_theme);
//        tv_theme.setText(result);

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

                if (id == R.id.NV_home) {
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                } else if (id == R.id.NV_wish) {
                    Intent intent = new Intent(getApplicationContext(), zzimlist.class);
                    startActivity(intent);
                } else if (id == R.id.NV_review) {
                    Intent intent = new Intent(getApplicationContext(), review.class);
                    startActivity(intent);
                } else if (id == R.id.NV_edit) {
                    Intent intent = new Intent(getApplicationContext(), memberInfoModify.class);
                    startActivity(intent);
                }
                return true;
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        adapter = new CafeAdapter(getApplicationContext(), R.layout.cafelist, data);
        lv_cafe.setAdapter(adapter);

        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        tv_detailResult = findViewById(R.id.tv_detailResult);
        ArrayList<String> detail = getIntent().getStringArrayListExtra("detail");
        String theme = getIntent().getStringExtra("theme");
        String region = getIntent().getStringExtra("region");
        Log.d("????????? ???????????? ????????????  -> ", region);

        String mem_id = PreferenceManager.getString(this, "mem_id");
        String result = "";
        if (detail != null) {
            result = "";
            for (String keyword : detail) {
                result += "#" + keyword + " ";
            }
            searchByKeyword(detail, region, mem_id);
        } else if (theme != null) {
            result = "";
            Log.d("theme : ", theme);
            result += "#" + theme;
            searchByCategory(theme, region, mem_id);
        }
        tv_detailResult.setText(result);
        adapter.notifyDataSetChanged();


    }

    public void searchByCategory(String theme, String region, String mem_id) {
        String url = "http://172.30.1.8:3003/Cafe/SearchByCategory";
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
                                Log.d("cafe item" + i + " : ", jsonObject.toString());
                                // ??? ????????? ????????? ??????
                                // CafeVO ??????
                                int cafe_id = jsonObject.getInt("cafe_id");
                                String name = jsonObject.getString("cafe_name");
                                String image = jsonObject.getString("cafe_image");
                                String address = jsonObject.getString("address");
                                String business_hour = jsonObject.getString("business_hours");
                                String holiday = jsonObject.getString("holiday");
                                String tel = jsonObject.getString("tel");
                                String sns = jsonObject.getString("sns");
                                String category = jsonObject.getString("category");
                                String[] test = new String[1];
                                Bitmap imageBitmap = StringToBitmap(image);

                                CafeVO vo = new CafeVO(cafe_id, name, imageBitmap, address,
                                        business_hour, holiday, tel,
                                        sns, category, test, 1, true);

                                getKeywords(cafe_id, vo);
                                getZzimCnt(cafe_id, vo);
                                getZzimSel(cafe_id, mem_id, vo);
                                data.add(vo);
                                adapter.notifyDataSetChanged();

                            }


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
                params.put("category", theme);
                params.put("region", region);

                return params;
            }
        };
        requestQueue.add(request);
    }

    public void searchByKeyword(ArrayList<String> detail, String region, String mem_id) {
        String url = "http://172.30.1.8:3003/Cafe/SearchByKeyword";
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
                                Log.d("cafe item" + i + " : ", jsonObject.toString());
                                // ??? ????????? ????????? ??????
                                // CafeVO ??????
                                int cafe_id = jsonObject.getInt("cafe_id");
                                String name = jsonObject.getString("cafe_name");
                                String image = jsonObject.getString("cafe_image");
                                String address = jsonObject.getString("address");
                                String business_hour = jsonObject.getString("business_hours");
                                String holiday = jsonObject.getString("holiday");
                                String tel = jsonObject.getString("tel");
                                String sns = jsonObject.getString("sns");
                                String category = jsonObject.getString("category");
                                String[] test = new String[1];
                                Bitmap imageBitmap = StringToBitmap(image);

                                CafeVO vo = new CafeVO(cafe_id, name, imageBitmap, address,
                                        business_hour, holiday, tel,
                                        sns, category, test, 1, true);

                                getKeywords(cafe_id, vo);
                                getZzimCnt(cafe_id, vo);
                                getZzimSel(cafe_id, mem_id, vo);
                                data.add(vo);
                                adapter.notifyDataSetChanged();

                            }


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
                String keyword = "";
                for (String d: detail) {
                    keyword += d + ",";
                }
                params.put("keyword", keyword);
                params.put("region", region);

                return params;
            }
        };
        requestQueue.add(request);
    }

    public void getKeywords(int cafe_id, CafeVO vo){
        String url = "http://172.30.1.8:3003/Detail/GetKeywords";
        StringRequest request = new StringRequest(
                Request.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // ?????? ?????? ?????? ?????? ?????????~~!!
                        try {
                            JSONObject jsonObject = (JSONObject) (new JSONArray(response)).get(0);
                            String keywords = jsonObject.getString("keywords");
                            String[] keywords_arr = keywords.split(",");
                            vo.setKeywords(keywords_arr);
                            adapter.notifyDataSetChanged();

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

    public void getZzimCnt(int cafe_id, CafeVO vo) {
        String url = "http://172.30.1.8:3003/Zzim/ZzimCnt";
        StringRequest request = new StringRequest(
                Request.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // ?????? ?????? ?????? ?????? ?????????~~!!
                        try {
                            JSONObject jsonObject = (JSONObject) (new JSONArray(response).get(0));
                            int zzimCnt = jsonObject.getInt("zzimCnt");
                            Log.d("zzimCnt in method > ", String.valueOf(zzimCnt));
                            vo.setZzimCnt(zzimCnt);
                            adapter.notifyDataSetChanged();

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

    public void getZzimSel(int cafe_id, String mem_id, CafeVO vo) {
        String url = "http://172.30.1.8:3003/Zzim/ZzimSel";
        StringRequest request = new StringRequest(
                Request.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // ?????? ?????? ?????? ?????? ?????????~~!!
                        try {
                            JSONObject jsonObject = (JSONObject) (new JSONArray(response).get(0));
                            int zzimSel = jsonObject.getInt("zzimSel");
                            Log.d("zzimSel in method > ", String.valueOf(zzimSel));
                            if (zzimSel == 0) {
                                vo.setZzimSel(false);
                            } else {
                                vo.setZzimSel(true);
                            }
                            adapter.notifyDataSetChanged();

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

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: { // ???????????? ?????? ????????? ???
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    public static Bitmap StringToBitmap(String encodedString) {
        try {
            byte[] encodeByte = Base64.decode(encodedString, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        } catch (Exception e) {
            e.getMessage();
            return null;
        }
    }


}