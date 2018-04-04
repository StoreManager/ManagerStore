package com.cottee.managerstore.activity1.emp_login;

import android.app.Activity;
import android.app.AlertDialog;
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
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.cottee.managerstore.R;
import com.cottee.managerstore.activity.ManagePicActivity;
import com.cottee.managerstore.bean.emp_login.EmpRequestInfo;
import com.cottee.managerstore.handle.emp_login.EmpUpdateHandle;
import com.cottee.managerstore.handle.oss_handler.OssHandler;
import com.cottee.managerstore.manage.enp_login.EmpUpdateManage;
import com.cottee.managerstore.utils.BitmapUtils;
import com.cottee.managerstore.utils.Canonical;
import com.cottee.managerstore.utils.myt_oss.ConfigOfOssClient;
import com.cottee.managerstore.utils.myt_oss.InitOssClient;
import com.cottee.managerstore.utils.myt_oss.UploadUtils;
import com.cottee.managerstore.view.ImageViewExtend;
import com.cottee.managerstore.view.SelectPicPopupWindow;
import com.cottee.managerstore.widget.BirthDateDialog;
import com.cottee.managerstore.widget.Title;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by Administrator on 2018/3/19.
 */

public class EmpUpdateDetailActivity extends Activity implements View.OnClickListener{

