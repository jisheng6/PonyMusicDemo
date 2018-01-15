package com.example.menglucywhh.ponymusicdemo.model;

import com.example.menglucywhh.ponymusicdemo.bean.SongBean;
import com.example.menglucywhh.ponymusicdemo.common.APIFactory;
import com.example.menglucywhh.ponymusicdemo.common.AbstractObserver;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Menglucywhh on 2017/12/29.
 */

public class SongModel {

    /*
      http://tingapi.ting.baidu.com
    /v1/restserver/ting?method=bai-du.ting.billboard.billList&format=json&type=23&size=15&offset=0&qq-pf-to=pcqq.group
   */
    public void getSong(String type, final SongModelCallBack songModelCallBack){
        String url = "/v1/restserver/ting";
        Map<String,String> map = new HashMap<>();
        map.put("method","bai-du.ting.billboard.billList");
        map.put("format","json");
        map.put("type",type);
        map.put("size","15");
        map.put("offset","0");
        map.put("qq-pf-to","pcqq.group");

        APIFactory.getInstance().get(url, map, new AbstractObserver<SongBean>() {
            @Override
            public void onSuccess(SongBean songBean) {
                songModelCallBack.success(songBean);
            }

            @Override
            public void onFailure(int code) {
                songModelCallBack.failure();
            }
        });
    }

}
