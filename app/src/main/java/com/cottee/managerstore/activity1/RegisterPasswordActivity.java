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
 * Created by Administrator on 2017/11/19.
 */

public class RegisterPasswordActivity extends Activity {

    private Button btinput;
    private EditText etinputpwd;
    private EditText et_again_input_pwd;
    private String etAgainInputPWD;
    private String etInputPWD;
    private String emailAddress;
    private Button btnbacktoregisteremail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_register_password);
        btinput = (Button) findViewById(R.id.bt_input);
        etinputpwd = (EditText) findViewById(R.id.et_input_pwd);
        et_again_input_pwd = (EditText) findViewById(R.id.et_again_input_pwd);
        btnbacktoregisteremail = (Button) findViewById(R.id.btn_back_to_registeremail);


        Intent intent = getIntent();

        emailAddress = intent.getStringExtra("email");


        btinput.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {

                etInputPWD = etinputpwd.getText().toString().trim();
                etAgainInputPWD = et_again_input_pwd.getText().toString().trim();


                if(!etInputPWD.isEmpty()||!etAgainInputPWD.isEmpty()){
                    if(etInputPWD.equals(etAgainInputPWD)){
                        if(etInputPWD.length()>=6){
                            LoginRegisterInformationManage pwsManage = new LoginRegisterInformationManage(RegisterPasswordActivity.this, new
                                    LoginRegisterInformationHandle(RegisterPasswordActivity.this,""));
                            pwsManage.finishUserRegister(emailAddress,etInputPWD);

                            Intent intent = new Intent(RegisterPasswordActivity.this,RegisterFinishActivity.class);
                            startActivity(intent);
                        }
                        else {
                            ToastUtils.showToast(RegisterPasswordActivity.this,"密码不能小于六位");
                        }


                    }else{
                        ToastUtils.showToast(RegisterPasswordActivity.this,"两次密码不一致");
                        return;
                    }
                }else {
                    ToastUtils.showToast(RegisterPasswordActivity.this,"密码不能为空");
                }


            }
        });


        btnbacktoregisteremail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                overridePendingTransition(R.anim.left_in, R.anim.right_out);
            }
        });




    }

}
