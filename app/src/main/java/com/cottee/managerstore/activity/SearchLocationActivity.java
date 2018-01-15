package com.cottee.managerstore.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.cottee.managerstore.R;
import com.cottee.managerstore.adapter.SearchLocationAdapter;
import com.cottee.managerstore.bean.SearchLocation;
import com.cottee.managerstore.utils.Utils;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.List;

/**
 * Created by user on 2017/11/16.
 */

public class SearchLocationActivity extends Activity {

    private static final String KEY = "7KLBZ-SVMRD-PBA4J-PR24M-TKJW3-C2BX2";
    AutoCompleteTextView actv;
    private Button btn_backToAddressStore;
    private String city;
    private String keyword;
    public List<SearchLocation.UserBean> userBeanList;
    private SearchLocationAdapter adapter;
    private ListView lv_location;

    int pageIndext = 1;
    private EditText et_city;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_searchlocation );

        Intent intent = getIntent();
        city = intent.getStringExtra( "city" );


        findView();


    }

    private void findView() {

        lv_location = (ListView) findViewById( R.id.lv_location );
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.anim_item);
        LayoutAnimationController controller = new LayoutAnimationController(animation);
        controller.setDelay(0.5f);
        controller.setOrder(LayoutAnimationController.ORDER_NORMAL);
        lv_location.setLayoutAnimation(controller);
        btn_backToAddressStore = (Button) findViewById( R.id.btn_backToAddressStore );
        actv = (AutoCompleteTextView) findViewById( R.id.auto );
        btn_backToAddressStore.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                overridePendingTransition(R.anim.left_in, R.anim.right_out);
            }
        } );
        et_city = (EditText) findViewById( R.id.et_city );

        et_city.setText( city );
        et_city.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                et_city.getText().clear();
            }
        } );
    }

    @Override
    protected void onResume() {
        super.onResume();
        actv.addTextChangedListener( new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                city=et_city.getText().toString().trim();
                keyword = actv.getText().toString().trim();
                if(city.isEmpty()){
                    Toast.makeText( SearchLocationActivity.this,"请输入城市",Toast.LENGTH_SHORT ).show();
                    return;
                }
                if (keyword.isEmpty()) {
                    return;
                }

                final String address = "http://apis.map.qq.com/ws/place/v1/search?boundary=region" +
                        "(" + city + ",0)" +
                        "&keyword=" + keyword + "&page_size=50&page_index=" + pageIndext +
                        "&orderby=_distance&key="
                        + KEY;

                new Thread() {
                    @Override
                    public void run() {
                        if (keyword != null) {
                            Utils.sendToWebService( address, new Callback() {
                                @Override
                                public void onFailure(Request request, IOException e) {
                                    Looper.prepare();
                                    Toast.makeText( SearchLocationActivity.this, "搜索失败，请检查网络",
                                            Toast
                                                    .LENGTH_SHORT ).show();
                                    Looper.loop();
                                }

                                @Override
                                public void onResponse(Response response) throws IOException {
                                    String s = response.body().string();
                                    if (s.isEmpty()) {
                                        return;
                                    }
                                    userBeanList = Utils.handleLocationResponse( s );
                                    adapter = new SearchLocationAdapter( SearchLocationActivity
                                            .this, R.layout.item_location, userBeanList );
                                    runOnUiThread( new Runnable() {
                                        @Override
                                        public void run() {
                                            lv_location.setAdapter( adapter );
                                            lv_location.setOnItemClickListener( new AdapterView.OnItemClickListener() {
                                                @Override
                                                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                                    SearchLocation.UserBean userBean = userBeanList.get( i );
                                                    float lat = userBean.getLocation().getLat();
                                                    float lng = userBean.getLocation().getLng();
                                                    float[] floats = {lat, lng};
                                                    Intent intent = new Intent();
                                                    intent.putExtra( "locations", floats );
                                                    intent.putExtra( "address", userBean
                                                            .getAddress() );
                                                    setResult( RESULT_OK, intent );
                                                    finish();
                                                    overridePendingTransition(R.anim.left_in, R.anim.right_out);
                                                }
                                            } );
                                        }
                                    } );
                                }
                            } );

                        }
                    }
                }.start();
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (adapter != null) {
                    adapter.notifyDataSetChanged();
                    adapter.notifyDataSetInvalidated();
                }

            }
        } );

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
