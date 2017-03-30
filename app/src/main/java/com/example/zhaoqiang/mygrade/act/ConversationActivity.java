package com.example.zhaoqiang.mygrade.act;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.example.zhaoqiang.mygrade.MainActivity;
import com.example.zhaoqiang.mygrade.R;
import com.example.zhaoqiang.mygrade.ada.ConversationAdapater;
import com.example.zhaoqiang.mygrade.help.Refresh;

import java.util.ArrayList;

/**
 * Created by 轩韩子 on 2017/3/23.
 * at 14:43
 * 消息列表页
 */

public class ConversationActivity extends AppCompatActivity implements View.OnClickListener {
    private View view;
    private Refresh swipe;
    private ListView listview;
    private ConversationAdapater conAda;
    private ArrayList<String> list = new ArrayList<>();
    private Button btn_conversation, btn_address_book, btn_set;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.conversation_act);
        //设置数据源
        getData();
        //初始化控件
        init();
    }

    public void init() {
        //获取控件
        swipe = (Refresh) findViewById(R.id.swipe);
        btn_set = (Button) findViewById(R.id.btn_set);
        listview = (ListView) findViewById(R.id.listview_con);
        btn_conversation = (Button) findViewById(R.id.btn_conversation);
        btn_address_book = (Button) findViewById(R.id.btn_address_book);
        //设置点击事件
        btn_set.setOnClickListener(this);
        btn_conversation.setOnClickListener(this);
        btn_address_book.setOnClickListener(this);
        //实例化适配器
        conAda = new ConversationAdapater(this, list);
        //加载foot view布局
        view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.con_new_item_pro, null, false);
        //设置适配器
        listview.setAdapter(conAda);
        // 设置上拉和下拉刷新
        setUpAndDown();


    }

    private void setUpAndDown() {
        //设置刷新旋转颜色
        swipe.setColorSchemeResources(R.color.colorAccent, R.color.colorPrimary);
        //设置下拉刷新监听
        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // 修改数据源里的数据
                list.add(0, "下拉刷新显示");
//                刷新listview
                conAda.notifyDataSetChanged();
                //关闭下拉进度
                swipe.setRefreshing(false);
            }
        });

        //设置上拉刷新监听
        swipe.setSetLoadlistener(new Refresh.loadListener() {
            @Override
            public void load() {
                swipe.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //  修改数据源里的数据
                        list.add("上拉刷新显示");
                        //刷新listview
                        conAda.notifyDataSetChanged();
//                        关闭加载进度
                        swipe.setLoadView(false);
                    }
                }, 1500);

            }

            @Override
            public void setFootView(boolean loading) {
                // 根据loading设置footview（底部加载更多控件）
                if (loading) {
                    listview.removeFooterView(view);
                    listview.addFooterView(view);
                    //将listview滑到最底部
                    listview.setSelection(conAda.getCount());
                } else {
                    listview.removeFooterView(view);
                }
            }
        });
    }

    private void getData() {
        list.add("xxxxxxx");
        list.add("zzzzzzz");
        list.add("ccccccc");
    }

    //点击事件
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_conversation:

                break;
            case R.id.btn_address_book:
                startActivity(new Intent(this, MainActivity.class));
                finish();
                break;
            case R.id.btn_set:
                startActivity(new Intent(this, SetActivity.class));
                finish();
                break;

        }
    }

}
