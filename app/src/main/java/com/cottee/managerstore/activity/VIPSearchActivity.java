package com.cottee.managerstore.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.cottee.managerstore.R;
import com.cottee.managerstore.adapter.SearchLocationAdapter;
import com.cottee.managerstore.adapter.StoreListviewAdapter;
import com.cottee.managerstore.adapter.VIPAdapter;
import com.cottee.managerstore.bean.SearchLocation;
import com.cottee.managerstore.bean.VIP;
import com.cottee.managerstore.bean.VIPStandard;
import com.cottee.managerstore.httputils.HttpUtilSession;
import com.cottee.managerstore.properties.Properties;
import com.cottee.managerstore.utils.ToastUtils;
import com.cottee.managerstore.utils.Utils;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

public class VIPSearchActivity extends Activity {

    private ListView lv_vip;
    private AutoCompleteTextView autoCompleteTextView;
    private List<VIP> vips = new ArrayList<>();
    private VIPAdapter vipAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_vipstandard );

        autoCompleteTextView = (AutoCompleteTextView) findViewById( R.id.auto );

        lv_vip = (ListView) findViewById( R.id.lv_vip );
    }

    @Override
    protected void onResume() {
        super.onResume();
        autoCompleteTextView.addTextChangedListener( new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String text = autoCompleteTextView.getText().toString().trim();
                if (text.isEmpty()) {
                    return;
                }
                String last_id = "0";
                if (vips.size() > 0) {
                    last_id = vips.get( vips.size() - 1 ).getUser_id();
                }
                HttpUtilSession.sendVIPOkHttpRequest( VIPSearchActivity.this, Properties
                        .VIP_PATH, autoCompleteTextView.getText().toString().trim(), last_id, new
                        okhttp3.Callback() {
                            @Override
                            public void onFailure(Call call, IOException e) {

                            }

                            @Override
                            public void onResponse(Call call, okhttp3.Response response) throws IOException {
                                String s = response.body().string();
                                if (s.equals( 250 )) {
                                    return;
                                }
                                if (s.isEmpty()) {
                                    return;
                                }
                                vips = Utils.handleSearvhVIPResponse( s );
                                if (vips.size() > 0) {
                                    runOnUiThread( new Runnable() {
                                        @Override
                                        public void run() {
                                            lv_vip.setVisibility( View.VISIBLE );
                                            vipAdapter = new VIPAdapter( VIPSearchActivity.this, vips );
                                            lv_vip.setAdapter( vipAdapter );
                                            vipAdapter.notifyDataSetChanged();
                                        }
                                    } );
                                }
                            }
                        } );

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        } );
    }
    public void backs(View view) {
        finish();
    }
}
