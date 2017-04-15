package com.example.zhaoqiang.mygrade.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.zhaoqiang.mygrade.R;
import com.hyphenate.EMContactListener;
import com.hyphenate.EMGroupChangeListener;
import com.hyphenate.chat.EMClient;

import java.util.ArrayList;

/**
 * Created by 轩韩子 on 2017/4/5.
 * at 13:54
 * 通讯录
 */

public class PersonFragment extends Fragment implements View.OnClickListener, EMContactListener, EMGroupChangeListener {
    private PersonListFragment personListFragment;
    private GroupListFragment groupListFragment;
    private FragmentPagerAdapter fragmentPagerAdapter;
    private TextView tv_penson, tv_group;
    private ViewPager add_pager;
    private ArrayList<Fragment> list = new ArrayList<Fragment>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.frag_person, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //监听好友状态事件
        EMClient.getInstance().contactManager().setContactListener(this);
        //监听群组状态事件
        EMClient.getInstance().groupManager().addGroupChangeListener(this);
        // 初始化控件
        init(view);

    }

    /*
    初始化控件
     */
    public void init(View view) {
        tv_penson = (TextView) view.findViewById(R.id.tv_penson);
        tv_group = (TextView) view.findViewById(R.id.tv_group);
        add_pager = (ViewPager) view.findViewById(R.id.add_pager);
        tv_penson.setOnClickListener(this);
        tv_group.setOnClickListener(this);
        //加载fragment
        addFragment();
        //设置滑动适配器
        pagerAdapter();
    }

    /*
    加载fragment
     */
    private void addFragment() {
        personListFragment = new PersonListFragment();
        groupListFragment = new GroupListFragment();

        list.add(personListFragment);
        list.add(groupListFragment);
    }

    /*
    设置滑动适配器
     */
    private void pagerAdapter() {
        fragmentPagerAdapter = new FragmentPagerAdapter(getChildFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return list.get(position);
            }

            @Override
            public int getCount() {
                return list.size();
            }

        };
        add_pager.setAdapter(fragmentPagerAdapter);
        add_pager.setCurrentItem(0);
    }

    /*
    重置所有文本的选中状态
     */
    private void setSelected() {
        tv_penson.setSelected(false);
        tv_group.setSelected(false);
    }

    /*
    点击事件
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_penson:
                setSelected();
                tv_penson.setSelected(true);
                add_pager.setCurrentItem(0);
                break;
            case R.id.tv_group:
                setSelected();
                tv_group.setSelected(true);
                add_pager.setCurrentItem(1);
                break;
        }
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
