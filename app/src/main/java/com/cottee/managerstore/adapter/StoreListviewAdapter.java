package com.cottee.managerstore.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.cottee.managerstore.R;
import com.cottee.managerstore.activity.StoreManagerActivity;
import com.cottee.managerstore.bean.StoreInfo;

import java.util.List;

/**
 * Created by Administrator on 2017-11-14.
 */

public class StoreListviewAdapter extends BaseAdapter {
    private Context context;
    private List<StoreInfo> storeList;
    public StoreListviewAdapter(Context context, List<StoreInfo> list) {
        this.context = context;
        this.storeList = list;
    }
    @Override
    public int getCount() {
        return storeList.size();
    }

    @Override
    public Object getItem(int i) {
        return storeList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        final ViewHolder viewHolder;
        if (view == null) {
            viewHolder=new ViewHolder();
            view= View.inflate(context, R.layout.layout_registerstore,null);
            viewHolder.tv_registerStoreName=view.findViewById(R.id.tv_registerStoreName);
            viewHolder.btn_storeManager=view.findViewById(R.id.btn_registerStoreManage);
            view.setTag(viewHolder);
        } else {
            viewHolder=(ViewHolder)view.getTag();
        }
        viewHolder.btn_storeManager.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent( context, StoreManagerActivity.class );
                intent.putExtra( "storeInfo",storeList.get( i ) );
                context.startActivity( intent );
            }
        } );
        viewHolder.tv_registerStoreName.setText( storeList.get( i ).getName());
        return view;
    }
    public static class ViewHolder {
        TextView tv_registerStoreName;
        TextView btn_storeManager;
    }
}
