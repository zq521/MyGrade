package com.example.zhaoqiang.mygrade.help;

import android.content.Context;
import android.graphics.Canvas;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;

/**
 * Created by 轩韩子 on 2017/3/29.
 * at 08:53
 * 重写下拉刷新控件，实现上拉加载
 */

public class Refresh extends SwipeRefreshLayout implements AbsListView.OnScrollListener {
    private ListView listview;
    //正在加载中 标志
    private boolean loading = false;
    //    监听对象，用于调用 接口的实现方法
    private loadListener loadListener;

    //   设置监听
    public void setSetLoadlistener(loadListener loadListener) {
        this.loadListener = loadListener;
    }

    public Refresh(Context context) {
        super(context);
    }

    public Refresh(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    //布局
    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        //获取子控件个数
        int childs = getChildCount();
        //判断子控件至少有一个
        if (childs >0) {
            //获取第一个子控件
            View child = getChildAt(0);
            //判断 第一个子控件 是不是listview 对象
            if (child instanceof ListView) {
                listview = (ListView) child;
                //设置滑动监听
                listview.setOnScrollListener(this);
            }
        }

    }

    //测量
    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    //绘制
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    //滚动状态改变
    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if (scrollState == 0) {
            canLoad();
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.e("dispatchTouchEvent", ev.getAction() + "");
        Log.e("dispatchTouchEvent", "getX=" + ev.getX());
        Log.e("dispatchTouchEvent", "getY=" + ev.getY());
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public float getX() {
        return super.getX();
    }

    @Override
    public float getY() {
        return super.getY();
    }

    //滚动
    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

    }


    //是否可用加载更多
    private boolean canLoad() {
        if (isBottom() && !loading) {

            load();
        }
        return false;
    }

    //开始加载
    private void load() {
        if (loadListener != null) {
            setLoadView(true);
            //实现加载数据的方法
            loadListener.load();
        }
    }

    //   设置 加载更多 是否可见
//    isLoading true
    public void setLoadView(boolean isLoading) {
        loading = isLoading;
        //显示视图
        loadListener.setFootView(isLoading);
    }

    //判断listview是否滑到底部
    private boolean isBottom() {
        // 判断listview不为空，并且有数据内容
        if (listview != null && listview.getAdapter().getCount() > 0) {

        }
        //判断当前最后一个可见item下标是否是listview最后一个item；
        if (listview.getLastVisiblePosition() == listview.getAdapter().getCount() - 1) {
            return true;
        }
        return false;
    }

    //上拉加载更多接口
    public interface loadListener {
        //用于加载数据
        void load();

        //用于设置加载更多 是否可见
        void setFootView(boolean loading);
    }


}
