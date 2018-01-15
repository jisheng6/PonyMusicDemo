package com.example.menglucywhh.ponymusicdemo.model;

import com.example.menglucywhh.ponymusicdemo.bean.SongBean;

/**
 * Created by Menglucywhh on 2017/12/29.
 */

public interface SongModelCallBack {
    public void success(SongBean songBean);
    public void failure();
}
