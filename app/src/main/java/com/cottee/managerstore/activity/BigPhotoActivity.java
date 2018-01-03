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
import com.cottee.managerstore.utils.CommonUtils;
import com.cottee.managerstore.utils.FileUtil;
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
    private Button btn_back_detial;
    private Button btn_changePhoto;
    private ImageView iv_bigPicture;
    public Context mContext;
    private LinearLayout ll_btn;
    private Button btn_openGallery;
    private Button btn_openCamera;
    private LinearLayout ll_picture;
    private Button btn_cancel;

    public static final int ACTIVITY_ALBUM_REQUESTCODE = 2000;

    public static final int ACTIVITY_CAMERA_REQUESTCODE = 2001;

    public static final int ACTIVITY_MODIFY_PHOTO_REQUESTCODE = 2002;

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
                Intent i = new Intent(Intent.ACTION_PICK, null);// 调用android的图库
                i.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                startActivityForResult(i, ACTIVITY_ALBUM_REQUESTCODE);

                break;
            case R.id.btn_openCamera:
                if (CommonUtils.isExistCamera(BigPhotoActivity.this)) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);// 调用android自带的照相机
                    Uri imageUri = Uri.fromFile( FileUtil.getHeadPhotoFileRaw());
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                    intent.putExtra(MediaStore.Images.Media.ORIENTATION, 0);
                    startActivityForResult(intent, ACTIVITY_CAMERA_REQUESTCODE);
                } else {
                    Toast.makeText(BigPhotoActivity.this,
                            getResources().getString(R.string.user_no_camera),
                            Toast.LENGTH_SHORT).show();
                }

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

    //返回结果的回调函数
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case ACTIVITY_ALBUM_REQUESTCODE:
                if (resultCode == Activity.RESULT_OK) {
                    if(data.getData() == null){
                        ToastUtils.showToast( this,getString(R.string.pic_not_valid ));
                        return;
                    }
                    CommonUtils.cutPhoto(this, data.getData(), true);
                }
                break;
            case ACTIVITY_CAMERA_REQUESTCODE:
                if (resultCode == Activity.RESULT_OK) {
                    BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
                    bitmapOptions.inSampleSize = 2;
                    int degree = FileUtil.readPictureDegree(FileUtil.getHeadPhotoDir() + FileUtil.HEADPHOTO_NAME_RAW);
                    Bitmap cameraBitmap = BitmapFactory.decodeFile(FileUtil.getHeadPhotoDir() + FileUtil.HEADPHOTO_NAME_RAW, bitmapOptions);
                    cameraBitmap = FileUtil.rotaingImageView(degree, cameraBitmap);
                    FileUtil.saveCutBitmapForCache(this,cameraBitmap);
                    CommonUtils.cutPhoto(this, Uri.fromFile(FileUtil.getHeadPhotoFileRaw()), true);
                }
                break;
            case ACTIVITY_MODIFY_PHOTO_REQUESTCODE:
                String coverPath = FileUtil.getHeadPhotoDir()  + FileUtil.HEADPHOTO_NAME_TEMP;
                Bitmap bitmap = BitmapFactory.decodeFile(coverPath);
                iv_bigPicture.setImageBitmap(bitmap);
                //接下来是完成上传功能
               /* HttpUtil.uploadCover(this, UrlContainer.UP_LIVE_COVER + "?uid="
                        + LoginUtils.getInstance(this), coverPath, this);*/
                //成功之后删除临时图片
                FileUtil.deleteTempAndRaw();

                break;

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
