package com.example.zhaoqiang.mygrade.act;

import android.app.ProgressDialog;
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
import com.hyphenate.EMError;
import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;


/**
 * Created by 轩韩子 on 2017/3/23.
 * at 09:44
 */

public class RegisterActivity extends AppCompatActivity {
    private EditText username, password, password_two;
    private ImageView image_code;
    private Button btn_code;
    private EditText ed_auth_code;
    private CodeUtils codeUtils;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_register);
        //初始化控件
        init();

    }

    private void init() {
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        password_two = (EditText) findViewById(R.id.password_two);
        image_code = (ImageView) findViewById(R.id.image_code);
        ed_auth_code = (EditText) findViewById(R.id.ed_auth_code);
        btn_code = (Button) findViewById(R.id.btn_code);
        btn_code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                codeUtils = CodeUtils.getInstance();
                Bitmap bitmap = codeUtils.createBitmap();
                image_code.setImageBitmap(bitmap);
            }
        });
    }

    //注册
    public void register(View view) {
        final String user = username.getText().toString().trim();
        final String pwd = password.getText().toString().trim();
        String pwd_two = password_two.getText().toString().trim();
        String codeStr = ed_auth_code.getText().toString().trim();
        String code = codeUtils.createCode().toString().trim();

        if (TextUtils.isEmpty(user)) {
            Toast.makeText(this, "用户名不能为空", Toast.LENGTH_SHORT).show();
            username.requestFocus();
            return ;
        } else if (TextUtils.isEmpty(pwd)) {
            Toast.makeText(this, "密码不能为空", Toast.LENGTH_SHORT).show();
            password.requestFocus();
            return ;
        } else if (TextUtils.isEmpty(pwd_two)) {
            Toast.makeText(this, "请在此输入密码不能为空", Toast.LENGTH_SHORT).show();
            password_two.requestFocus();
            return ;
        } else if (!pwd.equals(pwd_two)) {
            Toast.makeText(this, "两次输入密码不正确", Toast.LENGTH_SHORT).show();
            return ;
        } else if (TextUtils.isEmpty(codeStr)) {
            Toast.makeText(this, "请输入验证码", Toast.LENGTH_SHORT).show();
            return ;
        }
//        else if (!codeStr.equals(code)) {
//            Toast.makeText(this, "验证码错误", Toast.LENGTH_SHORT).show();
//
//             return;
//        }

        if (!TextUtils.isEmpty(user) && !TextUtils.isEmpty(pwd)) {
            final ProgressDialog pd = new ProgressDialog(this);
            pd.setMessage("正在注册");
            pd.show();
            new Thread(new Runnable() {
                public void run() {
                    try {
                        // 调用sdk注册方法
                        EMClient.getInstance().createAccount(user,pwd);
                        runOnUiThread(new Runnable() {
                            public void run() {
                                if (!RegisterActivity.this.isFinishing())
                                    pd.dismiss();
                                Toast.makeText(getApplicationContext(), "注册成功", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                                finish();
                            }
                        });
                    } catch (final HyphenateException e) {
                        runOnUiThread(new Runnable() {
                            public void run() {
                                if (!RegisterActivity.this.isFinishing())
                                    pd.dismiss();
                                int errorCode=e.getErrorCode();
                                if(errorCode== EMError.NETWORK_ERROR){
                                    Toast.makeText(getApplicationContext(),"网络异常，请检查网络！", Toast.LENGTH_SHORT).show();
                                }else if(errorCode == EMError.USER_ALREADY_EXIST){
                                    Toast.makeText(getApplicationContext(), "用户已存在", Toast.LENGTH_SHORT).show();
                                }else if(errorCode == EMError.USER_AUTHENTICATION_FAILED){
                                    Toast.makeText(getApplicationContext(),"注册失败，无权限", Toast.LENGTH_SHORT).show();
                                }else if(errorCode == EMError.USER_ILLEGAL_ARGUMENT){
                                    Toast.makeText(getApplicationContext(),"用户名不合法",Toast.LENGTH_SHORT).show();
                                }else{
                                    Toast.makeText(getApplicationContext(),"注册失败", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                }
            }).start();

        }

    }










}
