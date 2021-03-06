package com.hjh.likecafe;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
    Button btn_reviewUpdate, btn_reviewPictureUpload;
    RatingBar ratingbar;
    EditText et_review_writebox;
    TextView tv_limit;
    ImageView img_reviewPicture;
    Bitmap cafeImage;
    Double rate;

    TextView tv_reviewCafeName, tv_reviewAddress;

    Context mContext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_page);
        toolbar = findViewById(R.id.toolbar);
        drawerLayout = findViewById(R.id.drawer_layout);
        btn_reviewUpdate = findViewById(R.id.btn_reviewUpdate);
        ratingbar = findViewById(R.id.ratingbar);
        et_review_writebox = findViewById(R.id.et_review_writebox);
        tv_limit = findViewById(R.id.tv_limit);
        btn_reviewPictureUpload = findViewById(R.id.btn_reviewPictureUpload);
        img_reviewPicture = findViewById(R.id.img_reviewPicture);
        tv_reviewCafeName = findViewById(R.id.tv_reviewCafeName);
        tv_reviewAddress = findViewById(R.id.tv_reviewAddress);

        int cafe_id = getIntent().getIntExtra("cafe_id", 1);
        String cafe_image = getIntent().getStringExtra("cafe_image");
        cafeImage = StringToBitmap(cafe_image);
        String cafe_name = getIntent().getStringExtra("cafe_name");
        String cafe_address = getIntent().getStringExtra("cafe_address");

        img_reviewPicture.setImageBitmap(cafeImage);
        tv_reviewCafeName.setText(cafe_name);
        tv_reviewAddress.setText(cafe_address);

        mContext = this;


        // '????????????' ?????? ?????? ??? ??? ????????? ??????(by.?????????)
        btn_reviewPictureUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent,1);
            }
        });


        // ????????? ??????
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(getApplicationContext());
        }



        // ?????? ????????? ????????? ??????(by.?????????)
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
                String txt = String.valueOf(currentBytes) + " / 80 ??????";
                tv_limit.setText(txt);
            }

            @Override
            public void afterTextChanged(Editable sequence) {

            }
        });


        // ?????? ??????(by ?????????)
        ratingbar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                rate = (double) rating;
            }
        });

        // ?????? ????????????
        btn_reviewUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String review = et_review_writebox.getText().toString();
                String mem_id = PreferenceManager.getString(mContext, "mem_id");

                Toast.makeText(getApplicationContext(), "?????? ????????? ?????????????????????.", Toast.LENGTH_SHORT).show();
                postReview(mem_id, cafe_id, review);
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


    // ????????? ?????? ????????????(?????? ?????? ??? ?????? ????????????), ????????? ???????????? ??????
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);



        if (requestCode == 1) {

            if (resultCode == RESULT_OK) {
                try {
                    InputStream in = getContentResolver().openInputStream(data.getData());
                    cafeImage = BitmapFactory.decodeStream(in);
                    in.close();

                    img_reviewPicture.setImageBitmap(cafeImage);

                    Toast.makeText(reviewPage.this,
                            "????????? ?????????????????????.", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    // ???????????? ??????(???????????????)??? String????????? ???????????? (DB??? ?????? ??????)
    public static String BitmapToString (Bitmap bitmap) {
        if(bitmap == null) {
            return "????????? ?????????";
        }
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100,baos);
        byte[] bytes = baos.toByteArray();
        String bitString = Base64.encodeToString(bytes, Base64.DEFAULT);
        return bitString;
    }

    // Json ?????? ?????? ??? ?????? ????????? ??????
    public void postReview (String mem_id, int cafe_id, String review) {
        String url = "http://172.30.1.8:3003/Review/ReviewPage";  //
        StringRequest request = new StringRequest(
                Request.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = (JSONObject) (new JSONArray(response).get(0));
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
                params.put("cafe_id", String.valueOf(cafe_id));
                params.put("mem_id", mem_id);
                params.put("star", Double.toString(rate));
                params.put("content", review);
                params.put("review_image", BitmapToString(cafeImage));
                params.put("write_date", "2021-10-19"); // (????????? ????????????)

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