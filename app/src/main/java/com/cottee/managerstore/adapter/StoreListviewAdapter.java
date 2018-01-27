package com.cottee.managerstore.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cottee.managerstore.R;
import com.cottee.managerstore.activity.StoreManagerActivity;
import com.cottee.managerstore.bean.StoreInfo;
import com.cottee.managerstore.httputils.HttpUtilSession;
import com.cottee.managerstore.utils.ToastUtils;

import java.util.List;

/**
 * Created by Administrator on 2017-11-14.
 */

public class StoreListviewAdapter extends BaseAdapter {
    private Context context;
    private List<StoreInfo> storeList;
    private Drawable drawable;
    public StoreListviewAdapter(Context context, List<StoreInfo> list,Drawable drawable) {
        this.context = context;
        this.storeList = list;
        this.drawable=drawable;
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
            viewHolder.iv_publish=view.findViewById( R.id.iv_publish );
            view.setTag(viewHolder);
        } else {
            viewHolder=(ViewHolder)view.getTag();
        }
        viewHolder.btn_storeManager.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent( context, StoreManagerActivity.class );
                intent.putExtra( "storeInfo",storeList.get( i ) );
                intent.putExtra( "storeid",i );
                context.startActivity( intent );
            }
        } );
        viewHolder.tv_registerStoreName.setText( storeList.get( i ).getName());
        if(!storeList.get( i ).isPass()){
            viewHolder.iv_publish.setBackground( drawable );
            viewHolder.btn_storeManager.setText( "未审核" );
            viewHolder.btn_storeManager.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ToastUtils.showToast( context,"亲，请耐心等待，审核通过后才可以操作哦！" );
                }
            } );
            view.setBackgroundColor( Color.parseColor( "#10000000" ) );
//            view.setClickable( false );
        }
        return view;
    }
    public static class ViewHolder {
        TextView tv_registerStoreName;
        TextView btn_storeManager;
        ImageView iv_publish;

    }
}
