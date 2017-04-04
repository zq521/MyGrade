package com.example.zhaoqiang.mygrade.act;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.example.zhaoqiang.mygrade.MainActivity;
import com.example.zhaoqiang.mygrade.R;
import com.example.zhaoqiang.mygrade.help.ProDialog;
import com.example.zhaoqiang.mygrade.help.SPUtils;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;

import static android.text.TextUtils.isEmpty;

/**
 * Created by 轩韩子 on 2017/3/23.
 * at 10:03
 */

public class LoginActivity extends AppCompatActivity {
    private String user, pwd;
    private CheckBox checkBox;
    private SharedPreferences sp;
    private boolean login = false;
    private EditText username, password;
    private ProDialog proDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_login);
        //初始化控件
        init();
        //判断之前是否登陆过，并如果用户名改变，则清空密码
        logBefore();
        //密码显示与隐藏监听
        checkBox();

    }

    public void init() {
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        checkBox = (CheckBox) findViewById(R.id.checkBox1);

        username.setText(SPUtils.getLastUserName(this));
        password.setText(SPUtils.getLastPassword(this));
        //光标位置
        username.setSelection(username.getText().toString().length());
        password.setSelection(password.getText().toString().length());
    }

    private void logBefore() {
        // 如果登录成功过，直接进入主页面
        if (EMClient.getInstance().isLoggedInBefore()) {
            // ** 免登陆情况 加载所有本地群和会话
            //加上的话保证进了主页面会话和群组都已经load完毕
            EMClient.getInstance().groupManager().loadAllGroups();
            EMClient.getInstance().chatManager().loadAllConversations();
            login = true;
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            return;
        }
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

    //登陆
    public void login(View view) {
        if (!isNetWorkConnected(this)) {
            Toast.makeText(this, "网络不可用", Toast.LENGTH_SHORT).show();
            return;
        }
        //获取输入的数据
        user = username.getText().toString();
        pwd = password.getText().toString();
        Log.e(user,pwd);
        //判断密码正确错误
        if (isEmpty(user)) {
            Toast.makeText(LoginActivity.this, "用户名不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        if (isEmpty(pwd)) {
            Toast.makeText(LoginActivity.this, "密码不能为空", Toast.LENGTH_SHORT).show();

            return;
        }
        //进度条显示
        proDialog = new ProDialog(this, R.style.Xhz);
        proDialog.show();

        // 调用sdk登陆方法登陆聊天服务器
        EMClient.getInstance().login(user, pwd, new EMCallBack() {
            @Override
            public void onSuccess() {
                // 进入主页面
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                finish();
                //关闭进度
                proDialog.cancel();
            }

            @Override
            public void onProgress(int progress, String status) {

            }

            @Override
            public void onError(final int code, final String message) {
                Log.e("code","message="+message);
                runOnUiThread(new Runnable() {
                    public void run() {
                        proDialog.cancel();
                        Toast.makeText(getApplicationContext(), "登陆失败,密码或账号错误", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    //注册
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

    //检测网络是否可用
    public static boolean isNetWorkConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            if (mNetworkInfo != null) {
                return mNetworkInfo.isAvailable() && mNetworkInfo.isConnected();
            }
        }

        return false;
    }

    private void checkBox() {
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    //如果选中，显示密码
                    password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    //否则隐藏密码
                    password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });
    }
}
