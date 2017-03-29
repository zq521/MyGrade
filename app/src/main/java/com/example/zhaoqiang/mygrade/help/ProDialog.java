package com.example.zhaoqiang.mygrade.help;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.WindowManager;

import com.example.zhaoqiang.mygrade.R;

/**
 * Created by 轩韩子 on 2017/3/27.
 * at 11:09
 * 进度类
 */

public class ProDialog extends ProgressDialog {

    public ProDialog(Context context) {
        super(context);
    }

      //theme 自定义主题，样式
    public ProDialog(Context context, int theme) {
        super(context, theme);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //this.可用省略不写
        setIndeterminate(false);
        setCanceledOnTouchOutside(false);
        setContentView(R.layout.pro_dia);
        //获得
        WindowManager.LayoutParams params=getWindow().getAttributes();
        params.width=WindowManager.LayoutParams.WRAP_CONTENT;
        params.height=WindowManager.LayoutParams.WRAP_CONTENT;
        params.alpha=1.2f;
        //设置
        getWindow().setAttributes(params);

    }

}
