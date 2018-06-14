package com.myrecyclers.tcy.imitatiolibrary.expandable.adapter;

/**
 * 设置布局文件  绑定view 以及响应事件
 */
public interface ExpadableDelegate<T> {

    /**
     * 通过ViewType获取布局文件
     *
     * @param viewType 类型
     * @return layout id
     */
     int getLayoutId(int viewType);


    /**
     * 设置view 响应事件
     *
     * @param holder   viewHolder
     * @param obj      参数
     * @param position 位置
     */
     void initInterfaceGroupView(ExpandableBaseViewHolder holder,
                                 ExpandableBaseRecyclerAdapter.GroupModel groupModel,
                                 Object obj, int position);


     void initInterfaceChildView(ExpandableBaseViewHolder holder, Object groupObj, Object obj, int position);

     void initInterfaceHeaderView(ExpandableBaseViewHolder holder);
     void initInterfaceFooterView(ExpandableBaseViewHolder holder, int position);

}
