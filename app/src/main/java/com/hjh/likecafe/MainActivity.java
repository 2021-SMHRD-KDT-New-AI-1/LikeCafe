package com.hjh.likecafe;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    TextView tv_r_choice;
    static boolean click_r;
    Toolbar toolbar;
    DrawerLayout drawerLayout;

    //    ArrayList<Object> detaiList = new ArrayList<Object>();
    ArrayList<String> detail;

    Button btn_search,
            btn_exclusive, btn_group, btn_reserve, btn_field, btn_parking, btn_nokids, btn_toil,
            btn_con, btn_qui, btn_bgm, btn_com, btn_cou, btn_zer,
            btn_veg, btn_alcohol, btn_beans, btn_decaffe, btn_wf, btn_darkmood, btn_outf, //디테일버튼!
            btn_single, btn_together, btn_photo, btn_coffee, btn_dessert, btn_unique;
    boolean[] isClick;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);
        drawerLayout = findViewById(R.id.drawer_layout);
        setSupportActionBar(toolbar);

        btn_search = findViewById(R.id.btn_search);
        tv_r_choice = findViewById(R.id.tv_r_choice);
        btn_exclusive = findViewById(R.id.btn_exclusive);
        btn_group = findViewById(R.id.btn_group);
        btn_reserve = findViewById(R.id.btn_reserve);
        btn_field = findViewById(R.id.btn_field);
        btn_parking = findViewById(R.id.btn_parking);
        btn_nokids = findViewById(R.id.btn_nokids);
        btn_toil = findViewById(R.id.btn_toil);
        btn_con = findViewById(R.id.btn_con);
        btn_qui = findViewById(R.id.btn_qui);
        btn_bgm = findViewById(R.id.btn_bgm);
        btn_com = findViewById(R.id.btn_com);
        btn_cou = findViewById(R.id.btn_cou);
        btn_zer = findViewById(R.id.btn_zer);
        btn_veg = findViewById(R.id.btn_veg);
        btn_alcohol = findViewById(R.id.btn_alcohol);
        btn_beans = findViewById(R.id.btn_beans);
        btn_decaffe = findViewById(R.id.btn_decaffe);
        btn_wf = findViewById(R.id.btn_wf);
        btn_darkmood = findViewById(R.id.btn_darkmood);
        btn_outf = findViewById(R.id.btn_outf);
        btn_single = findViewById(R.id.btn_single);
        btn_together = findViewById(R.id.btn_together);
        btn_photo = findViewById(R.id.btn_photo);
        btn_coffee = findViewById(R.id.btn_coffee);
        btn_dessert = findViewById(R.id.btn_dessert);
        btn_unique = findViewById(R.id.btn_unique);


        isClick = new boolean[20];
        for (int i = 0; i < isClick.length; i++) {
            isClick[i] = false;
        }


        detail = new ArrayList<>();

        tv_r_choice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent(출발 Activity.this, 도착 Activity.class)
                click_r = true;
                Intent intent = new Intent(MainActivity.this, r_selection.class);
                startActivity(intent);
            }
        });

