package com.cottee.managerstore.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.cottee.managerstore.R;
import com.cottee.managerstore.activity1.BossLoginActivity;
import com.cottee.managerstore.adapter.StoreListviewAdapter;
import com.cottee.managerstore.bean.StoreInfo;

import java.util.ArrayList;
import java.util.List;

public class RegisterStoreActivity extends Activity {

    private ListView lv_registerStore;

    private StoreListviewAdapter storeListviewAdapter;
    public static List<StoreInfo> storeList = new ArrayList<>();
    private TextView tv_nostore;
    private Context mContext=RegisterStoreActivity.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        requestWindowFeature( Window.FEATURE_NO_TITLE );
        setContentView( R.layout.activity_registerstore );
        lv_registerStore = findViewById( R.id.lv_registerStore );
        Button button = (Button) findViewById( R.id.btn_user_exit );
        tv_nostore = (TextView) findViewById( R.id.tv_noStore );
        getStoreList();
        button.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sp = getSharedPreferences( "userLogin", Context.MODE_PRIVATE );
                SharedPreferences.Editor editor = sp.edit();
                editor.clear();
                editor.commit();

                Intent intent = new Intent( RegisterStoreActivity.this, BossLoginActivity.class );
                startActivity( intent );
                finish();


            }
        } );
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

    @Override
    protected void onResume() {
        super.onResume();
        getStoreList();
    }

    private void getStoreList() {
        if (storeList.size() == 0) {
            tv_nostore.setVisibility( View.VISIBLE );
        }else {
            tv_nostore.setVisibility( View.GONE );
            lv_registerStore.setVisibility( View.VISIBLE );
            storeListviewAdapter = new StoreListviewAdapter( this, storeList );
            lv_registerStore.setAdapter( storeListviewAdapter );
            storeListviewAdapter.notifyDataSetChanged();
        }
    }

    public void addRregisterStore(View view) {
        Intent intent = new Intent( this, RegisterStoreInfoActivity.class );
        startActivity( intent );
        overridePendingTransition( R.anim.right_in, R.anim.left_out );
    }
}
    