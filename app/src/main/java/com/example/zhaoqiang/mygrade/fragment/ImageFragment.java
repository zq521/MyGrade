package com.example.zhaoqiang.mygrade.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.zhaoqiang.mygrade.R;
import com.example.zhaoqiang.mygrade.ada.ImageAdapter;
import com.example.zhaoqiang.mygrade.help.FileUtils;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * Created by 轩韩子 on 2017/4/10.
 * at 17:58
 */

public class ImageFragment extends Fragment implements View.OnClickListener {
    private RecyclerView menu_image_recycler;
    private Button menu_btn_send_image;
    private ArrayList<String> list=new ArrayList<>();
    private ImageAdapter imageAdapter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.menu_image,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        list= FileUtils.getAllImg(getActivity());
        init(view);
    }

    private void init(View view){
        menu_image_recycler= (RecyclerView) view.findViewById(R.id.menu_image_recycler);
        menu_btn_send_image= (Button) view.findViewById(R.id.menu_btn_send_image);
        menu_btn_send_image.setOnClickListener(this);
        imageAdapter=new ImageAdapter(getActivity(),list);

        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);

        menu_image_recycler.setLayoutManager(linearLayoutManager);
        menu_image_recycler.setAdapter(imageAdapter);


    }

    @Override
    public void onClick(View v) {
        HashSet<String> checkList = new HashSet<>();
        for (String s:checkList){


            Log.e("checkList",s);
        }
    }
}
