package com.cottee.managerstore.httputils;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;

/**
 * Created by Administrator on 2017-12-08.
 */

public class HttpUtils {
    public static void sendOkHttpRequest1(final String address, final okhttp3.Callback callback) {
        okhttp3.OkHttpClient client = new okhttp3.OkHttpClient();
        okhttp3.Request request = new okhttp3.Request.Builder().url(address).build();
        client.newCall(request).enqueue(callback);
    }
    public static void sendOkHttpRequest(final String address, final Callback callback) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(address)
                .build();
        client.newCall(request).enqueue(callback);
    }
}
