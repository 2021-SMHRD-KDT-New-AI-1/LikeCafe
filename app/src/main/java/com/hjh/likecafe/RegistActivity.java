package com.hjh.likecafe;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;    //'생년월일'
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class RegistActivity extends AppCompatActivity {
    RequestQueue requestQueue;
    EditText et_memId, et_memPw, et_checkPw, et_nick;
    RadioGroup rg_gender;
    RadioButton rb_woman, rb_man;
    Button btn_submit;
    String gender;
    boolean registStatus;

    private TextView tv_birth;//'생년월일'
    private DatePickerDialog.OnDateSetListener callbackMethod;//'생년월일'


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regist);

        et_memId = findViewById(R.id.et_memId);
        et_memPw = findViewById(R.id.et_memPw);
        et_checkPw = findViewById(R.id.et_checkPw);
        et_nick = findViewById(R.id.et_nick);
        rg_gender = findViewById(R.id.rg_gender);
        rb_woman = findViewById(R.id.rb_woman);
        rb_man = findViewById(R.id.rb_man);
        btn_submit = findViewById(R.id.btn_submit);

        // 라디오 체크 리스너
        rg_gender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (i == R.id.rb_woman) {
                    Log.d("radiocheck", "woman");
                    gender = rb_woman.getText().toString();
                } else if (i == R.id.rb_man) {
                    Log.d("radiocheck", "man");
                    gender = rb_man.getText().toString();
                }
            }
        });

        // 서버와 통신
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        // 가입하기 버튼 클릭 리스너
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = et_memId.getText().toString();
                String pw = et_memPw.getText().toString();
                String nick = et_nick.getText().toString();
                String birth = tv_birth.getText().toString();

                // 입력값 검사 부분 (시간 남으면 ID, 닉네임 중복검사, 빈칸 검사 코드 추가)
                boolean checkPw = pw.equals(et_checkPw.getText().toString());
                boolean checkDuplicateId = true;
                boolean checkDuplicateNick = true;
                boolean checkBlank = true;

                if (!checkPw) {
                    // 비밀번호와 비밀번호 확인 값이 다른 경우
                    Toast.makeText(RegistActivity.this, "비밀번호를 다시 확인해주세요", Toast.LENGTH_SHORT).show();
                } else if (!checkDuplicateId) {
                    // id 중복검사를 실행하지 x
                    Toast.makeText(RegistActivity.this, "아이디 중복 검사가 필요해요", Toast.LENGTH_SHORT).show();
                } else if (!checkDuplicateNick) {
                    // nick 중복검사를 실행하지 x
                    Toast.makeText(RegistActivity.this, "닉네임 중복 검사가 필요해요", Toast.LENGTH_SHORT).show();
                } else if (!checkBlank) {
                    // 입력 칸에 공백이 있을 경우
                    Toast.makeText(RegistActivity.this, "모든 입력란에 입력을 완료해주세요", Toast.LENGTH_SHORT).show();
                } else {
                    postRegist(id, pw, nick, birth, gender);
                }
                //postRegist("test4","1234","testtest","2020-10-10","남자");
            }
        });

        this.InitializeView();//'생년월일'
        this.InitializeListener();//'생년월일'
    }

    public void InitializeView()//'생년월일'
    {
        tv_birth = (TextView) findViewById(R.id.tv_birth);
    }

    public void InitializeListener()//'생년월일'
    {
        callbackMethod = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                tv_birth.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
            }
        };
    }

    public void OnClickHandler(View view) //'생년월일'
    {
        DatePickerDialog dialog = new DatePickerDialog(this, callbackMethod, 1990, 7, 1);
        dialog.show();
    }

    // Json파일을 만들어 웹 서버로 보내기
    public void postRegist(String id, String pw, String nick, String birth, String gender) {
        String url = "http://172.30.1.8:3003/Member/Regist";
        StringRequest request = new StringRequest(
                Request.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // 응답 성공
                        try {
                            JSONObject jsonObject = (JSONObject) (new JSONArray(response).get(0));
                            Log.d("status : ", jsonObject.getString("status"));
                            registStatus = true;
                            Intent intent = new Intent(RegistActivity.this, loginsucc.class);
                            startActivity(intent);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        ) {
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