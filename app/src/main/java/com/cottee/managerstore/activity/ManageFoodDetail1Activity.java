
package com.cottee.managerstore.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
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
import com.cottee.managerstore.httputils.HttpUtilSession;
import com.cottee.managerstore.manage.LoginRegisterInformationManage;
import com.cottee.managerstore.manage.ProjectTypeDetailManager;
import com.cottee.managerstore.properties.Properties;
import com.cottee.managerstore.utils.ToastUtils;
import com.cottee.managerstore.view.SaleFoodDialog;
import com.cottee.managerstore.widget.ShapeLoadingDialog;
import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import static com.cottee.managerstore.properties.Properties
        .PROJECT_DETAIL_MANAGE_GET;

public class ManageFoodDetail1Activity extends Activity {
//    private static final int REFRESH_RESULT = 6;
//    private String  dishId;
//    private String dishName;
//    private RecyclerView recyclerView;
//    private RecyclerviewAdapter adapter;
//    private Button btn_delete;
//    private Button btn_selectAll;
//    private int index = 0;
//    private TextView tv_selectNum;
//    private LinearLayout mLlMycollectionBottomDialog;
//
//    private boolean isSelectAll = false;
//
//    private Button btn_top;
//    private StaggeredGridLayoutManager layout;
//    private Button btn_editFood;
//    private Button btn_cancelEdit;
//    private LinearLayout linear_reminder;
//    private TextView tv_title;
//    private ShapeLoadingDialog shapeLoadingDialog;
//    public static String name;
//    public static String description;
//    public  List<FoodDetail.ItemListBean> item_list;
//    public static FoodDetail foodDetail;
//    private Context mContext=ManageFoodDetail1Activity.this;
//  private ManageFoodDetail1Activity.ManageFoodDetailHandler handler= new ManageFoodDetail1Activity.ManageFoodDetailHandler();
//    public static List<FoodDetail> foodDetailList;
////    private SwipeRefreshLayout refresh;
//    private Button btn_scale;
//    private SaleFoodDialog saleFoodDialog;
//    private List<String> saleList;
//    private float sale1;
//    private String salePrice;
//    private String univalence;
//    private static float aFloat=1f;
//    private LinearLayout ll_addFood;
//    private File file;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_manage_food_detail1);
//        saleList = new ArrayList<>();
//        dishId = getIntent().getStringExtra("position");
//        dishName = getIntent().getStringExtra("positionName");
//        initView();
//        initEvent();
//    }
//    private void initView() {
//        ll_addFood = findViewById(R.id.ll_addFood);
//        btn_scale = findViewById(R.id.btn_sale);
////        refresh = findViewById(R.id.refresh);
//        tv_title = findViewById(R.id.tv_title);
//        btn_editFood = findViewById(R.id.btn_editFood);
//        btn_cancelEdit = findViewById(R.id.btn_cancelEdit);
//        linear_reminder = findViewById(R.id.linear_reminder);
//        recyclerView=  findViewById(R.id.recyclerView);
//        btn_delete = findViewById(R.id.btn_delete);
//        tv_selectNum = findViewById(R.id.tv_select_num);
//        btn_selectAll = findViewById(R.id.btn_selectAll);
//        tv_title.setText(dishName);
//        mLlMycollectionBottomDialog = findViewById(R.id.ll_mycollection_bottom_dialog);
//        btn_top = findViewById(R.id.btn_top);
//        //设置并列2行的layoutManager
//        layout = new StaggeredGridLayoutManager(2,
//                StaggeredGridLayoutManager.VERTICAL);
//        recyclerView.setLayoutManager(layout);
//
//        shapeLoadingDialog = new ShapeLoadingDialog.Builder(this)
//                .loadText("加载中...")
//                .build();
//        shapeLoadingDialog.show();
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                sendRequestWithOkHttp();
//
//            }
//        },2000);
//    }
//   private void initEvent()
//   {
////    refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
////        @Override
////        public void onRefresh() {
////            new Handler().postDelayed(new Runnable() {
////                @Override
////                public void run() {
////                    sendRequestWithOkHttp();
////                    refresh.setRefreshing(false);
////                }
////            },5000);
////
////        }
////    });
//  ll_addFood.setOnClickListener(new View.OnClickListener() {
//      @Override
//      public void onClick(View v) {
//          Intent intent = new Intent(ManageFoodDetail1Activity.this,AddFoodActivity.class);
//          startActivityForResult(intent,1);
//      }
//  });
//    btn_cancelEdit.setOnClickListener(new View.OnClickListener() {
//        @Override
//        public void onClick(View view) {
//            adapter.setCheckBoxVisible(View.GONE);
//            ll_addFood.setVisibility(View.VISIBLE);
//            btn_scale.setVisibility(View.GONE);
//            btn_editFood.setVisibility(View.VISIBLE);
//            btn_cancelEdit.setVisibility(View.GONE);
//            mLlMycollectionBottomDialog.setVisibility(View.GONE);
//        }
//    });
//    btn_editFood.setOnClickListener(new View.OnClickListener() {
//        @Override
//        public void onClick(View view) {
//            ll_addFood.setVisibility(View.GONE);
//            if (adapter.products.size()==0)
//            {
//                mLlMycollectionBottomDialog.setVisibility(View.GONE);
//                btn_cancelEdit.setVisibility(View.GONE);
//                btn_editFood.setVisibility(View.VISIBLE);
//                Toast.makeText(mContext, "请至少添加一项菜品", Toast
//                        .LENGTH_SHORT).show();
//            }
//            else
//            {
//                updateEditMode();
//                btn_cancelEdit.setVisibility(View.VISIBLE);
//                btn_editFood.setVisibility(View.GONE);
//                btn_scale.setVisibility(View.VISIBLE);
//
//            }
//        }
//    });
//    btn_scale.setOnClickListener(new View.OnClickListener() {
//        @Override
//        public void onClick(View v) {
//            saleFoodDialog = new SaleFoodDialog(mContext, new SaleFoodDialog.OnEditInputFinishedListener() {
//
//
//
//                @Override
//                public void editInputFinished(String sale) {
//                    aFloat = Float.parseFloat(sale);
//                    Toast.makeText(mContext, "折扣"+aFloat, Toast.LENGTH_SHORT).show();
//                }
//            });
//            saleFoodDialog.show();
//
//        }
//    });
//    btn_delete.setOnClickListener(new View.OnClickListener() {
//        @Override
//        public void onClick(View view) {
//            deleteVideo();
//        }
//    });
//    btn_selectAll.setOnClickListener(new View.OnClickListener() {
//        @Override
//        public void onClick(View view) {
//            selectAllMain();
//        }
//    });
//    btn_top.setOnClickListener(new View.OnClickListener() {
//        @Override
//        public void onClick(View view) {
//            for(int i=0;i<adapter.getFoodDetailList().size();i++)
//            {
//                if (foodDetailList.get(i).isSelect())
//                {
//                    FoodDetail foodDetail =foodDetailList.get(i);
//                    foodDetailList.remove(i);
//                    foodDetailList.add(0,foodDetail);
//                    btn_editFood.setVisibility(View.VISIBLE);
//                    btn_cancelEdit.setVisibility(View.GONE);
//                    mLlMycollectionBottomDialog.setVisibility(View.GONE);
//                    adapter.setCheckBoxVisible(View.GONE);
//                }
//            }
//            adapter.notifyDataSetChanged();
//        }
//    });
//}
//
//    private void sendRequestWithOkHttp() {
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//
//                HttpUtilSession.sendFoodDetailOkHttpRequest(ManageFoodDetail1Activity.this
//                        , Properties.PROJECT_DETAIL_MANAGE_GET_PATH
//                        ,dishId, new Callback() {
//                    @Override
//                    public void onFailure(Call call, IOException e) {
//                    }
//
//                    @Override
//                    public void onResponse(Call call, Response response) throws IOException {
//                        String responeData = response.body().string();
//                        System.out.println("ManageFoodDetail1111 Json:" + responeData);
//
//
//                        if(responeData.trim().equals("250")){
//                            shapeLoadingDialog.setDismiss();
//                            ToastUtils.showToast(ManageFoodDetail1Activity.this,"session无效效，正在重新登陆请稍等" );
//                            new LoginRegisterInformationManage(ManageFoodDetail1Activity.this,new LoginRegisterInformationHandle()).againLogin();
//                            /*sendRequestWithOkHttp();*/
//                        }
//                        else if (responeData.trim().equals("0")){
//
//                        }
//                        else {
//
//                            parseJSONWithGSON(responeData);
//                        }
//
//
//                    }
//
//
//
//
//                });
//            }
//        }).start();
//    }
//
//    private void parseJSONWithGSON(String responeData) {
//        System.out.println("解析解析：  "+responeData);
//        //使用轻量级的Gson解析得到的json
//        Gson gson = new Gson();
//        foodDetail = gson.fromJson(responeData, FoodDetail
//                .class);
//        System.out.println("MYT Type:" + foodDetail);
//
//        //控制台输出结果，便于查看
//        Message message = new Message();
//        message.what = PROJECT_DETAIL_MANAGE_GET;
//        message.obj = foodDetail;
//        handler.sendMessage(message);
//    }
//    public class ManageFoodDetailHandler extends Handler {
//        @Override
//        public void handleMessage(Message msg) {
//            switch (msg.what)
//            {
//                case PROJECT_DETAIL_MANAGE_GET:
//                    foodDetailList = new ArrayList<>();
//                    item_list = foodDetail.getItem_list();
//                    for (int i = 0; i < item_list.size(); i++) {
//                        foodDetail.setItem_list(item_list);
//                        foodDetailList.add(foodDetail);
//                    }
//                    for (int i=0;i<foodDetailList.size();i++)
//                    {
//                        univalence = foodDetailList.get(i)
//                                .getItem_list().get(i).getUnivalence();
//                        sale1 = Float.parseFloat(univalence)*0.5f;
//                        salePrice = String.valueOf(sale1);
//                        saleList.add(salePrice);
//                    }
//                    for (int i=0;i<saleList.size();i++)
//                    {
//                        System.out.println("折扣价格："+saleList.get(i));
//                    }
//                   adapter=new RecyclerviewAdapter(mContext, foodDetailList,saleList, new RecyclerviewAdapter.MyItemClickListener() {
//
//
//
//                       @Override
//                       public void onItemClick(int position, List<FoodDetail> foodDetailList) {
//                           if (mLlMycollectionBottomDialog.getVisibility() ==View.VISIBLE) {
//                               FoodDetail foodDetail =foodDetailList.get(position);
//                               boolean isSelect = foodDetail.isSelect();
//                               if (!isSelect) {
//                                   index++;
//                                   foodDetail.setSelect(true);
//                                   if (index == foodDetailList.size()) {
//                                       isSelectAll = true;
//                                       btn_selectAll.setText("取消全选");
//                                       btn_top.setClickable(false);
//                                   }
//
//                               } else {
//                                   foodDetail.setSelect(false);
//                                   index--;
//                                   isSelectAll = false;
//                                   btn_selectAll.setText("全选");
//                               }
//                               setBtnBackground(index);
//                               tv_selectNum.setText(String.valueOf(index));
//                               adapter.notifyDataSetChanged();
//                           }
//                           else {
//                               Intent intent = new Intent(mContext,FoodDetailActivity.class);
//                               intent.putExtra("position",position);
//                               startActivity(intent);
//                           }
//
//                       }
//
//                   });
////                    判断当前是否有数据，显示取消提示文字
//                    if (adapter.getFoodDetailList().size()==0)
//                    {
//                        linear_reminder.setVisibility(View.VISIBLE);
//                    }
//                    else {
//                        linear_reminder.setVisibility(View.GONE);
//                    }
//                    ((SimpleItemAnimator)recyclerView.getItemAnimator())
//                .setSupportsChangeAnimations(false);
//                    recyclerView.setAdapter(adapter);
//                    adapter.notifyAdapter(foodDetailList,false);
//                    adapter.notifyDataSetChanged();
//                    shapeLoadingDialog.setDismiss();
//                    break;
//                case REFRESH_RESULT:
//                    new Handler().postDelayed(new Runnable() {
//                        @Override
//                        public void run() {
//                            sendRequestWithOkHttp();
//                        }
//                    },2000);
////                    if (refresh.isRefreshing())
////                    {
////                        refresh.setRefreshing(false);
////                    }
//                    break;
//            }
//            super.handleMessage(msg);
//        }
//    }
//
//    /**
//     * 根据选择的数量是否为0来判断按钮的是否可点击.
//     *
//     * @param size
//     */
//    private void setBtnBackground(int size) {
//        if (size != 0) {
//            if (size==adapter.getFoodDetailList().size())
//            {
//                btn_top.setClickable(false);
//                btn_top.setBackgroundResource(R.drawable.btn_unusable);
//            }
//            if (size>0&&(size<adapter.getFoodDetailList().size())){
//                btn_top.setClickable(true);
//                btn_top.setBackgroundResource(R.drawable.top);
//            }
//            btn_delete.setBackgroundResource(R.drawable.button_shape);
//            btn_delete.setEnabled(true);
//            btn_delete.setTextColor(Color.WHITE);
//        } else {
//            btn_top.setClickable(false);
//            btn_delete.setBackgroundResource(R.drawable.button_shape);
//            btn_delete.setEnabled(false);
//            btn_delete.setTextColor(ContextCompat.getColor(this,R.color.color_4d4d4e));
//        }
//    }
//    private void updateEditMode() {
//
//        if (mLlMycollectionBottomDialog.getVisibility()==View.GONE) {
//            btn_cancelEdit.setText("取消");
//            mLlMycollectionBottomDialog.setVisibility(View.VISIBLE);
//
//        } else {
//            mLlMycollectionBottomDialog.setVisibility(View.GONE);
//
//            clearAll();
//        }
//        adapter.setCheckBoxVisible(View.VISIBLE);
//    }
//    private void clearAll() {
//        tv_selectNum.setText(String.valueOf(0));
//        isSelectAll = false;
//        tv_selectNum.setText("全选");
//        setBtnBackground(0);
//
//    }
//    /**
//     * 全选和反选
//     */
//    private void selectAllMain() {
//        if (adapter == null) return;
//        if (!isSelectAll) {
//            for (int i = 0, j = adapter.getFoodDetailList().size(); i < j; i++) {
//                adapter.getFoodDetailList().get(i).setSelect(true);
//            }
//            index = adapter.getFoodDetailList().size();
//            btn_top.setClickable(false);
//            btn_top.setBackgroundResource(R.drawable.btn_unusable);
//            btn_delete.setEnabled(true);
//            btn_selectAll.setText("取消全选");
//            isSelectAll = true;
//        } else {
//            for (int i = 0, j = adapter.getFoodDetailList().size(); i < j; i++) {
//                adapter.getFoodDetailList().get(i).setSelect(false);
//            }
//            index = 0;
//            btn_top.setClickable(true);
//            btn_top.setBackgroundResource(R.drawable.top);
//            btn_delete.setEnabled(false);
//            btn_selectAll.setText("全选");
//
//            isSelectAll = false;
//        }
//        adapter.notifyDataSetChanged();
//        setBtnBackground(index);
//        tv_selectNum.setText(String.valueOf(index));
//    }
//    /**
//     * 删除逻辑
//     */
//    private void deleteVideo() {
//        if (index == 0){
//            btn_delete.setEnabled(false);
//            return;
//        }
//        final android.app.AlertDialog alertDialog = new android.app.AlertDialog.Builder(mContext)
//                .setTitle("用户提示")
//                .setMessage("数据一经删除将无法恢复！！！")
//                .setPositiveButton("残忍删除",null).setNegativeButton("再想想",null)
//                .create();
//        alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
//            @Override
//            public void onShow(DialogInterface dialog) {
//                //残忍删除
//                Button btn_positive = alertDialog.getButton(android
//                        .app.AlertDialog.BUTTON_POSITIVE);
//                btn_positive.setTextColor(Color.GRAY);
//                btn_positive.setOnClickListener(new View.OnClickListener() {
//
//                    @Override
//                    public void onClick(View view) {
//                        ProjectTypeDetailManager detailManager =
//                                new ProjectTypeDetailManager(mContext,new LoginRegisterInformationHandle());
//                        String class_id = foodDetailList.get(index)
//                                .getItem_list().get(index)
//                                .getClass_id();
//                        String item_id = foodDetailList.get(index)
//                                .getItem_list().get(index).getItem_id();
//                        for (int i = adapter.getFoodDetailList().size(), j = 0; i > j; i--) {
//                            FoodDetail foodDetail = adapter.getFoodDetailList().get(i-1);
//                            if (foodDetail.isSelect()) {
//                                detailManager.projectDetailManageDelete(class_id,item_id);
//                                adapter.getFoodDetailList().remove(foodDetail);
//                                index--;
//
//
//                            }
//                            if (index==0)
//                            {
//                                btn_cancelEdit.setVisibility(View.GONE);
//                                btn_editFood.setVisibility(View.VISIBLE);
//                            }
//                        }
//                        index = 0;
//                        tv_selectNum.setText(String.valueOf(0));
//                        setBtnBackground(index);
//                        if (adapter.getFoodDetailList().size() == 0){
//                            mLlMycollectionBottomDialog.setVisibility(View.GONE);
//                        }
//                        adapter.notifyDataSetChanged();
//                        alertDialog.dismiss();
//                    }
//                });
//                Button btn_negative=alertDialog.getButton(android.app.AlertDialog.BUTTON_NEGATIVE);
//                btn_negative.setTextColor(Color.parseColor("#4862af"));
//                btn_negative.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        alertDialog.dismiss();
//                    }
//                });
//            }
//        });
//        alertDialog.show();
//    }
//
//
////    public void addFood(View view)
////    {
////        Intent intent = new Intent(ManageFoodDetail1Activity.this,AddFoodActivity.class);
////        startActivityForResult(intent,1);
////    }
//    public void backType(View view)
//    {
//        finish();
//    }

