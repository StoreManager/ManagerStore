package com.cottee.managerstore.httputils;

import com.cottee.managerstore.bean.UserRequestInfo;

import okhttp3.Callback;
import okhttp3.FormBody;

/**
 * Created by Administrator on 2018/3/11.
 */

public class Https {

    public static void sendSessionAndFieldOkHttpRequest(final String address,
                                                        final String field,
                                                        final String data,
                                                        final Callback callback) {

        String session = UserRequestInfo.getSession();
        okhttp3.OkHttpClient client = new okhttp3.OkHttpClient();
        okhttp3.Request request = new okhttp3.Request.Builder()
                .url(address)
                .post(new FormBody.Builder()
                        .add("session",session)
                        .add(field,data)
                        .build())
                .build();
        client.newCall( request ).enqueue((okhttp3.Callback) callback);
    }
}
