package com.cottee.managerstore.activity1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.cottee.managerstore.R;
import com.cottee.managerstore.activity.RegisterStoreActivity;
import com.cottee.managerstore.manage.UserManage;


/**
 * Created by Administrator on 2017/11/19.
 */

public class RegisterFinishActivity extends Activity {

    private Button btbacklogin;
    private String emailAddress;
    private String emailPwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_register_finish);
        btbacklogin = (Button) findViewById(R.id.bt_backLogin);

        Intent intent = getIntent();
        emailAddress = intent.getStringExtra("email");
        emailPwd = intent.getStringExtra("pwd");


        btbacklogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                UserManage manage = new UserManage();
                manage.saveUserLogin(RegisterFinishActivity.this,emailAddress,emailPwd,true);
                manage.saveUserInfo(RegisterFinishActivity.this,emailAddress,emailPwd);


                Intent intent = new Intent(RegisterFinishActivity.this,
                        RegisterStoreActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.left_in, R.anim.right_out);
                finish();
            }
        });
    }
}
