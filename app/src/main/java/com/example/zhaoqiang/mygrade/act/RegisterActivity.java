package com.example.zhaoqiang.mygrade.act;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.zhaoqiang.mygrade.R;


/**
 * Created by 轩韩子 on 2017/3/23.
 * at 09:44
 */

public class RegisterActivity extends AppCompatActivity {
    private EditText username, password, password_two;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_act);
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        password_two = (EditText) findViewById(R.id.password_two);

    }

    public void register(View view) {
        final String user = username.getText().toString().trim();
        final String pwd = password.getText().toString().trim();
        String pwd_two = password_two.getText().toString().trim();
        if (TextUtils.isEmpty(user)) {
            Toast.makeText(this, "用户名不能为空", Toast.LENGTH_SHORT).show();
            username.requestFocus();
            return;
        } else if (TextUtils.isEmpty(pwd)) {
            Toast.makeText(this, "密码不能为空", Toast.LENGTH_SHORT).show();
            password.requestFocus();
            return;
        } else if (TextUtils.isEmpty(pwd_two)) {
            Toast.makeText(this, "请在此输入密码不能为空", Toast.LENGTH_SHORT).show();
            password_two.requestFocus();
            return;
        } else if (!pwd.equals(pwd_two)) {
            Toast.makeText(this, "两次输入密码不正确", Toast.LENGTH_SHORT).show();
            return;
        }

        Toast.makeText(this, "注册成功", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(this, LoginActivity.class));

    }

}
