package com.cottee.managerstore.activity1.order_fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.cottee.managerstore.R;
import com.cottee.managerstore.adapter.order_adapter.HandlingOrderAdapter;
import com.cottee.managerstore.bean.OrderInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/4/6.
 */

public class HandlingOrderFragment extends Fragment {
    private ListView lv_handleorder;
    private static List<OrderInfo.IndentListNoOkBean> order_Handle_List = new ArrayList<OrderInfo.IndentListNoOkBean>();
    public static HandlingOrderAdapter handlingOrderAdapter;

    public static List<OrderInfo.IndentListNoOkBean> getOrder_Handle_List() {
        return order_Handle_List;
    }

    public static void setOrder_Handle_List(List<OrderInfo.IndentListNoOkBean> order_Handle_List_List) {
        HandlingOrderFragment.order_Handle_List = order_Handle_List_List;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_handleorder, container, false);
        initView(rootView);
        initClick();
        return rootView;
    }
    private void initClick() {
        lv_handleorder.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                Button btn_order_start_handle = view.findViewById(R.id.btn_order_start_handle);
                final LinearLayout ll_order_user_info = view.findViewById(R.id.ll_order_user_info);
                LinearLayout ll_order_title = view.findViewById(R.id.ll_order_title);
                ListView lv_dish_info = view.findViewById(R.id.lv_dish_info);
                lv_dish_info.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        LinearLayout ll_finish_dish = view.findViewById(R.id.ll_finish_dish);
                        final ImageButton ibt_enfinish_dish = view.findViewById(R.id.ibt_enfinish_dish);
                        final ImageButton ibt_finish_dish = view.findViewById(R.id.ibt_finish_dish);
                        final TextView tv_dish_enfinish_info = view.findViewById(R.id.tv_dish_enfinish_info);
                        final TextView tv_dish_finish_info = view.findViewById(R.id.tv_dish_finish_info);
                        ll_finish_dish.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                ibt_enfinish_dish.setVisibility(View.GONE);
                                ibt_finish_dish.setVisibility(View.VISIBLE);
                                tv_dish_enfinish_info.setVisibility(View.GONE);
                                tv_dish_finish_info.setVisibility(View.VISIBLE);
                            }
                        });
                    }
                });

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
                btn_order_start_handle.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        List<OrderInfo.IndentListNoOkBean> orderList = handlingOrderAdapter.getOrderList();
                        OrderInfo.IndentListNoOkBean remove = orderList.remove(position);
                        handlingOrderAdapter.notifyDataSetChanged();
                        List<OrderInfo.IndentListNoOkBean> order_handle_list = HistoryOrderFragment.getOrder_History_List();
                        remove.setOrder_info("已完成");
                        order_handle_list.add(remove);
                        HistoryOrderFragment.setOrder_History_List(order_handle_list);
                        HistoryOrderFragment.historyOrderAdapter.notifyDataSetChanged();
                    }
                });
            }
        });
    }
    private void initView(View rootView) {
        lv_handleorder = (ListView) rootView.findViewById(R.id.lv_handleorder);
        handlingOrderAdapter = new HandlingOrderAdapter(getContext(),order_Handle_List);
        lv_handleorder.setAdapter(handlingOrderAdapter);
    }
}
