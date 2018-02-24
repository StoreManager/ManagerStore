package com.cottee.managerstore.activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cottee.managerstore.R;
import com.cottee.managerstore.adapter.RecyclerviewAdapter;
import com.cottee.managerstore.bean.FoodDetail;
import com.cottee.managerstore.handle.LoginRegisterInformationHandle;
import com.cottee.managerstore.manage.ProjectTypeDetailManager;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class ManageFoodDetailActivity extends Activity {

    private String  dishId;
    private String dishName;

    private RecyclerView recyclerView;
    private RecyclerviewAdapter adapter;
    public AddFoodActivity addFoodActivity;
    private Button btn_delete;
    private Button btn_selectAll;
    private int index = 0;
    private TextView tv_selectNum;
    private LinearLayout mLlMycollectionBottomDialog;

    private boolean isSelectAll = false;

    private Button btn_top;
    private StaggeredGridLayoutManager layout;
    private Button btn_editFood;
    private Button btn_cancelEdit;
    private LinearLayout linear_reminder;
    private TextView tv_title;

    public static String name;
    public static String description;
    public static List<FoodDetail> detailFoodList;
    public static List<FoodDetail.ItemListBean> item_list;
    public static FoodDetail foodDetail;


    private ProjectTypeDetailManager manager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_food_detail);
        manager = new
                ProjectTypeDetailManager(ManageFoodDetailActivity.this
                ,new LoginRegisterInformationHandle());
        dishId = getIntent().getStringExtra("position");
        dishName = getIntent().getStringExtra("positionName");

        System.out.println("发送ID："+dishId);
        initView();
        initEvent();
        new Thread(new Runnable() {
            @Override
            public void run() {
        manager.JsonCommit(dishId);
            }
        }).start();

}
    private void initView() {
        addFoodActivity = new AddFoodActivity();
        tv_title = findViewById(R.id.tv_title);
        btn_editFood = findViewById(R.id.btn_editFood);
        btn_cancelEdit = findViewById(R.id.btn_cancelEdit);
        linear_reminder = findViewById(R.id.linear_reminder);
        recyclerView=  findViewById(R.id.recyclerView);
        btn_delete = findViewById(R.id.btn_delete);
        tv_selectNum = findViewById(R.id.tv_select_num);
        btn_selectAll = findViewById(R.id.btn_selectAll);
        tv_title.setText(dishName);
        mLlMycollectionBottomDialog = findViewById(R.id.ll_mycollection_bottom_dialog);
        btn_top = findViewById(R.id.btn_top);
        //设置并列2行的layoutManager
        layout = new StaggeredGridLayoutManager(2,
                StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layout);

    }

    /**
     * 根据选择的数量是否为0来判断按钮的是否可点击.
     *
     * @param size
     */
    private void setBtnBackground(int size) {
        if (size != 0) {
            if (size==adapter.getProducts().size())
            {
                btn_top.setClickable(false);
                btn_top.setBackgroundResource(R.drawable.btn_unusable);
            }
            if (size>0&&(size<adapter.getProducts().size())){
                btn_top.setClickable(true);
                btn_top.setBackgroundResource(R.drawable.top);
            }
            btn_delete.setBackgroundResource(R.drawable.button_shape);
            btn_delete.setEnabled(true);
            btn_delete.setTextColor(Color.WHITE);
        } else {
            btn_top.setClickable(false);
            btn_delete.setBackgroundResource(R.drawable.button_shape);
            btn_delete.setEnabled(false);
            btn_delete.setTextColor(ContextCompat.getColor(this,R.color.color_4d4d4e));
        }
    }
    private void initEvent()
    {
        adapter = new RecyclerviewAdapter(this,ManageFoodDetailActivity.detailFoodList,
                new RecyclerviewAdapter.MyItemClickListener() {

                    @Override
                    public void onItemClick(int position,List<FoodDetail> foodDetailList) {
                        if (mLlMycollectionBottomDialog.getVisibility() ==View.VISIBLE) {
                            FoodDetail foodDetail = foodDetailList.get(position);
                            boolean isSelect = foodDetail.isSelect();
                            if (!isSelect) {
                                index++;
                                foodDetail.setSelect(true);
                                if (index == foodDetailList.size()) {
                                    isSelectAll = true;
                                    btn_selectAll.setText("取消全选");
                                    btn_top.setClickable(false);
                                }

                            } else {
                                foodDetail.setSelect(false);
                                index--;
                                isSelectAll = false;
                                btn_selectAll.setText("全选");
                            }
                            setBtnBackground(index);
                            tv_selectNum.setText(String.valueOf(index));
                            adapter.notifyDataSetChanged();
                        }
                        else {
                            Intent intent = new Intent(ManageFoodDetailActivity.this,FoodDetailActivity.class);
                            intent.putExtra("position",position);
                            startActivity(intent);
                        }

                    }
                });
        ((SimpleItemAnimator)recyclerView.getItemAnimator())
                .setSupportsChangeAnimations(false);
        recyclerView.setAdapter(adapter);
        adapter.notifyAdapter(ManageFoodDetailActivity.detailFoodList,false);
        adapter.notifyDataSetChanged();
//       判断当前是否有数据，显示取消提示文字
        if (adapter.getProducts().size()==0)
        {
            linear_reminder.setVisibility(View.VISIBLE);
        }
        else {
            linear_reminder.setVisibility(View.GONE);
        }
        btn_cancelEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adapter.setCheckBoxVisible(View.GONE);


                btn_editFood.setVisibility(View.VISIBLE);
                btn_cancelEdit.setVisibility(View.GONE);
                mLlMycollectionBottomDialog.setVisibility(View.GONE);
            }
        });
        btn_editFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (adapter.products.size()==0)
                {
                    mLlMycollectionBottomDialog.setVisibility(View.GONE);
                    btn_cancelEdit.setVisibility(View.GONE);
                    btn_editFood.setVisibility(View.VISIBLE);
                    Toast.makeText(ManageFoodDetailActivity.this, "请至少添加一项菜品", Toast
                            .LENGTH_SHORT).show();
                }
                else
                {
                    updateEditMode();
                    btn_cancelEdit.setVisibility(View.VISIBLE);
                    btn_editFood.setVisibility(View.GONE);
                }
            }
        });
        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteVideo();
            }
        });
        btn_selectAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectAllMain();
            }
        });
        btn_top.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for(int i=0;i<adapter.getProducts().size();i++)
                {
                    if (ManageFoodDetailActivity.detailFoodList.get(i).isSelect())
                    {
                        FoodDetail foodDetail = ManageFoodDetailActivity.detailFoodList.get(i);
                        ManageFoodDetailActivity.detailFoodList.remove(i);
                        ManageFoodDetailActivity.detailFoodList.add(0,foodDetail);
                        btn_editFood.setVisibility(View.VISIBLE);
                        btn_cancelEdit.setVisibility(View.GONE);
                        mLlMycollectionBottomDialog.setVisibility(View.GONE);
                        adapter.setCheckBoxVisible(View.GONE);
                    }
                }
                adapter.notifyDataSetChanged();
            }
        });
    }

    private void updateEditMode() {

        if (mLlMycollectionBottomDialog.getVisibility()==View.GONE) {
            btn_cancelEdit.setText("取消");
            mLlMycollectionBottomDialog.setVisibility(View.VISIBLE);

        } else {
            mLlMycollectionBottomDialog.setVisibility(View.GONE);

            clearAll();
        }
        adapter.setCheckBoxVisible(View.VISIBLE);
    }

    private void clearAll() {
        tv_selectNum.setText(String.valueOf(0));
        isSelectAll = false;
        tv_selectNum.setText("全选");
        setBtnBackground(0);

    }
    /**
     * 全选和反选
     */
    private void selectAllMain() {
        if (adapter == null) return;
        if (!isSelectAll) {
            for (int i = 0, j = adapter.getProducts().size(); i < j; i++) {
                adapter.getProducts().get(i).setSelect(true);
            }
            index = adapter.getProducts().size();
            btn_top.setClickable(false);
            btn_top.setBackgroundResource(R.drawable.btn_unusable);
            btn_delete.setEnabled(true);
            btn_selectAll.setText("取消全选");
            isSelectAll = true;
        } else {
            for (int i = 0, j = adapter.getProducts().size(); i < j; i++) {
                adapter.getProducts().get(i).setSelect(false);
            }
            index = 0;
            btn_top.setClickable(true);
            btn_top.setBackgroundResource(R.drawable.top);
            btn_delete.setEnabled(false);
            btn_selectAll.setText("全选");

            isSelectAll = false;
        }
        adapter.notifyDataSetChanged();
        setBtnBackground(index);
        tv_selectNum.setText(String.valueOf(index));
    }

    /**
     * 删除逻辑
     */
    private void deleteVideo() {
        if (index == 0){
            btn_delete.setEnabled(false);
            return;
        }
        final android.app.AlertDialog alertDialog = new android.app.AlertDialog.Builder(ManageFoodDetailActivity.this)
                .setTitle("用户提示")
                .setMessage("数据一经删除将无法恢复！！！")
                .setPositiveButton("残忍删除",null).setNegativeButton("再想想",null)
                .create();
        alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                //残忍删除
                Button btn_positive = alertDialog.getButton(android
                        .app.AlertDialog.BUTTON_POSITIVE);
                btn_positive.setTextColor(Color.GRAY);
                btn_positive.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {
                        ProjectTypeDetailManager detailManager =
                                new ProjectTypeDetailManager(ManageFoodDetailActivity.this,new LoginRegisterInformationHandle());
                        String class_id = detailFoodList.get(index)
                                .getItem_list().get(index)
                                .getClass_id();
                        String item_id = detailFoodList.get(index)
                                .getItem_list().get(index).getItem_id();
                        for (int i = adapter.getProducts().size(), j =0 ; i > j; i--) {
                            FoodDetail foodDetail = adapter.getProducts().get(i-1);
                            if (foodDetail.isSelect()) {
//                                detailManager.projectDetailManageDelete(class_id,item_id);
                                adapter.getProducts().remove(foodDetail);
                                index--;


                            }
                            if (index==0)
                            {
                                btn_cancelEdit.setVisibility(View.GONE);
                                btn_editFood.setVisibility(View.VISIBLE);
                            }
                        }
                        index = 0;
                        tv_selectNum.setText(String.valueOf(0));
                        setBtnBackground(index);
                        if (adapter.getProducts().size() == 0){
                            mLlMycollectionBottomDialog.setVisibility(View.GONE);
                        }
                        adapter.notifyDataSetChanged();
                        alertDialog.dismiss();
                    }
                });
                Button btn_negative=alertDialog.getButton(android.app.AlertDialog.BUTTON_NEGATIVE);
                btn_negative.setTextColor(Color.parseColor("#4862af"));
                btn_negative.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        alertDialog.dismiss();
                    }
                });
            }
        });
        alertDialog.show();
    }
//    @Override
//    protected void onResume() {
//        super.onResume();
//        initView();
//        initEvent();
//        manager.JsonCommit(dishId);
//        adapter.notifyDataSetChanged();
//    }

    public  void parseJSONWithGSON(String jsonData) {
        System.out.println("解析解析：  "+jsonData);
        //使用轻量级的Gson解析得到的json
        Gson gson = new Gson();
        detailFoodList = new ArrayList<>();
        foodDetail = gson.fromJson(jsonData, FoodDetail
                .class);
        item_list = foodDetail.getItem_list();
        for (int i = 0; i  < item_list.size(); i++)
        {

            foodDetail.setItem_list(item_list);
            detailFoodList.add(foodDetail);
        }

        for(int i=0;i<detailFoodList.size();i++){
            System.out.println("名字:" + detailFoodList.get(i).getItem_list()
                    .get(i).getName());
            System.out.println("json依次为"+detailFoodList.get(i));
        }
    }
public void addFood(View view)
{
    Intent intent = new Intent(ManageFoodDetailActivity.this,AddFoodActivity.class);
    startActivityForResult(intent,1);
}
    public void backType(View view)
    {
        finish();
    }

}
