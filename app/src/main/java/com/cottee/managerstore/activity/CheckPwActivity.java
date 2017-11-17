package com.cottee.managerstore.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.cottee.managerstore.R;

/**
 * Created by user on 2017/11/15.
 */

public class CheckPwActivity extends Activity {
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_checkpw );
    }
    public void registers(View view){
        startActivity( new Intent( this,FinishActivity.class ) );
    }
}
