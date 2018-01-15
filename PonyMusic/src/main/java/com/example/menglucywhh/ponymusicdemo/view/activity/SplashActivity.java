package com.example.menglucywhh.ponymusicdemo.view.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.menglucywhh.ponymusicdemo.MainActivity;
import com.example.menglucywhh.ponymusicdemo.R;

public class SplashActivity extends AppCompatActivity {
private Handler handler = new Handler(){
    @Override
    public void handleMessage(Message msg) {
        super.handleMessage(msg);
        switch (msg.what){
            case 0:
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
                break;
            case 1:
                Intent intent1= new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent1);
                finish();
                break;
        }
    }
};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        SharedPreferences preferences = getSharedPreferences("config", 0);

       boolean flag = preferences.getBoolean("flag",false);

       if(flag){
           //第二次进入页面,不延迟直接跳转
           handler.sendEmptyMessage(1);
       }else{
           //第一次进入页面,,存值为true,,,延迟跳转
           preferences.edit().putBoolean("flag",true).commit();
           handler.sendEmptyMessageDelayed(0,3000);
       }

    }
}
