package com.hjh.likecafe;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;    //'생년월일'
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;  //'생년월일'
import android.widget.TextView;    //'생년월일'


public class RegistActivity extends AppCompatActivity {


    private TextView textView_Date;//'생년월일'
    private DatePickerDialog.OnDateSetListener callbackMethod;//'생년월일'


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regist);

        this.InitializeView();//'생년월일'
        this.InitializeListener();//'생년월일'
    }

    public void InitializeView()//'생년월일'
    {
        textView_Date = (TextView)findViewById(R.id.textView_date);
    }
    public void InitializeListener()//'생년월일'
    {
        callbackMethod = new DatePickerDialog.OnDateSetListener()
        {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth)
            {
                textView_Date.setText(year + "년 " + (monthOfYear+1) + "월 " + dayOfMonth + "일");
            }
        };
    }
    public void OnClickHandler(View view) //'생년월일'
    {
        DatePickerDialog dialog = new DatePickerDialog(this, callbackMethod, 1990, 7, 1);



        dialog.show();
    }


}