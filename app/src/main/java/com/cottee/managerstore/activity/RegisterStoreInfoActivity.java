package com.cottee.managerstore.activity;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cottee.managerstore.R;
import com.cottee.managerstore.manage.StoreInfoManager;
import com.cottee.managerstore.tabfragment.TestFragment;
import com.cottee.managerstore.view.MyImageButton;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Locale;

public class RegisterStoreInfoActivity extends Activity {

    private MyImageButton imgbtn_businessLicense;
    private TextView tv_storeStyle;
    private int position;
    private TestFragment testFragment;
    private static final int REQUEST_ADDRESS = 2;
    private static final int REQUEST_CAMERA = 3;
    private TextView tv_storeAddress;
    private EditText et_name;
    private EditText et_phoneNum;
    private Drawable state=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_register_store_info);
        initView();
        initEvent();

    }

    private void initView() {
        imgbtn_businessLicense = findViewById(R.id.imgbtn_businessLicense);
        tv_storeStyle = findViewById(R.id.tv_storeStyle);
        tv_storeAddress = (TextView) findViewById(R.id.tv_storeAddress);
        et_name = (EditText) findViewById(R.id.et_name);
        et_phoneNum = (EditText) findViewById(R.id.et_phoneNum);
    }

    private void initEvent() {
        imgbtn_businessLicense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, REQUEST_CAMERA);
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent
            data) {

        switch (requestCode) {
            case 1:
                if (resultCode == RESULT_OK) {
                    String storeStyle = data.getStringExtra("storeStyle");
                    System.out.println("ffff");
                    tv_storeStyle.setText(storeStyle);
                }
                break;
            case REQUEST_ADDRESS:
                if (resultCode == RESULT_OK) {
                    String address = data.getStringExtra("address");
                    tv_storeAddress.setText(address);
                }
                break;
            case REQUEST_CAMERA:
                if (resultCode == Activity.RESULT_OK) {
                    String sdStatus = Environment.getExternalStorageState();
                    if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) { //
                        // 检测sd是否可用
                        Log.i("TestFile",
                                "SD card is not avaiable/writeable right now.");
                        return;
                    }
                    String name = new DateFormat().format("yyyyMMdd_hhmmss",
                            Calendar.getInstance(Locale.CHINA)) + ".jpg";
//          Toast.makeText(this, name, Toast.LENGTH_LONG).show();
                    Bundle bundle = data.getExtras();
                    Bitmap bitmap = (Bitmap) bundle.get("data");//
                    // 获取相机返回的数据，并转换为Bitmap图片格式
                    FileOutputStream b = null;
                    File file = new File("/sdcard/myImage/");
                    file.mkdirs();// 创建文件夹
                    String fileName = "/sdcard/myImage/" + name;
                    try {
                        b = new FileOutputStream(fileName);
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, b);
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
                    imgbtn_businessLicense.setImageBitmap(bitmap);
                }

                break;
        }

    }

    public void chooseStoreStyle(View view) {
        Intent intent = new Intent(this, StoreStyleActivity.class);
        startActivityForResult(intent, 1);
        overridePendingTransition(R.anim.right_in, R.anim.left_out);
    }

    public void chooseStoreAddress(View view) {
        Intent intent = new Intent(this, StoreAddressActivity.class);
        startActivityForResult(intent, REQUEST_ADDRESS);
        overridePendingTransition(R.anim.right_in, R.anim.left_out);
    }

    public void backAddRegisterStore(View view) {
        Intent intent = new Intent(this, RegisterStoreActivity.class);
        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.right_in, R.anim.left_out);

    }

    public void finishRegisterStore(View v) {
        String shopName = et_name.getText().toString().trim();
        String shopPhoneNum = et_phoneNum.getText().toString().trim();
        String shopStyle = tv_storeStyle.getText().toString().trim();
        String shopAddress = tv_storeAddress.getText().toString().trim();
        state = imgbtn_businessLicense
                .getDrawable();
        StoreInfoManager storeInfoManager = new StoreInfoManager(RegisterStoreInfoActivity.this);
        storeInfoManager.infoIsEmpty(shopName, shopStyle,
                shopAddress, shopPhoneNum, state);

    }

}
