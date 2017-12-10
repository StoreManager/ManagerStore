package com.cottee.managerstore.manage;

import android.content.Context;
import android.os.Message;
import android.util.Log;

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
    public final static int NECESSARY_INFOMATION= 8;
    public final static int PHOTO_BULIC=9;

    public SubmitStoreInfoManager(Context context, LoginRegisterInformationHandle handler) {
        this.context = context;
        this.handler = handler;
    }

    private void sendRequest(final int type, final String name, final String classify, final String
            address, final String phone) {
        new Thread() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();
                    Request request = null;
                    switch (type) {
                        case NECESSARY_INFOMATION:
                            request = new Request.Builder().url( Properties.NECESSARY_INFO).post( new FormBody
                                    .Builder().add( "name", name ).add( "classify",
                                    classify ).add( "address", address ).add( "phone", phone )
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
                        message.arg1=Integer.parseInt(str);
                        handler.sendMessage( message );
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }.start();
    }

    private void sendRequest(final int type, final String path) {
        new Thread() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();
                    Request request = null;
                    switch (type) {
                        case PHOTO_BULIC:
                            MediaType MEDIA_TYPE_PNG = MediaType.parse("image/png");
                            File f = new File(path);
                            MultipartBody requestBody= new MultipartBody.Builder().setType( MultipartBody.FORM )
                                    .addFormDataPart( "photo_buslic", f.getName(), RequestBody.create(
                                            MEDIA_TYPE_PNG, f ) ).build();
                            request = new Request.Builder().url( Properties.PHOTO_BUSLIC).post(requestBody)
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
                        message.obj=str;
                        handler.sendMessage( message );
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }.start();
    }
    public void submitInfo(String name, String classify,String
            address, String phone) {
        sendRequest( NECESSARY_INFOMATION,name,classify,address,phone);
    }

    public void submitInfo(String path) {
        sendRequest( PHOTO_BULIC,path);
    }
}
