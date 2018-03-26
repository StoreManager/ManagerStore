package com.cottee.managerstore.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cottee.managerstore.R;
import com.cottee.managerstore.bean.FoodDetail;
import com.cottee.managerstore.handle.LoginRegisterInformationHandle;
import com.cottee.managerstore.manage.ProjectTypeDetailManager;

import java.util.List;

/**
 * Created by Administrator on 2018-03-25.
 */

public class ModifyFoodDetailActivity extends Activity {

    private LinearLayout linear_description;
    private TextView tv_countNumber;
    private Button btn_cancelEdit;
    private Button btn_foodCommitOk;
    private TextView tv_yuan;
    private TextView tv_title;
    private ImageButton imgbtn_foodImg;
    private TextView tv_foodName;
    private TextView tv_foodPrice;
    private TextView tv_description;
    private Button btn_change;
    private Button btn_delete;
    private EditText edit_foodName;
    private EditText edit_foodPrice;
    private EditText edit_foodDescription;
    private View view1;
    private View view2;
    private View view3;
    private int clicked=0;

    private ProjectTypeDetailManager detailManager;
    private List<FoodDetail.ItemListBean> item_list;
    private int positon;
    private String item_id;
    private String class_id;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_detail);
        clicked++;
        initView();
        initData();
    }
    private void initView() {
        linear_description = findViewById(R.id.linear_description);
        tv_countNumber = findViewById(R.id.tv_countNumber);
        btn_cancelEdit = findViewById(R.id.btn_cancelEdit);
        btn_foodCommitOk = findViewById(R.id.btn_foodCommitOk);
        tv_yuan = findViewById(R.id.tv_yuan);
        tv_title = findViewById(R.id.tv_title);
        imgbtn_foodImg = findViewById(R.id.imgbtn_foodImg);
        tv_foodName = findViewById(R.id.tv_foodName);
        tv_foodPrice = findViewById(R.id.tv_foodPrice);
        tv_description = findViewById(R.id.tv_des);
        btn_change = findViewById(R.id.btn_change);
        btn_delete = findViewById(R.id.btn_delete);
        edit_foodName = findViewById(R.id.edit_foodName);
        edit_foodPrice = findViewById(R.id.edit_foodPrice);
        edit_foodDescription = findViewById(R.id.edit_foodDescription);
        view1 = findViewById(R.id.view1);
        view2 = findViewById(R.id.view2);
        view3 = findViewById(R.id.view3);
    }
    private void initData() {
        item_list = ManageFoodDetail1Activity
                .item_list;
        detailManager = new ProjectTypeDetailManager(this,new LoginRegisterInformationHandle());
        positon = getIntent().getIntExtra("position", 0);
        item_id = item_list.get(positon).getItem_id();
        class_id = item_list.get(positon).getClass_id();
        String name = item_list.get(positon).getName();
        String univalence = item_list.get(positon).getUnivalence();
        String description = item_list.get(positon).getDescription();
        String photo = item_list.get(positon).getPhoto();
        tv_title.setText(class_id);
        tv_foodName.setText(name);
        tv_foodPrice.setText(univalence);
        tv_description.setText(description);
        Glide.with(this).load(photo).into(imgbtn_foodImg);

    }
}
