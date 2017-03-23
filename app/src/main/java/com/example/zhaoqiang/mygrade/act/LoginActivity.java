package com.example.zhaoqiang.mygrade.act;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.zhaoqiang.mygrade.MainActivity;
import com.example.zhaoqiang.mygrade.R;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;

import static android.text.TextUtils.isEmpty;

/**
 * Created by 轩韩子 on 2017/3/23.
 * at 10:03
 */

public class LoginActivity extends AppCompatActivity {
    private EditText username, password;
    String user = "xhz";
    String pwd = "727319870";
    private boolean login = false;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_act);
        // 如果登录成功过，直接进入主页面
        if (EMClient.getInstance().isLoggedInBefore()) {
            login = true;
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            return;
        }

        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        // 如果用户名改变，清空密码
        username.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                password.setText(null);
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    /**
     * 登陆
     */
    public void login(View view) {

        if (isEmpty(user)) {
            Toast.makeText(this, "用户名不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        if (isEmpty(pwd)) {
            Toast.makeText(this, "密码不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        if (username.equals(user) && password.equals(pwd)) {
            Toast.makeText(this, "登陆成功", Toast.LENGTH_SHORT).show();
        }else if (!username.equals(user)||!password.equals(pwd)){
            Toast.makeText(this, "用户名或密码错误", Toast.LENGTH_SHORT).show();
        }
        // 调用sdk登陆方法登陆聊天服务器
        EMClient.getInstance().login(user, pwd, new EMCallBack() {
            @Override
            public void onSuccess() {
                // 进入主页面
                Intent intent = new Intent(LoginActivity.this,
                        MainActivity.class);
                startActivity(intent);
                finish();
            }

            @Override
            public void onProgress(int progress, String status) {
            }

            @Override
            public void onError(final int code, final String message) {

            }
        });
    }


    /**
     * 注册
     */
    public void register(View view) {
        startActivityForResult(new Intent(this, RegisterActivity.class), 0);
    }
    @Override
    protected void onResume() {
        super.onResume();
        if (login) {
            return;
        }
    }

}
