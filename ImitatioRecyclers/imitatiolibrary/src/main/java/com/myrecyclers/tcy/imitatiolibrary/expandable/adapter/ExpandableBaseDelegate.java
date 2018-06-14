package com.myrecyclers.tcy.imitatiolibrary.expandable.adapter;

import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.ViewGroup;

import com.myrecyclers.tcy.imitatiolibrary.R;


/**
 * Created by tcy on 2018/6/5.
 */

public abstract class ExpandableBaseDelegate<T> implements ExpadableDelegate<T> {


    private int headerLayoutId;
    private int footerLayoutId;
    private int groupLayoutId;
    private int childLayoutId;
    private ExpandableBaseRecyclerAdapter adapter;

    public ExpandableBaseDelegate(int groupLayoutId,int childLayoutId) {
        this(1, groupLayoutId,childLayoutId);
    }

    public ExpandableBaseDelegate(int headerLayoutId, int groupLayoutId,int childLayoutId) {
        this.headerLayoutId = headerLayoutId;
        this.groupLayoutId = groupLayoutId;
        this.childLayoutId = childLayoutId;
        this.footerLayoutId = R.layout.item_footer;

    }

    @Override
    public int getLayoutId(int viewType) {
        //通过adapter 进行判断返回的是头尾还是普通的
        if (viewType == ExpandableBaseRecyclerAdapter.RECYCLE_TYPE_HEADER) {
            return getHeaderLayoutId(viewType);
        } else if (viewType == ExpandableBaseRecyclerAdapter.RECYCLE_TYPE_FOOTER) {
            return getFooterLayoutId(viewType);
        } else if(viewType == ExpandableBaseRecyclerAdapter.RECYCLE_TYPE_ITEM_GROUP){
            return  getGroupLayoutId(viewType);
        } else {
            return getChildLayoutId(viewType);
        }
    }


    /**
     * 获取组的数目
     * @return
     */
    public abstract int getGroupCount();

    /**
     * 获取特定组的数目
     * @param groupPosition 组的索引
     * @return
     */
    public abstract int getChildCount(int groupPosition);


    /**
     * 获取特定组Group的数据
     * @param groupPosition 组的索引
     * @return
     */
    public abstract Object getGroup(int groupPosition);

    /**
     * 获取特定组特定子项的数目
     * @param groupPosition
     * @param childPosition
     * @return
     */
    public abstract Object getChild(int groupPosition, int childPosition);


    /**
     * 获取头部布局 如需可修改其访问权限
     *
     * @param viewType adapter返回的界面类型
     * @return 头部布局id
     */
    private int getHeaderLayoutId(int viewType) {
        return this.headerLayoutId;
    }

    /**
     * 获取底部布局 如需可修改其访问权限
     *
     * @param viewType adapter返回的界面类型
     * @return 底部布局id
     */
    private int getFooterLayoutId(int viewType) {
        return this.footerLayoutId;
    }

    /**
     * @return
     * 获取item group布局
     */
    private int getGroupLayoutId(int viewType){
         return  this.groupLayoutId;
    }

    /**
     * @return
     * 获取item child布局
     */
    private int getChildLayoutId(int viewType){
        return  this.childLayoutId;
    }


    @Override
    public void initInterfaceGroupView(ExpandableBaseViewHolder holder, ExpandableBaseRecyclerAdapter.GroupModel groupModel, Object obj, int position) {
           initGroupView(holder,groupModel,obj,position);
    }

    @Override
    public void initInterfaceChildView(ExpandableBaseViewHolder holder, Object groupObj, Object obj, int position) {
            initChildView(holder,groupObj,obj,position);
    }

    @Override
    public void initInterfaceHeaderView(ExpandableBaseViewHolder holder) {
            initHeaderView(holder);
    }

    @Override
    public void initInterfaceFooterView(ExpandableBaseViewHolder holder, int position) {
          initFooterView(holder,position);
    }

    /**
     * 初始化头部布局
     *
     * @param holder BaseViewHolder
     * @param
     */
    public void initHeaderView(ExpandableBaseViewHolder holder) {
    }

    /**
     * 初始化低部布局
     *
     * @param holder   BaseViewHolder
     * @param position 具体的position 包含头部 尾部
     */
    public void initFooterView(ExpandableBaseViewHolder holder, int position) {
        ViewGroup.LayoutParams layoutParams = holder.itemView.getLayoutParams();
        if (layoutParams instanceof StaggeredGridLayoutManager.LayoutParams) {
            ((StaggeredGridLayoutManager.LayoutParams) layoutParams).setFullSpan(true);
        }
    }

    /**
     * 初始化group
     * @param holder
     * @param position
     */
    public void initGroupView(ExpandableBaseViewHolder holder, ExpandableBaseRecyclerAdapter.GroupModel groupModel, Object groupObject, int position){

    }

    /**
     * 初始化child
     * @param holder
     * @param position
     */
    public void initChildView(ExpandableBaseViewHolder holder, Object groupObject, Object childObject, int position){

    }

    public void setAdapter(ExpandableBaseRecyclerAdapter adapter) {
        this.adapter = adapter;
    }

    public ExpandableBaseRecyclerAdapter getAdapter() {
        return adapter;
    }

    public void notifyDataSetChanged() {
        if (adapter == null) {
        } else {
            adapter.notifyDataSetChanged();
        }
    }

}
