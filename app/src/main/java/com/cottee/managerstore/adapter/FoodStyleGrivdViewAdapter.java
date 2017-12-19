package com.cottee.managerstore.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.cottee.managerstore.R;

import java.util.List;

/**
 * Created by Administrator on 2017-12-09.
 */

public class FoodStyleGrivdViewAdapter extends BaseAdapter {
    private Context context;
    private List<String> foodList;
    public FoodStyleGrivdViewAdapter(Context context, List<String > foodList) {
        this.context = context;
        this.foodList = foodList;
    }
    @Override
    public int getCount() {
        return foodList.size();
    }

    @Override
    public Object getItem(int i) {
        return foodList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        final ViewHolder viewHolder;
        if (view == null) {
            viewHolder=new ViewHolder();
            view= View.inflate(context, R.layout.fragment_style,null);
            viewHolder.tv_style=  view.findViewById(R.id.tv_storestyle);
            view.setTag(viewHolder);
        } else {
            viewHolder=(ViewHolder)view.getTag();
        }
        viewHolder.tv_style.setText(foodList.get(i));
        return view;
    }
    protected static class ViewHolder {
        TextView tv_style;
    }
}
