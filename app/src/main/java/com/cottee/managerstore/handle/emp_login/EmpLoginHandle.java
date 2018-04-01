package com.cottee.managerstore.handle.emp_login;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;

import com.cottee.managerstore.activity1.emp_login.EmpMainActivity;
import com.cottee.managerstore.bean.emp_login.EmpRequestInfo;
import com.cottee.managerstore.manage.enp_login.EmpMessage;
import com.cottee.managerstore.properties.Properties;
import com.cottee.managerstore.utils.ToastUtils;

/**
 * Created by Administrator on 2018/3/14.
 */

public class EmpLoginHandle extends Handler {
    private  Context context;
    private  String empId;
    private  String psd;


    public EmpLoginHandle(Context context,String empId,String psd) {
        this.context = context;
        this.empId  = empId;
        this.psd = psd;
    }

    @Override
    public void handleMessage(Message msg) {
        switch(msg.what){
        case Properties.EMP_LOGIN:
            switch (msg.arg1){
                case 32:
                    EmpRequestInfo.setSession((String) msg.obj);
                    EmpRequestInfo.setUserEmail(empId);
                    EmpRequestInfo.setUserPassword(psd);
                    Intent intent = new Intent(context, EmpMainActivity.class);
                    context.startActivity(intent);
                    ToastUtils.showToast( context, "登录成功" );
                    EmpMessage userManage = new EmpMessage();
                    userManage.saveEmpLogin( context, empId, psd,true );
                    break;

                case 1:
                    ToastUtils.showToast(context,"账号不存在/密码错误");
                    break;
            }
        break;

        default:
        break;
        }
    }
}
