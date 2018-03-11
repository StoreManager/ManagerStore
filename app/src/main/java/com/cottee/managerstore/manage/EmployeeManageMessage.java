package com.cottee.managerstore.manage;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.cottee.managerstore.activity1.SuccessAddEmployeeActivity;
import com.cottee.managerstore.bean.AddEmployeeInfo;
import com.cottee.managerstore.bean.UserRequestInfo;
import com.cottee.managerstore.properties.Properties;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Administrator on 2018/3/6.
 */

public class EmployeeManageMessage {
    private Context context;
    private String str;

    public EmployeeManageMessage(Context context) {
        this.context = context;

    }


    private void sendRequest(final int type, final String empName,final String classId) {
        new Thread() {
            @Override
            public void run() {
                try {
                    String session = UserRequestInfo.getSession();


                    OkHttpClient client = new OkHttpClient();
                    Request request = null;
                    switch (type) {
                        case Properties.EMPLOYEE_ADD:
                            request = new Request.Builder().url(Properties.EMPLOYEE_ADD_PATH).post(new FormBody.Builder().add("name", empName).add("session",
                                    session).build())
                                    .build();
                            break;
                    }

                    Response response = client.newCall(request).execute();

                    if (response.isSuccessful()) {
                        str = response.body().string();
                        Log.i("ServerBackCode(服务 器返回):", str);

                        Gson gson = new Gson();
                        System.out.println("传来的账户密码："+str);
                        int id = gson.fromJson(str, AddEmployeeInfo.class).getStaff_id();
                        int password = gson.fromJson(str, AddEmployeeInfo.class).getPassword();

                        System.out.println("员工id:" + id);
                        System.out.println("员工密码:" + password);

                        Intent intent = new Intent(context, SuccessAddEmployeeActivity.class);
                        intent.putExtra("EMP_ID",id);
                        intent.putExtra("EMP_PSW",password);
                        intent.putExtra("EMP_NAME",empName);
                        context.startActivity(intent);


                        /*Message message = new Message();
                        message.what = type;
                        message.obj = str;
                        handler.sendMessage(message);*/

                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();

    }

    public void addEmployeeCommit(String empName) {

        sendRequest(Properties.EMPLOYEE_ADD, empName,"");

    }


}
