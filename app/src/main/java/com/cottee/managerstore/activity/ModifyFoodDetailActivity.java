package com.cottee.managerstore.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cottee.managerstore.R;
import com.cottee.managerstore.bean.FoodDetail;
import com.cottee.managerstore.bean.UserRequestInfo;
import com.cottee.managerstore.handle.LoginRegisterInformationHandle;
import com.cottee.managerstore.handle.oss_handler.OssHandler;
import com.cottee.managerstore.manage.AddFoodInfoIsEmpty;
import com.cottee.managerstore.manage.ProjectTypeDetailManager;
import com.cottee.managerstore.utils.BitmapUtils;
import com.cottee.managerstore.utils.ToastUtils;
import com.cottee.managerstore.utils.myt_oss.ConfigOfOssClient;
import com.cottee.managerstore.utils.myt_oss.DownloadUtils;
import com.cottee.managerstore.utils.myt_oss.InitOssClient;
import com.cottee.managerstore.utils.myt_oss.UploadUtils;
import com.cottee.managerstore.view.SelectPicPopupWindow;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import static com.cottee.managerstore.activity.AddFoodActivity.objectKey;
import static com.cottee.managerstore.activity.ManageFoodDetail1Activity
        .foodDetail;

/**
 * Created by Administrator on 2018-03-25.
 */

public class ModifyFoodDetailActivity extends Activity {

    private TextView tv_countNumber;
    private Button btn_saveInfo;
    private TextView tv_title;
    private ImageButton imgbtn_foodImg;
    private EditText edit_foodName;
    private EditText edit_foodPrice;
    private EditText edit_foodDescription;
    private int clicked=0;
    private String filePath = null;
    private ProjectTypeDetailManager detailManager;
    private List<FoodDetail.ItemListBean> item_list;
    private int positon;
    private String path=null;
    private String item_id;
    private String class_id;
    private SelectPicPopupWindow selectPicPopupWindow;
    private Context mContext=ModifyFoodDetailActivity.this;
    private OssHandler handler = new OssHandler(this);
    private String name;
    private int type=-1;
    private String key;
    private Bitmap bitmap;
    private AddFoodInfoIsEmpty addFoodInfoIsEmpty;
    private Button btn_deleteFood;
    private OssHandler ossHandler;
    private String discount_singe;
    private String discount;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_detail);
        key ="merchant/"+ UserRequestInfo.getUserEmail()+"/item"
                +"/"+new DateFormat().format( "yyyyMMdd_hhmmss",
                Calendar.getInstance( Locale.CHINA ) ) + ".jpg";
        InitOssClient.initOssClient(this, ConfigOfOssClient.TOKEN_ADDRESS, ConfigOfOssClient.ENDPOINT);
        clicked++;
        initView();
        initData();
        initEvent();
    }
    private void initView() {
        tv_countNumber = findViewById(R.id.tv_countNumber);
        btn_saveInfo = findViewById(R.id.btn_save);
        tv_title = findViewById(R.id.tv_title);
        imgbtn_foodImg = findViewById(R.id.imgbtn_foodImg);
        btn_deleteFood = findViewById(R.id.btn_deleteFood);
        edit_foodName = findViewById(R.id.edit_foodName);
        edit_foodPrice = findViewById(R.id.edit_foodPrice);
        edit_foodDescription = findViewById(R.id.edit_foodDescription);
    }
    private void initData() {
        addFoodInfoIsEmpty = new AddFoodInfoIsEmpty(this);
        ossHandler = new OssHandler(mContext,imgbtn_foodImg);
        item_list = foodDetail.getItem_list();
        detailManager = new ProjectTypeDetailManager(this,new LoginRegisterInformationHandle());
        positon = getIntent().getIntExtra("position", 0);
        String foodname = getIntent().getStringExtra("foodname");
        String foodprice = getIntent().getStringExtra("foodprice");
        String fooddesc = getIntent().getStringExtra("fooddesc");

        item_id = item_list.get(positon).getItem_id();
        class_id = item_list.get(positon).getClass_id();
        discount_singe = item_list.get(positon).getDiscount_singe();
        discount = item_list.get(positon).getDiscount();
        System.out.println("item_id:" + item_id + "classId:" + class_id);
        byte[] photo = getIntent().getByteArrayExtra("photo");
        if(photo!=null){
            bitmap = BitmapFactory.decodeByteArray(photo, 0, photo.length);
            imgbtn_foodImg.setImageBitmap(bitmap);
        }
        tv_title.setText(foodname);
        edit_foodName.setText(foodname);
        edit_foodPrice.setText(foodprice);
        edit_foodDescription.setText(fooddesc);
    }
    private void initEvent()
    {
        imgbtn_foodImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectPicPopupWindow = new SelectPicPopupWindow(mContext);
                selectPicPopupWindow.setOnClickItem(new SelectPicPopupWindow.OnClickItem() {
                    @Override
                    //拍照
                    public void click_take_photo() {
                                Intent intent = new Intent( MediaStore.ACTION_IMAGE_CAPTURE );
                                startActivityForResult( intent, 1 );
                    }

                    @Override
//                    相册取图
                    public void click_pick_photo() {
                        Intent intent = new Intent(Intent.ACTION_PICK,
                                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(intent, 2);
                    }
                });
                selectPicPopupWindow.showAtLocation(ModifyFoodDetailActivity.this.findViewById(R.id.scroll_add_detail),
                        Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
            }
        });
        edit_foodPrice.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int
                    count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String edit_price = edit_foodPrice.getText().toString().trim();
                        if (edit_price.isEmpty())
                        {
                            return;
                        }
                        boolean octNumber = DetialInfomation.isOctNumber(edit_price);
                        if (octNumber)
                        {
                        }
                        else {
                            Toast.makeText(mContext, "金额输入有误！",
                                    Toast.LENGTH_SHORT).show();
                        }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        //保存信息并上传
        btn_saveInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edit_foodName.getText().toString().isEmpty()||edit_foodPrice.getText().toString().isEmpty())
                {
                    ToastUtils.showToast(ModifyFoodDetailActivity.this,"请将信息填写完整");
                }
                else {
                    if (path==null)
                    {
                        System.out.println("名字"+edit_foodName.getText().toString());
                        System.out.println("价格:" + edit_foodPrice.getText().toString());
                        System.out.println("描述：" + edit_foodDescription.getText().toString());
                        System.out.println("图片：" + item_list.get(positon)
                                .getPhoto());
                        item_list.get(positon).setName(edit_foodName.getText().toString());
                        item_list.get(positon).setUnivalence(edit_foodPrice.getText().toString());
                        item_list.get(positon).setUnivalence(edit_foodDescription.getText().toString());
                        item_list.get(positon).setPhoto(item_list.get(positon).getPhoto());
                        detailManager.projectDetailManageUpdate(edit_foodName.getText().toString()
                                ,item_list.get(positon).getDiscount_singe()
                                ,item_list.get(positon).getDiscount()
                                ,item_list.get(positon).getClass_id()
                                ,item_list.get(positon).getPhoto()
                                , edit_foodDescription.getText().toString()
                        ,edit_foodPrice.getText().toString(),item_list.get(positon).getItem_id());
                        finish();
                    }
                    else {
                        item_list.get(positon).setName(edit_foodName.getText().toString().trim());
                        item_list.get(positon).setPhoto(key);
                        UploadUtils.uploadFileToOss(handler,ConfigOfOssClient.BUCKET_NAME,key,path);
                        item_list.get(positon).setUnivalence(edit_foodPrice.getText().toString().trim());
                        item_list.get(positon).setDescription(edit_foodDescription.getText().toString().trim());
                        detailManager.projectDetailManageUpdate(edit_foodName.getText().toString().trim()
                                ,discount_singe
                                ,discount
                                ,item_list.get(positon).getClass_id()
                                ,key,edit_foodDescription.getText().toString().trim()
                                ,edit_foodPrice.getText().toString().trim()
                                ,item_list.get(positon).getItem_id());
                        finish();
                    }

                }

            }
        });
       btn_deleteFood.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {

          final AlertDialog builder = new AlertDialog.Builder(ModifyFoodDetailActivity.this)
                  .create();
          builder.show();
          if (builder.getWindow() == null) return;
          builder.getWindow().setContentView(R.layout.pop_user);//设置弹出框加载的布局
          TextView msg = (TextView) builder.findViewById(R.id.tv_msg);
          Button cancle = (Button) builder.findViewById(R.id.btn_cancle);
          Button sure = (Button) builder.findViewById(R.id.btn_sure);
          if (msg == null || cancle == null || sure == null) return;
          msg.setText("数据删除无法恢复");
          cancle.setText("再想想");
          sure.setText("直接删除");
          cancle.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  builder.dismiss();
              }
          });
          sure.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  detailManager.projectDetailManageDelete(item_list.get(positon).getClass_id()
                          ,item_list.get(positon).getItem_id());
                  item_list.remove(positon);
                  builder.dismiss();
                  finish();
              }
          });
      }
  });
