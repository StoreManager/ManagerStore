package com.cottee.managerstore.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.cottee.managerstore.R;
import com.cottee.managerstore.bean.LocationInfo;
import com.tencent.lbssearch.object.param.SearchParam;
import com.tencent.map.geolocation.TencentLocation;
import com.tencent.map.geolocation.TencentLocationListener;
import com.tencent.map.geolocation.TencentLocationManager;
import com.tencent.map.geolocation.TencentLocationRequest;
import com.tencent.mapsdk.raster.model.BitmapDescriptorFactory;
import com.tencent.mapsdk.raster.model.LatLng;
import com.tencent.mapsdk.raster.model.Marker;
import com.tencent.mapsdk.raster.model.MarkerOptions;
import com.tencent.tencentmap.mapsdk.map.MapView;
import com.tencent.tencentmap.mapsdk.map.TencentMap;

import java.util.List;

public class StoreAddressActivity extends Activity implements TencentLocationListener {
    public static final int REQUEST_LOCATION = 1;
    private TencentLocationManager locationManager;
    private TencentLocationRequest request;
    private MapView mapview;
    private TextView tv_location;
    private TencentMap tencentMap;
    private Marker marker;
    private SearchParam.Region region;
    private Button bt_search;
    private String city;
    private Button btn_back;
    private Button btn_checkout;
    public static List<LocationInfo> locationDataList;
    private float[] locations;
    private String address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_store_address );

        findView();
        sendRequest();

        tencentMap = mapview.getMap();
        //设置缩放级别
        tencentMap.setZoom( 17 );
    }

    private void sendRequest() {
        boolean networkState = detect( StoreAddressActivity.this );
        if (!networkState)//判断网络
        {
            Toast.makeText( this, "定位失败，请连接网络", Toast.LENGTH_SHORT ).show();
        } else {
            locationManager = TencentLocationManager.getInstance( this );
            locationManager.setCoordinateType( TencentLocationManager.COORDINATE_TYPE_GCJ02 );
            request = TencentLocationRequest.create();
            request.setInterval( 3000 );
            request.setRequestLevel( TencentLocationRequest.REQUEST_LEVEL_ADMIN_AREA );
            int error = locationManager.requestLocationUpdates( request, this );
            if (error == 0) {
                System.out.println( "地址获取成功" );
            }
        }
    }

    private void findView() {
        mapview = (MapView) findViewById( R.id.mapview );
        tv_location = (TextView) findViewById( R.id.tv_location );
        bt_search = (Button) findViewById( R.id.bt_search );
        btn_back = (Button) findViewById( R.id.btn_back );
        btn_checkout = (Button) findViewById( R.id.btn_checkout );

        btn_back.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        } );
        btn_checkout.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.putExtra( "address",address );
                setResult( RESULT_OK,intent );
                finish();
            }
        } );
        bt_search.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent( StoreAddressActivity.this, SearchLocationActivity.class );
                intent.putExtra( "city",city );
                startActivityForResult( intent,REQUEST_LOCATION );
                overridePendingTransition(R.anim.right_in, R.anim.left_out);
            }
        } );
    }

    @Override
    public void onLocationChanged(TencentLocation location, int i, String s) {
        if (i == TencentLocation.ERROR_OK) {
            //经度
            double longitude = location.getLongitude();
            //纬度
            double latitude = location.getLatitude();
            LatLng locationlatLng = new LatLng( latitude, longitude );
            tencentMap.setCenter( locationlatLng );

            marker = tencentMap.addMarker( new MarkerOptions()
                    .position( locationlatLng )
                    .anchor( 0.5f, 0.5f )
                    .icon( BitmapDescriptorFactory
                            .defaultMarker() )
                    .draggable( true ) );

            address = location.getProvince() + location.getCity() + location.getDistrict()
                    .toString();
            city = location.getCity().toString();
            if (location.getStreet().toString().equals( "Unknown" )) {
                // 未得到街道地址
                tv_location.setText( address );
            } else {
                // 得到街道地址
                tv_location.setText( address + location.getStreet().toString() );
            }
            // 停止定位
            stopLocation();

        } else {
            // 停止定位
            stopLocation();
            // 定位失败
            Toast.makeText( this, "定位失败，请连接网络", Toast.LENGTH_SHORT )
                    .show();
        }
    }

    @Override
    public void onStatusUpdate(String s, int i, String s1) {

    }

    public void stopLocation() {
        locationManager.removeUpdates( this );
    }

    public static boolean detect(Activity act) {

        ConnectivityManager manager = (ConnectivityManager) act
                .getApplicationContext().getSystemService(
                        Context.CONNECTIVITY_SERVICE );
        if (manager == null) {
            return false;
        }
        NetworkInfo networkinfo = manager.getActiveNetworkInfo();
        if (networkinfo == null || !networkinfo.isAvailable()) {
            return false;
        }
        return true;
    }

    @Override
    protected void onDestroy() {
        mapview.onDestroy();
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        mapview.onPause();
        super.onPause();
    }

    @Override
    protected void onResume() {
        mapview.onResume();
        super.onResume();
    }

    @Override
    protected void onStop() {
        mapview.onStop();
        super.onStop();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_LOCATION:
                if (resultCode == RESULT_OK) {
                    float[] locations = data.getFloatArrayExtra( "locations" );
                    address = data.getStringExtra( "address" );
                    float lat = locations[0];
                    float lot = locations[1];
                    LatLng latLng = new LatLng( lat, lot );
                    tencentMap.setCenter(latLng);
                    marker = tencentMap.addMarker( new MarkerOptions()
                            .position( latLng)
                            .anchor( 0.5f, 0.5f )
                            .icon( BitmapDescriptorFactory
                                    .defaultMarker() )
                            .draggable( true ) );
                        tv_location.setText( address );
                }
                break;

            default:
                break;
        }
    }
}
