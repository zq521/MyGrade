package com.example.zhaoqiang.mygrade.act;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.AlphaAnimation;
import android.widget.RelativeLayout;

import com.example.zhaoqiang.mygrade.MainActivity;
import com.example.zhaoqiang.mygrade.R;
import com.hyphenate.chat.EMClient;

/**
 * Created by 轩韩子 on 2017/3/23.
 * at 10:19
 * message线程之间的通信，
 */

public class SplashActivity extends AppCompatActivity {
    private RelativeLayout rootLayout;
    private static final int sleepTime = 2000;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       setContentView(R.layout.act_splash);

        rootLayout = (RelativeLayout) findViewById(R.id.splash_root);
        AlphaAnimation animation = new AlphaAnimation(0.3f, 1.0f);
        animation.setDuration(1800);
        rootLayout.startAnimation(animation);
    }
    @Override
    protected void onStart() {
        super.onStart();
        new Thread(new Runnable() {
            public void run() {
                if (EMClient.getInstance().isLoggedInBefore()) {
                    long start = System.currentTimeMillis();
                    long costTime = System.currentTimeMillis() - start;
                    //等待sleeptime时长
                    if (sleepTime - costTime > 0) {
                        try {
                            Thread.sleep(sleepTime - costTime);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    //进入主页面
                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                    finish();
                }else {
                    try {
                        Thread.sleep(sleepTime);
                    } catch (Exception e) {
                    }
                    startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                    finish();
                }
            }
        }).start();

    }
    //重写返回键
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
