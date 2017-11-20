package com.cottee.managerstore.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.cottee.managerstore.R;
import com.cottee.managerstore.view.FindPassWordDialog;

/**
 * Created by user on 2017/11/15.
 */

public class ManagerActivity extends Activity {

    private LinearLayout linear_manager;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_manager );
        linear_manager = findViewById(R.id.linear_manager);
    }
    public void forgetPassWord(View view)
    {
        final FindPassWordDialog findPassWordDialog = new FindPassWordDialog(this);
        findPassWordDialog.show();
        linear_manager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                findPassWordDialog.dismiss();
            }
        });
    }
    public void back(View view){
        finish();
    }
    public void register(View view){
        startActivity( new Intent( this,RegisterActivity.class ) );
    }
    public void login(View view){
        startActivity( new Intent( this,RegisterStoreActivity.class ) );
    }
}
