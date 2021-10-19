package com.hjh.likecafe;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
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

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class reviewPage extends AppCompatActivity {
    RequestQueue requestQueue;
    Toolbar toolbar;
    DrawerLayout drawerLayout;
    Button btn_riviewupdate, btn_s1;
    RatingBar ratingbar;
    EditText et_review_writebox;
    TextView tv_limit;
    ImageView img_picture;
    Bitmap cafeImage;
    Float rate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_page);
        toolbar = findViewById(R.id.toolbar);
        drawerLayout = findViewById(R.id.drawer_layout);
        btn_riviewupdate = findViewById(R.id.btn_riviewupdate);
        ratingbar = findViewById(R.id.ratingbar);
        et_review_writebox = findViewById(R.id.et_review_writebox);
        tv_limit = findViewById(R.id.tv_limit);
        btn_s1 = findViewById(R.id.btn_s1);


        // '사진등록' 버튼 클릭 시 폰 갤러리 열기(by.안영상)
        btn_s1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent,1);
            }
        });


        // 서버와 통신
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(getApplicationContext());
        }



        // 리뷰 텍스트 글자수 제한(by.안영상)
        et_review_writebox.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence,
                                          int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence sequence,
                                      int start, int before, int count) {
                InputFilter[] filter = new InputFilter[1];
                filter[0] = new InputFilter.LengthFilter(40);
                et_review_writebox.setFilters(filter);

                int currentBytes = sequence.toString().getBytes().length;
                String txt = String.valueOf(currentBytes) + " / 80 글자";
                tv_limit.setText(txt);
            }

            @Override
            public void afterTextChanged(Editable sequence) {

            }
        });


        // 별점 박기(by 강성희)
        ratingbar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                rate = rating;
            }
        });

        // 리뷰 등록하기
        btn_riviewupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String review = et_review_writebox.getText().toString();

                Toast.makeText(getApplicationContext(), "리뷰 등록이 완료되었습니다.", Toast.LENGTH_SHORT).show();
                postReview(review);
            }
        });

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

    }


    // 선택한 사진 등록하기(리뷰 작성 시 박을 카페사진), 비트맵 형식으로 변환
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        img_picture = findViewById(R.id.img_picture);

        if (requestCode == 1) {

            if (resultCode == RESULT_OK) {
                try {
                    InputStream in = getContentResolver().openInputStream(data.getData());
                    cafeImage = BitmapFactory.decodeStream(in);
                    in.close();

                    img_picture.setImageBitmap(cafeImage);

                    Toast.makeText(reviewPage.this,
                            "사진이 등록되었습니다.", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    // 업로드한 사진(비트맵형식)을 String형태로 변환하기 (DB에 전송 목적)
    public static String BitmapToString (Bitmap bitmap) {
        if(bitmap == null) {
            return "디폴트 이미지";
        }
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100,baos);
        byte[] bytes = baos.toByteArray();
        String bitString = Base64.encodeToString(bytes, Base64.DEFAULT);
        return bitString;
    }






    // Json 파일 생성 및 리뷰 웹서버 전송
    public void postReview (String review) {
        String url = "http://172.30.1.8:3003/Review/ReviewPage";  //
        StringRequest request = new StringRequest(
                Request.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject();
                            Log.d("status : ", jsonObject.getString("status"));
                            Intent intent = new Intent(
                                    reviewPage.this, MainActivity.class);
                            startActivity(intent);
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
                params.put("review_id", "2"); // (확인용 가라정보)
                params.put("cafe_id", "1"); // (확인용 가라정보)
                params.put("mem_id", "test"); // (확인용 가라정보)
                params.put("star", Float.toString(rate));
                params.put("content", review);
                params.put("review_image", BitmapToString(cafeImage));
                params.put("write_date", "2021-10-19"); // (확인용 가라정보)

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