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
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.BounceInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.cottee.managerstore.R;
import com.cottee.managerstore.utils.ToastUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.Locale;

import static com.cottee.managerstore.properties.Properties.REQUEST_CAMERA;

/**
 * Created by user on 2017/12/20.
 */

public class BigPhotoActivity extends Activity implements View.OnClickListener {
    public static final int CHOOSE_PHOTO = 1;
    private Button btn_back_detial;
    private Button btn_changePhoto;
    private ImageView iv_bigPicture;
    public Context mContext;
    private Uri uri;
    private String imagePath;
    private LinearLayout ll_btn;
    private Button btn_openGallery;
    private Button btn_openCamera;
    private LinearLayout ll_picture;
    private Button btn_cancel;
    private String fileName;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_bigphoto_layout );
        findView();
        Intent intent = getIntent();
        String photo_url = intent.getStringExtra( "photo_url" );
        if (photo_url != null) {
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

        ll_btn = (LinearLayout) findViewById( R.id.ll_btn );
        ll_picture = (LinearLayout) findViewById( R.id.ll_picture );
        ll_picture.setOnClickListener( this );
        btn_openGallery = (Button) findViewById( R.id.btn_openGallery );
        btn_openGallery.setOnClickListener( this );
        btn_openCamera = (Button) findViewById( R.id.btn_openCamera );
        btn_openCamera.setOnClickListener( this );
        btn_cancel = (Button) findViewById( R.id.btn_cancel );
        btn_cancel.setOnClickListener( this );
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_back_to_detial:
                finish();
                break;
            case R.id.btn_changePhoto:
                if (ll_btn.getVisibility() == View.GONE) {
                    startbtnAnim();
                }
                break;
            case R.id.btn_openGallery:
                //判断是否授权
                if (ContextCompat.checkSelfPermission( BigPhotoActivity.this, Manifest.permission
                        .WRITE_EXTERNAL_STORAGE ) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions( BigPhotoActivity.this, new String[]{Manifest
                            .permission.WRITE_EXTERNAL_STORAGE}, 1 );
                } else {
                    openAlbum();
                }
                break;
            case R.id.btn_openCamera:
                Intent intent = new Intent( MediaStore.ACTION_IMAGE_CAPTURE );
                startActivityForResult( intent, REQUEST_CAMERA );
                break;
            case R.id.btn_cancel:
                cancelbtnAnim();
                break;
            case R.id.ll_picture:
                if (ll_btn.getVisibility() == View.VISIBLE) {
                    cancelbtnAnim();
                }
                break;
            default:
                break;
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
        if (requestCode == CHOOSE_PHOTO) {
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
        if(requestCode==REQUEST_CAMERA){
            if(resultCode==RESULT_OK){
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
                iv_bigPicture.setImageBitmap( bitmap );
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

    private void startbtnAnim() {
        final Animation T_Anim = new TranslateAnimation( 0f, 0, 900f, 0f );
        T_Anim.setDuration( 120 );
        T_Anim.setInterpolator( new BounceInterpolator() );
        T_Anim.setFillAfter( true );
        ll_btn.setVisibility( View.VISIBLE );
        ll_btn.startAnimation( T_Anim );

    }

    private void cancelbtnAnim() {
        final Animation T_Anim = new TranslateAnimation( 0f, 0, -10f, 900f );
        T_Anim.setDuration( 120 );
        ll_btn.setVisibility( View.GONE );
        ll_btn.startAnimation( T_Anim );
    }
}
