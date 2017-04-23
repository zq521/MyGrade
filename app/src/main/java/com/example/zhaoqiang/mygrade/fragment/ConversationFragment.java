package com.example.zhaoqiang.mygrade.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zhaoqiang.mygrade.R;
import com.example.zhaoqiang.mygrade.act.ChatActivity;
import com.example.zhaoqiang.mygrade.ada.ConversationAdapater;
import com.example.zhaoqiang.mygrade.callback.CallListener;
import com.example.zhaoqiang.mygrade.callback.MessageListListener;
import com.example.zhaoqiang.mygrade.help.MessageManager;
import com.example.zhaoqiang.mygrade.help.Refresh;
import com.hyphenate.EMConnectionListener;
import com.hyphenate.EMError;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.util.NetUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by 轩韩子 on 2017/4/5.
 * at 11:38
 * 消息列表
 */

public class ConversationFragment extends Fragment implements CallListener, MessageListListener {
    private View views;
    private Refresh swipe;
    private ListView listview;
    private ImageView ic_error_black;
    private TextView con_intent_text;
    private ConversationAdapater conversationAdapater;
    private static final int NUMBER = 112;
    private Map<String, EMConversation> conversations;
    private HashMap<String, String> textMap = new HashMap<>();
    private ArrayList<EMConversation> list = new ArrayList<>();

    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            conversationAdapater.notifyDataSetChanged();
        }

    };

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
        //注册 刷新消息列表 监听
        MessageManager.getInsatance().setMessageListener(this);
        //注册连接状态监听
        EMClient.getInstance().addConnectionListener(new MyConnectionListener());


    }

    /*
     * 初始化控件
     * @param view
     */
    public void init(View view) {
        swipe = (Refresh) view.findViewById(R.id.swipe);
        listview = (ListView) view.findViewById(R.id.listview_con);
        con_intent_text = (TextView) view.findViewById(R.id.con_intent_text);
        ic_error_black= (ImageView) view.findViewById(R.id.ic_error_black);
        //获取数据源
        getData();
        //实例化适配器
        conversationAdapater = new ConversationAdapater(getActivity(), list);
        conversationAdapater.setCallListener(this);
        //设置适配器
        listview.setAdapter(conversationAdapater);
        //加载foot view布局
        views = LayoutInflater.from(getActivity()).inflate(R.layout.progress_up_item, null, false);
        //设置上拉和下拉刷新
        conversationAdapater.notifyDataSetChanged();
        setUpAndDown();

    }

    /*
     * 获取数据源
     */
    private void getData() {
        conversations = EMClient.getInstance().chatManager().getAllConversations();
        list.clear();
        for (EMConversation em : conversations.values()) {
            list.add(em);
        }

    }

    /*
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
                        conversationAdapater.notifyDataSetChanged();
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

    /*
     * 实现侧滑点击删除
     */
    @Override
    public void Click(int id) {
        EMConversation messages = list.get(id);
        //删除和某个user会话，如果需要保留聊天记录，传false
        EMClient.getInstance().chatManager().deleteConversation(messages.getUserName(), false);
        list.remove(id);
        conversationAdapater.update(list);
    }

    /*
     * 跳转到聊天详情页
     */
    @Override
    public void ItemClick(int id) {
        intentToChat(list.get(id).getUserName());

    }

    /*
     * 携带数据跳转
     *
     * @param userName
     */
    public void intentToChat(String userName) {
        Intent intent = new Intent(getActivity(), ChatActivity.class);
        intent.putExtra("userName", userName);
        if (!TextUtils.isEmpty(textMap.get(userName)))
            intent.putExtra("text", textMap.get(userName));
        startActivityForResult(intent, NUMBER);
    }

    /*
     * 处理返回结果
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == -1) {
            switch (requestCode) {
                case NUMBER:
                    textMap.put(data.getStringExtra("username"), data.getStringExtra("text"));
                    try {
                        //如果内容为空，则移除存储内容
                        if (TextUtils.isEmpty(data.getStringExtra("text"))) {
                            textMap.remove(data.getStringExtra("username"));
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    //设置
                    setChatText(textMap);
                    break;
            }
        }
    }

    /*
     * 置顶
     */
    @Override
    public void top(int id) {
        list.add(0, list.get(id));
        // 置顶后listsize增加一 所以要position+1
        list.remove(id + 1);
        conversationAdapater.update(list);

    }

    /*
     * 刷新界面
     */
    @Override
    public void refChatList() {
        handler.sendMessage(new Message());

    }

    /*
     * 设置回调
     */

    public void setChatText(HashMap<String, String> textMap) {
        conversationAdapater.setTextMap(textMap);


    }

    /*
     * 实现ConnectionListener接口
     */
    public class MyConnectionListener implements EMConnectionListener {
        //注册一个监听连接状态的listener
        @Override
        public void onConnected() {

        }
        //账号发生异常的提醒
        @Override
        public void onDisconnected(final int error) {

            try {
                if (error == EMError.USER_REMOVED) {
                    con_intent_text.setText("帐号已经被移除");
                    con_intent_text.setVisibility(View.VISIBLE);
                    ic_error_black.setVisibility(View.VISIBLE);
                } else {
                    if (error == EMError.USER_LOGIN_ANOTHER_DEVICE) {
                        //new SetActivity().logout();
                        con_intent_text.setText("帐号在其他设备登录");
                        con_intent_text.setVisibility(View.VISIBLE);
                        ic_error_black.setVisibility(View.VISIBLE);
                    } else if (NetUtils.hasNetwork(getActivity())) {
                        con_intent_text.setText("连接不到聊天服务器");
                        con_intent_text.setVisibility(View.VISIBLE);
                        ic_error_black.setVisibility(View.VISIBLE);
                    } else {
                        con_intent_text.setText("当前网络不可用，请检查网络设置");
                        con_intent_text.setVisibility(View.VISIBLE);
                        ic_error_black.setVisibility(View.VISIBLE);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


}
