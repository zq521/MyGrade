package com.example.zhaoqiang.mygrade.act;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.zhaoqiang.mygrade.R;
import com.example.zhaoqiang.mygrade.fragment.AddGroupFragment;
import com.example.zhaoqiang.mygrade.fragment.AddPersonFragment;

import java.util.ArrayList;

/**
 * Created by 轩韩子 on 2017/3/24.
 * at 11:24
 * 加好友主页
 */

public class AddConActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView add_penson, add_group;
    private Button btn_add;
    private EditText et_username;
    private AddPersonFragment addPersonFragment;
    private AddGroupFragment addGroupFragment;
    private FragmentPagerAdapter fragmentPagerAdapter;
    private ViewPager add_pager;
    private ArrayList<Fragment> list = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_add);
        init();
        addFragment();
        pager();
    }

    private void init() {
        add_penson = (TextView) findViewById(R.id.add_penson);
        add_group = (TextView) findViewById(R.id.add_group);
        btn_add = (Button) findViewById(R.id.btn_add);
        add_pager = (ViewPager) findViewById(R.id.add_pager);
        et_username = (EditText) findViewById(R.id.et_username);
        add_penson.setOnClickListener(this);
        add_group.setOnClickListener(this);
        btn_add.setOnClickListener(this);

    }

    private void addFragment() {
        addPersonFragment = new AddPersonFragment();
        addGroupFragment = new AddGroupFragment();

        list.add(addPersonFragment);
        list.add(addGroupFragment);

    }

    private void pager() {
        fragmentPagerAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return list.get(position);
            }

            @Override
            public int getCount() {
                return list.size();
            }
        };
        add_pager.setOffscreenPageLimit(2);
        add_pager.setAdapter(fragmentPagerAdapter);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add_penson:

                break;
            case R.id.add_group:

                break;
            case R.id.btn_add:

                break;
        }


    }
}