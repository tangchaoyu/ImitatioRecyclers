package com.myrecyclers.tcy.imitatiolibrary.expandable.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by tcy on 2018/6/5.
 */
public class ExpandableBaseViewHolder extends RecyclerView.ViewHolder {
    private View mView;

    public ExpandableBaseViewHolder(View itemView) {
        super(itemView);
        this.mView = itemView;
    }

    public static ExpandableBaseViewHolder creatViewHolder(Context context, ViewGroup parent, int layoutId) {
        View view = LayoutInflater.from(context).inflate(layoutId, parent, false);
        return new ExpandableBaseViewHolder(view);
    }

    /**
     * 通过viewId获取控件
     *
     * @param viewId
     * @return
     */
    public <T extends View> T findViewById(int viewId) {
        View view = null;
        if (mView != null) {
            view = mView.findViewById(viewId);
        }
        return (T) view;
    }

    public View getConvertView() {
        return mView;
    }
}
