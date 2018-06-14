package com.myrecyclers.tcy.imitatiorecyclers;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;


import com.myrecyclers.tcy.imitatiolibrary.MyPullRecyclerView;
import com.myrecyclers.tcy.imitatiolibrary.MyPullSwipeRefresh;
import com.myrecyclers.tcy.imitatiolibrary.adapter.BaseRecyclerAdapter;
import com.myrecyclers.tcy.imitatiolibrary.adapter.PullRefreshAdapter;
import com.myrecyclers.tcy.imitatiorecyclers.adapter.Demo1Delegate;
import com.myrecyclers.tcy.imitatiorecyclers.bean.Demo1;

import java.util.ArrayList;

public class Demo1Activity extends AppCompatActivity {

    private MyPullSwipeRefresh pullSwipeRefresh;
    private MyPullRecyclerView recyclerView;
    private PullRefreshAdapter<Demo1> adapter;
    private ArrayList<Demo1> arrayList =new ArrayList<>();
    private Demo1Delegate delegate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo1);
        arrayList = new ArrayList<>();
        pullSwipeRefresh = (MyPullSwipeRefresh) findViewById(R.id.pullSwipeRefresh);
        recyclerView = (MyPullRecyclerView) findViewById(R.id.recyclerView);
        delegate = new Demo1Delegate();
        adapter = new PullRefreshAdapter(this,arrayList,1,delegate);
        recyclerView.setAdapter(adapter);
        adapter.setSwipeRefresh(pullSwipeRefresh);
        initData();

        pullSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                adapter.resetPageIndex();
                initData();
            }
        });

        recyclerView.setOnAddMoreListener(new MyPullRecyclerView.OnAddMoreListener() {
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

        adapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(getApplicationContext(),"点击了"+position,Toast.LENGTH_SHORT).show();
            }
        });

    }

    private  void initData(){
        ArrayList<Demo1> initList = new ArrayList<>();
        for (int i = 0; i <20 ; i++) {
            Demo1 demo1 = new Demo1();
            demo1.setName("第"+i+"条");
            initList.add(demo1);
        }
        adapter.setTotalPage(2);
        adapter.setPullData(initList);
    }

    private void addData(){

        ArrayList<Demo1> addList = new ArrayList<>();
        for (int i = 0; i <10 ; i++) {
            Demo1 demo1 = new Demo1();
            demo1.setName("我是新添加的"+i);
            addList.add(demo1);
        }
        adapter.setTotalPage(2);
        adapter.setPullData(addList);

    }

}
