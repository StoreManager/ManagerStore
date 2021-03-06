package com.cottee.managerstore.adapter.order_adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.cottee.managerstore.R;
import com.cottee.managerstore.adapter.order_adapter.dished_info.DishDetailAdapter;

import com.cottee.managerstore.bean.OrderInfo;
import com.cottee.managerstore.widget.CustomListView;

import java.util.List;

/**
 * Created by Administrator on 2018/4/7.
 */

public class HistoryOrderAdapter extends BaseAdapter {
    private Context context;
    private List<OrderInfo.IndentListNoOkBean> orderList;

    public HistoryOrderAdapter(Context context, List<OrderInfo.IndentListNoOkBean> orderList) {
        this.context = context;
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
            view = View.inflate(context, R.layout.item_order_history, null);
            holder.lv_dish_info = view.findViewById(R.id.lv_dish_info);
            holder.tv_createtime = view.findViewById(R.id.tv_createtime);
            holder.tv_bespeak_date = view.findViewById(R.id.tv_bespeak_date);
            holder.tv_user_name_tel = view.findViewById(R.id.tv_user_name_tel);
            holder.tv_people_count = view.findViewById(R.id.tv_people_count);
            holder.tv_dish_count = view.findViewById(R.id.tv_dish_count);
            holder.tv_price_count = view.findViewById(R.id.tv_price_count);
            holder.tv_ps = view.findViewById(R.id.tv_ps);
            holder.tv_waiter_name = view.findViewById(R.id.tv_waiter_name);
            holder.tv_history_table_number = view.findViewById(R.id.tv_history_table_number);
            holder.tv_order_over_info = view.findViewById(R.id.tv_order_over_info);

            view.setTag(holder);
        }else {
            holder = (ViewHolder) view.getTag();
        }
        DishDetailAdapter adapter = new DishDetailAdapter(context,orderList.get(i).getTrolley_list().getList());
        holder.lv_dish_info.setAdapter(adapter);
        holder.lv_dish_info.setAdapter(adapter);
        holder.tv_createtime.setText(orderList.get(i).getCreate_time());
        holder.tv_bespeak_date.setText(orderList.get(i).getIndent_info().getTime());
        holder.tv_user_name_tel.setText(orderList.get(i).getIndent_info().getName() + "(" +
                orderList.get(i).getIndent_info().getPhone() + ")");
        holder.tv_dish_count.setText("("+orderList.get(i).getTrolley_list().getList().size()+")");
        holder.tv_people_count.setText(orderList.get(i).getIndent_info().getPeopleNum()+"人");
        holder.tv_price_count.setText(orderList.get(i).getTrolley_list().getPay());
        holder.tv_ps.setText(orderList.get(i).getIndent_info().getDis());
        holder.tv_waiter_name.setText("接单员:"+orderList.get(i).getWaiter_name());
        holder.tv_history_table_number.setText(orderList.get(i).getTable_number().toString());
        holder.tv_order_over_info.setText(orderList.get(i).getOrder_info());
        return view;
    }
    protected static class ViewHolder{
        CustomListView lv_dish_info;
        TextView tv_createtime;
        TextView tv_bespeak_date;
        TextView tv_user_name_tel;
        TextView tv_people_count;
        TextView tv_dish_count;
        TextView tv_price_count;
        TextView tv_ps;
        TextView tv_waiter_name;
        TextView tv_history_table_number;
        TextView tv_order_over_info;
    }
}
