package com.example.menglucywhh.ponymusicdemo.view;

import com.example.menglucywhh.ponymusicdemo.bean.SongBean;

/**
 * Created by Menglucywhh on 2017/12/29.
 */

public interface SongViewCallBack {
    public void success(SongBean songBean);
    public void failure();
}
