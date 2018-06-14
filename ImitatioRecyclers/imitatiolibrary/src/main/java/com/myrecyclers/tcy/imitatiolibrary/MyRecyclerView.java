package com.myrecyclers.tcy.imitatiolibrary;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;
import android.util.Log;

import com.myrecyclers.tcy.imitatiolibrary.decoration.DividerItemDecoration;
import com.myrecyclers.tcy.imitatiolibrary.layoutmanager.MyGridViewLayoutManager;
import com.myrecyclers.tcy.imitatiolibrary.layoutmanager.MyLinearLayoutManager;
import com.myrecyclers.tcy.imitatiolibrary.layoutmanager.MyStaggeredGridLayoutManager;


public class MyRecyclerView extends RecyclerView {


    public static final String TAG = MyRecyclerView.class.getSimpleName();
    private Context mContext;
    private int layoutManagerType;
    private int spanceCount;
    private int dividerColor;
    private int dividerDrawable;
    //都是像素单位
    private int dividerWidth;
    private int dividerHeight;


    public MyRecyclerView(Context context) {
        super(context);
        initView(context, null);
    }

    public MyRecyclerView(Context context, @Nullable AttributeSet attrs)
    {
        super(context, attrs);
        initView(context, attrs);
    }

    public MyRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView(context, attrs);
    }

    void initView(Context context, AttributeSet attrs) {
        this.mContext = context;
        //解析初始化数据
        TypedArray attr = context.obtainStyledAttributes(attrs, R.styleable.MyRecyclerView, 0, 0);
        layoutManagerType = attr.getInt(R.styleable.MyRecyclerView_layoutManagerType, 0);
        spanceCount = attr.getInteger(R.styleable.MyRecyclerView_spanCount, 2);

        dividerHeight = attr.getInteger(R.styleable.MyRecyclerView_divider_decoration_height, 2);
        dividerWidth = attr.getInteger(R.styleable.MyRecyclerView_divider_decoration_width, 0);
        dividerColor = attr.getInteger(R.styleable.MyRecyclerView_divider_decoration_color, ContextCompat.getColor(context, R.color.line_d));
        dividerDrawable = attr.getInteger(R.styleable.MyRecyclerView_divider_decoration_drawable, 0);
        attr.recycle();

        // setLayoutManager 设置显示格式 线性显示 默认垂直 其它构造可设置水平  boolean意思 ：否倒叙
        // addItemDecoration 设置divider v7中有自带的  扩展成可自定义的
        if (layoutManagerType == 0) {
            setLayoutManager(new MyLinearLayoutManager(this.mContext));
            if (dividerDrawable == 0) {
                addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL_LIST, dividerHeight, dividerWidth, new ColorDrawable(dividerColor)));

            } else {
                addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL_LIST, dividerHeight, dividerWidth, ContextCompat.getDrawable(context, dividerDrawable)));

            }
        } else if (layoutManagerType == 1) {
            setLayoutManager(new MyLinearLayoutManager(this.mContext, LinearLayoutManager.HORIZONTAL, false));
            if (dividerDrawable == 0) {
                addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.HORIZONTAL_LIST, dividerHeight, dividerWidth, new ColorDrawable(dividerColor)));

            } else {
                addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.HORIZONTAL_LIST, dividerHeight, dividerWidth, ContextCompat.getDrawable(context, dividerDrawable)));
            }
        } else if (layoutManagerType == 2) {
            setLayoutManager(new MyGridViewLayoutManager(this.mContext, spanceCount));
//            addItemDecoration(new DividerGridItemDecoration(this.mContext, this.dividerWidth));
        } else if (layoutManagerType == 3) {
            setLayoutManager(new MyStaggeredGridLayoutManager(spanceCount, StaggeredGridLayoutManager.VERTICAL));
//            addItemDecoration(new DividerGridItemDecoration(this.mContext, this.dividerWidth));
        } else {
            Log.d(TAG, "layoutManagerType only has four type , layoutManagerType is now");
        }

        // 设置动画
        setItemAnimator(new DefaultItemAnimator());

    }

}
