package com.pastir.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pastir.R;
import com.pastir.model.Chapter;
import com.pastir.model.OnListItemClickListener;

import java.util.List;

public class ChapterItemAdapter extends android.widget.BaseAdapter {

    private Context mContext;
    private List<Chapter> mList;
    private OnListItemClickListener mListener;

    public ChapterItemAdapter(Context context, List<Chapter> list, OnListItemClickListener listener) {
        mList = list;
        mListener = listener;
        mContext = context;
    }


    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int i) {
        return mList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        TextView tvItemLabel;
        if (view == null) { //If not recycled
            LayoutInflater inflater = LayoutInflater.from(mContext);
            view = inflater.inflate(R.layout.chapter_grid_item, viewGroup, false);
        }

        tvItemLabel = view.findViewById(R.id.tvItemLabel);

        tvItemLabel.setText(mList.get(i).getNumber() + "");

        return view;
    }
}
