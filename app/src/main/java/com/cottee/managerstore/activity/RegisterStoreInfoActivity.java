package com.cottee.managerstore.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.os.Looper;
import android.provider.MediaStore;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cottee.managerstore.R;
import com.cottee.managerstore.handle.LoginRegisterInformationHandle;
import com.cottee.managerstore.manage.StoreInfoManager;
import com.cottee.managerstore.manage.SubmitStoreInfoManager;
import com.cottee.managerstore.properties.Properties;
import com.cottee.managerstore.tabfragment.TestFragment;
import com.cottee.managerstore.view.MyImageButton;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.cottee.managerstore.properties.Properties.REQUEST_ADDRESS;
import static com.cottee.managerstore.properties.Properties.REQUEST_CAMERA;

public class RegisterStoreInfoActivity extends Activity {

    private MyImageButton imgbtn_businessLicense;
    private TextView tv_storeStyle;
    private int position;
    private TestFragment testFragment;
    private TextView tv_storeAddress;
    private EditText et_name;
    private EditText et_phoneNum;
    private Drawable state = null;
    private LinearLayout linear_addStoreStyle;
    private LinearLayout linear_addStoreAddress;
    private Context context;
    private String fileName=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        requestWindowFeature( Window.FEATURE_NO_TITLE );
        setContentView( R.layout.activity_register_store_info );
        initView();
        initEvent();

    }

    private void initView() {
        linear_addStoreStyle = findViewById( R.id.linear_addStoreStyle );
        linear_addStoreAddress = findViewById( R.id.linear_addStoreAddress );
        imgbtn_businessLicense = findViewById( R.id.imgbtn_businessLicense );
        tv_storeStyle = findViewById( R.id.tv_storeStyle );
        tv_storeAddress = findViewById( R.id.tv_storeAddress );
        et_name = findViewById( R.id.et_name );
        et_phoneNum = findViewById( R.id.et_phoneNum );
    }

    private void initEvent() {
        linear_addStoreStyle.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent( RegisterStoreInfoActivity.this, StoreStyleActivity.class );
                startActivityForResult( intent, 1 );
                overridePendingTransition( R.anim.right_in, R.anim.left_out );
            }
        } );
        linear_addStoreAddress.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent( RegisterStoreInfoActivity.this, StoreAddressActivity.class );
                startActivityForResult( intent, REQUEST_ADDRESS );
                overridePendingTransition( R.anim.right_in, R.anim.left_out );
            }
        } );
        imgbtn_businessLicense.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent( MediaStore.ACTION_IMAGE_CAPTURE );
                startActivityForResult( intent, REQUEST_CAMERA );
            }
        } );
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent
            data) {

        switch (requestCode) {
            case 1:
                if (resultCode == RESULT_OK) {
                    String storeStyle = data.getStringExtra( "storeStyle" );
                    tv_storeStyle.setText( storeStyle );
                }
                break;
            case REQUEST_ADDRESS:
                if (resultCode == RESULT_OK) {
                    String address = data.getStringExtra( "address" );
                    tv_storeAddress.setText( address );
                }
                break;
            case REQUEST_CAMERA:
                if (resultCode == Activity.RESULT_OK) {
                    String sdStatus = Environment.getExternalStorageState();
                    if (!sdStatus.equals( Environment.MEDIA_MOUNTED )) { //
                        // 检测sd是否可用
                        Log.i( "TestFile",
                                "SD card is not avaiable/writeable right now." );
                        return;
                    }
                    String name = new DateFormat().format( "yyyyMMdd_hhmmss",
                            Calendar.getInstance( Locale.CHINA ) ) + ".jpg";
                    Bundle bundle = data.getExtras();
                    Bitmap bitmap = (Bitmap) bundle.get( "data" );//
                    // 获取相机返回的数据，并转换为Bitmap图片格式
                    FileOutputStream b = null;
                    File file = new File( "/sdcard/myImage/" );
                    file.mkdirs();// 创建文件夹
                    fileName = "/sdcard/myImage/" + name;
                    try {
                        b = new FileOutputStream( fileName );
                        bitmap.compress( Bitmap.CompressFormat.JPEG, 100, b );
                        // 把数据写入文件
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } finally {
                        try {
                            b.flush();
                            b.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    imgbtn_businessLicense.setImageBitmap( bitmap );
                }

                break;
        }

    }

    public void backAddRegisterStore(View view) {
        Intent intent = new Intent( this, RegisterStoreActivity.class );
        startActivity( intent );
        finish();
        overridePendingTransition( R.anim.left_in, R.anim.right_out );

    }

    public void finishRegisterStore(View v) {
        String shopName = et_name.getText().toString().trim();
        String shopPhoneNum = et_phoneNum.getText().toString().trim();
        String shopStyle = tv_storeStyle.getText().toString().trim();
        String shopAddress = tv_storeAddress.getText().toString().trim();
        state = imgbtn_businessLicense
                .getDrawable();
        StoreInfoManager storeInfoManager = new StoreInfoManager( RegisterStoreInfoActivity.this );
        boolean empty = storeInfoManager.infoIsEmpty( shopName, shopStyle,
                shopAddress, shopPhoneNum, state );
        if (empty) {
            SubmitStoreInfoManager submitStoreInfo = new SubmitStoreInfoManager(
                    RegisterStoreInfoActivity
                            .this, new LoginRegisterInformationHandle(
                    RegisterStoreInfoActivity.this, ""
            ) );
            submitStoreInfo.submitInfo( shopName, shopStyle, shopAddress, shopPhoneNum );
            if(fileName!=null){
                submitStoreInfo.submitInfo( fileName );
            }
        }


    }

}
