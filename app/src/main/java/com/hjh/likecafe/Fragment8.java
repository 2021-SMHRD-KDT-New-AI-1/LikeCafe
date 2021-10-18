package com.hjh.likecafe;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class Fragment8 extends Fragment {
    Button[] btns8;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_8, container, false);

        btns8 = new Button[5];
        for (int i = 0; i < btns8.length; i++) {
            int resID = getResources().getIdentifier("u" + (i + 1), "id", getActivity().getPackageName());
            btns8[i] = view.findViewById(resID);
        }
        for (int i =0; i< btns8.length;i++){
            btns8[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String currentGu = PreferenceManager.getString(getContext(), "gu");
                    String gu= ((Button) view).getText().toString();
                    if (currentGu.equals(gu)) {
                        ((Button)view).setBackgroundColor(Color.parseColor("#00ff0000"));
                        PreferenceManager.setString(getContext(),"gu","");
                    } else {
                        for(int i = 0; i < btns8.length; i++) {
                            if(currentGu.equals(btns8[i].getText().toString())) {
                                btns8[i].setBackgroundColor(Color.parseColor("#00ff0000"));
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