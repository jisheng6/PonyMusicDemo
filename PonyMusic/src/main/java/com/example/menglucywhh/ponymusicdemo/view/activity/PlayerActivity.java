package com.example.menglucywhh.ponymusicdemo.view.activity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.menglucywhh.ponymusicdemo.MainActivity;
import com.example.menglucywhh.ponymusicdemo.R;
import com.example.menglucywhh.ponymusicdemo.bean.Lrc;
import com.example.menglucywhh.ponymusicdemo.bean.Model;
import com.example.menglucywhh.ponymusicdemo.bean.PlayBean;
import com.example.menglucywhh.ponymusicdemo.bean.Timebean;
import com.example.menglucywhh.ponymusicdemo.common.APIFactory;
import com.example.menglucywhh.ponymusicdemo.common.AbstractObserver;
import com.example.menglucywhh.ponymusicdemo.fragment.CoverFragment;
import com.example.menglucywhh.ponymusicdemo.fragment.LrcFragment;
import com.example.menglucywhh.ponymusicdemo.service.MyService;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PlayerActivity extends AppCompatActivity {

    @BindView(R.id.image_guan)
    ImageView imageGuan;
    @BindView(R.id.text_songname)
    TextView textSongname;
    @BindView(R.id.text_songautor)
    TextView textSongautor;
    @BindView(R.id.linear1)
    LinearLayout linear1;
    @BindView(R.id.image_shang)
    ImageView imageShang;
    @BindView(R.id.radio_playm)
    ImageView radioPlaym;
    @BindView(R.id.image_xia)
    ImageView imageXia;
    @BindView(R.id.linear2)
    LinearLayout linear2;
    @BindView(R.id.seekbar)
    SeekBar seekbar;
    @BindView(R.id.linear3)
    LinearLayout linear3;
    @BindView(R.id.viewpager)
    ViewPager viewpager;
    @BindView(R.id.activity_player2)
    RelativeLayout activityPlayer2;
    @BindView(R.id.text_start)
    TextView textStart;
    @BindView(R.id.text_stop)
    TextView textStop;
    private ServiceConnection connection;
    private Intent service;
    public static MyService.MyBinder binder;
    String musicState = "播放中";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);
        ButterKnife.bind(this);

        EventBus.getDefault().register(this);

        radioPlaym.setTag(1);//1为 播放中,是pause的图片
        radioPlaym.setImageResource(R.drawable.ic_play_btn_pause);
        final Intent intent = getIntent();
        //判断接收到的值是 从本地传来 还是 在线音乐
        int flag = intent.getIntExtra("flag", 3);//默认值3
        if (flag == 0) {//flag为0是在线音乐
            radioPlaym.setTag(1);
            radioPlaym.setImageResource(R.drawable.ic_play_btn_pause);
            String songid = intent.getStringExtra("songid");
            EventBus.getDefault().postSticky(new Lrc(songid));
            musicState = "播放中";
            EventBus.getDefault().postSticky(musicState);//"暂停中"为设置继续图
            // http://tingapi.ting.baidu.com/v1/restserver/ting?method=baidu.ting.song.play&songid=877578

            Map<String, String> map = new HashMap<>();
            map.put("method", "baidu.ting.song.play");
            map.put("songid", songid);
            APIFactory.getInstance().get("/v1/restserver/ting", map, new AbstractObserver<PlayBean>() {

                @Override
                public void onSuccess(PlayBean playBean) {
                    if (playBean != null) {
                        //show_link
                        System.out.println(playBean.getSonginfo().getAuthor());
                        textSongname.setText(playBean.getSonginfo().getTitle());
                        textSongautor.setText(playBean.getSonginfo().getAuthor());

                        final String path = playBean.getBitrate().getShow_link();

                        //创建出service对象
                        service = new Intent(PlayerActivity.this, MyService.class);

                        //activity与服务连接上调用的方法
//IBinder service中间人对象....MyService中的onBind方法返回的对象
// activity与服务断开连接时调用的方法
                        connection = new ServiceConnection() {

                            @Override
                            public void onServiceConnected(ComponentName componentName, IBinder service) {

                                binder = (MyService.MyBinder) service;
                                binder.playInBinder(path);
                            }

                            //activity与服务连接上调用的方法
                            //IBinder service中间人对象....MyService中的onBind方法返回的对象
                            @Override
                            public void onServiceDisconnected(ComponentName componentName) {
                                // activity与服务断开连接时调用的方法
                            }
                        };

                        //在绑定服务之前
                        startService(service);

                        PlayerActivity.this.bindService(service, connection, BIND_AUTO_CREATE);

                    }
                }

                @Override
                public void onFailure(int code) {
                    System.out.println("网慢");
                }
            });
        }else if(flag==1){//本地音乐
            //可以取到model
            Model model = (Model) intent.getSerializableExtra("model");
            final String path = model.getPath();
            textSongname.setText(model.getText_song());
            textSongautor.setText(model.getText_singer());
            radioPlaym.setTag(1);
            radioPlaym.setImageResource(R.drawable.ic_play_btn_pause);
            musicState = "播放中";
            EventBus.getDefault().postSticky(musicState);//"暂停中"为设置继续图
            service = new Intent(PlayerActivity.this, MyService.class);
            //连接服务的对象
            // activity与服务断开连接时调用的方法
//activity与服务连接上调用的方法
//IBinder service中间人对象....MyService中的onBind方法返回的对象
// 链接后返回的中间人对象
/*//调用到了service里面的方法
binder.jieQianBinder();*/
            connection = new ServiceConnection() {

                @Override
                public void onServiceDisconnected(ComponentName name) {
                    // activity与服务断开连接时调用的方法

                }

                //activity与服务连接上调用的方法
                //IBinder service中间人对象....MyService中的onBind方法返回的对象
                @Override
                public void onServiceConnected(ComponentName name, IBinder service) {
                    // 链接后返回的中间人对象
                    binder = (MyService.MyBinder) service;
                    binder.playInBinder(path);

				/*//调用到了service里面的方法
				binder.jieQianBinder();*/
                }
            };

            //在绑定服务之前,,,调用startService来启动一下服务,,,,使用的是这种混合启动服务的方式...因为只绑定在退出的时候同生共死,服务销毁,,,而starService方式只要不调用stopService方法,服务不销毁
            startService(service);

            PlayerActivity.this.bindService(service, connection, BIND_AUTO_CREATE);
        }


        final List<Fragment> list = new ArrayList<>();
        list.add(new CoverFragment());
        list.add(new LrcFragment());

        viewpager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return list.get(position);
            }

            @Override
            public int getCount() {
                return list.size();
            }
        });

        //拖动条的监听事件
        seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            //该方法拖动进度条停止拖动的时候调用
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                seekBar.setProgress(seekBar.getProgress());
                Message message = new Message();
               message.obj = seekBar.getProgress();//把当前的进度传过去
               message.what=1;
               MyService.handler.sendMessage(message);
            }
        });

    }

    //非黏性事件
    @Subscribe(sticky = false)
    public void GetEvent(final Timebean timebean) {
        final SimpleDateFormat dateFormat = new SimpleDateFormat("mm:ss");

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                textStart.setText(dateFormat.format(new Date(timebean.currentPosition)));
                textStop.setText(dateFormat.format(new Date(timebean.duration)));
                seekbar.setProgress(timebean.currentPosition * 100 / timebean.duration);
            }
        });


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (connection != null) {
            unbindService(connection);
            connection = null;
        }
        EventBus.getDefault().unregister(this);
    }


    @OnClick({R.id.image_guan, R.id.image_shang, R.id.radio_playm, R.id.image_xia, R.id.seekbar})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.image_guan:
                finish();
                break;
            case R.id.image_shang:
                break;
            case R.id.radio_playm:
                binder.stopInBinder();

               int tag= (int) radioPlaym.getTag();
               if(tag==1){
                   radioPlaym.setTag(2);
                   radioPlaym.setImageResource(R.drawable.ic_play_btn_play);
                   //让mainactivity联动
                   musicState = "暂停中";
                   //EventBus.getDefault().postSticky(musicState);//false为设置继续图
               }else{
                   radioPlaym.setTag(1);
                   radioPlaym.setImageResource(R.drawable.ic_play_btn_pause);
                   musicState = "播放中";
                  // EventBus.getDefault().postSticky(musicState);//false为设置继续图
               }
                EventBus.getDefault().postSticky(musicState);//1为设置继续图

                  break;
            case R.id.image_xia:
                break;
            case R.id.seekbar:
                break;
        }

    }


}
