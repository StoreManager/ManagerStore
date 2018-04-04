package com.cottee.managerstore.handle;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

import com.cottee.managerstore.properties.Properties;

/**
 * Created by user on 2018/4/4.
 */

public class ChangeVIPHandle extends Handler {
    private Context context;

    public ChangeVIPHandle(Context context) {
        this.context = context;
    }

    @Override
    public void handleMessage(Message msg) {
        super.handleMessage( msg );

        switch (msg.what) {
            case Properties.VIP_CHANGE:
                switch (msg.arg1) {
                    case 0:
                        Toast.makeText( context, "网络状态不稳定，稍后重试哦", Toast.LENGTH_SHORT ).show();
                        break;
                    case 1:
                        Toast.makeText( context,"修改成功",Toast.LENGTH_SHORT ).show();
                        break;
                }
                break;
            default:
                break;

        }
    }
}
