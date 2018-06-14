# ImitatioRrecyclers
一个简单通用的recyclerView,主要实现普通列表和分组列表的大部分需求效果

在开发中列表是常用的,所以很有必要封装一下，ImitatioRrecyclers主要实现普通一级列表和分组二级列表
*  支持添加header
*  支持滚动到底部自动加载更多
*  支持分组
*  支持点击分组收缩
*  支持分组悬停吸顶

![img](https://github.com/tangchaoyu/ImitatioRecyclers/blob/master/QQ%E5%9B%BE%E7%89%8720180614112609.gif) 

![img](https://github.com/tangchaoyu/ImitatioRecyclers/blob/master/QQ%E5%9B%BE%E7%89%8720180614112614.gif) 

![img](https://github.com/tangchaoyu/ImitatioRecyclers/blob/master/QQ%E5%9B%BE%E7%89%8720180614112618.gif) 

使用说明：
 
 
 allprojects {
	repositories {
		...
		maven { url 'https://jitpack.io' }
	}
  }
  
	dependencies {
		implementation 'com.github.tangchaoyu:ImitatioRecyclers:1.0.1'

	}


 ## 一级列表
 
 ```
 <com.myrecyclers.tcy.imitatiolibrary.MyPullSwipeRefresh
        android:id="@+id/pullSwipeRefresh"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        >
        <com.myrecyclers.tcy.imitatiolibrary.MyPullRecyclerView
            android:id="@+id/recyclerView"
            android:layout_width="match_parent"
            android:layout_height="match_parent"
            >
        </com.myrecyclers.tcy.imitatiolibrary.MyPullRecyclerView>

    </com.myrecyclers.tcy.imitatiolibrary.MyPullSwipeRefresh>
```
 
 XXXDelegate extends BaseDelegate<Demo1> 指定泛型（你自己的定义的实体类对象）
 
```
 public class XXXDelegate extends BaseDelegate<Demo1> {

    public Demo1Delegate() {
        super(R.layout.header, R.layout.item);
    }
    @Override
    public void initHeaderView(BaseViewHolder holder) {
        TextView header = holder.findViewById(R.id.header);
        header.setText("我是header");
        super.initHeaderView(holder);
    }
    @Override
    public void initCustomView(BaseViewHolder holder, List<Demo1> data, int position) {
        TextView str =  holder.findViewById(R.id.str);
        str.setText(data.get(position).getName());
        super.initCustomView(holder, data, position);
    }
    
``` 
 指定headerlayout itemlayut  如果没有头部只指定itemlayut
 重写 initHeaderView initCustomView 如果没有头部只重写initCustomView
 
 ```
 delegate = new Demo1Delegate();
 adapter = new PullRefreshAdapter(this,arrayList,1,delegate);
 recyclerView.setAdapter(adapter);
 adapter.setSwipeRefresh(pullSwipeRefresh);
```
 adapter = new PullRefreshAdapter(this,arrayList,1,delegate);

 参数1 代表添加头部 如果不需要可以去掉或者写成0

 ```
adapter.setTotalPage(2);
adapter.setPullData(initList);

```
dapter.setTotalPage(2);设置总页数 必须写 设置为2代表可以加载两次分页

下拉刷新监听
```
pullSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
   @Override
    public void onRefresh() {
     adapter.resetPageIndex();
```
加载更多监听
```
 recyclerView.setOnAddMoreListener(new MyPullRecyclerView.OnAddMoreListener()
 adapter.addPageIndex();
```
点击事件
```
adapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener()
```
 ## 二级分组列表
 

```
 <com.myrecyclers.tcy.imitatiolibrary.MyPullSwipeRefresh
        android:id="@+id/pullSwipeRefresh"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        >
        <FrameLayout
            android:layout_width="match_parent"
            android:layout_height="match_parent"
            app:layout_behavior="@string/appbar_scrolling_view_behavior">

            <com.myrecyclers.tcy.imitatiolibrary.ExpandablePullRecyclerView
                android:id="@+id/recyclerView"
                android:layout_width="match_parent"
                android:layout_height="match_parent"
                app:divider_decoration_height="0"
                app:divider_decoration_width="0"
                >

            </com.myrecyclers.tcy.imitatiolibrary.ExpandablePullRecyclerView>
		
        </FrameLayout>
    </com.myrecyclers.tcy.imitatiolibrary.MyPullSwipeRefresh>
```
XXXDelegate extends ExpandableBaseDelegate<Demo2Group> 指定泛型（你自己的定义的实体类对象）

  ```
 public Demo2Delegate(Context context) {
        super(R.layout.header,R.layout.item_group, R.layout.item);
        this.context = context;
    }

```
指定headerlayout grouplayout itemlayut  如果没有头部只指定grouplayout,itemlayut

 ```
  @Override
    public int getGroupCount() {
    }

  @Override
    public int getChildCount(int groupPosition) {
    }
  @Override
    public Object getGroup(int groupPosition) {
    }
  @Override
    public Object getChild(int groupPosition, int childPosition){
   } 
    
```
实现这四个方法

  ```
   @Override
    public void initHeaderView(ExpandableBaseViewHolder holder) {
        super.initHeaderView(holder);
    }

    @Override
    public void initGroupView(ExpandableBaseViewHolder holder, ExpandableBaseRecyclerAdapter.GroupModel groupModel, Object groupObject,  int position) {
        super.initGroupView(holder, groupModel, groupObject, position);
    }

    @Override
    public void initChildView(ExpandableBaseViewHolder holder, Object groupObject, Object childObject, int position) {
        super.initChildView(holder, groupObject, childObject, position);
    }

```
重写这三个方法 如果没有头部可以不写initHeaderView方法

  ```
 public void setArrayList(ArrayList<Demo2Group> arrayList) {
        this.arrayList = arrayList;
    }

```
写一个设置数据源的方法

  ```
delegate = new Demo2Delegate(this);
adapter = new ExpandablePullRefreshAdapter(this,demo2Groups,1,delegate);
recyclerView.setAdapter(adapter);
adapter.setSwipeRefresh(pullSwipeRefresh);
adapter.setClickGroup(true);

```
创建ExpandablePullRefreshAdapter适配器
setClickGroup方法默认为true 设为flase 点击没有收缩效果

刷新
  ```
 pullSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                adapter.resetPageIndex();
                initData();
            }
        });
```

加载更多
  ```
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
```

点击事件

  ```
    adapter.setOnItemClickListener(new ExpandableBaseRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onGroupItemClick(View view, int groupPosition) {

            }

            @Override
            public void onChildItemClick(View view, int groupPosition, int childPosition) {

            }
        });
```
## 二级分组列表悬停效果
  ```
 <com.myrecyclers.tcy.imitatiolibrary.MyPullSwipeRefresh
        android:id="@+id/pullSwipeRefresh"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        >
        <FrameLayout
            android:layout_width="match_parent"
            android:layout_height="match_parent"
            app:layout_behavior="@string/appbar_scrolling_view_behavior">

            <com.myrecyclers.tcy.imitatiolibrary.ExpandablePullRecyclerView
                android:id="@+id/recyclerView"
                android:layout_width="match_parent"
                android:layout_height="match_parent"
                app:divider_decoration_height="0"
                app:divider_decoration_width="0"
                >

            </com.myrecyclers.tcy.imitatiolibrary.ExpandablePullRecyclerView>

            <LinearLayout
                android:layout_width="match_parent"
                android:layout_height="match_parent"
                android:orientation="vertical">
                <include layout="@layout/header"></include>
                <include layout="@layout/item_group"></include>
            </LinearLayout>

        </FrameLayout>
    </com.myrecyclers.tcy.imitatiolibrary.MyPullSwipeRefresh>

```
如果有头部 把头部布局和group布局放在一个线性布局内 如果没有头部 就直接引用你的group布局

  ```
header_ly = (RelativeLayout) findViewById(R.id.header_ly);
header_ly.setVisibility(View.INVISIBLE);
group_ly = (LinearLayout) findViewById(R.id.group_ly);
group_ly.setVisibility(View.INVISIBLE);

recyclerView.setHeaderView(header_ly);
recyclerView.setStickyheaderView(group_ly);
recyclerView.setStickyheaderText(str);

adapter.setClickGroup(false);
```
如果有头部 把头部布局和group布局设置成INVISIBLE 并调用setHeaderView方法 如果没有则不需要
设置 adapter.setClickGroup(false); 为false 不可点击收缩

在你的initGroupView initChildView方法里设置group的title：
 ```
holder.itemView.setContentDescription(demo2Group.getName());
```

详细文档地址：https://blog.csdn.net/dongbeitcy/article/details/80652834
