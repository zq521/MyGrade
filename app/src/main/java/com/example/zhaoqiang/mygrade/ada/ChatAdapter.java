package com.example.zhaoqiang.mygrade.ada;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.zhaoqiang.mygrade.R;
import com.hyphenate.chat.EMImageMessageBody;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMTextMessageBody;

import java.util.ArrayList;

/**
 * Created by 轩韩子 on 2017/3/31.
 * at 09:06
 * 聊天详情页面适配器
 */

public class ChatAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<EMMessage> sendMessage = new ArrayList<>();

    public ChatAdapter(Context context, ArrayList<EMMessage> sendMessage) {
        this.context = context;
        this.sendMessage = sendMessage;
    }

    @Override
    public int getCount() {
        return sendMessage.size();
    }

    @Override
    public EMMessage getItem(int position) {
        return sendMessage.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.item_chat, parent, false);
            holder.chat_my_image = (ImageView) convertView.findViewById(R.id.chat_my_image);
            holder.chat_my_message = (TextView) convertView.findViewById(R.id.chat_my_message);
            holder.send_image= (ImageView) convertView.findViewById(R.id.send_image);

            holder.chat_other_image = (ImageView) convertView.findViewById(R.id.chat_other_image);
            holder.chat_other_message = (TextView) convertView.findViewById(R.id.chat_other_message);
            holder.get_image= (ImageView) convertView.findViewById(R.id.get_image);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        //获取会话
        EMMessage emMessage = getItem(position);

        EMMessage.Type type = emMessage.getType();
        switch (type) {
            case TXT:
                EMTextMessageBody body = (EMTextMessageBody) emMessage.getBody();
                if (emMessage.getFrom().equals(emMessage.getUserName())) {
                    holder.chat_other_message.setText(body.getMessage());
                    holder.chat_other_message.setVisibility(View.VISIBLE);
                    holder.chat_other_image.setVisibility(View.VISIBLE);
                } else {
                    holder.chat_my_message.setText(body.getMessage());
                    holder.chat_my_message.setVisibility(View.VISIBLE);
                    holder.chat_my_image.setVisibility(View.VISIBLE);
                }
                break;
            case IMAGE:
                EMImageMessageBody body1= (EMImageMessageBody) emMessage.getBody();
                if (emMessage.getFrom().equals(emMessage.getUserName())) {
                    Glide.with(context)
                            .load(body1.getThumbnailUrl())
                            .override(300,500)
                            .into(holder.get_image);
                    holder.get_image.setVisibility(View.VISIBLE);
                    holder.chat_other_image.setVisibility(View.VISIBLE);
                } else {
                    Glide.with(context)
                            .load(body1.getLocalUrl())
                            .into(holder.send_image);
                    holder.send_image.setVisibility(View.VISIBLE);
                    holder.chat_my_image.setVisibility(View.VISIBLE);

                }
                break;


        }
        return convertView;
    }


    class ViewHolder {
        TextView chat_other_message, chat_my_message;
        ImageView chat_other_image, chat_my_image,send_image,get_image;
    }
}
