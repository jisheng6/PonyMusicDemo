package com.example.menglucywhh.ponymusicdemo.view.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.menglucywhh.ponymusicdemo.R;
import com.example.menglucywhh.ponymusicdemo.bean.PlayBean;
import com.example.menglucywhh.ponymusicdemo.common.APIFactory;
import com.example.menglucywhh.ponymusicdemo.common.AbstractObserver;
import com.example.menglucywhh.ponymusicdemo.downloadutils.DownloadProgressListener;
import com.example.menglucywhh.ponymusicdemo.downloadutils.FileDownloader;

import java.io.File;
import java.util.HashMap;
import java.util.Map;



public class DownLoadActivity extends Activity {
    private TextView resultView;
    private ProgressBar progressBar;

    /**
     * 当Handler被创建会关联到创建它的当前线程的消息队列，该类用于往消息队列发送消息
     * 消息队列中的消息由当前线程内部进行处理
     */
    private Handler handler = new Handler(){

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    progressBar.setProgress(msg.getData().getInt("size"));
                    float num = (float)progressBar.getProgress()/(float)progressBar.getMax();
                    int result = (int)(num*100);
                    resultView.setText(result+ "%");

                    if(progressBar.getProgress()==progressBar.getMax()){
                        Toast.makeText(DownLoadActivity.this, R.string.success, Toast.LENGTH_SHORT).show();
                    }
                    break;
                case -1:
                    Toast.makeText(DownLoadActivity.this, R.string.error, Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_down_load);

        Intent it=getIntent();
        String songid = it.getStringExtra("songid");
        String autor = it.getStringExtra("autor");
        String tittle = it.getStringExtra("tittle");
        TextView songname=this.findViewById(R.id.songname);
        progressBar = (ProgressBar) this.findViewById(R.id.downloadbar);
        resultView = (TextView) this.findViewById(R.id.resultView);
        songname.setText(tittle+"-"+autor);
        File sd=Environment.getExternalStorageDirectory();
        String path=sd.getPath()+"/WHTPonyMusic";
        final File file=new File(path);
        if(!file.exists())
        {
            file.mkdir();
        }

        Map<String,String> map=new HashMap<String, String>();
         //map.put("format","json");
         map.put("method","baidu.ting.song.play");
         map.put("songid",songid);

         APIFactory.getInstance().get("v1/restserver/ting",map,new AbstractObserver<PlayBean>() {
        @Override
        public void onSuccess(PlayBean playBean) {
            if(playBean!=null) {
                Log.i("*******", "onSuccess: "+playBean.getBitrate().getShow_link());

                String songurl=playBean.getBitrate().getShow_link();

                System.out.println(Environment.getExternalStorageState() + "------" + Environment.MEDIA_MOUNTED);

                if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                    download(songurl, file, playBean.getSonginfo().getTitle(), playBean.getSonginfo().getAuthor(), playBean.getBitrate().getFile_extension());
                } else {
                    Toast.makeText(DownLoadActivity.this, R.string.sdcarderror, Toast.LENGTH_SHORT).show();
                }
            }
        }
        @Override
        public void onFailure(int code) {
            System.out.println("网问题");
        }
        });
    }

    /**
     * 主线程(UI线程)
     * 对于显示控件的界面更新只是由UI线程负责，如果是在非UI线程更新控件的属性值，更新后的显示界面不会反映到屏幕上
     * @param path
     * @param savedir
     */
    private void download(final String path, final File savedir, final String fname, final String author, final String file_extension) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                FileDownloader loader = new FileDownloader(DownLoadActivity.this, path, savedir,3,fname,author,file_extension);
                progressBar.setMax(loader.getFileSize());//设置进度条的最大刻度为文件的长度

                try {
                    loader.download(new DownloadProgressListener() {
                        @Override
                        public void onDownloadSize(int size) {//实时获知文件已经下载的数据长度
                            Message msg = new Message();
                            msg.what = 1;
                            msg.getData().putInt("size", size);
                            handler.sendMessage(msg);//发送消息
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                    handler.obtainMessage(-1).sendToTarget();
                }
            }
        }).start();
    }
}
