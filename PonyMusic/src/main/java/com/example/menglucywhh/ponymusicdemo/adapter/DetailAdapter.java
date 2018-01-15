package com.example.menglucywhh.ponymusicdemo.adapter;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.menglucywhh.ponymusicdemo.R;
import com.example.menglucywhh.ponymusicdemo.bean.SearchBean;
import com.example.menglucywhh.ponymusicdemo.bean.SongBean;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Menglucywhh on 2017/12/29.
 */

public class DetailAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    List<SongBean> list;
    Context context;

    public DetailAdapter(Context context) {
        this.context = context;
    }

    public void addData(SongBean songBean) {
        if(list==null){
            list = new ArrayList<>();
        }

        list.add(songBean);
        notifyDataSetChanged();
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType==1){
            View view = LayoutInflater.from(context).inflate(R.layout.detail_item01, parent, false);
            //View view = View.inflate(context, R.layout.detail_item01,null);
            return new MyViewHolder1(view);
        }
        View view = View.inflate(context, R.layout.detail_recy,null);
        return new MyViewHolder2(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof MyViewHolder1){
             /*
       pic_s640
    //billboard里面的name
     //最近更新update_date
    //comment 实时展现百度音乐最热门欧美歌曲排行*/
            MyViewHolder1 myViewHolder1 = (MyViewHolder1) holder;

            myViewHolder1.detail01_image.setImageURI(list.get(0).getBillboard().getPic_s640());
            myViewHolder1.detail01_text01.setText(list.get(0).getBillboard().getName());
            myViewHolder1.detail01_text02.setText("最近更新 : "+list.get(0).getBillboard().getUpdate_date());
            myViewHolder1.detail01_text03.setText(list.get(0).getBillboard().getComment());
       if(list.get(0).getBillboard().getName().equals("热歌榜")){
           //爵士name为空null 流行,
     myViewHolder1.detail01_linear.setBackgroundResource(R.drawable.hotmusicbejing);

       }else if(list.get(0).getBillboard().getName().equals("新歌榜")){
           //爵士name为空null 流行,
           myViewHolder1.detail01_linear.setBackgroundResource(R.drawable.newmusicbeijing);

       }else if(list.get(0).getBillboard().getName().equals("摇滚榜")){
           //爵士name为空null 流行,
           myViewHolder1.detail01_linear.setBackgroundResource(R.drawable.yaogunbeijing);

       }else if(list.get(0).getBillboard().getName().equals("欧美金曲榜")){
           //爵士name为空null 流行,
           myViewHolder1.detail01_linear.setBackgroundResource(R.drawable.oumei);

       }else if(list.get(0).getBillboard().getName().equals("经典老歌榜")){
           //爵士name为空null 流行,
           myViewHolder1.detail01_linear.setBackgroundResource(R.drawable.jingdianbeijing);

       }else if(list.get(0).getBillboard().getName().equals("情歌对唱榜")){
           //爵士name为空null 流行,
           myViewHolder1.detail01_linear.setBackgroundResource(R.drawable.qinggebeijing);

       }else if(list.get(0).getBillboard().getName().equals("影视金曲榜")){
           //爵士name为空null 流行,
           myViewHolder1.detail01_linear.setBackgroundResource(R.drawable.yingshibeijing);

       }else if(list.get(0).getBillboard().getName().equals("网络歌曲榜")){
           //爵士name为空null 流行,
           myViewHolder1.detail01_linear.setBackgroundResource(R.drawable.wangluobeijing);

       }
        }
        if(holder instanceof MyViewHolder2){
            MyViewHolder2 myViewHolder2 = (MyViewHolder2) holder;
           myViewHolder2.detail_recy.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false));
          DetailDownloadAdapter detailDownloadAdapter = new DetailDownloadAdapter(context);
          detailDownloadAdapter.addData(list.get(0).getSong_list());
          myViewHolder2.detail_recy.setAdapter(detailDownloadAdapter);
        }
    }


    @Override
    public int getItemViewType(int position) {
        if(position==0){
            //第一个条目
            return 1;
        }
        return 2;
    }

    @Override
    public int getItemCount() {
        return 2;
    }



    public static class MyViewHolder1 extends RecyclerView.ViewHolder{

        private final SimpleDraweeView detail01_image;

        private final TextView detail01_text01;
        private final TextView detail01_text02;
        private final TextView detail01_text03;
        private final LinearLayout detail01_linear;

        public MyViewHolder1(View itemView) {
            super(itemView);
            detail01_image = itemView.findViewById(R.id.detail01_image);
            detail01_text01 = itemView.findViewById(R.id.detail01_text01);
            detail01_text02 = itemView.findViewById(R.id.detail01_text02);
            detail01_text03 = itemView.findViewById(R.id.detail01_text03);
            detail01_linear = itemView.findViewById(R.id.detail01_linear);


        }
    }
    public static class MyViewHolder2 extends RecyclerView.ViewHolder{

        private final RecyclerView detail_recy;

        public MyViewHolder2(View itemView) {
            super(itemView);
            detail_recy = itemView.findViewById(R.id.detail_recy);
        }
    }


}
