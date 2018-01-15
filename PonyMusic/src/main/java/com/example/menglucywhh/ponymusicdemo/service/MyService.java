package com.example.menglucywhh.ponymusicdemo.service;

import android.app.Activity;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnPreparedListener;
import android.net.Uri;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;

import com.example.menglucywhh.ponymusicdemo.bean.Timebean;
import com.example.menglucywhh.ponymusicdemo.fragment.CoverFragment;
import com.example.menglucywhh.ponymusicdemo.fragment.LrcFragment;

import org.greenrobot.eventbus.EventBus;

import java.security.PublicKey;
import java.util.Timer;
import java.util.TimerTask;

public class MyService extends Service{
	private static final String TAG = "MyService";
	int flag=0;
	private Timer timer;
	//创建中间人对象
	MyBinder binder = new MyBinder();
     private TimerTask task;
	public static MediaPlayer mediaPlayer;

	public static Handler handler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			if(msg.what==1){

				//接收到PLayerActivity的seekbar传来的进度
				int progress = (int) msg.obj;
				int duration =mediaPlayer.getDuration();

				int a = progress*duration/100;
				mediaPlayer.seekTo(a);//播放到相应位置

			}else if(msg.what==2){
				int time = (int) msg.obj;
				//Log.i("-----",time+"");
				mediaPlayer.seekTo(time);
			}
		}
	};

	//服务创建的时候
	@Override
	public void onCreate() {
		Log.i(TAG, "onCreate");
		super.onCreate();
		
		mediaPlayer = new MediaPlayer();
		timer = new Timer();

	}
	
	//绑定服务的时候
	@Override
	public IBinder onBind(Intent intent) {
		Log.i(TAG, "onBind");
		// 返回binder对象
		return binder;
	}
	//解除绑定
	@Override
	public boolean onUnbind(Intent intent) {

		Log.i(TAG, "onUnbind");
		return super.onUnbind(intent);
	}
	//销毁的时候
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		Log.i(TAG, "onDestroy");
		
		if (mediaPlayer != null) {//释放
			mediaPlayer.release();
			mediaPlayer = null;
		}
		
		super.onDestroy();
	}
	
	public void jieQian(){
		Log.i("---", "你数同意了,借你50块");
	}
	//服务中播放音乐的方法
	public void playMusic(String show_link){
		Log.i(TAG, "play");
		mediaPlayer.reset();
		
		try {

			mediaPlayer.setDataSource(this, Uri.parse(show_link));
			
			mediaPlayer.prepareAsync();
			mediaPlayer.setOnPreparedListener(new OnPreparedListener() {
				
				@Override
				public void onPrepared(MediaPlayer mp) {
					Log.i(TAG, "ok");
					mediaPlayer.start();
                    flag=0;
					task = new TimerTask() {
						@Override
						public void run() {
						int currentPosition = mediaPlayer.getCurrentPosition();
						int duration = mediaPlayer.getDuration();
							EventBus.getDefault().postSticky(new Timebean(currentPosition,duration));
						//	Log.i("------","走一次");
							//发送给歌词的fragment,把当前的时间
							Message message = new Message();
							long time =(long)currentPosition;
							message.obj=time;
							message.what=0;
							LrcFragment.handler.sendMessage(message);

							}
					};

					timer.schedule(task,0,1000);
				}
			});
		
		} catch (Exception e) {

			e.printStackTrace();
		}
		
	}
	
	public void stopMusic(){
		if (mediaPlayer != null&&flag==0) {
			mediaPlayer.pause();
			CoverFragment.albumcover.pause();//暂停的动画
              task.cancel();
			flag=1;
		}else{
			CoverFragment.albumcover.start();//继续的动画
			mediaPlayer.start();
			flag=0;
			TimerTask task = new TimerTask() {
				@Override
				public void run() {
					int currentPosition = mediaPlayer.getCurrentPosition();
					int duration = mediaPlayer.getDuration();
					EventBus.getDefault().postSticky(new Timebean(currentPosition,duration));
					//Log.i("------","走一次");
					long time=(long) currentPosition;
					Message message=new Message();
					message.obj=time;
					message.what=0;
					LrcFragment.handler.sendMessage(message);
				}
			};
			timer.schedule(task,0,1000);
		}
	}
	
	//中间人
	public class MyBinder extends Binder{
		
		public void jieQianBinder(){
			jieQian();
		}
		//中间人里面播放的方法
		public void playInBinder(String show_link) {
			
			playMusic(show_link);
		}
		public void stopInBinder() {

			stopMusic();
		}
	}
	


}
