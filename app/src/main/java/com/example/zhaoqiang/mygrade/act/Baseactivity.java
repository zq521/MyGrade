package com.example.zhaoqiang.mygrade.act;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.MediaController;
import android.widget.VideoView;

import com.example.zhaoqiang.mygrade.R;

/**
 * Created by 轩韩子 on 2017/3/22.
 * at 14:04
 */

public class Baseactivity extends AppCompatActivity {
    private VideoView videoView;
    private String path;
    private String getPath;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_base);
        path=getIntent().getStringExtra("path");
        getPath=getIntent().getStringExtra("getPath");
        init();

    }

    private void init() {
        videoView = (VideoView) findViewById(R.id.videoView);
        if (TextUtils.isEmpty(path)){
            videoView.setVideoPath(getPath);
        }else {
            videoView.setVideoPath(path);
            videoView.setMediaController(new MediaController(this));
            videoView.requestFocus();
        }

        videoView.start();

    }

}
