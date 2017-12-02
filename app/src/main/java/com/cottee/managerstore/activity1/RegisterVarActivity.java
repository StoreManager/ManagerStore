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
 * Created by Administrator on 2017/11/19.
 */

public class RegisterVarActivity extends Activity implements View.OnClickListener {

    private Button btnemailnext;
    private Button btngetauthenticationcode;
    private EditText etinputemailaddress;
    private String emailAddress;
    private EditText etinputauthenticationcode;
    private String authenticationcode;
    private TimeCount timeCount;
    private Button btnbacktologinregister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_register_var);
        btnemailnext = (Button) findViewById(R.id.btn_email_next);
        btngetauthenticationcode = (Button) findViewById(R.id.btn_get_authentication_code);
        etinputemailaddress = (EditText) findViewById(R.id.et_input_email_address);
        etinputauthenticationcode = (EditText) findViewById(R.id.et_input_authentication_code);
        btnbacktologinregister = (Button) findViewById(R.id.btn_back_to_loginregister);



        btnemailnext.setOnClickListener(this);
        btngetauthenticationcode.setOnClickListener(this);
        btnbacktologinregister.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
        case R.id.btn_email_next:
            emailAddress = etinputemailaddress.getText().toString().trim();
            authenticationcode = etinputauthenticationcode.getText().toString().trim();

            if(!emailAddress.isEmpty()){
                if(new Canonical().isEmail(emailAddress)){

                    if(!authenticationcode.isEmpty()){

                        LoginRegisterInformationManage emailvarmanage = new LoginRegisterInformationManage(RegisterVarActivity.this, new
                                LoginRegisterInformationHandle(RegisterVarActivity.this,emailAddress));

                        emailvarmanage.checkoutEmailVer(emailAddress, authenticationcode);
                    }else{
                        ToastUtils.showToast(RegisterVarActivity.this,"验证码不能为空");
                    }


                }else {
                    ToastUtils.showToast(RegisterVarActivity.this,"邮箱格式不正确");
                }


            }else {
                ToastUtils.showToast(RegisterVarActivity.this,"邮箱不能为空");
            }





            break;
        case R.id.btn_get_authentication_code:

            emailAddress = etinputemailaddress.getText().toString().trim();
            if(new Canonical().isEmail(emailAddress)){

                timeCount = new TimeCount(100000,1000,btngetauthenticationcode,RegisterVarActivity.this);
                timeCount.start();
                LoginRegisterInformationManage emailmanage = new LoginRegisterInformationManage(RegisterVarActivity.this, new
                        LoginRegisterInformationHandle(RegisterVarActivity.this,""));
                emailmanage.checkoutEmail(emailAddress);

            }else {
                ToastUtils.showToast(RegisterVarActivity.this,"邮箱格式不对");
            }


        break;
            case R.id.btn_back_to_loginregister :
                finish();
                overridePendingTransition(R.anim.left_in, R.anim.right_out);

            break;
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (timeCount!=null)
        {
            timeCount.cancel();
            timeCount=null;
        }
    }
}
