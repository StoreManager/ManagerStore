package com.cottee.managerstore.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.cottee.managerstore.R;
import com.cottee.managerstore.bean.UserRequestInfo;
import com.cottee.managerstore.bean.emp_login.EmpRequestInfo;
import com.cottee.managerstore.handle.BossModifyPwdHandler;
import com.cottee.managerstore.manage.BossModifyPwdManage;
import com.cottee.managerstore.utils.ToastUtils;

public class ModifyBossLoginPasswordActivity extends Activity {

    private EditText et_input_boss_pwd;
    private EditText et_input_new_boss_pwd;
    private EditText et_again_input_boss_new_pwd;
    private Button bt_input_boss_pass;
    private Context context=ModifyBossLoginPasswordActivity.this;
    private BossModifyPwdManage bossManage;
    private String oldPwd;
    private String newPwd;
    private String againNewPwd;
    private String userPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_boss_login_password);
        initView();
        initData();
        initEvent();
    }

    private void initView() {

        et_input_boss_pwd = findViewById(R.id.et_input_boss_pwd);
        et_input_new_boss_pwd = findViewById(R.id.et_input_new_boss_pwd);
        et_again_input_boss_new_pwd = findViewById(R.id.et_again_input_boss_new_pwd);
        bt_input_boss_pass = findViewById(R.id.bt_input_boss_pass);
    }
    private void initData()
    {
        bossManage = new BossModifyPwdManage(context,new BossModifyPwdHandler(context));

    }
    private void initEvent()
    {
        findViewById(R.id.btn_back_to_boss).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        bt_input_boss_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userPassword = UserRequestInfo.getUserPassword();
                oldPwd = et_input_boss_pwd.getText().toString();
                newPwd = et_input_new_boss_pwd.getText().toString();
                againNewPwd = et_again_input_boss_new_pwd.getText().toString();
                String session = UserRequestInfo.getSession();
               if (oldPwd.isEmpty()||newPwd.isEmpty()||againNewPwd.isEmpty())
               {
                   ToastUtils.showToast(context,"密码不能为空");
               }
               else
               {
                   if (!userPassword.equals(oldPwd))
                   {
                       ToastUtils.showToast(context,"原始密码输入错误");
                   }
                  if (!newPwd.equals(againNewPwd))
                  {
                      ToastUtils.showToast(context,"两次输入不一致");
                  }
                  else
                  {
                      bossManage.modifyBossPwd(newPwd,session);
                      finish();
                  }
               }
            }
        });
    }
}
