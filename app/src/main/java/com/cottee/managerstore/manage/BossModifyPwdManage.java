package com.cottee.managerstore.manage;

import android.content.Context;
import android.os.Message;
import android.util.Log;

import com.cottee.managerstore.handle.BossModifyPwdHandler;
import com.cottee.managerstore.handle.ModifyEmpPwdHandler;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Administrator on 2018-04-04.
 */

public class BossModifyPwdManage {
    public final static int BOSSPWDMF=10;
    public static String str;
    private Context context;
    private BossModifyPwdHandler handle;
    public BossModifyPwdManage(Context context, BossModifyPwdHandler handle)
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
                        case BOSSPWDMF:
                            request=new Request.Builder().url("https://thethreestooges.cn:1225/login/change/pwd")
                                    .post(new FormBody.Builder()
                                            .add("pwd",password)
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
    public void modifyBossPwd(String password,String session) {

        sendRequest(BOSSPWDMF,password,session);

    }
}
