package com.cottee.managerstore.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;

import com.cottee.managerstore.R;
import com.cottee.managerstore.adapter.TabFragmentAdapter;
import com.cottee.managerstore.bean.StoreStyleInfo;
import com.cottee.managerstore.fragment.EntertainmentFragment;
import com.cottee.managerstore.fragment.FoodFragment;
import com.cottee.managerstore.fragment.TestFragment;
import com.cottee.managerstore.httputils.HttpUtils;
import com.cottee.managerstore.view.TabContainerView;
import com.google.gson.Gson;
import com.squareup.okhttp.Callback;

import java.io.IOException;
import java.util.List;

import static android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP;
import static android.content.Intent.FLAG_ACTIVITY_SINGLE_TOP;

///////////////////
public class StoreStyleActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener   {

    private String url = "http://thethreestooges.cn/merchant/static/file/project_static.json";


    private static List<String> entertainment;
    private StoreStyleActivity.myHandler myHandler;
    private static List<String> food;


    private static class myHandler extends Handler {
        private Context context;
        private Fragment[] fragments;

        public myHandler(Context context, Fragment[] fragments) {
            this.context = context;
            this.fragments = fragments;
        }

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    ((FoodFragment) fragments[1]).initFoodData((List<String>) msg.obj);
                    break;
            }
            super.handleMessage(msg);
        }
    }


    public static void startMainActivity(Activity activity, int tab) {

        Intent intent = new Intent(activity, StoreStyleActivity.class);
        intent.putExtra("tab", tab);
        intent.addFlags(FLAG_ACTIVITY_CLEAR_TOP | FLAG_ACTIVITY_SINGLE_TOP);
        activity.startActivity(intent);

    }

    private final int ICONS_RES[][] = {
            {
                    R.mipmap.ic_launcher,
                    R.mipmap.ic_launcher
            },
            {
                    R.mipmap.ic_launcher,
                    R.mipmap.ic_launcher
            },

            {
                    R.mipmap.ic_launcher,
                    R.mipmap.ic_launcher
            }
    };

    /**
     * tab 颜色值
     */
    private final int[] TAB_COLORS = new int[]{
            R.color.main_bottom_tab_textcolor_normal,
            R.color.main_bottom_tab_textcolor_selected};

    private Fragment[] fragments = {
            new TestFragment(),
            new FoodFragment(),
            new EntertainmentFragment()
    };

    private void initViews() {

        TabFragmentAdapter mAdapter = new TabFragmentAdapter(getSupportFragmentManager(), fragments);
        ViewPager mPager = (ViewPager) findViewById(R.id.tab_pager);
        //设置当前可见Item左右可见page数，次范围内不会被销毁
        mPager.setOffscreenPageLimit(1);
        mPager.setAdapter(mAdapter);

        TabContainerView mTabLayout = (TabContainerView) findViewById(R.id.ll_tab_container);
        mTabLayout.setOnPageChangeListener(this);

        mTabLayout.initContainer(getResources().getStringArray(R.array.tab_main_title), ICONS_RES, TAB_COLORS, true);

        int width = getResources().getDimensionPixelSize(R.dimen.tab_icon_width);
        int height = getResources().getDimensionPixelSize(R.dimen.tab_icon_height);
        mTabLayout.setContainerLayout(R.layout.tab_container_view, R.id.iv_tab_icon, R.id.tv_tab_text, width, height);
//        mTabLayout.setSingleTextLayout(R.layout.tab_container_view, R.id.tv_tab_text);
//        mTabLayout.setSingleIconLayout(R.layout.tab_container_view, R.id.iv_tab_icon);

        mTabLayout.setViewPager(mPager);

        mPager.setCurrentItem(getIntent().getIntExtra("tab", 0));

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        for (int index = 0, len = fragments.length; index < len; index++) {
            fragments[index].onHiddenChanged(index != position);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_style);
        sendRequestWithOkHttp();
        myHandler = new myHandler(StoreStyleActivity.this,fragments);
        initViews();
    }

    private void sendRequestWithOkHttp() {
        new Thread(new Runnable() {
            @Override
            public void run() {

                HttpUtils.sendOkHttpRequest(url, new Callback() {
                    @Override
                    public void onFailure(com.squareup.okhttp.Request request, IOException e) {

                    }

                    @Override
                    public void onResponse(com.squareup.okhttp.Response response) throws IOException {
                        String responeData = response.body().string();
                        List<String> strings = parseJSONWithGSON(responeData);
                        Message message = new Message();
                        message.what = 0;
                        message.obj = strings;
                        myHandler.sendMessage(message);
                    }
                });
            }
        }).start();
    }


    private List<String> parseJSONWithGSON(String jsonData) {
        //使用轻量级的Gson解析得到的json
        Gson gson = new Gson();
        StoreStyleInfo styleInfo = gson.fromJson(jsonData, StoreStyleInfo.class);
        entertainment = styleInfo.getEntertainment();
        food = styleInfo.getFood();
        return food;

    }

    public static List<String> getEntertainments() {
        return entertainment;
    }
    public static List<String > getFood(){
        return food;
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent home = new Intent(Intent.ACTION_MAIN);
            home.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            home.addCategory(Intent.CATEGORY_HOME);
            startActivity(home);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    public void backRegisterStore(View view)
    {
        finish();
        overridePendingTransition(R.anim.left_in,R.anim.right_out);
    }


}
