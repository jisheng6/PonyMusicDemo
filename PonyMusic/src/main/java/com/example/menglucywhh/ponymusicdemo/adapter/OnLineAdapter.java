package com.example.menglucywhh.ponymusicdemo.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.menglucywhh.ponymusicdemo.R;
import com.example.menglucywhh.ponymusicdemo.bean.SongBean;
import com.example.menglucywhh.ponymusicdemo.fragment.OnLineFragment;
import com.example.menglucywhh.ponymusicdemo.okhttp.AbstractUiCallBack;
import com.example.menglucywhh.ponymusicdemo.okhttp.OkhttpUtils;
import com.example.menglucywhh.ponymusicdemo.presenter.SongPresenter;
import com.example.menglucywhh.ponymusicdemo.view.SongViewCallBack;
import com.example.menglucywhh.ponymusicdemo.view.activity.DetailActivity;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Menglucywhh on 2017/12/29.
 */

public class OnLineAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{


    Context context;

    String type = "2";//热歌
    private List<SongBean.SongListBean> song_list;

    public OnLineAdapter(Context context) {
        this.context = context;
        Fresco.initialize(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
     if(viewType==1){
         View view = View.inflate(context, R.layout.online_item01,null);
         return new ViewHolder1(view);
     }
        View view = View.inflate(context, R.layout.online_item02,null);
        return new ViewHolder2(view);

    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
      if(holder instanceof  ViewHolder1){
          if(position==0){
              ViewHolder1 viewHolder1 = (ViewHolder1) holder;
              viewHolder1.online_text01.setText("主打榜单");
          }else if(position==3){
              ViewHolder1 viewHolder1 = (ViewHolder1) holder;
              viewHolder1.online_text01.setText("分类榜单");
          }else if(position==11){
              ViewHolder1 viewHolder1 = (ViewHolder1) holder;
              viewHolder1.online_text01.setText("媒体榜单");
          }

      }else if(holder instanceof  ViewHolder2){
          String url =" http://tingapi.ting.baidu.com/v1/restserver/ting";

                ViewHolder2 viewHolder2 = (ViewHolder2) holder;
            if(position==1) {
//  type = 1-新歌榜,2-热歌榜,11-摇滚榜,12-爵士,16-流行,21-欧美金曲榜,22-经典老歌榜,23-情歌对唱榜,24-影视金曲榜,25-网络歌曲榜
                type = "2";//新歌
                Map<String,String> map = new HashMap<>();
                map.put("method","bai-du.ting.billboard.billList");
                map.put("format","json");
                map.put("type",type);
                map.put("size","15");
                map.put("offset","0");
                map.put("qq-pf-to","pcqq.group");


                 OkhttpUtils.getInstance().asy(map, url, new AbstractUiCallBack<SongBean>() {
                      @Override
                    public void success(SongBean songBean) {
                          if (songBean != null) {
                              song_list = songBean.getSong_list();
                              ((ViewHolder2) holder).item02_image.setImageURI(songBean.getBillboard().getPic_s260());
                              ((ViewHolder2) holder).item02_text01.setText
                                      ("1." + song_list.get(0).getTitle() + " - "
                                              + song_list.get(0).getAuthor());
                              ((ViewHolder2) holder).item02_text02.setText
                                      ("2." + song_list.get(1).getTitle() + " - "
                                              + song_list.get(1).getAuthor());
                              ((ViewHolder2) holder).item02_text03.setText
                                      ("3." + song_list.get(2).getTitle() + " - "
                                              + song_list.get(2).getAuthor());
                          }
                      }
                     @Override
                    public void fail(Exception e) {

                    }
                });

                 //条目点击事件,跳到详情页面
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                       // Toast.makeText(context, position+"", Toast.LENGTH_SHORT).show();
                        //传值到另一个activity
                       Intent intent = new Intent(context, DetailActivity.class);
                        intent.putExtra("type","2");
                        context.startActivity(intent);

                    }
                });

            } else if(position==2) {
                type = "1";//新歌
                Map<String,String> map = new HashMap<>();
                map.put("method","bai-du.ting.billboard.billList");
                map.put("format","json");
                map.put("type",type);
                map.put("size","15");
                map.put("offset","0");
                map.put("qq-pf-to","pcqq.group");
                OkhttpUtils.getInstance().asy(map, url, new AbstractUiCallBack<SongBean>() {
                    @Override
                    public void success(SongBean songBean) {
                        if (songBean != null) {
                            song_list = songBean.getSong_list();
                            ((ViewHolder2) holder).item02_image.setImageURI(songBean.getBillboard().getPic_s260());
                            ((ViewHolder2) holder).item02_text01.setText
                                    ("1." + song_list.get(0).getTitle() + " - "
                                            + song_list.get(0).getAuthor());
                            ((ViewHolder2) holder).item02_text02.setText
                                    ("2." + song_list.get(1).getTitle() + " - "
                                            + song_list.get(1).getAuthor());
                            ((ViewHolder2) holder).item02_text03.setText
                                    ("3." + song_list.get(2).getTitle() + " - "
                                            + song_list.get(2).getAuthor());
                        }
                    }
                    @Override
                    public void fail(Exception e) {

                    }
                });

                //条目点击事件,跳到详情页面
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                       // Toast.makeText(context, position+"", Toast.LENGTH_SHORT).show();
                        //传值到另一个activity

                        Intent intent = new Intent(context, DetailActivity.class);
                        intent.putExtra("type","1");
                        context.startActivity(intent);

                    }
                });
            }else if(position==4) {
                type = "25";//华语
                Map<String,String> map = new HashMap<>();
                map.put("method","bai-du.ting.billboard.billList");
                map.put("format","json");
                map.put("type",type);
                map.put("size","15");
                map.put("offset","0");
                map.put("qq-pf-to","pcqq.group");
                OkhttpUtils.getInstance().asy(map, url, new AbstractUiCallBack<SongBean>() {
                    @Override
                    public void success(SongBean songBean) {
                        if (songBean != null) {
                            List<SongBean.SongListBean> song_list = songBean.getSong_list();
                            ((ViewHolder2) holder).item02_image.setImageURI(songBean.getBillboard().getPic_s260());
                            ((ViewHolder2) holder).item02_text01.setText
                                    ("1." + song_list.get(0).getTitle() + " - "
                                            + song_list.get(0).getAuthor());
                            ((ViewHolder2) holder).item02_text02.setText
                                    ("2." + song_list.get(1).getTitle() + " - "
                                            + song_list.get(1).getAuthor());
                            ((ViewHolder2) holder).item02_text03.setText
                                    ("3." + song_list.get(2).getTitle() + " - "
                                            + song_list.get(2).getAuthor());
                        }
                    }
                    @Override
                    public void fail(Exception e) {

                    }

                });


                //条目点击事件,跳到详情页面
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                       // Toast.makeText(context, position+"", Toast.LENGTH_SHORT).show();
                        //传值到另一个activity

                        Intent intent = new Intent(context, DetailActivity.class);
                        intent.putExtra("type","25");
                        context.startActivity(intent);

                    }
                });
            }else if(position==5) {
                type = "21";//欧美
                Map<String,String> map = new HashMap<>();
                map.put("method","bai-du.ting.billboard.billList");
                map.put("format","json");
                map.put("type",type);
                map.put("size","15");
                map.put("offset","0");
                map.put("qq-pf-to","pcqq.group");
                OkhttpUtils.getInstance().asy(map, url, new AbstractUiCallBack<SongBean>() {
                    @Override
                    public void success(SongBean songBean) {
                        if (songBean != null) {
                            List<SongBean.SongListBean> song_list = songBean.getSong_list();
                            ((ViewHolder2) holder).item02_image.setImageURI(songBean.getBillboard().getPic_s260());
                            ((ViewHolder2) holder).item02_text01.setText
                                    ("1." + song_list.get(0).getTitle() + " - "
                                            + song_list.get(0).getAuthor());
                            ((ViewHolder2) holder).item02_text02.setText
                                    ("2." + song_list.get(1).getTitle() + " - "
                                            + song_list.get(1).getAuthor());
                            ((ViewHolder2) holder).item02_text03.setText
                                    ("3." + song_list.get(2).getTitle() + " - "
                                            + song_list.get(2).getAuthor());
                        }
                    }
                    @Override
                    public void fail(Exception e) {

                    }
                });
                //条目点击事件,跳到详情页面
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //Toast.makeText(context, position+"", Toast.LENGTH_SHORT).show();
                        //传值到另一个activity

                        Intent intent = new Intent(context, DetailActivity.class);
                        intent.putExtra("type","21");
                        context.startActivity(intent);

                    }
                });
            }else if(position==6) {
                type = "24";//影视
                Map<String,String> map = new HashMap<>();
                map.put("method","bai-du.ting.billboard.billList");
                map.put("format","json");
                map.put("type",type);
                map.put("size","15");
                map.put("offset","0");
                map.put("qq-pf-to","pcqq.group");
                OkhttpUtils.getInstance().asy(map, url, new AbstractUiCallBack<SongBean>() {
                    @Override
                    public void success(SongBean songBean) {
                        if (songBean != null) {
                            List<SongBean.SongListBean> song_list = songBean.getSong_list();
                            ((ViewHolder2) holder).item02_image.setImageURI(songBean.getBillboard().getPic_s260());
                            ((ViewHolder2) holder).item02_text01.setText
                                    ("1." + song_list.get(0).getTitle() + " - "
                                            + song_list.get(0).getAuthor());
                            ((ViewHolder2) holder).item02_text02.setText
                                    ("2." + song_list.get(1).getTitle() + " - "
                                            + song_list.get(1).getAuthor());
                            ((ViewHolder2) holder).item02_text03.setText
                                    ("3." + song_list.get(2).getTitle() + " - "
                                            + song_list.get(2).getAuthor());
                        }
                    }
                    @Override
                    public void fail(Exception e) {

                    }
                });
                //条目点击事件,跳到详情页面
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                       // Toast.makeText(context, position+"", Toast.LENGTH_SHORT).show();
                        //传值到另一个activity

                        Intent intent = new Intent(context, DetailActivity.class);
                        intent.putExtra("type","24");
                        context.startActivity(intent);

                    }
                });
            }else if(position==7) {
                type = "23";//情歌
                Map<String,String> map = new HashMap<>();
                map.put("method","bai-du.ting.billboard.billList");
                map.put("format","json");
                map.put("type",type);
                map.put("size","15");
                map.put("offset","0");
                map.put("qq-pf-to","pcqq.group");
                OkhttpUtils.getInstance().asy(map, url, new AbstractUiCallBack<SongBean>() {
                    @Override
                    public void success(SongBean songBean) {
                        if (songBean != null) {
                            List<SongBean.SongListBean> song_list = songBean.getSong_list();
                            ((ViewHolder2) holder).item02_image.setImageURI(songBean.getBillboard().getPic_s260());
                            ((ViewHolder2) holder).item02_text01.setText
                                    ("1." + song_list.get(0).getTitle() + " - "
                                            + song_list.get(0).getAuthor());
                            ((ViewHolder2) holder).item02_text02.setText
                                    ("2." + song_list.get(1).getTitle() + " - "
                                            + song_list.get(1).getAuthor());
                            ((ViewHolder2) holder).item02_text03.setText
                                    ("3." + song_list.get(2).getTitle() + " - "
                                            + song_list.get(2).getAuthor());
                        }
                    }
                    @Override
                    public void fail(Exception e) {

                    }
                });
                //条目点击事件,跳到详情页面
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                      //  Toast.makeText(context, position+"", Toast.LENGTH_SHORT).show();
                        //传值到另一个activity

                        Intent intent = new Intent(context, DetailActivity.class);
                        intent.putExtra("type","23");
                        context.startActivity(intent);

                    }
                });
            }else if(position==8) {
                type = "25";//网络
                Map<String,String> map = new HashMap<>();
                map.put("method","bai-du.ting.billboard.billList");
                map.put("format","json");
                map.put("type",type);
                map.put("size","15");
                map.put("offset","0");
                map.put("qq-pf-to","pcqq.group");
                OkhttpUtils.getInstance().asy(map, url, new AbstractUiCallBack<SongBean>() {
                    @Override
                    public void success(SongBean songBean) {
                        if (songBean != null) {
                            List<SongBean.SongListBean> song_list = songBean.getSong_list();
                            ((ViewHolder2) holder).item02_image.setImageURI(songBean.getBillboard().getPic_s260());
                            ((ViewHolder2) holder).item02_text01.setText
                                    ("1." + song_list.get(0).getTitle() + " - "
                                            + song_list.get(0).getAuthor());
                            ((ViewHolder2) holder).item02_text02.setText
                                    ("2." + song_list.get(1).getTitle() + " - "
                                            + song_list.get(1).getAuthor());
                            ((ViewHolder2) holder).item02_text03.setText
                                    ("3." + song_list.get(2).getTitle() + " - "
                                            + song_list.get(2).getAuthor());
                        }
                    }
                    @Override
                    public void fail(Exception e) {

                    }
                });
                //条目点击事件,跳到详情页面
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                       // Toast.makeText(context, position+"", Toast.LENGTH_SHORT).show();
                        //传值到另一个activity

                        Intent intent = new Intent(context, DetailActivity.class);
                        intent.putExtra("type","25");
                        context.startActivity(intent);

                    }
                });
            }else if(position==9) {
                type = "22";//经典老歌
                Map<String,String> map = new HashMap<>();
                map.put("method","bai-du.ting.billboard.billList");
                map.put("format","json");
                map.put("type",type);
                map.put("size","15");
                map.put("offset","0");
                map.put("qq-pf-to","pcqq.group");
                OkhttpUtils.getInstance().asy(map, url, new AbstractUiCallBack<SongBean>() {
                    @Override
                    public void success(SongBean songBean) {
                        if (songBean != null) {
                            List<SongBean.SongListBean> song_list = songBean.getSong_list();
                            ((ViewHolder2) holder).item02_image.setImageURI(songBean.getBillboard().getPic_s260());
                            ((ViewHolder2) holder).item02_text01.setText
                                    ("1." + song_list.get(0).getTitle() + " - "
                                            + song_list.get(0).getAuthor());
                            ((ViewHolder2) holder).item02_text02.setText
                                    ("2." + song_list.get(1).getTitle() + " - "
                                            + song_list.get(1).getAuthor());
                            ((ViewHolder2) holder).item02_text03.setText
                                    ("3." + song_list.get(2).getTitle() + " - "
                                            + song_list.get(2).getAuthor());
                        }
                    }
                    @Override
                    public void fail(Exception e) {

                    }
                });
                //条目点击事件,跳到详情页面
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                       // Toast.makeText(context, position+"", Toast.LENGTH_SHORT).show();
                        //传值到另一个activity

                        Intent intent = new Intent(context, DetailActivity.class);
                        intent.putExtra("type","22");
                        context.startActivity(intent);

                    }
                });
            }else if(position==10) {
                type = "11";//摇滚
                Map<String,String> map = new HashMap<>();
                map.put("method","bai-du.ting.billboard.billList");
                map.put("format","json");
                map.put("type",type);
                map.put("size","15");
                map.put("offset","0");
                map.put("qq-pf-to","pcqq.group");
                OkhttpUtils.getInstance().asy(map, url, new AbstractUiCallBack<SongBean>() {
                    @Override
                    public void success(SongBean songBean) {
                        if (songBean != null) {
                            List<SongBean.SongListBean> song_list = songBean.getSong_list();
                            ((ViewHolder2) holder).item02_image.setImageURI(songBean.getBillboard().getPic_s260());
                            ((ViewHolder2) holder).item02_text01.setText
                                    ("1." + song_list.get(0).getTitle() + " - "
                                            + song_list.get(0).getAuthor());
                            ((ViewHolder2) holder).item02_text02.setText
                                    ("2." + song_list.get(1).getTitle() + " - "
                                            + song_list.get(1).getAuthor());
                            ((ViewHolder2) holder).item02_text03.setText
                                    ("3." + song_list.get(2).getTitle() + " - "
                                            + song_list.get(2).getAuthor());
                        }
                    }
                    @Override
                    public void fail(Exception e) {

                    }
                });
                //条目点击事件,跳到详情页面
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                       // Toast.makeText(context, position+"", Toast.LENGTH_SHORT).show();
                        //传值到另一个activity

                        Intent intent = new Intent(context, DetailActivity.class);
                        intent.putExtra("type","11");
                        context.startActivity(intent);

                    }
                });
            }else if(position==12) {
                type = "21";//爵士
                Map<String,String> map = new HashMap<>();
                map.put("method","bai-du.ting.billboard.billList");
                map.put("format","json");
                map.put("type",type);
                map.put("size","15");
                map.put("offset","0");
                map.put("qq-pf-to","pcqq.group");
                OkhttpUtils.getInstance().asy(map, url, new AbstractUiCallBack<SongBean>() {
                    @Override
                    public void success(SongBean songBean) {
                        if (songBean != null) {
                            List<SongBean.SongListBean> song_list = songBean.getSong_list();
                            ((ViewHolder2) holder).item02_image.setImageURI(songBean.getBillboard().getPic_s260());
                            ((ViewHolder2) holder).item02_text01.setText
                                    ("1." + song_list.get(0).getTitle() + " - "
                                            + song_list.get(0).getAuthor());
                            ((ViewHolder2) holder).item02_text02.setText
                                    ("2." + song_list.get(1).getTitle() + " - "
                                            + song_list.get(1).getAuthor());
                            ((ViewHolder2) holder).item02_text03.setText
                                    ("3." + song_list.get(2).getTitle() + " - "
                                            + song_list.get(2).getAuthor());
                        }
                    }
                    @Override
                    public void fail(Exception e) {

                    }
                });
                //条目点击事件,跳到详情页面
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                      //  Toast.makeText(context, position+"", Toast.LENGTH_SHORT).show();
                        //传值到另一个activity

                        Intent intent = new Intent(context, DetailActivity.class);
                        intent.putExtra("type","21");
                        context.startActivity(intent);

                    }
                });
            }else if(position==13) {
                type = "1";//流行
                Map<String,String> map = new HashMap<>();
                map.put("method","bai-du.ting.billboard.billList");
                map.put("format","json");
                map.put("type",type);
                map.put("size","15");
                map.put("offset","0");
                map.put("qq-pf-to","pcqq.group");
                OkhttpUtils.getInstance().asy(map, url, new AbstractUiCallBack<SongBean>() {
                    @Override
                    public void success(SongBean songBean) {
                        if (songBean != null) {
                            List<SongBean.SongListBean> song_list = songBean.getSong_list();
                            ((ViewHolder2) holder).item02_image.setImageURI(songBean.getBillboard().getPic_s260());
                            ((ViewHolder2) holder).item02_text01.setText
                                    ("1." + song_list.get(0).getTitle() + " - "
                                            + song_list.get(0).getAuthor());
                            ((ViewHolder2) holder).item02_text02.setText
                                    ("2." + song_list.get(1).getTitle() + " - "
                                            + song_list.get(1).getAuthor());
                            ((ViewHolder2) holder).item02_text03.setText
                                    ("3." + song_list.get(2).getTitle() + " - "
                                            + song_list.get(2).getAuthor());
                        }
                    }
                    @Override
                    public void fail(Exception e) {

                    }
                });
                //条目点击事件,跳到详情页面
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                      //  Toast.makeText(context, position+"", Toast.LENGTH_SHORT).show();
                        //传值到另一个activity

                        Intent intent = new Intent(context, DetailActivity.class);
                        intent.putExtra("type","1");
                        context.startActivity(intent);

                    }
                });
            }else if(position==14) {
                type = "23";//爵士
                Map<String,String> map = new HashMap<>();
                map.put("method","bai-du.ting.billboard.billList");
                map.put("format","json");
                map.put("type",type);
                map.put("size","15");
                map.put("offset","0");
                map.put("qq-pf-to","pcqq.group");
                OkhttpUtils.getInstance().asy(map, url, new AbstractUiCallBack<SongBean>() {
                    @Override
                    public void success(SongBean songBean) {
                        if (songBean != null) {
                            List<SongBean.SongListBean> song_list = songBean.getSong_list();
                            ((ViewHolder2) holder).item02_image.setImageURI(songBean.getBillboard().getPic_s260());
                            ((ViewHolder2) holder).item02_text01.setText
                                    ("1." + song_list.get(0).getTitle() + " - "
                                            + song_list.get(0).getAuthor());
                            ((ViewHolder2) holder).item02_text02.setText
                                    ("2." + song_list.get(1).getTitle() + " - "
                                            + song_list.get(1).getAuthor());
                            ((ViewHolder2) holder).item02_text03.setText
                                    ("3." + song_list.get(2).getTitle() + " - "
                                            + song_list.get(2).getAuthor());
                        }
                    }
                    @Override
                    public void fail(Exception e) {

                    }
                });
                //条目点击事件,跳到详情页面
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //Toast.makeText(context, position+"", Toast.LENGTH_SHORT).show();
                        //传值到另一个activity

                        Intent intent = new Intent(context, DetailActivity.class);
                        intent.putExtra("type","23");
                        context.startActivity(intent);

                    }
                });
            }else if(position==15) {
                type = "22";//爵士
                Map<String,String> map = new HashMap<>();
                map.put("method","bai-du.ting.billboard.billList");
                map.put("format","json");
                map.put("type",type);
                map.put("size","15");
                map.put("offset","0");
                map.put("qq-pf-to","pcqq.group");
                OkhttpUtils.getInstance().asy(map, url, new AbstractUiCallBack<SongBean>() {
                    @Override
                    public void success(SongBean songBean) {
                        if (songBean != null) {
                            List<SongBean.SongListBean> song_list = songBean.getSong_list();
                            ((ViewHolder2) holder).item02_image.setImageURI(songBean.getBillboard().getPic_s260());
                            ((ViewHolder2) holder).item02_text01.setText
                                    ("1." + song_list.get(0).getTitle() + " - "
                                            + song_list.get(0).getAuthor());
                            ((ViewHolder2) holder).item02_text02.setText
                                    ("2." + song_list.get(1).getTitle() + " - "
                                            + song_list.get(1).getAuthor());
                            ((ViewHolder2) holder).item02_text03.setText
                                    ("3." + song_list.get(2).getTitle() + " - "
                                            + song_list.get(2).getAuthor());
                        }
                    }
                    @Override
                    public void fail(Exception e) {

                    }
                });
                //条目点击事件,跳到详情页面
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                       // Toast.makeText(context, position+"", Toast.LENGTH_SHORT).show();
                        //传值到另一个activity

                        Intent intent = new Intent(context, DetailActivity.class);
                        intent.putExtra("type","22");
                        context.startActivity(intent);

                    }
                });
            }

        }

    }

    @Override
    public int getItemViewType(int position) {
        if(position==0) {
            return 1;//这三个地方都是第一个条目.主打榜单
        }else if(position==3){
            return 1;
        }else if(position==11){
            return 1;
            //另一个条目
        }
        return 2;
    }

    @Override
    public int getItemCount() {
        return 16;
    }


    public static class ViewHolder1 extends RecyclerView.ViewHolder {

        private final TextView online_text01;

        public ViewHolder1(View itemView) {
            super(itemView);
            online_text01 = itemView.findViewById(R.id.online_text01);
        }
    }


    public static class ViewHolder2 extends RecyclerView.ViewHolder {


        private final SimpleDraweeView item02_image;
        private final TextView item02_text01;
        private final TextView item02_text02;
        private final TextView item02_text03;

        public ViewHolder2(View itemView) {
            super(itemView);
            item02_image = itemView.findViewById(R.id.item02_image);
            item02_text01 = itemView.findViewById(R.id.item02_text01);
            item02_text02 = itemView.findViewById(R.id.item02_text02);
            item02_text03 = itemView.findViewById(R.id.item02_text03);

        }
    }

}
