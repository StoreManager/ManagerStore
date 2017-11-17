package com.tencent.mapsdk.raster.demo;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

import com.tencent.map.geolocation.TencentLocation;
import com.tencent.map.geolocation.TencentLocationListener;
import com.tencent.map.geolocation.TencentLocationManager;
import com.tencent.map.geolocation.TencentLocationRequest;
import com.tencent.mapsdk.raster.model.BitmapDescriptorFactory;
import com.tencent.mapsdk.raster.model.Circle;
import com.tencent.mapsdk.raster.model.CircleOptions;
import com.tencent.mapsdk.raster.model.LatLng;
import com.tencent.mapsdk.raster.model.Marker;
import com.tencent.mapsdk.raster.model.MarkerOptions;
import com.tencent.tencentmap.mapsdk.map.MapActivity;
import com.tencent.tencentmap.mapsdk.map.MapView;
import com.tencent.tencentmap.mapsdk.map.TencentMap;

public class ShowMyLocationActivity extends MapActivity implements 
TencentLocationListener{
	private ImageButton btnShowLocation;

	private TencentLocationManager locationManager;
	private TencentLocationRequest locationRequest;
	private Marker myLocation;
	private Circle accuracy;
	private MapView mapView;
	private TencentMap tencentMap;

	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.activity_show_location);
		init();
		bindListener();
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		locationManager.removeUpdates(this);
	}

	protected void init() {
		btnShowLocation = (ImageButton)findViewById(R.id.btn_show_location);
		locationManager = TencentLocationManager.getInstance(this);
		locationRequest = TencentLocationRequest.create();
		mapView = (MapView)findViewById(R.id.map);
		tencentMap = mapView.getMap();
	}

	protected void bindListener() {
		btnShowLocation.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				int error = locationManager.requestLocationUpdates(
						locationRequest,ShowMyLocationActivity.this);
				switch (error) {
				case 0:
					Log.e("location", "成功注册监听器");
					break;
				case 1:
					Log.e("location", "设备缺少使用腾讯定位服务需要的基本条件");
					break;
				case 2:
					Log.e("location", "manifest 中配置的 key 不正确");
					break;
				case 3:
					Log.e("location", "自动加载libtencentloc.so失败");
					break;

				default:
					break;
				}
			}
		});
	}

	@Override
	public void onLocationChanged(TencentLocation arg0, int arg1, String arg2) {
		// TODO Auto-generated method stub
		if (arg1 == TencentLocation.ERROR_OK) {
			LatLng latLng = new LatLng(arg0.getLatitude(), arg0.getLongitude());
			if (myLocation == null) {
				myLocation = tencentMap.addMarker(new MarkerOptions().
						position(latLng).
						icon(BitmapDescriptorFactory.fromResource(R.mipmap.navigation)).
						anchor(0.5f, 0.5f));
			}
			if (accuracy == null) {
				accuracy = tencentMap.addCircle(new CircleOptions().
						center(latLng).
						radius((double)arg0.getAccuracy()).
						fillColor(0x440000ff).
						strokeWidth(0f));
			}
			myLocation.setPosition(latLng);
			myLocation.setRotation(arg0.getBearing()); //仅当定位来源于gps有效，或者使用方向传感器
			accuracy.setCenter(latLng);
			accuracy.setRadius(arg0.getAccuracy());
		} else {
			Log.e("location", "location failed:" + arg2);
		}
	}

	@Override
	public void onStatusUpdate(String arg0, int arg1, String arg2) {
		// TODO Auto-generated method stub
		String desc = "";
		switch (arg1) {
			case STATUS_DENIED:
				desc = "权限被禁止";
				break;
			case STATUS_DISABLED:
				desc = "模块关闭";
				break;
			case STATUS_ENABLED:
				desc = "模块开启";
				break;
			case STATUS_GPS_AVAILABLE:
				desc = "GPS可用，代表GPS开关打开，且搜星定位成功";
				break;
			case STATUS_GPS_UNAVAILABLE:
				desc = "GPS不可用，可能 gps 权限被禁止或无法成功搜星";
				break;
			case STATUS_LOCATION_SWITCH_OFF:
				desc = "位置信息开关关闭，在android M系统中，此时禁止进行wifi扫描";
				break;
			case STATUS_UNKNOWN:
				break;
		}
		Log.e("location", "location status:" + arg0 + ", " + arg2 + " " + desc);
	}
}