    private static final int REFRESH_RESULT = 6;
    private String dishId;
    private String dishName;
    private RecyclerView recyclerView;
    private RecyclerviewAdapter adapter;
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
    private ShapeLoadingDialog shapeLoadingDialog;
    public static String name;
    public static String description;
//    public List<FoodDetail.ItemListBean> item_list;
    public static FoodDetail foodDetail;
    private Context mContext = ManageFoodDetail1Activity.this;
    private ManageFoodDetail1Activity.ManageFoodDetailHandler handler = new ManageFoodDetail1Activity.ManageFoodDetailHandler();
    public static List<FoodDetail> foodDetailList;
    private ProjectTypeDetailManager detailManager;
    public static List<FoodDetail.ItemListBean> item_list;
    private LinearLayout ll_addFood;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_food_detail1);
        dishId = getIntent().getStringExtra("position");
        dishName = getIntent().getStringExtra("positionName");
        initView();
        initData();
        initEvent();
    }

    private void initData() {
        detailManager = new ProjectTypeDetailManager(mContext,new LoginRegisterInformationHandle());

    }

    private void initView() {
        ll_addFood = findViewById(R.id.ll_addFood);
        tv_title = findViewById(R.id.tv_title);
        btn_editFood = findViewById(R.id.btn_editFood);
        btn_cancelEdit = findViewById(R.id.btn_cancelEdit);
        linear_reminder = findViewById(R.id.linear_reminder);
        recyclerView = findViewById(R.id.recyclerView);
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
        shapeLoadingDialog = new ShapeLoadingDialog.Builder(this)
                .loadText("加载中...")
                .build();
        shapeLoadingDialog.show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                sendRequestWithOkHttp();

            }
        }, 2000);
    }

    private void initEvent() {
        ll_addFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(ManageFoodDetail1Activity.this,AddFoodActivity.class), 1);
            }
        });
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
                if (adapter.products.size() == 0) {
                    mLlMycollectionBottomDialog.setVisibility(View.GONE);
                    btn_cancelEdit.setVisibility(View.GONE);
                    btn_editFood.setVisibility(View.VISIBLE);
                    Toast.makeText(mContext, "请至少添加一项菜品", Toast
                            .LENGTH_SHORT).show();
                } else {
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
        //置顶
        btn_top.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<FoodDetail.ItemListBean> foodDetailList = adapter.getFoodDetailList();
                for (int i = 0; i < foodDetailList.size(); i++) {
                    if (foodDetailList.get(i).isSelect()) {
                        FoodDetail.ItemListBean foodDetail =
                                foodDetailList.get(i);
                        foodDetailList.remove(i);
                       foodDetailList.add(0,foodDetail);
                        detailManager.projectDetailManageStick(foodDetailList.get(i).getClass_id()
                               ,foodDetailList.get(i).getItem_id());
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

    private void sendRequestWithOkHttp() {
        new Thread(new Runnable() {
            @Override
            public void run() {

                HttpUtilSession.sendFoodDetailOkHttpRequest(ManageFoodDetail1Activity.this
                        , Properties.PROJECT_DETAIL_MANAGE_GET_PATH
                        , dishId, new Callback() {
                            @Override
                            public void onFailure(Call call, IOException e) {
                            }

                            @Override
                            public void onResponse(Call call, Response response) throws IOException {
                                String responeData = response.body().string();
                                System.out.println("ManageFoodDetail1111 Json:" + responeData);


                                if (responeData.trim().equals("250")) {
                                    shapeLoadingDialog.setDismiss();
                                    ToastUtils.showToast(ManageFoodDetail1Activity.this, "session无效效，正在重新登陆请稍等");
                                    new LoginRegisterInformationManage(ManageFoodDetail1Activity.this, new LoginRegisterInformationHandle()).againLogin();
                            /*sendRequestWithOkHttp();*/
                                } else if (responeData.trim().equals("0")) {

                                } else {

                                    parseJSONWithGSON(responeData);
                                }


                            }


                        });
            }
        }).start();
    }

    private void parseJSONWithGSON(String responeData) {
        System.out.println("解析解析：  " + responeData);
        //使用轻量级的Gson解析得到的json
        Gson gson = new Gson();
        foodDetail = gson.fromJson(responeData, FoodDetail
                .class);
        System.out.println("MYT Type:" + foodDetail);

        //控制台输出结果，便于查看
        Message message = new Message();
        message.what = PROJECT_DETAIL_MANAGE_GET;
        message.obj = foodDetail;


        handler.sendMessage(message);
    }

    public class ManageFoodDetailHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case PROJECT_DETAIL_MANAGE_GET:
                    foodDetailList = new ArrayList<>();

                    item_list = ((FoodDetail) msg.obj).getItem_list();

                    adapter = new RecyclerviewAdapter(mContext, item_list, new RecyclerviewAdapter.MyItemClickListener() {
                        @Override
                        public void onItemClick(FoodDetail.ItemListBean item,int position) {
                            if (mLlMycollectionBottomDialog.getVisibility()==View.VISIBLE)
                            {
                                if (item.isSelect()) {
                                    item.setSelect(false);
                                } else {
                                    item.setSelect(true);
                                }
                                adapter.notifyDataSetChanged();
                                boolean select = item.isSelect();
                                if (select)
                                {
                                    index++;
                                    item.setSelect(true);
                                    if (index== item_list.size())
                                    {
                                        isSelectAll=true;
                                        btn_selectAll.setText("取消全选");
                                        btn_top.setClickable(false);
                                    }
                                }
                                else {
                                    item.setSelect(false);
                                    index--;
                                    isSelectAll=false;
                                }
                                setBtnBackground(index);
                                tv_selectNum.setText(String.valueOf(index));
                                adapter.notifyDataSetChanged();
                            }
                           else {
                                Intent intent = new Intent(mContext,
                                        ModifyFoodDetailActivity.class);
                                intent.putExtra("position",position);
                                startActivity(intent);
                            }
                        }


                    });
//                    判断当前是否有数据，显示取消提示文字
                    if (adapter.getFoodDetailList().size() == 0) {
                        linear_reminder.setVisibility(View.VISIBLE);
                    } else {
                        linear_reminder.setVisibility(View.GONE);
                    }
                    ((SimpleItemAnimator) recyclerView.getItemAnimator())
                            .setSupportsChangeAnimations(false);
                    if (recyclerView.getAdapter() == null) {
                        recyclerView.setAdapter(adapter);
                    }
                       adapter.notifyAdapter(item_list,false);
                        adapter.notifyDataSetChanged();
                    shapeLoadingDialog.setDismiss();
                    break;
                case REFRESH_RESULT:
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            sendRequestWithOkHttp();
                        }
                    }, 2000);
