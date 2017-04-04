package com.example.zhaoqiang.mygrade.help;

import android.content.Context;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by 轩韩子 on 2017/3/31.
 * at 15:43
 */

public class SPUtils {
    private static final String SP_NAME = "userName";
    private static final String SP_PASS="passWord";
    private static final String LAST_USERNAME = "LastUserName";
    private static final String LAST_PASSWORD = "lastPassword";

    public static void setLastUserName(Context context, String userName) {

        context.getSharedPreferences(SP_NAME, MODE_PRIVATE)
                .edit()
                .putString(LAST_USERNAME, userName)
                .apply();
    }

    public static String getLastUserName(Context context) {

        return context.getSharedPreferences(SP_NAME, MODE_PRIVATE)
                .getString(LAST_USERNAME, "");

    }

    public static void setlastPassword(Context context, String passWord) {

        context.getSharedPreferences(SP_NAME, MODE_PRIVATE)
                .edit()
                .putString(LAST_PASSWORD, passWord)
                .apply();
    }

    public static String getLastPassword(Context context) {

        return context.getSharedPreferences(SP_NAME, MODE_PRIVATE)
                .getString(LAST_PASSWORD, "");

    }
}
