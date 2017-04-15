package com.example.zhaoqiang.mygrade.ada;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * Created by 轩韩子 on 2017/4/12.
 * at 21:51
 */

public class MyPagerAdapter extends FragmentPagerAdapter {

    private ArrayList<Fragment> list = new ArrayList<Fragment>();

    public MyPagerAdapter(FragmentManager fm,ArrayList<Fragment> list) {
        super(fm);
        this.list=list;
    }


    @Override
    public Fragment getItem(int position) {
        return list.get(position);
    }

    @Override
    public int getCount() {
        return list.size();
    }


}
