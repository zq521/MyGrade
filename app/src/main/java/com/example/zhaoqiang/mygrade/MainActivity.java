package com.example.zhaoqiang.mygrade;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.zhaoqiang.mygrade.fragment.ConversationFragment;
import com.example.zhaoqiang.mygrade.fragment.PersonListFragment;
import com.example.zhaoqiang.mygrade.fragment.SetFragment;
import com.example.zhaoqiang.mygrade.magnager.MessageManager;
import com.hyphenate.EMContactListener;
import com.hyphenate.EMGroupChangeListener;
import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.exceptions.HyphenateException;

import java.util.List;

/**
 * Created by 轩韩子 on 2017/3/23.
 * at 21:25
 * 联系人页面
 */

public class MainActivity extends AppCompatActivity implements View.OnClickListener, EMMessageListener, EMContactListener, EMGroupChangeListener{
    private Button btn_conversation, btn_address_book, btn_set;
    FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        //添加接受消息监听
        EMClient.getInstance().chatManager().addMessageListener(this);
        //监听好友状态事件
        EMClient.getInstance().contactManager().setContactListener(this);
        //监听群组状态事件
        EMClient.getInstance().groupManager().addGroupChangeListener(this);
        init();

    }

    /*
    初始化控件
     */
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

    /*
     点击事件
     */
    @Override
    public void onClick(View v) {
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.zoom_enter,R.anim.zoom_exit);
        try {
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.geren);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            //就是你自己定义的actionbar样式
//            getSupportActionBar().setCustomView(R.layout.act_chat);
//            getSupportActionBar().getDisplayOptions(DISPLAY_SERVICE);


        } catch (Exception e) {
            e.printStackTrace();
        }
        switch (v.getId()) {
            case R.id.btn_conversation:
                getSupportActionBar().setTitle("会话列表");
                setSelected();
                btn_conversation.setSelected(true);
                fragmentTransaction.replace(R.id.fragment, new ConversationFragment());
                break;
            case R.id.btn_address_book:
                getSupportActionBar().setTitle("通讯录");
                setSelected();
                btn_address_book.setSelected(true);
                fragmentTransaction.replace(R.id.fragment, new PersonListFragment());
                break;
            case R.id.btn_set:
                getSupportActionBar().setTitle("设置");
                setSelected();
                btn_set.setSelected(true);
                fragmentTransaction.replace(R.id.fragment, new SetFragment());
                break;

        }
        //从返回栈删除
        //fragmentManager.popBackStack("需要删除的名字",FragmentManager.POP_BACK_STACK_INCLUSIVE);
        //将当前的事务添加到了回退栈
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

    }

    /*
    标题点击事件
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:

                break;
            case R.id.add_penson:
                add();
                ab.setPositiveButton("加人", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //申请联系人
                        addPerson();
                    }
                }).show();
                break;
            case R.id.add_group:
                add();
                ab.setPositiveButton("加群", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //申请加群
                        addGroup();
                    }
                }).show();
                break;

        }

        return super.onOptionsItemSelected(item);
    }

    //提示框内的控件
    EditText add_et_number, add_et_reason;
    String toAddUsername;
    String toAddGroup;
    String reason;
    AlertDialog.Builder ab;

    /*
    添加提示框
     */
    private void add() {
        ab = new AlertDialog.Builder(this);
        View mView = LayoutInflater.from(this).inflate(R.layout.add_dialog, null);
        add_et_number = (EditText) mView.findViewById(R.id.add_et_number);
        add_et_reason = (EditText) mView.findViewById(R.id.add_et_reason);
        //得到输入框内容
        reason = add_et_reason.getText().toString();
        ab.setTitle("添加")
                .setView(mView)
                .setNeutralButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(MainActivity.this, "取消~", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    /*
  申请联系人
   */
    private void addPerson() {
        toAddUsername = add_et_number.getText().toString();
        if (toAddUsername.isEmpty()) {
            Toast.makeText(MainActivity.this, "不能为空", Toast.LENGTH_SHORT).show();
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    //参数为要添加的好友的username和添加理由
                    EMClient.getInstance().contactManager().addContact(toAddUsername, reason);
                } catch (HyphenateException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        Toast.makeText(MainActivity.this, "申请成功", Toast.LENGTH_SHORT).show();


    }

    /*
        申请加群
         */
    private void addGroup() {
        toAddGroup = add_et_number.getText().toString();
        if (toAddGroup.isEmpty()) {
            Toast.makeText(MainActivity.this, "不能为空", Toast.LENGTH_SHORT).show();
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    EMClient.getInstance().groupManager().joinGroup(toAddGroup);
                    //需要申请和验证才能加入的，即group.isMembersOnly()为true，调用下面方法
                    EMClient.getInstance().groupManager().applyJoinToGroup(toAddUsername, reason);

                } catch (HyphenateException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        Toast.makeText(MainActivity.this, "申请成功", Toast.LENGTH_SHORT).show();


    }

    /*
        标题点击事件
         */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = new MenuInflater(this);
        menuInflater.inflate(R.menu.add_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    /*
        重置所有按钮的选中状态
         */
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

    /*
   好友监听
    */
    @Override
    public void onContactAgreed(String username) {
        //好友请求被同意
    }

    @Override
    public void onContactRefused(String username) {
        //好友请求被拒绝
    }

    @Override
    public void onContactInvited(String username, String reason) {
        //收到好友邀请
    }

    @Override
    public void onContactDeleted(String username) {
        //被删除时回调此方法
    }


    @Override
    public void onContactAdded(String username) {
        //增加了联系人时回调此方法
    }

    /*
    群组监听
     */
    @Override
    public void onUserRemoved(String groupId, String groupName) {
        //当前用户被管理员移除出群组
    }

    @Override
    public void onGroupDestroyed(String s, String s1) {

    }

    @Override
    public void onAutoAcceptInvitationFromGroup(String s, String s1, String s2) {

    }

    @Override
    public void onInvitationReceived(String groupId, String groupName, String inviter, String reason) {
        //收到加入群组的邀请
    }

    @Override
    public void onInvitationDeclined(String groupId, String invitee, String reason) {
        //群组邀请被拒绝
    }

    @Override
    public void onApplicationReceived(String groupId, String groupName, String applyer, String reason) {
        //收到加群申请
    }

    @Override
    public void onApplicationAccept(String groupId, String groupName, String accepter) {
        //加群申请被同意
    }

    @Override
    public void onApplicationDeclined(String groupId, String groupName, String decliner, String reason) {
        // 加群申请被拒绝
    }

    @Override
    public void onInvitationAccepted(String s, String s1, String s2) {

    }
}