package com.cottee.managerstore.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cottee.managerstore.R;
import com.cottee.managerstore.view.PressureButton;
import com.cottee.managerstore.view.StoreStausPopupWindow;

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
    private TextView tv_storeStatus;
    private Button btn_releaseStore;
    private PressureButton pbtn_order;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_storemanager );
        findView();
        tl_custom.setTitle("");//设置Toolbar标题
        tl_custom.setBackgroundColor(getResources().getColor(R.color.purplishblue));
//        tl_custom.setTitleTextColor(getResources().getColor(R.color.white)); //设置标题颜色
        setSupportActionBar(tl_custom);
        getSupportActionBar().setHomeButtonEnabled(true); //设置返回键可用
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mDrawerToggle = new ActionBarDrawerToggle(this, dl_left,
                tl_custom,R.string.drawer_open,R.string.drawer_close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        };
        mDrawerToggle.syncState();
        dl_left.setDrawerListener(mDrawerToggle);
        Intent intent = getIntent();
        String storename = intent.getStringExtra( "storename" );



//        tv_storename_manager.setText( storename + "管理" );
    }

    private void findView() {
        pbtn_order = findViewById(R.id.pbtn_order);
        btn_releaseStore = findViewById(R.id.btn_releaseStore);
        tv_storeStatus = findViewById(R.id.tv_storeStatus);
        tl_custom = findViewById(R.id.tl_custom);
        dl_left = findViewById(R.id.dl_left);
        linear_changeStore = findViewById(R.id.linear_changeStore);
//        tv_storename_manager = (TextView) findViewById( R.id.tv_storename_manager );
        btn_storeManager = (Button) findViewById( R.id.btn_storeManager );
        btn_storeManager.setOnClickListener( this );
        tv_storeManager = (TextView) findViewById( R.id.tv_storeManager );
        pbtn_order.setOnClickListener(this);
        btn_releaseStore.setOnClickListener(this);
        tv_storeStatus.setOnClickListener(this);
        tv_storeManager.setOnClickListener( this );
        linear_changeStore.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_storeManager:
            case R.id.tv_storeManager:
                Intent intent = new Intent( this, DetialInfomation.class );
                startActivity( intent );
                finish();
                break;
            case R.id.linear_changeStore:
                startActivity(new Intent(this,RegisterStoreActivity.class));
                finish();
                overridePendingTransition(R.anim.left_in,R.anim.right_out);
                break;
            case R.id.tv_Status:
                storeStausPopupWindow = new
                        StoreStausPopupWindow(this);
                storeStausPopupWindow.getContentView().setOnFocusChangeListener(new View.OnFocusChangeListener() {



                    @Override
                    public void onFocusChange(View view, boolean hasFocus) {
                        if (!hasFocus)
                            storeStausPopupWindow.dismiss();

                    }
                });
                //设置默认获取焦点
                storeStausPopupWindow.setFocusable(true);
//以某个控件的x和y的偏移量位置开始显示窗口
                storeStausPopupWindow.showAsDropDown(tv_storeStatus, 0, 0);
//如果窗口存在，则更新
                storeStausPopupWindow.update();
                break;
            case R.id.btn_releaseStore:
                Toast.makeText(mContext, "发布店铺", Toast.LENGTH_SHORT).show();
                break;
            case R.id.pbtn_order:
                Toast.makeText(mContext, "下单", Toast.LENGTH_SHORT).show();
            default:
                break;
        }
    }

}
