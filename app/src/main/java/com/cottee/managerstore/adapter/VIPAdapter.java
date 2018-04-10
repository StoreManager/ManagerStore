package com.cottee.managerstore.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Base64;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cottee.managerstore.R;
import com.cottee.managerstore.activity.ChangeVIPStandardActivity;
import com.cottee.managerstore.bean.VIP;
import com.cottee.managerstore.handle.oss_handler.OssHandler;
import com.cottee.managerstore.utils.myt_oss.ConfigOfOssClient;
import com.cottee.managerstore.utils.myt_oss.DownloadUtils;

import java.io.File;
import java.util.List;

/**
 * Created by user on 2018/4/9.
 */

public class VIPAdapter extends BaseAdapter {
    private Context context;
    private List<VIP> vips;
    private View view;

    public VIPAdapter(Context context, List<VIP> vips) {
        this.context = context;
        this.vips = vips;
    }

    @Override
    public int getCount() {
        return vips.size();
    }

    @Override
    public Object getItem(int position) {
        return vips.get( position );
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final VIPAdapter.ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new VIPAdapter.ViewHolder();
            view = View.inflate( context, R.layout.item_vip, null );
            viewHolder.iv_icon=view.findViewById( R.id.iv_vipIcon );
            viewHolder.tv_name=view.findViewById( R.id.tv_vipname );
            viewHolder.tv_id=view.findViewById( R.id.tv_vipID );
            viewHolder.tv_standardName=view.findViewById( R.id.tv_standardNames );
            viewHolder.tv_min=view.findViewById( R.id.tv_vipmin );
            view.setTag( viewHolder );
        } else {
            viewHolder = (VIPAdapter.ViewHolder) view.getTag();
        }
        OssHandler ossHandler = new OssHandler( context,viewHolder.iv_icon);
        File cache_image = new File( context.getCacheDir(), Base64.encodeToString
                ( vips.get( position ).getPhoto().getBytes(),
                        Base64.DEFAULT ) );
        DownloadUtils.downloadFileFromOss( cache_image, ossHandler, ConfigOfOssClient
                .BUCKET_NAME, vips.get( position ).getPhoto() );
        viewHolder.tv_id.setText( "ID:" +vips.get( position ).getUser_id() );
        viewHolder.tv_name.setText( vips.get( position ).getNickname() );
        viewHolder.tv_min.setText( vips.get( position ).getIntegral() );
        viewHolder.tv_standardName.setText( vips.get( position ).getVip_name() );
        return view;
    }

    public static class ViewHolder {
        ImageView iv_icon;
        TextView tv_name;
        TextView tv_id;
        TextView tv_standardName;
        TextView tv_min;
    }
}
