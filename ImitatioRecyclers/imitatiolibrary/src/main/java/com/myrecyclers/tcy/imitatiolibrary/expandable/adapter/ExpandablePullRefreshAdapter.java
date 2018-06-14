package com.myrecyclers.tcy.imitatiolibrary.expandable.adapter;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;

import java.util.List;


/**
 * Created by tcy on 2018/6/5.
 */

public  class ExpandablePullRefreshAdapter<T> extends ExpandableBaseRecyclerAdapter<T> {

    //当前页数
    private int pageIndex;
    //总页数
    private int totalPage;
    private static final int DEFAULT_PAGE = 0;
    private SwipeRefreshLayout mySwipeRefresh;

    //是否加载更多种
    private Boolean isLoading;

    /**
     * 没有头部 调用此方法
     */
    public ExpandablePullRefreshAdapter(Context mContext, List<T> mData, ExpandableBaseDelegate baseDelegate) {
        this(mContext, mData, 0, baseDelegate);
    }

    /**
     * 存在头部时调用此方法
     * @param context
     * @param headerCount
     * @param baseDelegate
     */
    public ExpandablePullRefreshAdapter(Context context, List<T> mData, int headerCount, ExpandableBaseDelegate baseDelegate) {
        super(context, mData, baseDelegate);
        //footerCount设置0  是防止第一次进来就显示
        this.footerCount = 0;
        this.headerCount = headerCount;
        this.pageIndex = DEFAULT_PAGE;
        this.totalPage = DEFAULT_PAGE;
        this.isLoading = false;
    }


    public int getPageIndex() {
        return pageIndex;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public ExpandablePullRefreshAdapter setTotalPage(int totalPage) {
        this.totalPage = totalPage;
        return this;
    }

    public Boolean getLoading() {
        return isLoading;
    }

    public void setLoading(Boolean loading) {
        isLoading = loading;
    }

    //摄者swiperefresh 用于刷新判断
    public SwipeRefreshLayout getSwipeRefresh() {
        return mySwipeRefresh;
    }

    public void setSwipeRefresh(SwipeRefreshLayout mySwipeRefresh) {
        this.mySwipeRefresh = mySwipeRefresh;
    }

    //刷新时调用
    public void resetPageIndex() {
        this.pageIndex = DEFAULT_PAGE;
    }
    //加载时调用
    public void addPageIndex() {
        this.pageIndex++;
    }

    //判断是否可以加载更多 刷新和加载更多时,已经是最后一页时不可以
    public Boolean isBeginLoading() {
        if (mySwipeRefresh == null) {
            return false;
        } else if (mySwipeRefresh.isRefreshing() || isLoading) {
            return false;
        } else if (pageIndex >= totalPage) {
            return false;
        }
        return true;
    }

    /**
     * 接收数据源 根据页面进行设置
     *
     * @param data 新数据源
     */
    public void setPullData(List<T> data) {
        // TODO: 2017/3/4
        // 当前页面小于总页数 则需要显示footer 否则就不显示了
        // 需要考虑 就一页并且当前数据不足以覆盖手机屏幕
        if (pageIndex < totalPage) {
            setFooterCount(1);
        } else {
            setFooterCount(0);
        }
        //是否是第一页的数据
        if (DEFAULT_PAGE == pageIndex) {
            resetData(data);
            if (mySwipeRefresh != null) {
                mySwipeRefresh.setRefreshing(false);
            }
        } else {
            addData(data);
            isLoading = false;
            mySwipeRefresh.setEnabled(true);
        }
    }
}
