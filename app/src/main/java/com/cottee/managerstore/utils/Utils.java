package com.cottee.managerstore.utils;

import com.cottee.managerstore.bean.SearchLocation;
import com.cottee.managerstore.bean.StoreInfo;
import com.cottee.managerstore.bean.VIPStandard;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 2017/11/20.
 */

public class Utils {
    public static void sendToWebService(String address, Callback callback) {
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder().url( address ).build();
        okHttpClient.newCall( request ).enqueue( callback );
    }

    public static List<SearchLocation.UserBean> handleLocationResponse(String response) {
        JsonObject jsonObject = new JsonParser().parse( response ).getAsJsonObject();
        JsonArray jsonArray = jsonObject.getAsJsonArray( "data" );
        Gson gson = new Gson();
        List<SearchLocation.UserBean> userBeanList = new ArrayList<>();
        for (JsonElement user : jsonArray) {
            //通过反射 得到UserBean.class
            SearchLocation.UserBean userBean = gson.fromJson( user, new TypeToken<SearchLocation.UserBean>() {
            }.getType() );
            userBeanList.add( userBean );
        }
        return userBeanList;
    }

    public static List<StoreInfo> handleStoreResponse(String response) {
        JsonObject jsonObject = new JsonParser().parse( response ).getAsJsonObject();
        JsonArray jsonArray = jsonObject.getAsJsonArray( "merinfo" );
        Gson gson = new Gson();
        List<StoreInfo> userBeanList = new ArrayList<>();
        for (JsonElement user : jsonArray) {
            //通过反射 得到UserBean.class
            StoreInfo storeInfo = gson.fromJson( user, new TypeToken<StoreInfo>() {
            }.getType() );
            userBeanList.add( storeInfo );
        }
        return userBeanList;
    }


    public static List<VIPStandard> handleVIPResponse(String response) {
        JsonObject jsonObject = new JsonParser().parse( response ).getAsJsonObject();
        JsonArray jsonArray = jsonObject.getAsJsonArray( "vip_list" );
        Gson gson = new Gson();
        List<VIPStandard> userBeanList = new ArrayList<>();
        for (JsonElement user : jsonArray) {
            //通过反射 得到UserBean.class
            VIPStandard vipStandardInfo = gson.fromJson( user, new TypeToken<VIPStandard>() {
            }.getType() );
            userBeanList.add( vipStandardInfo );
        }
        return userBeanList;
    }




    /**
     * 宽高比例
     * @param widthProportion
     * @param heightProportion
     * @param width
     * @return
     */
    public static int getHeight(int widthProportion,int heightProportion,int width) {
        int temp = width / widthProportion;

        return temp * heightProportion;
    }


}