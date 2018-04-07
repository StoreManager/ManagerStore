package com.cottee.managerstore.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cottee.managerstore.R;
import com.cottee.managerstore.activity1.EmployeeManageActivity;
import com.cottee.managerstore.activity1.OrdersManageActivity;
import com.cottee.managerstore.activity1.ProjectManageActivity;
import com.cottee.managerstore.bean.StoreInfo;
import com.cottee.managerstore.httputils.HttpUtilSession;
import com.cottee.managerstore.properties.Properties;
import com.cottee.managerstore.utils.Utils;
import com.cottee.managerstore.view.StoreStausPopupWindow;
import com.cottee.managerstore.widget.ZoomHoverView;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;

public class StoreManagerActivity extends AppCompatActivity implements View.OnClickListener {
    private Toolbar tl_custom;
    private DrawerLayout dl_left;
    private ActionBarDrawerToggle mDrawerToggle;
    private ImageView btn_storeManager;
    public Context mContext = StoreManagerActivity.this;
    private TextView tv_storeManager;
    private LinearLayout linear_changeStore;
    private StoreStausPopupWindow storeStausPopupWindow;
    private ImageButton imgbtn_storeStatus;
    private Button pbtn_order;
    private TextView tv_storename_manager;
    private StoreInfo storeInfo;
    private int clicked = 1;
    private ImageView btntoprojectmanage;
    private ImageView btn_to_employee_manage;
    private int storeid;
    private ImageView btn_vipManager;
    private TextView tv_vipManager;
    private TextView tv_to_project_manage;
    private TextView tv_to_employee_manage;
    private ImageView btn_moneyManage;
    private TextView tv_moneyManage;
    private ImageView btn_orderManage;
    private TextView tv_orderManage;

    private boolean isOpen=false;
    private ZoomHoverView mZoomHoverView;
    private LinearLayout store_main_money_info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_storemanager );
        findView();
        initAnim();
        tl_custom.setTitle("");//设置Toolbar标题
        tl_custom.setBackgroundColor( getResources().getColor( R.color.main_close_black ) );
