package com.example.menglucywhh.ponymusicdemo.view.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.menglucywhh.ponymusicdemo.R;
import com.example.menglucywhh.ponymusicdemo.bean.AuthorBean;
import com.example.menglucywhh.ponymusicdemo.common.APIFactory;
import com.example.menglucywhh.ponymusicdemo.common.AbstractObserver;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AuthorActivity extends AppCompatActivity {

    @BindView(R.id.author_image)
    SimpleDraweeView authorImage;
    @BindView(R.id.author_name)
    TextView authorName;
    @BindView(R.id.author_country)
    TextView authorCountry;
    @BindView(R.id.author_xingzuo)
    TextView authorXingzuo;
    @BindView(R.id.author_height)
    TextView authorHeight;
    @BindView(R.id.author_weight)
    TextView authorWeight;
    @BindView(R.id.author_birth)
    TextView authorBirth;
    @BindView(R.id.author_jianjie)
    TextView authorJianjie;
    @BindView(R.id.author_chakan)
    TextView authorChakan;

    List<AuthorBean> list = new ArrayList<>();
    @BindView(R.id.author_fanhui)
    TextView authorFanhui;
    @BindView(R.id.author_daname)
    TextView authorDaname;
    @BindView(R.id.titleRelative)
    RelativeLayout titleRelative;
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_author);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        String ting_uid = intent.getStringExtra("ting_uid");

        Fresco.initialize(this);

        webView = new WebView(this);


        //  Toast.makeText(this, ting_uid, Toast.LENGTH_SHORT).show();

        //查询歌手信息did
//http://tingapi.ting.baidu.com/v1/restserver/ting?method=baidu.ting.artist.getInfo&tinguid=1226

        Map<String, String> map = new HashMap<>();
        map.put("method", "baidu.ting.artist.getInfo");
        map.put("tinguid", ting_uid);
        APIFactory.getInstance().get("/v1/restserver/ting", map, new AbstractObserver<AuthorBean>() {
            @Override
            public void onSuccess(final AuthorBean authorBean) {
                //System.out.println(authorBean.getArea());
             /*   照片avatar_s500姓名name  国籍country 星座constellation身高stature
                体重weight 生日birth 简介intro
                查看更多信息  url,, 这是可以用webview加载的*/

                if (authorBean != null) {
                    list.add(authorBean);
                    String[] split = authorBean.getAvatar_s500().split("@");
                    authorImage.setImageURI(Uri.parse(split[0]));
                    authorName.setText("姓名 : " + authorBean.getName());
                    authorCountry.setText("国籍 : " + authorBean.getCountry());
                    authorXingzuo.setText("星座 : " + authorBean.getConstellation());
                    authorHeight.setText("身高 : " + authorBean.getStature());
                    authorWeight.setText("体重 : " + authorBean.getWeight());
                    authorBirth.setText("生日 : " + authorBean.getBirth());
                    authorJianjie.setText("简介 : " + authorBean.getIntro());
                    authorDaname.setText(authorBean.getName());
                }
            }

            @Override
            public void onFailure(int code) {
                System.out.println("网慢");
            }
        });

    }

    @OnClick({R.id.author_fanhui, R.id.author_chakan})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.author_fanhui:
              finish();
                break;
            case R.id.author_chakan:

//                Toast.makeText(this, "ee", Toast.LENGTH_SHORT).show();
                WebSettings webSettings = webView.getSettings();
                webSettings.setJavaScriptEnabled(true);
                webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
                webView.loadUrl(list.get(0).getUrl());
                setContentView(webView);
                webView.setWebViewClient(new WebViewClient());
                break;
        }
    }





}
