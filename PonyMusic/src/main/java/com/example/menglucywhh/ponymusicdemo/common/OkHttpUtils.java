package com.example.menglucywhh.ponymusicdemo.common;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;


public class OkHttpUtils {


    private static OkHttpClient client = null ;


    public static OkHttpClient getInstance(){

        if(client == null){
            synchronized (OkHttpUtils.class){
                if(client == null){
                    client = new OkHttpClient.Builder()
                            .connectTimeout(20000, TimeUnit.SECONDS)
                            .writeTimeout(20000,TimeUnit.SECONDS)
                            .readTimeout(20000,TimeUnit.SECONDS)
                            .addInterceptor(new SongInterceptor())
                            .build();
                }
            }
        }
        return client;
    }


}
