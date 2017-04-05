package com.example.zhaoqiang.mygrade.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.zhaoqiang.mygrade.R;
import com.example.zhaoqiang.mygrade.act.LoginActivity;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;

/**
 * Created by 轩韩子 on 2017/4/5.
 * at 13:37
 */

public class SetFragment extends Fragment implements View.OnClickListener{
    private Button btn_my_sms, btn_alter, btn_image, btn_logout;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
                super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.frag_set,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);
    }

    public void init(View view) {
        btn_my_sms = (Button) view.findViewById(R.id.btn_my_sms);
        btn_alter = (Button) view.findViewById(R.id.btn_alter);
        btn_image = (Button) view.findViewById(R.id.btn_image);
        btn_logout = (Button) view.findViewById(R.id.btn_logout);
        btn_my_sms.setOnClickListener(this);
        btn_alter.setOnClickListener(this);
        btn_image.setOnClickListener(this);
        btn_logout.setOnClickListener(this);



    }

    //点击事件
    @Override
    public void onClick(View v) {
        AlertDialog.Builder ab = new AlertDialog.Builder(getActivity());
        switch (v.getId()) {
            case R.id.btn_my_sms:
                ab.setTitle("个人消息：")
                        .setIcon(R.drawable.touxiang)
                        .setMessage("用户名：xhz" + "\n" +
                                "个性签名：没个性" + "\n" +
                                "地区：北京——石景山")
                        .setPositiveButton("确定", null)
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(getActivity(), "你取消了~", Toast.LENGTH_SHORT).show();
                            }
                        }).show();
                break;
            case R.id.btn_alter:
                EditText set_dialog_name;
                View mView = LayoutInflater.from(getActivity()).inflate(R.layout.set_mymes_dialog_name, null);
                set_dialog_name= (EditText)mView.findViewById(R.id.set_dialog_name);
                ab.setTitle("修改昵称：")
                        .setView(mView)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(getActivity(), "修改成功", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(getActivity(), "你点击了取消按钮~", Toast.LENGTH_SHORT).show();
                            }
                        }).show();

                break;
            case R.id.btn_image:
                ImageButton set_dialog_image;
                View imageview = LayoutInflater.from(getActivity()).inflate(R.layout.set_mymes_dialog_image, null);
                set_dialog_image= (ImageButton) imageview.findViewById(R.id.set_dialog_image);
                ab.setTitle("修改头像：")
                        .setView(imageview)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(getActivity(), "修改成功", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(getActivity(), "你点击了取消按钮~", Toast.LENGTH_SHORT).show();
                            }
                        }).show();

                break;
            case R.id.btn_logout:
                ab.setTitle("退出")
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
                                Toast.makeText(getActivity(), "你取消了~", Toast.LENGTH_SHORT).show();
                            }
                        }).show();

                break;

        }

    }
    //登出
    public void logout() {

        EMClient.getInstance().logout(false, new EMCallBack() {
            @Override
            public void onSuccess() {
                new Thread(new Runnable() {
                    public void run() {
                        startActivity(new Intent(getActivity(), LoginActivity.class));
                        getActivity().finish();
                        Toast.makeText(getActivity(), "退出成功", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onProgress(int progress, String status) {

            }

            @Override
            public void onError(int code, String message) {
                new Thread(new Runnable() {

                    @Override
                    public void run() {
                        Toast.makeText(getActivity(), "退出失败", Toast.LENGTH_SHORT).show();
                    }
                });
            }

        });
    }

}
