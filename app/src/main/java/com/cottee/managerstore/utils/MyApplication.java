package com.cottee.managerstore.utils;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.alibaba.sdk.android.oss.ClientConfiguration;
import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.OSSClient;
import com.alibaba.sdk.android.oss.common.auth.OSSCredentialProvider;
import com.alibaba.sdk.android.oss.common.auth.OSSStsTokenCredentialProvider;
import com.cottee.managerstore.httputils.HttpUtils;
import com.google.gson.Gson;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

/**
 * Created by chn on 2018/1/10.
 * (╯°□°）╯︵ ┻━┻ MMP好气啊！
 * (╯‵□′)╯︵┻━┻ 老子怒掀桌子！
 * ┻━┻︵╰(‵□′)╯︵┻━┻老子双手掀桌！
 * ┬─┬﻿ ノ( ゜-゜ノ) 算了，我不生气了！日子还得过老老实实敲吧~
 */

public class MyApplication extends Application {
    //todo 选取session并且判读当为“”时，则视为暂无登陆
    private static Context context;
    private static OSSClient ossClient;
    private static SharedPreferences config;
    public final static String endpoint = "http://oss-cn-shenzhen.aliyuncs" + ".com";
//    public static String KEY_ID = "STS.J4G2KHA54nktUV6g83uVygCjn";
//    public static String SECRET_KEY_ID = "2aEFRYXejM3BM7px7sQW3Vkb7fZT4TKM2FZN18VKyUuY";
//    public static String TOKEN = "CAIS/QF1q6Ft5B2yfSjIq/fyefH8rOoV2amfV3CHgzhmedlViIbBjDz2IHtKe3ZvAekZsfkwlWxT7fwclqp5QZUd0e9GxzM0vPpt6gqET9frma7ctM4p6vCMHWyUFGSIvqv7aPn4S9XwY+qkb0u++AZ43br9c0fJPTXnS+rr76RqddMKRAK1QCNbDdNNXGtYpdQdKGHaOITGUHeooBKJVxAx4Fsk0DMisP3vk5DD0HeE0g2mkN1yjp/qP52pY/NrOJpCSNqv1IR0DPGajnEPtEATq/gr0/0Yomyd4MvuCl1Q8giANPHP7tpsIQl2a643AadYq+Lmkvl1qmkSey1SFdInGoABZWT8M1buzA7KRNqHKKAzFbD3A/Ud7k1us6hSIhvLz7v5hUpdHqaMbAqI7dMN2Ww88KfV1rde7alJL7yjY7PSW20joRDaK/qr+/A5pHoxdePf/76duaw15aL4RCxly6hdK7ZwHICZdunQlbJ+VCurRyTliBTjKga632dwwChOs3U=";

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


    }
    public static Context getContext() {
        return context;
    }

    public static OSSClient getOSSClient() {
        return ossClient;
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
