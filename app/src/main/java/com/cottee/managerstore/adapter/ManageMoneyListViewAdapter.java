package com.cottee.managerstore.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.cottee.managerstore.R;

/**
 * Created by Administrator on 2018-01-15.
 */

public class ManageMoneyListViewAdapter extends BaseAdapter {
    private Context context;
    public ManageMoneyListViewAdapter(Context context)
    {
        this.context = context;
    }
    @Override
    public int getCount() {
        return 10;
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if (view==null)
        {
            viewHolder=new ViewHolder();
            LayoutInflater mInflate=LayoutInflater.from(context);
            view=mInflate.inflate(R.layout.item_manage_money_list,null);
            viewHolder.tv_day=view.findViewById(R.id.tv_day);
            viewHolder.tv_week=view.findViewById(R.id.tv_week);
            viewHolder.tv_income=view.findViewById(R.id.tv_income);
            viewHolder.tv_every_income=view.findViewById(R.id.tv_every_income);
            view.setTag(viewHolder);
        }
        else {
            viewHolder=(ViewHolder)view.getTag();
        }


        return view;
    }
    public static class ViewHolder {
        public TextView tv_day;
        private TextView tv_week;
        private TextView tv_income;
        private TextView tv_every_income;
    }
}
