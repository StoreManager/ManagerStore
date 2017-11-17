package com.tencent.mapsdk.raster.demo;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;

import com.tencent.mapsdk.raster.model.BitmapDescriptorFactory;
import com.tencent.mapsdk.raster.model.LatLng;
import com.tencent.mapsdk.raster.model.Polyline;
import com.tencent.mapsdk.raster.model.PolylineOptions;
import com.tencent.tencentmap.mapsdk.map.CameraUpdateFactory;
import com.tencent.tencentmap.mapsdk.map.QSupportMapFragment;
import com.tencent.tencentmap.mapsdk.map.TencentMap;

import java.util.ArrayList;
import java.util.List;

public class PolylineActivity extends FragmentActivity {

	private TencentMap tencentMap;
	private Polyline polyline;
	private Button btnAddPolyline;
	private Button btnRemovePolyline;
	private SeekBar sbWidth;
	private SeekBar sbHue;
	private SeekBar sbAlpha;

	private Polyline roadLine;

	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.activity_polyline);
		init();
		bindListener();
	}

	protected void init() {
		FragmentManager fm = getSupportFragmentManager();
		QSupportMapFragment mapFragment =
				(QSupportMapFragment)fm.findFragmentById(R.id.frag_map);
		tencentMap = mapFragment.getMapView().getMap();
		tencentMap.moveCamera(CameraUpdateFactory.zoomTo(10f));
		btnAddPolyline = (Button)findViewById(R.id.btn_add_polyline);
		btnRemovePolyline = (Button)findViewById(R.id.btn_remove_polyline);
		sbWidth = (SeekBar)findViewById(R.id.sb_width);
		sbAlpha = (SeekBar)findViewById(R.id.sb_transparency);
		sbHue = (SeekBar)findViewById(R.id.sb_hue);
	}

	protected void bindListener() {
		OnClickListener onClickListener = new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				switch (v.getId()) {
				case R.id.btn_add_polyline:
					addPolyLine();
					addRoadLine();
					break;
				case R.id.btn_remove_polyline:
					if (polyline != null) {
						polyline.remove();
						polyline = null;
					}
					if (roadLine != null) {
						roadLine.remove();
						roadLine = null;
					}
					break;

				default:
					break;
				}
			}
		};

		btnAddPolyline.setOnClickListener(onClickListener);
		btnRemovePolyline.setOnClickListener(onClickListener);

		OnSeekBarChangeListener onSeekBarChangeListener = new OnSeekBarChangeListener() {

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
				// TODO Auto-generated method stub
				if (polyline == null) {
					return;
				}
				switch (seekBar.getId()) {
				case R.id.sb_width:
					polyline.setWidth(progress);
					break;
				case R.id.sb_transparency:
					polyline.setColor(Color.HSVToColor(progress,
                            new float[]{sbHue.getProgress(), 1f, 1f}));
					break;
				case R.id.sb_hue:
					polyline.setColor(Color.HSVToColor(
                            sbAlpha.getProgress(),
                            new float[]{progress, 1f, 1f}));
					break;
				default:
					break;
				}
			}
		};

        sbHue.setOnSeekBarChangeListener(onSeekBarChangeListener);
		sbAlpha.setOnSeekBarChangeListener(onSeekBarChangeListener);
		sbWidth.setOnSeekBarChangeListener(onSeekBarChangeListener);
	}

	protected void addPolyLine() {
		if (polyline != null) {
			return;
		}
		List<LatLng> latLngs = new ArrayList<LatLng>();
		latLngs.add(new LatLng(39.999391,116.135972));
		latLngs.add(new LatLng(39.898323,116.057694));
		latLngs.add(new LatLng(39.900430,116.265061));
		latLngs.add(new LatLng(39.955192,116.140092));
		polyline = tencentMap.addPolyline(new PolylineOptions().
				addAll(latLngs).
                color(Color.HSVToColor(sbAlpha.getProgress(), new float[]{sbHue.getProgress(), 1f, 1f})).
				width(sbWidth.getProgress()));
	}

	protected void addRoadLine() {
		if (roadLine != null) {
			return;
		}
		List<LatLng> latLngs = new ArrayList<>();
		latLngs.add(new LatLng(39.939391,116.135972));
		latLngs.add(new LatLng(39.828323,116.057694));
		latLngs.add(new LatLng(39.840430,116.265061));
		latLngs.add(new LatLng(39.895192,116.140092));
		roadLine = tencentMap.addPolyline(new PolylineOptions().
				addAll(latLngs).
				color(0xff0066CC).
				width(10).
				arrowTexture(BitmapDescriptorFactory.fromAsset("texture_arrow.png")).
				arrowGap(30).
				edgeColor(0xff0072E3).
				edgeWidth(8));
	}
}
