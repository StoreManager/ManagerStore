package com.cottee.managerstore.manage.enp_login;

import android.content.Context;
import android.os.Message;
import android.util.Log;

import com.cottee.managerstore.handle.emp_login.EmpLoginHandle;
import com.cottee.managerstore.properties.Properties;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Administrator on 2018/3/6.
 */

public class EmpLoginManage {
    private Context context;
    private EmpLoginHandle handler ;

    public EmpLoginManage(Context context, EmpLoginHandle handler) {
        this.context = context;
        this.handler = handler;
    }

    private void sendRequest(final int type, final String empName, final String pwd) {
        new Thread() {
            @Override
            public void run() {
                try {

                    OkHttpClient client = new OkHttpClient();
                    Request request = null;
                    switch (type) {
                        case Properties.EMP_LOGIN:
                            request = new Request.Builder().url(Properties.EMP_LOGIN_PATH).post(new FormBody.Builder().add("user_id", empName)
                                    .add("password", pwd).build())
                                    .build();
                            break;
                    }

                    Response response = client.newCall(request).execute();

                    if (response.isSuccessful()) {
                       String str = response.body().string();
                        Log.i("ServerBackCode(服务 器返回):", str);



                        Message message = new Message();
                        message.what = type;
                        message.obj = str;
                        message.arg1 = str.length();
                        handler.sendMessage(message);

                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();

    }

    public void empLogin(String empName,String pwd) {

        sendRequest(Properties.EMP_LOGIN, empName,pwd);

    }


}
