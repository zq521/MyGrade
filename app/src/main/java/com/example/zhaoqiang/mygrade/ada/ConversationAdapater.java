package com.example.zhaoqiang.mygrade.ada;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.zhaoqiang.mygrade.R;
import com.example.zhaoqiang.mygrade.callback.CallListener;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMTextMessageBody;
import com.mcxtzhang.swipemenulib.SwipeMenuLayout;

import java.util.ArrayList;


/**
 * Created by 轩韩子 on 2017/3/26.
 * at 18:40
 * 消息列表适配器
 */

public class ConversationAdapater extends BaseAdapter {
    private Context context;
    private CallListener callListener;
    private ArrayList<EMConversation> list;





    public void setCallListener(CallListener callListener) {
        this.callListener = callListener;
    }


    public ConversationAdapater(Context context, ArrayList<EMConversation> list) {
        this.context = context;
        this.list = list;

    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public EMConversation getItem(int position) {
        return list.get(position);
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
            holder.chat_item=convertView.findViewById(R.id.chat_item);
            //将Holder存储到convertView中
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        //获取当前item的数据内容
        EMConversation emConversation = getItem(position);
        //从当前会话对象中 获取，最后一条消息的对象
        EMMessage latMessage = emConversation.getLastMessage();
        //从最后一次消息对象中，获取该消息的消息类型

        EMMessage.Type type = latMessage.getType();
        switch (type) {
            case TXT:
                //获取消息体并强转成文本消息类型体
                EMTextMessageBody textMessageBody = (EMTextMessageBody) latMessage.getBody();
                //从消息体中拿到消息内容 并 设置给 控件
                holder.con_tv_msg.setText(textMessageBody.getMessage());
                break;
            case IMAGE:
                holder.con_tv_msg.setText("[图片]");
                break;
            case VIDEO:
                holder.con_tv_msg.setText("[视频]");
                break;

        }
        //设置接收消息的名字
        holder.con_tv_name.setText(emConversation.getUserName());
        //设置消息数目
//        holder.unread_msg_number.setText(emConversation.getUnredMsgCount());
        //给名字设置点击事件

        holder.chat_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (callListener != null) {
                    callListener.ItemClick(position);
                }
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
                if (callListener != null) {
                    callListener.Click(position);
                }
            }
        });

        return convertView;
    }
    //内部类
    class ViewHolder {
        private View chat_item;
        private TextView con_tv_name, con_tv_msg, unread_msg_number;
        private ImageView con_image_per_mes;
    }

}
