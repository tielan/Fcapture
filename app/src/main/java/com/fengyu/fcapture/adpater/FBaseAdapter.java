package com.fengyu.fcapture.adpater;

import android.content.Context;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

/**

 */
public abstract class FBaseAdapter<T> extends BaseAdapter {

    protected List<T> mData = new ArrayList<>();

    protected Context mContext;

    public FBaseAdapter(Context context) {
        this.mContext = context;
    }

    @Override
    public int getCount() {
        return mData == null ? 0 : mData.size();
    }

    @Override
    public T getItem(int position) {
        return mData.size() > position ? mData.get(position) : null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void notifyAdapter(List<T> data) {
        this.mData = data;
        notifyDataSetChanged();
    }

    public void setData(List<T> data) {
        this.mData = data;
    }

}
