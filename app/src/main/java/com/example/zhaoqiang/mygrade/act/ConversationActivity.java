package com.example.zhaoqiang.mygrade.act;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.example.zhaoqiang.mygrade.MainActivity;
import com.example.zhaoqiang.mygrade.R;
import com.example.zhaoqiang.mygrade.ada.ConversationAdapater;

import java.util.ArrayList;

/**
 * Created by 轩韩子 on 2017/3/23.
 * at 14:43
 * 消息列表页
 */

public class ConversationActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btn_conversation, btn_address_book, btn_set;
    private ListView listview;
    private SwipeRefreshLayout swipe;
    private ConversationAdapater con;
    private ArrayList<String> list = new ArrayList<>();
    private Context context;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.conversation_act);
        init();
    }

    public void init() {
        swipe = (SwipeRefreshLayout) findViewById(R.id.swipe);
        listview = (ListView) findViewById(R.id.listview_con);
        btn_conversation = (Button) findViewById(R.id.btn_conversation);
        btn_address_book = (Button) findViewById(R.id.btn_address_book);
        btn_set = (Button) findViewById(R.id.btn_set);
        btn_conversation.setOnClickListener(this);
        btn_address_book.setOnClickListener(this);
        btn_set.setOnClickListener(this);
        con = new ConversationAdapater(this, list);
        listview.setAdapter(con);
        //设置刷新旋转颜色
        swipe.setColorSchemeResources(R.color.colorAccent, R.color.colorPrimary);
        //下拉刷新监听
        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipe.setRefreshing(false);
                list.add(0, "new item");
                con.notifyDataSetChanged();
            }
        });
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
