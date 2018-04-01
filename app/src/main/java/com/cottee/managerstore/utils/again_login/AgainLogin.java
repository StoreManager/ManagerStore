package com.cottee.managerstore.utils.again_login;

import android.content.Context;
import android.content.SharedPreferences;

import com.cottee.managerstore.bean.UserRequestInfo;
import com.cottee.managerstore.utils.ToastUtils;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Administrator on 2018/3/25.
 */

public class AgainLogin {
    public static boolean IS_UPDATE = false;
    //老板重新登录
    public static  void setBossAgainLogin(final Context context){
        final SharedPreferences config = context.getSharedPreferences("userLogin",
                MODE_PRIVATE);
        if (config.getBoolean("IS_LOGIN", true)) {
            final String email = config.getString("USER_EMAIL", "");
            final String psw = config.getString("PASSWORD", "");
            new Thread() {
                @Override
                public void run() {

                    try {
                        OkHttpClient client = new OkHttpClient();
                        Request request = new Request.Builder().url
                                ("https://thethreestooges.cn:1225/identity/login/login.php").post(new FormBody
                                .Builder().add("username", email).add
                                ("password", psw).build()).build();
                        Response response = client.newCall(request).execute();
                        if (response.isSuccessful()) {
                            String sessionId = response.body().string();
                            UserRequestInfo.setSession(sessionId);
                            System.out.println("session:" + sessionId);
                            ToastUtils.showToast(context,"重新登录成功");
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }.start();
        }

    }
}
