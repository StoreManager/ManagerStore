package com.cottee.managerstore.manage;

import android.app.DownloadManager;
import android.content.Context;
import android.os.Message;
import android.util.Log;


import com.cottee.managerstore.handle.LoginRegisterInformationHandle;
import com.cottee.managerstore.properties.Properties;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


/**
 * Created by Administrator on 2017/11/21.
 */

public class LoginRegisterInformationManage {
    private Context context;
    private LoginRegisterInformationHandle handler;

    public final static int USER_LOGIN = 1;
    public final static int CHECKOUT_EMAIL = 2;
    public final static int CHECKOUT_EMAIL_VER = 3;
    public final static int FINISH_USER_REGISTER = 4;
    public final static int FORGET_PASSWORD_EMAIL_SUBMIT = 5;
    public final static int FORGET_PASSWORD_EMAIL_VER_SUBMIT = 6;
    public final static int FORGET_PASSWORD_EMAIL_PSD_SUBMIT = 7;
    private String str;

    public LoginRegisterInformationManage(Context context, LoginRegisterInformationHandle handler) {
        this.context = context;
        this.handler = handler;
    }

    /**
     * 发送数据请求
     *
     * @param type     数据请求类型
     * @param email    邮箱
     * @param password 密码
     */
    private void sendRequest(final int type, final String email,  final
    String password) {
        new Thread() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();
                    Request request = null;
                    switch (type) {
                        case USER_LOGIN:
                            request = new Request.Builder().url(Properties.LOGIN_PATH).post(new FormBody.Builder().add("username", email).add("password",
                                    password).build()).build();
                            break;
                        case CHECKOUT_EMAIL:
                            request = new Request.Builder().url(Properties.EMAIL_SUBMIT_PATH).post(new FormBody.Builder().add("mail_address", email).build()).build();
                            break;
                        case CHECKOUT_EMAIL_VER:
                            request = new Request.Builder().url(Properties.MAIL_VERIFICATION_PATH).post(new FormBody.Builder().add("mail_address", email).add(" " +
                                    "mail_ver", password).build()).build();
                            break;
                        case FINISH_USER_REGISTER:
                            request = new Request.Builder().url(Properties.USER_BUILD_PATH).post(new FormBody.Builder().add("mail_address", email).add(" " +
                                    "password", password).build()).build();
                            break;
                        case FORGET_PASSWORD_EMAIL_SUBMIT:
                            request = new Request.Builder().url(Properties.FORGET_PASSWORD_EMAIL_SUBMIT_PATH).post(new FormBody.Builder().add("mail_address", email).build()).build();
                            break;

                        case FORGET_PASSWORD_EMAIL_VER_SUBMIT:
                            request = new Request.Builder().url(Properties.FORGET_PASSWORD_EMAIL_VER_SUBMIT_PATH).post(new FormBody.Builder().add("mail_address", email).add(" " +
                                    "mail_ver", password).build()).build();
                            break;

                        case FORGET_PASSWORD_EMAIL_PSD_SUBMIT:
                            request = new Request.Builder().url(Properties.FORGET_PASSWORD_EMAIL_PSD_SUBMIT_PATH).post(new FormBody.Builder().add
                                    ("mail_address", email).add(" " +"password", password).build()).build();
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
                        message.arg1 = Integer.parseInt(str);
                        handler.sendMessage(message);

                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();

    }

    /**
     * 用户登陆
     *
     * @param email    用户邮箱
     * @param password 密码
     */
    public void userLogin(String email, String password) {

        sendRequest(USER_LOGIN, email, password);

    }

    /**
     * 请求验证码
     *
     * @param email 邮箱字符串
     */
    public void checkoutEmail(String email) {

        sendRequest(CHECKOUT_EMAIL, email, null);

    }

    /**
     * 提交验证码和邮件地址
     *
     * @param email       邮件地址
     * @param verPassword 验证码
     * @return
     */
    public void checkoutEmailVer(String email, String verPassword) {

         sendRequest(CHECKOUT_EMAIL_VER, email, verPassword);


    }

    /**
     * 提交邮箱和密码
     *
     * @param email    邮箱
     * @param password 密码
     */
    public void finishUserRegister(String email, String password) {

        sendRequest(FINISH_USER_REGISTER, email, password);

    }

    /**
     * 忘记密码请求验证码
     *
     * @param email 邮箱字符串
     */
    public void forgetPSDCheckOutEmail(String email) {

        sendRequest(FORGET_PASSWORD_EMAIL_SUBMIT, email, null);

    }

    /**
     * 忘记密码请求邮箱验证码
     *
     * @param email 邮箱字符串
     */
    public void forgetPSDCheckOutEmailVar(String email,String varPassword) {

        sendRequest(FORGET_PASSWORD_EMAIL_VER_SUBMIT, email, varPassword);

    }



    /**
     * 忘记密码请求邮箱密码
     *
     * @param email 邮箱字符串
     */
    public void forgetPSDCheckOutEmailPwd(String email,String password) {

        sendRequest(FORGET_PASSWORD_EMAIL_PSD_SUBMIT, email, password);

    }






    /**
     * 删除表
     */
    public static void deleteTable() {
        new Thread() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder().url("  http://thethreestooges.cn/consumer/test/temp_delete.php").get().build();
                    Response response = client.newCall(request).execute();
                    if (response.isSuccessful()) {
                        System.out.println(response.body().string());
                    }
                    /*request= new Request.Builder().url(" " + "http://thethreestooges.cn/consumer/test/user_delete.php").get().build();
                    Response execute = client.newCall(request).execute();*/
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }


}
