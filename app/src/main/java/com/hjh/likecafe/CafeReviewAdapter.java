package com.hjh.likecafe;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import java.util.List;

public class CafeReviewAdapter extends BaseAdapter {
    Context context; // 현재 페이지 정보
    int layout; // item 디자인 템플릿
    List<ReviewVO> data; // cafe item 클래스

    private RequestQueue requestQueue;

    private LayoutInflater inflater; // xml파일을 View객체로 변환
    CafeReviewViewHolder viewHolder;

    public CafeReviewAdapter(Context context, int layout, List<ReviewVO> data) {
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

            viewHolder = new CafeReviewViewHolder();
            viewHolder.img_reviewDetail = view.findViewById(R.id.img_reviewDetail);
            viewHolder.tv_reviewDetailName = view.findViewById(R.id.tv_reviewDetailName);
            viewHolder.tv_reviewDetailContent = view.findViewById(R.id.tv_reviewDetailContent);
            viewHolder.detailStars = new ImageView[5];
            for (int j = 0; j < viewHolder.detailStars.length; j++) {
                int resID = view.getResources().getIdentifier("img_detailStar" + (j + 1), "id", context.getPackageName());
                viewHolder.detailStars[j] = view.findViewById(resID);
            }

            view.setTag(viewHolder);
        }

        viewHolder = (CafeReviewViewHolder) view.getTag();
        viewHolder.img_reviewDetail.setImageBitmap(data.get(i).getImage());
        viewHolder.tv_reviewDetailName.setText(data.get(i).getNick());
        viewHolder.tv_reviewDetailContent.setText(data.get(i).getContent());
        double star = data.get(i).getStar();
        for (int j = 0; j < viewHolder.detailStars.length; j++) {
            if ((j + 1) <= star) {
                viewHolder.detailStars[j].setImageResource(R.drawable.star2);
            } else if ((j + 1) == star + 0.5) {
                viewHolder.detailStars[j].setImageResource(R.drawable.starhalf2);
            } else {
                viewHolder.detailStars[j].setImageResource(R.drawable.stargray);
            }
        }


        return view;
    }


}
