package com.hjh.likecafe;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;


public class MainActivity extends AppCompatActivity {
    TextView tv_r_choice;
    static boolean click_r;
    Toolbar toolbar;
    DrawerLayout drawerLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar);
        drawerLayout = findViewById(R.id.drawer_layout);
        setSupportActionBar(toolbar);
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

                if(id == R.id.home){
                    Toast.makeText(getApplicationContext(), title + ": 계정 정보를 확인합니다.", Toast.LENGTH_SHORT).show();
                }
                else if(id == R.id.wishlist){
                    Toast.makeText(getApplicationContext(), title + ": 설정 정보를 확인합니다.", Toast.LENGTH_SHORT).show();
                }
                else if(id == R.id.review){
                    Toast.makeText(getApplicationContext(), title + ": 로그아웃 시도중", Toast.LENGTH_SHORT).show();
                }else if(id == R.id.edit){
                    Toast.makeText(getApplicationContext(), title + ": 로그아웃 시도중", Toast.LENGTH_SHORT).show();
                }

                return true;
            }


        });

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
