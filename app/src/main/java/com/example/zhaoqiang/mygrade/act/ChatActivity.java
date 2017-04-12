package com.example.zhaoqiang.mygrade.act;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zhaoqiang.mygrade.R;
import com.example.zhaoqiang.mygrade.ada.ChatAdapter;
import com.example.zhaoqiang.mygrade.fragment.ImageFragment;
import com.example.zhaoqiang.mygrade.help.MessageManager;
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
 * 聊天详情页面
 */

public class ChatActivity extends AppCompatActivity implements View.OnClickListener, EMMessageListener {
    private String text;
    private String userName;
    private Button chat_btn_send;
    private ListView chat_listView;
    private ChatAdapter chatAdapter;
    private EditText chat_et_content;
    private ImageButton chat_btn_jiahao;
    private TextView chat_tv_toUsername;
    private EMConversation emConversation;
    ImageFragment imageFragment;
    FragmentTransaction transaction;
    FragmentManager fragmentManager;
    private ArrayList<EMMessage> sendMessagelist = new ArrayList<>();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_chat);
        //接收消息监听
        EMClient.getInstance().chatManager().addMessageListener(this);
        //初始化控件
        init();
        InitFragment();
    }


    private void InitFragment() {
        fragmentManager = getSupportFragmentManager();
        imageFragment = new ImageFragment();

    }


    /**
     * 初始化控件
     */
    private void init() {
        //接收跳转过来的数据
        userName = getIntent().getStringExtra("userName");
        text = getIntent().getStringExtra("text");
        chat_btn_send = (Button) findViewById(R.id.chat_btn_send);
        chat_listView = (ListView) findViewById(R.id.chat_listView);
        chat_et_content = (EditText) findViewById(R.id.chat_et_content);
        chat_btn_jiahao = (ImageButton) findViewById(R.id.chat_btn_jiahao);
        chat_tv_toUsername = (TextView) findViewById(R.id.chat_tv_toUsername);
        chat_btn_send.setOnClickListener(this);
        chat_btn_jiahao.setOnClickListener(this);
        //如果text不能空，设置数据到输入框
        if (!TextUtils.isEmpty(text)) {
            chat_et_content.setText(text);
            chat_et_content.setSelection(chat_et_content.getText().length());
        }
        //为输入框的文字添加监听事件
        chat_et_content.addTextChangedListener(new TextWatcher() {
            //旧的文本长度处理监听
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            //调用此方法,通知刚刚取代旧的文本长度
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            //通知你在某个地方文本已经改变
            @Override
            public void afterTextChanged(Editable s) {
                text = s.toString();
            }
        });

        //设置与某某聊天名字
        chat_tv_toUsername.setText(userName);
        //获取与某个人的所有会话
        emConversation = EMClient.getInstance().chatManager().getConversation(userName);
        //清空所有会话
        sendMessagelist.clear();
        //设置数据源
        sendMessagelist = (ArrayList<EMMessage>) emConversation.getAllMessages();
        chatAdapter = new ChatAdapter(this, sendMessagelist);
        chat_listView.setAdapter(chatAdapter);
    }

    /**
     * 返回键传递数据，返回结果
     */
    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putExtra("text", text);
        intent.putExtra("username", userName);
        setResult(RESULT_OK, intent);
        super.onBackPressed();
    }

    /**
     * 点击事件
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.chat_btn_send:
                //发送消息
                send();
                chat_et_content.setText("");
                text = "";
                chatAdapter.notifyDataSetChanged();
                break;
            case R.id.chat_btn_jiahao:
                //弹出菜单
                showPopupMenu(chat_btn_jiahao);
        }
    }


    /**
     * 发送消息
     */
    public void send() {
        String send = chat_et_content.getText().toString();
        if (send.equals("")) {
            Toast.makeText(this, "不能发送空白信息", Toast.LENGTH_SHORT).show();
        } else {
            //创建一条文本消息
            EMMessage message = EMMessage.createTxtSendMessage(send, userName);
            //设置文本类型
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
            sendMessagelist.add(message);
            //调用刷新消息列表的方法
            MessageManager.getInsatance().getMessageListener().refChatList();

        }
    }

    /**
     * 弹出菜单
     */
    private void showPopupMenu(View view) {
        PopupMenu popupMenu = new PopupMenu(this, view);
        // menu布局
        popupMenu.getMenuInflater().inflate(R.menu.main, popupMenu.getMenu());
        //设置监听

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_image:
                       if (imageFragment.isAdded()){
                           transaction=fragmentManager.beginTransaction();
                           transaction.remove(imageFragment);
                           transaction.commit();
                       } else {
                           transaction = fragmentManager.beginTransaction();
                           transaction.replace(R.id.message_bottom_fragment_lay, imageFragment);
                           transaction.commit();
                       }

                        break;
                    case R.id.menu_video:

                        break;
                    case R.id.menu_speak:

                        break;
                }

                Toast.makeText(ChatActivity.this, item.getTitle(), Toast.LENGTH_SHORT).show();
                return false;
            }

        });
        popupMenu.setOnDismissListener(new PopupMenu.OnDismissListener() {
            @Override
            public void onDismiss(PopupMenu menu) {
                Toast.makeText(ChatActivity.this, "关闭", Toast.LENGTH_SHORT).show();
            }
        });
        //显示
        popupMenu.show();
    }


    /**
     * 销毁会话
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        //移除会话
        EMClient.getInstance().chatManager().removeMessageListener(this);
    }

    /**
     * 消息收到
     *
     * @param list
     */
    @Override
    public void onMessageReceived(List<EMMessage> list) {
        this.sendMessagelist.addAll(list);
        chatAdapter.notifyDataSetChanged();
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
