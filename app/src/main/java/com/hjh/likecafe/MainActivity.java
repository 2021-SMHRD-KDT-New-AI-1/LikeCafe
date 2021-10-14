package com.hjh.likecafe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {
    TextView tv_r_choice;
    static boolean click_r;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       tv_r_choice = findViewById(R.id.tv_r_choice);

       tv_r_choice.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               //Intent(출발 Activity.this, 도착 Activity.class)
               click_r = true;
               Intent intent = new Intent(MainActivity.this,r_selection.class);
               startActivity(intent);
           }
       });

//        String region = getIntent().getStringExtra("region");
//        if(region.equals("")) {
//            tv_r_choice.setText("지역선택");
//        } else {
//            tv_r_choice.setText(region);
//        }


       if(click_r) {
           String region = getIntent().getStringExtra("region");
           tv_r_choice.setText(region);
       } else {
           tv_r_choice.setText("지역선택");
       }



    }


}