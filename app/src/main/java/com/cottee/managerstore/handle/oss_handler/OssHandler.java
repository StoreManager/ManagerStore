package com.cottee.managerstore.handle.oss_handler;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.Image;
import android.os.Handler;
import android.os.Message;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.cottee.managerstore.utils.ToastUtils;
import com.cottee.managerstore.utils.myt_oss.ConfigOfOssClient;

/**
 * Created by Administrator on 2018/4/1.
 */

public class OssHandler extends Handler {
    private Context context;
    private ImageView imv;

    public OssHandler(Context context) {
        this.context = context;
    }

    public OssHandler(Context context, ImageView imv) {
        this.context = context;
        this.imv = imv;
    }

    @Override
    public void handleMessage(Message msg) {
        switch(msg.what){
            case ConfigOfOssClient.WHAT_SUCCESS_UPLOAD:
                ToastUtils.showToast(context,(String) msg.obj);
                break;
            case ConfigOfOssClient.WHAT_FAILED_UPLOAD:
                ToastUtils.showToast(context,(String) msg.obj);
                break;
            case  ConfigOfOssClient.WHAT_SUCCESS_DOWNLOAD:
                Bitmap bitmap = (Bitmap) msg.obj;
                if(bitmap!=null){
                    System.out.println("下载图片bitmap:"+bitmap);
                    imv.setImageBitmap(bitmap);
//

                    String success_download = "图片下载成功";
//                    Toast.makeText(context, success_download, Toast.LENGTH_SHORT).show();
                }
                System.out.println("下载图片bitmap"+bitmap);
                break;
            case ConfigOfOssClient.WHAT_FAILED_DOWNLOAD:
                String failed_download = (String) msg.obj;
                Toast.makeText(context, failed_download, Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }
}
