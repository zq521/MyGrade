package com.example.zhaoqiang.mygrade.act;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zhaoqiang.mygrade.R;
import com.example.zhaoqiang.mygrade.ada.ChatAdapter;
import com.hyphenate.EMCallBack;
import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMMessage;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 轩韩子 on 2017/3/29.
 * at 19:37
 * 聊天页面
 */

public class ChatActivity extends AppCompatActivity implements View.OnClickListener, EMMessageListener {
    private ArrayList<EMMessage> sendMessagelist = new ArrayList<>();
    private ListView chat_listView;
    private Button chat_btn_send;
    String username;
    private EditText chat_et_content;
    private TextView chat_tv_toUsername;
    private ChatAdapter chatAdapter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_chat);
        EMClient.getInstance().chatManager().addMessageListener(this);
        username = getIntent().getStringExtra("username");
        init();
    }

    private void init() {
        chat_btn_send = (Button) findViewById(R.id.chat_btn_send);
        chat_et_content = (EditText) findViewById(R.id.chat_et_content);
        chat_listView = (ListView) findViewById(R.id.chat_listView);
        chat_tv_toUsername= (TextView) findViewById(R.id.chat_tv_toUsername);
        chat_btn_send.setOnClickListener(this);
        //设置与某某聊天名字
        chat_tv_toUsername.setText(username);
        //清空所以会话
        sendMessagelist.clear();
        //获取与某个人的所以会话
        EMConversation emConversation = EMClient.getInstance().chatManager().getConversation(username);
        //设置数据源
         sendMessagelist = (ArrayList<EMMessage>) emConversation.getAllMessages();

        chatAdapter = new ChatAdapter(this, sendMessagelist);
        chat_listView.setAdapter(chatAdapter);
    }


    @Override
    public void onClick(View v) {
        String send = chat_et_content.getText().toString();
        if (send.equals("")) {
            Toast.makeText(this, "不能发送空白信息", Toast.LENGTH_SHORT).show();
        } else {
            //创建一条文本消息，content为消息文字内容，toChatUsername为对方用户或者群聊的id，后文皆是如此
            EMMessage message = EMMessage.createTxtSendMessage(send, username);
            message.setChatType(EMMessage.ChatType.Chat);
            message.setMessageStatusCallback(new EMCallBack() {
                @Override
                public void onSuccess() {
                    Log.e("message", "onSuccess");
                }

                @Override
                public void onError(int i, String s) {
                    Log.e("message", "onError=" + i + "   " + s);
                }

                @Override
                public void onProgress(int i, String s) {

                }
            });
            //发送消息
            EMClient.getInstance().chatManager().sendMessage(message);
            chat_et_content.setText("");
            sendMessagelist.add(message);
            chatAdapter.notifyDataSetChanged();
        }


    }

    @Override
    public void onMessageReceived(List<EMMessage> list) {
        this.sendMessagelist.addAll(sendMessagelist);
        chatAdapter.notifyDataSetChanged();

    }

    @Override
    public void onCmdMessageReceived(List<EMMessage> list) {

    }

    @Override
    public void onMessageReadAckReceived(List<EMMessage> list) {

    }

    @Override
    public void onMessageDeliveryAckReceived(List<EMMessage> list) {

    }

    @Override
    public void onMessageChanged(EMMessage emMessage, Object o) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //移除会话
        EMClient.getInstance().chatManager().removeMessageListener(this);
    }
}
