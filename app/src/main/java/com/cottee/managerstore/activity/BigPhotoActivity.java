package com.cottee.managerstore.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.util.Base64;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.BounceInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.cottee.managerstore.R;
import com.cottee.managerstore.utils.BitmapUtils;
import com.cottee.managerstore.utils.LogUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

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

    public static final int CAMRA_SETRESULT_CODE = 0;// 相册返回码
    public static final int PHOTO_SETRESULT_CODE = 1;// 拍照返回码
    public static final int PHOTO_CORPRESULT_CODE = 2;// 裁剪返回码

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_frontcover_layout );

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
                Intent intent = new Intent( Intent.ACTION_PICK,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI );
                startActivityForResult( intent,
                        CAMRA_SETRESULT_CODE );
                break;
            case R.id.btn_openCamera:
                Intent intents = new Intent( MediaStore.ACTION_IMAGE_CAPTURE );
                intents.putExtra( MediaStore.EXTRA_OUTPUT,
                        Uri.fromFile( new File( getPhotoPath() ) ) );
                startActivityForResult( intents,
                        PHOTO_SETRESULT_CODE );
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

    // 拍照使用系统当前日期加以调整作为照片的名称
    private static String getPhotoFileName() {
        Date date = new Date( System.currentTimeMillis() );
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "'IMG'_yyyyMMdd_HHmmss" );
        return dateFormat.format( date ) + ".jpg";
    }

    // 拍照路径
    public String getPhotoPath() {
        File file = new File( Environment.getExternalStorageDirectory(), "/imgs" );
        if (!file.exists()) {
            file.mkdirs();
        }
        String path = file.getPath() + "photo.jpg";
        return path;
    }

    // file转换成BitMap
    public static Bitmap readBitmapAutoSize(String filePath) {
        // outWidth和outHeight是目标图片的最大宽度和高度，用作限制
        Bitmap bm = null;
        try {

            BitmapFactory.Options opt = new BitmapFactory.Options();
            opt.inJustDecodeBounds = true;
            // 设置只是解码图片的边距，此操作目的是度量图片的实际宽度和高度
            BitmapFactory.decodeFile( filePath, opt );
            opt.inDither = false;
            opt.inPreferredConfig = Bitmap.Config.RGB_565;

            // 设置加载图片的颜色数为16bit，默认是RGB_8888，表示24bit颜色和透明通道，但一般用不上
            // opt.inSampleSize = 1;
            opt.inSampleSize = computeSampleSize( opt, -1, 900 * 900 );
            opt.inJustDecodeBounds = false;
            bm = BitmapFactory.decodeFile( filePath, opt );
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bm;
    }

    public static int computeSampleSize(BitmapFactory.Options options,
                                        int minSideLength, int maxNumOfPixels) {
        int initialSize = computeInitialSampleSize( options, minSideLength,
                maxNumOfPixels );
        int roundedSize;
        if (initialSize <= 8) {
            roundedSize = 1;
            while (roundedSize < initialSize) {
                roundedSize <<= 1;
            }
        } else {
            roundedSize = (initialSize + 7) / 8 * 8;
        }
        return roundedSize;
    }

    private static int computeInitialSampleSize(BitmapFactory.Options options,
                                                int minSideLength, int maxNumOfPixels) {
        double w = options.outWidth;
        double h = options.outHeight;
        int lowerBound = (maxNumOfPixels == -1) ? 1 : (int) Math.ceil( Math
                .sqrt( w * h / maxNumOfPixels ) );
        int upperBound = (minSideLength == -1) ? 128 : (int) Math.min(
                Math.floor( w / minSideLength ), Math.floor( h / minSideLength ) );
        if (upperBound < lowerBound) {
            return lowerBound;
        }
        if ((maxNumOfPixels == -1) && (minSideLength == -1)) {
            return 1;
        } else if (minSideLength == -1) {
            return lowerBound;
        } else {
            return upperBound;
        }
    }

    // bitmap转换成字节流
    public static String bitmaptoString(Bitmap bitmap) {
        // 将Bitmap转换成字符串
        String result = "";
        ByteArrayOutputStream bStream = new ByteArrayOutputStream();
        bitmap.compress( Bitmap.CompressFormat.JPEG, 100, bStream );
        byte[] bytes = bStream.toByteArray();
        byte[] bb = Base64.encode( bytes, Base64.DEFAULT );
        try {
            result = new String( bb, "UTF-8" ).replace( "+", "%2B" );
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
        }
        return result;
    }

    // 得到相册路径
    public String getCameraPath(Intent data) {
        Uri originalUri = data.getData();
        String[] proj = {MediaStore.Images.Media.DATA};

        // 好像是android多媒体数据库的封装接口，具体的看Android文档     数据库

        Cursor cursor = this.managedQuery( originalUri, proj,
                null, null, null );

        // 获取游标

        int column_index = cursor
                .getColumnIndexOrThrow( MediaStore.Images.Media.DATA );

        // 将光标移至开头 ，这个很重要，不小心很容易引起越界

        cursor.moveToFirst();

        // 最后根据索引值获取图片路径

        String path = cursor.getString( column_index );
        return path;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult( requestCode, resultCode, data );

        // 相册返回
        if (CAMRA_SETRESULT_CODE == requestCode) {
            if (resultCode == RESULT_OK) {
                // 相册选中图片路径
                String cameraPath = getCameraPath( data );
                Bitmap bitmap = readBitmapAutoSize( cameraPath );
                iv_bigPicture.setImageBitmap( bitmap );
                String str =
                        bitmaptoString( bitmap );
                LogUtils.d( "相相册选中路径  = " + cameraPath );
                startClipActivity( cameraPath );
            }
        }
        // 相机返回
        else if (PHOTO_SETRESULT_CODE == requestCode) {
            if (resultCode == RESULT_OK) {
                String photoPath = getPhotoPath();
                Bitmap bitmap = readBitmapAutoSize( photoPath );
                String str = bitmaptoString( bitmap );
                iv_bigPicture.setImageBitmap( bitmap );
                LogUtils.d( "相机选中路径  = " + photoPath );
                startClipActivity( photoPath );

            }
        }
        // 裁剪返回
        else if (PHOTO_CORPRESULT_CODE == requestCode) {
            if (resultCode == RESULT_OK) {
                LogUtils.d( "裁剪返回  = " );
                String path = data.getStringExtra( "path" );
                BitmapUtils bitmapUtils = new BitmapUtils( getApplicationContext() );
                Bitmap bitmap = bitmapUtils.decodeFile( path );
                iv_bigPicture.setImageBitmap( bitmap );
            }
        }
    }

    public void startClipActivity(String path) {
        Intent intent = new Intent( this, PhotoClipSmallActivity.class );
        intent.putExtra( "path", path );
        startActivityForResult( intent, PHOTO_CORPRESULT_CODE );
        cancelbtnAnim();
    }
}
