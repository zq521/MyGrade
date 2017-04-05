package com.example.zhaoqiang.mygrade.help;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.zhaoqiang.mygrade.R;

/**
 * Created by 轩韩子 on 2017/4/5.
 * at 13:46
 */

public class SetImage {

    public static void httpImg(Context context, ImageView imageView, String imageUrl){

        Glide.with(context)
                .load(imageUrl)//加载图片地址 Uri.fromFile( new File( filePath ) )
                .placeholder(R.mipmap.ic_launcher)//加载未完成时显示的图片
                .error(R.mipmap.ic_launcher)//加载失败时显示的图片
                .override(100,100)
                .into(imageView);//将图片显示到控件



    }
}
