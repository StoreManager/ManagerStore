package com.cottee.managerstore.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.cottee.managerstore.R;
import com.cottee.managerstore.bean.FoodDetail;
import com.cottee.managerstore.manage.AddFoodInfoIsEmpty;
import com.cottee.managerstore.utils.BitmapUtils;
import com.cottee.managerstore.view.SelectPicPopupWindow;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class AddFoodActivity extends Activity {
    private ImageButton imgbtn_foodImg;
    private EditText edit_foodName;
    private EditText edit_foodPrice;
    private EditText edit_foodDescription;
    private Button btn_cancel;
    private Button btn_oK;
    private SelectPicPopupWindow selectPicPopupWindow;
    private String filePath=null;
    private TextView tv_inputNumber;
    private Drawable foodImage=null;
    private int num=100;
    private int type=-1;
    private String foodName;
    private String foodPrice;
    private String foodDescription;
    private AddFoodInfoIsEmpty addFoodInfoIsEmpty;
    public static List<FoodDetail> foodDetailList=new ArrayList<>();
    private String path;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_food);
        initView();
        initEvent();

    }
    private void initView() {
        imgbtn_foodImg = findViewById(R.id.imgbtn_foodImg);
        edit_foodName = findViewById(R.id.edit_foodName);
        edit_foodPrice = findViewById(R.id.edit_foodPrice);
        edit_foodDescription = findViewById(R.id.edit_foodDescription);
        tv_inputNumber = findViewById(R.id.tv_inputNumber);
        btn_cancel = findViewById(R.id.btn_cancle);
        btn_oK = findViewById(R.id.btn_ok);
    }
    private void initEvent() {

//        菜品图片调取相册和相机
        imgbtn_foodImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectPicPopupWindow =
                        new SelectPicPopupWindow(AddFoodActivity.this);
                selectPicPopupWindow.setOnClickItem(new SelectPicPopupWindow.OnClickItem() {


                    @Override
                    //拍照
                    public void click_take_photo() {
                        AlertDialog.Builder builder = new AlertDialog.Builder
                                (AddFoodActivity.this);
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
                selectPicPopupWindow.showAtLocation(AddFoodActivity.this.findViewById(R.id.scroll_addFood),
                        Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL, 0, 0); //设置layout在PopupWindow中显示的位置
            }
        });
//     菜品描述
        edit_foodDescription.addTextChangedListener(new TextWatcher() {

            private CharSequence wordNum;//记录输入的字数
            private int selectionStart;
            private int selectionEnd;

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int
                    i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1,
                                      int i2) {
                wordNum= charSequence;//实时记录输入的字数

            }

            @Override
            public void afterTextChanged(Editable editable) {
                int number = num - editable.length();
                //TextView显示剩余字数
                tv_inputNumber.setText("还剩余" + number+"字数");
                selectionStart=edit_foodDescription.getSelectionStart();
                selectionEnd = edit_foodDescription.getSelectionEnd();
                if (wordNum.length() > num) {
                    //删除多余输入的字（不会显示出来）
                    editable.delete(selectionStart - 1, selectionEnd);
                    int tempSelection = selectionEnd;
                    edit_foodDescription.setText(editable);
                    edit_foodDescription.setSelection(tempSelection);//设置光标在最后
                }
            }
        });

        //填写完成按钮
        btn_oK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                foodImage=imgbtn_foodImg.getDrawable();
                foodName = edit_foodName.getText().toString();
                foodPrice = edit_foodPrice.getText().toString();
                foodDescription = edit_foodDescription.getText().toString();
                addFoodInfoIsEmpty = new
                        AddFoodInfoIsEmpty(AddFoodActivity.this);
                boolean empty= addFoodInfoIsEmpty.isInfoEmpty(foodImage,foodName,foodPrice);
                if (empty)
                {
                    FoodDetail foodDetail = new FoodDetail();
                    foodDetail.setFoodImg(path);
                    foodDetail.setFoodName(foodName);
                    foodDetail.setPrice(foodPrice);
                    foodDetail.setDescription(foodDescription);
                    foodDetailList.add(foodDetail);
                    finish();

                }


            }
        });
    }
    //加载相册图片
    private void showImage(String imaePath){
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
                    String name = new DateFormat().format( "yyyyMMdd_hhmmss",
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
                    BitmapUtils bitmapUtils = new BitmapUtils( getApplicationContext() );
                    bitmap = bitmapUtils.decodeFile(path);
                    imgbtn_foodImg.setImageBitmap( bitmap );
                    break;
            }
        }

    }
    public void startClipActivity(String path) {
        Intent intent = new Intent( this, ManagePicActivity.class );
        intent.putExtra( "path", path );
        startActivityForResult( intent, 4 );
    }
    public void backType(View view)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder
                (AddFoodActivity.this);
        builder.setTitle("用户提示")
                .setMessage("小主确定放弃本次菜单信息的输入吗？")
                .setCancelable(false);
        builder.setPositiveButton("放弃", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();

            }
        });

        builder.setNegativeButton("不放弃", new DialogInterface.OnClickListener() {



            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.show();
    }
}
