package com.cottee.managerstore.manage;

import android.content.Context;
import android.os.Message;
import android.util.Log;

import com.cottee.managerstore.bean.UserRequestInfo;
import com.cottee.managerstore.handle.ChangeVIPHandle;
import com.cottee.managerstore.handle.DeleteVIPHandle;
import com.cottee.managerstore.properties.Properties;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by user on 2018/4/4.
 */

public class DeleVIPStandardManager {
    private Context context;
    private DeleteVIPHandle deleteVIPHandle;
    public final static int VIP_DELE = 36;

    public DeleVIPStandardManager(Context context, DeleteVIPHandle deleteVIPHandle) {
        this.context = context;
        this.deleteVIPHandle = deleteVIPHandle;
    }

    private void sendRequest(final int type, final String vip_id) {
        new Thread() {

            private String str;

            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();
                    Request request = null;
                    switch (type) {
                        case VIP_DELE:
                            request = new Request.Builder().url( Properties.VIP_DELE_PATH ).post( new
                                    FormBody
                                            .Builder().add(
                                    "session", UserRequestInfo.getSession() ).add( "vip_id", vip_id )
                                    .build() ).build();
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
                        deleteVIPHandle.sendMessage( message );
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }.start();

    }

    public void deleteVIP(final String vip_id) {
        sendRequest( VIP_DELE, vip_id);
    }
}
