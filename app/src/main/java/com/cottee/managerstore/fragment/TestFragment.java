package com.cottee.managerstore.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.cottee.managerstore.R;
import com.cottee.managerstore.activity.RegisterStoreInfoActivity;

public class TestFragment extends BaseFragment {

    protected TextView mTvContent;

    private static final String ARG_POSITION = "position";

    protected int position;
    private TextView tv_storestyle;

    public static TestFragment newInstance(int position) {
        TestFragment f = new TestFragment();
        Bundle b = new Bundle();
        b.putInt(ARG_POSITION, position);
        f.setArguments(b);
        return f;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_style;
    }

    @Override
    protected void initView() {
        tv_storestyle = mView.findViewById(R.id
        .tv_storestyle);
        tv_storestyle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), RegisterStoreInfoActivity
                        .class);
                startActivity(intent);

            }
        });
        super.initView();

    }
}
