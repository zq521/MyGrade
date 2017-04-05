package com.example.zhaoqiang.mygrade;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.zhaoqiang.mygrade.fragment.ConversationFragment;
import com.example.zhaoqiang.mygrade.fragment.PersonFragment;
import com.example.zhaoqiang.mygrade.fragment.SetFragment;

/**
 * Created by 轩韩子 on 2017/3/23.
 * at 21:25
 * 联系人页面
 */

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btn_conversation, btn_address_book, btn_set;
    private ConversationFragment conversationFragment;
    private PersonFragment personFragment;
    private SetFragment setFragment;
    FragmentManager fragmentManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        init();

    }

    //初始化控件
    public void init() {
        btn_conversation = (Button) findViewById(R.id.btn_conversation);
        btn_address_book = (Button) findViewById(R.id.btn_address_book);
        btn_set = (Button) findViewById(R.id.btn_set);
        btn_conversation.setOnClickListener(this);
        btn_address_book.setOnClickListener(this);
        btn_set.setOnClickListener(this);
        fragmentManager = getSupportFragmentManager();
        btn_address_book.performClick();//模拟一次点击，既进去后选择第一项

    }

    /**
     * 点击事件
     */
    @Override
    public void onClick(View v) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        switch (v.getId()) {
            case R.id.btn_address_book:
                fragmentTransaction.replace(R.id.fragment, new PersonFragment());
                break;
            case R.id.btn_conversation:
                    fragmentTransaction.replace(R.id.fragment, new ConversationFragment());
                break;
            case R.id.btn_set:
                    fragmentTransaction.replace(R.id.fragment, new SetFragment());

                break;

        }
        fragmentTransaction.commit();
        fragmentTransaction.addToBackStack(null);
    }

}