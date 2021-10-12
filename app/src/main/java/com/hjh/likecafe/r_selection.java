package com.hjh.likecafe;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;



public class r_selection extends AppCompatActivity {
    Button btn_r1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rselection);

        Button btn_r1 = findViewById(R.id.btn_r1);

        btn_r1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSupportFragmentManager().beginTransaction().replace(R.id.fl, new Fragment1()).commit();
            }
        });

    }
}