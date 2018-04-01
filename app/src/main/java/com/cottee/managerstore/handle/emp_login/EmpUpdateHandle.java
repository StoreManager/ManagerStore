package com.cottee.managerstore.handle.emp_login;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;

import com.cottee.managerstore.activity1.emp_login.EmpDetailMessageActivity;
import com.cottee.managerstore.properties.Properties;
import com.cottee.managerstore.utils.ToastUtils;

/**
 * Created by Administrator on 2018/3/25.
 */

public class EmpUpdateHandle extends Handler{
    private Context context;


    public EmpUpdateHandle(Context context) {
        this.context = context;
    }

    @Override
    public void handleMessage(Message msg) {
        switch(msg.what){
            case Properties.EMP_UPDATE:
                switch (msg.arg1){
                    case 1:
                        ToastUtils.showToast(context,"更新成功");
                        Intent intent = new Intent(context, EmpDetailMessageActivity.class);
                        context.startActivity(intent);
                        break;

                    case 0:
                        ToastUtils.showToast(context,"更新失败");
                        break;
                }
                break;
            default:
                break;
        }
    }
}
