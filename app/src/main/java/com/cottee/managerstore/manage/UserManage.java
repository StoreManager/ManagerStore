package com.cottee.managerstore.manage;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.cottee.managerstore.bean.UserInfo;

/**
 * Created by Administrator on 2017/12/4.
 */

public class UserManage {

    private static UserManage instance;

    public UserManage() {
    }

    public static UserManage getInstance() {
        if (instance == null) {
            instance = new UserManage();
        }
        return instance;
    }


    /**
     * 保存自动登录的用户信息
     */
    public void saveUserLogin(Context context, String userEmail, String password,boolean islogin) {
        SharedPreferences sp = context.getSharedPreferences("userLogin", Context.MODE_PRIVATE);//Context.MODE_PRIVATE表示SharePrefences的数据只有自己应用程序能访问。
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("USER_EMAIL", userEmail);
        editor.putString("PASSWORD", password);
        editor.putBoolean("IS_LOGIN", islogin);
        editor.commit();
    }

    public void saveUserInfo(Context context, String userEmail, String password) {
        SharedPreferences sp = context.getSharedPreferences("userInfo", Context.MODE_PRIVATE);//Context.MODE_PRIVATE表示SharePrefences的数据只有自己应用程序能访问。
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("USER_EMAIL", userEmail);
        editor.putString("PASSWORD", password);
        editor.commit();
    }


    /**
     * 获取用户信息model
     *
     * @param context
     * @param
     * @param
     */
    public UserInfo getUserInfo(Context context) {
        SharedPreferences sp = context.getSharedPreferences("userLogin", Context.MODE_PRIVATE);
        UserInfo userInfo = new UserInfo();
        userInfo.setUserEmail(sp.getString("USER_EMAIL", ""));
        userInfo.setPassword(sp.getString("PASSWORD", ""));
        userInfo.setLogin(sp.getBoolean("IS_LOGIN", false));
        return userInfo;
    }


    /**
     * userInfo中是否有数据
     */
    public boolean hasUserInfo(Context context) {
        UserInfo userInfo = getUserInfo(context);
        if (userInfo != null) {
            if ((!TextUtils.isEmpty(userInfo.getUserEmail())) && (!TextUtils.isEmpty(userInfo.getPassword()))&&userInfo.isLogin()!=false) {//有数据
                return true;
            } else {
                return false;
            }
        }
        return false;
    }
}
