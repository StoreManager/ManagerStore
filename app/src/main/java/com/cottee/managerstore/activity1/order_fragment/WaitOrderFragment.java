package com.cottee.managerstore.activity1.order_fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.cottee.managerstore.R;
import com.cottee.managerstore.adapter.order_adapter.StoreOrderAdapter;
import com.cottee.managerstore.adapter.order_adapter.WaitOrderAdapter;
import com.cottee.managerstore.bean.OrderInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/4/6.
 */

public class WaitOrderFragment extends Fragment {
    private ListView lv_waitorder;
    private static List<OrderInfo.IndentListNoOkBean> order_Wait_List = new ArrayList<OrderInfo.IndentListNoOkBean>();
    public static WaitOrderAdapter waitOrderAdapter;

    public static List<OrderInfo.IndentListNoOkBean> getOrder_Wait_List() {
        return order_Wait_List;
    }

    public static void setOrder_Wait_List(List<OrderInfo.IndentListNoOkBean> order_Wait_List) {
        WaitOrderFragment.order_Wait_List = order_Wait_List;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_waitorder, container, false);
        initView(rootView);
        initClick();
        return rootView;
    }

    private void initClick() {
        lv_waitorder.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                Button btn_order_start_handle = view.findViewById(R.id.btn_order_start_handle);
                final LinearLayout ll_order_user_info = view.findViewById(R.id.ll_order_user_info);
                LinearLayout ll_order_title = view.findViewById(R.id.ll_order_title);
                final TextView tv_table_number = view.findViewById(R.id.tv_table_number);
                final TextView tv_order_info = view.findViewById(R.id.tv_order_info);
                tv_order_info.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                        final EditText et_order_name = new EditText(getContext());
                        builder.setTitle("接单员设定");
                        builder.setMessage("请输入接单员姓名");
                        builder.setView(et_order_name);
                        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                return;
                            }
                        });
                        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                waitOrderAdapter.getOrderList().get(position).setWaiter_name(et_order_name.getText().toString());
                                waitOrderAdapter.notifyDataSetChanged();
                            }
                        });
                        AlertDialog dialog = builder.create();
                        dialog.show();
                    }
                });
                tv_table_number.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                        final EditText et_table_number = new EditText(getContext());
                        builder.setTitle("餐桌号设定");
                        builder.setMessage("请输入餐桌号");
                        builder.setView(et_table_number);
                        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                return;
                            }
                        });
                        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                waitOrderAdapter.getOrderList().get(position).setTable_number
                                        (et_table_number.getText().toString());
                                waitOrderAdapter.notifyDataSetChanged();
                            }
                        });
                        AlertDialog dialog = builder.create();
                        dialog.show();
                    }
                });
                ll_order_title.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (ll_order_user_info.getVisibility() == View.GONE) {
                            ll_order_user_info.setVisibility(View.VISIBLE);
                        } else {
                            ll_order_user_info.setVisibility(View.GONE);
                        }
                    }
                });
                btn_order_start_handle.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(tv_table_number.getText().equals("餐桌号")){
                            Toast.makeText(getActivity(), "请输入餐桌号", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if(tv_order_info.getText().equals("接单员:暂无")){
                            Toast.makeText(getActivity(), "请输入接单员姓名", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        List<OrderInfo.IndentListNoOkBean> orderList = waitOrderAdapter.getOrderList();
                        OrderInfo.IndentListNoOkBean remove = orderList.remove(position);
                        waitOrderAdapter.notifyDataSetChanged();
                        List<OrderInfo.IndentListNoOkBean> order_handle_list = HandlingOrderFragment.getOrder_Handle_List();
                        order_handle_list.add(remove);
                        HandlingOrderFragment.setOrder_Handle_List(order_handle_list);
                        HandlingOrderFragment.handlingOrderAdapter.notifyDataSetChanged();
                    }
                });
            }
        });
    }

    private void initView(View rootView) {
        lv_waitorder = (ListView) rootView.findViewById(R.id.lv_waitorder);
        waitOrderAdapter = new WaitOrderAdapter(getContext(), order_Wait_List);
        lv_waitorder.setAdapter(waitOrderAdapter);
    }

}
