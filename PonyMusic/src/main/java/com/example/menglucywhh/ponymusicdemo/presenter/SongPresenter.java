package com.example.menglucywhh.ponymusicdemo.presenter;

import com.example.menglucywhh.ponymusicdemo.bean.SongBean;
import com.example.menglucywhh.ponymusicdemo.fragment.OnLineFragment;
import com.example.menglucywhh.ponymusicdemo.model.SongModel;
import com.example.menglucywhh.ponymusicdemo.model.SongModelCallBack;
import com.example.menglucywhh.ponymusicdemo.view.SongViewCallBack;

/**
 * Created by Menglucywhh on 2017/12/29.
 */

public class SongPresenter{
    SongModel songModel;

    SongViewCallBack songViewCallBack;
    public SongPresenter(SongViewCallBack songViewCallBack) {
        this.songViewCallBack = songViewCallBack;
        songModel = new SongModel();
    }

    public void getSong(String type){
     songModel.getSong(type,new SongModelCallBack() {
         @Override
         public void success(SongBean songBean) {
             songViewCallBack.success(songBean);
         }

         @Override
         public void failure() {
             songViewCallBack.failure();
         }
     });
    }
}
