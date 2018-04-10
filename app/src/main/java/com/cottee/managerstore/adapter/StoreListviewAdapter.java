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

import com.alibaba.sdk.android.oss.common.utils.HttpUtil;
import com.cottee.managerstore.R;
import com.cottee.managerstore.activity.RegisterStoreInfoActivity;
import com.cottee.managerstore.activity.StoreManagerActivity;
import com.cottee.managerstore.bean.StoreInfo;
import com.cottee.managerstore.handle.LoginRegisterInformationHandle;
import com.cottee.managerstore.httputils.HttpUtilSession;
import com.cottee.managerstore.httputils.HttpUtils;
import com.cottee.managerstore.manage.SubmitStoreInfoManager;
import com.cottee.managerstore.utils.ToastUtils;

import java.util.List;

/**
 * Created by Administrator on 2017-11-14.
 */

public class StoreListviewAdapter extends BaseAdapter {
    private Context context;
    private List<StoreInfo> storeList;
    private Drawable off;
    private Drawable on;

    public StoreListviewAdapter(Context context, List<StoreInfo> list, Drawable off, Drawable on) {
        this.context = context;
        this.storeList = list;
        this.off = off;
        this.on = on;
    }

    @Override
    public int getCount() {
        return storeList.size();
    }

    @Override
    public Object getItem(int i) {
        return storeList.get( i );
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
            view = View.inflate( context, R.layout.layout_registerstore, null );
            viewHolder.tv_registerStoreName = view.findViewById( R.id.tv_registerStoreName );
            viewHolder.btn_storeManager = view.findViewById( R.id.btn_registerStoreManage );
            viewHolder.iv_publish = view.findViewById( R.id.iv_publish );
            viewHolder.tv_storeManager = view.findViewById( R.id.tv_registerStoreManage );
            view.setTag( viewHolder );
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.tv_registerStoreName.setText( storeList.get( i ).getName() );
        viewHolder.tv_storeManager.setVisibility( View.VISIBLE );
        viewHolder.btn_storeManager.setVisibility( View.GONE );
        view.setBackgroundColor( Color.parseColor( "#10000000" ) );
        if (storeList.get( i ).isPass()) {
            viewHolder.iv_publish.setImageDrawable( on );
            viewHolder.tv_storeManager.setVisibility( View.GONE );
            viewHolder.btn_storeManager.setVisibility( View.VISIBLE );
            view.setBackgroundColor( Color.parseColor( "#00000000" ) );
            viewHolder.btn_storeManager.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent( context, StoreManagerActivity.class );
                    intent.putExtra( "storeInfo", storeList.get( i ) );
                    intent.putExtra( "locationStoreID", i );
                    SubmitStoreInfoManager submitStoreInfo = new SubmitStoreInfoManager(
                            context, new LoginRegisterInformationHandle(
                            context, "" ) );
                    submitStoreInfo.submitStoreId( storeList.get( i ).getMer_id() );
                    context.startActivity( intent );
                }
            } );

        } else {
            viewHolder.iv_publish.setImageDrawable( off );
        }

        return view;
    }

    public static class ViewHolder {
        TextView tv_registerStoreName;
        TextView btn_storeManager;
        ImageView iv_publish;
        TextView tv_storeManager;
    }
}
