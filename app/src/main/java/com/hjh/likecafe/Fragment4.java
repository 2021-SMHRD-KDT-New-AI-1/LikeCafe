package com.hjh.likecafe;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class Fragment4 extends Fragment {
    Button[] btns4;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_4, container, false);

        btns4 = new Button[8];
        for (int i = 0; i < btns4.length; i++) {
            int resID = getResources().getIdentifier("d" + (i + 1), "id", getActivity().getPackageName());
            btns4[i] = view.findViewById(resID);
        }
        for (int i =0; i< btns4.length;i++){
            btns4[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String currentGu = PreferenceManager.getString(getContext(), "gu");
                    String gu= ((Button) view).getText().toString();
                    if (currentGu.equals(gu)) {
                        ((Button)view).setBackgroundColor(Color.parseColor("#00ff0000"));
                        PreferenceManager.setString(getContext(),"gu","");
                    } else {
                        for(int i = 0; i < btns4.length; i++) {
                            if(currentGu.equals(btns4[i].getText().toString())) {
                                btns4[i].setBackgroundColor(Color.parseColor("#00ff0000"));
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