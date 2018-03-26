package com.cottee.managerstore.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.cottee.managerstore.R;

import java.util.List;

public class VIPSearchActivity extends Activity {

    private ListView lv_vip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_vipstandard );

        lv_vip = (ListView) findViewById( R.id.lv_vip );
        lv_vip.setAdapter( new BaseAdapter() {
            @Override
            public int getCount() {
                return 20;
            }

            @Override
            public Object getItem(int i) {
                return null;
            }

            @Override
            public long getItemId(int i) {
                return 0;
            }

            @Override
            public View getView(int i, View view, ViewGroup viewGroup) {
                view= View.inflate(VIPSearchActivity.this, R.layout.item_vip,null );
                return view;
            }
        } );
    }
    public void backs(View view){
        finish();
    }
}
