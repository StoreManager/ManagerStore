package com.cottee.managerstore.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.cottee.managerstore.R;
import com.cottee.managerstore.utils.ToastUtils;

/**
 * Created by user on 2017/12/20.
 */

public class BigPhotoActivity extends Activity implements View.OnClickListener{

    private Button btn_back_detial;
    private Button btn_changePhoto;
    private ImageView iv_bigPicture;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_bigphoto_layout );
        findView();
        Intent intent = getIntent();
        String photo_url = intent.getStringExtra("photo_url");
        if(photo_url!=null){
            iv_bigPicture.setBackground( null );
        }
        Glide.with( this ).load( photo_url ).into( iv_bigPicture );
    }

    private void findView() {
        iv_bigPicture = (ImageView) findViewById( R.id.iv_bigPicture );
        btn_back_detial = (Button) findViewById( R.id.btn_back_to_detial );
        btn_back_detial.setOnClickListener( this );
        btn_changePhoto = (Button) findViewById( R.id.btn_changePhoto );
        btn_changePhoto.setOnClickListener( this );
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_back_to_detial:
                finish();
                break;
            case R.id.btn_changePhoto:
                ToastUtils.showToast( this,"修改图片" );
                break;
            default:break;
        }
    }
}
