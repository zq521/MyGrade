package com.example.zhaoqiang.mygrade.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.example.zhaoqiang.mygrade.R;
import com.example.zhaoqiang.mygrade.act.AddConActivity;
import com.example.zhaoqiang.mygrade.act.ChatActivity;
import com.example.zhaoqiang.mygrade.ada.PersonAdapter;
import com.example.zhaoqiang.mygrade.help.Refresh;
import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 轩韩子 on 2017/4/5.
 * at 13:54
 */

public class PersonFragment extends Fragment implements View.OnClickListener {
    private View views;
    private Refresh swipe_main;
    protected ListView listview;
    private PersonAdapter personAdapter;
    protected ArrayList<String> list = new ArrayList<>();
    private Button button_add;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.frag_person, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);
        //获取好友列表
         getPerson();

    }

    //初始化控件
    public void init(View view) {
        listview = (ListView) view.findViewById(R.id.listview_main);
        button_add = (Button) view.findViewById(R.id.button_add);
        button_add.setOnClickListener(this);
        swipe_main = (Refresh) view.findViewById(R.id.swipe_main);
        listview = (ListView) view.findViewById(R.id.listview_main);
        personAdapter = new PersonAdapter(getActivity(), list);
        //加载foot view布局
        views = LayoutInflater.from(getActivity()).inflate(R.layout.progress_up_item, null, false);

        setUpAndDown();

        //item点击事件
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

                startActivity(new Intent(getActivity(), ChatActivity.class));
            }

        });
    }


    private void setUpAndDown() {
        //下拉
        swipe_main.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                list.add(0, "我是下拉出来的");
                personAdapter.notifyDataSetChanged();
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

    /**
     * 获取好友列表
     */
    private void getPerson() {
        try {
            List<String> usernames = EMClient.getInstance().contactManager().getAllContactsFromServer();
            for (String s:usernames
                 ) {
                list.add(s);
            }

            personAdapter.notifyDataSetChanged();
        } catch (HyphenateException e) {
            e.printStackTrace();
        }

        listview.setAdapter(personAdapter);
    }

    //跳转到添加好友页面
    @Override
    public void onClick(View v) {
        startActivity(new Intent(getActivity(), AddConActivity.class));
    }
}
