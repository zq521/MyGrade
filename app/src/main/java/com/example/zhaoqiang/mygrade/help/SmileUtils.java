package com.example.zhaoqiang.mygrade.help;

import com.example.zhaoqiang.mygrade.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Created by 轩韩子 on 2017/4/28.
 * at 13:43
 * 表情库
 */

public class SmileUtils {
    static HashMap<String, Integer> hashMap = new HashMap<>();
    static Integer value;

    public static final String f_static_00 = "[):0]";
    public static final String f_static_01 = "[):]";
    public static final String f_static_02 = "[:D]";
    public static final String f_static_03 = "[;)]";
    public static final String f_static_04 = "[:-o]";
    public static final String f_static_05 = "[:p]";
    public static final String f_static_06 = "[(H)]";
    public static final String f_static_07 = "[:@]";
    public static final String f_static_08 = "[:s]";
    public static final String f_static_09 = "[:$]";
    public static final String f_static_010 = "[:(]";
    public static final String f_static_011 = "[:'(]";
    public static final String f_static_012 = "[:|]";
    public static final String f_static_013 = "[(a)]";
    public static final String f_static_014 = "[8o|]";
    public static final String f_static_015 = "[8-|]";
    public static final String f_static_016 = "[+o(]";
    public static final String f_static_017 = "[<o)]";
    public static final String f_static_018 = "[|-)]";
    public static final String f_static_019 = "[*-)]";
    public static final String f_static_020 = "[:-#]";
    public static final String f_static_021 = "[:-*]";
    public static final String f_static_022 = "[^o)]";
    public static final String f_static_023 = "[8-)]";
    public static final String f_static_024 = "[(|)]";
    public static final String f_static_025 = "[(u)]";
    public static final String f_static_026 = "[(S)]";
    public static final String f_static_027 = "[(*)]";
    public static final String f_static_028 = "[(#)]";
    public static final String f_static_029 = "[(R)]";
    public static final String f_static_030 = "[({)]";
    public static final String f_static_031 = "[(})]";
    public static final String f_static_032 = "[(k)]";
    public static final String f_static_033 = "[(F)]";
    public static final String f_static_034 = "[(W)]";
    public static final String f_static_035 = "[(D)]";
    public static final String f_static_036 = "[(D1)]";
    public static final String f_static_037 = "[(D2)]";
    public static final String f_static_038 = "[(D3)]";
    public static final String f_static_039 = "[(D4)]";
    public static final String f_static_040 = "[(D5)]";
    public static final String f_static_041 = "[(D6)]";
    public static final String f_static_042 = "[(D7)]";
    public static final String f_static_043 = "[(D8)]";
    public static final String f_static_044 = "[(D9)]";
    public static final String f_static_045 = "[(D10)]";
    public static final String f_static_046 = "[(D11)]";
    public static final String f_static_047 = "[(D12)]";
    public static final String f_static_048 = "[(D13)]";
    public static final String f_static_049 = "[(D14)]";
    public static final String f_static_050 = "[(D15)]";
    public static final String f_static_051 = "[(D16)]";
    public static final String f_static_052 = "[(D17)]";
    public static final String f_static_053 = "[(D18)]";
    public static final String f_static_054 = "[(D19)]";
    public static final String f_static_055 = "[(D20)]";
    public static final String f_static_056 = "[(D21)]";
    public static final String f_static_057 = "[(D22)]";
    public static final String f_static_058 = "[(D23)]";
    public static final String f_static_059 = "[(D24)]";
    public static final String f_static_060 = "[(D25)]";
    public static final String f_static_061 = "[(D26)]";
    public static final String f_static_062 = "[(D27)]";

