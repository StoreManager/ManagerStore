package com.cottee.managerstore.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.ToggleButton;

import com.bumptech.glide.Glide;
import com.cottee.managerstore.Filter.CashierInputFilter;
import com.cottee.managerstore.R;
import com.cottee.managerstore.bean.StoreInfo;
import com.cottee.managerstore.handle.LoginRegisterInformationHandle;
import com.cottee.managerstore.manage.StoreInfoManager;
import com.cottee.managerstore.manage.SubmitStoreInfoManager;
import com.cottee.managerstore.utils.BitmapUtils;
import com.cottee.managerstore.utils.OssUtils;
import com.cottee.managerstore.utils.ToastUtils;

import java.io.File;
import java.util.Calendar;
import java.util.List;

/**
 * Created by user on 2017/12/12.
 */

public class DetialInfomation extends Activity implements View.OnClickListener {
    private static final int PATH_REQ = 3;
    private static final int ONE_REQ = 4;
    private static final int TWO_REQ = 5;
    private static final int THREE_REQ = 6;
    private static final int AM_TIME = 1;
    private static final int PM_TIME = 2;
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
    private ToggleButton btn_order;
    private boolean reserve;
    private Drawable on;
    private Drawable off;
    private String time;
    private Button btn_back;
    private TextView isMoney;
    private List<String> environment;
    private SubmitStoreInfoManager submitStoreInfo;
    private ImageView shop_icon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_detialinfomation );
        Resources resources = this.getResources();
        on = resources.getDrawable( R.mipmap.turnon );
        off = resources.getDrawable( R.mipmap.turnoff );
        Intent intent = getIntent();
        storeInfo = (StoreInfo) intent.getSerializableExtra( "storeInfo" );
        findView();
        surface = storeInfo.getSurface();
        environment = storeInfo.getEnvironment();
        reserve = storeInfo.isReserve();
        if (reserve) {
            btn_order.setChecked( true );
            btn_order.setBackground( on );
        } else {
            btn_order.setChecked( false );
            btn_order.setBackground( off );
        }
        if (surface != null && !surface.equals( "#" ) && !surface.equals( " " )) {
            iv_surface.setBackground( null );
            Glide.with( this ).load( surface ).into( iv_surface );
        }
        if (environment.size() > 1) {
            one = environment.get( 0 );
            two = environment.get( 1 );
            three = environment.get( 2 );
            if (environment.get( 0 ) != null) {
                iv_photo1.setBackground( null );
                Glide.with( this ).load( environment.get( 0 ) ).into( iv_photo1 );
            }
            if (environment.get( 1 ) != null) {
                iv_photo2.setBackground( null );
                Glide.with( this ).load( environment.get( 1 ) ).into( iv_photo2 );
            }
            if (environment.get( 2 ) != null) {
                iv_photo3.setBackground( null );
                Glide.with( this ).load( environment.get( 2 ) ).into( iv_photo3 );
            }
        }
        time = storeInfo.getBusiness_hours();
        if (time != null) {
            String[] split = time.split( "-" );
            timeAM = split[0];
            timePM = split[1];
            tv_timeAM.setText( timeAM );
            tv_timePM.setText( timePM );
        }
        //不可更改
        tv_storeName.setText( storeInfo.getName() );
        tv_address.setText( storeInfo.getAddress() );
        tv_style.setText( storeInfo.getClassify() );

        //更改
        sign = storeInfo.getIntroduce();
        tv_sign.setText( sign );
        phone = storeInfo.getPhone();
        tv_phone.setText( phone );
        money = storeInfo.getAvecon();
        tv_money.setText( money );

        btn_order.setOnCheckedChangeListener( new OnChangeListener() );
    }

    private void findView() {

        shop_icon = (ImageView)findViewById( R.id.iv_shop_icon );
        shop_icon.setOnClickListener( this );
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

        isMoney = (TextView) findViewById( R.id.tv_isMoney );
        iv_surface = (ImageView) findViewById( R.id.iv_surface );
        iv_surface.setOnClickListener( this );
        iv_photo1 = (ImageView) findViewById( R.id.iv_photo1 );
        iv_photo1.setOnClickListener( this );
        iv_photo2 = (ImageView) findViewById( R.id.iv_photo2 );
        iv_photo2.setOnClickListener( this );
        iv_photo3 = (ImageView) findViewById( R.id.iv_photo3 );
        iv_photo3.setOnClickListener( this );

        btn_back = (Button) findViewById( R.id.btn_backs );
        btn_back.setOnClickListener( this );

        btn_timeAM.setOnClickListener( this );
        btn_timePM.setOnClickListener( this );

        btn_order = (ToggleButton) findViewById( R.id.btn_order );
        CashierInputFilter[] filters = {new CashierInputFilter()};
        et_money.setFilters( filters );
        et_money.addTextChangedListener( new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String trim = et_money.getText().toString().trim();
                if (trim.isEmpty()) {
                    return;
                }
                boolean octNumber = isOctNumber( trim );
                if (octNumber) {
                    isMoney.setVisibility( View.GONE );
                } else {
                    isMoney.setVisibility( View.VISIBLE );
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        } );

    }

    public void edit(View view) {
        sign = tv_sign.getText().toString().trim();
        timeAM = tv_timeAM.getText().toString().trim();
        timePM = tv_timePM.getText().toString().trim();
        money = tv_money.getText().toString().trim();
        phone = tv_phone.getText().toString().trim();

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


        et_sign.setText( sign );
        btn_timeAM.setText( timeAM );
        btn_timePM.setText( timePM );
        et_money.setText( money );
        et_phone.setText( phone );

    }

    public void save(View view) {
        sign = et_sign.getText().toString().trim();
        timeAM = btn_timeAM.getText().toString().trim();
        timePM = btn_timePM.getText().toString().trim();
        money = et_money.getText().toString().trim();
        phone = et_phone.getText().toString().trim();
        time = timeAM + "-" + timePM;
        submit();
    }

    private void submit() {
        if (money.isEmpty() || money.equals( "0.00" ) || money.equals( "0.0" )) {
            money = "0";
        }
        Drawable background = btn_order.getBackground();
        if (background == on) {
            reserve = true;
        } else {
            reserve = false;
        }
        boolean mobileNo = isMobileNo( phone );
        boolean phoneNo = isTelePhoneNo( phone );
        boolean octNumber = isOctNumber( money );
        if (octNumber == false) {
            ToastUtils.showToast( this, "金额格式有误哦" );
            return;
        }

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

            tv_sign.setText( sign );
            tv_timeAM.setText( timeAM );
            tv_timePM.setText( timePM );
            tv_money.setText( money );
            tv_phone.setText( phone );
        } else {
            ToastUtils.showToast( this, "号码输入有误哦" );
            return;
        }

        submitStoreInfo = new SubmitStoreInfoManager( this, new LoginRegisterInformationHandle(
                this, ""
        ) );
        submitStoreInfo.changeInfo( sign, time, money, String.valueOf( reserve ), phone, surface );
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent( this, BigPhotoActivity.class );
        switch (view.getId()) {
            case R.id.iv_surface:
                Intent surfaceIntent = new Intent( this, FrontCoverActivity.class );
                surfaceIntent.putExtra( "photo_url", surface );
                startActivityForResult( surfaceIntent, PATH_REQ );
                break;
            case R.id.iv_photo1:
                intent.putExtra( "photo_url", one );
                intent.putExtra( "photo_id", "4" );
                startActivityForResult( intent, ONE_REQ );
                break;
            case R.id.iv_photo2:
                intent.putExtra( "photo_url", two );
                intent.putExtra( "photo_id", "2" );
                startActivityForResult( intent, TWO_REQ );
                break;
            case R.id.iv_photo3:
                intent.putExtra( "photo_url", three );
                intent.putExtra( "photo_id", "3" );
                startActivityForResult( intent, THREE_REQ );
                break;
            case R.id.et_timeAM:
                showTimePickerDialog( btn_timeAM, timeAM, AM_TIME );
                break;
            case R.id.et_timePM:
                showTimePickerDialog( btn_timePM, timePM, PM_TIME );
                break;
            case R.id.btn_backs:
                onBackPressed();
                break;
            case R.id.iv_shop_icon:
                ToastUtils.showToast( this,"修改头像" );
                break;
            default:
                break;
        }
    }

    /**
     * 时间轴
     */
    private void showTimePickerDialog(final Button button, String times, final int flag) {
        String[] split = times.split( ":" );
        final int hour = Integer.parseInt( split[0] );
        int minute = Integer.parseInt( split[1] );

        Dialog dialog = new TimePickerDialog( DetialInfomation.this,
                new TimePickerDialog.OnTimeSetListener() {

                    private String hour;
                    private String min;

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay,
                                          int minute) {
                        if (hourOfDay < 10) {
                            hour = "0" + String.valueOf( hourOfDay );
                        } else {
                            hour = String.valueOf( hourOfDay );
                        }
                        if (minute < 10) {
                            min = "0" + String.valueOf( minute );
                        } else {
                            min = String.valueOf( minute );
                        }
                        String time = hour + ":" + min;
                        button.setText( time );
                        if (flag == AM_TIME) {
                            timeAM = time;
                        } else if (flag == PM_TIME) {
                            timePM = time;
                        }
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


    @Override
    public void onBackPressed() {
        if (btn_save.getVisibility() == View.VISIBLE) {
            AlertDialog.Builder builder = new AlertDialog.Builder( this );
            builder.setMessage( "信息尚未保存，确定要返回吗？" );
            builder.setCancelable( true );
            builder.setNegativeButton( "取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            } );
            builder.setPositiveButton( "确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    finish();
                }
            } );
            AlertDialog dialog = builder.create();
            Window window = dialog.getWindow();
//为Window设置动画
            window.setWindowAnimations( R.style.CustomDialog );
            dialog.show();
        } else {
            finish();
        }
    }

    //十进制
    public static boolean isOctNumber(String str) {
        boolean flag = true;
        for (int i = 0, n = str.length(); i < n; i++) {
            char c = str.charAt( i );
            if (c == '0' | c == '1' | c == '2' | c == '3' | c == '4' | c == '5' | c == '6' | c == '7' | c == '8' | c == '9') {
                flag = true;
            }
        }

        if (str.length() > 1) {
            String num = str.substring( 0, 1 );
            if (num.equals( "." )) {
                flag = false;
            } else if (Integer.valueOf( num ) == 0) {
                if (str.substring( 1, 2 ).equals( "." ) || str.substring( 1, 2 ) == null) {
                    flag = true;
                } else {
                    flag = false;
                }
            }
            String last = str.substring( str.length() - 1, str.length() );
            boolean equals = last.equals( "." );
            if (equals) {
                flag = false;
            }

            if (str.contains( "." )) {
                int i = str.indexOf( "." );
                String substring = str.substring( i + 1, str.length() );
                if (substring.length() > 2) {
                    flag = false;
                }
            }
        }
        return flag;
    }

    public class OnChangeListener implements CompoundButton.OnCheckedChangeListener {
        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
            btn_order.setBackground( b ? on : off );
            if (btn_save.getVisibility() == View.VISIBLE) {
                return;
            }
            submit();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case PATH_REQ:
                if (resultCode == RESULT_OK) {
                    surface = data.getStringExtra( "submitPath" );
                    String bitmampath = data.getStringExtra( "bitmampath" );
                    submit();
//                    Bitmap bitmap = FrontCoverActivity.readBitmapAutoSize( bitmampath );
                    String ossExtranetPath = OssUtils.getOSSExtranetPath( surface );
                    Glide.with( this ).load( ossExtranetPath ).into( iv_surface );
                }
            case ONE_REQ:
                if (resultCode == RESULT_OK) {
                    String submitPath1 = data.getStringExtra( "submitPath" );
                    String bitmampath = data.getStringExtra( "bitmampath" );
//                    submitStoreInfo.submitEnvirInfo( submitPath1 );
                    Bitmap bitmap = FrontCoverActivity.readBitmapAutoSize( bitmampath );
                    iv_photo1.setImageBitmap( bitmap );
                }
                break;
            case TWO_REQ:
                if (resultCode == RESULT_OK) {
                    String submitPath2 = data.getStringExtra( "submitPath" );
                    String bitmampath = data.getStringExtra( "bitmampath" );
//                    submitStoreInfo.submitEnvirInfo( submitPath2 );
                    Bitmap bitmap = FrontCoverActivity.readBitmapAutoSize( bitmampath );
                    iv_photo2.setImageBitmap( bitmap );
                }
                break;
            case THREE_REQ:
                String submitPath3 = data.getStringExtra( "submitPath" );
                if (resultCode == RESULT_OK) {
                    String bitmampath = data.getStringExtra( "bitmampath" );
//                    submitStoreInfo.submitEnvirInfo( submitPath3 );
                    Bitmap bitmap = FrontCoverActivity.readBitmapAutoSize( bitmampath );
                    iv_photo3.setImageBitmap( bitmap );
                }
                break;
            default:
                break;
        }
    }
}
