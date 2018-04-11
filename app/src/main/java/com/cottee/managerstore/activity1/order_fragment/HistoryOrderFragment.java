package com.cottee.managerstore.activity1.order_fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.cottee.managerstore.R;
import com.cottee.managerstore.adapter.order_adapter.HistoryOrderAdapter;
import com.cottee.managerstore.bean.OrderInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/4/6.
 */

public class HistoryOrderFragment extends Fragment {
    private static List<OrderInfo.IndentListNoOkBean> order_History_List = new ArrayList<OrderInfo.IndentListNoOkBean>();
    public static HistoryOrderAdapter historyOrderAdapter;
    private ListView lv_historyorder;

    public static List<OrderInfo.IndentListNoOkBean> getOrder_History_List() {
        return order_History_List;
    }

    public static void setOrder_History_List(List<OrderInfo.IndentListNoOkBean> order_History_List) {
        HistoryOrderFragment.order_History_List = order_History_List;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_historyorder, container, false);
        initView(rootView);
        initClick();
        return rootView;
    }

    private void initClick() {
        lv_historyorder.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                final LinearLayout ll_order_user_info = view.findViewById(R.id.ll_order_user_info);
                LinearLayout ll_order_title = view.findViewById(R.id.ll_order_title);
                ll_order_title.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(ll_order_user_info.getVisibility() == View.GONE){
                            ll_order_user_info.setVisibility(View.VISIBLE);
                        }else{
                            ll_order_user_info.setVisibility(View.GONE);
                        }
                    }
                });
            }
        });
    }

    private void initView(View rootView) {
        lv_historyorder = rootView.findViewById(R.id.lv_historyorder);
        historyOrderAdapter = new HistoryOrderAdapter(getContext(),order_History_List);
        lv_historyorder.setAdapter(historyOrderAdapter);
    }
}
