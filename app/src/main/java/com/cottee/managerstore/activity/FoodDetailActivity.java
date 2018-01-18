package com.cottee.managerstore.activity;

import android.app.Activity;
import android.app.AlertDialog;
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
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cottee.managerstore.R;
import com.cottee.managerstore.bean.FoodDetail;
import com.cottee.managerstore.utils.BitmapUtils;
import com.cottee.managerstore.view.SelectPicPopupWindow;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class FoodDetailActivity extends Activity {
    private ImageButton imgbtn_foodImg;
    private TextView tv_foodName;
    private TextView tv_foodPrice;
    private Button btn_change;
    private EditText edit_foodName;
    private EditText edit_foodPrice;
    private EditText edit_foodDescription;
    private TextView tv_des;
    private SelectPicPopupWindow selectPicPopupWindow;
    private String filePath = null;
    List<FoodDetail> foodDetailList = AddFoodActivity
            .foodDetailList;
    private int positon;
    private Button btn_delete;
    private TextView tv_title;
    private Button btn_ok;
    private Button btn_cancelEdit;
    private String path;
    private LinearLayout linear_description;
    private TextView tv_countNumber;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_detail);
        initView();
        initData();
        initEvent();
    }
    private void initView() {
        linear_description = findViewById(R.id.linear_description);
        tv_countNumber = findViewById(R.id.tv_countNumber);
        btn_cancelEdit = findViewById(R.id.btn_cancelEdit);
        btn_ok = findViewById(R.id.btn_ok);
        tv_title = findViewById(R.id.tv_title);
        imgbtn_foodImg = findViewById(R.id.imgbtn_foodImg);
        tv_foodName = findViewById(R.id.tv_foodName);
        tv_foodPrice = findViewById(R.id.tv_foodPrice);
        tv_des = findViewById(R.id.tv_des);
        btn_change = findViewById(R.id.btn_change);
        btn_delete = findViewById(R.id.btn_delete);
        edit_foodName = findViewById(R.id.edit_foodName);
        edit_foodPrice = findViewById(R.id.edit_foodPrice);
        edit_foodDescription = findViewById(R.id.edit_foodDescription);
    }
    private void initData() {

        positon = getIntent().getIntExtra("position", 0);
        tv_foodName.setText(AddFoodActivity.foodDetailList.get(positon).getFoodName());
        tv_foodPrice.setText(AddFoodActivity.foodDetailList.get(positon).getPrice());
        imgbtn_foodImg.setImageBitmap(BitmapFactory.decodeFile(AddFoodActivity.foodDetailList.get(positon).getFoodImg()));
        tv_des.setText(AddFoodActivity.foodDetailList.get(positon).getDescription());
        tv_title.setText(AddFoodActivity.foodDetailList.get(positon).getFoodName());
    }
    private void initEvent() {
        btn_change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                btn_delete.setVisibility(View.GONE);
                btn_ok.setVisibility(View.VISIBLE);


                imgbtn_foodImg.setClickable(true);
                btn_cancelEdit.setVisibility(View.VISIBLE);
                btn_change.setVisibility(View.GONE);
                imgbtn_foodImg.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        selectPicPopupWindow = new SelectPicPopupWindow(FoodDetailActivity.this);
                        selectPicPopupWindow.setOnClickItem(new SelectPicPopupWindow.OnClickItem() {


                            @Override
                            //拍照
                            public void click_take_photo() {
                                AlertDialog.Builder builder = new AlertDialog.Builder
                                        (FoodDetailActivity.this);
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
//                                相册取图
                            public void click_pick_photo() {
                                Intent intent = new Intent(Intent.ACTION_PICK,
                                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                                startActivityForResult(intent, 2);

                            }
                        });
                        selectPicPopupWindow.showAtLocation(FoodDetailActivity.this.findViewById(R.id.linear_add),
                                Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
                    }
                });


                tv_foodName.setVisibility(View.GONE);
                tv_foodPrice.setVisibility(View.GONE);
                tv_des.setVisibility(View.GONE);

                edit_foodName.setVisibility(View.VISIBLE);
                edit_foodPrice.setVisibility(View.VISIBLE);
                linear_description.setVisibility(View.VISIBLE);

                edit_foodName.setText(tv_foodName.getText().toString());
                edit_foodName.setSelection(tv_foodName.getText().toString().length());

                edit_foodPrice.setText(tv_foodPrice.getText().toString());
                edit_foodPrice.setSelection(tv_foodPrice.getText().length());

                edit_foodDescription.setText(tv_des.getText().toString());

                tv_foodName.setText(edit_foodName.getText().toString());
                tv_foodPrice.setText(edit_foodPrice.getText().toString());
                tv_des.setText(edit_foodDescription.getText().toString());


                AddFoodActivity.foodDetailList.get(positon).setFoodName(tv_foodName.getText().toString().trim());
                AddFoodActivity.foodDetailList.get(positon).setPrice(tv_foodPrice.getText().toString().trim());
                AddFoodActivity.foodDetailList.get(positon).setDescription(tv_des.getText().toString().trim());

                if (filePath!=null) {
                    AddFoodActivity.foodDetailList.get(positon).setFoodImg(filePath);

                }

            }
        });
        btn_cancelEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                btn_ok.setVisibility(View.GONE);
                btn_delete.setVisibility(View.VISIBLE);


                imgbtn_foodImg.setClickable(false);
                btn_change.setVisibility(View.VISIBLE);
                btn_cancelEdit.setVisibility(View.GONE);
                edit_foodName.setVisibility(View.GONE);
                edit_foodPrice.setVisibility(View.GONE);
                linear_description.setVisibility(View.GONE);
                tv_foodName.setVisibility(View.VISIBLE);
                tv_foodPrice.setVisibility(View.VISIBLE);
                tv_des.setVisibility(View.VISIBLE);


                AddFoodActivity.foodDetailList.get(positon).setFoodName(tv_foodName.getText().toString().trim());
                AddFoodActivity.foodDetailList.get(positon).setPrice(tv_foodPrice.getText().toString().trim());
                AddFoodActivity.foodDetailList.get(positon).setDescription(tv_des.getText().toString().trim());
                AddFoodActivity.foodDetailList.get(positon).setFoodImg(filePath);
            }
        });
        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imgbtn_foodImg.setClickable(false);
                btn_change.setVisibility(View.VISIBLE);
                btn_cancelEdit.setVisibility(View.GONE);
                edit_foodName.setVisibility(View.GONE);
                edit_foodPrice.setVisibility(View.GONE);
                linear_description.setVisibility(View.GONE);
                tv_foodName.setVisibility(View.VISIBLE);
                tv_foodPrice.setVisibility(View.VISIBLE);
                tv_des.setVisibility(View.VISIBLE);
                imgbtn_foodImg.setImageBitmap(BitmapFactory.decodeFile(AddFoodActivity.foodDetailList.get(positon).getFoodImg()));
                tv_foodName.setText(edit_foodName.getText().toString());
                tv_foodPrice.setText(edit_foodPrice.getText().toString());
                tv_des.setText(edit_foodDescription.getText().toString());
                imgbtn_foodImg.setImageBitmap(BitmapFactory.decodeFile(AddFoodActivity.foodDetailList.get(positon).getFoodImg()));

                AddFoodActivity.foodDetailList.get(positon).setFoodName(tv_foodName.getText().toString().trim());
                AddFoodActivity.foodDetailList.get(positon).setPrice(tv_foodPrice.getText().toString().trim());
                AddFoodActivity.foodDetailList.get(positon).setDescription(tv_des.getText().toString().trim());

                if (path!=null) {
                    AddFoodActivity.foodDetailList.get(positon).setFoodImg(path);
                }
                finish();
            }
        });

        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog alertDialog = new AlertDialog.Builder(FoodDetailActivity.this)
                        .setTitle("用户提示")
                        .setMessage("数据一经删除将无法恢复！！！")
                        .setPositiveButton("残忍删除",null).setNegativeButton("再想想",null)
                        .create();
                alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
                    @Override
                    public void onShow(DialogInterface dialog) {
                        //残忍删除
                        Button btn_positive = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
                        btn_positive.setTextColor(Color.GRAY);
                        btn_positive.setOnClickListener(new View.OnClickListener() {

                            @Override
                            public void onClick(View view) {
                                AddFoodActivity.foodDetailList.remove(positon);
                                finish();
                            }
                        });
                        Button btn_negative=alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE);
                        btn_negative.setTextColor(Color.parseColor("#4862af"));
                    }
                });
                alertDialog.show();
            }
        });
