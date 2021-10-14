package com.hjh.likecafe;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class memberInfoModify extends AppCompatActivity {

    RequestQueue requestQueue;
    EditText et_changeNick, et_currentPw, et_changePw, et_chkPw;
    RadioGroup rg_sex;
    RadioButton rb_female, rb_male;
    Button btn_modify;
    String sex;

    // '생년월일'
    private TextView textView_Date;
    private DatePickerDialog.OnDateSetListener callbackMethod;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_info_modify);

        et_changeNick = findViewById(R.id.et_changeNick);
        et_currentPw = findViewById(R.id.et_currentPw);
        et_changePw = findViewById(R.id.et_changePw);
        et_chkPw = findViewById(R.id.et_chkPw);
        rg_sex = findViewById(R.id.rg_sex);
        rb_female = findViewById(R.id.rb_female);
        rb_male = findViewById(R.id.rb_male);
        btn_modify = findViewById(R.id.btn_modify);


        // 라디오 체크 리스너
        rg_sex.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (i == R.id.rb_female) {
                    Log.d("radiocheck", "woman");
                    sex = rb_female.getText().toString();
                } else if (i == R.id.rb_male) {
                    Log.d("radiocheck", "man");
                    sex = rb_male.getText().toString();
                }
            }
        });

        // 서버와 통신
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        // '수정하기' 버튼 클릭 리스너
        btn_modify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nick = et_changeNick.getText().toString();
                String currentPw = et_currentPw.getText().toString();
                String changePw = et_changePw.getText().toString();
                String birth = textView_Date.getText().toString();

                // 입력값 검사 부분 (시간 남을 시 닉네임 중복검사, 빈칸 검사 코드 추가)
                boolean chkPw = changePw.equals(et_chkPw.getText().toString());
                boolean chkDuplicateNick = true;
                boolean chkBlank = true;

                if (!chkPw) { // 만약 변경비번과 확인비번의 값이 다른경우
                    Toast.makeText(memberInfoModify.this,
                            "비밀번호를 다시 확인해주세요", Toast.LENGTH_SHORT).show();
                } else if (!chkDuplicateNick) { // 닉네임 중복검사?
                    Toast.makeText(memberInfoModify.this,
                            "닉네임 중복검사가 필요해요", Toast.LENGTH_SHORT).show();
                } else if (!chkBlank) { // 빈칸검사?
                    Toast.makeText(memberInfoModify.this,
                            "모든 입력란에 입력을 완료해주세요", Toast.LENGTH_SHORT).show();
                } else {
                    // id와 입력받은 값을 매개변수로 하여 modify 메소드 호출
                    modify("test", currentPw, nick, changePw, birth, sex); // 일단 임시로 id값 대신 test를 넣어주었음
                }
            }
        });

        this.InitializeView(); // '생년월일'
        this.InitializeListener(); // '생년월일'
    }

    private void InitializeView() { // '생년월일'
        textView_Date = (TextView) findViewById(R.id.textView_Date);
    }

    private void InitializeListener() { // '생년월일'
        callbackMethod = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int monthOfYear
                    , int dayOfMonth) {
                textView_Date.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
            }
        };
    }

    public void OnClickHandler(View view) // '생년월일'
    {
        DatePickerDialog dialog = new DatePickerDialog(this, callbackMethod, 1990, 7, 1);
        dialog.show();
    }

    // Json파일을 만들어 웹 서버로 보내기
    public void postModify(String nick, String pw, String birth, String sex) {
        String url = "http://172.30.1.8:3003/Member/Modify";
        StringRequest request = new StringRequest(
                Request.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = (JSONObject) (new JSONArray(response).get(0));
                            Log.d("status : ", jsonObject.getString("status"));
                            Intent intent = new Intent
                                    (memberInfoModify.this, MainActivity.class);
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
                params.put("nick", nick);
                params.put("pw", pw);
                params.put("birth", birth);
                params.put("sex", sex);

                return params;
            }
        };
        requestQueue.add(request);
    }

    public void modify(String id, String currentPw, String nick, String changePw, String birth, String sex) {
        // 먼저 현재 비밀번호가 일치하는지부터 확인해야함
        // 비밀번호랑 id가 매치되는지(올바른 현재 비밀번호를 입력했는지)는 로그인 요청으로 확인가능
        String url = "http://172.30.1.8:3003/Member/Login"; // 로그인 요청 : id와 pw가 회원 테이블에 있는지 확인해줌
        // 로그인 요청을 하면 서버에서 로그인 성공여부에 따라 status에 success 혹은 fail을 담아서 보내줌
        StringRequest request = new StringRequest(
                Request.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = (JSONObject) (new JSONArray(response).get(0));
                            Log.d("status : ", jsonObject.getString("status"));
                            String status = jsonObject.getString("status");

                            if (status.equals("success")) { // 로그인 성공 (비밀번호가 일치함)
                                postModify(nick, changePw, birth, sex); // postModify 메소드 호출
                            } else { // 로그인 실패 (비밀번호가 일치하지 않음)
                                Toast.makeText(memberInfoModify.this, "현재 비밀번호를 확인해주세요", Toast.LENGTH_SHORT).show();
                            }

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
                params.put("id", id);
                params.put("pw", currentPw);

                return params;
            }
        };
        requestQueue.add(request);
    }


}