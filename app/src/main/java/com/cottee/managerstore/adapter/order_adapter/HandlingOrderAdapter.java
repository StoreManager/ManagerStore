package com.cottee.managerstore.adapter.order_adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.cottee.managerstore.R;
import com.cottee.managerstore.adapter.order_adapter.dished_info.HandleDishInfoAdapter;

import com.cottee.managerstore.bean.OrderInfo;
import com.cottee.managerstore.widget.CustomListView;

import java.util.List;

/**
 * Created by Administrator on 2018/4/7.
 */

public class HandlingOrderAdapter extends BaseAdapter {
    private Context context;
    private List<OrderInfo.IndentListNoOkBean> orderList;

    public HandlingOrderAdapter(Context context, List<OrderInfo.IndentListNoOkBean> orderList) {
        this.context =context;
        this.orderList = orderList;
    }

    public List<OrderInfo.IndentListNoOkBean> getOrderList() {
        return orderList;
    }

    public void setOrderList(List<OrderInfo.IndentListNoOkBean> orderList) {
        this.orderList = orderList;
    }

    @Override
    public int getCount() {
        return orderList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        ViewHolder holder;
        if(view==null){
            holder = new ViewHolder();
            view = View.inflate(context, R.layout.item_order_info, null);
            holder.lv_dish_info = view.findViewById(R.id.lv_dish_info);
            holder.btn_order_start_handle = view.findViewById(R.id.btn_order_start_handle);
            holder.tv_createtime = view.findViewById(R.id.tv_createtime);
            holder.tv_order_cancel = view.findViewById(R.id.tv_order_cancel);
            holder.tv_bespeak_date = view.findViewById(R.id.tv_bespeak_date);
            holder.tv_user_name_tel = view.findViewById(R.id.tv_user_name_tel);
            holder.tv_people_count = view.findViewById(R.id.tv_people_count);
            holder.tv_dish_count = view.findViewById(R.id.tv_dish_count);
            holder.tv_price_count = view.findViewById(R.id.tv_price_count);
            holder.tv_ps = view.findViewById(R.id.tv_ps);
            holder.tv_order_info = view.findViewById(R.id.tv_order_info);
            holder.tv_table_number = view.findViewById(R.id.tv_table_number);
            view.setTag(holder);
        }else {
            holder = (ViewHolder) view.getTag();
        }
        holder.btn_order_start_handle.setText("完成订单");
        HandleDishInfoAdapter adapter = new HandleDishInfoAdapter(context,orderList.get(i).getTrolley_list().getList());
        holder.lv_dish_info.setAdapter(adapter);
        holder.tv_createtime.setText(orderList.get(i).getCreate_time());
        holder.tv_bespeak_date.setText(orderList.get(i).getIndent_info().getTime());
        holder.tv_user_name_tel.setText(orderList.get(i).getIndent_info().getName() + "(" +
                orderList.get(i).getIndent_info().getPhone() + ")");
        holder.tv_dish_count.setText("("+orderList.get(i).getTrolley_list().getList().size()+")");
        holder.tv_people_count.setText(orderList.get(i).getIndent_info().getPeopleNum()+"人");
        holder.tv_price_count.setText(orderList.get(i).getTrolley_list().getPay());
        holder.tv_ps.setText(orderList.get(i).getIndent_info().getDis());
        holder.tv_order_info.setText("接单员:"+orderList.get(i).getWaiter_name());
        holder.tv_table_number.setText(orderList.get(i).getTable_number());
        holder.tv_order_cancel.setVisibility(View.GONE);
        return view;
    }
    protected static class ViewHolder{
        CustomListView lv_dish_info;
        Button btn_order_start_handle;
        TextView tv_createtime;
        TextView tv_bespeak_date;
        TextView tv_user_name_tel;
        TextView tv_people_count;
        TextView tv_dish_count;
        TextView tv_price_count;
        TextView tv_ps;
        TextView tv_order_info;
        TextView tv_table_number;
        TextView tv_order_cancel;
    }
}
