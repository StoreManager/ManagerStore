package com.tencent.mapsdk.raster.demo;

import java.util.ArrayList;
import java.util.List;

import android.Manifest;
import android.app.ListActivity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends ListActivity {
	
	public AppInfo appInfo;
	
	private static final DemoInfo[] demos = {
		new DemoInfo(R.string.demo_label_basemap, 
				R.string.demo_desc_basemap, BasicMapActivity.class),
		new DemoInfo(R.string.demo_label_control, 
				R.string.demo_desc_control, MapControlActivity.class),
		new DemoInfo(R.string.demo_label_itemized_overlay, 
				R.string.demo_desc_itemized_overlay, ItemizedOverlayActivity.class),
		new DemoInfo(R.string.demo_label_uisettings,
				R.string.demo_desc_uisettings, MapUisettingsActivity.class),
		new DemoInfo(R.string.demo_label_marker_attr, 
				R.string.demo_desc_marker_attr, MarkerAttributeActivity.class),
		new DemoInfo(R.string.demo_label_marker, 
				R.string.demo_desc_marker, MarkerActivity.class),
		new DemoInfo(R.string.demo_label_map_polyline,
				R.string.demo_desc_map_polyline, PolylineActivity.class),
		new DemoInfo(R.string.demo_label_map_polygon,
				R.string.demo_desc_map_polygon, PolygonActivity.class),
		new DemoInfo(R.string.demo_label_map_circle,
				R.string.demo_desc_map_circle, CircleActivity.class),
		new DemoInfo(R.string.demo_label_mapfragment, 
				R.string.demo_desc_mapfragment, MapFragmentActivity.class),
		new DemoInfo(R.string.demo_label_cust_mapfragment, 
				R.string.demo_desc_cust_mapfragment, CustMapFragmentActivity.class),
		new DemoInfo(R.string.demo_label_projection, 
				R.string.demo_desc_projection, ProjectionActivity.class),
		new DemoInfo(R.string.demo_label_show_my_location, 
				R.string.demo_desc_show_my_location, ShowMyLocationActivity.class)
	};
	private DemoListAdapter demoListAdapter;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
        	appInfo = AppInfo.getAppInfo(this.getPackageManager().
    				getApplicationInfo(getPackageName(), PackageManager.GET_META_DATA).
    				metaData.getString("TencentMapSDK"));
		} catch (Exception e) {
			// TODO: handle exception
			Toast toast = Toast.makeText(this, "未找到key", Toast.LENGTH_LONG);
			toast.show();
		}
		getListView().setBackgroundColor(0xffffffff);
        demoListAdapter = new DemoListAdapter();
        setListAdapter(demoListAdapter);

        if (Build.VERSION.SDK_INT >= 23) {
			List<String> permissions = new ArrayList<String>();
			if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
					!= PackageManager.PERMISSION_GRANTED) {
				permissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
			}
			if (checkSelfPermission(Manifest.permission.READ_PHONE_STATE)
					!= PackageManager.PERMISSION_GRANTED) {
				permissions.add(Manifest.permission.READ_PHONE_STATE);
			}
			if (checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION)
					!= PackageManager.PERMISSION_GRANTED) {
				permissions.add(Manifest.permission.ACCESS_COARSE_LOCATION);
			}
			if (permissions.size() > 0) {
				requestPermissions(permissions.toArray(
						new String[permissions.size()]), 0);
			}
		}
    } 

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
    	// TODO Auto-generated method stub
    	super.onListItemClick(l, v, position, id);
    	
    	Intent intent = new Intent(this, demos[position].demoActivityClass);
    	startActivity(intent);
    }
    
    private class DemoListAdapter extends BaseAdapter {
    	
    	public DemoListAdapter () {
    		super();
    	}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return demos.length;
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return demos[position];
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			ViewHolder holder;
			if (convertView == null) {
				convertView = View.inflate(MainActivity.this, R.layout.demo_list_item, null);
				holder = new ViewHolder();
				holder.tvLable = (TextView)convertView.findViewById(R.id.label);
				holder.tvDesc = (TextView)convertView.findViewById(R.id.desc);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder)convertView.getTag();
			}
			holder.tvLable.setText(demos[position].label);
			holder.tvDesc.setText(demos[position].desc);
			return convertView;
		}
    	
		private class ViewHolder {
			TextView tvLable;
			TextView tvDesc;
		}
    }
    
    private static class DemoInfo {
    	private final int label;
    	private final int desc;
    	private final Class<? extends android.app.Activity> demoActivityClass;
    	public DemoInfo(int lable, int desc, Class<? extends android.app.Activity> demoActivityClass) {
    		this.label = lable;
    		this.desc = desc;
    		this.demoActivityClass = demoActivityClass;
    	}
    }
    
    //获取key
    public static class AppInfo {
    	private final String USER_KEY;
    	private static AppInfo appInfo;
    	
    	private AppInfo(String key) {
    		USER_KEY = key;
    	}
    	public static AppInfo getAppInfo(String key) {
    		if (appInfo == null) {
    			appInfo = new AppInfo(key);
			}
    		return appInfo;
    	}
    	public static String getAppkey() {
    		return appInfo.USER_KEY;
    	}
    }
}
