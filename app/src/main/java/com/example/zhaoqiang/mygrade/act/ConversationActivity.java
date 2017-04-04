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
import android.widget.Toast;

import com.example.zhaoqiang.mygrade.MainActivity;
import com.example.zhaoqiang.mygrade.R;
import com.example.zhaoqiang.mygrade.ada.ConversationAdapater;
import com.example.zhaoqiang.mygrade.callback.CallListener;
import com.example.zhaoqiang.mygrade.help.Refresh;
import com.hyphenate.EMConnectionListener;
import com.hyphenate.EMError;
import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.util.NetUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by 轩韩子 on 2017/3/23.
 * at 14:43
 * 消息列表页
 */

public class ConversationActivity extends AppCompatActivity implements View.OnClickListener,CallListener {
    private View view;
    private Refresh swipe;
    private ListView listview;
    private ConversationAdapater conAda;
    private ArrayList<EMConversation> list = new ArrayList<>();
    private Button btn_conversation, btn_address_book, btn_set;
    private EMMessageListener msgListener;
    Map<String, EMConversation> conversations;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_conversation);
        //初始化控件
        init();
        //接收消息
        getMessage();
        //注册连接状态监听
        EMClient.getInstance().addConnectionListener(new MyConnectionListener());
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
        //获取聊天
        getData();
        //实例化适配器
        conAda = new ConversationAdapater(this, list);
        conAda.setCallListener(this);
        //加载foot view布局
        view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.progress_up_item, null, false);
        //设置适配器
        listview.setAdapter(conAda);
        //设置上拉和下拉刷新
        setUpAndDown();
        conAda.notifyDataSetChanged();
    }

    private void getData() {
        conversations = EMClient.getInstance().chatManager().getAllConversations();

        for (EMConversation em : conversations.values()
                ) {
            list.add(em);

        }
    }


    /**
     * 设置上拉和下拉刷新
     */
    private void setUpAndDown() {
        //设置刷新旋转颜色
        swipe.setColorSchemeResources(R.color.colorAccent, R.color.colorPrimary);
        //设置下拉刷新监听
        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        conAda.notifyDataSetChanged();
                        //关闭下拉进度
                        swipe.setRefreshing(false);
                    }
                }).start();
                Toast.makeText(ConversationActivity.this,"刷新成功",Toast.LENGTH_SHORT).show();

            }
        });

//        //设置上拉刷新监听
//        swipe.setSetLoadlistener(new Refresh.loadListener() {
//            @Override
//            public void load() {
//                swipe.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        //刷新listview
//                        conAda.notifyDataSetChanged();
//                        //关闭加载进度
//                        swipe.setLoadView(false);
//                    }
//                }, 1500);
//
//            }
//
//            @Override
//            public void setFootView(boolean loading) {
//                // 根据loading设置footview（底部加载更多控件）
//                if (loading) {
//                    listview.removeFooterView(view);
//                    listview.addFooterView(view);
//                    //将listview滑到最底部
//                    listview.setSelection(conAda.getCount());
//                } else {
//                    listview.removeFooterView(view);
//                }
//            }
//        });
    }

    /**
     * 点击事件
     */
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

    /**
     * 接收消息
     */
    private void getMessage() {
        EMClient.getInstance().chatManager().addMessageListener(msgListener);
        msgListener = new EMMessageListener() {

            @Override
            public void onMessageReceived(List<EMMessage> messages) {
                Toast.makeText(ConversationActivity.this,"收到消息",Toast.LENGTH_SHORT).show();
                conAda.notifyDataSetChanged();
                //收到消息
            }

            @Override
            public void onCmdMessageReceived(List<EMMessage> messages) {
                //收到透传消息
            }

            @Override
            public void onMessageReadAckReceived(List<EMMessage> list) {
                //收到已读回执
            }

            @Override
            public void onMessageDeliveryAckReceived(List<EMMessage> list) {
                //收到已送达回执
            }

            @Override
            public void onMessageChanged(EMMessage message, Object change) {
                //消息状态变动
            }
        };
    }

    @Override
    public void Click(int id) {
        EMConversation messages = list.get(id);
        //删除和某个user会话，如果需要保留聊天记录，传false
        EMClient.getInstance().chatManager().deleteConversation(messages.getUserName(), false);
        list.remove(id);
        conAda.notifyDataSetChanged();
    }

    @Override
    public void ItemClick(int id) {
        startActivity(new Intent(this,ChatActivity.class).putExtra("username",list.get(id).getUserName()));
    }




    /**
     * 实现ConnectionListener接口
     */
    public class MyConnectionListener implements EMConnectionListener {
        @Override
        public void onConnected() {

        }
   //账号发生异常的提醒
        @Override
        public void onDisconnected(final int error) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (error == EMError.USER_REMOVED) {
                        Toast.makeText(ConversationActivity.this, "显示帐号已经被移除", Toast.LENGTH_SHORT).show();

                    } else {
                        if (error == EMError.USER_LOGIN_ANOTHER_DEVICE) {
                            new SetActivity().logout();
                            Toast.makeText(ConversationActivity.this, "显示帐号在其他设备登录", Toast.LENGTH_SHORT).show();
                        } else if (NetUtils.hasNetwork(ConversationActivity.this)) {
                            Toast.makeText(ConversationActivity.this, "连接不到聊天服务器", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(ConversationActivity.this, "当前网络不可用，请检查网络设置", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            });
        }
    }
}
