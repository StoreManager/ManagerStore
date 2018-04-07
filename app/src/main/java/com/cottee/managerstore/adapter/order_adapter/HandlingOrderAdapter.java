package com.cottee.managerstore.adapter.order_adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;

import com.cottee.managerstore.R;
import com.cottee.managerstore.adapter.order_adapter.dished_info.HandleDishInfoAdapter;
import com.cottee.managerstore.widget.CustomListView;

/**
 * Created by Administrator on 2018/4/7.
 */

public class HandlingOrderAdapter extends BaseAdapter {
    private Context context;

    public HandlingOrderAdapter(Context context) {
        this.context =context;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        ViewHolder holder;
        if(view==null){
            holder = new ViewHolder();
            view = View.inflate(context, R.layout.item_order_info, null);
            holder.lv_dish_info = view.findViewById(R.id.lv_dish_info);
            holder.btn_order_start_handle = view.findViewById(R.id.btn_order_start_handle);
            view.setTag(holder);
        }else {
            holder = (ViewHolder) view.getTag();
        }
        holder.btn_order_start_handle.setText("完成订单");
        HandleDishInfoAdapter adapter = new HandleDishInfoAdapter(context);
        holder.lv_dish_info.setAdapter(adapter);

        return view;
    }
    protected static class ViewHolder{
        CustomListView lv_dish_info;
        Button btn_order_start_handle;
    }
}
