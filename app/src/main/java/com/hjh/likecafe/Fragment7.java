package com.hjh.likecafe;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class Fragment7 extends Fragment {
    Button[] btns7;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_7, container, false);

        btns7 = new Button[5];
        for (int i = 0; i < btns7.length; i++) {
            int resID = getResources().getIdentifier("dd" + (i + 1), "id", getActivity().getPackageName());
            btns7[i] = view.findViewById(resID);
        }
        for (int i =0; i< btns7.length;i++){
            btns7[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String currentGu = PreferenceManager.getString(getContext(), "gu");
                    String gu= ((Button) view).getText().toString();
                    if (currentGu.equals(gu)) {
                        ((Button)view).setBackgroundColor(Color.parseColor("#00ff0000"));
                        PreferenceManager.setString(getContext(),"gu","");
                    } else {
                        for(int i = 0; i < btns7.length; i++) {
                            if(currentGu.equals(btns7[i].getText().toString())) {
                                btns7[i].setBackgroundColor(Color.parseColor("#00ff0000"));
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