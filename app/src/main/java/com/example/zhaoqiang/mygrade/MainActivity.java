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
import com.example.zhaoqiang.mygrade.help.MessageManager;
import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;

import java.util.List;

/**
 * Created by 轩韩子 on 2017/3/23.
 * at 21:25
 * 联系人页面
 */

public class MainActivity extends AppCompatActivity implements View.OnClickListener,EMMessageListener {
    private Button btn_conversation, btn_address_book, btn_set;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        //添加接受消息监听
        EMClient.getInstance()
                .chatManager()
                .addMessageListener(this);
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
        btn_conversation.performClick();//模拟一次点击，既进去后选择第一项

    }

    /**
     * 点击事件
     */
    @Override
    public void onClick(View v) {
         fragmentTransaction = fragmentManager.beginTransaction();
        switch (v.getId()) {
            case R.id.btn_conversation:
                setSelected();
                btn_conversation.setSelected(true);
                fragmentTransaction.replace(R.id.fragment, new ConversationFragment());
                break;
            case R.id.btn_address_book:
                setSelected();
                btn_address_book.setSelected(true);
                fragmentTransaction.replace(R.id.fragment, new PersonFragment());
                break;
            case R.id.btn_set:
                setSelected();
                btn_set.setSelected(true);
                fragmentTransaction.replace(R.id.fragment, new SetFragment());
                break;

        }
        //将当前的事务添加到了回退栈
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

    }



    //重置所有文本的选中状态
    private void setSelected() {
        btn_address_book.setSelected(false);
        btn_conversation.setSelected(false);
        btn_set.setSelected(false);

    }

    @Override
    public void onMessageReceived(List<EMMessage> list) {
        //调用刷新消息列表的方法
        MessageManager.getInsatance().getMessageListener().refChatList();
    }

    @Override
    public void onCmdMessageReceived(List<EMMessage> list) {
        //收到透传消息
    }

    @Override
    public void onMessageReadAckReceived(List<EMMessage> list) {
        //收到已送达回执
    }

    @Override
    public void onMessageDeliveryAckReceived(List<EMMessage> list) {
         //收到已送达回执
    }

    @Override
    public void onMessageChanged(EMMessage emMessage, Object o) {
        //消息状态变动
    }
}