package com.cottee.managerstore.httputils;

import android.content.Context;

import com.cottee.managerstore.bean.UserRequestInfo;

import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Created by Administrator on 2018/1/17.
 */

public class HttpUtilSession {
    public static void sendSessionOkHttpRequest(Context context,final String address, final Callback callback) {

        String session = UserRequestInfo.getSession();

        OkHttpClient client = new OkHttpClient();

        RequestBody requestBody = new FormBody.Builder().add("session",session).build();

        Request request = new Request.Builder()
                .url(address).post(requestBody)
                .build();
        client.newCall(request).enqueue(callback);
    }
}
