package com.cottee.managerstore.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cottee.managerstore.R;
import com.cottee.managerstore.adapter.RecyclerviewAdapter;
import com.cottee.managerstore.bean.FoodDetail;
import com.cottee.managerstore.view.PopupMenuWindow;

import java.util.List;

public class ManageFoodDetailActivity extends Activity {

    private int dishId;
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
    private LinearLayoutManager linearLayoutManager;
    private Button btn_pop_menu;
    private PopupMenuWindow popupMenuWindow;
    private Button btn_cancelEdit;
    private LinearLayout linear_reminder;
    private TextView tv_title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_food_detail);
        dishId = getIntent().getIntExtra("position",0);
        dishName = getIntent().getStringExtra("positionName");
        initView();
        initData();
        initEvent();
    }
    private void initView() {
        String[] popmenu = new String[]{"添加菜品", "编辑菜品"};
        popupMenuWindow = new PopupMenuWindow(this,popmenu);
        addFoodActivity = new AddFoodActivity();
        tv_title = findViewById(R.id.tv_title);
        btn_pop_menu = findViewById(R.id.btn_pop_menu);
        btn_cancelEdit = findViewById(R.id.btn_cancelEdit);
        linear_reminder = findViewById(R.id.linear_reminder);
        recyclerView=  findViewById(R.id.recyclerView);
        btn_delete = findViewById(R.id.btn_delete);
        tv_selectNum = findViewById(R.id.tv_select_num);
        btn_selectAll = findViewById(R.id.btn_selectAll);

        mLlMycollectionBottomDialog = findViewById(R.id.ll_mycollection_bottom_dialog);
        btn_top = findViewById(R.id.btn_top);
        //设置并列2行的layoutManager
        layout = new StaggeredGridLayoutManager(2,
                StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layout);
        //设置adapter
        adapter = new RecyclerviewAdapter(AddFoodActivity.foodDetailList,
                new RecyclerviewAdapter.MyItemClickListener() {

                    @Override
                    public void onItemClick(int position,List<FoodDetail> foodDetailList) {
                        if (mLlMycollectionBottomDialog.getVisibility() == View.VISIBLE) {
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

        recyclerView.setAdapter(adapter);
        adapter.notifyAdapter(AddFoodActivity.foodDetailList,false);
        adapter.notifyDataSetChanged();

    }
    private void initData()
    {
        tv_title.setText(dishName);
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


                btn_pop_menu.setVisibility(View.VISIBLE);
                btn_cancelEdit.setVisibility(View.GONE);
                mLlMycollectionBottomDialog.setVisibility(View.GONE);
//               adapter.getItemId()
            }
        });
        btn_pop_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupMenuWindow.showLocation(R.id.btn_pop_menu);
                popupMenuWindow.setOnItemClickListener(new PopupMenuWindow.OnItemClickListener() {



                    @Override
                    public void onClick(PopupMenuWindow.MENUITEM item, String str) {
                        if (str=="添加菜品")
                        {
                            Intent intent = new Intent(ManageFoodDetailActivity.this,AddFoodActivity.class);
                            startActivityForResult(intent,1);
                        }
                        else if (str=="编辑菜品")
                        {

                            if (adapter.products.size()==0)
                            {
                                mLlMycollectionBottomDialog.setVisibility(View.GONE);
                                btn_cancelEdit.setVisibility(View.GONE);
                                btn_pop_menu.setVisibility(View.VISIBLE);
                                Toast.makeText(ManageFoodDetailActivity.this, "请至少添加一项菜品", Toast
                                        .LENGTH_SHORT).show();
                            }
                            else
                            {
                                updataEditMode();
                                btn_cancelEdit.setVisibility(View.VISIBLE);
                                btn_pop_menu.setVisibility(View.GONE);
                            }

                        }
                    }
                });

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
                    if (AddFoodActivity.foodDetailList.get(i).isSelect())
                    {
                        FoodDetail foodDetail = AddFoodActivity
                                .foodDetailList.get(i);
                        AddFoodActivity.foodDetailList.remove(i);
                        AddFoodActivity.foodDetailList.add(0,foodDetail);
                    }
                }
                adapter.notifyDataSetChanged();
            }
        });
    }

    private void updataEditMode() {

        if (mLlMycollectionBottomDialog.getVisibility()==View.GONE) {
            btn_cancelEdit.setText("取消");
//            btn_editType.setText("取消");
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
        final AlertDialog builder = new AlertDialog.Builder(this)
                .create();
        builder.show();
        if (builder.getWindow() == null) return;
        builder.getWindow().setContentView(R.layout.pop_user);//设置弹出框加载的布局
        TextView msg =  builder.findViewById(R.id.tv_msg);
        Button cancle =  builder.findViewById(R.id.btn_cancle);
        Button sure = builder.findViewById(R.id.btn_sure);
        if (msg == null || cancle == null || sure == null) return;

        if (index == 1) {
            msg.setText("删除后不可恢复，是否删除该条目？");
        } else {
            msg.setText("删除后不可恢复，是否删除这" + index + "个条目？");
        }
        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder.dismiss();
            }
        });
        sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = adapter.getProducts().size(), j =0 ; i > j; i--) {
                    FoodDetail foodDetail = adapter.getProducts().get(i-1);
                    if (foodDetail.isSelect()) {

                        adapter.getProducts().remove(foodDetail);
                        index--;
                    }
                    if (index==0)
                    {
                        btn_cancelEdit.setVisibility(View.GONE);
                        btn_pop_menu.setVisibility(View.VISIBLE);
                    }
                }
                index = 0;
                tv_selectNum.setText(String.valueOf(0));
                setBtnBackground(index);
                if (adapter.getProducts().size() == 0){
                    mLlMycollectionBottomDialog.setVisibility(View.GONE);
                }
                adapter.notifyDataSetChanged();
                builder.dismiss();
            }
        });
    }
    @Override
    protected void onResume() {
        super.onResume();
        initView();
        initEvent();
        adapter.notifyDataSetChanged();
    }
    public void backType(View view)
    {
        Toast.makeText(this, "返回按钮!", Toast.LENGTH_SHORT).show();
    }

}
