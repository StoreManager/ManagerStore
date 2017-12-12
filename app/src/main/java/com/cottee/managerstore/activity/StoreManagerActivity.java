package com.cottee.managerstore.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.cottee.managerstore.R;

public class StoreManagerActivity extends Activity implements View.OnClickListener {

    private TextView tv_storename_manager;
    private Button btn_storeManager;
    public Context mContext = StoreManagerActivity.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_storemanager );

        Intent intent = getIntent();
        String storename = intent.getStringExtra( "storename" );


        findView();
        tv_storename_manager.setText( storename + "管理" );
    }

    private void findView() {
        tv_storename_manager = (TextView) findViewById( R.id.tv_storename_manager );
        btn_storeManager = (Button) findViewById( R.id.btn_storeManager );
        btn_storeManager.setOnClickListener( this );
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_storeManager:
                Intent intent = new Intent( this, DetialInfomation.class );
                startActivity( intent );
                finish();
                break;
            default:
                break;
        }
    }
}
