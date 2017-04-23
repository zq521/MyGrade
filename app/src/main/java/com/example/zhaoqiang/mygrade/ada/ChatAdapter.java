package com.example.zhaoqiang.mygrade.ada;

import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.zhaoqiang.mygrade.R;
import com.example.zhaoqiang.mygrade.act.Baseactivity;
import com.example.zhaoqiang.mygrade.callback.CallListener;
import com.example.zhaoqiang.mygrade.help.ProDialog;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMImageMessageBody;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMTextMessageBody;
import com.hyphenate.chat.EMVideoMessageBody;

import java.util.ArrayList;
import java.util.HashMap;


/**
 * Created by 轩韩子 on 2017/3/31.
 * at 09:06
 * 聊天详情页面适配器
 */

public class ChatAdapter extends BaseAdapter {
    private CallListener callListener;
    private Context context;
    private ProDialog proDialog;
    private EMVideoMessageBody body2;
    private ArrayList<EMMessage> sendMessage = new ArrayList<>();
    final String s = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + System.currentTimeMillis() + ".mp4";

    /*
     * 设置接口
     */
    public void setCallListener(CallListener callListener) {
        this.callListener = callListener;
    }


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
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.item_chat, parent, false);
            holder.chat_my_image = (ImageView) convertView.findViewById(R.id.chat_my_image);
            holder.chat_my_message = (TextView) convertView.findViewById(R.id.chat_my_message);
            holder.send_image = (ImageView) convertView.findViewById(R.id.send_image);

            holder.chat_other_image = (ImageView) convertView.findViewById(R.id.chat_other_image);
            holder.chat_other_message = (TextView) convertView.findViewById(R.id.chat_other_message);
            holder.get_image = (ImageView) convertView.findViewById(R.id.get_image);

            holder.item_chat = convertView.findViewById(R.id.item_chat);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        //获取会话
        EMMessage emMessage = getItem(position);

        EMMessage.Type type = emMessage.getType();
        switch (type) {
            case TXT:
                //文本实现
                txtMessage(holder, emMessage);
                break;
            case IMAGE:
                //图片实现
                imageMessage(holder, emMessage);
                break;
            case VIDEO:
                //视频实现
                body2 = (EMVideoMessageBody) emMessage.getBody();
                videoMessage(holder, emMessage);
                break;

        }
        //item点击事件
        holder.item_chat.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View v) {
                if (callListener != null) {
                    callListener.ItemClick(position);
                }
            }

        });
        //自己发送图片点击事件
        holder.send_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Baseactivity.class);
                intent.putExtra("path", body2.getLocalUrl());
                context.startActivity(intent);

            }
        });
        //对方发送图片点击事件
        holder.get_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //进度条显示
                proDialog = new ProDialog(context, R.style.Xhz);

                proDialog.show();
                switch (body2.downloadStatus()) {
                    case DOWNLOADING:

                        break;
                    case SUCCESSED:
                        Intent intent = new Intent(context, Baseactivity.class);
                        intent.putExtra("getPath", s);
                        context.startActivity(intent);
                        break;
                    case FAILED:
                    case PENDING:
                        //下载视频
                        getVideo(position);
                        break;
                    default:

                }

            }
        });


        return convertView;
    }

    /*
    下载视频
     */
    private void getVideo(int i) {
        HashMap<String, String> map = new HashMap<String, String>();
        if (TextUtils.isEmpty(body2.getSecret())) {
            map.put("share-secret", body2.getSecret());
        }
        final EMVideoMessageBody body3 = (EMVideoMessageBody) sendMessage.get(i).getBody();

        EMClient.getInstance().chatManager().downloadFile(
                body3.getRemoteUrl()//视频地址
                , s
                , map
                , new EMCallBack() {
                    @Override
                    public void onSuccess() {
                        Log.e("onSuccess", "onSuccess");
                        //关闭进度
                        proDialog.cancel();

                        Intent intent = new Intent(context, Baseactivity.class);
                        intent.putExtra("getPath", s);
                        context.startActivity(intent);

                    }

                    @Override
                    public void onError(int i, String s) {
                        Log.e("onError", "onError" + i + "  " + s);
                    }

                    @Override
                    public void onProgress(int i, String s) {

                        Log.e("onProgress", "onProgress" + i + "  " + s);
                    }
                });
    }

    /*
   //文本实现
    */
    private void txtMessage(ViewHolder holder, EMMessage emMessage) {
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
    }

    /*
   //图片实现
    */
    private void imageMessage(ViewHolder holder, EMMessage emMessage) {
        EMImageMessageBody body1 = (EMImageMessageBody) emMessage.getBody();
        if (emMessage.getFrom().equals(emMessage.getUserName())) {
            Glide.with(context)
                    .load(body1.getThumbnailUrl())
                    .override(300, 500)
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
    }

    /*
   视频实现
    */
    private void videoMessage(ViewHolder holder, EMMessage emMessage) {

        if (emMessage.getFrom().equals(emMessage.getUserName())) {

            Glide.with(context)
                    .load(R.drawable.img_video_default)
                    .override(300, 500)
                    .into(holder.get_image);
            holder.chat_other_image.setVisibility(View.VISIBLE);
            holder.get_image.setVisibility(View.VISIBLE);

        } else {
            Glide.with(context)
                    .load(body2.getLocalUrl())
                    .override(300, 500)
                    .into(holder.send_image);
            holder.chat_my_image.setVisibility(View.VISIBLE);
            holder.send_image.setVisibility(View.VISIBLE);

        }
    }


    class ViewHolder {
        View item_chat;
        TextView chat_other_message, chat_my_message;
        ImageView chat_other_image, chat_my_image, send_image, get_image;
    }
}
