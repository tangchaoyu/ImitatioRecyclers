package com.myrecyclers.tcy.imitatiolibrary.decoration;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

public class DividerItemDecoration extends RecyclerView.ItemDecoration
{
    private static final int[] ATTRS = new int[]{
            android.R.attr.listDivider
    };

    public static final int HORIZONTAL_LIST = LinearLayoutManager.HORIZONTAL;

    public static final int VERTICAL_LIST = LinearLayoutManager.VERTICAL;

    private Drawable mDivider;
    private int mDividerHeight = 1;//分割线高度，默认为1px
    private int mDividerWidth = 0;//分割线宽度，默认为0px

    private int mOrientation;
    private Context mContext;

    public DividerItemDecoration(Context context, int orientation)
    {
        this.mContext = context;
        final TypedArray a = context.obtainStyledAttributes(ATTRS);
        mDivider = a.getDrawable(0);
        a.recycle();
        setOrientation(orientation);
    }

    /**
     * 自定义分割线
     *
     * @param context
     * @param orientation 列表方向
     * @param drawable    分割线图片
     */
//    public DividerItemDecoration(Context context, int orientation, Drawable drawable)
//    {
//
//        mDivider = drawable;
//        mDividerHeight = mDivider.getIntrinsicHeight();
//        setOrientation(orientation);
//
//    }

    /**
     * 自定义分割线
     *
     * @param context
     * @param orientation   列表方向
     * @param dividerHeight 分割线高度
     * @param dividerColor  分割线颜色
     */
    public DividerItemDecoration(Context context, int orientation, int dividerHeight, int dividerWidth, Drawable dividerColor)
    {
        this.mContext = context;
        mDividerHeight = dividerHeight;
        mDividerWidth = dividerWidth;
        mDivider = dividerColor;
        setOrientation(orientation);
    }

    public void setOrientation(int orientation)
    {
        if (orientation != HORIZONTAL_LIST && orientation != VERTICAL_LIST)
        {
            throw new IllegalArgumentException("invalid orientation");
        }
        mOrientation = orientation;
    }


    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state)
    {
        if (mOrientation == LinearLayoutManager.VERTICAL)
        {
            drawVertical(c, parent);
        } else
        {
            drawHorizontal(c, parent);
        }
    }

    private void drawVertical(Canvas c, RecyclerView parent)
    {
        final int left = parent.getPaddingLeft();
        final int right = parent.getWidth() - parent.getPaddingRight();
        final int childCount = parent.getChildCount();
        for (int i = 0; i < childCount-1; i++)
        {
            View child = parent.getChildAt(i);
            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
            int top = child.getBottom() + params.bottomMargin;
            int bottom = top + mDividerHeight;
            mDivider.setBounds(left + mDividerWidth, top, right - mDividerWidth, bottom);
            mDivider.draw(c);

        }
    }

    private void drawHorizontal(Canvas c, RecyclerView parent)
    {
        int top = parent.getPaddingTop();
        int bottom = parent.getHeight() - parent.getPaddingBottom();
        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++)
        {
            View child = parent.getChildAt(i);
            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
            int left = child.getRight() + params.rightMargin;
            int right = left + mDividerHeight;
            mDivider.setBounds(left, top + mDividerWidth, right, bottom - mDividerWidth);
            mDivider.draw(c);
        }
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state)
    {
        if (mOrientation == LinearLayout.VERTICAL)
        {
            outRect.set(0, 0, 0, mDividerHeight);
        } else
        {
            outRect.set(0, 0, mDividerHeight, 0);
        }
    }

}