//
    }
    //加载相册图片
    private void showImage(String imaePath) {
        Bitmap bm = BitmapFactory.decodeFile(imaePath);
        imgbtn_foodImg.setImageBitmap(bm);
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
                        b = new FileOutputStream( filePath );
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
                    imgbtn_foodImg.setImageBitmap(bitmap);
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
                    filePath=imagePath;
                    showImage(imagePath);
                    startClipActivity(imagePath);
                    c.close();
                    break;
                case 4:
                    path = data.getStringExtra( "path" );
                    BitmapUtils bitmapUtils = new BitmapUtils(getApplicationContext());
                    bitmap = bitmapUtils.decodeFile(path);
                    imgbtn_foodImg.setImageBitmap( bitmap );
                    break;
            }
        }

    }
    public void startClipActivity(String path) {
        Intent intent = new Intent( this, ManagePicActivity.class );
        intent.putExtra( "path", path );
        startActivityForResult( intent, 4);
    }
    public void backType(View v)
    {
        if (clicked==1)
        {
            finish();
        }
        else {
            AlertDialog.Builder builder = new AlertDialog.Builder
                    (ModifyFoodDetailActivity.this);
            builder.setTitle("用户提示")
                    .setMessage("小主确定放弃本次编辑吗？")
                    .setCancelable(false);
            builder.setPositiveButton("放弃", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    finish();
                }
            });

            builder.setNegativeButton("不放弃", new DialogInterface
                    .OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                }
            });
            builder.show();
        }
    }
}
