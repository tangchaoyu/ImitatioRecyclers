package com.myrecyclers.tcy.imitatiolibrary.adapter;

import java.util.List;

/**
 * 设置布局文件  绑定view 以及响应事件
 */
public interface Delegate<T> {

    /**
     * 通过ViewType获取布局文件
     *
     * @param viewType 类型
     * @return layout id
     */
    public int getLayoutId(int viewType);

    /**
     * 设置view 响应事件
     *
     * @param holder   viewHolder
     * @param obj      参数
     * @param position 位置
     */
    public void initView(BaseViewHolder holder, List<T> obj, int position, int viewType);


}