//        tl_custom.setTitleTextColor(getResources().getColor(R.color.white)); //设置标题颜色
        setSupportActionBar( tl_custom );
        getSupportActionBar().setHomeButtonEnabled( true ); //设置返回键可用
        getSupportActionBar().setDisplayHomeAsUpEnabled( true );
        mDrawerToggle = new ActionBarDrawerToggle( this, dl_left,
                tl_custom, R.string.drawer_open, R.string.drawer_close ) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened( drawerView );
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed( drawerView );
            }
        };
        mDrawerToggle.syncState();
        dl_left.setDrawerListener( mDrawerToggle );
        Intent intent = getIntent();
        storeInfo = (StoreInfo)intent.getSerializableExtra( "storeInfo" );
        storeid = intent.getIntExtra( "locationStoreID", 0 );
    }

    private void findView() {
        mZoomHoverView = (ZoomHoverView) findViewById(R.id.zoom_hover_view_1);
        pbtn_order = findViewById( R.id.pbtn_order );
        imgbtn_storeStatus = findViewById( R.id.imgbtn_storeStatus );
        tl_custom = findViewById( R.id.tl_custom );
        dl_left = findViewById( R.id.dl_left );
        store_main_money_info = (LinearLayout) findViewById(R.id.store_main_money_info);
        btn_to_employee_manage =  findViewById(R.id.imv_icon_five);
        tv_to_employee_manage = findViewById(R.id.tv_item_five);
        btn_moneyManage = findViewById(R.id.imv_icon_three);
        tv_moneyManage = findViewById(R.id.tv_item_three);
        btn_orderManage = findViewById(R.id.imv_icon_four);
        tv_orderManage = findViewById(R.id.tv_item_four);
        btn_to_employee_manage.setOnClickListener(this);
        linear_changeStore = findViewById( R.id.linear_changeStore );
        tv_storename_manager = (TextView) findViewById( R.id.tv_storename_manager );
        btn_storeManager =  findViewById( R.id.imv_icon );
        btn_storeManager.setOnClickListener( this );
        tv_storeManager = (TextView) findViewById( R.id.tv_item );
        tv_storeManager.setOnClickListener( this );
        btn_vipManager =  findViewById( R.id.imv_icon_six );
        btn_vipManager.setOnClickListener( this );
        tv_vipManager = (TextView) findViewById( R.id.tv_item_six );
        btntoprojectmanage =  findViewById( R.id.imv_icon_two );
        tv_to_project_manage = findViewById(R.id.tv_item_two);
        pbtn_order.setOnClickListener( this );
        imgbtn_storeStatus.setOnClickListener( this );
        linear_changeStore.setOnClickListener( this );
        btntoprojectmanage.setOnClickListener( this );
        btn_orderManage.setOnClickListener(this);
    }

    private void initAnim() {
        mZoomHoverView.setOnButtonView(new ZoomHoverView.OnButtonSelectedListener() {
            @Override
            public void onButtonSelected(View view, int viewId) {
                switch(view.getId()){
                    case R.id.ll_item_one:
                        Intent intent = new Intent( StoreManagerActivity.this, DetialInfomation.class );
                        storeInfo=RegisterStoreActivity.storeList.get( storeid );
//                intent.putExtra( "storeInfo", storeInfo );
                        intent.putExtra( "storeId",storeid );
                        startActivity( intent );
                        break;
                    case R.id.ll_item_two:
                        Intent intentOne = new Intent( StoreManagerActivity.this, ProjectManageActivity.class );
                        startActivity( intentOne );
                        break;
                    case R.id.ll_item_three:
                        startActivity(new Intent(StoreManagerActivity.this,ManageMoneyActivity.class));
                        break;
                    case R.id.ll_item_four:
                        startActivity(new Intent(StoreManagerActivity.this,OrdersManageActivity.class));
                        break;
                    case R.id.ll_item_five:
                        Intent intentTwo = new Intent( StoreManagerActivity.this, EmployeeManageActivity.class );
                        startActivity( intentTwo );
                        break;
                    case R.id.ll_item_six:
                        startActivity( new Intent( StoreManagerActivity.this, VIPManagerActivity.class ) );
                        break;

                }
            }
        });


        mZoomHoverView.setOnZoomAnimatorListener(new ZoomHoverView.OnZoomAnimatorListener() {
            @Override
            public void onZoomInStart(View view) {
                view.setBackground(getResources().getDrawable(R.drawable.holo_light_frame));
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    view.setElevation(15.0f);
                }
            }

            @Override
            public void onZoomInEnd(View view) {

            }

            @Override
            public void onZoomOutStart(View view) {

            }

            @Override
            public void onZoomOutEnd(View view) {
                view.setBackgroundColor(getResources().getColor(R.color.white));

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    view.setElevation(0);
                }

            }
        });
    }





    @Override
    protected void onResume() {
        super.onResume();
        mZoomHoverView.setOnItemView(new ZoomHoverView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(View view) {
                mZoomHoverView.setCloseAnimation(view);
            }
        });
        /*getStoreList();*/
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.linear_changeStore:
                startActivity( new Intent( this, RegisterStoreActivity.class ) );
                finish();
                overridePendingTransition( R.anim.left_in, R.anim.right_out );
                break;
            case R.id.imgbtn_storeStatus:
                clicked++;
                if (clicked % 2 == 0) {
                    isOpen=true;
                    pbtn_order.setEnabled(true);
                    view.setBackgroundResource( R.mipmap.openthedoor );
                    ScaleAnimation scaleAnimation = new ScaleAnimation( 0.8f, 1.3f,
                            0.8f, 1.3f, Animation.RELATIVE_TO_PARENT, 0f, Animation.RELATIVE_TO_PARENT, 0f );
                    scaleAnimation.setDuration( 200 );
                    view.startAnimation( scaleAnimation );
                    store_main_money_info.setBackgroundColor(getResources().getColor(R.color.babyblue));
                    btn_storeManager.setBackgroundResource(R.mipmap.myt_store_open);
                    tv_storeManager.setTextColor(getResources().getColor(R.color.babyblue));
                    btntoprojectmanage.setBackgroundResource(R.mipmap.myt_restaurant_open);
                    tv_to_project_manage.setTextColor(getResources().getColor(R.color.babyblue));
                    btn_to_employee_manage.setBackgroundResource(R.mipmap.myt_emp_open);
                    tv_to_employee_manage.setTextColor(getResources().getColor(R.color.babyblue));
                    btn_vipManager.setBackgroundResource(R.mipmap.myt_vip_open);
                    tv_vipManager.setTextColor(getResources().getColor(R.color.babyblue));
                    btn_moneyManage.setBackgroundResource(R.mipmap.myt_money_open);
                    tv_moneyManage.setTextColor(getResources().getColor(R.color.babyblue));
                    btn_orderManage.setBackgroundResource(R.mipmap.myt_dingdan_open);
                    tv_orderManage.setTextColor(getResources().getColor(R.color.babyblue));
                    tl_custom.setBackgroundColor( getResources().getColor( R.color.purplishblue ) );
                    pbtn_order.setBackgroundResource(R.color.purplishblue);

                } else {
                    isOpen=false;
                    pbtn_order.setEnabled(false);
                    view.setBackgroundResource( R.mipmap.closethedoor );
                    ScaleAnimation scaleAnimation = new ScaleAnimation( 0.8f, 1.3f,
                            0.8f, 1.3f, Animation.RELATIVE_TO_PARENT, 0f, Animation.RELATIVE_TO_PARENT, 0f );
                    scaleAnimation.setDuration( 200 );
                    view.startAnimation( scaleAnimation );
                    store_main_money_info.setBackgroundColor(getResources().getColor(R.color.gray));
                    btn_storeManager.setBackgroundResource(R.mipmap.myt_store_close);
                    tv_storeManager.setTextColor(getResources().getColor(R.color.gray));
                    btntoprojectmanage.setBackgroundResource(R.mipmap.myt_restaurant_close);
                    tv_to_project_manage.setTextColor(getResources().getColor(R.color.gray));
                    btn_to_employee_manage.setBackgroundResource(R.mipmap.myt_emp_close);
                    tv_to_employee_manage.setTextColor(getResources().getColor(R.color.gray));
                    btn_vipManager.setBackgroundResource(R.mipmap.myt_vip_close);
                    tv_vipManager.setTextColor(getResources().getColor(R.color.gray));
                    btn_moneyManage.setBackgroundResource(R.mipmap.myt_money_close);
                    tv_moneyManage.setTextColor(getResources().getColor(R.color.gray));
                    btn_orderManage.setBackgroundResource(R.mipmap.myt_dingdan_close);
                    tv_orderManage.setTextColor(getResources().getColor(R.color.gray));
                    tl_custom.setBackgroundColor( getResources().getColor( R.color.main_close_black ) );
                    pbtn_order.setBackgroundResource(R.color.main_close_black);
                }
                break;

            case R.id.pbtn_order:
                if (isOpen)
                {
//                    Toast.makeText(mContext, "Close!Now", Toast.LENGTH_SHORT)
//                            .show();
                }
                else
                {
                    Toast.makeText( mContext, "下单", Toast.LENGTH_SHORT ).show();
                }

                break;

            /*case R.id.btn_orderManage:
//                startActivity(new Intent(StoreManagerActivity.this,OrderManageMainActivity.class));
                break;*/
            default:
                break;
        }
    }

    private List<StoreInfo> getStoreList() {
        new Thread() {
            @Override
            public void run() {
                HttpUtilSession.sendSessionOkHttpRequest( mContext, Properties.GET_STORE, new okhttp3.Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {

                    }

                    @Override
                    public void onResponse(Call call, okhttp3.Response response) throws IOException {
                        String s = response.body().string();
                        if (s.isEmpty()) {
                            return;
                        }
                        RegisterStoreActivity.storeList = Utils.handleStoreResponse( s );
                    }
                } );
            }
        }.start();
        return RegisterStoreActivity.storeList;
    }

}
