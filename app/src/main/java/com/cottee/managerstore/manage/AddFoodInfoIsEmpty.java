package com.cottee.managerstore.manage;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.widget.Toast;

/**
 * Created by Administrator on 2018-01-18.
 */

public class AddFoodInfoIsEmpty {
    private Context context;
    public AddFoodInfoIsEmpty(Context context)
    {
        this.context=context;
    }
    private String toast_foodImg="请上传菜品图片";
    private String toast_foodName="请输入菜品名称";
    private String toast_foodPrice="请输入菜品单价";

    public boolean isInfoEmpty(Drawable Img, String foodName, String foodPrice)
    {
        if (Img==null)
        {
            Toast.makeText( context, toast_foodImg, Toast.LENGTH_SHORT ).show();
            return false;
        }
        else if (TextUtils.isEmpty(foodName))
        {
            Toast.makeText(context, toast_foodName, Toast.LENGTH_SHORT).show();
            return false;
        }
        else if (TextUtils.isEmpty(foodPrice))
        {
            Toast.makeText(context, toast_foodPrice, Toast.LENGTH_SHORT).show();
            return false;
        }


        return true;
    }
}
