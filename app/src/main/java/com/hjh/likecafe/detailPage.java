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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

public class detailPage extends AppCompatActivity {
    Toolbar toolbar;
    DrawerLayout drawerLayout;

    TextView tv_detailName, tv_detailAddress, tv_detailBusiness, tv_detailTel, tv_detailSns;

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

        // 카페 상세 페이지 생성
        // 뷰 초기화
        tv_detailName = findViewById(R.id.tv_detailName);
        tv_detailAddress = findViewById(R.id.tv_detailAddress);
        tv_detailBusiness = findViewById(R.id.tv_detailBusiness);
        tv_detailTel = findViewById(R.id.tv_detailTel);
        tv_detailSns = findViewById(R.id.tv_detailSns);

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

        // 뷰에 받아온 값 설정하기
        tv_detailName.setText(cafe_name);
        tv_detailAddress.setText(address);
        tv_detailBusiness.setText(business_hour + holiday);
        tv_detailTel.setText(tel);
        tv_detailSns.setText(sns);

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