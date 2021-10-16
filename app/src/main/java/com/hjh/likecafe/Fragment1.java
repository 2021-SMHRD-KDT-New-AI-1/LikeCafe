package com.hjh.likecafe;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;


public class Fragment1 extends Fragment {
    Button[] btns;
    //ButtonFragment buttonFragment;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_1, container, false);

        btns = new Button[12];
        //for문을 통해 ImageView 9개 초기화!!
        for (int i = 0; i < btns.length; i++) {
            int resID = getResources().getIdentifier("s" + (i + 1), "id", getActivity().getPackageName());
            btns[i] = view.findViewById(resID);
        }


        for (int i =0; i< btns.length;i++){
            btns[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String gu = ((Button) view).getText().toString();

                    // 요기 있죠 요기 이부분에서 구 정보를
                    // Intent말고 SharedPreference 에다가 저장시키고!

                    Intent intent = new Intent(getActivity(), r_selection.class);
                    intent.putExtra("gu",gu);//데이터 넣기
                    startActivity(intent);

                }
            });
        }
//        btns[0].setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                String gu = btns[0].getText().toString();
//
//                //buttonFragment = new ButtonFragment();
//                //Bundle bundle = new Bundle();
//                //bundle.putString("gu", gu);
//                //buttonFragment.setArguments(bundle);
//
//                Intent intent = new Intent(getActivity(), r_selection.class);
//                intent.putExtra("gu",gu);//데이터 넣기
//                startActivity(intent);
//
//            }
//        });
//        btns[1].setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                String gu = btns[0].getText().toString();
//                Intent intent = new Intent(getActivity(), r_selection.class);
//                intent.putExtra("gu",gu);//데이터 넣기
//                startActivity(intent);
//
//            }
//        });

        return view;
    }
}