    public static HashMap<String, Integer> setImg() {
        hashMap.put(SmileUtils.f_static_00, R.drawable.f_static_00);
        hashMap.put(SmileUtils.f_static_01, R.drawable.f_static_01);
        hashMap.put(SmileUtils.f_static_02, R.drawable.f_static_02);
        hashMap.put(SmileUtils.f_static_03, R.drawable.f_static_03);
        hashMap.put(SmileUtils.f_static_04, R.drawable.f_static_04);
        hashMap.put(SmileUtils.f_static_05, R.drawable.f_static_05);
        hashMap.put(SmileUtils.f_static_06, R.drawable.f_static_06);
        hashMap.put(SmileUtils.f_static_07, R.drawable.f_static_07);
        hashMap.put(SmileUtils.f_static_08, R.drawable.f_static_08);
        hashMap.put(SmileUtils.f_static_09, R.drawable.f_static_09);
        hashMap.put(SmileUtils.f_static_010, R.drawable.f_static_010);
        hashMap.put(SmileUtils.f_static_011, R.drawable.f_static_011);
        hashMap.put(SmileUtils.f_static_012, R.drawable.f_static_012);
        hashMap.put(SmileUtils.f_static_013, R.drawable.f_static_013);
        hashMap.put(SmileUtils.f_static_014, R.drawable.f_static_014);
        hashMap.put(SmileUtils.f_static_015, R.drawable.f_static_015);
        hashMap.put(SmileUtils.f_static_016, R.drawable.f_static_016);
        hashMap.put(SmileUtils.f_static_017, R.drawable.f_static_017);
        hashMap.put(SmileUtils.f_static_018, R.drawable.f_static_018);
        hashMap.put(SmileUtils.f_static_019, R.drawable.f_static_019);
        hashMap.put(SmileUtils.f_static_020, R.drawable.f_static_020);
        hashMap.put(SmileUtils.f_static_021, R.drawable.f_static_021);
        hashMap.put(SmileUtils.f_static_022, R.drawable.f_static_022);
        hashMap.put(SmileUtils.f_static_023, R.drawable.f_static_023);
        hashMap.put(SmileUtils.f_static_024, R.drawable.f_static_024);
        hashMap.put(SmileUtils.f_static_025, R.drawable.f_static_025);
        hashMap.put(SmileUtils.f_static_026, R.drawable.f_static_026);
        hashMap.put(SmileUtils.f_static_027, R.drawable.f_static_027);
        hashMap.put(SmileUtils.f_static_028, R.drawable.f_static_028);
        hashMap.put(SmileUtils.f_static_029, R.drawable.f_static_029);
        hashMap.put(SmileUtils.f_static_030, R.drawable.f_static_030);
        hashMap.put(SmileUtils.f_static_031, R.drawable.f_static_031);
        hashMap.put(SmileUtils.f_static_032, R.drawable.f_static_032);
        hashMap.put(SmileUtils.f_static_033, R.drawable.f_static_033);
        hashMap.put(SmileUtils.f_static_034, R.drawable.f_static_034);
        hashMap.put(SmileUtils.f_static_035, R.drawable.f_static_035);
        hashMap.put(SmileUtils.f_static_036, R.drawable.f_static_036);
        hashMap.put(SmileUtils.f_static_037, R.drawable.f_static_037);
        hashMap.put(SmileUtils.f_static_038, R.drawable.f_static_038);
        hashMap.put(SmileUtils.f_static_039, R.drawable.f_static_039);
        hashMap.put(SmileUtils.f_static_040, R.drawable.f_static_040);
        hashMap.put(SmileUtils.f_static_041, R.drawable.f_static_041);
        hashMap.put(SmileUtils.f_static_042, R.drawable.f_static_042);
        hashMap.put(SmileUtils.f_static_043, R.drawable.f_static_043);
        hashMap.put(SmileUtils.f_static_044, R.drawable.f_static_044);
        hashMap.put(SmileUtils.f_static_045, R.drawable.f_static_045);
        hashMap.put(SmileUtils.f_static_046, R.drawable.f_static_046);
        hashMap.put(SmileUtils.f_static_047, R.drawable.f_static_047);
        hashMap.put(SmileUtils.f_static_048, R.drawable.f_static_048);
        hashMap.put(SmileUtils.f_static_049, R.drawable.f_static_049);
        hashMap.put(SmileUtils.f_static_050, R.drawable.f_static_050);
        hashMap.put(SmileUtils.f_static_051, R.drawable.f_static_051);
        hashMap.put(SmileUtils.f_static_052, R.drawable.f_static_052);
        hashMap.put(SmileUtils.f_static_053, R.drawable.f_static_053);
        hashMap.put(SmileUtils.f_static_054, R.drawable.f_static_054);
        hashMap.put(SmileUtils.f_static_055, R.drawable.f_static_055);
        hashMap.put(SmileUtils.f_static_056, R.drawable.f_static_056);
        hashMap.put(SmileUtils.f_static_057, R.drawable.f_static_057);
        hashMap.put(SmileUtils.f_static_058, R.drawable.f_static_058);
        hashMap.put(SmileUtils.f_static_059, R.drawable.f_static_059);
        hashMap.put(SmileUtils.f_static_060, R.drawable.f_static_060);
        hashMap.put(SmileUtils.f_static_061, R.drawable.f_static_061);
        hashMap.put(SmileUtils.f_static_062, R.drawable.f_static_062);

        return hashMap;
    }

    public static int getImg(String key) {
        if (hashMap.size() == 0)
            setImg();
        return hashMap.get(key);
    }


    public static ArrayList<Integer> emjeo() {
        ArrayList<Integer> list = new ArrayList<>();
        //利用KeySet 迭代
        Iterator it = hashMap.keySet().iterator();
        while (it.hasNext()) {
            String key;
            key = it.next().toString();
            value = hashMap.get(key);
            list.add(value);
        }
        return list;
    }

}