package com.cottee.managerstore.activity1.emp_login;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.cottee.managerstore.R;
import com.cottee.managerstore.activity1.StoreManagerMainActivity;
import com.cottee.managerstore.handle.emp_login.EmpLoginHandle;
import com.cottee.managerstore.manage.enp_login.EmpLoginManage;
import com.cottee.managerstore.manage.enp_login.EmpMessage;
import com.cottee.managerstore.utils.ToastUtils;

/**
 * Created by Administrator on 2018/3/14.
 */

public class EmpLoginActivity extends Activity implements View.OnClickListener{

    private Button btn_emp_login;
    private TextView tv_back_to_main;
    private EditText et_emp_login_account;
    private EditText et_emp_login_password;
    private SharedPreferences config;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emp_login);
        initView();
        readData();
    }

    private void initView() {
        btn_emp_login = (Button) findViewById(R.id.btn_emp_login);
        tv_back_to_main = (TextView) findViewById(R.id.tv_back_to_main);
        et_emp_login_account = (EditText) findViewById(R.id.et_emp_login_account);
        et_emp_login_password = (EditText) findViewById(R.id.et_emp_login_password);
        btn_emp_login.setOnClickListener(this);
        tv_back_to_main.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
        case R.id.btn_emp_login:
            String loginEmail = et_emp_login_account.getText().toString().trim();
            String loginPassword = et_emp_login_password.getText().toString().trim();

            EmpMessage userManage = new EmpMessage();
            userManage.saveEmpInfo(EmpLoginActivity.this,loginEmail,loginPassword);

            if(!loginEmail.isEmpty()){
                if(!loginPassword.isEmpty()){
                    EmpLoginManage loginManage = new EmpLoginManage(EmpLoginActivity.this,new EmpLoginHandle(EmpLoginActivity.this,loginEmail,loginPassword));
                    loginManage.empLogin(loginEmail,loginPassword);
                }else {
                    ToastUtils.showToast(EmpLoginActivity.this,"亲，密码不能为空");
                }

            }else {
                ToastUtils.showToast(EmpLoginActivity.this,"亲，账号不能为空");
            }

        break;
        case R.id.tv_back_to_main:
            Intent intent = new Intent(EmpLoginActivity.this, StoreManagerMainActivity.class);
            startActivity(intent);
            finish();
        break;
        default:
        break;
        }
    }
    private void readData() {
        config = getSharedPreferences("empInfo", Context.MODE_PRIVATE);
        String number = config.getString("USER_EMAIL","");
        String psd  = config.getString("PASSWORD","");
        et_emp_login_account.setText(number);
        et_emp_login_password.setText(psd);

    }
}
