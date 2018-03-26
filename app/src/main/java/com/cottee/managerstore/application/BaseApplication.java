package com.cottee.managerstore.application;

import android.app.Application;
import android.content.Context;

/**
 * Created by Administrator on 2018-03-16.
 */

public class BaseApplication extends Application {
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context=getApplicationContext();
    }
    public static Context getContext()
    {
        return context;
    }

}
