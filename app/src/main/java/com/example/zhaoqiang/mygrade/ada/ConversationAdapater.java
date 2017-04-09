package com.example.zhaoqiang.mygrade.ada;

import android.content.Context;
import android.text.TextUtils;
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

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;


/**
 * Created by 轩韩子 on 2017/3/26.
 * at 18:40
 * 消息列表适配器
 */

public class ConversationAdapater extends BaseAdapter {
    private Context context;
    private CallListener callListener;
    private ArrayList<EMConversation> list;
    private HashMap<String, String> textMap = new HashMap<>();

    /**
     * 设置接口
     */
    public void setCallListener(CallListener callListener) {
        this.callListener = callListener;
    }

    /**
     * 刷新adapter方法
     */
    public void update(ArrayList<EMConversation> list){
        this.list=list;
        notifyDataSetChanged();
    }

    /**
     * 构造方法
     */
    public ConversationAdapater(Context context, ArrayList<EMConversation> list) {
        this.context = context;
        this.list = list;

    }

    /**
     *
     * @param textMap
     */
    public void setTextMap(HashMap<String, String> textMap) {
        this.textMap = textMap;
        notifyDataSetChanged();
    }


    /**
     * 数目
     * @return
     */
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
            holder.msg_time = (TextView) convertView.findViewById(R.id.msg_time);
            holder.unread_msg_number = (TextView) convertView.findViewById(R.id.unread_msg_number);
            holder.con_image_per_mes = (ImageView) convertView.findViewById(R.id.con_image_per_mes);
            holder.chat_item = convertView.findViewById(R.id.chat_item);

            //将Holder存储到convertView中
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        //获取当前item的数据内容
        EMConversation emConversation = getItem(position);
        //从当前会话对象中 获取，最后一条消息的对象
        EMMessage latMessage = emConversation.getLastMessage();
        //判断该用户是否有草稿
        if (!TextUtils.isEmpty(textMap.get(emConversation.getUserName()))) {
                holder.con_tv_msg.setText("[草稿]" + textMap.get(emConversation.getUserName()));
            } else {
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
        }
        //设置标题（发送消息的名字）
        holder.con_tv_name.setText(emConversation.getUserName());
        //设置最后一条时间
        holder.msg_time.setText(getLastMsgTime(emConversation));
        //设置消息数目
        if (emConversation==null||emConversation.getUnreadMsgCount()==0){
            holder.unread_msg_number.setVisibility(View.INVISIBLE);
        }else {
            holder.unread_msg_number.setVisibility(View.INVISIBLE);
            holder.unread_msg_number.setText(emConversation.getUnreadMsgCount() + "");
        }

        //给item设置点击事件
        holder.chat_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (callListener != null) {
                    callListener.ItemClick(position);
                }
            }
        });

        //置顶该列
        convertView.findViewById(R.id.con_btn_top).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (callListener!=null){
                    callListener.top(position);
                }


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

    /**
     *⌚时间️转换
     * @param msg
     * @return
     */
    private String getLastMsgTime(EMConversation msg) {
        //得到最后一条消息的时间
        long lastTime = msg.getLastMessage().getMsgTime();
        //距离现在 时间  =  得到当前时间  -   最后一条消息的时间
        long notTime = new Date().getTime() - lastTime;
        //将毫秒转换成分钟
        int m = m2M(notTime);
        //  判断是否 大于60分钟  若大于转换成小时
        if (m > 60) {
            //判断是否 大于24小时  若大于转换天
            if (m2h(m) > 24) {
                return h2d(m2h(m)) + "天前";
            } else {
                return m2h(m) + "小时前";
            }
        } else {
            //判断是否大于1分钟 如果不是  显示 刚刚
            if (m > 1) {
                return m + "分钟前";
            } else
                return "刚刚";
        }
    }

    //毫秒转分钟
    private int m2M(long time) {
        return (int) time / 1000 / 60;
    }

    //分钟转小时
    private int m2h(long time) {
        return (int) (time / 60);
    }

    //小时转天数
    private int h2d(long time) {
        return (int) (time / 24);
    }


    //内部类
    class ViewHolder {
        private View chat_item;
        private TextView con_tv_name, con_tv_msg, unread_msg_number, msg_time;
        private ImageView con_image_per_mes;
    }

}
