package com.cottee.managerstore.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.cottee.managerstore.R;

/**
 * Created by user on 2018/1/16.
 */

public class VIPManagerActivity extends Activity implements View.OnClickListener {

    private Button btn_back_to_manager;
    private String[] data={"Apple","Banana","Orange","Watermelon","Pear","Grape","Pineapple","Strawberry","Cherry","Mango"};//要储存的数据

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_vipmanager_layout );

        btn_back_to_manager = (Button) findViewById( R.id.btn_back_to_manager );
        btn_back_to_manager.setOnClickListener( this );
        ListView listView = (ListView) findViewById( R.id.lv_vip );

        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,data);//把每一行内容的XML文件与数据给ArrayAdapter
        listView.setAdapter(adapter);//把ArrayAdapter给ListView
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_back_to_manager:
                finish();
                break;
            default:
                break;
        }
    }
}
