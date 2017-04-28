package com.example.zhaoqiang.mygrade.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.zhaoqiang.mygrade.R;
import com.example.zhaoqiang.mygrade.ada.EmojiAdapter;
import com.example.zhaoqiang.mygrade.help.SmileUtils;

import java.util.ArrayList;


/**
 * Created by 轩韩子 on 2017/4/26.
 * at 14:17
 */

public class EmojiFragment extends Fragment {
    private RecyclerView emjoe_image;
    private EmojiAdapter emjoeAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<Integer> list=new ArrayList<>();


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.frag_emoji, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
       list=SmileUtils.emjeo();
        init(view);

    }

    private void init(View view) {
        emjoe_image = (RecyclerView) view.findViewById(R.id.emjoe_image);
        layoutManager = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.HORIZONTAL);
        emjoeAdapter = new EmojiAdapter(getActivity(), list);
        emjoe_image.setLayoutManager(layoutManager);
        emjoe_image.setAdapter(emjoeAdapter);
    }

}