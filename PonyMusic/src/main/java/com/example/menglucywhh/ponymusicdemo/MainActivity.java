package com.example.menglucywhh.ponymusicdemo;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.menglucywhh.ponymusicdemo.bean.Model;
import com.example.menglucywhh.ponymusicdemo.bean.Timebean;
import com.example.menglucywhh.ponymusicdemo.bean.Titlebean;
import com.example.menglucywhh.ponymusicdemo.fragment.Mine2Fragment;
import com.example.menglucywhh.ponymusicdemo.fragment.OnLineFragment;
import com.example.menglucywhh.ponymusicdemo.view.activity.PlayerActivity;
import com.example.menglucywhh.ponymusicdemo.view.activity.SearchActivity;
import com.facebook.drawee.view.SimpleDraweeView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.text.SimpleDateFormat;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.ce_image)
    ImageView ceImage;
    @BindView(R.id.btn_01)
    RadioButton btn01;
    @BindView(R.id.btn_02)
    RadioButton btn02;
    @BindView(R.id.radio_group)
    RadioGroup radioGroup;
    @BindView(R.id.manage_viewpager)
    ViewPager manageViewpager;
    @BindView(R.id.linear_cemian)
    LinearLayout linearCemian;
    @BindView(R.id.drawer_gen)
    DrawerLayout drawerGen;
    @BindView(R.id.gongneng)
    TextView gongneng;
    @BindView(R.id.night)
    TextView night;
    @BindView(R.id.timestop)
    TextView timestop;
    @BindView(R.id.tuichu)
    TextView tuichu;
    @BindView(R.id.guanyu)
    TextView guanyu;
    @BindView(R.id.titleRelative)
    RelativeLayout titleRelative;
    @BindView(R.id.bottomRelative)
    RelativeLayout bottomRelative;
    @BindView(R.id.search)
    ImageView search;
    @BindView(R.id.image_cover)
    SimpleDraweeView imageCover;
    @BindView(R.id.text_name)
    TextView textName;
    @BindView(R.id.text_autor)
    TextView textAutor;
    @BindView(R.id.progressbar)
    ProgressBar progressbar;
    @BindView(R.id.radio_play)
    ImageView radioPlay;
    @BindView(R.id.image_next)
    ImageView imageNext;
    @BindView(R.id.idid)
    LinearLayout idid;
    private Model model1;
    private Titlebean titlebean1;
    private String songid;
    private String musicLastState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);

        radioPlay.setTag(1);//1为不播放,暂停中
        radioPlay.setImageResource(R.drawable.music_bofang);

        night.setTag(1);//夜间模式
        //radiogroup的点击监听
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.btn_01:
                        //点击第一个radiobutton,显示viewpager的第一页
                        manageViewpager.setCurrentItem(0, false);
                        break;
                    case R.id.btn_02:
                        //点击第二个radiobutton,显示viewpager的第二页
                        manageViewpager.setCurrentItem(1, false);
                        break;
                }
            }
        });

        //viewpager设置适配器
        manageViewpager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                Fragment fragment = null;
                switch (position) {
                    case 0:
                        //当滑动到第一页时候,展示这个fragment
                        //  fragment = new MineFragment();
                        fragment = new Mine2Fragment();
                        break;
                    case 1:
                        //当滑动到第二页时候,展示这个fragment
                        fragment = new OnLineFragment();
                        break;
                }
                return fragment;
            }

            @Override
            public int getCount() {
                //返回viewpager的数量
                return 2;
            }
        });

        //viewpager滑动监听
        manageViewpager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                //radiogroup选中对应的radiobutton
                radioGroup.check(radioGroup.getChildAt(position).getId());
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


    }


    @Subscribe(sticky = false)
    public void GetEvent(Timebean timebean) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("mm:ss");
      //  Log.i("------", timebean.currentPosition + "");
       // Log.i("------", timebean.duration + "");
        progressbar.setProgress(timebean.currentPosition * 100 / timebean.duration);
        //Log.i("----", "eeeeeee");

    }

    //在线音乐传来的
    @Subscribe(sticky = true)
    public void getTitlebean(Titlebean titlebean) {

        textName.setText(titlebean.getName());
        textAutor.setText(titlebean.getAutor());
        if (titlebean.getPath() != null) {
            imageCover.setImageURI(Uri.parse(titlebean.getPath()));
            titlebean1 = new Titlebean(titlebean.getName(), titlebean.getAutor(), titlebean.getPath());

        } else {
            imageCover.setImageResource(R.drawable.default_cover);
            titlebean1 = new Titlebean(titlebean.getName(), titlebean.getAutor(),null);
        }
        radioPlay.setTag(2);
        radioPlay.setImageResource(R.drawable.music_zanting);
    }

    //在线音乐传来的songid
    @Subscribe(sticky = true)
    public void getSongId(String songidd) {

        songid = songidd;
        radioPlay.setTag(2);
        radioPlay.setImageResource(R.drawable.music_zanting);

    }

    //本地音乐传来的
    @Subscribe(sticky = true)
    public void getModel(Model model) {

        //model里面是
        textName.setText(model.getText_song());
        textAutor.setText(model.getText_singer());
        String downModelPath = model.getPath();

      model1 = new Model(model.getText_song(), model.getText_singer(), model.getPath());
        radioPlay.setTag(2);
        radioPlay.setImageResource(R.drawable.music_zanting);
    }

    //在线音乐传来的songid
    @Subscribe(sticky = true)
    public void getMusicState(String musicState) {

        musicLastState = musicState;
        //false为设置继续按钮

    }


    @Override
    protected void onResume() {
        super.onResume();
        if(musicLastState!=null) {
            if (musicLastState.equals("播放中")) {
                radioPlay.setTag(2);
                radioPlay.setImageResource(R.drawable.music_zanting);
                // Toast.makeText(this, "musicState : "+musicState, Toast.LENGTH_SHORT).show();
                //System.out.println("musicStateMainActivity : "+musicState);
            } else {
                radioPlay.setTag(1);
                radioPlay.setImageResource(R.drawable.music_bofang);
                // Toast.makeText(this, "musicState : "+musicState, Toast.LENGTH_SHORT).show();
                // System.out.println("musicStateMainActivity : "+musicState);
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @OnClick({R.id.gongneng, R.id.search, R.id.night, R.id.timestop, R.id.idid, R.id.tuichu, R.id.guanyu, R.id.ce_image,R.id.radio_play})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ce_image:
                //点击图片 侧滑菜单出现
                if (!drawerGen.isDrawerOpen(linearCemian)) {
                    //如果当前已经关闭了侧面,就打开
                    drawerGen.openDrawer(linearCemian);
                }
                break;
            case R.id.gongneng:
                drawerGen.closeDrawer(linearCemian);
                break;
            case R.id.night:

                int mode = getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
                if (mode == Configuration.UI_MODE_NIGHT_YES) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    night.setText("夜间模式");
                    titleRelative.setBackgroundColor(Color.RED);
                    bottomRelative.setBackgroundColor(Color.WHITE);
                } else if (mode == Configuration.UI_MODE_NIGHT_NO) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    titleRelative.setBackgroundColor(Color.BLACK);
                    bottomRelative.setBackgroundColor(Color.BLACK);
                    night.setText("日间模式");
                } else {
                    // blah blah
                }

                recreate();
                int tag = (int) night.getTag();
                if (tag == 1) {
                    night.setTag(2);
                    night.setText("日间模式");
                } else {
                    night.setTag(1);
                    night.setText("夜间模式");
                }
                drawerGen.closeDrawer(linearCemian);
                break;
            case R.id.timestop:
                drawerGen.closeDrawer(linearCemian);
                break;
            case R.id.tuichu:
                finish();
                drawerGen.closeDrawer(linearCemian);
                break;
            case R.id.guanyu:
                drawerGen.closeDrawer(linearCemian);
                break;
            case R.id.radio_play://点击下面的播放暂停图片的时候
                if(PlayerActivity.binder!=null) {
                    int radio_tag = (int) radioPlay.getTag();
                    if (radio_tag == 1) {
                        radioPlay.setTag(2);
                        radioPlay.setImageResource(R.drawable.music_zanting);
                    } else {
                        radioPlay.setTag(1);
                        radioPlay.setImageResource(R.drawable.music_bofang);
                    }
                    PlayerActivity.binder.stopInBinder();
                }
                break;
            case R.id.search:
                //点击搜索按钮
                Intent intent = new Intent(this, SearchActivity.class);
                startActivity(intent);
                break;
            case R.id.idid:
                //点击下面的音乐播放按钮
              /*  Intent intent1 = new Intent(this, PlayerActivity.class);
                startActivity(intent1);
*/
                String textName1 = textName.getText().toString();
                String textAuthor1 = textAutor.getText().toString();

                //EventBus.getDefault().postSticky(new Titlebean(textName1, textAuthor1, null));
                if(!textName1.equals(" ")&&!textAuthor1.equals(" ")) {
                    if (model1 != null) {
                        Intent intent1 = new Intent(this, PlayerActivity.class);
                        intent1.putExtra("flag", 1);//传1 ,,是从本地音乐发送
                        intent1.putExtra("model", model1);
                        startActivity(intent1);
                        model1 = null;
                    } else {
                        Intent intent1 = new Intent(this, PlayerActivity.class);
                        intent1.putExtra("flag", 0);//0为在线音乐

                        //传一个songid
                        intent1.putExtra("songid", songid);
                        startActivity(intent1);
                        //Toast.makeText(this, "model1为空", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
        }
    }


}
