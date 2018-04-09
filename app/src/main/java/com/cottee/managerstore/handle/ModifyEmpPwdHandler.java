package com.cottee.managerstore.handle;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

import com.cottee.managerstore.properties.Properties;

import static com.cottee.managerstore.manage.ModifyEmpPwdManage.EMPPWDMF;

/**
 * Created by Administrator on 2018-04-03.
 */

public class ModifyEmpPwdHandler extends Handler {
    private static final int MODIFYSUCCESS = 1;
    private static final int MODIFYFAILED = 0;
    private Context context;
    public ModifyEmpPwdHandler(Context context)
    {
        this.context=context;
    }

    @Override
    public void handleMessage(Message msg) {
        switch (msg.what)
        {
            case EMPPWDMF:
                switch (msg.arg1)
                {
                    case MODIFYSUCCESS:
                        Toast.makeText(context, "密码修改成功", Toast
                                .LENGTH_SHORT)
                                .show();
                        break;
                    case MODIFYFAILED:
                        Toast.makeText(context, "密码修改失败", Toast
                                .LENGTH_SHORT)
                                .show();
                        break;
                    default:
                        break;
                }
                break;
                default:
                    break;
        }
    }
}
