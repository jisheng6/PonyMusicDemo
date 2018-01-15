package com.example.menglucywhh.ponymusicdemo.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.menglucywhh.ponymusicdemo.R;
import com.example.menglucywhh.ponymusicdemo.adapter.DetailAdapter;
import com.example.menglucywhh.ponymusicdemo.bean.SearchBean;
import com.example.menglucywhh.ponymusicdemo.bean.SongBean;
import com.example.menglucywhh.ponymusicdemo.common.APIFactory;
import com.example.menglucywhh.ponymusicdemo.common.AbstractObserver;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DetailActivity extends AppCompatActivity {

    @BindView(R.id.detail_fanhui)
    TextView detailFanhui;
    @BindView(R.id.detail_name)
    TextView detailName;

    @BindView(R.id.detail_recy)
    RecyclerView detailRecy;
    private DetailAdapter detailAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        String type = intent.getStringExtra("type");
        System.out.println("type = "+type);

        detailAdapter = new DetailAdapter(this);
        detailRecy.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
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
                if (songBean != null) {
                    System.out.println(songBean.getSong_list().get(0).getTitle());
                    detailAdapter.addData(songBean);
                    detailRecy.setAdapter(detailAdapter);
                    detailName.setText(songBean.getBillboard().getName());
                }
            }

            @Override
            public void onFailure(int code) {
                Toast.makeText(DetailActivity.this, "网慢", Toast.LENGTH_SHORT).show();
            }
        });




    }

    @OnClick(R.id.detail_fanhui)
    public void onViewClicked() {
        finish();
    }
}
