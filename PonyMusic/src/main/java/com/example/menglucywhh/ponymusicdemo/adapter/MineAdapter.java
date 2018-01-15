package com.example.menglucywhh.ponymusicdemo.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.RippleDrawable;
import android.media.Image;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.menglucywhh.ponymusicdemo.R;
import com.example.menglucywhh.ponymusicdemo.bean.Model;
import com.example.menglucywhh.ponymusicdemo.view.activity.AuthorActivity;

import java.util.List;

/**
 * Created by Menglucywhh on 2018/1/3.
 */

public class MineAdapter extends BaseAdapter {
    List<Model> lists;
    Context context;
    public MineAdapter(List<Model> lists, Context context) {
        this.context = context;
        this.lists = lists;
    }

    @Override
    public int getCount() {
        return lists==null?0:lists.size();
    }

    @Override
    public Object getItem(int i) {
        return lists.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if(view==null){
            view = View.inflate(context, R.layout.item_list2,null);

            holder = new ViewHolder();
            holder.text_song = view.findViewById(R.id.text_name);
            holder.text_autor = view.findViewById(R.id.text_autor);
            holder.image = view.findViewById(R.id.simple);
            holder.imageXuan = view.findViewById(R.id.image_more);
            view.setTag(holder);

        }else{
            holder = (ViewHolder) view.getTag();
        }
        holder.text_song.setText(lists.get(i).getText_song());
        holder.text_autor.setText(lists.get(i).getText_singer());

        holder.imageXuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View contentView = View.inflate(context, R.layout.mine_popup, null);
                String name = lists.get(i).getText_song();
                TextView pop_name = contentView.findViewById(R.id.pop_name);
                TextView pop_fenxiang = contentView.findViewById(R.id.pop_fenxiang);
                TextView pop_sheling = contentView.findViewById(R.id.pop_sheling);
                TextView pop_xinxi = contentView.findViewById(R.id.pop_xinxi);
                TextView pop_shanchu = contentView.findViewById(R.id.pop_shanchu);
                pop_name.setText(name);
                //xianShiPop();

                final AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setView(contentView);
                final AlertDialog alertDialog = builder.create();

                alertDialog.show();

                pop_fenxiang.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        alertDialog.dismiss();
                    }
                });

                pop_sheling.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        alertDialog.dismiss();
                    }
                });
                pop_xinxi.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        alertDialog.dismiss();
                        //查看歌手信息,跳到信息页面
                        Intent intent = new Intent(context, AuthorActivity.class);
                        int uid =1220;
                       String ting_uid = String.valueOf(uid);

                        intent.putExtra("ting_uid",ting_uid);
                        context.startActivity(intent);
                    }
                });
                pop_shanchu.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        alertDialog.dismiss();
                        lists.remove(i);
                        notifyDataSetChanged();
                    }
                });
            }
        });
        return view;
    }

    public class ViewHolder{
        TextView text_song;
        TextView text_autor;
        ImageView image;
        ImageView imageXuan;
    }
}
