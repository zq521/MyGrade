package com.example.zhaoqiang.mygrade.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.example.zhaoqiang.mygrade.R;
import com.example.zhaoqiang.mygrade.act.ChatActivity;
import com.example.zhaoqiang.mygrade.ada.ImageAdapter;
import com.example.zhaoqiang.mygrade.help.FileUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashSet;

import static android.app.Activity.RESULT_OK;
import static android.provider.MediaStore.ACTION_IMAGE_CAPTURE;

/**
 * Created by 轩韩子 on 2017/4/10.
 * at 17:58
 * 本地图片选择页
 */

public class ImageFragment extends Fragment implements View.OnClickListener {
    private static final int OPEN_CAMERA = 101;
    private RecyclerView menu_image_recycler;
    private Button menu_btn_send_image;
    private ArrayList<String> list = new ArrayList<>();
    private ImageAdapter imageAdapter;
    private ImageView camera_image;
    private File file = null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.menu_image, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //获取图片资源
        list = FileUtils.getAllImg(getActivity());
        init(view);
    }

    private void init(View view) {
        menu_image_recycler = (RecyclerView) view.findViewById(R.id.menu_image_recycler);
        menu_btn_send_image = (Button) view.findViewById(R.id.menu_btn_send_image);
        camera_image = (ImageView) view.findViewById(R.id.camera_image);
        camera_image.setOnClickListener(this);
        menu_btn_send_image.setOnClickListener(this);

        imageAdapter = new ImageAdapter(getActivity(), list);
        //线性布局管理器  设置 水平滚动
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        menu_image_recycler.setLayoutManager(linearLayoutManager);
        menu_image_recycler.setAdapter(imageAdapter);

    }

    /*
    点击事件
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.menu_btn_send_image:
                //获取选中的所有图片途径
                HashSet<String> checkList = imageAdapter.getCheckList();
                for (String s : checkList) {
                    //调用activity中的发送图片方法
                    ((ChatActivity) getActivity()).sendImage(s, false);
                }
                break;

            case R.id.camera_image:
                //调用系统相机
                Intent intent = new Intent(ACTION_IMAGE_CAPTURE);//图片
                startActivityForResult(intent, OPEN_CAMERA);
                break;
        }
    }

    /*
    回调
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case OPEN_CAMERA:
                //直接发送拍摄图片
                setImageTwo(resultCode, data);
                break;
        }
    }
    /*
    发送拍摄图片
     */
    private void setImageTwo(int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            creatImage(bitmap);
        }
        ((ChatActivity) getActivity()).sendImage(file.getAbsolutePath(), false);
        Log.e("图片路径", file.getAbsolutePath());
    }

    public void creatImage(Bitmap bitmap) {
        try {
            //获得根目录
            file = new File(Environment.getExternalStorageDirectory()
                    , System.currentTimeMillis() + ".jpg");
            //开启文件的输出流
            FileOutputStream out = new FileOutputStream(file);
            //把内容写入输出流
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
            System.out.println("保存到");
            //刷新输出流
            out.flush();
            //关闭输出流
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
