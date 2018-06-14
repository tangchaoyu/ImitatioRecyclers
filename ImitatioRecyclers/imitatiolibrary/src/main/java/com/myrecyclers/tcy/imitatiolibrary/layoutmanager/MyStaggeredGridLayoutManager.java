package com.myrecyclers.tcy.imitatiolibrary.layoutmanager;

import android.content.Context;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;

public class MyStaggeredGridLayoutManager extends StaggeredGridLayoutManager
{
    public MyStaggeredGridLayoutManager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes)
    {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public MyStaggeredGridLayoutManager(int spanCount, int orientation)
    {
        super(spanCount, orientation);
        //Pixel distance must be non-negative 解决这个bug 25以后
        setItemPrefetchEnabled(false);
    }
//    collectAdjacentPrefetchPositions

    @Override
    public void collectInitialPrefetchPositions(int adapterItemCount, LayoutPrefetchRegistry layoutPrefetchRegistry)
    {
        try {
            super.collectInitialPrefetchPositions(adapterItemCount, layoutPrefetchRegistry);
        } catch (IllegalArgumentException e) {
        }
    }
}
