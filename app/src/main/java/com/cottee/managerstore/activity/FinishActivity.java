package com.cottee.managerstore.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.cottee.managerstore.R;

/**
 * Created by user on 2017/11/15.
 */

public class FinishActivity extends Activity {
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_finish );
    }
    public void backLogin(View view){
        startActivity( new Intent( this,ManagerActivity.class ) );
        finish();
    }
}
