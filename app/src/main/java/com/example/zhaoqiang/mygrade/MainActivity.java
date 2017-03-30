package com.example.zhaoqiang.mygrade;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.example.zhaoqiang.mygrade.act.AddConActivity;
import com.example.zhaoqiang.mygrade.act.ChatActivity;
import com.example.zhaoqiang.mygrade.act.ConversationActivity;
import com.example.zhaoqiang.mygrade.act.SetActivity;
import com.example.zhaoqiang.mygrade.ada.PersonAdapter;
import com.example.zhaoqiang.mygrade.help.Refresh;

import java.util.ArrayList;

/**
 * Created by 轩韩子 on 2017/3/23.
 * at 21:25
 */

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btn_conversation, btn_address_book, btn_set, button_add;
    private View view;
    private Refresh swipe_main;
    protected ListView listview;
    private PersonAdapter personAdapter;
    protected ArrayList<String> list = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                startActivity(new Intent(MainActivity.this, ChatActivity.class));
                finish();
            }

        });

    }

    public void init() {
        listview = (ListView) findViewById(R.id.listview_main);
        btn_conversation = (Button) findViewById(R.id.btn_conversation);
        btn_address_book = (Button) findViewById(R.id.btn_address_book);
        btn_set = (Button) findViewById(R.id.btn_set);
        button_add = (Button) findViewById(R.id.button_add);
        btn_conversation.setOnClickListener(this);
        btn_address_book.setOnClickListener(this);
        btn_set.setOnClickListener(this);
        button_add.setOnClickListener(this);
        swipe_main= (Refresh) findViewById(R.id.swipe_main);
        listview = (ListView) this.findViewById(R.id.listview_main);
        personAdapter = new PersonAdapter(this,list);
        //加载foot view布局
        view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.con_new_item_pro, null, false);
        listview.setAdapter(personAdapter);
        setUpAndDown();
    }

    private void setUpAndDown() {
        //下拉
        swipe_main.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                list.add(0,"我是下拉出来的");
                personAdapter.notifyDataSetChanged();
                swipe_main.setRefreshing(false);
            }
        });
        //上拉
        swipe_main.setSetLoadlistener(new Refresh.loadListener() {
            @Override
            public void load() {
                swipe_main.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        list.add("我是上拉出来的");
                        personAdapter.notifyDataSetChanged();
                        swipe_main.setLoadView(false);
                    }
                },1500);
            }

            @Override
            public void setFootView(boolean loading) {
                if (loading){
                    listview.removeFooterView(view);
                    listview.addFooterView(view);
                    listview.setSelection(personAdapter.getCount());
                }else {
                    listview.removeFooterView(view);
                }
            }
        });


    }

    //点击事件
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_conversation:
                startActivity(new Intent(this, ConversationActivity.class));
                finish();
                break;
            case R.id.btn_address_book:

                break;
            case R.id.btn_set:
                startActivity(new Intent(this, SetActivity.class));
                finish();

                break;
            case R.id.button_add:
                startActivity(new Intent(this, AddConActivity.class));
                finish();
                break;
        }
    }

}