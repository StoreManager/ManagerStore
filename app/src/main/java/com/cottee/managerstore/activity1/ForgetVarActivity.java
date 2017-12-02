package com.cottee.managerstore.activity1;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import com.cottee.managerstore.R;
import com.cottee.managerstore.handle.LoginRegisterInformationHandle;
import com.cottee.managerstore.manage.LoginRegisterInformationManage;
import com.cottee.managerstore.utils.Canonical;
import com.cottee.managerstore.utils.TimeCount;
import com.cottee.managerstore.utils.ToastUtils;


/**
 * Created by Administrator on 2017/11/26.
 */

public class ForgetVarActivity extends Activity implements View.OnClickListener {

    private Button btnforgetemailnext;
    private Button btnforgetgetauthenticationcode;
    private EditText etforgetinputemailaddress;
    private EditText etforgetinputauthenticationcode;
    private Button btnforgetbacktologinregister;
    private String emailAddress;
    private String authenticationcode;
    private TimeCount timeCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_forget_var);




        btnforgetemailnext = (Button) findViewById(R.id.btn_forget_email_next);
        btnforgetgetauthenticationcode = (Button) findViewById(R.id.btn_forget_get_authentication_code);
        etforgetinputemailaddress = (EditText) findViewById(R.id.et_forget_input_email_address);
        etforgetinputauthenticationcode = (EditText) findViewById(R.id.et_forget_input_authentication_code);
        btnforgetbacktologinregister = (Button) findViewById(R.id.btn_forget_back_to_loginregister);



        btnforgetemailnext.setOnClickListener(this);
        btnforgetgetauthenticationcode.setOnClickListener(this);
        btnforgetbacktologinregister.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.btn_forget_email_next:
                emailAddress = etforgetinputemailaddress.getText().toString().trim();
                authenticationcode = etforgetinputauthenticationcode.getText().toString().trim();

                if(!emailAddress.isEmpty()){
                    if(new Canonical().isEmail(emailAddress)){

                        if(!authenticationcode.isEmpty()){

                            LoginRegisterInformationManage emailvarmanage = new LoginRegisterInformationManage(ForgetVarActivity.this, new
                                    LoginRegisterInformationHandle(ForgetVarActivity.this, emailAddress));

                            emailvarmanage.forgetPSDCheckOutEmailVar(emailAddress, authenticationcode);
                        }else{
                            ToastUtils.showToast(ForgetVarActivity.this,"验证码不能为空");
                        }


                    }else {
                        ToastUtils.showToast(ForgetVarActivity.this,"邮箱格式不正确");
                    }


                }else {
                    ToastUtils.showToast(ForgetVarActivity.this,"邮箱不能为空");
                }




                break;
            case R.id.btn_forget_get_authentication_code:

                emailAddress = etforgetinputemailaddress.getText().toString().trim();
                if(new Canonical().isEmail(emailAddress)){

                    timeCount = new TimeCount(100000,1000,btnforgetgetauthenticationcode,ForgetVarActivity.this);
                    timeCount.start();
                    LoginRegisterInformationManage emailmanage = new LoginRegisterInformationManage(ForgetVarActivity.this, new
                            LoginRegisterInformationHandle(ForgetVarActivity.this,""));
                    emailmanage.forgetPSDCheckOutEmail(emailAddress);

                }else {
                    ToastUtils.showToast(ForgetVarActivity.this,"邮箱格式不对");
                }


                break;
            case R.id.btn_forget_back_to_loginregister :
                finish();
                break;


        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (timeCount !=null)
        {
            timeCount.cancel();
            timeCount =null;
        }
    }
}
