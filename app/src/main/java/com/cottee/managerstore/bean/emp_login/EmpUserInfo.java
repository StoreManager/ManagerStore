package com.cottee.managerstore.bean.emp_login;

/**
 * Created by Administrator on 2018/3/19.
 */

public class EmpUserInfo {
    private String account;
    private boolean isLogin;
    private String password;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public boolean isLogin() {
        return isLogin;
    }

    public void setLogin(boolean login) {
        isLogin = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
