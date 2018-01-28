package com.cottee.managerstore.manage;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.cottee.managerstore.bean.UserRequestInfo;
import com.cottee.managerstore.handle.LoginRegisterInformationHandle;
import com.cottee.managerstore.properties.Properties;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Administrator on 2017/12/20.
 */

public class ProjectTypeManage {

    private final static int PROJECT_MANAGE_LARGE_INFORMATION = 13;
    private Context context;
    private Handler handler;



    public ProjectTypeManage(Context context, LoginRegisterInformationHandle handler) {
        this.context = context;
        this.handler = handler;
    }


    private void sendRequest(final int type, final String typeNum,final String classId) {
        new Thread() {
            @Override
            public void run() {
                try {
                    String session = UserRequestInfo.getSession();


                    OkHttpClient client = new OkHttpClient();
                    Request request = null;
                    switch (type) {
                        case PROJECT_MANAGE_LARGE_INFORMATION:
                            request = new Request.Builder().url(Properties.PROJECT_MANAGE_ADD_PATH).post(new FormBody.Builder().add("name", typeNum).add("session",session).build())
                                    .build();
                            break;
                        case Properties.PROJECT_MANAGE_DELETE:
                            request = new Request.Builder().url(Properties.PROJECT_MANAGE_DELETE_PATH).post(new FormBody.Builder().add("class_id",
                                    classId).add("session",session).build())
                                    .build();
                            break;
                        case Properties.PROJECT_MANAGE_UPDATE:
                            request = new Request.Builder().url(Properties.PROJECT_MANAGE_UPDATE_PATH).post(new FormBody.Builder().add("name",
                                    typeNum).add("class_id",
                                    classId).add("session",session).build())
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


    public void projectManageCommit(String typeName) {

        sendRequest(PROJECT_MANAGE_LARGE_INFORMATION, typeName,"");

    }

    public void projectManageDelete(String classId) {

        sendRequest(Properties.PROJECT_MANAGE_DELETE,"",classId);

    }

    public void projectManageUpdate(String typeName,String classId) {

        sendRequest(Properties.PROJECT_MANAGE_UPDATE,typeName,classId);

    }


}
