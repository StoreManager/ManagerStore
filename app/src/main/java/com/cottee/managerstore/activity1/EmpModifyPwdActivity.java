package com.cottee.managerstore.activity1;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.cottee.managerstore.R;
import com.cottee.managerstore.bean.UserRequestInfo;
import com.cottee.managerstore.bean.emp_login.EmpRequestInfo;
import com.cottee.managerstore.handle.ModifyEmpPwdHandler;
import com.cottee.managerstore.manage.LoginRegisterInformationManage;
import com.cottee.managerstore.manage.ModifyEmpPwdManage;
import com.cottee.managerstore.utils.ToastUtils;

public class EmpModifyPwdActivity extends Activity {

    private EditText et_input_new_pwd;
    private EditText et_again_input_emp;
    private Button btn_input_emp_pwd;
    private String newPassword;
    private String againPassword;
    private LoginRegisterInformationManage modifyEmpPassword;
private Context context=EmpModifyPwdActivity.this;
    private ModifyEmpPwdManage pwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emp_modify_pwd);
        initView();
        initData();
        initEvent();
    }
    private void initView()
    {
        et_input_new_pwd = findViewById(R.id.et_input_new_emp_pwd);
        et_again_input_emp = findViewById(R.id.et_again_input_emp_pwd);
        btn_input_emp_pwd = findViewById(R.id.bt_input_emp_pass);
    }
    private void initData()
    {
        pwd = new ModifyEmpPwdManage(context,new ModifyEmpPwdHandler(context));

    }
    private void initEvent()
    {
        findViewById(R.id.btn_back_to_emp).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btn_input_emp_pwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String session = EmpRequestInfo.getSession();
                newPassword = et_input_new_pwd.getText().toString();
                againPassword = et_again_input_emp.getText().toString();
                if(!newPassword.isEmpty()||!againPassword.isEmpty()){
                    if(newPassword.equals(againPassword)){
                        if (newPassword.length()>=6) {
                            pwd.modifyEmpPwd(newPassword,session);
                            finish();
//
                        }else{
                            ToastUtils.showToast(context,"密码不能小于六位");
                        }
                    }else{
                        ToastUtils.showToast(context,"两次密码不一致");
                        return;
                    }
                }else {
                    ToastUtils.showToast(context,"密码不能为空");
                }
            }
        });

    }
}
