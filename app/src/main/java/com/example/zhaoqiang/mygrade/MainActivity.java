package com.example.zhaoqiang.mygrade;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.example.zhaoqiang.mygrade.act.ConversationActivity;
import com.example.zhaoqiang.mygrade.act.SetActivity;
import com.example.zhaoqiang.mygrade.ada.PersonAdapter;
import com.example.zhaoqiang.mygrade.help.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by 轩韩子 on 2017/3/23.
 * at 21:25
 */

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btn_conversation, btn_address_book, btn_set;

    protected List<User> user = new ArrayList<User>();
    protected ListView listview;
    private Map<String, User> contactsMap;
    private PersonAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void init() {
        listview = (ListView) findViewById(R.id.listview_con);
        btn_conversation = (Button) findViewById(R.id.btn_conversation);
        btn_address_book = (Button) findViewById(R.id.btn_address_book);
        btn_set = (Button) findViewById(R.id.btn_set);
        btn_conversation.setOnClickListener(this);
        btn_address_book.setOnClickListener(this);
        btn_set.setOnClickListener(this);
        adapter = new PersonAdapter(this, user);
        listview.setAdapter(adapter);
    }

    //点击事件
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_conversation:
                startActivity(new Intent(this, ConversationActivity.class));
                break;
            case R.id.btn_address_book:

                break;
            case R.id.btn_set:
                startActivity(new Intent(this, SetActivity.class));
                break;

        }
    }

}
