package com.cottee.managerstore.activity1.base;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.cottee.managerstore.utils.WeiboDialogUtils;

/**
 * Created by Administrator on 2018/4/7.
 */

public class BaseActivity extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public Dialog startDialog(String name){
        Dialog dialog = WeiboDialogUtils.createLoadingDialog(this, name);
        return dialog;
    }

    public void stopDialog(Dialog dialog){
        if(dialog!=null){
            WeiboDialogUtils.closeDialog(dialog);
        }

    }
}
