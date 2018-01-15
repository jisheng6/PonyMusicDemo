package com.example.menglucywhh.ponymusicdemo.common;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;


public class RetrofitUtils {


    private static APIService service  = null ;

/*http://tingapi.ting.baidu.com
 /v1/restserver/ting?method=bai-du.ting.billboard.billList&format=json&type=23&size=15&offset=0&qq-pf-to=pcqq.group
 */
    public static APIService getInstance(){
        if(service == null){
            synchronized (RetrofitUtils.class){

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("http://tingapi.ting.baidu.com")
                        .addConverterFactory(ScalarsConverterFactory.create())
                        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                        .client(OkHttpUtils.getInstance())
                        .build();
                service = retrofit.create(APIService.class);
            }
        }
        return service;
    }

}
