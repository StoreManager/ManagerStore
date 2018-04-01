package com.cottee.managerstore.activity1;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;

import com.cottee.managerstore.R;
import com.cottee.managerstore.bean.UserRequestInfo;
import com.cottee.managerstore.handle.LoginRegisterInformationHandle;
import com.cottee.managerstore.manage.UserManage;
import com.cottee.managerstore.properties.Properties;
import com.cottee.managerstore.utils.myt_oss.ConfigOfOssClient;
import com.cottee.managerstore.utils.myt_oss.InitOssClient;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Administrator on 2017/12/3.
 */

public class SplashScreenActivity extends Activity {

    private LoginRegisterInformationHandle handle;
    private String sessionId;
    private String email;
    private String psw;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splashscreen);

        InitOssClient.initOssClient(this, ConfigOfOssClient.TOKEN_ADDRESS, ConfigOfOssClient.ENDPOINT);

        handle = new LoginRegisterInformationHandle(SplashScreenActivity.this, "");


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (UserManage.getInstance().hasUserInfo(SplashScreenActivity.this))//自动登录判断，SharePrefences中有数据，则跳转到主页，没数据则跳转到登录页
                {
                    final SharedPreferences config = getSharedPreferences("userLogin",
                            MODE_PRIVATE);
                    if (config.getBoolean("IS_LOGIN", true)) {
                        email = config.getString("USER_EMAIL", "");
                         psw = config.getString("PASSWORD", "");
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
                                    sessionId = response.body().string();
                                    UserRequestInfo.setSession(sessionId);
                                    UserRequestInfo.setUserEmail(email);
                                    UserRequestInfo.setUserPassword(psw);
                                    System.out.println("session:" + sessionId);
                                    handle.sendEmptyMessageDelayed(Properties.TO_HOME,0);
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                        }
                    }.start();

                }

                }else {
                    handle.sendEmptyMessageAtTime(Properties.TO_LOGIN, 2000);

                }

//                finish();


            }
        }, 1000 * 2);


    }

}

