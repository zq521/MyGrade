package com.example.zhaoqiang.mygrade.help;

import com.hyphenate.chat.EMConversation;

import java.util.ArrayList;

/**
 * Created by 轩韩子 on 2017/3/31.
 * at 15:27
 */

public class MessageManager {
    private static MessageManager messageManager;
    private ArrayList<EMConversation> list=new ArrayList<>();

    public static synchronized  MessageManager getInsatance(){
        if (messageManager==null){
            messageManager=new MessageManager();
        }
           return   messageManager;
    }

    public ArrayList<EMConversation> getChatList() {
        return list;
    }

    public void setChatList(ArrayList<EMConversation> list) {
        this.list = list;
    }

}
