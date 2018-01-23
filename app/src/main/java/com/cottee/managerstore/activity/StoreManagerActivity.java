package com.cottee.managerstore.activity;

import android.content.Context;
import android.content.Intent;
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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cottee.managerstore.R;
import com.cottee.managerstore.activity1.EmployeeManageActivity;
import com.cottee.managerstore.activity1.ProjectManageActivity;
import com.cottee.managerstore.bean.StoreInfo;
import com.cottee.managerstore.properties.Properties;
import com.cottee.managerstore.utils.Utils;
import com.cottee.managerstore.view.PressureButton;
import com.cottee.managerstore.view.StoreStausPopupWindow;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.List;

public class StoreManagerActivity extends AppCompatActivity implements View.OnClickListener {
    private Toolbar tl_custom;
    private DrawerLayout dl_left;
    private ActionBarDrawerToggle mDrawerToggle;
    //    private TextView tv_storename_manager;
    private Button btn_storeManager;
    public Context mContext = StoreManagerActivity.this;
    private TextView tv_storeManager;
    private LinearLayout linear_changeStore;
    private StoreStausPopupWindow storeStausPopupWindow;
    private ImageButton imgbtn_storeStatus;
    private Button btn_releaseStore;
    private PressureButton pbtn_order;
    private TextView tv_storename_manager;
    private StoreInfo storeInfo;
    private int clicked = 1;
    private Button btntoprojectmanage;
    private Button btn_to_employee_manage;
    private int storeid;
    private Button btn_vipManager;
    private TextView tv_vipManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_storemanager );
        findView();
        tl_custom.setTitle( "" );//设置Toolbar标题
        tl_custom.setBackgroundColor( getResources().getColor( R.color.purplishblue ) );
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
//        storeInfo = (StoreInfo)intent.getSerializableExtra( "storeInfo" );
        storeid = intent.getIntExtra( "storeid", 0 );
//        tv_storename_manager.setText( storeInfo.getName() );
    }

    private void findView() {
        pbtn_order = findViewById( R.id.pbtn_order );
        btn_releaseStore = findViewById( R.id.btn_releaseStore );
        imgbtn_storeStatus = findViewById( R.id.imgbtn_storeStatus );
        tl_custom = findViewById( R.id.tl_custom );
        dl_left = findViewById( R.id.dl_left );
        btn_to_employee_manage = (Button) findViewById(R.id.btn_to_employee_manage);
        btn_to_employee_manage.setOnClickListener(this);
        linear_changeStore = findViewById( R.id.linear_changeStore );
        tv_storename_manager = (TextView) findViewById( R.id.tv_storename_manager );
        btn_storeManager = (Button) findViewById( R.id.btn_storeManager );
        btn_storeManager.setOnClickListener( this );
        tv_storeManager = (TextView) findViewById( R.id.tv_storeManager );
        tv_storeManager.setOnClickListener( this );
        btn_vipManager = (Button) findViewById( R.id.btn_vipManager );
        btn_vipManager.setOnClickListener( this );
        tv_vipManager = (TextView) findViewById( R.id.tv_vipManager );
        tv_vipManager.setOnClickListener( this );
        btntoprojectmanage = (Button) findViewById( R.id.btn_to_project_manage );
//        btn_to_employee_manage = (Button) findViewById(R.id.btn_to_employee_manage);
        pbtn_order.setOnClickListener( this );
        btn_releaseStore.setOnClickListener( this );
        imgbtn_storeStatus.setOnClickListener( this );
        tv_storeManager.setOnClickListener( this );
        linear_changeStore.setOnClickListener( this );
        btntoprojectmanage.setOnClickListener( this );
//        btn_to_employee_manage.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getStoreList();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_storeManager:
            case R.id.tv_storeManager:
                storeInfo = RegisterStoreActivity.storeList.get( storeid );
                Intent intent = new Intent( this, DetialInfomation.class );
                intent.putExtra( "storeInfo", storeInfo );
                startActivity( intent );
                break;
            case R.id.btn_vipManager:
            case R.id.tv_vipManager:
                startActivity( new Intent( this, VIPManagerActivity.class ) );
                break;
            case R.id.linear_changeStore:
                startActivity( new Intent( this, RegisterStoreActivity.class ) );
                finish();
                overridePendingTransition( R.anim.left_in, R.anim.right_out );
                break;
            case R.id.imgbtn_storeStatus:
                clicked++;
                if (clicked % 2 == 0) {
                    view.setBackgroundResource( R.mipmap.openthedoor );
                    ScaleAnimation scaleAnimation = new ScaleAnimation( 0.8f, 1.3f,
                            0.8f, 1.3f, Animation.RELATIVE_TO_PARENT, 0f, Animation.RELATIVE_TO_PARENT, 0f );
                    scaleAnimation.setDuration( 200 );
                    view.startAnimation( scaleAnimation );
                } else {
                    view.setBackgroundResource( R.mipmap.closethedoor );
                    ScaleAnimation scaleAnimation = new ScaleAnimation( 0.8f, 1.3f,
                            0.8f, 1.3f, Animation.RELATIVE_TO_PARENT, 0f, Animation.RELATIVE_TO_PARENT, 0f );
                    scaleAnimation.setDuration( 200 );
                    view.startAnimation( scaleAnimation );
                }
                break;
            case R.id.btn_releaseStore:
                Toast.makeText( mContext, "发布店铺", Toast.LENGTH_SHORT ).show();
                break;
            case R.id.pbtn_order:
                Toast.makeText( mContext, "下单", Toast.LENGTH_SHORT ).show();
            case R.id.btn_to_project_manage:
                Intent intentOne = new Intent( StoreManagerActivity.this, ProjectManageActivity.class );
                startActivity( intentOne );
                break;
            case R.id.btn_to_employee_manage:
                Intent intentTwo = new Intent( StoreManagerActivity.this, EmployeeManageActivity.class );
                startActivity( intentTwo );
                break;
            default:
                break;
        }
    }

    private List<StoreInfo> getStoreList() {
        new Thread() {
            @Override
            public void run() {
                Utils.sendToWebService( Properties.GET_STORE, new Callback() {
                    @Override
                    public void onFailure(Request request, IOException e) {

                    }

                    @Override
                    public void onResponse(Response response) throws IOException {
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
    public void manageMoney(View view)
    {
        startActivity(new Intent(StoreManagerActivity.this,ManageMoneyActivity.class));
    }


}
