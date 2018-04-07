package com.cottee.managerstore.activity1.order_fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cottee.managerstore.R;

/**
 * Created by Administrator on 2018/4/6.
 */

public class WaitOrderFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_waitorder, container, false);
        return rootView;
    }
}
