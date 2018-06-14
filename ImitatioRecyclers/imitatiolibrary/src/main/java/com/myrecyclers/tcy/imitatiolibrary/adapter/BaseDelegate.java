package com.myrecyclers.tcy.imitatiolibrary.adapter;



import com.myrecyclers.tcy.imitatiolibrary.R;

import java.util.List;


public class BaseDelegate<T> implements Delegate<T> {
    private int headerLayoutId;
    private int footerLayoutId;
    private int customLayoutId;
    private BaseRecyclerAdapter adapter;


    public BaseDelegate(int customLayoutId) {
        this(1, customLayoutId);
    }

    /**
     * 构造
     *
     * @param headerLayoutId 头布局的文件id
     * @param customLayoutId 普通布局的文件id
     */

    public BaseDelegate(int headerLayoutId, int customLayoutId) {
        this.headerLayoutId = headerLayoutId;
        this.customLayoutId = customLayoutId;
        this.footerLayoutId = R.layout.item_footer;

    }

    /**
     * 获取所有item的布局包含头部，尾部，普通的
     *
     * @param viewType 类型
     * @return 布局 id
     */
    @Override
    public int getLayoutId(int viewType) {
        //通过adapter 进行判断返回的是头尾还是普通的
        if (viewType == BaseRecyclerAdapter.RECYCLE_TYPE_HEADER) {
            return getHeaderLayoutId(viewType);

        } else if (viewType == BaseRecyclerAdapter.RECYCLE_TYPE_FOOTER) {
            return getFooterLayoutId(viewType);

        } else {
            return getCommonLayoutId(viewType);
        }
    }

    /**
     * 获取头部布局 如需可修改其访问权限
     *
     * @param viewType adapter返回的界面类型
     * @return 头部布局id
     */
    private int getHeaderLayoutId(int viewType)
    {
        return this.headerLayoutId;
    }

    /**
     * 获取底部布局 如需可修改其访问权限
     *
     * @param viewType adapter返回的界面类型
     * @return 底部布局id
     */
    private int getFooterLayoutId(int viewType)
    {
        return this.footerLayoutId;
    }

    /**
     * 获取普通布局
     *
     * @param viewType 如需可修改其访问权限
     * @return 普通的布局id
     */
    public int getCommonLayoutId(int viewType)
    {
        return this.customLayoutId;
    }


    @Override
    public void initView(BaseViewHolder holder, List<T> obj, int position, int viewType) {

//        if (position < headerCount)
//        {
//            initHeaderView(holder, position);
//        } else if (position >= dataCount + headerCount)
//        {
//            initFooterView(holder, position);
//        } else
//        {
//            initCustomView(holder, obj, position - headerCount);
//        }

        if (viewType == BaseRecyclerAdapter.RECYCLE_TYPE_HEADER) {
            initHeaderView(holder);
        } else if (viewType == BaseRecyclerAdapter.RECYCLE_TYPE_FOOTER) {
            initFooterView(holder, position);
        } else {
            initCustomView(holder, obj, position);
        }
    }


    /**
     * 初始化头部布局
     *
     * @param holder BaseViewHolder
     * @param
     */
    public void initHeaderView(BaseViewHolder holder) {
    }

    /**
     * 初始化低部布局
     *
     * @param holder   BaseViewHolder
     * @param position 具体的position 包含头部 尾部
     */
    public void initFooterView(BaseViewHolder holder, int position) {
    }

    /**
     * 初始化普通布局
     *
     * @param holder   BaseViewHolder
     * @param position 不包含头部 尾部的位置，只有数据源的position
     */
    public void initCustomView(BaseViewHolder holder, List<T> data, int position) {
    }

    public void setAdapter(BaseRecyclerAdapter adapter) {
        this.adapter = adapter;
    }

    public BaseRecyclerAdapter getAdapter() {
        return adapter;
    }


    public void notifyDataSetChanged() {
        if (adapter == null) {
        } else {
            adapter.notifyDataSetChanged();
        }
    }

}
