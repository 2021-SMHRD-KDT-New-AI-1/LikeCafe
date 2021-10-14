package com.hjh.likecafe;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;

import android.widget.DatePicker;
import android.widget.TextView;


public class memberInfoModify extends AppCompatActivity {

    private TextView textView_Date; // '생년월일'
    private DatePickerDialog.OnDateSetListener callbackMethod; // '생년월일'


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_info_modify);

        this.InitializeView(); // '생년월일'
        this.InitializeListener(); // '생년월일'
    }

    private void InitializeView() { // '생년월일'
        textView_Date = (TextView)findViewById(R.id.tv_bday);
    }

    private void InitializeListener() { // '생년월일'
        callbackMethod = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int monthOfYear
                    , int dayOfMonth) {
                textView_Date.setText(year + "년 " + (monthOfYear+1) + "월 " + dayOfMonth + "일");
            }
        };
    }

    public void OnClickHandler(View view) // '생년월일'
    {
        DatePickerDialog dialog = new DatePickerDialog(this, callbackMethod, 2019, 5, 24);

        dialog.show();
    }


}