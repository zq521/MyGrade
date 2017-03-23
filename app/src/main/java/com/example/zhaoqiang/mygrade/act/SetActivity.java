package com.example.zhaoqiang.mygrade.act;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.zhaoqiang.mygrade.R;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;

/**
 * Created by 轩韩子 on 2017/3/23.
 * at 14:41
 * 设置页面
 */

public class SetActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btn_my_sms, btn_alter, btn_image, btn_logout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.set_act);
        init();
    }

    public void init() {
        btn_my_sms = (Button) findViewById(R.id.btn_my_sms);
        btn_alter = (Button) findViewById(R.id.btn_alter);
        btn_image = (Button) findViewById(R.id.btn_image);
        btn_logout = (Button) findViewById(R.id.btn_logout);

        btn_my_sms.setOnClickListener(this);
        btn_alter.setOnClickListener(this);
        btn_image.setOnClickListener(this);
        btn_logout.setOnClickListener(this);


    }

    //点击事件
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_my_sms:

                break;
            case R.id.btn_alter:

                break;
            case R.id.btn_image:

                break;
            case R.id.btn_logout:
                logout();
                break;


        }

    }

    public void logout() {

        EMClient.getInstance().logout(false, new EMCallBack() {
            @Override
            public void onSuccess() {
                runOnUiThread(new Runnable() {
                    public void run() {
                        startActivity(new Intent(SetActivity.this, LoginActivity.class));

                    }
                });
            }

            @Override
            public void onProgress(int progress, String status) {

            }

            @Override
            public void onError(int code, String message) {
                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {

                        Toast.makeText(SetActivity.this, "退出成功", Toast.LENGTH_SHORT).show();


                    }
                });
            }

        });
    }
}
