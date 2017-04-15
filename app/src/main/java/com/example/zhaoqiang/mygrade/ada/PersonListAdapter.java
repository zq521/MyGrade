package com.example.zhaoqiang.mygrade.ada;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.example.zhaoqiang.mygrade.R;
import com.example.zhaoqiang.mygrade.callback.CallListener;
import com.example.zhaoqiang.mygrade.help.NenoTextview;

import java.util.ArrayList;

/**
 * Created by 轩韩子 on 2017/3/23.
 * at 09:35
 * 联系人适配器
 */

public class PersonListAdapter extends RecyclerView.Adapter<PersonListAdapter.MyViewHolder> {
    private Context context;
    private CallListener callListener;
    private ArrayList<String> list = new ArrayList<>();

    public PersonListAdapter(Context context, ArrayList<String> list) {
        this.context = context;
        this.list = list;
    }

    public void setCallListener(CallListener callListener) {
        this.callListener = callListener;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private NenoTextview person_tv_name;
        private ImageView person_iv_avatar;
        private View penson_item;
        private Button main_btn_remove;

        public MyViewHolder(View itemView) {
            super(itemView);
            person_tv_name= (NenoTextview) itemView.findViewById(R.id.person_tv_name);
            person_iv_avatar= (ImageView) itemView.findViewById(R.id.person_iv_avatar);
            penson_item=itemView.findViewById(R.id.penson_item);
            main_btn_remove= (Button) itemView.findViewById(R.id.main_btn_remove);

        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_person_list, parent, false));
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        //设置联系人信息
        holder.person_tv_name.setText(list.get(position));
        //设置点击事件
        holder.penson_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (callListener!=null){
                    callListener.ItemClick(position);
                }
            }
        });
        // 弹出提示框（修改备注)
       holder.main_btn_remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (callListener!=null){
                    callListener.Click(position);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
