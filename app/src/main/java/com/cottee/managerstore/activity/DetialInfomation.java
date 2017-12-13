package com.cottee.managerstore.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.cottee.managerstore.R;

/**
 * Created by user on 2017/12/12.
 */

public class DetialInfomation extends Activity {

    private TextView tv_storeName;
    private Button btn_edit;
    private Button btn_save;
    private TextView tv_time;
    private EditText et_time;
    private TextView tv_sign;
    private EditText et_sign;
    private TextView tv_money;
    private EditText et_money;
    private TextView tv_phone;
    private EditText et_phone;
    private TextView tv_style;
    private TextView tv_address;
    private ImageView iv_photo1;
    private ImageView iv_photo2;
    private ImageView iv_photo3;
    private String sign;
    private String time;
    private String money;
    private String phone;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_detialinfomation );

        findView();
    }

    private void findView() {
        tv_storeName = (TextView) findViewById( R.id.tv_storeName );

        btn_edit = (Button) findViewById( R.id.btn_edit );
        btn_save = (Button) findViewById( R.id.btn_save );

        tv_time = (TextView) findViewById( R.id.tv_time );
        et_time = (EditText) findViewById( R.id.et_time );

        tv_sign = (TextView) findViewById( R.id.tv_sign );
        et_sign = (EditText) findViewById( R.id.et_sign );

        tv_money = (TextView) findViewById( R.id.tv_money );
        et_money = (EditText) findViewById( R.id.et_money );

        tv_phone = (TextView) findViewById( R.id.tv_phone );
        et_phone = (EditText) findViewById( R.id.et_phone );

        tv_style = (TextView) findViewById( R.id.tv_style );
        tv_address = (TextView) findViewById( R.id.tv_address );

        iv_photo1 = (ImageView) findViewById( R.id.iv_photo1 );
        iv_photo2 = (ImageView) findViewById( R.id.iv_photo2 );
        iv_photo3 = (ImageView) findViewById( R.id.iv_photo3 );

        sign = tv_sign.getText().toString().trim();
        time = tv_time.getText().toString().trim();
        money = tv_money.getText().toString().trim();
        phone = tv_phone.getText().toString().trim();
    }

    public void edit(View view){
        tv_sign.setVisibility( View.GONE );
        et_sign.setVisibility( View.VISIBLE );

        tv_time.setVisibility( View.GONE );
        et_time.setVisibility( View.VISIBLE );

        tv_money.setVisibility( View.GONE );
        et_money.setVisibility( View.VISIBLE );

        tv_phone.setVisibility( View.GONE );
        et_phone.setVisibility( View.VISIBLE);

        btn_edit.setVisibility( View.GONE );
        btn_save.setVisibility( View.VISIBLE );

        et_sign.setText( sign );
        et_time.setText( time );
        et_money.setText( money );
        et_phone.setText( phone );

    }

    public void save(View view){
        sign = et_sign.getText().toString().trim();
        time = et_time.getText().toString().trim();
        money = et_money.getText().toString().trim();
        phone = et_phone.getText().toString().trim();

        et_sign.setVisibility( View.GONE );
        tv_sign.setVisibility( View.VISIBLE );

        et_time.setVisibility( View.GONE );
        tv_time.setVisibility( View.VISIBLE );

        et_money.setVisibility( View.GONE );
        tv_money.setVisibility( View.VISIBLE );

        et_phone.setVisibility( View.GONE );
        tv_phone.setVisibility( View.VISIBLE);

        btn_edit.setVisibility( View.VISIBLE);
        btn_save.setVisibility( View.GONE );

        tv_sign.setText( sign );
        tv_time.setText( time );
        tv_money.setText( money );
        tv_phone.setText( phone );
    }

}
