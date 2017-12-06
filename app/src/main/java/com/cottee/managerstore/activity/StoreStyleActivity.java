package com.cottee.managerstore.activity;

import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import com.cottee.managerstore.R;
import com.cottee.managerstore.adapter.BaseViewPagerAdapter;
import com.cottee.managerstore.bean.StoreStyleInfo;
import com.cottee.managerstore.tabfragment.TestFragment;
import com.cottee.managerstore.view.TabPagerIndicator;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
///////////////////
public class StoreStyleActivity extends BaseActivity  {

    private TabPagerIndicator mPagerIndicator;
    private ViewPager mViewPager;


    private ArrayList<Fragment> mFragments;
    private String[] mTitles;
    private BaseViewPagerAdapter mPagerAdapter;

    @Override
    protected int getContentViewLayoutID() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        sendRequestWithOkHttp();
        return R.layout.activity_store_style;
    }
  private void sendRequestWithOkHttp()
  {
      new Thread(new Runnable() {
          @Override
          public void run() {

              try {
                  OkHttpClient okHttpClient = new OkHttpClient();
                  Request request=new Request.Builder()
                          .url("http://thethreestooges.cn/merchant/static/file/project_static.json")
                          .build();
                  Response response=okHttpClient.newCall(request).execute();
                  String responseData = response.body().string();
                   parseJsonWithGson(responseData);
              } catch (IOException e) {
                  e.printStackTrace();
              }
          }
      }).start();
  }
     private void parseJsonWithGson(String jsonData) {
      Gson gson = new Gson();
      List<StoreStyleInfo> storeStyleInfoList = gson.fromJson(jsonData,
              new TypeToken<List<StoreStyleInfo>>() {
      }.getType());
      for (StoreStyleInfo storeStyleInfo :
            storeStyleInfoList) {
          Toast.makeText(mContext, "AA"+storeStyleInfo.getFood(), Toast.LENGTH_SHORT).show();
      }
  }
    @Override
    protected void initView() {
        mPagerIndicator = findViewById(R.id.pagerIndicator);
        mPagerIndicator.setIndicatorColor(Color.parseColor("#7a7cc9"));
        mViewPager = findViewById(R.id.viewPager);
    }

    @Override
    protected void initData() {
        mTitles = this.getResources().getStringArray(R.array.fragments_titles);
        TestFragment testFragment = TestFragment.newInstance(0);
        Toast.makeText(mContext, "-----"+testFragment.toString(), Toast.LENGTH_SHORT).show();
        TestFragment testFragment1 = TestFragment.newInstance(1);

        mFragments = new ArrayList<>();
        mFragments.add(testFragment);
        mFragments.add(testFragment1);
        mPagerAdapter = new BaseViewPagerAdapter(getSupportFragmentManager(),
                mFragments, mTitles);
        mViewPager.setAdapter(mPagerAdapter);
        mPagerIndicator.setViewPager(mViewPager);
    }
    public void backRegisterStore(View view)
    {
        finish();
        overridePendingTransition(R.anim.left_in,R.anim.right_out);
    }


}
