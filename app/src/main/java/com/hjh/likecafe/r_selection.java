package com.hjh.likecafe;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;



public class r_selection extends AppCompatActivity {
    Button btn_r1,btn_r2,btn_r3,btn_r4,btn_r5,btn_r6,btn_r7,btn_r8;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rselection);

        Button btn_r1 = findViewById(R.id.btn_r1);
        Button btn_r2 = findViewById(R.id.btn_r2);
        Button btn_r3 = findViewById(R.id.btn_r3);
        Button btn_r4 = findViewById(R.id.btn_r4);
        Button btn_r5 = findViewById(R.id.btn_r5);
        Button btn_r6 = findViewById(R.id.btn_r6);
        Button btn_r7 = findViewById(R.id.btn_r7);
        Button btn_r8 = findViewById(R.id.btn_r8);


        btn_r1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSupportFragmentManager().beginTransaction().replace(R.id.fl, new Fragment1()).commit();
            }
        });
        btn_r2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSupportFragmentManager().beginTransaction().replace(R.id.fl,new Fragment2()).commit();
            }
        });
        btn_r3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSupportFragmentManager().beginTransaction().replace(R.id.fl,new Fragment3()).commit();
            }
        });
        btn_r4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSupportFragmentManager().beginTransaction().replace(R.id.fl,new Fragment4()).commit();
            }
        });
        btn_r5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSupportFragmentManager().beginTransaction().replace(R.id.fl,new Fragment5()).commit();
            }
        });
        btn_r6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSupportFragmentManager().beginTransaction().replace(R.id.fl,new Fragment6()).commit();
            }
        });
        btn_r7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSupportFragmentManager().beginTransaction().replace(R.id.fl,new Fragment7()).commit();
            }
        });
        btn_r8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSupportFragmentManager().beginTransaction().replace(R.id.fl,new Fragment8()).commit();
            }
        });


    }
}