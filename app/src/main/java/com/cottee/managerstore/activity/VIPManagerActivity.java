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
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.cottee.managerstore.R;
import com.cottee.managerstore.utils.ToastUtils;
import com.cottee.managerstore.view.VIPStandardDialog;

/**
 * Created by user on 2018/1/16.
 */

public class VIPManagerActivity extends Activity implements View.OnClickListener, VIPStandardDialog
        .OnCenterItemClickListener  {

    private Button btn_back_to_manager;
    private Button btn_toVipStandard;
    private Button btn_searchVIP;
    private TextView tv_empty;
    private ListView lv_vipStandard;
    private VIPStandardDialog centerDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_vipmanager_layout );

        centerDialog = new VIPStandardDialog( this, R.layout.dialog_layout,
                new int[]{R.id.dialog_cancel, R.id.dialog_sure} );
        centerDialog.setOnCenterItemClickListener( this );

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
                centerDialog.show();
                break;
            case R.id.btn_searchVIP://搜索会员
                ToastUtils.showToast( this,"search" );
                break;
            default:
                break;
        }
    }

    @Override
    public void OnCenterItemClick(VIPStandardDialog dialog, View view) {
        switch (view.getId()) {
            case R.id.dialog_sure:
                String[] string = getString( centerDialog );
                if (string == null) {
                    return;
                } else {
                    Toast.makeText( this, "确定按钮", Toast
                            .LENGTH_SHORT )
                            .show();
                    dialog.dismiss();
                }
                break;
            case R.id.dialog_cancel:
                dialog.dismiss();
                break;
            default:
                break;
        }
    }

    @Override
    public String[] getString(VIPStandardDialog dialog) {
        EditText et_name = (EditText) dialog.findViewById( R.id.et_vipStandardName );
        EditText et_min = (EditText) dialog.findViewById( R.id.et_min );
        EditText et_max = (EditText) dialog.findViewById( R.id.et_max );
        String name = et_name.getText().toString().trim();
        String min = et_min.getText().toString().trim();
        String max = et_max.getText().toString().trim();
        if (name.isEmpty()) {
            Toast.makeText( this, "输入名字哦", Toast.LENGTH_SHORT ).show();
            return null;
        }else if(min.isEmpty()||max.isEmpty()){
            Toast.makeText( this, "输入积分区间哦", Toast.LENGTH_SHORT ).show();
            return null;
        }
        return new String[]{name, min, max};
    }
}
