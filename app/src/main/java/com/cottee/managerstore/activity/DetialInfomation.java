package com.cottee.managerstore.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cottee.managerstore.R;
import com.cottee.managerstore.bean.StoreInfo;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by user on 2017/12/12.
 */

public class DetialInfomation extends Activity implements View.OnClickListener {

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
    private StoreInfo storeInfo;
    private Bitmap bitmap;
    private ImageView iv_surface;
    private String surface;
    private String one;
    private String two;
    private String three;
    public Context mContext=DetialInfomation.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_detialinfomation );

        Intent intent = getIntent();
        storeInfo = (StoreInfo) intent.getSerializableExtra( "storeInfo" );
        findView();
        surface = storeInfo.getSurface();
        one = storeInfo.getThumbnail_one();
        two = storeInfo.getThumbnail_two();
        three = storeInfo.getThumbnail_three();
        Glide.with( this ).load( surface ).into( iv_surface );
        Glide.with( this ).load( one ).into( iv_photo1 );
        Glide.with( this ).load( two ).into( iv_photo2 );
        Glide.with( this ).load( three ).into( iv_photo3 );
        tv_storeName.setText( storeInfo.getName() );
        tv_time.setText( storeInfo.getBusiness_hours() );
        tv_sign.setText( storeInfo.getIntroduce() );
        tv_phone.setText( storeInfo.getPhone() );
        tv_style.setText( "(" + storeInfo.getClassify() + ")" );
        tv_address.setText( storeInfo.getAddress() );
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

        iv_surface = (ImageView) findViewById( R.id.iv_surface );
        iv_surface.setOnClickListener( this );
        iv_photo1 = (ImageView) findViewById( R.id.iv_photo1 );
        iv_photo1.setOnClickListener( this );
        iv_photo2 = (ImageView) findViewById( R.id.iv_photo2 );
        iv_photo2.setOnClickListener( this );
        iv_photo3 = (ImageView) findViewById( R.id.iv_photo3 );
        iv_photo3.setOnClickListener( this );

    }

    public void edit(View view) {

        sign = tv_sign.getText().toString().trim();
        time = tv_time.getText().toString().trim();
        money = tv_money.getText().toString().trim();
        phone = tv_phone.getText().toString().trim();

        tv_sign.setVisibility( View.GONE );
        et_sign.setVisibility( View.VISIBLE );

        tv_time.setVisibility( View.GONE );
        et_time.setVisibility( View.VISIBLE );

        tv_money.setVisibility( View.GONE );
        et_money.setVisibility( View.VISIBLE );

        tv_phone.setVisibility( View.GONE );
        et_phone.setVisibility( View.VISIBLE );

        btn_edit.setVisibility( View.GONE );
        btn_save.setVisibility( View.VISIBLE );

        et_sign.setText( sign );
        et_time.setText( time );
        et_money.setText( money );
        et_phone.setText( phone );

    }

    public void save(View view) {
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
        tv_phone.setVisibility( View.VISIBLE );

        btn_edit.setVisibility( View.VISIBLE );
        btn_save.setVisibility( View.GONE );

        tv_sign.setText( sign );
        tv_time.setText( time );
        tv_money.setText( money );
        tv_phone.setText( phone );
    }

    public byte[] getImage(String path) throws Exception {
        URL url = new URL( path );
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setConnectTimeout( 5 * 1000 );
        conn.setRequestMethod( "GET" );
        InputStream inStream = conn.getInputStream();
        if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
            return readStream( inStream );
        }
        return null;
    }

    public static byte[] readStream(InputStream inStream) throws Exception {
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        while ((len = inStream.read( buffer )) != -1) {
            outStream.write( buffer, 0, len );
        }
        outStream.close();
        inStream.close();
        return outStream.toByteArray();
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent( this, BigPhotoActivity.class );
        switch (view.getId()) {
            case R.id.iv_surface:
                intent.putExtra( "photo_url", surface );
                startActivity( intent );
                break;
            case R.id.iv_photo1:
                intent.putExtra( "photo_url", one );
                startActivity( intent );
                break;
            case R.id.iv_photo2:
                intent.putExtra( "photo_url", two );
                startActivity( intent );
                break;
            case R.id.iv_photo3:
                intent.putExtra( "photo_url", three );
                startActivity( intent );
                break;
            default:
                break;
        }
    }
}
