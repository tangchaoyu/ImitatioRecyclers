package com.myrecyclers.tcy.imitatiorecyclers.adapter;

import android.content.Context;
import android.widget.TextView;

import com.myrecyclers.tcy.imitatiolibrary.expandable.adapter.ExpandableBaseDelegate;
import com.myrecyclers.tcy.imitatiolibrary.expandable.adapter.ExpandableBaseRecyclerAdapter;
import com.myrecyclers.tcy.imitatiolibrary.expandable.adapter.ExpandableBaseViewHolder;
import com.myrecyclers.tcy.imitatiorecyclers.R;
import com.myrecyclers.tcy.imitatiorecyclers.bean.Demo2Child;
import com.myrecyclers.tcy.imitatiorecyclers.bean.Demo2Group;

import java.util.ArrayList;


/**
 * Created by tcy on 2018/6/12.
 */

public class Demo2Delegate extends ExpandableBaseDelegate<Demo2Group> {

    private Context context;
    private ArrayList<Demo2Group> arrayList;
    public Demo2Delegate(Context context) {
        super(R.layout.header, R.layout.item_group, R.layout.item);
        this.context = context;
    }

    @Override
    public int getGroupCount() {
        if(arrayList==null){
            return 0;
        }
        return arrayList.size();
    }

    @Override
    public int getChildCount(int groupPosition) {
        if(arrayList==null){
            return 0;
        }else if(arrayList.get(groupPosition).getList()==null){
            return 0;
        }
        return arrayList.get(groupPosition).getList().size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        if(arrayList==null){
            return "";
        }
        return arrayList.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        if(arrayList==null){
            return "";
        }else if(arrayList.get(groupPosition).getList().get(childPosition)==null){
            return "";
        }
        return arrayList.get(groupPosition).getList().get(childPosition);
    }


    @Override
    public void initHeaderView(ExpandableBaseViewHolder holder) {
        TextView header = holder.findViewById(R.id.header);
        header.setText("我是header");
        super.initHeaderView(holder);
    }

    @Override
    public void initGroupView(ExpandableBaseViewHolder holder, ExpandableBaseRecyclerAdapter.GroupModel groupModel, Object groupObject, int position) {
        TextView str =  holder.findViewById(R.id.str);
        Demo2Group demo2Group = (Demo2Group) groupObject;
        str.setText(demo2Group.getName());
        holder.itemView.setContentDescription(demo2Group.getName());
        super.initGroupView(holder, groupModel, groupObject, position);
    }

    @Override
    public void initChildView(ExpandableBaseViewHolder holder, Object groupObject, Object childObject, int position) {
        TextView str =  holder.findViewById(R.id.str);
        Demo2Child demo2Child = (Demo2Child) childObject;
        Demo2Group demo2Group = (Demo2Group) groupObject;
        str.setText(demo2Child.getName());
        holder.itemView.setContentDescription(demo2Group.getName());
        super.initChildView(holder, groupObject, childObject, position);
    }


    public void setArrayList(ArrayList<Demo2Group> arrayList) {
        this.arrayList = arrayList;
    }
}
