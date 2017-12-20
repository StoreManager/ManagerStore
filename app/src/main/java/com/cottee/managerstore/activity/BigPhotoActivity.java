package com.cottee.managerstore.activity;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.cottee.managerstore.R;
import com.cottee.managerstore.utils.ToastUtils;

/**
 * Created by user on 2017/12/20.
 */

public class BigPhotoActivity extends Activity implements View.OnClickListener{
    public static final int CHOOSE_PHOTO = 1;
    private Button btn_back_detial;
    private Button btn_changePhoto;
    private ImageView iv_bigPicture;
    public Context mContext;
    private Uri uri;
    private String imagePath;

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
                //判断是否授权
                if (ContextCompat.checkSelfPermission( BigPhotoActivity.this, Manifest.permission
                        .WRITE_EXTERNAL_STORAGE ) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions( BigPhotoActivity.this, new String[]{Manifest
                            .permission.WRITE_EXTERNAL_STORAGE}, 1 );
                } else {
                    openAlbum();
                }
                break;
            default:break;
        }
    }

    //打开相册
    private void openAlbum() {
        Intent intent = new Intent( "android.intent.action.GET_CONTENT" );
        intent.setType( "image/*" );
        startActivityForResult( intent, CHOOSE_PHOTO );
    }

    //请求授权
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openAlbum();
            } else {
                Toast.makeText( BigPhotoActivity.this, "你没有获取的权限", Toast.LENGTH_LONG ).show();
            }
        }

    }

    //返回结果的回调函数
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CHOOSE_PHOTO) {
            if (resultCode == RESULT_OK) {
                if (Build.VERSION.SDK_INT >= 19) {//判断版本是否在4.4以上，执行不同的处理方法
                    handlerImageOnKitKat( data );
                } else {
                    handlerImageBeforeKitKat( data );
                }
            }
        }
    }

    //4.4以下版本的处理方法
    private void handlerImageBeforeKitKat(Intent data) {
        uri = data.getData();
        imagePath = getImagePath( uri, null );
        displayImage( imagePath );
    }

    //4.4以上版本的处理方法
    @TargetApi(19)
    private void handlerImageOnKitKat(Intent data) {
        imagePath = null;
        uri = data.getData();
        if (DocumentsContract.isDocumentUri( this, uri )) {
            String docId = DocumentsContract.getDocumentId( uri );
            if ("com.android.providers.media.documents".equals( uri.getAuthority() )) {
                String id = docId.split( ":" )[1];
                String selection = MediaStore.Images.Media._ID + "=" + id;
                imagePath = getImagePath( MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection );
            } else if ("com.android.providers.downloads.documents".equals( uri.getAuthority() )) {
                Uri contentUri = ContentUris.withAppendedId( Uri.parse(
                        "content://downloads/public_downloads" ), Long.valueOf( docId ) );
                imagePath = getImagePath( contentUri, null );
            }
        } else if ("content".equalsIgnoreCase( uri.getScheme() )) {
            imagePath = getImagePath( uri, null );
        } else if ("file".equalsIgnoreCase( uri.getScheme() )) {
            imagePath = uri.getPath();
        }
        displayImage( imagePath );
    }

    //获得图片路径
    private String getImagePath(Uri uri, String selection) {
        String path = null;
        Cursor cursor = getContentResolver().query( uri, null, selection, null, null );
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                path = cursor.getString( cursor.getColumnIndex( MediaStore.Images.Media.DATA ) );
            }
            cursor.close();
        }
        return path;
    }

    //显示图片
    private void displayImage(String imagePath) {
        if (imagePath != null) {
            Bitmap bitmap = BitmapFactory.decodeFile( imagePath );
            iv_bigPicture.setImageBitmap( bitmap );
        } else {
            Toast.makeText( this, "获取图片失败", Toast.LENGTH_LONG ).show();
        }

    }
}
