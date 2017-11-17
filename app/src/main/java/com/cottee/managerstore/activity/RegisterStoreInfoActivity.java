package com.cottee.managerstore.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.cottee.managerstore.R;

public class RegisterStoreInfoActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_store_info);
    }
    public void chooseStoreStyle(View view)
    {
        startActivity( new Intent( this,StoreStyleActivity.class ) );
    }
    public void chooseStoreAddress(View view)
    {
        Intent intent = new Intent(this, StoreAddressActivity.class);
        startActivity(intent);
    }
    public void backAddRegisterStore(View view)
    {
//        Intent intent = new Intent(this, RegisterStoreActivity.class);
//        startActivity(intent);
        finish();
    }
    public void businessLicense(View v)
    {
        Toast.makeText(this, "调出相机拍照！", Toast.LENGTH_SHORT).show();
    }
    public void finishRegisterStore(View v )
    {
        Toast.makeText(this, "完成信息填写！", Toast.LENGTH_SHORT).show();
    }


}
