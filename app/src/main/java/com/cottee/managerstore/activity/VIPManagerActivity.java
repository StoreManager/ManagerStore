package com.cottee.managerstore.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.cottee.managerstore.R;

/**
 * Created by user on 2018/1/16.
 */

public class VIPManagerActivity extends Activity implements View.OnClickListener {

    private Button btn_back_to_manager;
    private Button btn_toVipStandard;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_vipmanager_layout );

        btn_back_to_manager = (Button) findViewById( R.id.btn_back_to_manager );
        btn_back_to_manager.setOnClickListener( this );
        btn_toVipStandard = (Button) findViewById( R.id.btn_toVipStandard );
        btn_toVipStandard.setOnClickListener( this );
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_back_to_manager:
                finish();
                break;
            case R.id.btn_toVipStandard:
                startActivity( new Intent( this,VIPSearchActivity.class ) );
                break;
            default:
                break;
        }
    }
}
