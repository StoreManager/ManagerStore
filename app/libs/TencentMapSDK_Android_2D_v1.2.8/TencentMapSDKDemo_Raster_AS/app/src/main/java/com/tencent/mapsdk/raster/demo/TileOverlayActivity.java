package com.tencent.mapsdk.raster.demo;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.tencent.mapsdk.raster.model.LatLng;
import com.tencent.mapsdk.raster.model.Tile;
import com.tencent.mapsdk.raster.model.TileOverlay;
import com.tencent.mapsdk.raster.model.TileOverlayOptions;
import com.tencent.mapsdk.raster.model.TileProvider;
import com.tencent.mapsdk.rastercore.tile.MapTile;
import com.tencent.tencentmap.mapsdk.map.MapView;
import com.tencent.tencentmap.mapsdk.map.TencentMap;

import java.io.BufferedInputStream;
import java.io.IOException;

/**
 * Created by wangxiaokun on 2017/1/23.
 */

public class TileOverlayActivity extends Activity implements View.OnClickListener {

    private Button btnAddTileOverlay;
    private Button btnClearTileOverlay;
    private TencentMap mTencentMap;
    private TileOverlay mTileOverlay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tile_overlay);
        initView();
    }

    protected void initView() {
        MapView mapView = (MapView)findViewById(R.id.map);
        mTencentMap = mapView.getMap();
        mTencentMap.setCenter(new LatLng(39.922211, 116.392593));
        mTencentMap.setZoom(15);

        btnAddTileOverlay = (Button) findViewById(R.id.btn_add_tile_overlay);
        btnClearTileOverlay = (Button) findViewById(R.id.btn_clear_tile_overlay_cache);

        btnAddTileOverlay.setOnClickListener(this);
        btnClearTileOverlay.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_add_tile_overlay:
                if (mTileOverlay == null) {
                    mTileOverlay = mTencentMap.addTileOverlay(
                            new TileOverlayOptions().
                                    tileProvider(new LocalTileProvider(this)));
                }
                break;
            case R.id.btn_clear_tile_overlay_cache:
                if (mTileOverlay != null) {
                    mTileOverlay.clearTileCache();
                    mTileOverlay.remove();
                    mTileOverlay = null;
                }
                break;
            default:
                break;
        }
    }

    class LocalTileProvider implements TileProvider {

        private Context mContext;

        public LocalTileProvider(Context context) {
            // TODO Auto-generated constructor stub
            mContext = context;
        }

        public byte[] getTile(String name) {
            byte[] data = null;
            try {
                BufferedInputStream bis = new BufferedInputStream(mContext.getAssets().open(name));
                data = new byte[bis.available()];
                bis.read(data);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return data;
        }

        @Override
        public int getTileHeight() {
            // TODO Auto-generated method stub
            return 256;
        }

        @Override
        public int getTileWidth() {
            // TODO Auto-generated method stub
            return 256;
        }

        @Override
        public Tile getTile(int arg0, int arg1, int arg2, MapTile.MapSource mapSource, Object... arg3) {
            // TODO Auto-generated method stub
            LatLng latLng = new LatLng(39.922211, 116.392593);
            int zoom = 15;
            double n = Math.pow(2, zoom);
            int x = (int) (((latLng.getLongitude() + 180) / 360) * n);
            int y = (int) ((1 - (Math.log(Math.tan(Math.toRadians(latLng.getLatitude())) +
                    (1 / Math.cos(Math.toRadians(latLng.getLatitude())))) / Math.PI)) / 2 * n);
            if (arg2 == zoom && arg0 == x && arg1 == y) {
                Log.e("tile", "zoom:" + arg2 + " x:" + arg0 + " y:" + arg1);
                return new Tile(arg0, arg1, arg2, getTile("tile.jpg"));
            }
            return TileProvider.NO_TILE;
        }
    }
}
