package com.cottee.managerstore.activity1.order_fragment;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.cottee.managerstore.R;
import com.cottee.managerstore.adapter.order_adapter.StoreOrderAdapter;
import com.cottee.managerstore.bean.OrderInfo;
import com.cottee.managerstore.httputils.HttpUtilSession;
import com.cottee.managerstore.properties.Properties;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by Administrator on 2018/4/6.
 */

public class StoreOrderFragment extends Fragment {

    private ListView lv_storeorder;
    private static List<OrderInfo.IndentListNoOkBean> order_Store_List = new ArrayList<OrderInfo.IndentListNoOkBean>();
    public static StoreOrderAdapter storeOrderAdapter;
    private StoreOrderFragment.OrderManageHandle handler = new StoreOrderFragment.OrderManageHandle();

    public static List<OrderInfo.IndentListNoOkBean> getOrder_Store_List() {
        return order_Store_List;
    }

    public static void setOrder_Store_List(List<OrderInfo.IndentListNoOkBean> order_Store_List) {
        StoreOrderFragment.order_Store_List = order_Store_List;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_storeorder, container, false);
        initView(rootView);
        initClick();
        return rootView;
    }

    public void sendRequestWithOkHttpGetList() {
        new Thread(new Runnable() {
            @Override
            public void run() {

                HttpUtilSession.sendSessionOkHttpRequest(getContext(), Properties.ORDER_GET_PATH, new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {

                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String responeData = response.body().string();
                        System.out.println("--------------------------------------------------所有订单列表 Json:" + responeData);


                        if(responeData.trim().equals("250")){

                            System.out.println("服务器返回："+responeData);

                        }
                        else if (responeData.trim().equals("0")){
                            System.out.println("服务器返回："+responeData);
                        }
                        else {

                            parseJSONWithGSON(responeData);
                        }

                    }

                });
            }
        }).start();
    }


    private void parseJSONWithGSON(String jsonData) {
        //使用轻量级的Gson解析得到的json
        Gson gson = new Gson();
        List<OrderInfo.IndentListNoOkBean> orderInfoList = gson.fromJson(jsonData, OrderInfo.class).getIndent_list_no_ok();

        Message message = new Message();
        message.what = Properties.ORDER_ADD;
        message.obj = orderInfoList;
        handler.sendMessage(message);
    }
    public void sendRequestWithOkHttpUpdateServer(final String indent_id) {
        new Thread(new Runnable() {
            @Override
            public void run() {

                HttpUtilSession.sendOrderIdOkHttpRequest(getContext(), Properties.ORDER_UPDATE_PATH, indent_id,new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {

                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String responeData = response.body().string();
                        System.out.println("--------------------------------------------------所有订单列表 Json:" + responeData);


                        if(responeData.trim().equals("250")){
                            System.out.println("服务器返回："+responeData+"session 过期");
                        }
                        else if (responeData.trim().equals("0")){
                            System.out.println("服务器返回："+responeData+"请求失败");
                        }else{
                            System.out.println("服务器返回："+responeData+"请求成功");
                        }

                    }

                });
            }
        }).start();
    }
    private void initClick() {
        lv_storeorder.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                Button btn_order_start_handle = view.findViewById(R.id.btn_order_start_handle);
                TextView tv_order_cancel = view.findViewById(R.id.tv_order_cancel);
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
                tv_order_cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        new AlertDialog.Builder(getContext()).setMessage("确定要取消该订单吗")
                                .setPositiveButton("拒绝", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface
                                                                dialog, int
                                                                which) {
                                        List<OrderInfo.IndentListNoOkBean> orderList = storeOrderAdapter.getOrderList();
                                        OrderInfo.IndentListNoOkBean remove = orderList.remove(position);
                                        sendRequestWithOkHttpUpdateServer(remove.getIndent_id());
                                        storeOrderAdapter.notifyDataSetChanged();
                                        List<OrderInfo.IndentListNoOkBean> order_history_list = HistoryOrderFragment.getOrder_History_List();
                                        remove.setOrder_info("确定");
                                        order_history_list.add(remove);
                                        HistoryOrderFragment.setOrder_History_List(order_history_list);
                                    }
                                })
                                .setNegativeButton("取消",null).show();

                    }
                });
                btn_order_start_handle.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        List<OrderInfo.IndentListNoOkBean> orderList = storeOrderAdapter.getOrderList();
                        OrderInfo.IndentListNoOkBean remove = orderList.remove(position);
                        sendRequestWithOkHttpUpdateServer(remove.getIndent_id());
                        storeOrderAdapter.notifyDataSetChanged();
                        List<OrderInfo.IndentListNoOkBean> order_wait_list = WaitOrderFragment.getOrder_Wait_List();
                        order_wait_list.add(remove);
                        WaitOrderFragment.setOrder_Wait_List(order_wait_list);
                        WaitOrderFragment.waitOrderAdapter.notifyDataSetChanged();
                    }
                });
            }
        });
    }

    private void initView(View rootView) {
        lv_storeorder = (ListView) rootView.findViewById(R.id.lv_storeorder);
        sendRequestWithOkHttpGetList();
    }
    public class OrderManageHandle extends Handler {
        private Context context;
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case Properties.ORDER_ADD:
                    order_Store_List = (List<OrderInfo.IndentListNoOkBean>) msg.obj;
                    storeOrderAdapter = new StoreOrderAdapter(getContext(),order_Store_List);
                    lv_storeorder.setAdapter(storeOrderAdapter);
                    break;
            }
        }
    }
}
