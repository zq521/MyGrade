package com.example.zhaoqiang.mygrade.ada;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.zhaoqiang.mygrade.R;
import com.example.zhaoqiang.mygrade.help.User;

import java.util.List;

/**
 * Created by 轩韩子 on 2017/3/23.
 * at 18:27
 */

public class PersonAdapter extends BaseAdapter {
    private Context context;
    private List<User> users;
    private LayoutInflater inflater;

    public PersonAdapter(Context context, List<User> users) {

        this.context = context;
        this.users = users;
    }

    @Override
    public int getCount() {
        return users.size();
    }

    @Override
    public User getItem(int position) {
        return users.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_main, parent, false);

        }
        TextView tv_name = (TextView) convertView.findViewById(R.id.tv_name);
        tv_name.setText(getItem(position).getName());
        return convertView;
    }


}