//        菜品描述
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
                int number = 100 - editable.length();
                //TextView显示剩余字数
                tv_countNumber.setText("还剩余" + number+"字数");
                selectionStart=edit_foodDescription.getSelectionStart();
                selectionEnd = edit_foodDescription.getSelectionEnd();
                if (wordNum.length() > 100) {
                    //删除多余输入的字（不会显示出来）
                    editable.delete(selectionStart - 1, selectionEnd);
                    int tempSelection = selectionEnd;
                    edit_foodDescription.setText(editable);
                    edit_foodDescription.setSelection(tempSelection);//设置光标在最后
                }
            }
        });
    }

    //加载相册图片
    private void showImage(String imaePath) {
        Bitmap bm = BitmapFactory.decodeFile(imaePath);
        imgbtn_foodImg.setImageBitmap(bm);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case 1:
                    String sdStatus = Environment.getExternalStorageState();
                    if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) { //
                        // 检测sd是否可用
                        Log.i("TestFile",
                                "SD card is not avaiable/writeable right now.");
                        return;
                    }
                    String name = new DateFormat().format("yyyyMMdd_hhmmss",
                            Calendar.getInstance(Locale.CHINA)) + ".jpg";
                    Bundle bundle = data.getExtras();
                    Bitmap bitmap = (Bitmap) bundle.get("data");//
                    // 获取相机返回的数据，并转换为Bitmap图片格式
                    FileOutputStream b = null;
                    File file = new File("/sdcard/myImage/");
                    file.mkdirs();// 创建文件夹
                    filePath = "/sdcard/myImage/" + name;
                    try {
                        b = new FileOutputStream(filePath);
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, b);
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
                    Uri selectedImage = data.getData();
                    String[] filePathColumns = {MediaStore.Images.Media.DATA};
                    Cursor c = getContentResolver().query(selectedImage, filePathColumns, null, null, null);
                    c.moveToFirst();
                    int columnIndex = c.getColumnIndex(filePathColumns[0]);
                    String imagePath = c.getString(columnIndex);
                    filePath = imagePath;
                    showImage(imagePath);
                    c.close();
                    startClipActivity(imagePath);
                    break;
                case 4:
                    path = data.getStringExtra( "path" );
                    BitmapUtils bitmapUtils = new BitmapUtils( getApplicationContext() );
                    bitmap = bitmapUtils.decodeFile(path);
                    imgbtn_foodImg.setImageBitmap( bitmap );
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
        AlertDialog.Builder builder = new AlertDialog.Builder
                (FoodDetailActivity.this);
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
