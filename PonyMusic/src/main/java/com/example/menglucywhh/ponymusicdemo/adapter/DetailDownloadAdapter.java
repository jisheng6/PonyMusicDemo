package com.example.menglucywhh.ponymusicdemo.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.menglucywhh.ponymusicdemo.R;
import com.example.menglucywhh.ponymusicdemo.bean.PlayBean;
import com.example.menglucywhh.ponymusicdemo.bean.SongBean;
import com.example.menglucywhh.ponymusicdemo.bean.Titlebean;
import com.example.menglucywhh.ponymusicdemo.common.APIFactory;
import com.example.menglucywhh.ponymusicdemo.common.AbstractObserver;

import com.example.menglucywhh.ponymusicdemo.downloadutils.FileDownloader;
import com.example.menglucywhh.ponymusicdemo.view.activity.AuthorActivity;
import com.example.menglucywhh.ponymusicdemo.view.activity.DownLoadActivity;
import com.example.menglucywhh.ponymusicdemo.view.activity.PlayerActivity;
import com.facebook.drawee.view.SimpleDraweeView;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Menglucywhh on 2017/12/29.
 */

public class DetailDownloadAdapter extends RecyclerView.Adapter<DetailDownloadAdapter.MyViewHolder> {

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler(){

        @Override//信息
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    int size = msg.getData().getInt("size");
                    downloadbar.setProgress(size);
                    float result = (float)downloadbar.getProgress()/ (float)downloadbar.getMax();
                   int p = (int)(result*100);
                    resultView.setText(p+"%");
                    if(downloadbar.getProgress()==downloadbar.getMax())
                        // Toast.makeText(SmartDownloadActivity.this, R.string.success, 1).show();
                        break;

                case -1:
                    //  Toast.makeText(SmartDownloadActivity.this, R.string.error, 1).show();
                    break;
            }

        }
    };
    List<SongBean.SongListBean> list;
    Context context;
    private View contentView;
    private View parent;
    private PopupWindow popupWindow;
    private TextView pop_name;
    private TextView pop_chakan;
    private TextView pop_fenxiang;
    private TextView pop_download;
    private ProgressBar downloadbar;
    private EditText pathText;
    private TextView resultView;
    private Button button;
    public DetailDownloadAdapter(Context context) {
        this.context = context;
    }

    public void addData(List<SongBean.SongListBean> song) {
        if(list==null){
            list = new ArrayList<>();
        }
        list.addAll(song);
        notifyDataSetChanged();
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.detail_item02,null);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        /**
         * 下面的 recyclerview
         *pic_big,,,title,,,author+album_title
         *
         * */
         holder.detail02_image.setImageURI(list.get(position).getPic_big());
         holder.detail02_text01.setText(list.get(position).getTitle());
        holder.detail02_text02.setText(list.get(position).getAuthor()+" - "+list.get(position).getAlbum_title());

        //popupwindow
        //contentView = View.inflate(context, R.layout.detail_popup, null);

        parent = View.inflate(context, R.layout.activity_main, null);

        popupWindow = new PopupWindow(contentView, ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setTouchable(true);//设置窗体可以触摸,,,默认就是true
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);//设置窗体外部可以触摸
        popupWindow.setBackgroundDrawable(new BitmapDrawable());//设置背景
        holder.xuanxiang.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                View contentView = View.inflate(context, R.layout.detail_popup, null);
                String name = list.get(position).getTitle();
                pop_name = contentView.findViewById(R.id.pop_name);
                pop_fenxiang = contentView.findViewById(R.id.pop_fenxiang);
                pop_download = contentView.findViewById(R.id.pop_download);
                pop_chakan = contentView.findViewById(R.id.pop_chakan);
                pop_name.setText(name);
                //xianShiPop();

                final AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setView(contentView);
                final AlertDialog alertDialog = builder.create();


                alertDialog.show();


                //查看歌手信息,跳转页面,,传一个歌手id:ting_uid
                pop_chakan.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                     //   Toast.makeText(context, list.get(position).getTing_uid(), Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(context, AuthorActivity.class);
                        intent.putExtra("ting_uid",list.get(position).getTing_uid());
                        context.startActivity(intent);
                       // popupWindow.dismiss();
                        alertDialog.dismiss();

                    }
                });

                pop_fenxiang.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                      //  popupWindow.dismiss();
                        alertDialog.dismiss();
                    }
                });

                pop_download.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        alertDialog.dismiss();
                      //  popupWindow.dismiss();
                        //=---------
                        String songid=list.get(position).getSong_id();

                        //http://tingapi.ting.baidu.com/v1/restserver/ting?
                        // format=json&method=baidu.ting.song.play&songid=877578

                        String autor=list.get(position).getAuthor();
                        String tittle=list.get(position).getTitle();
                        Intent it=new Intent(context, DownLoadActivity.class);
                        it.putExtra("songid",songid);
                        it.putExtra("autor",autor);
                        it.putExtra("tittle",tittle);
                        context.startActivity(it);

                    }
                });
            }
        });

        //条目的点击事件
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 //播放音乐
                EventBus.getDefault().postSticky(new Titlebean(list.get(position).getTitle(),list.get(position).getAuthor(),list.get(position).getPic_small()));
//list.get(position).getSong_id(),
                EventBus.getDefault().postSticky(list.get(position).getSong_id());//在线音乐传送songid
                Intent intent = new Intent(context, PlayerActivity.class);
                intent.putExtra("flag",0);//0为在线音乐

                //传一个songid
                intent.putExtra("songid",list.get(position).getSong_id());
                context.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return list==null?0:list.size();
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder{

        private final SimpleDraweeView detail02_image;
        private final TextView detail02_text01;
        private final TextView detail02_text02;
        private final ImageView xuanxiang;

        public MyViewHolder(View itemView) {
            super(itemView);
            detail02_image = itemView.findViewById(R.id.detail02_image);
            detail02_text01 = itemView.findViewById(R.id.detail02_text01);
            detail02_text02 = itemView.findViewById(R.id.detail02_text02);
            xuanxiang = itemView.findViewById(R.id.xuanxiang);

        }
    }

    //按钮的点击事件
    public void xianShiPop() {
        //显示一个窗体...只做显示的功能
        //popupWindow.showAsDropDown(button);//展示在控件的左下方
        //popupWindow.showAsDropDown(button, -100, 40);
        /**
         * 在父窗体的某个位置展示
         * View parent,
         * int gravity, 重力/方向  Gravity.BOTTOM...Gravity.CENTER
         * int x,
         * int y
         */
        popupWindow.showAtLocation(parent, Gravity.CENTER, 0, 0);
    }




}
