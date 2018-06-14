package com.myrecyclers.tcy.imitatiorecyclers;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.myrecyclers.tcy.imitatiolibrary.ExpandablePullRecyclerView;
import com.myrecyclers.tcy.imitatiolibrary.MyPullSwipeRefresh;
import com.myrecyclers.tcy.imitatiolibrary.expandable.adapter.ExpandableBaseRecyclerAdapter;
import com.myrecyclers.tcy.imitatiolibrary.expandable.adapter.ExpandablePullRefreshAdapter;
import com.myrecyclers.tcy.imitatiorecyclers.adapter.Demo2Delegate;
import com.myrecyclers.tcy.imitatiorecyclers.bean.Demo2Child;
import com.myrecyclers.tcy.imitatiorecyclers.bean.Demo2Group;

import java.util.ArrayList;

public class Demo3Activity extends AppCompatActivity {
    private MyPullSwipeRefresh pullSwipeRefresh;
    private ExpandablePullRecyclerView recyclerView;
    private ArrayList<Demo2Group> demo2Groups= new ArrayList<>();
    private ExpandablePullRefreshAdapter<Demo2Group> adapter;
    private Demo2Delegate delegate;

    private RelativeLayout header_ly;
    private LinearLayout group_ly;
    private TextView str;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo3);
        pullSwipeRefresh = (MyPullSwipeRefresh) findViewById(R.id.pullSwipeRefresh);
        recyclerView = (ExpandablePullRecyclerView) findViewById(R.id.recyclerView);

        header_ly = (RelativeLayout) findViewById(R.id.header_ly);
        header_ly.setVisibility(View.INVISIBLE);
        group_ly = (LinearLayout) findViewById(R.id.group_ly);
        group_ly.setVisibility(View.INVISIBLE);
        str = (TextView) findViewById(R.id.str);

        recyclerView.setHeaderView(header_ly);
        recyclerView.setStickyheaderView(group_ly);
        recyclerView.setStickyheaderText(str);

        delegate = new Demo2Delegate(this);
        adapter = new ExpandablePullRefreshAdapter(this,demo2Groups,1,delegate);
        recyclerView.setAdapter(adapter);
        adapter.setSwipeRefresh(pullSwipeRefresh);
        adapter.setClickGroup(false);


        pullSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                adapter.resetPageIndex();
                initData();
            }
        });

        recyclerView.setOnAddMoreListener(new ExpandablePullRecyclerView.OnAddMoreListener() {
            @Override
            public void addMoreListener() {
                adapter.addPageIndex();
                new Handler().postDelayed(new Runnable(){
                    public void run() {
                        addData();
                    }
                }, 2000);

            }
        });

        adapter.setOnItemClickListener(new ExpandableBaseRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onGroupItemClick(View view, int groupPosition) {

            }

            @Override
            public void onChildItemClick(View view, int groupPosition, int childPosition) {

            }
        });

        initData();

    }

    private void initData(){
        ArrayList<Demo2Group> groups= new ArrayList<>();
        for (int i = 0; i <10 ; i++) {
            ArrayList<Demo2Child> childs= new ArrayList<>();
            Demo2Group demo2Group = new Demo2Group();
            demo2Group.setName("group"+i);
            for (int j = 0; j <8 ; j++) {
                Demo2Child demo2Child = new Demo2Child();
                demo2Child.setName("child"+j);
                childs.add(demo2Child);
                demo2Group.setList(childs);
            }

            groups.add(demo2Group);
        }

        delegate.setArrayList(groups);
        adapter.setTotalPage(2);
        adapter.setPullData(groups);
        str.setText(groups.get(0).getName());
    }

    private void addData(){
        ArrayList<Demo2Group> groups= new ArrayList<>();
        for (int i = 0; i <5 ; i++) {
            ArrayList<Demo2Child> childs= new ArrayList<>();
            Demo2Group demo2Group = new Demo2Group();
            demo2Group.setName("新加group"+i);
            for (int j = 0; j <6 ; j++) {
                Demo2Child demo2Child = new Demo2Child();
                demo2Child.setName("新加child"+j);
                childs.add(demo2Child);
                demo2Group.setList(childs);
            }

            groups.add(demo2Group);
        }

        delegate.setArrayList(groups);
        adapter.setTotalPage(2);
        adapter.setPullData(groups);
    }
}
