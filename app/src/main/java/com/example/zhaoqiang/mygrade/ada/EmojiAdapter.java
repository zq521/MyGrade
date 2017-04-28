package com.example.zhaoqiang.mygrade.ada;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.example.zhaoqiang.mygrade.R;
import java.util.ArrayList;


/**
 * Created by 轩韩子 on 2017/4/26.
 * at 14:18
 */

public class EmojiAdapter extends RecyclerView.Adapter<EmojiAdapter.MyViewHolder>{
    private Context context;
    private ArrayList<Integer> list=new ArrayList<>();

    public EmojiAdapter(Context context, ArrayList<Integer> list) {
        this.context = context;
        this.list = list;
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        private ImageView emjoe;
        public MyViewHolder(View itemView) {
            super(itemView);
            emjoe= (ImageView) itemView.findViewById(R.id.emjoe);
        }
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_emoji,parent,false));
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

      holder.emjoe.setImageResource(list.get(position));

    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
