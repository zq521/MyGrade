package com.example.zhaoqiang.mygrade.ada;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.zhaoqiang.mygrade.R;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMTextMessageBody;

import java.util.ArrayList;

/**
 * Created by 轩韩子 on 2017/3/31.
 * at 09:06
 */

public class ChatAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<EMMessage> sendMessage=new ArrayList<>();

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
    ViewHolder holder;
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
       holder=new ViewHolder();
       if (convertView==null){
           convertView= LayoutInflater.from(context).inflate(R.layout.item_chat,parent,false);
           holder.chat_other_message= (TextView) convertView.findViewById(R.id.chat_other_message);
           holder.chat_other_image= (ImageView) convertView.findViewById(R.id.chat_other_image);
           holder.chat_my_message= (TextView) convertView.findViewById(R.id.chat_my_message);
           holder.chat_my_image= (ImageView) convertView.findViewById(R.id.chat_my_image);
           convertView.setTag(holder);
       }else {
           holder= (ViewHolder) convertView.getTag();
       }
        EMMessage emMessage=getItem(position);
        EMMessage.Type type=emMessage.getType();

        switch (type){
            case TXT:
                EMTextMessageBody body= (EMTextMessageBody) emMessage.getBody();
                if (emMessage.getFrom().equals(emMessage.getUserName())){
                    holder.chat_other_message.setText(body.getMessage());
                    holder.chat_other_message.setVisibility(View.VISIBLE);
                    holder.chat_other_image.setVisibility(View.VISIBLE);
                }else {
                    holder.chat_my_message.setText(body.getMessage());
                    holder.chat_my_message.setVisibility(View.VISIBLE);
                    holder.chat_my_image.setVisibility(View.VISIBLE);
                }
                break;
        }


        return convertView;
    }


    class  ViewHolder{
        TextView chat_other_message,chat_my_message;
        ImageView chat_other_image,chat_my_image;


    }
}
