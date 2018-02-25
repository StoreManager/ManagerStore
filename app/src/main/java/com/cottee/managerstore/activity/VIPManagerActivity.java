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
import android.widget.TextView;
import android.widget.Toast;

import com.cottee.managerstore.R;
import com.cottee.managerstore.utils.ToastUtils;

/**
 * Created by user on 2018/1/16.
 */

public class VIPManagerActivity extends Activity implements View.OnClickListener {

    private Button btn_back_to_manager;
    private Button btn_toVipStandard;
    private Button btn_searchVIP;
    private TextView tv_empty;
    private ListView lv_vipStandard;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_vipmanager_layout );

        btn_back_to_manager = (Button) findViewById( R.id.btn_back_to_manager );
        btn_back_to_manager.setOnClickListener( this );
        btn_toVipStandard = (Button) findViewById( R.id.btn_addVipStandard );
        btn_toVipStandard.setOnClickListener( this );
        btn_searchVIP = (Button) findViewById( R.id.btn_searchVIP );
        btn_searchVIP.setOnClickListener( this );
        tv_empty = (TextView) findViewById( R.id.tv_empty );
        lv_vipStandard = (ListView) findViewById( R.id.lv_vipStandard );
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_back_to_manager:
                finish();
                break;
            case R.id.btn_addVipStandard:// 添加会员标准
                startActivity( new Intent( this,VIPSearchActivity.class ) );
                break;
            case R.id.btn_searchVIP://搜索会员
                ToastUtils.showToast( this,"search" );
                break;
            default:
                break;
        }
    }
}
