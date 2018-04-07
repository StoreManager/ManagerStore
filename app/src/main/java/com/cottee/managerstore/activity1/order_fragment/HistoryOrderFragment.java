package com.cottee.managerstore.activity1.order_fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.cottee.managerstore.R;
import com.cottee.managerstore.adapter.order_adapter.HistoryOrderAdapter;

/**
 * Created by Administrator on 2018/4/6.
 */

public class HistoryOrderFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_historyorder, container, false);
        initView(rootView);
        return rootView;
    }

    private void initView(View rootView) {
        ListView lv_historyorder = rootView.findViewById(R.id.lv_historyorder);
        HistoryOrderAdapter adapter = new HistoryOrderAdapter(getContext());
        lv_historyorder.setAdapter(adapter);
    }
}
