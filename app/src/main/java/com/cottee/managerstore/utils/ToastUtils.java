package com.cottee.managerstore.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by Administrator on 2017/11/20.
 */

public class ToastUtils {
    public static void showToast(Context context, String content){
        Toast.makeText(context,content,Toast.LENGTH_SHORT).show();


    }
}
