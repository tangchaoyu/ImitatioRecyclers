package com.myrecyclers.tcy.imitatiolibrary.expandable.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tcy on 2018/6/5.
 */
public  class ExpandableBaseRecyclerAdapter<T> extends RecyclerView.Adapter<ExpandableBaseViewHolder> {

    public static final int RECYCLE_TYPE_ITEM_GROUP = 1003; //item group类型
    public  static final int RECYCLE_TYPE_ITEM_CHILD = 1000; //item child 类型
    public static final int RECYCLE_TYPE_HEADER = 1001; //头部类型
    public  static final int RECYCLE_TYPE_FOOTER = 1002;//底部类型

    protected Context mContext;
    protected List<T> mData;

    //整个Adapter的数据源
    private ArrayList dataList;
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
    private ExpandableBaseDelegate<T> baseDelegate;

    //是否可以点击group
    private boolean isClickGroup = true;

    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
         void onGroupItemClick(View view, int groupPosition);
        void onChildItemClick(View view, int groupPosition, int childPosition);

    }

    /**
     * 点击相应事件
     * @param onItemClickListener
     */
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public ExpandableBaseRecyclerAdapter(Context mContext, List<T> mData, ExpandableBaseDelegate baseDelegate) {
        this.mContext = mContext;
        this.mData = mData;
        this.baseDelegate =  baseDelegate;
        this.baseDelegate.setAdapter(this);
    }

    public  void initData(){
        dataList = new ArrayList();
        for (int i = 0; i < baseDelegate.getGroupCount(); i++) {
           GroupModel groupModel = new GroupModel(true, true, baseDelegate.getGroup(i),i);
            dataList.add(groupModel);
            for (int j = 0; j < baseDelegate.getChildCount(i); j++) {
                groupModel.childList.add(baseDelegate.getChild(i, j));
            }
        }
        mData = dataList;
    }

    /**
     * 添加新的数据
     *
     * @param newData 新数据
     */
    public void addData(List<T> newData) {
        if (this.mData != null && newData != null && newData.size() != 0) {
           ArrayList dataList = new ArrayList();
            for (int i = 0; i < baseDelegate.getGroupCount(); i++) {
               GroupModel groupModel = new GroupModel(true, true, baseDelegate.getGroup(i),mData.size()+i);
                dataList.add(groupModel);
                for (int j = 0; j < baseDelegate.getChildCount(i); j++) {
                    groupModel.childList.add(baseDelegate.getChild(i, j));
                }
            }
            mData.addAll(dataList);
            notifyDataSetChanged();
            //出入数据 会有动画效果
//            notifyItemRangeInserted(this.mData.size() - newData.size(), newData.size());
        }
    }

    /**
     * 重置数据
     * @param newData 新数据
     */
    public void resetData(List<T> newData) {
        if (this.mData != null && newData != null) {
            this.mData.clear();
            ArrayList dataList = new ArrayList();
            for (int i = 0; i < baseDelegate.getGroupCount(); i++) {
                GroupModel groupModel = new GroupModel(true, true,baseDelegate.getGroup(i),i);
                dataList.add(groupModel);
                for (int j = 0; j < baseDelegate.getChildCount(i); j++) {
                    groupModel.childList.add(baseDelegate.getChild(i, j));
                }
            }
            this.mData.addAll(dataList);
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
     * 返回数据内容+头部和底部
     * @return
     */
    @Override
    public int getItemCount() {
//        int count = mData == null ? 0 : showCount != -1 ? Math.min(mData.size(), showCount) : mData.size();
        int size = 0;
        for (int i = 0; i < mData.size(); i++) {
            GroupModel croupModel =(GroupModel)mData.get(i);
            if (croupModel.isShow) {
                size++;
            }
            if (croupModel.isOpen && croupModel.childList != null) {
                size += croupModel.childList.size();
            }
        }
        return size + footerCount + headerCount;
    }

    //列表数据和分组信息
  public class GroupModel {
        boolean isOpen;
        boolean isShow;
        Object mObject;
        int groupPostion;
        List childList;

        public GroupModel(boolean isOpen, boolean isShow, Object object, int groupPostion) {
            this.isOpen = isOpen;
            this.isShow = isShow;
            mObject = object;
            this.groupPostion = groupPostion;
            childList = new ArrayList();
        }

        public boolean isOpen() {
            return isOpen;
        }

        public boolean isShow() {
            return isShow;
        }

        public Object getmObject() {
            return mObject;
        }

        public int getGroupPostion() {
            return groupPostion;
        }

        public List getChildList() {
            return childList;
        }
    }

    @Override
    public ExpandableBaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return ExpandableBaseViewHolder.creatViewHolder(mContext, parent, this.baseDelegate.getLayoutId(viewType));
    }

    @Override
    public void onBindViewHolder(final ExpandableBaseViewHolder holder, final int position) {
        final int viewType = getItemViewType(position);

        if (viewType == ExpandableBaseRecyclerAdapter.RECYCLE_TYPE_HEADER) {
            this.baseDelegate.initInterfaceHeaderView(holder);
        } else if (viewType == ExpandableBaseRecyclerAdapter.RECYCLE_TYPE_FOOTER) {
            this.baseDelegate.initInterfaceFooterView(holder,position - headerCount);
        } else if(viewType == ExpandableBaseRecyclerAdapter.RECYCLE_TYPE_ITEM_GROUP){
             GroupModel groupModel = (GroupModel)getItem(position-headerCount);
            this.baseDelegate.initInterfaceGroupView(holder, groupModel, groupModel.mObject,position - headerCount);
        }else {
            Object childObj = getItem(position-headerCount);
            GroupModel groupConModel = null;
            for (int i = 0; i <mData.size() ; i++) {
                GroupModel groupModel = (GroupModel) mData.get(i);

                for (int j = 0; j <groupModel.childList.size() ; j++) {
                    Object obj =groupModel.childList.get(j);
                    Object obj1 = getItem(position - headerCount);
                    if(obj.equals(obj1)){
                        groupConModel = groupModel;

                    }
                }
            }

            this.baseDelegate.initInterfaceChildView(holder,groupConModel.mObject,childObj, position - headerCount);
        }

        if (onItemClickListener != null) {
            holder.getConvertView().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (viewType == RECYCLE_TYPE_ITEM_GROUP){
                        GroupModel groupModel = (GroupModel) getItem(position - headerCount);
                        if(isClickGroup){
                            groupModel.isOpen = !groupModel.isOpen;
                            notifyDataSetChanged();
                        }
                        onItemClickListener.onGroupItemClick(holder.getConvertView(),groupModel.groupPostion);
                    }else {
                          int groupPostion=-1;
                          int childPostion=-1;
                        for (int i = 0; i <mData.size() ; i++) {
                            GroupModel groupModel = (GroupModel) mData.get(i);

                                for (int j = 0; j <groupModel.childList.size() ; j++) {
                                Object obj =groupModel.childList.get(j);
                                Object obj1 = getItem(position - headerCount);
                                if(obj.equals(obj1)){
                                    groupPostion = groupModel.groupPostion;
                                    childPostion =j;
                                }
                            }
                        }

                        onItemClickListener.onChildItemClick(holder.getConvertView(), groupPostion,childPostion);
                    }

                }
            });
        }

    }

    @Override
    public int getItemViewType(int position) {
        Object obj = getItem(position - headerCount);
        if (isHeaderItem(position)) {
            return RECYCLE_TYPE_HEADER;
        } else if (isFooterItem(position)) {
            return RECYCLE_TYPE_FOOTER;
        } else if(obj instanceof ExpandableBaseRecyclerAdapter.GroupModel){
            return RECYCLE_TYPE_ITEM_GROUP;
        }
        return  RECYCLE_TYPE_ITEM_CHILD;
    }

    @Override
    public void onViewAttachedToWindow(ExpandableBaseViewHolder holder) {
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
     * 获取特定Item的数据
     * @param position
     * @return
     */
    public Object getItem(int position) {
        if(position>=0){
            int size = 0;
            for (int i = 0; i < mData.size(); i++) {
                GroupModel groupModel = (GroupModel) mData.get(i);
                //如果当前组显示
                if (groupModel.isShow) {
                    size++;
                    if (size - 1 == position) {
                        return groupModel;
                    }
                }
                //如果当前组展开
                if (groupModel.isOpen && groupModel.childList != null) {
                    int tempSize = size + groupModel.childList.size();
                    if (position <= tempSize - 1) {
                        return groupModel.childList.get(position - size);
                    }
                    size = tempSize;
                }
            }
        }

        return null;
    }

    public void setClickGroup(boolean clickGroup) {
        isClickGroup = clickGroup;
    }
}
