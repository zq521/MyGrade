package com.example.zhaoqiang.mygrade;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.example.zhaoqiang.mygrade.act.AddConActivity;
import com.example.zhaoqiang.mygrade.act.ConversationActivity;
import com.example.zhaoqiang.mygrade.act.SetActivity;
import com.example.zhaoqiang.mygrade.ada.EaseUser;
import com.example.zhaoqiang.mygrade.ada.PersonAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by 轩韩子 on 2017/3/23.
 * at 21:25
 */

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btn_conversation, btn_address_book, btn_set, button_add;

    protected ListView listview;
    private PersonAdapter adapter;
    private Map<String, EaseUser> contactsMap;
    protected List<EaseUser> contactList = new ArrayList<EaseUser>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        listview = (ListView) this.findViewById(R.id.listview_main);

        adapter = new PersonAdapter(this, contactList);
        listview.setAdapter(adapter);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                // startActivity(new Intent(MainActivity.this, ChatActivity.class).putExtra("username", adapter.getItem(arg2).getUsername()));
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