//        String region = getIntent().getStringExtra("region");
//        if(region.equals("")) {
//            tv_r_choice.setText("지역선택");
//        } else {
//            tv_r_choice.setText(region);
//        }


        if (click_r) {
            String region = getIntent().getStringExtra("region");
            PreferenceManager.setString(this,"gu","");

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
        switch (item.getItemId()) {
            case android.R.id.home: { // 뒤로가기 버튼 눌렀을 때
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }




    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.btn_exclusive:
                // 눌렀을때 처리??
                if (isClick[0]) { // 눌렸을때 누르면
                    btn_exclusive.setTextColor(Color.BLACK);
                    btn_exclusive.setBackgroundColor(Color.parseColor("#FAF8F8"));
                    isClick[0] = false;
                    // 값 빼기
                    detail.remove(((Button) view).getText().toString());

                } else { // 안눌려있을때 누르면
                    btn_exclusive.setTextColor(Color.WHITE);
                    btn_exclusive.setBackgroundColor(Color.parseColor("#494F39"));
                    isClick[0] = true;
                    // 값 더하기
                    detail.add(((Button) view).getText().toString());
                }
                Log.d("isClick", String.valueOf(isClick[0]));
                break;

            case R.id.btn_group:
                if (isClick[1]) { // 눌렸을때 누르면
                    btn_group.setTextColor(Color.BLACK);
                    btn_group.setBackgroundColor(Color.parseColor("#FAF8F8"));
                    isClick[1] = false;
                    detail.remove(((Button) view).getText().toString());
                } else { // 안눌려있을때 누르면
                    btn_group.setTextColor(Color.WHITE);
                    btn_group.setBackgroundColor(Color.parseColor("#494F39"));
                    isClick[1] = true;
                    detail.add(((Button) view).getText().toString());
                }
                Log.d("isClick", String.valueOf(isClick[1]));
                break;
            case R.id.btn_reserve:
                if (isClick[2]) { // 눌렸을때 누르면
                    btn_reserve.setTextColor(Color.BLACK);
                    btn_reserve.setBackgroundColor(Color.parseColor("#FAF8F8"));
                    isClick[2] = false;
                    detail.remove(((Button) view).getText().toString());
                } else { // 안눌려있을때 누르면
                    btn_reserve.setTextColor(Color.WHITE);
                    btn_reserve.setBackgroundColor(Color.parseColor("#494F39"));
                    isClick[2] = true;
                    detail.add(((Button) view).getText().toString());
                }
                Log.d("isClick", String.valueOf(isClick[2]));
                break;
            case R.id.btn_field:
                if (isClick[3]) { // 눌렸을때 누르면
                    btn_field.setTextColor(Color.BLACK);
                    btn_field.setBackgroundColor(Color.parseColor("#FAF8F8"));
                    isClick[3] = false;
                    detail.remove(((Button) view).getText().toString());
                } else { // 안눌려있을때 누르면
                    btn_field.setTextColor(Color.WHITE);
                    btn_field.setBackgroundColor(Color.parseColor("#494F39"));
                    isClick[3] = true;
                    detail.add(((Button) view).getText().toString());
                }
                Log.d("isClick", String.valueOf(isClick[3]));
                break;
            case R.id.btn_parking:
                if (isClick[4]) { // 눌렸을때 누르면
                    btn_parking.setTextColor(Color.BLACK);
                    btn_parking.setBackgroundColor(Color.parseColor("#FAF8F8"));
                    isClick[4] = false;
                    detail.remove(((Button) view).getText().toString());
                } else { // 안눌려있을때 누르면
                    btn_parking.setTextColor(Color.WHITE);
                    btn_parking.setBackgroundColor(Color.parseColor("#494F39"));
                    isClick[4] = true;
                    detail.add(((Button) view).getText().toString());
                }
                Log.d("isClick", String.valueOf(isClick[4]));
                break;
            case R.id.btn_nokids:
                if (isClick[5]) { // 눌렸을때 누르면
                    btn_nokids.setTextColor(Color.BLACK);
                    btn_nokids.setBackgroundColor(Color.parseColor("#FAF8F8"));
                    isClick[5] = false;
                    detail.remove(((Button) view).getText().toString());
                } else { // 안눌려있을때 누르면
                    btn_nokids.setTextColor(Color.WHITE);
                    btn_nokids.setBackgroundColor(Color.parseColor("#494F39"));
                    isClick[5] = true;
                    detail.add(((Button) view).getText().toString());
                }
                Log.d("isClick", String.valueOf(isClick[5]));
                break;
            case R.id.btn_toil:
                if (isClick[6]) { // 눌렸을때 누르면
                    btn_toil.setTextColor(Color.BLACK);
                    btn_toil.setBackgroundColor(Color.parseColor("#FAF8F8"));
                    isClick[6] = false;
                    detail.remove(((Button) view).getText().toString());
                } else { // 안눌려있을때 누르면
                    btn_toil.setTextColor(Color.WHITE);
                    btn_toil.setBackgroundColor(Color.parseColor("#494F39"));
                    isClick[6] = true;
                    detail.add(((Button) view).getText().toString());
                }
                Log.d("isClick", String.valueOf(isClick[6]));
                break;
            case R.id.btn_con:
                if (isClick[7]) { // 눌렸을때 누르면
                    btn_con.setTextColor(Color.BLACK);
                    btn_con.setBackgroundColor(Color.parseColor("#FAF8F8"));
                    isClick[7] = false;
                    detail.remove(((Button) view).getText().toString());
                } else { // 안눌려있을때 누르면
                    btn_con.setTextColor(Color.WHITE);
                    btn_con.setBackgroundColor(Color.parseColor("#494F39"));
                    isClick[7] = true;
                    detail.add(((Button) view).getText().toString());
                }
                Log.d("isClick", String.valueOf(isClick[7]));
                break;
            case R.id.btn_qui:
                if (isClick[8]) { // 눌렸을때 누르면
                    btn_qui.setTextColor(Color.BLACK);
                    btn_qui.setBackgroundColor(Color.parseColor("#FAF8F8"));
                    isClick[8] = false;
                    detail.remove(((Button) view).getText().toString());
                } else { // 안눌려있을때 누르면
                    btn_qui.setTextColor(Color.WHITE);
                    btn_qui.setBackgroundColor(Color.parseColor("#494F39"));
                    isClick[8] = true;
                    detail.add(((Button) view).getText().toString());
                }
                Log.d("isClick", String.valueOf(isClick[8]));
                break;
            case R.id.btn_bgm:
                if (isClick[9]) { // 눌렸을때 누르면
                    btn_bgm.setTextColor(Color.BLACK);
                    btn_bgm.setBackgroundColor(Color.parseColor("#FAF8F8"));
                    isClick[9] = false;
                    detail.remove(((Button) view).getText().toString());
                } else { // 안눌려있을때 누르면
                    btn_bgm.setTextColor(Color.WHITE);
                    btn_bgm.setBackgroundColor(Color.parseColor("#494F39"));
                    isClick[9] = true;
                    detail.add(((Button) view).getText().toString());
                }
                Log.d("isClick", String.valueOf(isClick[9]));
                break;
            case R.id.btn_com:
                if (isClick[10]) { // 눌렸을때 누르면
                    btn_com.setTextColor(Color.BLACK);
                    btn_com.setBackgroundColor(Color.parseColor("#FAF8F8"));
                    isClick[10] = false;
                    detail.remove(((Button) view).getText().toString());
                } else { // 안눌려있을때 누르면
                    btn_com.setTextColor(Color.WHITE);
                    btn_com.setBackgroundColor(Color.parseColor("#494F39"));
                    isClick[10] = true;
                    detail.add(((Button) view).getText().toString());
                }
                Log.d("isClick", String.valueOf(isClick[10]));
                break;
            case R.id.btn_cou:
                if (isClick[11]) { // 눌렸을때 누르면
                    btn_cou.setTextColor(Color.BLACK);
                    btn_cou.setBackgroundColor(Color.parseColor("#FAF8F8"));
                    isClick[11] = false;
                    detail.remove(((Button) view).getText().toString());
                } else { // 안눌려있을때 누르면
                    btn_cou.setTextColor(Color.WHITE);
                    btn_cou.setBackgroundColor(Color.parseColor("#494F39"));
                    isClick[11] = true;
                    detail.add(((Button) view).getText().toString());
                }
                Log.d("isClick", String.valueOf(isClick[11]));
                break;
            case R.id.btn_zer:
                if (isClick[12]) { // 눌렸을때 누르면
                    btn_zer.setTextColor(Color.BLACK);
                    btn_zer.setBackgroundColor(Color.parseColor("#FAF8F8"));
                    isClick[12] = false;
                    detail.remove(((Button) view).getText().toString());
                } else { // 안눌려있을때 누르면
                    btn_zer.setTextColor(Color.WHITE);
                    btn_zer.setBackgroundColor(Color.parseColor("#494F39"));
                    isClick[12] = true;
                    detail.add(((Button) view).getText().toString());
                }
                Log.d("isClick", String.valueOf(isClick[12]));
                break;
            case R.id.btn_veg:
                if (isClick[13]) { // 눌렸을때 누르면
                    btn_veg.setTextColor(Color.BLACK);
                    btn_veg.setBackgroundColor(Color.parseColor("#FAF8F8"));
                    isClick[13] = false;
                    detail.remove(((Button) view).getText().toString());
                } else { // 안눌려있을때 누르면
                    btn_veg.setTextColor(Color.WHITE);
                    btn_veg.setBackgroundColor(Color.parseColor("#494F39"));
                    isClick[13] = true;
                    detail.add(((Button) view).getText().toString());
                }
                Log.d("isClick", String.valueOf(isClick[13]));
                break;
            case R.id.btn_alcohol:
                if (isClick[14]) { // 눌렸을때 누르면
                    btn_alcohol.setTextColor(Color.BLACK);
                    btn_alcohol.setBackgroundColor(Color.parseColor("#FAF8F8"));
                    isClick[14] = false;
                    detail.remove(((Button) view).getText().toString());
                } else { // 안눌려있을때 누르면
                    btn_alcohol.setTextColor(Color.WHITE);
                    btn_alcohol.setBackgroundColor(Color.parseColor("#494F39"));
                    isClick[14] = true;
                    detail.add(((Button) view).getText().toString());
                }
                Log.d("isClick", String.valueOf(isClick[14]));
                break;
            case R.id.btn_beans:
                if (isClick[15]) { // 눌렸을때 누르면
                    btn_beans.setTextColor(Color.BLACK);
                    btn_beans.setBackgroundColor(Color.parseColor("#FAF8F8"));
                    isClick[15] = false;
                    detail.remove(((Button) view).getText().toString());
                } else { // 안눌려있을때 누르면
                    btn_beans.setTextColor(Color.WHITE);
                    btn_beans.setBackgroundColor(Color.parseColor("#494F39"));
                    isClick[15] = true;
                    detail.add(((Button) view).getText().toString());
                }
                Log.d("isClick", String.valueOf(isClick[15]));
                break;
            case R.id.btn_decaffe:
                if (isClick[16]) { // 눌렸을때 누르면
                    btn_decaffe.setTextColor(Color.BLACK);
                    btn_decaffe.setBackgroundColor(Color.parseColor("#FAF8F8"));
                    isClick[16] = false;
                    detail.remove(((Button) view).getText().toString());
                } else { // 안눌려있을때 누르면
                    btn_decaffe.setTextColor(Color.WHITE);
                    btn_decaffe.setBackgroundColor(Color.parseColor("#494F39"));
                    isClick[16] = true;
                    detail.add(((Button) view).getText().toString());
                }
                Log.d("isClick", String.valueOf(isClick[16]));
                break;
            case R.id.btn_wf:
                if (isClick[17]) { // 눌렸을때 누르면
                    btn_wf.setTextColor(Color.BLACK);
                    btn_wf.setBackgroundColor(Color.parseColor("#FAF8F8"));
                    isClick[17] = false;
                    detail.remove(((Button) view).getText().toString());
                } else { // 안눌려있을때 누르면
                    btn_wf.setTextColor(Color.WHITE);
                    btn_wf.setBackgroundColor(Color.parseColor("#494F39"));
                    isClick[17] = true;
                    detail.add(((Button) view).getText().toString());
                }
                Log.d("isClick", String.valueOf(isClick[17]));
                break;
            case R.id.btn_darkmood:
                if (isClick[18]) { // 눌렸을때 누르면
                    btn_darkmood.setTextColor(Color.BLACK);
                    btn_darkmood.setBackgroundColor(Color.parseColor("#FAF8F8"));
                    isClick[18] = false;
                    detail.remove(((Button) view).getText().toString());
                } else { // 안눌려있을때 누르면
                    btn_darkmood.setTextColor(Color.WHITE);
                    btn_darkmood.setBackgroundColor(Color.parseColor("#494F39"));
                    isClick[18] = true;
                    detail.add(((Button) view).getText().toString());
                }
                Log.d("isClick", String.valueOf(isClick[18]));
                break;
            case R.id.btn_outf:
                if (isClick[19]) { // 눌렸을때 누르면
                    btn_outf.setTextColor(Color.BLACK);
                    btn_outf.setBackgroundColor(Color.parseColor("#FAF8F8"));
                    isClick[19] = false;
                    detail.remove(((Button) view).getText().toString());
                } else { // 안눌려있을때 누르면
                    btn_outf.setTextColor(Color.WHITE);
                    btn_outf.setBackgroundColor(Color.parseColor("#494F39"));
                    isClick[19] = true;
                    detail.add(((Button) view).getText().toString());
                }
                Log.d("isClick", String.valueOf(isClick[19]));
                break;


            case R.id.btn_search:

//                Toast.makeText(MainActivity.this, detail.get(0), Toast.LENGTH_SHORT).show();

                Intent intent_detail = new Intent(MainActivity.this, list.class);
                intent_detail.putExtra("detail", detail);//데이터 넣기
                startActivity(intent_detail);
                detail.clear();
                // 선택된 버튼 색을 다시 바꿔놔야됨.


//                Intent intent_move = new Intent(MainActivity.this, detail_list.class);
//                startActivity(intent_move);
                break;

            case R.id.btn_single:
                detail.clear();
                detail.add("혼자가기 좋은");
                Intent intent = new Intent(MainActivity.this, list.class);
                intent.putExtra("detail", detail);//데이터 넣기
                startActivity(intent);
                break;
            case R.id.btn_together  :
                detail.clear();
                detail.add("함께가기 좋은");
                intent = new Intent(MainActivity.this, list.class);
                intent.putExtra("detail", detail);//데이터 넣기
                startActivity(intent);
                break;
            case R.id.btn_photo    :
                detail.clear();
                detail.add("사진찍기 좋은");
                intent = new Intent(MainActivity.this, list.class);
                intent.putExtra("detail", detail);//데이터 넣기
                startActivity(intent);
                break;
            case R.id.btn_coffee    :
                detail.clear();
                detail.add("커피맛이 좋은");
                intent = new Intent(MainActivity.this, list.class);
                intent.putExtra("detail", detail);//데이터 넣기
                startActivity(intent);
                break;
            case R.id.btn_dessert      :
                detail.clear();
                detail.add("디저트가 맛있는");
                intent = new Intent(MainActivity.this, list.class);
                intent.putExtra("detail", detail);//데이터 넣기
                startActivity(intent);
                break;
            case R.id.btn_unique    :
                detail.clear();
                detail.add("이색적인");
                intent = new Intent(MainActivity.this, list.class);
                intent.putExtra("detail", detail);//데이터 넣기
                startActivity(intent);
                break;


        }


    }


}
