package com.cottee.managerstore.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.cottee.managerstore.R;

/**
 * Created by user on 2017/11/15.
 */

public class ManagerActivity extends Activity {
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_manager );
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
