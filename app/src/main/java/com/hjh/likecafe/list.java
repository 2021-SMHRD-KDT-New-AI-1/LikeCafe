package com.hjh.likecafe;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.google.android.material.navigation.NavigationView;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        lv_cafe = findViewById(R.id.lv_cafe);
        data = new ArrayList<CafeVO>();
        toolbar = findViewById(R.id.toolbar);
        drawerLayout = findViewById(R.id.drawer_layout);
        setSupportActionBar(toolbar);

        CafeAdapter adapter = new CafeAdapter(getApplicationContext(), R.layout.cafelist, data);
        lv_cafe.setAdapter(adapter);

        if(requestQueue == null) {
            requestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        // 서버 연결 x, 테스트용 임시 데이터
        Map<String, String> test = new HashMap<>();
        CafeVO vo1 = new CafeVO(1, "테스트1", R.drawable.cafeimagexml, "테스트1의 주소",
                "테스트1의 운영시간", "테스트1의 휴무일", "테스트1의 전화번호",
                "테스트1의 sns주소", "테스트1의 카테고리", test, 1, true);
        CafeVO vo2 = new CafeVO(2, "테스트2", R.drawable.cafeimagexml, "테스트2의 주소",
                "테스트2의 운영시간", "테스트2의 휴무일", "테스트2의 전화번호",
                "테스트2의 sns주소", "테스트2의 카테고리", test, 2, true);

        data.add(vo1);
        data.add(vo2);

        adapter.notifyDataSetChanged();

        tv_detailResult = findViewById(R.id.tv_detailResult);
        ArrayList<String> detail = getIntent().getStringArrayListExtra("detail");
        String result = "";
        for (String keyword:detail) {
            result += "#"+keyword+" ";
        }
        tv_detailResult.setText(result);

        tv_theme = findViewById(R.id.tv_theme);
        tv_theme.setText(result);

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