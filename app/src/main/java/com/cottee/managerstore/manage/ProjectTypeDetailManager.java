package com.cottee.managerstore.manage;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.cottee.managerstore.activity1.ProjectManageActivity;
import com.cottee.managerstore.bean.UserRequestInfo;
import com.cottee.managerstore.handle.LoginRegisterInformationHandle;
import com.cottee.managerstore.properties.Properties;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static android.content.ContentValues.TAG;

/**
 * Created by Administrator on 2018-01-18.
 */

public class ProjectTypeDetailManager {
    public final static int PROJECT_MANAGE_DETAIL_INFORMATION = 20;
    private final static int PROJECT_MANAGE_DETAIL_JSON_INFORMATION = 24;
    private Context context;
    private Handler handler;
    public static String str;

    public ProjectTypeDetailManager(Context context, LoginRegisterInformationHandle handler) {
        this.context = context;
        this.handler = handler;
    }
    private void sendRequest(final int type
            , final String name
            ,final String discount_singe
            ,final String discount
            , final String classId
            ,final String itemid
            , final String univalence
            , final String description
            ,final String photo) {
        new Thread() {
            @Override
            public void run() {
                try {
                    String session = UserRequestInfo.getSession();

                    OkHttpClient client = new OkHttpClient();
                    Request request = null;
                    switch (type) {
                        case PROJECT_MANAGE_DETAIL_INFORMATION:
                            request = new Request.Builder()
                                    .url(Properties.PROJECT_DETAIL_MANAGE_ADD_PATH)
                                    .post(new FormBody.Builder()
                                    .add("name", name)
                                    .add("discount_singe",discount_singe)
                                    .add("discount",discount)
                                    .add("session",session)
                                    .add("class_id",classId)
                                    .add("univalence",univalence)
                                    .add("description",description)
                                    .add("photo",photo).build())
                                    .build();
                            break;
                        case Properties.PROJECT_DETAIL_MANAGE_DELETE:
                            request = new Request.Builder().url(Properties.PROJECT_DETAIL_MANAGE_DELETE_PATH)
                                    .post(new FormBody.Builder()
                                            .add("class_id", classId)
                                            .add("item_id",itemid)
                                            .add("session",session).build())
                                    .build();
                            break;
                        case Properties.PROJECT_DETAIL_MANAGE_UPDATE:
                            request = new Request.Builder()
                                    .url(Properties.PROJECT_DETAIL_MANAGE_UPDATE_PATH)
                                    .post(new FormBody.Builder()
                                            .add("name", name)
                                            .add("discount_singe",discount_singe)
                                            .add("discount",discount)
                                            .add("session",session)
                                            .add("class_id",classId)
                                            .add("univalence",univalence)
                                            .add("description",description)
                                            .add("photo",photo).build())
                                    .build();
                            break;
//                            case
                        case PROJECT_MANAGE_DETAIL_JSON_INFORMATION:
                            Log.i(TAG, "run: 11111111111111111111111"+session);
                            Log.i(TAG, "run: -------------------"+ classId);
                             request = new Request.Builder().url(Properties.PROJECT_DETAIL_MANAGE_GET_PATH)
                                    .post(new FormBody.Builder()
                                            .add("class_id", classId)
                                            .add("session",session).build())
                                    .build();
                            break;
                        default:
                            break;
                    }

                    Response response = client.newCall(request).execute();

                    if (response.isSuccessful()) {
                        str = response.body().string();
                        Log.i("ServerBackCode(服务 器返回):", str);
                        Message message = new Message();
                        message.what = type;
                        message.obj = str;
                        handler.sendMessage(message);

                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();

    }
    public void projectDetailManageCommit( String foodName,String discount_singe,String discount,String classId,String description,String univalence,String photo) {

        sendRequest(PROJECT_MANAGE_DETAIL_INFORMATION, foodName,discount_singe,discount,classId,"",description,univalence,photo);

    }

    public void projectDetailManageDelete(String classId,String itemId) {

        sendRequest(Properties.PROJECT_DETAIL_MANAGE_DELETE,"","","",classId,itemId,"","","");

    }

    public void projectDetailManageUpdate(String foodName,String discount_singe,String discount,String classId,String itemId,String univalence,String description,String photo) {

        sendRequest(Properties.PROJECT_DETAIL_MANAGE_UPDATE,foodName,discount_singe,discount,classId,itemId,description,univalence,photo);

    }
    public void projectDetailManageStick(String class_id,String item_id)
    {

    }
    public void JsonCommit( String classId) {

//        sendRequest(PROJECT_MANAGE_DETAIL_JSON_INFORMATION,"",classId,"","","","");

    }
}
