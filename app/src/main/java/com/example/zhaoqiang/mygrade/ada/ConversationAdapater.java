package com.example.zhaoqiang.mygrade.ada;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zhaoqiang.mygrade.R;
import com.mcxtzhang.swipemenulib.SwipeMenuLayout;

import java.util.ArrayList;




/**
 * Created by 轩韩子 on 2017/3/26.
 * at 18:40
 * 消息列表适配器
 */

public class ConversationAdapater extends BaseAdapter {
    private Context context;
    private ArrayList<String> list = new ArrayList<>();

    public ConversationAdapater(Context context, ArrayList<String> list) {
        this.context = context;
        this.list = list;

    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public String getItem(int position) {
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
            convertView = LayoutInflater.from(context).inflate(R.layout.item_consersation, parent, false);
            holder = new ViewHolder();
            holder.con_tv_name = (TextView) convertView.findViewById(R.id.con_tv_name);
            holder.con_tv_msg = (TextView) convertView.findViewById(R.id.con_tv_msg);
            holder.unread_msg_number = (TextView) convertView.findViewById(R.id.unread_msg_number);
            holder.con_image_per_mes = (ImageView) convertView.findViewById(R.id.con_image_per_mes);
            //将Holder存储到convertView中
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.con_tv_name.setText(list.get(position));
        holder.con_tv_msg.setText(list.get(position));
        holder.unread_msg_number.setText("");
        //给名字设置点击事件
        holder.con_tv_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "你选择了该联系人", Toast.LENGTH_SHORT).show();
            }
        });
        //置顶该列
        final View finalConvertView = convertView;
        convertView.findViewById(R.id.con_btn_top).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list.add(0, list.get(position));
                // 置顶后list.size增加一 所以要position+1
                list.remove(position + 1);
                notifyDataSetChanged();
                ((SwipeMenuLayout) finalConvertView).quickClose();
            }
        });
        //删除该列
        convertView.findViewById(R.id.con_btn_remove).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list.remove(position);
                notifyDataSetChanged();
                ((SwipeMenuLayout) finalConvertView).quickClose();
            }
        });

        return convertView;
    }
    //内部类
    class ViewHolder {
        TextView con_tv_name, con_tv_msg, unread_msg_number;
        ImageView con_image_per_mes;
    }

}
