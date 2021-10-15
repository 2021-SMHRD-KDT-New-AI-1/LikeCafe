package com.hjh.likecafe;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Toast;

import java.util.List;

public class CafeAdapter extends BaseAdapter {
    Context context; // 현재 페이지 정보
    int layout; // item 디자인 템플릿
    List<CafeVO> data; // cafe item 클래스

    private LayoutInflater inflater; // xml파일을 View객체로 변환
    CafeViewHolder viewHolder; // ViewHolder 패턴을 활용하기 위한 변수

    public CafeAdapter(Context context, int layout, List<CafeVO> data) {
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

        if(view == null) {
            // xml파일(layout)을 view객체로 변환시켜 view에 저장한다.
            view = inflater.inflate(layout, null);

            viewHolder = new CafeViewHolder();
            viewHolder.cafeListBody = view.findViewById(R.id.cafeListBody);
            viewHolder.img_cafeListImage = view.findViewById(R.id.img_cafeListImage);
            viewHolder.img_cafeListZzim = view.findViewById(R.id.img_cafeListZzim);
            viewHolder.tv_cafeListName = view.findViewById(R.id.tv_cafeListName);
            viewHolder.tv_cafeListAddress = view.findViewById(R.id.tv_cafeListAddress);
            viewHolder.tv_cafeListZzimCnt = view.findViewById(R.id.tv_cafeListZzimCnt);

            view.setTag(viewHolder);
        }

        viewHolder = (CafeViewHolder) view.getTag();
        viewHolder.img_cafeListImage.setImageResource(data.get(i).getImage());
        viewHolder.tv_cafeListName.setText(data.get(i).getName());
        viewHolder.tv_cafeListAddress.setText(data.get(i).getAddress());
        viewHolder.tv_cafeListZzimCnt.setText(String.valueOf(data.get(i).getZzimCnt()));
        if(data.get(i).isZzimSel()) {
            viewHolder.img_cafeListZzim.setImageResource(R.drawable.zzimsel);
        } else {
            viewHolder.img_cafeListZzim.setImageResource(R.drawable.zzim);
        }
        viewHolder.img_cafeListZzim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(data.get(i).isZzimSel()) {
                    // 찜 목록에서 삭제하는 기능
                    Toast.makeText(context.getApplicationContext(), "찜 삭제!", Toast.LENGTH_SHORT).show();
                } else {
                    // 찜 목록에 추가하는 기능
                    Toast.makeText(context.getApplicationContext(), "찜 추가!", Toast.LENGTH_SHORT).show();
                }
                data.get(i).setZzimSel(!data.get(i).isZzimSel());
                notifyDataSetChanged();
            }
        });
        viewHolder.cafeListBody.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context.getApplicationContext(), "카페 상세 정보로 이동!", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }
}
