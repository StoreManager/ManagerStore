package com.cottee.managerstore.bean.emp_login;

/**
 * Created by Administrator on 2018/1/22.
 */

public class EmpRequestInfo {
    public static String session = "";
    public static String userEmail;
    public static String userPassword;


    public static String getSession() {
        return session;
    }

    public static void setSession(String session) {
        EmpRequestInfo.session = session;
    }

    public static String getUserEmail() {
        return userEmail;
    }

    public static void setUserEmail(String userEmail) {
        EmpRequestInfo.userEmail = userEmail;
    }

    public static String getUserPassword() {
        return userPassword;
    }

    public static void setUserPassword(String userPassword) {
        EmpRequestInfo.userPassword = userPassword;
    }

}
