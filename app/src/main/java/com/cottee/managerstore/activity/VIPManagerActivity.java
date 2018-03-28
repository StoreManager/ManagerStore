package com.cottee.managerstore.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.cottee.managerstore.R;
import com.cottee.managerstore.adapter.StoreListviewAdapter;
import com.cottee.managerstore.adapter.VIPStandardAdapter;
import com.cottee.managerstore.bean.VIPStandard;
import com.cottee.managerstore.httputils.HttpUtilSession;
import com.cottee.managerstore.properties.Properties;
import com.cottee.managerstore.utils.Utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;


/**
 * Created by user on 2018/1/16.
 */

public class VIPManagerActivity extends Activity implements View.OnClickListener, PopupMenu.OnMenuItemClickListener   {

    private Button btn_back_to_manager;
    private View btn_menu;
    private Button btn_searchVIP;
    private TextView tv_empty;
    private ListView lv_vipStandard;
    public static List<VIPStandard> vipStandardList=new ArrayList<VIPStandard>();
    private VIPStandardAdapter vipStandardAdapter;
    private PopupMenu mMenu;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_vipmanager_layout );

        btn_back_to_manager = (Button) findViewById( R.id.btn_back_to_manager );
        btn_back_to_manager.setOnClickListener( this );

        btn_menu = findViewById( R.id.btn_menu );
        if(btn_menu!=null){
            btn_menu.setOnClickListener( this );
            mMenu = new PopupMenu( this, btn_menu );
            mMenu.getMenuInflater().inflate( R.menu.popupmenu, mMenu.getMenu() );
            mMenu.setOnMenuItemClickListener( this );
        }

        tv_empty = (TextView) findViewById( R.id.tv_empty );
        lv_vipStandard = (ListView) findViewById( R.id.lv_vipStandard );

    }

    @Override
    protected void onResume() {
        super.onResume();
        HttpUtilSession.sendSessionOkHttpRequest( this,
                Properties.VIP_STANDARD_SHOW, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String s = response.body().string();
                if (s.isEmpty()) {
                    return;
                }
                vipStandardList= Utils.handleVIPResponse( s );
                if (vipStandardList.size() == 0) {
                    tv_empty.setVisibility( View.VISIBLE );
                } else {
                    runOnUiThread( new Runnable() {
                        @Override
                        public void run() {
                            tv_empty.setVisibility( View.GONE );
                            lv_vipStandard.setVisibility( View.VISIBLE );
                            vipStandardAdapter = new VIPStandardAdapter( VIPManagerActivity.this,
                                    vipStandardList );
                            lv_vipStandard.setAdapter( vipStandardAdapter );
                            vipStandardAdapter.notifyDataSetChanged();
                        }
                    } );

                }
            }
        } );

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_back_to_manager:
                finish();
                break;
            case R.id.btn_menu:
                mMenu.show();
                break;
            default:
                break;
        }
    }
    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        if (menuItem.getItemId() == R.id.action_addLevel) {  //根据ItemId进行判断。
            Intent intent = new Intent( this, AddVIPStandardActivity.class );
            intent.putExtra( "level",vipStandardList.size() );
            String min_num = "0";
            if(vipStandardList.size()>0){
                min_num=vipStandardList.get( vipStandardList.size() - 1 ).getMin_num();
            }
            intent.putExtra( "min" ,min_num);
            startActivity( intent );
            return true;
        }else if(menuItem.getItemId()==R.id.action_search){
            startActivity( new Intent( this,VIPSearchActivity.class ) );
            return true;
        }
        return false;
    }

}
