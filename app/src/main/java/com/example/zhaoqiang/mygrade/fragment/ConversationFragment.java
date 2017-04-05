package com.example.zhaoqiang.mygrade.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.example.zhaoqiang.mygrade.R;
import com.example.zhaoqiang.mygrade.act.ChatActivity;
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
 * Created by 轩韩子 on 2017/4/5.
 * at 11:38
 */

public class ConversationFragment extends Fragment implements CallListener {
    private View views;
    private Refresh swipe;
    private ListView listview;
    private ConversationAdapater conAda;
    private ArrayList<EMConversation> list = new ArrayList<>();
    private EMMessageListener msgListener;
    Map<String, EMConversation> conversations;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.frag_conversation, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //初始化控件
        init(view);
        //接收消息
        getMessage();
        //注册连接状态监听
        EMClient.getInstance().addConnectionListener(new MyConnectionListener());
    }

    public void init(View view) {
        //获取控件
        swipe = (Refresh) view.findViewById(R.id.swipe);
        listview = (ListView) view.findViewById(R.id.listview_con);

        //获取聊天
        getData();

        //实例化适配器
        conAda = new ConversationAdapater(getActivity(), list);
        conAda.setCallListener(this);

        //加载foot view布局
        views = LayoutInflater.from(getActivity()).inflate(R.layout.progress_up_item, null, false);
        //设置适配器
        listview.setAdapter(conAda);
        //设置上拉和下拉刷新
        setUpAndDown();
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
                Toast.makeText(getActivity(), "刷新成功", Toast.LENGTH_SHORT).show();

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
     * 接收消息
     */
    private void getMessage() {
        EMClient.getInstance().chatManager().addMessageListener(msgListener);
        msgListener = new EMMessageListener() {

            @Override
            public void onMessageReceived(List<EMMessage> messages) {
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
        conAda.update(list);
    }

    @Override
    public void ItemClick(int id) {
        startActivity(new Intent(getActivity(), ChatActivity.class).putExtra("username", list.get(id).getUserName()));
    }

    @Override
    public void refChatList() {
        getData();

    }


    /**
     * 实现ConnectionListener接口
     */
    public class MyConnectionListener implements EMConnectionListener {
        @Override
        public void onConnected() {

        }

        //注册一个监听连接状态的listener
        //账号发生异常的提醒
        @Override
        public void onDisconnected(final int error) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    if (error == EMError.USER_REMOVED) {
                        Toast.makeText(getActivity(), "显示帐号已经被移除", Toast.LENGTH_SHORT).show();

                    } else {
                        if (error == EMError.USER_LOGIN_ANOTHER_DEVICE) {
                            //new SetActivity().logout();
                            Toast.makeText(getActivity(), "显示帐号在其他设备登录", Toast.LENGTH_SHORT).show();
                        } else if (NetUtils.hasNetwork(getActivity())) {
                            Toast.makeText(getActivity(), "连接不到聊天服务器", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getActivity(), "当前网络不可用，请检查网络设置", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            });
        }
    }


}
