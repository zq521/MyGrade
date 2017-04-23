package com.example.zhaoqiang.mygrade.fragment;

import android.content.Intent;
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
import android.widget.TextView;

import com.example.zhaoqiang.mygrade.R;
import com.example.zhaoqiang.mygrade.act.ChatActivity;
import com.example.zhaoqiang.mygrade.act.GroupListActivity;
import com.example.zhaoqiang.mygrade.ada.PersonListAdapter;
import com.example.zhaoqiang.mygrade.callback.CallListener;
import com.example.zhaoqiang.mygrade.help.Refresh;
import com.hyphenate.chat.EMClient;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 轩韩子 on 2017/4/7.
 * at 16:05
 *好友页面
 */

public class PersonListFragment extends Fragment implements View.OnClickListener, CallListener {
    private View views;
    private TextView new_person,group;
    private Refresh swipe_main;
    private RecyclerView penson_recycler;
    private PersonListAdapter personListAdapter;
    private ArrayList<String> list = new ArrayList<>();

    private Handler handler = new Handler(){
        public void handleMessage(Message msg) {
            personListAdapter.notifyDataSetChanged();
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.frag_person_list, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);

    }

    private void init(View view) {
        new_person= (TextView) view.findViewById(R.id.new_person);
        group= (TextView) view.findViewById(R.id.group);
        new_person.setOnClickListener(this);
        group.setOnClickListener(this);
        swipe_main = (Refresh) view.findViewById(R.id.swipe_main);
        penson_recycler = (RecyclerView) view.findViewById(R.id.penson_recycler);
        //获取好友列表
        getPerson();
        personListAdapter = new PersonListAdapter(getActivity(), list);
        personListAdapter.setCallListener(this);

        //线性布局管理器  设置 水平滚动
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        penson_recycler.setLayoutManager(linearLayoutManager);
        penson_recycler.setAdapter(personListAdapter);


        //加载foot view布局
        views = LayoutInflater.from(getActivity()).inflate(R.layout.progress_up_item, null, false);
        setUpAndDown();

    }


    /*
    上拉和下拉刷新
     */
    private void setUpAndDown() {
        //设置刷新旋转颜色
        swipe_main.setColorSchemeResources(R.color.colorPrimary);
        //下拉
        swipe_main.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                personListAdapter.notifyDataSetChanged();
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
        * 获取好友列表
        */
    private void getPerson() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    List<String> usernames = EMClient.getInstance().contactManager().getAllContactsFromServer();
                    for (String s : usernames
                            ) {
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
    跳转到添加页面
     */
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.new_person:

                break;
            case R.id.group:
                startActivity(new Intent(getActivity(),GroupListActivity.class));
                break;

        }

    }

    /*
    修改备注
     */
    @Override
    public void Click(int id) {

    }

    /*
    跳转到聊天详情页
     */
    @Override
    public void ItemClick(int id) {
        startActivity(new Intent(getActivity(), ChatActivity.class).putExtra("userName", list.get(id).toString()));
    }

    @Override
    public void top(int id) {

    }


}
