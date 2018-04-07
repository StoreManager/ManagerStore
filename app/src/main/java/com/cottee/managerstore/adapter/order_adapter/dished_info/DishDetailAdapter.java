package com.cottee.managerstore.adapter.order_adapter.dished_info;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.cottee.managerstore.R;

/**
 * Created by Administrator on 2018/4/7.
 */

public class DishDetailAdapter extends BaseAdapter {
    private Context context;

    public DishDetailAdapter(Context context) {
        this.context =context;
    }

    @Override
    public int getCount() {
        return 3;
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
            view = View.inflate(context, R.layout.item_order_dish_detail, null);

            view.setTag(holder);
        }else {
            holder = (ViewHolder) view.getTag();
        }


        return view;
    }
    protected static class ViewHolder{

    }
}
