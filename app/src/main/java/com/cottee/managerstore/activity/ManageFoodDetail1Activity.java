
package com.cottee.managerstore.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.opengl.Visibility;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.cottee.managerstore.R;
import com.cottee.managerstore.adapter.RecyclerviewAdapter;
import com.cottee.managerstore.bean.FoodDetail;
import com.cottee.managerstore.handle.LoginRegisterInformationHandle;
import com.cottee.managerstore.handle.OssHandler;
import com.cottee.managerstore.httputils.HttpUtilSession;
import com.cottee.managerstore.manage.LoginRegisterInformationManage;
import com.cottee.managerstore.manage.ProjectTypeDetailManager;
import com.cottee.managerstore.properties.Properties;
import com.cottee.managerstore.utils.ToastUtils;
import com.cottee.managerstore.utils.myt_oss.ConfigOfOssClient;
import com.cottee.managerstore.utils.myt_oss.DownloadUtils;
import com.cottee.managerstore.utils.myt_oss.InitOssClient;
import com.cottee.managerstore.utils.myt_oss_file.FileUtils;
import com.cottee.managerstore.view.SaleFoodDialog;
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

public class ManageFoodDetail1Activity extends Activity  {
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
    private Button btn_top;
    private LinearLayoutManager layout;
    private Button btn_editFood;
    private Button btn_cancelEdit;
    private LinearLayout linear_reminder;
    private TextView tv_title;
    public static String name;
    public static String description;
    public static FoodDetail foodDetail;
    private Context mContext = ManageFoodDetail1Activity.this;
    private ManageFoodDetail1Activity.ManageFoodDetailHandler handler = new ManageFoodDetail1Activity.ManageFoodDetailHandler();

    private ProjectTypeDetailManager detailManager;
    public static List<FoodDetail.ItemListBean> item_list;
    private Button btn_addFood;

    List<FoodDetail.ItemListBean> foodDetailList =new ArrayList<>();

    private SaleFoodDialog saleFoodDialog;

