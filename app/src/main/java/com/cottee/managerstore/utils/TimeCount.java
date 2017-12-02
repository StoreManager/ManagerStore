package com.cottee.managerstore.utils;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.os.CountDownTimer;
import android.widget.Button;

import com.cottee.managerstore.R;


/**
 * Created by Administrator on 2017/11/22.
 */

public class TimeCount extends CountDownTimer {

    private  Button btngetauthenticationcode;
    private Context context;
    public TimeCount(long millisInFuture, long countDownInterval, Button btngetauthenticationcode, Context context) {
        super(millisInFuture, countDownInterval);
        this.btngetauthenticationcode = btngetauthenticationcode;
        this.context = context;

    }

    @Override
    public void onTick(long l) {
        btngetauthenticationcode.setTextSize(12);
        btngetauthenticationcode.setText(l / 1000 + "秒后重新发送");
        btngetauthenticationcode.setClickable(false);

    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onFinish() {
        btngetauthenticationcode.setText("重新获取验证码");
        btngetauthenticationcode.setBackgroundDrawable(context.getDrawable(R.drawable.btn_var_square));
        btngetauthenticationcode.setClickable(true);
    }
}