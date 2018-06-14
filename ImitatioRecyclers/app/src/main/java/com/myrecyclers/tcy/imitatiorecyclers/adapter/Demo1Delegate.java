package com.myrecyclers.tcy.imitatiorecyclers.adapter;

import android.widget.TextView;


import com.myrecyclers.tcy.imitatiolibrary.adapter.BaseDelegate;
import com.myrecyclers.tcy.imitatiolibrary.adapter.BaseViewHolder;
import com.myrecyclers.tcy.imitatiorecyclers.R;
import com.myrecyclers.tcy.imitatiorecyclers.bean.Demo1;

import java.util.List;

/**
 * Created by tcy on 2018/6/11.
 */

public class Demo1Delegate extends BaseDelegate<Demo1> {

    public Demo1Delegate() {
        super(R.layout.header, R.layout.item);
    }
    @Override
    public void initHeaderView(BaseViewHolder holder) {
        TextView header = holder.findViewById(R.id.header);
        header.setText("我是header");
        super.initHeaderView(holder);
    }
    @Override
    public void initCustomView(BaseViewHolder holder, List<Demo1> data, int position) {
        TextView str =  holder.findViewById(R.id.str);
        str.setText(data.get(position).getName());
        super.initCustomView(holder, data, position);
    }
}
