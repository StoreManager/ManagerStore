package com.cottee.managerstore.activity1;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;

import com.cottee.managerstore.R;
import com.cottee.managerstore.handle.LoginRegisterInformationHandle;
import com.cottee.managerstore.manage.UserManage;
import com.cottee.managerstore.properties.Properties;

/**
 * Created by Administrator on 2017/12/3.
 */

public class SplashScreenActivity extends Activity {

    private LoginRegisterInformationHandle handle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);

        handle = new LoginRegisterInformationHandle(SplashScreenActivity.this,"");


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (UserManage.getInstance().hasUserInfo(SplashScreenActivity.this))//自动登录判断，SharePrefences中有数据，则跳转到主页，没数据则跳转到登录页
                {
                    handle.sendEmptyMessageDelayed(Properties.TO_HOME, 2000);

                } else {
                    handle.sendEmptyMessageAtTime(Properties.TO_LOGIN, 2000);

                }

                overridePendingTransition(R.anim.right_in, R.anim.left_out);

            }
        },1000*2);


    }
}
