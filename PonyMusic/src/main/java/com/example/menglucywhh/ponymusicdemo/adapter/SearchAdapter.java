package com.example.menglucywhh.ponymusicdemo.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.menglucywhh.ponymusicdemo.MainActivity;
import com.example.menglucywhh.ponymusicdemo.R;
import com.example.menglucywhh.ponymusicdemo.bean.SearchBean;
import com.example.menglucywhh.ponymusicdemo.bean.Titlebean;
import com.example.menglucywhh.ponymusicdemo.view.activity.DownLoadActivity;
import com.example.menglucywhh.ponymusicdemo.view.activity.PlayerActivity;
import com.facebook.drawee.backends.pipeline.Fresco;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Menglucywhh on 2017/12/29.
 */

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.MyViewHolder> {

    List<SearchBean.SongBean> list;
    Context context;
    private PopupWindow popupWindow;
    private TextView pop_name;
    private View contentView;
    private TextView pop_fenxiang;
    private TextView pop_download;
    private TextView pop_chakan;

    public SearchAdapter(Context context) {
        this.context = context;
    }

    public void addData(List<SearchBean.SongBean> song) {
        if(list==null){
            list = new ArrayList<>();
        }
        list.clear();
        list.addAll(song);
        notifyDataSetChanged();
    }

    public void clearList(){
        if(list!=null) {
            list.clear();
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.search_item,null);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.music_name.setText(list.get(position).getSongname());
        holder.author_name.setText(list.get(position).getArtistname());

        holder.xuanxiang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //popupwindow
                contentView = View.inflate(context, R.layout.search_popup, null);
               // Toast.makeText(context, list.get(position).getSongname(), Toast.LENGTH_SHORT).show();
                String name = list.get(position).getSongname();

                pop_name = contentView.findViewById(R.id.pop_name);
                pop_fenxiang = contentView.findViewById(R.id.pop_fenxiang);
                pop_download = contentView.findViewById(R.id.pop_download);
                pop_chakan = contentView.findViewById(R.id.pop_chakan);
                pop_name.setText(name);
                //xianShiPop();

                final AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setView(contentView);//设置自定义布局样式弹窗
                final AlertDialog alertDialog = builder.create();

                alertDialog.show();

                pop_fenxiang.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //弹出窗体消失
                        alertDialog.dismiss();
                    }
                });

                pop_download.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //弹出窗体消失
                        //=---------
                        String songid=list.get(position).getSongid();

                        //http://tingapi.ting.baidu.com/v1/restserver/ting?
                        // format=json&method=baidu.ting.song.play&songid=877578

                        String autor=list.get(position).getArtistname();
                        String tittle=list.get(position).getSongname();
                        Intent it=new Intent(context, DownLoadActivity.class);
                        it.putExtra("songid",songid);
                        it.putExtra("autor",autor);
                        it.putExtra("tittle",tittle);
                        context.startActivity(it);
                        alertDialog.dismiss();
                    }
                });
          }
        });




        //条目点击播放
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // Toast.makeText(context, "songid : "+list.get(position).getSongid(), Toast.LENGTH_SHORT).show();
                //播放音乐
                EventBus.getDefault().postSticky(new Titlebean(list.get(position).getSongname(),list.get(position).getArtistname(),null));
//list.get(position).getSong_id(),
                EventBus.getDefault().postSticky(list.get(position).getSongid());//在线音乐传送songid
                Intent intent = new Intent(context, PlayerActivity.class);
                intent.putExtra("flag",0);//0为在线音乐

                //传一个songid
                intent.putExtra("songid",list.get(position).getSongid());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder{

        private final TextView music_name;
        private final TextView author_name;
        private final ImageView xuanxiang;

        public MyViewHolder(View itemView) {
            super(itemView);
            music_name = itemView.findViewById(R.id.music_name);
            author_name = itemView.findViewById(R.id.author_name);
            xuanxiang = itemView.findViewById(R.id.xuanxiang);
        }
    }
}
