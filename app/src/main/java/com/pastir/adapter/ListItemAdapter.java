package com.pastir.adapter;

import com.pastir.model.ListItem;
import com.pastir.model.OnListItemClickListener;

import java.util.List;

/**
 * Used for handling loading of simple list items
 */

public class ListItemAdapter extends BaseAdapter {

    private int mLayoutId;
    private List<? extends ListItem> mList;
    private OnListItemClickListener mListener;

    public ListItemAdapter(int layoutId, List<? extends ListItem> list, OnListItemClickListener listener) {
        mLayoutId = layoutId;
        mList = list;
        mListener = listener;
    }

    @Override
    protected Object getItemForPosition(int position) {
        return mList.get(position);
    }

    @Override
    protected OnListItemClickListener getListener() {
        return mListener;
    }

    @Override
    protected int getLayoutIdForPosition(int position) {
        return mLayoutId;
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }
}
