package com.hjh.likecafe;


import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;

import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
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

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;


public class memberInfoModify extends AppCompatActivity {

    RequestQueue requestQueue;
    EditText et_changeNick, et_currentPw, et_changePw, et_chkPw;
    RadioGroup rg_sex;
    RadioButton rb_female, rb_male;
    Button btn_modify, btn_picChange;
    String sex;
    boolean modifyStatus;

    ImageView img_profile;

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
        btn_picChange = findViewById(R.id.btn_picChange);



        // '사진수정'버튼 클릭 시 폰 갤러리 열기
        btn_picChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent,1);
            }
        });


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
                String pw = et_changePw.getText().toString();
                String birth = textView_Date.getText().toString();

                // 입력값 검사 부분 (시간 남을 시 닉네임 중복검사, 빈칸 검사 코드 추가)
                boolean chkPw = pw.equals(et_chkPw.getText().toString());
                boolean chkDuplicateNick = true;
                boolean chkBlank = true;
                // (보류)boolean chkcurrentPw = this.chkcurrentPw(id, pw);

                if (!chkPw) { // 만약 변경비번과 확인비번의 값이 다른경우
                    Toast.makeText(memberInfoModify.this,
                            "비밀번호를 다시 확인해주세요", Toast.LENGTH_SHORT).show();
                } else if (!chkDuplicateNick) { // 닉네임 중복검사?
                    Toast.makeText(memberInfoModify.this,
                            "닉네임 중복검사가 필요해요", Toast.LENGTH_SHORT).show();
                } else if (!chkBlank) { // 빈칸검사?
                    Toast.makeText(memberInfoModify.this,
                            "모든 입력란에 입력을 완료해주세요", Toast.LENGTH_SHORT).show();
                } // (보류) else if (!chkcurrentPw) {토스트'비밀번호가 틀렸습니다.'}
                else {
                    postModify(nick, pw, birth, sex);
                }
            }
        });

        this.InitializeView(); // '생년월일'
        this.InitializeListener(); // '생년월일'
    }

        //선택한 사진으로 프사바꾸기
        @Override
        protected void onActivityResult(int requestCode, int resultCode, Intent data) {
            super.onActivityResult(requestCode, resultCode, data);

            img_profile = findViewById(R.id.img_profile);

            if (requestCode == 1) {

                if (resultCode == RESULT_OK) {
                    try {
                        InputStream in = getContentResolver().openInputStream(data.getData());
                        Bitmap img = BitmapFactory.decodeStream(in);
                        in.close();

                        img_profile.setImageBitmap(img);
                        Toast.makeText(memberInfoModify.this,
                                "프로필 사진이 변경되었다능!", Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }


        // '생년월일'
        private void InitializeView() { // '생년월일'
            textView_Date = (TextView) findViewById(R.id.textView_Date);
        }

        // '생년월일'
        private void InitializeListener() {
            callbackMethod = new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker, int year, int monthOfYear
                        , int dayOfMonth) {
                    textView_Date.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                }
            };
        }

        // '생년월일'
        public void OnClickHandler(View view)
        {
            DatePickerDialog dialog = new DatePickerDialog(this, callbackMethod, 1990, 7, 1);
            dialog.show();
        }

    // Json파일을 만들어 웹 서버로 보내기
    public void postModify(String nick, String pw, String birth, String sex) {
        String url = "http://172.30.1.12:3003/Member/Modify";
        StringRequest request = new StringRequest(
                Request.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = (JSONObject) (new JSONArray(response).get(0));
                            Log.d("status : ", jsonObject.getString("status"));
                            modifyStatus = true;
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

    public boolean chkcurrentPw(String id, String pw) {
        // 서버에 id를 보내서 서버로부터 pw를 받아온다.
        // 받아온 pw와 매개변수 pw를 비교하여 같으면 t 아니면 f 리턴
        return true;
    }




}