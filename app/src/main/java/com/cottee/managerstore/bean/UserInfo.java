package com.cottee.managerstore.bean;

/**
 * Created by Administrator on 2017/12/4.
 */

public class UserInfo {
    private String userEmail;
    private boolean isLogin;
    private String password;

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isLogin() {
        return isLogin;
    }

    public void setLogin(boolean login) {
        this.isLogin = login;
    }
}
