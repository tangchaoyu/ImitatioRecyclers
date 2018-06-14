package com.myrecyclers.tcy.imitatiolibrary;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import com.myrecyclers.tcy.imitatiolibrary.expandable.adapter.ExpandableBaseDelegate;
import com.myrecyclers.tcy.imitatiolibrary.expandable.adapter.ExpandablePullRefreshAdapter;


/**
 * Created by tcy on 2018/6/6.
 */

public class ExpandablePullRecyclerView extends MyRecyclerView {

    // 加载更多的回调
    private OnAddMoreListener addMoreListener;
    private boolean isInTheBottom = false;
    private ExpandablePullRefreshAdapter myAdapter;
    private View StickyheaderView;
    private TextView StickyheaderText;
    private View headerView;
    private ExpandableBaseDelegate delegate;
    private int mCurrentPosition = 0;
    private int mSuspensionHeight;
    private int mheaderHeight;


    public ExpandablePullRecyclerView(Context context) {
        super(context);
    }

    public ExpandablePullRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ExpandablePullRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }


    public void setStickyheaderView(View stickyheaderView) {
        StickyheaderView = stickyheaderView;
    }

    public void setStickyheaderText(TextView stickyheaderText) {
        StickyheaderText = stickyheaderText;
    }

    public void setHeaderView(View headerView) {
        this.headerView = headerView;
    }

    @Override
    public void setAdapter(RecyclerView.Adapter adapter) {
        super.setAdapter(adapter);
        if (adapter instanceof ExpandablePullRefreshAdapter) {
            this.myAdapter = (ExpandablePullRefreshAdapter) adapter;
        }
        resetItem();
    }


    public void setBaseDelegate(ExpandableBaseDelegate delegate){
        this.delegate = delegate;
    }

    //GridLayoutManager 时, 设置底部或者头部有布局时 充满整个屏幕
    public void resetItem() {
        if (myAdapter == null) {
            return;
        }
        RecyclerView.LayoutManager layoutManager = getLayoutManager();
        if (layoutManager == null) { //it maybe unnecessary
            throw new RuntimeException("LayoutManager is null,Please check it!");
        }

        if (layoutManager instanceof GridLayoutManager) {
            final GridLayoutManager lm = (GridLayoutManager) layoutManager;
            lm.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    return myAdapter.isHeaderItem(position) ? lm.getSpanCount() : (myAdapter.isFooterItem(position) ? lm.getSpanCount() : 1);
                }
            });
        }
    }

    /**
     * reachBottomRow = 1;(default)
     * mean : when the lastVisibleRow is lastRow , call the onReachBottom();
     * reachBottomRow = 2;
     * mean : when the lastVisibleRow is Penultimate Row , call the onReachBottom();
     * And so on
     */
    private int reachBottomRow = 2;

    @Override
    public void onScrollStateChanged(int state) {
        super.onScrollStateChanged(state);
        if(StickyheaderView!=null){
            mSuspensionHeight = StickyheaderView.getHeight();
        }
        if(headerView!=null){
            mheaderHeight = headerView.getHeight();
        }
    }


    @Override
    public void onScrolled(int dx, int dy) {
        super.onScrolled(dx, dy);
        RecyclerView.LayoutManager linearLayoutManager =   getLayoutManager();

        if (StickyheaderView!=null&&linearLayoutManager instanceof LinearLayoutManager) {

            if(myAdapter.getItemViewType(mCurrentPosition + 1)
                    ==ExpandablePullRefreshAdapter.RECYCLE_TYPE_ITEM_GROUP){

                View stickyInfoView = linearLayoutManager.findViewByPosition(mCurrentPosition+1);
                if (stickyInfoView != null && stickyInfoView.getContentDescription() != null){
                     if(headerView==null){
                         if (stickyInfoView.getTop() <= mSuspensionHeight) {
                             StickyheaderView.setY(-(mSuspensionHeight - stickyInfoView.getTop()));
                         } else {
                             StickyheaderView.setY(0);
                         }
                     }else {
                            if(mCurrentPosition+1>=2){
                                if (stickyInfoView.getTop() <= mSuspensionHeight) {
                                    StickyheaderView.setY(-(mSuspensionHeight - stickyInfoView.getTop()));
                                } else {
                                    StickyheaderView.setY(0);

                                }
                            }else if(mCurrentPosition+1>=1){
                                if (stickyInfoView.getTop() <= mSuspensionHeight) {
                                } else {
                                    StickyheaderView.setY(0+mheaderHeight);
                                }
                            }
                     }
                }
            }

            if (mCurrentPosition != ((LinearLayoutManager) linearLayoutManager).findFirstVisibleItemPosition()) {
                mCurrentPosition = ((LinearLayoutManager) linearLayoutManager).findFirstVisibleItemPosition();
                if(headerView!=null){
                    if(mCurrentPosition==0){
                        StickyheaderView.setY(0+mheaderHeight);
                        if(StickyheaderView!=null){
                            StickyheaderView.setVisibility(GONE);
                        }
                    }else {
                        StickyheaderView.setY(0);
                        if(StickyheaderView!=null){
                            StickyheaderView.setVisibility(VISIBLE);
                        }
                    }

                }else {
                    StickyheaderView.setY(0);
                    if(StickyheaderView!=null){
                        StickyheaderView.setVisibility(VISIBLE);
                    }
                }

                View stickyInfoView = linearLayoutManager.findViewByPosition(mCurrentPosition);
                StickyheaderText.setText(String.valueOf(stickyInfoView.getContentDescription()));
            }

        }


        if (addMoreListener != null) {
            RecyclerView.LayoutManager layoutManager = getLayoutManager();
            if (layoutManager == null) { //it maybe unnecessary
                throw new RuntimeException("LayoutManager is null,Please check it!");
            }
            RecyclerView.Adapter adapter = getAdapter();
            if (adapter == null) { //it maybe unnecessary
                throw new RuntimeException("Adapter is null,Please check it!");
            }
            boolean isReachBottom = false;
            //is GridLayoutManager
            if (layoutManager instanceof GridLayoutManager) {
                GridLayoutManager gridLayoutManager = (GridLayoutManager) layoutManager;
                int rowCount = adapter.getItemCount() / gridLayoutManager.getSpanCount();
                int lastVisibleRowPosition = gridLayoutManager.findLastVisibleItemPosition() / gridLayoutManager.getSpanCount();
                isReachBottom = (lastVisibleRowPosition >= rowCount - reachBottomRow);
                //这个判断是为了兼容数据不满一屏幕时
                if(isReachBottom){
                    if(gridLayoutManager.findLastVisibleItemPosition()==adapter.getItemCount()-1){
                        isInTheBottom= false;
                    }
                }

            }
            //is LinearLayoutManager
            else if (layoutManager instanceof LinearLayoutManager) {
                int lastVisibleItemPosition = ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();
                int rowCount = adapter.getItemCount();
                if (reachBottomRow > rowCount)
                    reachBottomRow = 1;
                isReachBottom = (lastVisibleItemPosition >= rowCount - reachBottomRow);
            }
            //is StaggeredGridLayoutManager
            else if (layoutManager instanceof StaggeredGridLayoutManager) {
                StaggeredGridLayoutManager staggeredGridLayoutManager = (StaggeredGridLayoutManager) layoutManager;
                int spanCount = staggeredGridLayoutManager.getSpanCount();
                int[] into = new int[spanCount];
                int[] eachSpanListVisibleItemPosition = staggeredGridLayoutManager.findLastVisibleItemPositions(into);
                for (int i = 0; i < spanCount; i++) {
                    if (eachSpanListVisibleItemPosition[i] > adapter.getItemCount() - reachBottomRow * spanCount) {
                        isReachBottom = true;
                        break;
                    }
                }
                //这个判断是为了兼容数据不满一屏幕时
                if(isReachBottom){
                    if(eachSpanListVisibleItemPosition[into.length-1]==adapter.getItemCount()-1){
                        isInTheBottom= false;
                    }
                }
            }

            if (!isReachBottom) {
                isInTheBottom = false;
            } else if (!isInTheBottom) {
                isInTheBottom = true;
                // 正在加载或者刷新的 不执行
                if (myAdapter != null && myAdapter.isBeginLoading()) {
                    myAdapter.setLoading(true);
                    myAdapter.getSwipeRefresh().setEnabled(false);
                    addMoreListener.addMoreListener();
                }
            }
        }
    }
    // get set 方法
    public void setOnAddMoreListener(OnAddMoreListener addMoreListener) {
        this.addMoreListener = addMoreListener;
    }


    public interface OnAddMoreListener {
        void addMoreListener();
    }
}
