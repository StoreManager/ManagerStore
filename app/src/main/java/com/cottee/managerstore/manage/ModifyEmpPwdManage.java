package com.cottee.managerstore.manage;

import android.content.Context;
import android.os.Message;
import android.util.Log;

import com.cottee.managerstore.bean.UserRequestInfo;
import com.cottee.managerstore.handle.LoginRegisterInformationHandle;
import com.cottee.managerstore.handle.ModifyEmpPwdHandler;
import com.cottee.managerstore.properties.Properties;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static com.cottee.managerstore.manage.LoginRegisterInformationManage
        .MODIFY_EMP_PASSWORD;
import static com.cottee.managerstore.manage.LoginRegisterInformationManage
        .MODIFY_EMP_PASSWORD_SUBMIT;

/**
 * Created by Administrator on 2018-04-03.
 */

public class ModifyEmpPwdManage {
    public final static int EMPPWDMF=9;
    public static String str;
    private Context context;
    private ModifyEmpPwdHandler handle;
    public ModifyEmpPwdManage(Context context, ModifyEmpPwdHandler handle)
    {
        this.context=context;
        this.handle=handle;
    }
    private void sendRequest(final int type,final
    String password,final String session) {
        new Thread() {
            @Override
            public void run() {
                try {
//                    String session = UserRequestInfo.getSession();
                    OkHttpClient client = new OkHttpClient();
                    Request request=null;
                    switch (type) {
                        case EMPPWDMF:
                            request=new Request.Builder().url("https://thethreestooges.cn:1225/staff/staff_change/pwd")
                                    .post(new FormBody.Builder()
                                            .add("password",password)
                                            .add("session",session).build())
                                    .build();
                            break;
                        default:
                            break;
                    }
                    Response response = client.newCall(request).execute();

                    if (response.isSuccessful()) {
                        str = response.body().string();
                        Log.i("ServerBackCode(服务 器返回):", str);
                        Message message = new Message();
                        message.what = type;
                        message.arg1 = Integer.parseInt(str);
                        handle.sendMessage(message);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();

    }
    public void modifyEmpPwd(String password,String session) {

        sendRequest(EMPPWDMF,password,session);

    }
}
