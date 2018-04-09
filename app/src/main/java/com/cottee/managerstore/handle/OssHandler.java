package com.cottee.managerstore.handle;


import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

import com.cottee.managerstore.application.BaseApplication;

/**
 * Created by Administrator on 2018-03-24.
 */

public class OssHandler extends Handler {
    private Context context;
    public OssHandler(Context context)
    {
        this.context=context;
    }
    @Override
    public void handleMessage(Message msg) {
        switch (msg.what)
        {
            case 1:
                String success_upload = (String) msg.obj;
                Toast.makeText(context, success_upload, Toast.LENGTH_SHORT).show();
                break;
            case 2:
                String failed_upload = (String) msg.obj;
                Toast.makeText(context, failed_upload, Toast.LENGTH_SHORT).show();
                break;
            case 3:
                Bitmap bitmap = (Bitmap) msg.obj;
//                    iv_download.setImageBitmap(bitmap);
                String success_download = "成功下载";
                Toast.makeText(context, success_download, Toast.LENGTH_SHORT).show();
                break;
            case 4:
                String failed_download = (String) msg.obj;
                Toast.makeText(context, failed_download, Toast.LENGTH_SHORT).show();
                break;
                default:
                    break;
        }
        super.handleMessage(msg);
    }
}