    private LinearLayout ll_allAdd;
    private TextView tv_sale_type;
    private PopupMenu mPopupMenu;
    private View img_arrow;
    private MenuInflater inflater;
    private String discount_sing="1";
    private String discount="0";
    private AlertDialog.Builder builder;
    private SwipeRefreshLayout refresh;
    private File cache_image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_food_detail1);

        InitOssClient.initOssClient(this, ConfigOfOssClient.TOKEN_ADDRESS, ConfigOfOssClient.ENDPOINT);
        dishId = getIntent().getStringExtra("position");
        dishName = getIntent().getStringExtra("positionName");
        sendRequestWithOkHttp();
        initView();
        initData();
        initEvent();
    }

    private void initData() {
        detailManager = new ProjectTypeDetailManager(mContext,new LoginRegisterInformationHandle());

    }
    private void initView() {
        refresh = findViewById(R.id.refresh);
        tv_sale_type = findViewById(R.id.tv_sale_type);
        btn_addFood = findViewById(R.id.btn_addFood);
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
        layout = new LinearLayoutManager(mContext);
        recyclerView.setLayoutManager(layout);
        handler.sendEmptyMessageDelayed(REFRESH_RESULT, 2000);
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


                                if (responeData.trim().equals("250")) {
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
                      adapter=new RecyclerviewAdapter(mContext, item_list,
                              new RecyclerviewAdapter.MyItemClickListener() {
                          @Override
                          public void onItemClick(FoodDetail.ItemListBean item, int position) {
                              if (mLlMycollectionBottomDialog.getVisibility() == View.VISIBLE) {

                                  boolean select = item.isSelect();
                                  if (select) {
                                      selectedList.remove(item);
                                      item.setSelect(false);
                                      if (selectedList.size() <= item_list.size()) {
                                          btn_selectAll.setText("全选");
                                          btn_top.setClickable(false);
                                      }
                                  } else {
                                      selectedList.add(item);
                                      item.setSelect(true);
                                      if (selectedList.size() == item_list.size()) {
                                          btn_selectAll.setText("取消全选");
                                          btn_top.setClickable(false);
                                      }

                                  }
                                  setBtnBackground(selectedList.size());
                                  tv_selectNum.setText(String.valueOf(selectedList.size()));
                                  adapter.notifyDataSetChanged();
                              } else {
                                  cache_image = new File(getCacheDir()
                                          , Base64.encodeToString(item_list.get(position).getPhoto().getBytes(), Base64.DEFAULT));
                                  DownloadUtils.downloadFileFromOss(cache_image
                                          , new OssHandler(mContext), ConfigOfOssClient.BUCKET_NAME, item_list.get(position).getPhoto());
                                  Intent intent = new Intent(mContext,
                                          ModifyFoodDetailActivity.class);
                                  intent.putExtra("position",position);
                                  intent.putExtra("foodname",item_list.get(position).getName());
                                  intent.putExtra("foodprice",item_list.get(position).getUnivalence());
                                  intent.putExtra("fooddesc",item_list.get(position).getDescription());
                                  if(cache_image!=null){
                                      intent.putExtra("photo", FileUtils.fileToByte(cache_image));
                                  }
                                  startActivity(intent);
                              }
                          }
                      });
//
//                    判断当前是否有数据，显示取消提示文字
                    if (adapter.getFoodDetailList().size() == 0) {
                        linear_reminder.setVisibility(View.VISIBLE);
                    } else {
                        linear_reminder.setVisibility(View.GONE);
                    }

                    if (recyclerView.getAdapter() == null) {
//                        adapter.setHasStableIds(true);
                        recyclerView.setAdapter(adapter);
                    }
                       adapter.notifyAdapter(item_list,false);
                        adapter.notifyDataSetChanged();
                    break;
                case REFRESH_RESULT:
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            sendRequestWithOkHttp();
                            refresh.setRefreshing(false);
                        }
                    }, 2000);
                    break;
            }
            super.handleMessage(msg);
        }
    }
    private void initEvent() {
        //数据刷新机制
       refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
           @Override
           public void onRefresh() {

              new Thread(new Runnable() {
                  @Override
                  public void run() {
                      if (adapter==null)
                      {
                            refresh.setRefreshing(false);
                      }
                      adapter=new RecyclerviewAdapter(mContext, item_list, new RecyclerviewAdapter.MyItemClickListener() {
                          @Override
                          public void onItemClick(FoodDetail.ItemListBean item, int position) {
                              if (mLlMycollectionBottomDialog.getVisibility() == View.VISIBLE) {
                                  boolean select = item.isSelect();
                                  if (select) {
                                      selectedList.remove(item);
                                      item.setSelect(false);
                                      if (selectedList.size() <= item_list.size()) {
                                          btn_selectAll.setText("全选");
                                          btn_top.setClickable(false);
                                      }
                                  } else {
                                      selectedList.add(item);
                                      item.setSelect(true);
                                      if (selectedList.size() == item_list.size()) {
                                          btn_selectAll.setText("取消全选");
                                          btn_top.setClickable(false);
                                      }

                                  }
                                  setBtnBackground(selectedList.size());
                                  tv_selectNum.setText(String.valueOf(selectedList.size()));
                                  adapter.notifyDataSetChanged();
                              } else {
                                  cache_image = new File(getCacheDir()
                                          , Base64.encodeToString(item_list.get(position).getPhoto().getBytes(), Base64.DEFAULT));
                                  DownloadUtils.downloadFileFromOss(cache_image
                                          , new OssHandler(mContext), ConfigOfOssClient.BUCKET_NAME, item_list.get(position).getPhoto());
                                  Intent intent = new Intent(mContext,
                                          ModifyFoodDetailActivity.class);
                                  intent.putExtra("position",position);
                                  intent.putExtra("foodname",item_list.get(position).getName());
                                  intent.putExtra("foodprice",item_list.get(position).getUnivalence());
                                  intent.putExtra("fooddesc",item_list.get(position).getDescription());
                                  if(cache_image!=null){
                                      intent.putExtra("photo", FileUtils.fileToByte(cache_image));
                                  }
                                  startActivity(intent);
                              }
                          }
                      });
                      recyclerView.setAdapter(adapter);
                      adapter.notifyDataSetChanged();
                      refresh.setRefreshing(false);
                  }
              }).start();
           }
       });
        btn_addFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(ManageFoodDetail1Activity.this,AddFoodActivity.class), 1);
            }
        });
        btn_cancelEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adapter.setCheckBoxVisible(View.GONE);
                btn_addFood.setVisibility(View.VISIBLE);
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
                    adapter.setCheckBoxVisible(View.VISIBLE);
                    btn_addFood.setVisibility(View.GONE);
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

            private FoodDetail.ItemListBean listBean;

            @Override
            public void onClick(View view) {
                int size=selectedList.size();
                for (int i=0;i<adapter.getFoodDetailList().size();i++)
                {
                    listBean = adapter
                            .getFoodDetailList().get(i);
                    if(!listBean.isSelect())
                    {
                        adapter.getFoodDetailList().remove(listBean);

                        size--;
                    }
                }
                tv_selectNum.setText(String.valueOf(0));
                setBtnBackground(size);
//                System.out.println("classId: "+bean.getClass_id());
//                System.out.println("item_id: " + bean.getItem_id());
                detailManager.projectDetailManageStick(listBean.getClass_id(),listBean.getItem_id());
//                detailManager.projectDetailManageDelete(listBean.getClass_id(), listBean.getItem_id());
//                for (int i = adapter.getFoodDetailList().size(), j =0 ; i > j; i--) {
//                    FoodDetail.ItemListBean listBean = adapter
//                            .getFoodDetailList().get(i - 1);
//                    if (!listBean.isSelect()) {
//
//                        adapter.getFoodDetailList().remove(listBean);
//                        size--;
//                    }
//                }
                size = 0;
                tv_selectNum.setText(String.valueOf(0));
                setBtnBackground(size);
                if (adapter.getFoodDetailList().size() == 0){
                    mLlMycollectionBottomDialog.setVisibility(View.GONE);
                }
                adapter.notifyDataSetChanged();
            }
        });
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
//            btn_editFood.setText("取消");
            mLlMycollectionBottomDialog.setVisibility(View.VISIBLE);

        } else {
            mLlMycollectionBottomDialog.setVisibility(View.GONE);

            clearAll();
        }
        adapter.setCheckBoxVisible(View.VISIBLE);
    }

    private void clearAll() {
        tv_selectNum.setText(String.valueOf(0));
        foodDetailList.clear();
        btn_selectAll.setText("全选");
        setBtnBackground(0);
    }

    /**
     * 全选和反选
     */
    private ArrayList<FoodDetail.ItemListBean> selectedList = new ArrayList<>();
    private void selectAllMain() {
        if (adapter == null)
            return;
        if (selectedList.size() == adapter.getItemCount()) {
            for (FoodDetail.ItemListBean item : adapter.getFoodDetailList()) {
                item.setSelect(false);
            }
            selectedList.clear();
            btn_delete.setEnabled(true);
            btn_selectAll.setText("全选");
        } else {
            selectedList.clear();
            for (FoodDetail.ItemListBean item : adapter.getFoodDetailList()) {
                item.setSelect(true);
                selectedList.add(item);
            }
            btn_delete.setEnabled(false);
            btn_selectAll.setText("取消全选");

        }
        int size = selectedList.size();
        adapter.notifyDataSetChanged();
        setBtnBackground(size);
        tv_selectNum.setText(String.valueOf(size));
    }

    /**
     * 删除逻辑
     */
    private void deleteVideo() {
        if (selectedList.size() == 0) {
            btn_delete.setEnabled(false);
            return;
        }
        final AlertDialog builder = new AlertDialog.Builder(this)
                .create();
        builder.show();
        if (builder.getWindow() == null) return;
        builder.getWindow().setContentView(R.layout.pop_user);//设置弹出框加载的布局
        TextView msg =  builder.findViewById(R.id.tv_msg);
        Button cancle = builder.findViewById(R.id.btn_cancle);
        Button sure =  builder.findViewById(R.id.btn_sure);
        if (msg == null || cancle == null || sure == null) return;

        if (selectedList.size() == 1) {
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

            private FoodDetail.ItemListBean listBean;

            @Override
            public void onClick(View v) {
                int size=selectedList.size();
                for (int i=0;i<adapter.getFoodDetailList().size();i++)
                {
                    listBean = adapter
                            .getFoodDetailList().get(i);
                    if(!listBean.isSelect())
                    {
                        adapter.getFoodDetailList().remove(listBean);
                        size--;
                    }
                }
                tv_selectNum.setText(String.valueOf(0));
                setBtnBackground(size);
                System.out.println("classId: "+listBean.getClass_id());
                System.out.println("item_id: " + listBean.getItem_id());
                detailManager.projectDetailManageDelete(listBean.getClass_id(), listBean.getItem_id());
//                for (int i = adapter.getFoodDetailList().size(), j =0 ; i > j; i--) {
//                    FoodDetail.ItemListBean listBean = adapter
//                            .getFoodDetailList().get(i - 1);
//                    if (!listBean.isSelect()) {
//
//                        adapter.getFoodDetailList().remove(listBean);
//                        size--;
//                    }
//                }
                size = 0;
                tv_selectNum.setText(String.valueOf(0));
                setBtnBackground(size);
                if (adapter.getFoodDetailList().size() == 0){
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
       sendRequestWithOkHttp();
    }
    public void backType(View view) {
        finish();
    }
}
