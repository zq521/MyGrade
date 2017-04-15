package com.example.zhaoqiang.mygrade.ada;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.zhaoqiang.mygrade.R;
import com.example.zhaoqiang.mygrade.callback.CallListener;
import com.hyphenate.chat.EMGroup;

import java.util.ArrayList;

/**
 * Created by 轩韩子 on 2017/4/12.
 * at 11:10
 */

public class GroupListAdapter extends RecyclerView.Adapter<GroupListAdapter.MyViewHolder> {
    private Context context;
    private CallListener callListener;
    private ArrayList<EMGroup> list = new ArrayList<>();

    public GroupListAdapter(Context context, ArrayList<EMGroup> list) {
        this.context = context;
        this.list = list;
    }

    public void setCallListener(CallListener callListener) {
        this.callListener = callListener;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private View penson_item;
        private TextView main_tv_name;
        private ImageView main_iv_avatar;
        private Button main_btn_remove;


        public MyViewHolder(View itemView) {
            super(itemView);
            main_tv_name = (TextView) itemView.findViewById(R.id.main_tv_name);
            main_iv_avatar = (ImageView) itemView.findViewById(R.id.main_iv_avatar);
            penson_item=itemView.findViewById(R.id.penson_item);
            main_btn_remove= (Button) itemView.findViewById(R.id.main_btn_remove);

        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_group_list, parent, false));
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        //设置联系人信息
//        holder.main_tv_name.setText((CharSequence) list.get(position));
        //设置点击事件
        holder.penson_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (callListener!=null){
                    callListener.ItemClick(position);
                }
            }
        });

        // （删除该群)
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
