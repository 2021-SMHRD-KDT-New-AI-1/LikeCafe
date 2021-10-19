package com.hjh.likecafe;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;

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
import java.util.List;
import java.util.Map;

public class ReviewAdapter extends BaseAdapter {
    Context context; // 현재 페이지 정보
    int layout; // item 디자인 템플릿
    List<ReviewVO> data; // cafe item 클래스

    private RequestQueue requestQueue;

    private LayoutInflater inflater; // xml파일을 View객체로 변환
    ReviewViewHolder viewHolder; // ViewHolder 패턴을 활용하기 위한 변수

    public ReviewAdapter(Context context, int layout, List<ReviewVO> data) {
        this.context = context;
        this.layout = layout;
        this.data = data;

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int i) {
        return data.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(context);
        }


        if (view == null) {
            // xml파일(layout)을 view객체로 변환시켜 view에 저장한다.
            view = inflater.inflate(layout, null);

            viewHolder = new ReviewViewHolder();
            viewHolder.img_reviewListImg = view.findViewById(R.id.img_reviewListImg);
            viewHolder.tv_reviewListName = view.findViewById(R.id.tv_reviewListName);
            viewHolder.tv_reviewListContent = view.findViewById(R.id.tv_reviewListContent);
            viewHolder.btn_reviewDelete = view.findViewById(R.id.btn_reviewDelete);
            viewHolder.stars = new ImageView[5];
            for (int j = 0; j < viewHolder.stars.length; j++) {
                int resID = view.getResources().getIdentifier("img_star" + (j + 1), "id", context.getPackageName());
                viewHolder.stars[j] = view.findViewById(resID);
            }

            view.setTag(viewHolder);
        }

        viewHolder = (ReviewViewHolder) view.getTag();
        viewHolder.img_reviewListImg.setImageResource(data.get(i).getImage());
        viewHolder.tv_reviewListName.setText(data.get(i).getCafe_name());
        viewHolder.tv_reviewListContent.setText(data.get(i).getContent());
        double star = data.get(i).getStar();
        for (int j = 0; j < viewHolder.stars.length; j++) {
            if ((j + 1) <= star) {
                viewHolder.stars[j].setImageResource(R.drawable.star2);
            } else if ((j + 1) == star + 0.5) {
                viewHolder.stars[j].setImageResource(R.drawable.starhalf2);
            } else {
                viewHolder.stars[j].setImageResource(R.drawable.stargray);
            }
        }

        // 리뷰삭제 버튼을 눌렀을 경우
        viewHolder.btn_reviewDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "리뷰 삭제!", Toast.LENGTH_SHORT).show();
                reviewDelete(1,"test");
            }
        });

        return view;
    }
    // (리뷰삭제)서버에 보내기
    public void reviewDelete(int cafe_id, String mem_id) {
        String url = "http://172.30.1.8:3003/Review/ReviewDele";
        StringRequest request = new StringRequest(
                Request.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = (JSONObject) (new JSONArray(response).get(0));
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
                params.put("cafe_id", String.valueOf(cafe_id));
                params.put("mem_id", mem_id);

                return params;
            }
        };
        requestQueue.add(request);
    }

}
