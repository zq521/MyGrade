package com.example.zhaoqiang.mygrade.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.zhaoqiang.mygrade.R;
import com.example.zhaoqiang.mygrade.ada.GroupListAdapter;
import com.example.zhaoqiang.mygrade.callback.CallListener;
import com.example.zhaoqiang.mygrade.help.Refresh;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 轩韩子 on 2017/4/7.
 * at 16:06
 * 群组列表
 */

public class GroupListFragment extends Fragment implements CallListener {
    private View views;
    private Refresh swipe_main;
    private RecyclerView group_recycler;
    private ArrayList<EMGroup> list = new ArrayList<>();
    private GroupListAdapter groupListAdapter;

    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            groupListAdapter.notifyDataSetChanged();
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.frag_group_list, container, false);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);
    }

    private void init(View view) {
        swipe_main = (Refresh) view.findViewById(R.id.swipe_main);
        group_recycler = (RecyclerView) view.findViewById(R.id.group_recycler);
        //获取数据
        getData();
        groupListAdapter = new GroupListAdapter(getActivity(), list);
        groupListAdapter.setCallListener(this);
        //线性布局管理器  设置 水平滚动
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        group_recycler.setLayoutManager(linearLayoutManager);
        group_recycler.setAdapter(groupListAdapter);
        groupListAdapter.notifyDataSetChanged();

        //加载foot view布局
        views = LayoutInflater.from(getActivity()).inflate(R.layout.progress_up_item, null, false);
        setUpAndDown();
    }

    /*
    获取群组列表
     */
    private void getData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    //从服务器群组列表
                    List<EMGroup> grouplist = EMClient.getInstance().groupManager().getJoinedGroupsFromServer();
                    for (EMGroup s : grouplist) {
                        list.add(s);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                handler.sendMessage(new Message());
            }
        }).start();

    }

    /*
    下拉和上拉刷新
     */
    private void setUpAndDown() {
        //下拉
        swipe_main.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                groupListAdapter.notifyDataSetChanged();
                swipe_main.setRefreshing(false);
            }
        });
//        //上拉
//        swipe_main.setSetLoadlistener(new Refresh.loadListener() {
//            @Override
//            public void load() {
//                swipe_main.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        list.add("我是上拉出来的");
//                        personAdapter.notifyDataSetChanged();
//                        swipe_main.setLoadView(false);
//                    }
//                }, 1500);
//            }
//
//            @Override
//            public void setFootView(boolean loading) {
//                if (loading) {
//                    listview.removeFooterView(views);
//                    listview.addFooterView(views);
//                    listview.setSelection(personAdapter.getCount());
//                } else {
//                    listview.removeFooterView(views);
//                }
//            }
//        });
//

    }

    /*
    删除
     */
    @Override
    public void Click(int id) {

    }

    /*
   跳转到聊天详情页
    */
    @Override
    public void ItemClick(int id) {

    }

    @Override
    public void top(int id) {

    }


}
