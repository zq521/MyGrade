package com.example.zhaoqiang.mygrade.act;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.zhaoqiang.mygrade.R;
import com.example.zhaoqiang.mygrade.help.CodeUtils;


/**
 * Created by 轩韩子 on 2017/3/23.
 * at 09:44
 */

public class RegisterActivity extends AppCompatActivity {
    private EditText username, password, password_two;
    private ImageView image;
    private Button btn;
    private EditText et;
    private CodeUtils codeUtils;
    private String codeStr;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_act);
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        password_two = (EditText) findViewById(R.id.password_two);
        image = (ImageView) findViewById(R.id.image);
        et = (EditText) findViewById(R.id.et);
        btn = (Button) findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                codeUtils = CodeUtils.getInstance();
                Bitmap bitmap = codeUtils.createBitmap();
                image.setImageBitmap(bitmap);
            }
        });

    }

    public void register(View view) {
        final String user = username.getText().toString().trim();
        final String pwd = password.getText().toString().trim();
        String pwd_two = password_two.getText().toString().trim();
        codeStr = et.getText().toString().trim();
        String code = codeUtils.createCode().trim();
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
        } else if (null == codeStr || TextUtils.isEmpty(codeStr)) {
            Toast.makeText(this, "请输入验证码", Toast.LENGTH_SHORT).show();
            return;
        } else if (code.equalsIgnoreCase("")) {
            Toast.makeText(this, "验证码不能为空", Toast.LENGTH_SHORT).show();

        } else if(!code.equalsIgnoreCase(codeStr)){
            Toast.makeText(this, "验证码错误", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, LoginActivity.class));
            finish();

        }




    }


}
