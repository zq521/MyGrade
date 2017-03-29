package com.example.zhaoqiang.mygrade.ada;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zhaoqiang.mygrade.R;
import com.mcxtzhang.swipemenulib.SwipeMenuLayout;

import java.util.ArrayList;

import static com.example.zhaoqiang.mygrade.R.id.btn1;

/**
 * Created by 轩韩子 on 2017/3/26.
 * at 18:40
 * 消息列表适配器
 */

public class ConversationAdapater extends BaseAdapter {
    private Context context;
    private ArrayList<String> list = new ArrayList<>();

    public ConversationAdapater(Context context, ArrayList<String> list) {
        this.context = context;
        this.list = list;

    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public String getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        ViewHolder holder=null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_consersation, parent, false);
            holder = new ViewHolder();
            holder.image_per_mes= (ImageView) convertView.findViewById(R.id.image_per_mes);
            holder.name_text = (TextView) convertView.findViewById(R.id.name_text);
            holder.mes_text = (TextView) convertView.findViewById(R.id.mes_text);
            //将Holder存储到convertView中
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
            holder.name_text.setText(list.get(position));
            holder.mes_text.setText(list.get(position));

            holder.name_text.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context,"vv",Toast.LENGTH_SHORT).show();
                }
            });
        final View finalConvertView = convertView;
        convertView.findViewById(btn1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list.add(0, list.get(position));
                // 置顶后list.size增加一 所以要position+1
                list.remove(position + 1);
                notifyDataSetChanged();

                ((SwipeMenuLayout) finalConvertView).quickClose();
            }
        });

            convertView.findViewById(R.id.btn2).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    list.remove(position);
                    notifyDataSetChanged();
                    ((SwipeMenuLayout) finalConvertView).quickClose();
                }
            });

        return convertView;
    }

    class ViewHolder {
        TextView name_text,mes_text;
        ImageView image_per_mes;
    }

}
