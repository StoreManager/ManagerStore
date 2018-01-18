package com.cottee.managerstore.httputils;

import com.cottee.managerstore.bean.InfoEntity;
import com.google.gson.Gson;
import com.squareup.okhttp.Request;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public abstract class LocationUtils {
    private final static String post = "http://apis.map.qq" +
            ".com/ws/geocoder/v1/?location=";
    private final static String key =
            "&get_poi=0&key=DUSBZ-LL63F-FOIJI-J2GW4-TWNFT-FEFBB";
    private double lat;

    private double lon;

    public LocationUtils(double lat, double lon) {
        this.lat = lat;
        this.lon = lon;
        send();

    }

    abstract void onData(String string, String city);

    private void send() {
        new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        HttpUtils.sendOkHttpRequest( post + lat + "," + lon + key, new
                                com.squareup.okhttp.Callback() {
                                    @Override
                                    public void onFailure(Request request, IOException e) {

                                    }

                                    @Override
                                    public void onResponse(com.squareup.okhttp.Response response) throws IOException {
                                        String string = response.body()
                                                .string();
                                        Gson gson = new Gson();
                                        InfoEntity infoEntity = gson.fromJson( string, InfoEntity.class );


                                        onData( infoEntity.getResult()
                                                .getAddress_reference()
                                                .getFamous_area().getTitle(), infoEntity.getResult().getAd_info().getCity() );
                                    }
                                });
                    }
                } ).start();
    }
}

