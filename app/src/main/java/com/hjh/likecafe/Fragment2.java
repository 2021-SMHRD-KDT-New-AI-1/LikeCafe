package com.hjh.likecafe;

import android.content.Intent;
import android.graphics.Color;
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

        View view = inflater.inflate(R.layout.fragment_2, container, false);

        btns2 = new Button[4];

        for (int i = 0; i < btns2.length; i++) {
            int resID = getResources().getIdentifier("g" + (i + 1), "id", getActivity().getPackageName());
            btns2[i] = view.findViewById(resID);
        }
        for (int i =0; i< btns2.length;i++){
            btns2[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String currentGu = PreferenceManager.getString(getContext(), "gu");
                    String gu= ((Button) view).getText().toString();
                    if (currentGu.equals(gu)) {
                        ((Button)view).setBackgroundColor(Color.parseColor("#00ff0000"));
                        PreferenceManager.setString(getContext(),"gu","");
                    } else {
                        for(int i = 0; i < btns2.length; i++) {
                            if(currentGu.equals(btns2[i].getText().toString())) {
                                btns2[i].setBackgroundColor(Color.parseColor("#00ff0000"));
                            }
                        }
                        ((Button)view).setBackgroundColor(Color.parseColor("#6B9900"));
                        PreferenceManager.setString(getContext(),"gu",gu);
                    }
                }
            });
        }
        return view;
    }
}