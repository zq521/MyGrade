package com.example.zhaoqiang.mygrade.magnager;

import com.example.zhaoqiang.mygrade.callback.MessageListListener;

/**
 * Created by 轩韩子 on 2017/3/31.
 * at 15:27
 */

public class MessageManager {
    private static MessageManager messageManager;

    private MessageListListener messageListListener;
    public static synchronized  MessageManager getInsatance(){
        if (messageManager==null){
            messageManager=new MessageManager();
        }
           return   messageManager;
    }

    public MessageListListener getMessageListener() {
        return messageListListener;
    }

    public void setMessageListener(MessageListListener messageListListener) {
        this.messageListListener = messageListListener;
    }
}
