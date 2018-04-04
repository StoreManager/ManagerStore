package com.cottee.managerstore.manage;

import android.content.Context;
import android.os.Message;
import android.util.Log;

import com.cottee.managerstore.bean.UserRequestInfo;
import com.cottee.managerstore.handle.LoginRegisterInformationHandle;
import com.cottee.managerstore.properties.Properties;

import java.io.File;
import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by user on 2017/12/9.
 */

public class SubmitStoreInfoManager {
    private Context context;
    private LoginRegisterInformationHandle handler;
    private String str;
    public final static int NECESSARY_INFOMATION = 8;
    public final static int PHOTO_BULIC = 9;
    public final static int SUPPLY_INFO = 14;
    public final static int SUPPLY_STORE_ID = 19;
    public final static int PHOTO_SURFACE = 18;

    public SubmitStoreInfoManager(Context context, LoginRegisterInformationHandle handler) {
        this.context = context;
        this.handler = handler;
    }

    private void sendRequest(final int type, final String name, final String classify, final String
            address, final String phone, final double lat, final double lng,final String
            photoUrl,final String city,final String circle,final String photo) {

        new Thread() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();
                    Request request = null;
                    switch (type) {
                        case NECESSARY_INFOMATION:
                            request = new Request.Builder().url( Properties.NECESSARY_INFO ).post( new FormBody
                                    .Builder().add(
                                    "session", UserRequestInfo.getSession() ).add( "name", name )
                                    .add( "classify",
                                            classify ).add( "latitude", String.valueOf( lat ) ).add(
                                            "longitude", String.valueOf( lng ) ).add(
                                            "address", address ).add(
                                            "phone", phone ).add( "photo",photoUrl ).add(
                                                    "city", city)
                                    .add( "circle", "hahaha").add( "photo",photo )
                                    .build() )
                                    .build();
                            break;
                        default:
                            break;
                    }

                    Response response = client.newCall( request ).execute();

                    if (response.isSuccessful()) {
                        str = response.body().string();
                        Log.i( "ServerBackCode(服务 器返回):", str );
                        Message message = new Message();
                        message.what = type;
                        message.arg1 = Integer.parseInt( str );
                        handler.sendMessage( message );
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }.start();
    }

    private void sendEnvirRequest(final int type, final String path) {
        new Thread() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();
                    Request request = null;
                    switch (type) {
                        case PHOTO_BULIC:
                            request = new Request.Builder().url( Properties.NECESSARY_INFO ).post( new FormBody
                                    .Builder().add(
                                    "session", UserRequestInfo.getSession() ).add( "envir ", path )
                                    .build() )
                                    .build();
                            break;
                        default:
                            break;
                    }

                    Response response = client.newCall( request ).execute();

                    if (response.isSuccessful()) {
                        str = response.body().string();
                        Log.i( "ServerBackCode(服务 器返回):", str );
                        Message message = new Message();
                        message.what = type;
                        message.arg1 = Integer.parseInt( str );
                        handler.sendMessage( message );
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }.start();
    }

    private void sendRequest(final int type, final String introduce, final String time, final
    String money, final String reserve, final String phone,final String url) {
        new Thread() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();
                    Request request = null;
                    switch (type) {
                        case SUPPLY_INFO:
                            request = new Request.Builder().url( Properties.SUPPLY_INFOS ).post( new FormBody
                                    .Builder().add( "introduce", introduce )
                                    .add( "business_hours",
                                    time ).add( "reserve", reserve ).add( "avecon", money ).add( "session",UserRequestInfo.getSession
                                    () ).add( "photo_surface",url ).add( "status","false" )
                                    .build() )
                                    .build();
                            break;
                        default:
                            break;
                    }

                    Response response = client.newCall( request ).execute();

                    if (response.isSuccessful()) {
                        str = response.body().string();
                        Log.i( "ServerBackCode(服务 器返回):", str );
                        Message message = new Message();
                        message.what = type;
                        message.arg1 = Integer.parseInt( str );
                        handler.sendMessage( message );
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }.start();
    }

    private void sendStoreIdRequest(final int type, final String storeId) {
        new Thread() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();
                    Request request = null;
                    switch (type) {
                        case SUPPLY_STORE_ID:
                            request = new Request.Builder().url( Properties.SUPPLY_STORE_ID ).post( new FormBody
                                    .Builder().add( "session", UserRequestInfo.getSession() )
                                    .add( "mer_id",storeId )
                                    .build() )
                                    .build();
                            break;
                        default:
                            break;
                    }

                    Response response = client.newCall( request ).execute();

                    if (response.isSuccessful()) {
                        str = response.body().string();
                        Log.i( "ServerBackCode(服务 器返回):", str );
                        Message message = new Message();
                        message.what = type;
                        message.arg1 = Integer.parseInt( str );
                        handler.sendMessage( message );
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }.start();
    }



    public void submitInfo(String name, String classify, String
            address, String phone, double lat, double lng,String photoUrl,String city, String
            circle,String photo) {
        sendRequest( NECESSARY_INFOMATION, name, classify, address, phone, lat, lng,photoUrl,
                city,circle,photo);
    }

    public void submitEnvirInfo(String path) {
        sendEnvirRequest( PHOTO_BULIC, path );
    }

    public void changeInfo(String introduce, String time, String money, String reserve, String
            phone,String url) {
        sendRequest( SUPPLY_INFO, introduce, time, money, reserve, phone ,url);
    }
    public void submitStoreId(String storeId) {
        sendStoreIdRequest( SUPPLY_STORE_ID, storeId );
    }
}
