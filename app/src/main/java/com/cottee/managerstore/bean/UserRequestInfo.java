package com.cottee.managerstore.bean;

/**
 * Created by Administrator on 2018/1/22.
 */

public class UserRequestInfo {
    public static String session = "";
    public static String userEmail;
    public static String userPassword;
    public static String dishType;


    public static String getSession() {
        return session;
    }

    public static void setSession(String session) {
        UserRequestInfo.session = session;
    }

    public static String getUserEmail() {
        return userEmail;
    }

    public static void setUserEmail(String userEmail) {
        UserRequestInfo.userEmail = userEmail;
    }

    public static String getUserPassword() {
        return userPassword;
    }

    public static void setUserPassword(String userPassword) {
        UserRequestInfo.userPassword = userPassword;
    }

    public static String getDishType() {
        return dishType;
    }

    public static void setDishType(String dishType) {
        UserRequestInfo.dishType = dishType;
    }
}
