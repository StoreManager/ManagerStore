package com.cottee.managerstore.activity1.order_fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.cottee.managerstore.R;
import com.cottee.managerstore.adapter.order_adapter.StoreOrderAdapter;

/**
 * Created by Administrator on 2018/4/6.
 */

public class StoreOrderFragment extends Fragment {

    private ListView lv_storeorder;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_storeorder, container, false);
        initView(rootView);
        return rootView;
    }

    private void initView(View rootView) {
        lv_storeorder = (ListView) rootView.findViewById(R.id.lv_storeorder);
        StoreOrderAdapter adapter = new StoreOrderAdapter(getContext());
        lv_storeorder.setAdapter(adapter);
    }
}
