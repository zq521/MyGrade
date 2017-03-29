package com.example.zhaoqiang.mygrade.act;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.zhaoqiang.mygrade.MainActivity;
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
    private Button btn_conversation, btn_address_book, btn_set;

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
        btn_conversation = (Button) findViewById(R.id.btn_conversation);
        btn_address_book = (Button) findViewById(R.id.btn_address_book);
        btn_set = (Button) findViewById(R.id.btn_set);
        btn_set.setOnClickListener(this);
        btn_my_sms.setOnClickListener(this);
        btn_alter.setOnClickListener(this);
        btn_image.setOnClickListener(this);
        btn_logout.setOnClickListener(this);
        btn_conversation.setOnClickListener(this);
        btn_address_book.setOnClickListener(this);


    }

    //点击事件
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_my_sms:
                new AlertDialog.Builder(this)
                        .setTitle("个人消息：")
                        .setMessage("用户名：xhz" + "\n" +
                                "个性签名：没个性")
                        .setPositiveButton("确定", null)
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(SetActivity.this, "你取消了~", Toast.LENGTH_SHORT).show();
                            }
                        }).show();
                break;
            case R.id.btn_alter:

                AlertDialog.Builder ab = new AlertDialog.Builder(this);
                ab.setTitle("修改昵称：")
                        .setPositiveButton("确定", null)
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(SetActivity.this, "你点击了取消按钮~", Toast.LENGTH_SHORT).show();
                            }
                        }).show();
                break;
            case R.id.btn_image:

                break;
            case R.id.btn_logout:
                new AlertDialog.Builder(this)
                        .setTitle("退出")
                        .setMessage("是否退出登录")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                logout();
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(SetActivity.this, "你取消了~", Toast.LENGTH_SHORT).show();
                            }
                        }).show();

                break;
            case R.id.btn_conversation:
                startActivity(new Intent(this, ConversationActivity.class));
                finish();
                break;
            case R.id.btn_address_book:
                startActivity(new Intent(this, MainActivity.class));
                finish();
                break;
            case R.id.btn_set:

                break;

        }

    }

    public void logout() {

        EMClient.getInstance().logout(false, new EMCallBack() {
            @Override
            public void onSuccess() {
                runOnUiThread(new Runnable() {
                    public void run() {
                        finish();
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
