package com.cottee.managerstore.activity;

import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.cottee.managerstore.R;
import com.cottee.managerstore.adapter.BaseViewPagerAdapter;
import com.cottee.managerstore.fragment.TestFragment;
import com.cottee.managerstore.view.TabPagerIndicator;

import java.util.ArrayList;

public class StoreStyleActivity extends BaseActivity {

    private TabPagerIndicator mPagerIndicator;
    private ViewPager mViewPager;


    private ArrayList<Fragment> mFragments;
    private String[] mTitles;
    private BaseViewPagerAdapter mPagerAdapter;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_store_style;
    }

    @Override
    protected void initView() {
        mPagerIndicator = (TabPagerIndicator) findViewById(R.id.pagerIndicator);
        mPagerIndicator.setIndicatorColor(Color.parseColor("#7a7cc9"));
        mViewPager = (ViewPager) findViewById(R.id.viewPager);
    }
    @Override
    protected void initData() {
        mTitles = this.getResources().getStringArray(R.array.fragments_titles);
        TestFragment testFragment = (TestFragment) TestFragment.newInstance(0);
        TestFragment testFragment1 =(TestFragment) TestFragment.newInstance(1);

        mFragments = new ArrayList<>();
        mFragments.add(testFragment);
        mFragments.add(testFragment1);
        mPagerAdapter = new BaseViewPagerAdapter(getSupportFragmentManager(), mFragments, mTitles);
        mViewPager.setAdapter(mPagerAdapter);
        mPagerIndicator.setViewPager(mViewPager);
    }
    public void backRegisterStore(View view)
    {
        Intent intent = new Intent(this, RegisterStoreInfoActivity.class);
        startActivity(intent);
    }
}
