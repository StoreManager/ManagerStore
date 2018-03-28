package com.cottee.managerstore.manage;

import android.content.Context;
import android.os.Message;
import android.util.Log;

import com.cottee.managerstore.bean.UserRequestInfo;
import com.cottee.managerstore.handle.VIPStandardHandle;
import com.cottee.managerstore.properties.Properties;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by user on 2018/3/28.
 */

public class SubmitVIPStandardManager {
    private Context context;
    private VIPStandardHandle vipStandardHandle;
    public final static int VIP_UPLOAD = 32;

    public SubmitVIPStandardManager(Context context, VIPStandardHandle vipStandardHandle) {
        this.context = context;
        this.vipStandardHandle = vipStandardHandle;
    }

    private void sendRequest(final int type, final String vipname, final String min, final
    String discount) {
        new Thread() {

            private String str;

            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();
                    Request request = null;
                    switch (type) {
                        case VIP_UPLOAD:
                            request = new Request.Builder().url( Properties.VIP_STANDARD_PATH ).post( new FormBody
                                    .Builder().add(
                                    "session", UserRequestInfo.getSession() ).add( "VIP_name", vipname )
                                    .add( "min_num",
                                            min ).add( "discount", discount ).build() ).build();
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
                        vipStandardHandle.sendMessage( message );
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }.start();

    }

    public void submitVIP(final String vipname, final String min, final
    String discount){
        sendRequest( VIP_UPLOAD,vipname,min,discount );
    }
}
