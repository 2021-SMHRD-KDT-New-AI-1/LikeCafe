package com.hjh.likecafe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


public class r_selection extends AppCompatActivity {
    Button btn_r1,btn_r2,btn_r3,btn_r4,btn_r5,btn_r6,btn_r7,btn_r8,btn_r_choice;
    String region;
    private Object view;
    Context mContext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rselection);

        mContext = this;

        btn_r1 = findViewById(R.id.btn_r1);
        btn_r2 = findViewById(R.id.btn_r2);
        btn_r3 = findViewById(R.id.btn_r3);
        btn_r4 = findViewById(R.id.btn_r4);
        btn_r5 = findViewById(R.id.btn_r5);
        btn_r6 = findViewById(R.id.btn_r6);
        btn_r7 = findViewById(R.id.btn_r7);
        btn_r8 = findViewById(R.id.btn_r8);
        btn_r_choice = findViewById(R.id.btn_r_choice);

        btn_r1.setOnClickListener(view -> {
            getSupportFragmentManager().beginTransaction().replace(R.id.fl, new Fragment1()).commit();
            region = "서울 ";
            Log.d("gugugu---", region);
        });
//        btn_r2.setOnClickListener(view -> {
//
//                getSupportFragmentManager().beginTransaction().replace(R.id.fl,new Fragment2()).commit();
//                region = "경기";
//                Log.d("gugugu---", region);
//                String gu = getIntent().getStringExtra("gu");
//                region += gu;
//                Log.d("gugugu---", region);
//            });
//
//        btn_r3.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                getSupportFragmentManager().beginTransaction().replace(R.id.fl,new Fragment3()).commit();
//                region = "부산";
//            }
//        });
//        btn_r4.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                getSupportFragmentManager().beginTransaction().replace(R.id.fl,new Fragment4()).commit();
//                region = "대구";
//            }
//        });
//        btn_r5.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                getSupportFragmentManager().beginTransaction().replace(R.id.fl,new Fragment5()).commit();
//                region = "인천";
//            }
//        });
//        btn_r6.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                getSupportFragmentManager().beginTransaction().replace(R.id.fl,new Fragment6()).commit();
//                region = "광주";
//            }
//        });
//        btn_r7.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                getSupportFragmentManager().beginTransaction().replace(R.id.fl,new Fragment7()).commit();
//                region = "대전";
//            }
//        });
//        btn_r8.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                getSupportFragmentManager().beginTransaction().replace(R.id.fl,new Fragment8()).commit();
//                region = "울산";
//            }
//        });
//
        btn_r_choice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String gu = PreferenceManager.getString(mContext,"gu");
                region += gu;
                Log.d("gugu choice", region);

                Intent intent = new Intent(r_selection.this,MainActivity.class);
                intent.putExtra("region",region);
                startActivity(intent);

            }
        });



    }
}