    private Title title;
    private String empName;
    private String empSax;
    private String empBirth;
    private String empPhone;
    private EditText et_emp_update_name;
    private EditText et_emp_update_sex;
    private TextView et_emp_update_birth;
    private EditText et_emp_update_phone;
    private ImageViewExtend imv_update_header;
    private Button btn_emp_info_commit;
    private TextView tv_first_star;
    private TextView tv_toast_show;
    private TextView tv_forth_star;
    private String updateBirth;
    private String updateSex;
    private String updatePhone;
    private Button btn_emp_info_no_commit;
    private SelectPicPopupWindow selectPicPopupWindow;
    private String accunt;
    private String objectKey;
    private String name;
    private String filePath = null;
    private int type=-1;
    private String path=null;
    private OssHandler handler = new OssHandler(this);
    private Bitmap bitmap;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emp_update_detail);
        Intent intent = getIntent();
        empName = intent.getStringExtra("NAME");
        empSax = intent.getStringExtra("SAX");
        empBirth = intent.getStringExtra("BIRTH");
        empPhone = intent.getStringExtra("PHONE");
        byte[] empPhoto = intent.getByteArrayExtra("PHOTO");
        if(empPhoto!=null){
            bitmap = BitmapFactory.decodeByteArray(empPhoto, 0, empPhoto.length);
        }


        accunt = EmpRequestInfo.getUserEmail();
        InitOssClient.initOssClient(this, ConfigOfOssClient.TOKEN_ADDRESS, ConfigOfOssClient.ENDPOINT);
        objectKey ="merchant/"+ accunt+"/item"
                +"/"+new DateFormat().format( "yyyyMMdd_hhmmss",
                Calendar.getInstance( Locale.CHINA ) ) + ".jpg";
        initTitle();
        initView();
    }

    private void initView() {
        et_emp_update_name = (EditText) findViewById(R.id.et_emp_update_name);
        et_emp_update_sex = (EditText) findViewById(R.id.et_emp_update_sex);
        et_emp_update_birth = (TextView) findViewById(R.id.tv_emp_update_birth);
        et_emp_update_phone = (EditText) findViewById(R.id.et_emp_update_phone);
        imv_update_header = (ImageViewExtend) findViewById(R.id.imv_update_header);
        btn_emp_info_commit = (Button) findViewById(R.id.btn_emp_info_commit);
        btn_emp_info_no_commit = (Button) findViewById(R.id.btn_emp_info_no_commit);
        tv_first_star = (TextView) findViewById(R.id.tv_first_star);
        tv_forth_star = (TextView) findViewById(R.id.tv_forth_star);
        tv_toast_show = (TextView) findViewById(R.id.tv_toast_show);
        et_emp_update_name.setText(empName);
        et_emp_update_sex.setText(empSax);
        et_emp_update_birth.setText(empBirth);
        et_emp_update_phone.setText(empPhone);
        imv_update_header.setmDrawShapeType(ImageViewExtend.SHAPE_CIRCLE);
        if(bitmap!=null){
            imv_update_header.setImageBitmap(bitmap);
        }

        et_emp_update_birth.setOnClickListener(this);
        btn_emp_info_commit.setOnClickListener(this);
        imv_update_header.setOnClickListener(this);
        et_emp_update_name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String name = et_emp_update_name.getText().toString().trim();
                if(name.isEmpty()){
                    btn_emp_info_commit.setVisibility(View.GONE);
                    btn_emp_info_no_commit.setVisibility(View.VISIBLE);
                    tv_first_star.setVisibility(View.VISIBLE);
                    tv_toast_show.setText("姓名不能为空");
                    tv_toast_show.setVisibility(View.VISIBLE);
                }else {
                    tv_first_star.setVisibility(View.INVISIBLE);
                    if(tv_forth_star.getVisibility()==View.VISIBLE){
                        tv_toast_show.setText("手机号格式不正确");
                    }else {
                        btn_emp_info_commit.setVisibility(View.VISIBLE);
                        btn_emp_info_no_commit.setVisibility(View.GONE);
                        tv_toast_show.setVisibility(View.INVISIBLE);
                    }
                }
            }
        });
        et_emp_update_phone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String phone = et_emp_update_phone.getText().toString().trim();
                if(Canonical.isMobile(phone)||phone.isEmpty()){

                    tv_forth_star.setVisibility(View.INVISIBLE);
                    if(tv_first_star.getVisibility()==View.VISIBLE){
                        tv_toast_show.setText("姓名不能为空");
                    }else {
                        tv_toast_show.setVisibility(View.INVISIBLE);
                        btn_emp_info_commit.setVisibility(View.VISIBLE);
                        btn_emp_info_no_commit.setVisibility(View.GONE);
                    }

                }else {
                    tv_forth_star.setVisibility(View.VISIBLE);
                    tv_toast_show.setText("手机号格式不正确");
                    tv_toast_show.setVisibility(View.VISIBLE);
                    btn_emp_info_commit.setVisibility(View.GONE);
                    btn_emp_info_no_commit.setVisibility(View.VISIBLE);
                }
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent
            data) {
        if (resultCode==RESULT_OK)
        {
            switch (requestCode){
                case 1:
                    String sdStatus = Environment.getExternalStorageState();
                    if (!sdStatus.equals( Environment.MEDIA_MOUNTED )) { //
                        // 检测sd是否可用
                        Log.i( "TestFile",
                                "SD card is not avaiable/writeable right now." );
                        return;
                    }
                    name = new DateFormat().format( "yyyyMMdd_hhmmss",
                            Calendar.getInstance( Locale.CHINA ) ) + ".jpg";
                    Bundle bundle = data.getExtras();
                    Bitmap bitmap = (Bitmap) bundle.get( "data" );//
                    // 获取相机返回的数据，并转换为Bitmap图片格式
                    FileOutputStream b = null;
                    File file = new File( "/sdcard/myImage/" );
                    file.mkdirs();// 创建文件夹
                    filePath = "/sdcard/myImage/" + name;
                    try {
                        b = new FileOutputStream(filePath);
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
                    imv_update_header.setmDrawShapeType(ImageViewExtend.SHAPE_CIRCLE);
                    imv_update_header.setImageBitmap(bitmap);
                    startClipActivity(filePath);
                    break;
                case 2:
                    type=2;
                    Uri selectedImage = data.getData();
                    String[] filePathColumns = {MediaStore.Images.Media.DATA};
                    Cursor c =getContentResolver().query(selectedImage, filePathColumns, null, null, null);
                    c.moveToFirst();
                    int columnIndex = c.getColumnIndex(filePathColumns[0]);
                    String imagePath = c.getString(columnIndex);
                    filePath =imagePath;
                    showImage(imagePath);
                    startClipActivity(imagePath);
                    c.close();
                    break;
                case 4:
                    path = data.getStringExtra( "path" );
                    BitmapUtils bitmapUtils = new BitmapUtils( getApplicationContext() );
                    bitmap = bitmapUtils.decodeFile(path);
                    imv_update_header.setmDrawShapeType(ImageViewExtend.SHAPE_CIRCLE);
                    imv_update_header.setImageBitmap( bitmap );
                    UploadUtils.uploadFileToOss(handler, ConfigOfOssClient.BUCKET_NAME, objectKey, path);
                    break;
            }
        }

    }

    //加载相册图片
    private void showImage(String imagePath){
        Bitmap bm = BitmapFactory.decodeFile(imagePath);
        imv_update_header.setmDrawShapeType(ImageViewExtend.SHAPE_CIRCLE);
        imv_update_header.setImageBitmap(bm);
    }
    public void startClipActivity(String path) {
        Intent intent = new Intent( this, ManagePicActivity.class );
        intent.putExtra( "path", path );
        startActivityForResult( intent, 4 );
    }

    public void selectBirthday() {
        String registrationDate = et_emp_update_birth.getText().toString();
        BirthDateDialog carRegistrationDateDialog = new
                BirthDateDialog(this).setChoosedDate(registrationDate);
        carRegistrationDateDialog.getRegistrationDateListener(new BirthDateDialog.RegistrationDateListener() {
            @Override
            public void onClick(String dateTime) {
                if (!et_emp_update_birth.getText().equals(dateTime)) {
                    et_emp_update_birth.setText(dateTime);
                   /* userInfoBean.getUserDetailInfoBean().setBirthday(RouteManUtil.strToDate(dateTime));
                    updatePersonalInfo(userInfoBean);*/
                }
            }
        });
        carRegistrationDateDialog.show();
    }

    private void initTitle() {
        title = (Title) findViewById(R.id.title);
        title.setTitleNameStr("编辑员工信息");
        title.setTheme(Title.TitleTheme.THEME_TRANSLATE);
        title.mSetButtonInfo(new Title.ButtonInfo(true, Title
                .BUTTON_LEFT, R.mipmap.back_2x, null
        ));
        //可加button1
        title.mSetButtonInfo(new Title.ButtonInfo(true, Title
                .BUTTON_RIGHT1, 0,
                ""));
        title.setOnTitleButtonClickListener(new Title.OnTitleButtonClickListener() {
            @Override
            public void onClick(int id, Title.ButtonViewHolder viewHolder) {
                switch (id) {
                    case Title.BUTTON_RIGHT1:


                        break;
                    case Title.BUTTON_LEFT:
                        finish();
                }
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
        case R.id.tv_emp_update_birth:
            selectBirthday();
        break;
        case R.id.btn_emp_info_commit:
            updateBirth = et_emp_update_birth.getText().toString().trim();
            updateSex = et_emp_update_sex.getText().toString().trim();
            updatePhone = et_emp_update_phone.getText().toString().trim();
            if (path!=null)
            {
                System.out.println("图片路径"+path);
                try {



                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
            System.out.println("员工图片路径:"+objectKey);
            EmpUpdateManage manage = new EmpUpdateManage(new EmpUpdateHandle(EmpUpdateDetailActivity.this));
            manage.empUpdate(updateSex,updateBirth,updatePhone,objectKey);
            break;

        case R.id.imv_update_header:

                    selectPicPopupWindow =
                            new SelectPicPopupWindow(EmpUpdateDetailActivity.this);
                    selectPicPopupWindow.setOnClickItem(new SelectPicPopupWindow.OnClickItem() {


                        @Override
                        //拍照
                        public void click_take_photo() {
                            AlertDialog.Builder builder = new AlertDialog.Builder
                                    (EmpUpdateDetailActivity.this);
                            builder.setTitle("用户提示")
                                    .setMessage("小主务必将手机横向拍摄！")
                                    .setCancelable(false);
                            builder.setPositiveButton("偏不", new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {


                                }
                            });

                            builder.setNegativeButton("好的", new DialogInterface.OnClickListener() {



                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    Intent intent = new Intent( MediaStore.ACTION_IMAGE_CAPTURE );
                                    startActivityForResult( intent, 1 );
                                }
                            });
                            builder.show();

                        }

                        @Override
//                    相册选取
                        public void click_pick_photo() {
                            Intent intent = new Intent(Intent.ACTION_PICK,
                                    android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                            startActivityForResult(intent,2);

                        }
                    });
                    selectPicPopupWindow.showAtLocation(EmpUpdateDetailActivity.this.findViewById(R.id.ll_emp_update_layout),
                            Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL, 0, 0); //设置layout在PopupWindow中显示的位置
                break;

        default:
        break;
        }
    }
}
