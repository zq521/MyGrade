package com.example.zhaoqiang.mygrade.ada;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.zhaoqiang.mygrade.R;
import com.example.zhaoqiang.mygrade.help.EaseUser;
import com.mcxtzhang.swipemenulib.SwipeMenuLayout;

import java.util.ArrayList;

/**
 * Created by 轩韩子 on 2017/3/23.
 * at 18:27
 */

public class PersonAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<String> list = new ArrayList<>();

    public PersonAdapter(Context context,ArrayList<String> list) {
        this.context = context;
        this.list = list;

    }

    @Override
    public int getCount() {

        return list.size();
    }

    @Override
    public EaseUser getItem(int position) {

        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_person, parent, false);
            holder = new ViewHolder();
            holder.main_tv_name = (TextView) convertView.findViewById(R.id.main_tv_name);
            holder.main_iv_avatar = (ImageView) convertView.findViewById(R.id.main_iv_avatar);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        //设置联系人信息
        holder.main_tv_name.setText(list.get(position));

       // 弹出提示框（修改备注)
        holder.main_tv_name.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                return false;
            }
        });
        //删除该列
        final View finalConvertView = convertView;
        convertView.findViewById(R.id.main_btn_remove).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list.remove(position);
                notifyDataSetChanged();
                ((SwipeMenuLayout) finalConvertView).quickClose();

            }
        });
        return convertView;
    }

    class ViewHolder {
        TextView main_tv_name;
        ImageView main_iv_avatar;

    }
}


