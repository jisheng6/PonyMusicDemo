package com.example.menglucywhh.ponymusicdemo.view.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.menglucywhh.ponymusicdemo.R;
import com.example.menglucywhh.ponymusicdemo.adapter.SearchAdapter;
import com.example.menglucywhh.ponymusicdemo.bean.SearchBean;
import com.example.menglucywhh.ponymusicdemo.common.APIFactory;
import com.example.menglucywhh.ponymusicdemo.common.AbstractObserver;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SearchActivity extends AppCompatActivity {

    @BindView(R.id.search_fanhui)
    TextView searchFanhui;
    @BindView(R.id.search_edit)
    EditText searchEdit;
    @BindView(R.id.search_cha)
    ImageView searchCha;
    @BindView(R.id.search_recy)
    RecyclerView searchRecy;
    private SearchAdapter searchAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);

        searchRecy.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        searchAdapter = new SearchAdapter(this);
    }

    @OnClick({R.id.search_fanhui, R.id.search_cha})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.search_fanhui:
                finish();
                break;
            case R.id.search_cha:
                if (TextUtils.isEmpty(searchEdit.getText().toString())) {
                    Toast.makeText(this, "搜索内容不能为空", Toast.LENGTH_SHORT).show();
                    searchAdapter.clearList();
                    searchAdapter.notifyDataSetChanged();
                } else {
/*
http://tingapi.ting.baidu.com/v1/restserver/ting?method=baidu.ting.search.catalogSug&query=海阔天空
 */
                    String query = searchEdit.getText().toString();
                    Map<String, String> map = new HashMap<>();
                    map.put("method", "baidu.ting.search.catalogSug");
                    map.put("query", query);
                    map.put("format","json");
                    APIFactory.getInstance().get("/v1/restserver/ting", map, new AbstractObserver<SearchBean>() {
                        @Override
                        public void onSuccess(SearchBean searchBean) {
                            if (searchBean != null) {
                                System.out.println(searchBean.getSong().get(0).getSongname());
                                searchAdapter.addData(searchBean.getSong());
                                searchRecy.setAdapter(searchAdapter);
                            }
                        }

                        @Override
                        public void onFailure(int code) {
                            Toast.makeText(SearchActivity.this, "网慢", Toast.LENGTH_SHORT).show();
                        }
                    });

                }
                break;
        }
    }
}
