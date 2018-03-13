package com.pastir.adapter;

import android.text.TextUtils;

import com.pastir.model.Book;
import com.pastir.model.OnListItemClickListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Adapter used for showing and filtering books
 */

public class BookSearchAdapter extends BaseAdapter {

    private List<Book> mList;
    private List<Book> mListCopy;
    private OnListItemClickListener mListener;
    private int mLayoutId;

    public BookSearchAdapter(List<Book> list, OnListItemClickListener listener, int layoutId) {
        mLayoutId = layoutId;
        mList = new ArrayList<>();
        mListCopy = list;
        mList.addAll(mListCopy);
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

    public void filter(String text) {
        mList.clear();
        if (TextUtils.isEmpty(text)) {
            mList.addAll(mListCopy);
        } else {
            text = text.toLowerCase();
            for(Book item:mListCopy) {
                String searchableText = item.getName();
                if (searchableText.toLowerCase().contains(text)) {
                    mList.add(item);
                }
            }
        }
        notifyDataSetChanged();
    }
}
