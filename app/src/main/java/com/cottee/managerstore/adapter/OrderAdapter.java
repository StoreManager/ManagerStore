package com.cottee.managerstore.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.cottee.managerstore.R;


/**
 * Created by 37444 on 2018/3/29.
 */

public class OrderAdapter extends BaseAdapter {
    private Context context;

    public OrderAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return 10;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            switch (position) {
                case 0:
                    convertView = View.inflate(context, R.layout.layout_mouth_accountlist_item, null);
                    viewHolder.tv_mouthdate = convertView.findViewById(R.id.tv_mouthdate);
                    break;
                default:
                    convertView = View.inflate(context, R.layout.layouyt_orderlist_iteam, null);
                    viewHolder.tv_order_id = convertView.findViewById(R.id.tv_order_id);
                    viewHolder.tv_order_idnumber = convertView.findViewById(R.id.tv_order_idnumber);
                    viewHolder.tv_order_time = convertView.findViewById(R.id.tv_order_time);
                    viewHolder.tv_order_price = convertView.findViewById(R.id.tv_order_price);
                    break;
            }
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        switch (position) {
            case 0:
                viewHolder.tv_mouthdate.setText("1月28日/星期二");
            default:

                break;
        }
        return convertView;
    }
    @Override
    public boolean isEnabled(int position)
    {
        if(position==0){
            return false;
        }
        return super.isEnabled(position);
    }

    static class ViewHolder {
        private TextView tv_mouthdate;
        private TextView tv_order_id;
        private TextView tv_order_idnumber;
        private TextView tv_order_time;
        private TextView tv_order_price;
    }
}
