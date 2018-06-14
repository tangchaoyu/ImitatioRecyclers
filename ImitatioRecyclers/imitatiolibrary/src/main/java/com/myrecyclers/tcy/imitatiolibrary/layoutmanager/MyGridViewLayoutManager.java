package com.myrecyclers.tcy.imitatiolibrary.layoutmanager;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;

/**
 * Created by MaShiZhao on 2017/1/10
 */
public class MyGridViewLayoutManager extends GridLayoutManager
{
    public MyGridViewLayoutManager(Context context, int spanCount)
    {
        //默认方向是VERTICAL
        super(context, spanCount);
    }

    public MyGridViewLayoutManager(Context context, int spanCount, int orientation, boolean reverseLayout)
    {
        super(context, spanCount, orientation, reverseLayout);
    }
//
//    @Override
//    public void onMeasure(RecyclerView.Recycler recycler, RecyclerView.State state, int widthSpec, int heightSpec)
//    {
//        int height = 0;
//        int childCount = getItemCount();
//        for (int i = 0; i < childCount; i++)
//        {
//            View child = recycler.getViewForPosition(i);
//            measureChild(child, widthSpec, heightSpec);
//            if (i % getSpanCount() == 0)
//            {
//                int measuredHeight = child.getMeasuredHeight() + getDecoratedBottom(child);
//                height += measuredHeight;
//            }
//        }
//        setMeasuredDimension(View.MeasureSpec.getSize(widthSpec), height);
//    }
}
