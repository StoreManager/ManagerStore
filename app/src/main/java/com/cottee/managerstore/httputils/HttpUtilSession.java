package com.cottee.managerstore.httputils;

import android.content.Context;

import com.cottee.managerstore.bean.UserRequestInfo;
import com.cottee.managerstore.properties.Properties;

import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Administrator on 2018/1/17.
 */

public class HttpUtilSession {
    public static void sendSessionOkHttpRequest(Context context, final String address, final Callback callback) {

        String session = UserRequestInfo.getSession();
        OkHttpClient client = new OkHttpClient();

        RequestBody requestBody = new FormBody.Builder().add( "session", session ).build();

        Request request = new Request.Builder()
                .url( address ).post( requestBody )
                .build();
        client.newCall( request ).enqueue( callback );
    }

    public static void sendFoodDetailOkHttpRequest(Context context,
                                                   final String address,
                                                   final String classId,
                                                   final Callback callback) {

        String session = UserRequestInfo.getSession();
        OkHttpClient client = new OkHttpClient();
      Request  request = new Request.Builder()
              .url(Properties.PROJECT_DETAIL_MANAGE_GET_PATH)
                .post(new FormBody.Builder()
                        .add("session",session)
                        .add("class_id",classId)
                      .build())
                .build();
        client.newCall( request ).enqueue( callback );
    }

    public static void sendVIPOkHttpRequest(Context context,
                                                   final String address,
                                                   final String text,
                                                   final String lastId,
                                                   final Callback callback) {

        String session = UserRequestInfo.getSession();
        OkHttpClient client = new OkHttpClient();
        Request  request = new Request.Builder()
                .url(address)
                .post(new FormBody.Builder()
                        .add("session",session)
                        .add("name_part",text).add( "last_id",lastId )
                        .build())
                .build();
        client.newCall( request ).enqueue( callback );
    }
}