//                    if (refresh.isRefreshing())
//                    {
//                        refresh.setRefreshing(false);
//                    }
                    break;
            }
            super.handleMessage(msg);
        }
    }

    /**
     * 根据选择的数量是否为0来判断按钮的是否可点击.
     *
     * @param size
     */
    private void setBtnBackground(int size) {
        if (size != 0) {
            if (size == adapter.getFoodDetailList().size()) {
                btn_top.setClickable(false);
                btn_top.setBackgroundResource(R.drawable.btn_unusable);
            }
            if (size > 0 && (size < adapter.getFoodDetailList().size())) {
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
            btn_delete.setTextColor(ContextCompat.getColor(this, R.color.color_4d4d4e));
        }
    }

    private void updateEditMode() {

        if (mLlMycollectionBottomDialog.getVisibility() == View.GONE) {
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
        if (adapter == null)
            return;
        if (!isSelectAll) {
            for (int i = 0, j = adapter.getFoodDetailList().size(); i < j; i++) {
                adapter.getFoodDetailList().get(i).setSelect(true);
            }
            index = adapter.getFoodDetailList().size();
            btn_top.setClickable(false);
            btn_top.setBackgroundResource(R.drawable.btn_unusable);
            btn_delete.setEnabled(true);
            btn_selectAll.setText("取消全选");
            isSelectAll = true;
        } else {
            for (int i = 0, j = adapter.getFoodDetailList().size(); i < j; i++) {
                adapter.getFoodDetailList().get(i).setSelect(false);
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
        if (index == 0) {
            btn_delete.setEnabled(false);
            return;
        }
        new AlertDialog.Builder(mContext)
                .setTitle("用户提醒！")
                .setMessage("数据一经删除将无法恢复！")
                .setNegativeButton("再想想", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setPositiveButton("残忍删除", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                       for(int i=adapter.getFoodDetailList().size(),j=0;i>j;i--)
                       {
                           FoodDetail.ItemListBean listBean = adapter
                                   .getFoodDetailList().get(i - 1);
                           if (listBean.isSelect())
                           {
                               detailManager.projectDetailManageDelete(item_list.get(index).getClass_id()
                                       ,item_list.get(index).getItem_id());
                               adapter.getFoodDetailList().remove(i);

                               index--;
                               adapter.notifyAdapter(item_list,false);
                               adapter.notifyDataSetChanged();
                           }
                           if (index==0)
                           {
                               btn_cancelEdit.setVisibility(View.GONE);
                               btn_editFood.setVisibility(View.VISIBLE);
                           }
                               index=0;
                           tv_selectNum.setText(String.valueOf(0));
                        setBtnBackground(index);
                        if (adapter.getFoodDetailList().size() == 0) {
                            mLlMycollectionBottomDialog.setVisibility(View.GONE);
                        }
                        adapter.notifyDataSetChanged();
                        dialog.dismiss();
                    }

                    }
                })
                .create().show();
    }


//    public void addFood(View view) {
////        Intent intent = new Intent(ManageFoodDetail1Activity.this, AddFoodActivity.class);
//        startActivityForResult(new Intent(ManageFoodDetail1Activity.this,AddFoodActivity.class), 1);
//    }

    public void backType(View view) {
        finish();
    }
}
