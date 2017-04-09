package com.example.zhaoqiang.mygrade.help;

import com.example.zhaoqiang.mygrade.callback.MessageListener;

/**
 * Created by 轩韩子 on 2017/3/31.
 * at 15:27
 */

public class MessageManager {
    private static MessageManager messageManager;

    private MessageListener messageListener;
    public static synchronized  MessageManager getInsatance(){
        if (messageManager==null){
            messageManager=new MessageManager();
        }
           return   messageManager;
    }

    public MessageListener getMessageListener() {
        return messageListener;
    }

    public void setMessageListener(MessageListener messageListener) {
        this.messageListener = messageListener;
    }
}
