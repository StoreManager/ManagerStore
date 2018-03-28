package com.cottee.managerstore.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.cottee.managerstore.R;
import com.cottee.managerstore.bean.VIPStandard;

import java.util.List;

/**
 * Created by Administrator on 2017-11-14.
 */

public class VIPStandardAdapter extends BaseAdapter {
    private Context context;
    private List<VIPStandard> vipStandardList;

    public VIPStandardAdapter(Context context, List<VIPStandard> list) {
        this.context = context;
        this.vipStandardList = list;
    }

    @Override
    public int getCount() {
        return vipStandardList.size();
    }

    @Override
    public Object getItem(int i) {
        return vipStandardList.get( i );
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        final ViewHolder viewHolder;
        if (view == null) {
            viewHolder = new ViewHolder();
            view = View.inflate( context, R.layout.item_vipstandard, null );
//            viewHolder.tv_vipLevelNumber = view.findViewById( R.id.tv_vipnumber );
            viewHolder.tv_vipname = view.findViewById( R.id.tv_vipname );
            viewHolder.tv_min = view.findViewById( R.id.tv_min );
            viewHolder.discount = view.findViewById( R.id.tv_discount );

            view.setTag( viewHolder );
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.tv_vipname.setText( vipStandardList.get( i ).getVIP_name() );
        viewHolder.tv_min.setText( vipStandardList.get( i ).getMin_num() );
        viewHolder.discount.setText( vipStandardList.get( i ).getDiscount() );

//        if (vipStandardList.get( i ).getNumber() == null) {
//            viewHolder.tv_vipLevelNumber.setText( "等级人数：0" );
//        } else {
//            viewHolder.tv_vipLevelNumber.setText( vipStandardList.get( i ).getNumber() );
//        }
        return view;
    }

    public static class ViewHolder {
        TextView tv_vipname;//等级名称
        TextView tv_min;//等级积分
//        TextView tv_vipLevelNumber;//等级人数
        TextView discount;//优惠折扣
    }
}
