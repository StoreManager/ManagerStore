package com.cottee.managerstore.utils;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.alibaba.sdk.android.oss.ClientConfiguration;
import com.alibaba.sdk.android.oss.OSSClient;
import com.alibaba.sdk.android.oss.common.auth.OSSCredentialProvider;
import com.alibaba.sdk.android.oss.common.auth.OSSStsTokenCredentialProvider;
import com.alibaba.sdk.android.push.CloudPushService;
import com.alibaba.sdk.android.push.CommonCallback;
import com.alibaba.sdk.android.push.noonesdk.PushServiceFactory;
import com.cottee.managerstore.httputils.HttpUtils;
import com.google.gson.Gson;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

import static android.content.ContentValues.TAG;

public class MyApplication extends Application {
    //todo 选取session并且判读当为“”时，则视为暂无登陆
    private static Context context;
    private static OSSClient ossClient;
    private static SharedPreferences config;
    public final static String endpoint = "http://oss-cn-shenzhen.aliyuncs" + ".com";

    /**
     * 获取全局上下文
     */
    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        config = context.getSharedPreferences("config",
                MODE_PRIVATE);
        initOSS();
        initCloudChannel(this);


    }
    public static Context getContext() {
        return context;
    }

    public static OSSClient getOSSClient() {
        return ossClient;
    }

    private static CloudPushService pushService;

    private void initCloudChannel(Context applicationContext) {
        PushServiceFactory.init(applicationContext);
        pushService = PushServiceFactory.getCloudPushService
                ();
        pushService.register(applicationContext, new CommonCallback() {
            @Override
            public void onSuccess(String response) {
                Log.d(TAG, "init cloudchannel success");
            }

            @Override
            public void onFailed(String errorCode, String errorMessage) {
                Log.d(TAG, "init cloudchannel failed -- errorcode:" + errorCode + " -- errorMessage:" + errorMessage);
            }
        });
        if (config.getBoolean("logined", false)) {
            String username = config.getString("Id", "");
            if (!username.equals(""))
                pushService.bindAccount(username, new CommonCallback() {
                    @Override
                    public void onSuccess(String s) {
                        Log.i("ClodePush", "Success");
                    }
                    @Override
                    public void onFailed(String s, String s1) {
                        Log.i("ClodePush", "onFailed");
                    }
                });
        }
    }
    private void initOSS() {
        HttpUtils.sendOkHttpRequest("https://thethreestooges.cn:5210/identity/oss/token.php", new Callback() {


            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(Response response) throws IOException {
                Gson gson = new Gson();
                OssBean ossBean = gson.fromJson(response.body().string
                        (), OssBean.class);
                String KEY_ID = ossBean.getCredentials().getAccessKeyId();
                String TOKEN = ossBean.getCredentials().getSecurityToken();
                String SECRET_KEY_ID = ossBean.getCredentials().getAccessKeySecret();
                OSSCredentialProvider credentialProvider = new
                        OSSStsTokenCredentialProvider(KEY_ID, SECRET_KEY_ID, TOKEN);
                ClientConfiguration conf = new ClientConfiguration();
                conf.setConnectionTimeout(15 * 1000); // 连接超时，默认15秒
                conf.setSocketTimeout(15 * 1000); // socket超时，默认15秒
                conf.setMaxConcurrentRequest(5); // 最大并发请求数，默认5个
                conf.setMaxErrorRetry(2); // 失败后最大重试次数，默认2次
                ossClient = new OSSClient(context, endpoint,
                        credentialProvider);


            }
        });
    }
}
