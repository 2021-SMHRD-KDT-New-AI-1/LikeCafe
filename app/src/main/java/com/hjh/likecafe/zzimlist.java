package com.hjh.likecafe;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class zzimlist extends AppCompatActivity {
    ListView lv_zzim;

    RequestQueue requestQueue;
    List<CafeVO> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zzimlist);

        lv_zzim = findViewById(R.id.lv_zzim);
        data = new ArrayList<CafeVO>();

        CafeAdapter adapter = new CafeAdapter(getApplicationContext(), R.layout.cafelist, data);
        lv_zzim.setAdapter(adapter);

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
                "테스트2의 sns주소", "테스트2의 카테고리", test, 2, false);

        data.add(vo1);
        data.add(vo2);

        adapter.notifyDataSetChanged();

    }
}