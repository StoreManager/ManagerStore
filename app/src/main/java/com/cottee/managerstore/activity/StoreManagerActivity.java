package com.cottee.managerstore.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.cottee.managerstore.R;

public class StoreManagerActivity extends Activity {

    private TextView tv_storename_manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_storemanager );

        Intent intent = getIntent();
        String storename = intent.getStringExtra( "storename" );


        tv_storename_manager = (TextView) findViewById( R.id.tv_storename_manager );
        tv_storename_manager.setText( storename+"管理" );
    }
}
