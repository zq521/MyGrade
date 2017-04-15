package com.example.zhaoqiang.mygrade.act;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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
import android.widget.Toast;

import com.example.zhaoqiang.mygrade.R;
import com.example.zhaoqiang.mygrade.ada.ChatAdapter;
import com.example.zhaoqiang.mygrade.callback.MessageListListener;
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

public class ChatActivity extends AppCompatActivity implements View.OnClickListener, EMMessageListener, MessageListListener {
    private String text;
    private String userName;
    private Button chat_btn_send;
    private ListView chat_listView;
    private ChatAdapter chatAdapter;
    private EditText chat_et_content;
    private ImageButton chat_btn_jiahao;
    private EMConversation emConversation;
    ImageFragment imageFragment;
    FragmentTransaction transaction;
    FragmentManager fragmentManager;
    private ArrayList<EMMessage> sendMessagelist = new ArrayList<>();

    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            chatAdapter.notifyDataSetChanged();
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_chat);

        //接收消息监听
        EMClient.getInstance().chatManager().addMessageListener(this);
        //初始化控件
        init();
        InitFragment();
        //设置系统自带标题栏内容
         setBar();
    }

    /*
    设置标题栏
     */
    private void setBar() {
        try {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(userName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /*
    设置标题栏点击事件监听
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    /*
    加载图库fragment
     */

    private void InitFragment() {
        fragmentManager = getSupportFragmentManager();
        imageFragment = new ImageFragment();

    }

    /*
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

        //获取与某个人的所有会话
        emConversation = EMClient.getInstance().chatManager().getConversation(userName);
        //清空所有会话
        sendMessagelist.clear();
        //设置数据源
        sendMessagelist = (ArrayList<EMMessage>) emConversation.getAllMessages();

        chatAdapter = new ChatAdapter(this, sendMessagelist);
        handler.sendMessage(new Message());
        chat_listView.setAdapter(chatAdapter);
    }

    /*
     返回键传递数据，返回结果
     */
    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putExtra("text", text);
        intent.putExtra("username", userName);
        setResult(RESULT_OK, intent);
        super.onBackPressed();
    }

    /*
     点击事件
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.chat_btn_send:
                //发送消息
                sendText();
                chat_et_content.setText("");
                text = "";
                chatAdapter.notifyDataSetChanged();
                break;
            case R.id.chat_btn_jiahao:
                //弹出菜单
                showPopupMenu(chat_btn_jiahao);
        }
    }


    /*
     发送文本消息
     */
    public void sendText() {
        String send = chat_et_content.getText().toString();
        if (send.equals("")) {
            Toast.makeText(this, "不能发送空白信息", Toast.LENGTH_SHORT).show();
        } else {
            //创建一条文本消息
            EMMessage message = EMMessage.createTxtSendMessage(send, userName);
            sendMessage(message);


        }
    }

    /*
     * 发送图片
     * @param imgPath  本地路径
     * @param isThumbnail  是否发送原图
     */
    public void sendImage(String imgPath, boolean isThumbnail) {
        //imagePath为图片本地路径，false为不发送原图（默认超过100k的图片会压缩后发给对方），需要发送原图传true
        EMMessage message = EMMessage.createImageSendMessage(imgPath, false, userName);
        //如果是群聊，设置chattype，默认是单聊
        // if (chatType == CHATTYPE_GROUP)
        sendMessage(message);

    }

    private void sendMessage(EMMessage message) {
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
        MessageManager.getInsatance().setMessageListener(this);
    }


    /*
     弹出菜单
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
                        if (imageFragment.isAdded()) {
                            transaction = fragmentManager.beginTransaction();
                            transaction.remove(imageFragment);
                            transaction.commit();
                            fragmentManager.popBackStackImmediate("myFragment", FragmentManager.POP_BACK_STACK_INCLUSIVE);
                        } else {
                            transaction = fragmentManager.beginTransaction();
                            transaction.replace(R.id.message_bottom_fragment_lay, imageFragment);
                            transaction.addToBackStack("myFragment");
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


    /*
     销毁会话
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        //移除会话
        EMClient.getInstance().chatManager().removeMessageListener(this);
    }

    /*
    消息收到
     * @param list
     */
    @Override
    public void onMessageReceived(List<EMMessage> list) {
        //收到消息
        this.sendMessagelist.addAll(list);
        handler.sendMessage(new Message());
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

     /*
     刷新列表
      */
    @Override
    public void refChatList() {
        chatAdapter.notifyDataSetChanged();
    }
}
