package com.hjh.likecafe;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class Fragment2 extends Fragment {
    Button[] btns2;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_2, container, false);

        btns2 = new Button[4];
        //for문을 통해 ImageView 9개 초기화!!
        for (int i = 0; i < btns2.length; i++) {
            int resID = getResources().getIdentifier("s" + (i + 1), "id", getActivity().getPackageName());
            btns2[i] = view.findViewById(resID);
        }
        for (int i =0; i< btns2.length;i++){
            btns2[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String gu = ((Button) view).getText().toString();
                    Intent intent = new Intent(getActivity(), r_selection.class);
                    intent.putExtra("gu",gu);//데이터 넣기
                    startActivity(intent);

                }
            });
        }
        return view;
    }
}