package com.cottee.managerstore.manage.enp_login;

import android.content.Context;
import android.os.Message;
import android.util.Log;

import com.cottee.managerstore.bean.emp_login.EmpRequestInfo;
import com.cottee.managerstore.handle.emp_login.EmpUpdateHandle;
import com.cottee.managerstore.properties.Properties;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Administrator on 2018/3/25.
 */

public class EmpUpdateManage {
    private Context context;
    private EmpUpdateHandle handler ;

    public EmpUpdateManage(EmpUpdateHandle handler) {
        this.handler = handler;
    }

    private void sendRequest(final int type, final String empSex, final String empBirth, final String empPhone, final String empPicture) {
        new Thread() {
            @Override
            public void run() {
                try {
                    String session = EmpRequestInfo.getSession();
                    OkHttpClient client = new OkHttpClient();
                    Request request = null;
                    switch (type) {
                        case Properties.EMP_UPDATE:
                            request = new Request.Builder().url(Properties.EMP_UPDATE_PATH).post(new FormBody.Builder().add("session", session)
                                    .add("sex", empSex).add("birth", empBirth).add("phone_number", empPhone).add("photo", empPicture).build())
                                    .build();
                            break;
                        default:
                            break;
                    }

                    Response response = client.newCall(request).execute();

                    if (response.isSuccessful()) {
                        String str = response.body().string();
                        Log.i("ServerBackCode(服务 器返回):", str);

                        Message message = new Message();
                        message.what = type;
                        message.arg1 = Integer.parseInt(str);
                        handler.sendMessage(message);

                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();

    }

    public void empUpdate(String empSex,String empBirth,String empPhone,String empPicture) {

        sendRequest(Properties.EMP_UPDATE,empSex,empBirth,empPhone,empPicture);

    }

}
