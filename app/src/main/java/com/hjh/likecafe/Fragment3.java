package com.hjh.likecafe;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class Fragment3 extends Fragment {
    Button[] btns3;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_3, container, false);

        btns3 = new Button[9];
        for (int i = 0; i < btns3.length; i++) {
            int resID = getResources().getIdentifier("b" + (i + 1), "id", getActivity().getPackageName());
            btns3[i] = view.findViewById(resID);
        }
        for (int i =0; i< btns3.length;i++){
            btns3[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String currentGu = PreferenceManager.getString(getContext(), "gu");
                    String gu= ((Button) view).getText().toString();
                    if (currentGu.equals(gu)) {
                        ((Button)view).setBackgroundColor(Color.parseColor("#00ff0000"));
                        PreferenceManager.setString(getContext(),"gu","");
                    } else {
                        for(int i = 0; i < btns3.length; i++) {
                            if(currentGu.equals(btns3[i].getText().toString())) {
                                btns3[i].setBackgroundColor(Color.parseColor("#00ff0000"));
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