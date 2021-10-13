package com.hjh.likecafe;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

import android.widget.DatePicker;  //'생년월일'
import android.widget.TextView;    //'생년월일'


public class RegistActivity extends AppCompatActivity {
    RequestQueue requestQueue;
    Button button3;


    private TextView textView_Date;//'생년월일'
    private DatePickerDialog.OnDateSetListener callbackMethod;//'생년월일'


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regist);

        button3 = findViewById(R.id.button3);

        // 서버와 통신
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                postRegist("test3","111","ttttt","2000-10-10","남자");
            }
        });

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

    public void postRegist(String id, String pw, String nick, String birth, String gender) {
        String url = "http://172.30.1.8:3003/MemberRegist";
        StringRequest request = new StringRequest(
                Request.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("mem_id", id);
                params.put("pw", pw);
                params.put("nick", nick);
                params.put("birth", birth);
                params.put("gender", gender);

                return params;
            }
        };
        requestQueue.add(request);
    }


}