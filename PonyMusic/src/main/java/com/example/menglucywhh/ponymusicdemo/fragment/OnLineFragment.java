package com.example.menglucywhh.ponymusicdemo.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.menglucywhh.ponymusicdemo.R;
import com.example.menglucywhh.ponymusicdemo.adapter.OnLineAdapter;
import com.example.menglucywhh.ponymusicdemo.bean.SongBean;
import com.example.menglucywhh.ponymusicdemo.presenter.SongPresenter;
import com.example.menglucywhh.ponymusicdemo.view.SongViewCallBack;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Menglucywhh on 2017/12/28.
 */

public class OnLineFragment extends Fragment{

    @BindView(R.id.online_recyclerview)
    RecyclerView onlineRecyclerview;
    Unbinder unbinder;
    private OnLineAdapter onLineAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_online, container, false);
        unbinder = ButterKnife.bind(this, view);

/*
http://tingapi.ting.baidu.com
 /v1/restserver/ting?method=bai-du.ting.billboard.billList&format=json&type=23&size=15&offset=0&qq-pf-to=pcqq.group
                */

        onlineRecyclerview.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));

        onLineAdapter = new OnLineAdapter(getActivity());
        onlineRecyclerview.setAdapter(onLineAdapter);

        return view;

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
