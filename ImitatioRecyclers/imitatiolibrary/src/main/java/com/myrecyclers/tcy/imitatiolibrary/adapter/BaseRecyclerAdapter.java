package com.myrecyclers.tcy.imitatiolibrary.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class BaseRecyclerAdapter<T> extends RecyclerView.Adapter<BaseViewHolder> {

    public static final int RECYCLE_TYPE_ITEM = 1000;
    static final int RECYCLE_TYPE_HEADER = 1001;
    static final int RECYCLE_TYPE_FOOTER = 1002;

    protected Context mContext;
    protected List<T> mData;

    private OnItemClickListener onItemClickListener;
    private setOnItemLongClickListener onItemLongClickListener;

    /**
     * 头部数量 默认没有 涉及头部现阶段需要添加一个即可
     */
    protected int headerCount = 0;
    /**
     * 底部数量 默认没有 涉及底部现阶段需要添加一个即可
     */
    protected int footerCount = 0;
    /**
     * 设置显示数量 默认-1 显示全部
     */
    protected int showCount = -1;

    /**
     * 设置deleate
     */
    private BaseDelegate<T> baseDelegate;


    private int selectPosition = 0;

    public BaseRecyclerAdapter(Context mContext, List<T> mData, BaseDelegate baseDelegate) {
        this.mContext = mContext;
        this.mData = mData;
        this.baseDelegate = baseDelegate;
        this.baseDelegate.setAdapter(this);

    }

    /**
     * 添加新的数据
     *
     * @param newData 新数据
     */
    public void addData(List<T> newData) {
        if (this.mData != null && newData != null && newData.size() != 0) {
            mData.addAll(newData);
            notifyDataSetChanged();
            //出入数据 会有动画效果
//            notifyItemRangeInserted(this.mData.size() - newData.size(), newData.size());
        }
    }

    /**
     * 只需要添加一条数据
     *
     * @param bean 新数据
     */
    public void addData(T bean) {
        if (this.mData != null && bean != null) {
            mData.add(bean);
            notifyItemInserted(mData.size() - 1);
            notifyDataSetChanged();

            //           notifyItemInserted(this.mData.size() - 1);
//            notifyItemRangeChanged(this.mData.size() - 1, this.mData.size());
        }
    }

    public void addData(int position, T bean) {
        if (this.mData != null && bean != null) {
            mData.add(position, bean);
//            notifyDataSetChanged();
            notifyItemInserted(position);
//            notifyItemRangeChanged(this.mData.size() - 1, this.mData.size());
        }
    }


    /**
     * 重置数据
     *
     * @param newData 新数据
     */
    public void resetData(List<T> newData) {
        if (this.mData != null && newData != null) {
            this.mData.clear();
            this.mData.addAll(newData);
//            notifyDataSetChan();
//            notifyItemRangeInserted(0,mData.size());
            notifyDataSetChanged();
         }
    }

    /**
     * 返回列表数据
     */
    public List<T> getData() {
        if (mData == null) {
            return new ArrayList<T>();
        }
        return mData;
    }


    /**
     * 清空数据
     */
    public void clear() {
        if (this.mData != null) {
            mData.clear();
            notifyDataSetChanged();
        }
    }

    /**
     * 删除某个对象
     *
     * @param position 位置
     */
    public void deletItem(int position) {
        if (this.mData != null && this.mData.size() > position) {
            mData.remove(position);
            notifyItemRemoved(position);
//            notifyItemRangeChanged(position, mData.size() - position);
        }
    }

    /**
     * 设置头部数量
     *
     * @param headerCount 数量
     */
    public void setHeaderCount(int headerCount) {
        this.headerCount = headerCount;
    }

    /**
     * 设置底部数量
     *
     * @param footerCount 数量
     */
    public void setFooterCount(int footerCount) {
        this.footerCount = footerCount;
    }

    /**
     * 设置显示数量 不包含头部和底部
     *
     * @param showCount 显示数量
     */
    public void setShowCount(int showCount) {
        this.showCount = showCount;
    }

    public int getShowCount() {
        return mData == null ? 0 : showCount != -1 ? Math.min(mData.size(), showCount) : mData.size();
    }


    /**
     * 返回数据内容+头部和底部
     *
     * @return
     */
    @Override
    public int getItemCount() {
        int count = mData == null ? 0 : showCount != -1 ? Math.min(mData.size(), showCount) : mData.size();
        return count + footerCount + headerCount;
    }

    /**
     * 是否属于头部
     *
     * @param position 位置
     */
    public Boolean isHeaderItem(int position) {
        return position < headerCount;
    }

    /**
     * 是否属于底部
     *
     * @param position 位置
     */
    public Boolean isFooterItem(int position) {
        return position >= getItemCount() - footerCount;
    }

    /**
     * 点击相应事件
     *
     * @param onItemClickListener
     */
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
 /**
     * 点击相应事件
     *
     * @param  onItemLongClickListener
     */
    public void setOnItemLongClickListener(setOnItemLongClickListener onItemLongClickListener) {
        this.onItemLongClickListener = onItemLongClickListener;
    }

    public int getSelectPosition() {
        return selectPosition;
    }

    public void setSelectPosition(int selectPosition) {
        this.selectPosition = selectPosition;
    }

    public T getSelectItem() {
        if (mData.size() > this.selectPosition) {
            return mData.get(this.getSelectPosition());
        }

        return null;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return BaseViewHolder.creatViewHolder(mContext, parent, this.baseDelegate.getLayoutId(viewType));
    }

    @Override
    public void onBindViewHolder(final BaseViewHolder holder, final int position) {

        this.baseDelegate.initView(holder, mData, position - headerCount, getItemViewType(position));

        if (onItemClickListener != null) {
            holder.getConvertView().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onItemClick(holder.getConvertView(), position - headerCount);
                }
            });
        }
        if (onItemLongClickListener != null) {
            holder.getConvertView().setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    onItemLongClickListener.onItemLongClick(holder.getConvertView(), position - headerCount);
                    return false;
                }
            });
        }

    }

    @Override
    public int getItemViewType(int position) {
        if (isHeaderItem(position)) {
            return RECYCLE_TYPE_HEADER;

        } else if (isFooterItem(position)) {
            return RECYCLE_TYPE_FOOTER;

        } else {
            return getCommonViewType(position - headerCount);
        }
    }

    @Override
    public void onViewAttachedToWindow(BaseViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        ViewGroup.LayoutParams lp = holder.itemView.getLayoutParams();
        if(lp != null && lp instanceof StaggeredGridLayoutManager.LayoutParams) {
            StaggeredGridLayoutManager.LayoutParams p = (StaggeredGridLayoutManager.LayoutParams) lp;
            if(isHeaderItem(holder.getLayoutPosition())||isFooterItem(holder.getLayoutPosition())){
                p.setFullSpan(true);
            }

        }

    }

    /**
     * @param position 只是数据的position 不包含header footer
     */
    protected int getCommonViewType(int position) {

        return RECYCLE_TYPE_ITEM;
    }


    public interface OnItemClickListener {
        public void onItemClick(View view, int position);
    }
    public interface setOnItemLongClickListener {
        public void onItemLongClick(View view, int position);
    }


}
