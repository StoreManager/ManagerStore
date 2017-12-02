package com.cottee.managerstore.activity1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import com.cottee.managerstore.R;
import com.cottee.managerstore.handle.LoginRegisterInformationHandle;
import com.cottee.managerstore.manage.LoginRegisterInformationManage;
import com.cottee.managerstore.utils.ToastUtils;


/**
 * Created by Administrator on 2017/11/26.
 */

public class ForgetPasswordActivity extends Activity {

    private Button btnforgetfinish;
    private EditText etforgetinputpwd;
    private EditText etforgetagaininputpwd;
    private Button btnforgetbacktoforgetemail;
    private String emailAddress;
    private String etInputPWD;
    private String etAgainInputPWD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_forget_password);

        btnforgetfinish = (Button) findViewById(R.id.btn_forget_finish);
        etforgetinputpwd = (EditText) findViewById(R.id.et_forget_input_pwd);
        etforgetagaininputpwd = (EditText) findViewById(R.id.et_forget_again_input_pwd);
        btnforgetbacktoforgetemail = (Button) findViewById(R.id.btn_forget_back_to_forgetemail);


        Intent intent = getIntent();

        emailAddress = intent.getStringExtra("email");


        btnforgetfinish.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {

                etInputPWD = etforgetinputpwd.getText().toString().trim();
                etAgainInputPWD = etforgetagaininputpwd.getText().toString().trim();

                if(!etInputPWD.isEmpty()||!etAgainInputPWD.isEmpty()){
                    if(etInputPWD.equals(etAgainInputPWD)){
                        if (etInputPWD.length()>=6) {
                            LoginRegisterInformationManage pwsManage = new LoginRegisterInformationManage(ForgetPasswordActivity.this, new
                                    LoginRegisterInformationHandle(ForgetPasswordActivity.this,""));
                            pwsManage.forgetPSDCheckOutEmailPwd(emailAddress, etInputPWD);

                            Intent intent = new Intent(ForgetPasswordActivity.this,BossLoginActivity.class);
                            startActivity(intent);
                        }else{
                            ToastUtils.showToast(ForgetPasswordActivity.this,"密码不能小于六位");
                        }
                    }else{
                        ToastUtils.showToast(ForgetPasswordActivity.this,"两次密码不一致");
                        return;
                    }
                }else {
                    ToastUtils.showToast(ForgetPasswordActivity.this,"密码不能为空");
                }


            }
        });


        btnforgetbacktoforgetemail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                overridePendingTransition(R.anim.left_in, R.anim.right_out);
            }
        });




    }

}
