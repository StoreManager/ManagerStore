package com.cottee.managerstore.activity;

import android.app.Activity;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;

import com.bumptech.glide.Glide;
import com.cottee.managerstore.R;
import com.cottee.managerstore.bean.StoreInfo;
import com.cottee.managerstore.utils.ToastUtils;

import java.util.Calendar;

/**
 * Created by user on 2017/12/12.
 */

public class DetialInfomation extends Activity implements View.OnClickListener {

    private TextView tv_storeName;
    private Button btn_edit;
    private Button btn_save;
    private TextView tv_timeAM;
    private Button btn_timeAM;
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
    private String timeAM;
    private String timePM;
    private String money;
    private String phone;
    private StoreInfo storeInfo;
    private Bitmap bitmap;
    private ImageView iv_surface;
    private String surface;
    private String one;
    private String two;
    private String three;
    public Context mContext = DetialInfomation.this;
    private TextView tv_timePM;
    private Button btn_timePM;
    private TextView tv_order;
    private Button btn_order;
    private String order;
    private int click=1;

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

        String time = storeInfo.getBusiness_hours();
        String[] split = time.split( "-" );
        tv_storeName.setText( storeInfo.getName() );
        tv_timeAM.setText( split[0] );
        tv_timePM.setText( split[1] );
        tv_sign.setText( storeInfo.getIntroduce() );
        tv_phone.setText( storeInfo.getPhone() );
        tv_style.setText( "(" + storeInfo.getClassify() + ")" );
        tv_address.setText( storeInfo.getAddress() );
    }

    private void findView() {
        tv_storeName = (TextView) findViewById( R.id.tv_storeName );

        btn_edit = (Button) findViewById( R.id.btn_edit );
        btn_save = (Button) findViewById( R.id.btn_save );

        tv_timeAM = (TextView) findViewById( R.id.tv_timeAM );
        btn_timeAM = (Button) findViewById( R.id.et_timeAM );

        tv_timePM = (TextView) findViewById( R.id.tv_timePM );
        btn_timePM = (Button) findViewById( R.id.et_timePM );

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

        btn_timeAM.setOnClickListener( this );
        btn_timePM.setOnClickListener( this );

        tv_order = (TextView) findViewById( R.id.tv_order );
        btn_order = (Button) findViewById( R.id.btn_order );
        btn_order.setOnClickListener( this );

    }

    public void edit(View view) {

        sign = tv_sign.getText().toString().trim();
        timeAM = tv_timeAM.getText().toString().trim();
        timePM = tv_timePM.getText().toString().trim();
        money = tv_money.getText().toString().trim();
        phone = tv_phone.getText().toString().trim();
        order = tv_order.getText().toString().trim();

        tv_sign.setVisibility( View.GONE );
        et_sign.setVisibility( View.VISIBLE );

        tv_timeAM.setVisibility( View.GONE );
        btn_timeAM.setVisibility( View.VISIBLE );

        tv_timePM.setVisibility( View.GONE );
        btn_timePM.setVisibility( View.VISIBLE );

        tv_money.setVisibility( View.GONE );
        et_money.setVisibility( View.VISIBLE );

        tv_phone.setVisibility( View.GONE );
        et_phone.setVisibility( View.VISIBLE );

        btn_edit.setVisibility( View.GONE );
        btn_save.setVisibility( View.VISIBLE );

        tv_order.setVisibility( View.GONE );
        btn_order.setVisibility( View.VISIBLE );

        et_sign.setText( sign );
        btn_timeAM.setText( timeAM );
        btn_timePM.setText( timePM );
        et_money.setText( money );
        et_phone.setText( phone );
        btn_order.setText( order );

    }

    public void save(View view) {
        sign = et_sign.getText().toString().trim();
        timeAM = btn_timeAM.getText().toString().trim();
        timePM = btn_timePM.getText().toString().trim();
        money = et_money.getText().toString().trim();
        phone = et_phone.getText().toString().trim();
        order=btn_order.getText().toString().trim();

        boolean mobileNo = isMobileNo( phone );
        boolean phoneNo = isTelePhoneNo( phone );
        if (mobileNo == true || phoneNo == true) {
            et_sign.setVisibility( View.GONE );
            tv_sign.setVisibility( View.VISIBLE );

            btn_timeAM.setVisibility( View.GONE );
            tv_timeAM.setVisibility( View.VISIBLE );

            btn_timePM.setVisibility( View.GONE );
            tv_timePM.setVisibility( View.VISIBLE );

            et_money.setVisibility( View.GONE );
            tv_money.setVisibility( View.VISIBLE );

            et_phone.setVisibility( View.GONE );
            tv_phone.setVisibility( View.VISIBLE );

            btn_edit.setVisibility( View.VISIBLE );
            btn_save.setVisibility( View.GONE );

            btn_order.setVisibility( View.GONE );
            tv_order.setVisibility( View.VISIBLE );

            tv_sign.setText( sign );
            tv_timeAM.setText( timeAM );
            tv_timePM.setText( timePM );
            tv_money.setText( money );
            tv_phone.setText( phone );
            tv_order.setText( order );
        } else {
            ToastUtils.showToast( this, "号码输入有误哦" );
            return;
        }
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
            case R.id.et_timeAM:
                showTimePickerDialog( btn_timeAM );
                break;
            case R.id.et_timePM:
                showTimePickerDialog( btn_timePM );
                break;
            case R.id.btn_order:
                click++;
                if(click%2==0){
                    btn_order.setText( "可预约" );
                }else {
                    btn_order.setText( "不可预约" );
                }

            default:
                break;
        }
    }
    /**
     * 时间轴
     */
    private void showTimePickerDialog(final Button button) {
        Calendar calendar = Calendar.getInstance();
        final int hour = calendar.get( Calendar.HOUR_OF_DAY );
        int minute = calendar.get( Calendar.MINUTE );
        Dialog dialog = new TimePickerDialog( DetialInfomation.this,
                new TimePickerDialog.OnTimeSetListener() {

                    private String hour;
                    private String min;

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay,
                                          int minute) {
                        if(hourOfDay<10){
                            hour = "0"+String.valueOf( hourOfDay );
                        }else {
                            hour=String.valueOf( hourOfDay );
                        }
                        if(minute<10){
                            min = "0"+String.valueOf( minute );
                        }else {
                           min=String.valueOf( minute );
                        }
                        String time= hour +":"+min;
                        button.setText(time);
                    }
                }, hour, minute, DateFormat.is24HourFormat( DetialInfomation.this ) );
        dialog.show();
    }

    /**
     * 手机号正则
     */
    public static boolean isMobileNo(String mobileNums) {
        String telRegex = "[1][3587]\\d{9}";
        if (TextUtils.isEmpty( mobileNums ))
            return false;
        else
            return mobileNums.matches( telRegex );
    }

    /**
     * 固定电话正则
     */
    public static boolean isTelePhoneNo(String mobileNums) {
        String telRegex = "^\\d{3,4}-\\d{7,8}$";
        if (TextUtils.isEmpty( mobileNums ))
            return false;
        else
            return mobileNums.matches( telRegex );
    }
}
