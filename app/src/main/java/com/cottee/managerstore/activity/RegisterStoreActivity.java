package com.cottee.managerstore.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.cottee.managerstore.R;
import com.cottee.managerstore.activity1.BossLoginActivity;
import com.cottee.managerstore.adapter.StoreListviewAdapter;
import com.cottee.managerstore.bean.StoreInfo;
import com.cottee.managerstore.properties.Properties;
import com.cottee.managerstore.utils.Utils;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class RegisterStoreActivity extends AppCompatActivity {
    private Toolbar tl_custom;
    private DrawerLayout dl_left;
    private ActionBarDrawerToggle mDrawerToggle;
    private LinearLayout linear_changePassword;
    private LinearLayout linear_exitLogin;
    private LinearLayout linear_helpCenter;
    private LinearLayout linear_feedbackProblem;
    private ListView lv_registerStore;

    private StoreListviewAdapter storeListviewAdapter;
    public static List<StoreInfo> storeList = new ArrayList<>();
    private TextView tv_nostore;
    private Context mContext = RegisterStoreActivity.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
//        requestWindowFeature( Window.FEATURE_NO_TITLE );
        setContentView( R.layout.activity_registerstore );
        initView();
        initEvent();
        tl_custom.setTitle( "Sweet商铺管理系统" );//设置Toolbar标题
        tl_custom.setBackgroundColor( getResources().getColor( R.color.purplishblue ) );
        tl_custom.setTitleTextColor( getResources().getColor( R.color.white ) ); //设置标题颜色
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
//        Button button = (Button) findViewById( R.id.btn_user_exit );
        tv_nostore = (TextView) findViewById( R.id.tv_noStore );
        getStoreList();
//        button.setOnClickListener( new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                SharedPreferences sp = getSharedPreferences( "userLogin", Context.MODE_PRIVATE );
//                SharedPreferences.Editor editor = sp.edit();
//                editor.clear();
//                editor.commit();
//
//                Intent intent = new Intent( RegisterStoreActivity.this, BossLoginActivity.class );
//                startActivity( intent );
//                finish();
//
//
//            }
//        } );
        lv_registerStore.setOnItemClickListener( new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, final int position, long l) {

                AlertDialog.Builder builder = new AlertDialog.Builder( mContext );
                builder.setMessage( "确定删除此店铺吗？" );
                builder.setCancelable( true );
                builder.setPositiveButton( "确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        storeList.remove( position );
                        onResume();
                    }
                } );
                builder.setNegativeButton( "取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        onResume();
                    }
                } );
                builder.show();
            }
        } );
    }

    private void initView() {
        lv_registerStore = findViewById( R.id.lv_registerStore );
        tl_custom = findViewById( R.id.tl_custom );
        dl_left = findViewById( R.id.dl_left );
        linear_changePassword = findViewById( R.id.linear_changePassword );
        linear_exitLogin = findViewById( R.id.linear_exitLogin );
        linear_helpCenter = findViewById( R.id.linear_helpCenter );
        linear_feedbackProblem = findViewById( R.id.linear_feedbackProblem );
    }

    private void initEvent() {
//       修改密码
        linear_changePassword.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText( RegisterStoreActivity.this, "修改密码！", Toast
                        .LENGTH_SHORT ).show();
            }
        } );
//       退出登录
        linear_exitLogin.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sp = getSharedPreferences( "userLogin", Context.MODE_PRIVATE );
                SharedPreferences.Editor editor = sp.edit();
                editor.clear();
                editor.commit();

                Intent intent = new Intent( RegisterStoreActivity.this, BossLoginActivity.class );
                startActivity( intent );
                finish();
                overridePendingTransition( R.anim.left_in, R.anim.right_out );
            }
        } );
//    帮助中心
        linear_helpCenter.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText( RegisterStoreActivity.this, "帮助中心", Toast
                        .LENGTH_SHORT ).show();
            }
        } );
//       问题反馈
        linear_feedbackProblem.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText( RegisterStoreActivity.this, "问题反馈", Toast
                        .LENGTH_SHORT ).show();
            }
        } );
    }

    @Override
    protected void onResume() {
        super.onResume();
        getStoreList();
    }

    private void getStoreList() {
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
                        storeList = Utils.handleStoreResponse( s );
                        if (storeList.size() == 0) {
                            tv_nostore.setVisibility( View.VISIBLE );
                        } else {
                            runOnUiThread( new Runnable() {
                                @Override
                                public void run() {
                                    tv_nostore.setVisibility( View.GONE );
                                    lv_registerStore.setVisibility( View.VISIBLE );
                                    storeListviewAdapter = new StoreListviewAdapter(
                                            mContext, storeList );
                                    lv_registerStore.setAdapter( storeListviewAdapter );
                                    storeListviewAdapter.notifyDataSetChanged();
                                }
                            } );

                        }
                    }
                } );

            }
        }.start();

    }

    public void addRregisterStore(View view) {
        Intent intent = new Intent( this, RegisterStoreInfoActivity.class );
        startActivity( intent );
        overridePendingTransition( R.anim.right_in, R.anim.left_out );
    }


    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent home = new Intent( Intent.ACTION_MAIN );
            home.setFlags( Intent.FLAG_ACTIVITY_CLEAR_TOP );
            home.addCategory( Intent.CATEGORY_HOME );
            startActivity( home );
            return true;
        }
        return super.onKeyDown( keyCode, event );
    }


}
    