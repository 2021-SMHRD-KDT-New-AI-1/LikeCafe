package com.hjh.likecafe;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CafeAdapter extends BaseAdapter {
    Context context; // 현재 페이지 정보
    int layout; // item 디자인 템플릿
    List<CafeVO> data; // cafe item 클래스

    private LayoutInflater inflater; // xml파일을 View객체로 변환
//    ViewHolder viewHolder; // ViewHolder 패턴을 활용하기 위한 변수

    public CafeAdapter(Context context, int layout, List<CafeVO> data) {
        this.context = context;
        this.layout = layout;
        this.data = data;
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        return null;
    }
}
