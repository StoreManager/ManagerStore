package com.tencent.mapsdk.raster.demo;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.tencent.tencentmap.mapsdk.map.MapActivity;
import com.tencent.tencentmap.mapsdk.map.MapView;
import com.tencent.tencentmap.mapsdk.map.UiSettings;

public class MapUisettingsActivity extends MapActivity {

    private MapView mapView;
    private UiSettings mUiSettings;

    private Button btnLb;
    private Button btnRb;
    private Button btnRt;
    private Button btnLt;
    private Button btnCb;
    private Button btnCt;
    private Button btnLbs;
    private Button btnRbs;
    private Button btnCbs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_uisettings);
        init();
    }

    protected void init() {
        mapView = (MapView)findViewById(R.id.map);
        mUiSettings  = mapView.getUiSettings();

        btnLb = (Button)findViewById(R.id.lb);
        btnRb = (Button)findViewById(R.id.rb);
        btnRt = (Button)findViewById(R.id.rt);
        btnLt = (Button)findViewById(R.id.lt);
        btnCb = (Button)findViewById(R.id.cb);
        btnCt = (Button)findViewById(R.id.ct);
        btnLbs = (Button)findViewById(R.id.lbs);
        btnRbs = (Button)findViewById(R.id.rbs);
        btnCbs = (Button)findViewById(R.id.cbs);

        //设置地图logo和比例尺的位置
        View.OnClickListener positionClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                switch (v.getId()) {
                    case R.id.lb:
                        mUiSettings.setLogoPosition(UiSettings.LOGO_POSITION_LEFT_BOTTOM);
                        break;
                    case R.id.cb:
                        mUiSettings.setLogoPosition(UiSettings.LOGO_POSITION_CENTER_BOTTOM);
                        break;
                    case R.id.rb:
                        mUiSettings.setLogoPosition(UiSettings.LOGO_POSITION_RIGHT_BOTTOM);
                        break;
                    case R.id.lt:
                        mUiSettings.setLogoPosition(UiSettings.LOGO_POSITION_LEFT_TOP);
                        break;
                    case R.id.ct:
                        mUiSettings.setLogoPosition(UiSettings.LOGO_POSITION_CENTER_TOP);
                        break;
                    case R.id.rt:
                        mUiSettings.setLogoPosition(UiSettings.LOGO_POSITION_RIGHT_TOP);
                        break;
                    case R.id.lbs:
                        mUiSettings.setScaleViewPosition(UiSettings.SCALEVIEW_POSITION_LEFT_BOTTOM);
                        break;
                    case R.id.cbs:
                        mUiSettings.setScaleViewPosition(UiSettings.SCALEVIEW_POSITION_CENTER_BOTTOM);
                        break;
                    case R.id.rbs:
                        mUiSettings.setScaleViewPosition(UiSettings.SCALEVIEW_POSITION_RIGHT_BOTTOM);
                        break;
                    default:
                        break;
                }
            }
        };
        btnLb.setOnClickListener(positionClickListener);
        btnCb.setOnClickListener(positionClickListener);
        btnRb.setOnClickListener(positionClickListener);
        btnLt.setOnClickListener(positionClickListener);
        btnCt.setOnClickListener(positionClickListener);
        btnRt.setOnClickListener(positionClickListener);
        btnLbs.setOnClickListener(positionClickListener);
        btnCbs.setOnClickListener(positionClickListener);
        btnRbs.setOnClickListener(positionClickListener);

    }
}
