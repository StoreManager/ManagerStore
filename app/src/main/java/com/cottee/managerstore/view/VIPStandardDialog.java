package com.cottee.managerstore.view;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.cottee.managerstore.R;

/**
 * Created by user on 2018/2/25.
 */

public class VIPStandardDialog extends Dialog implements View.OnClickListener{

    private Context context;      // 上下文
    private int layoutResID;      // 布局文件id
    private int[] listenedItems;  // 要监听的控件id
    public VIPStandardDialog(Context context, int layoutResID, int[] listenedItems) {
        super(context, R.style.dialog_custom); //dialog的样式
        this.context = context;
        this.layoutResID = layoutResID;
        this.listenedItems = listenedItems;
    }

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        Window window = getWindow();
        window.setGravity( Gravity.CENTER );// // 此处可以设置dialog显示的位置为居中
        window.setWindowAnimations( R.anim.bottom_menu_enter ); // 添加动画效果
        setContentView(layoutResID);

        WindowManager windowManager = ((Activity) context).getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.width = display.getWidth()*4/5; // 设置dialog宽度为屏幕的4/5
        getWindow().setAttributes(lp);
        setCanceledOnTouchOutside(false);// 点击Dialog外部消失
        //遍历控件id,添加点击事件
        for (int id : listenedItems) {
            findViewById(id).setOnClickListener(this);
        }
    }


    private OnCenterItemClickListener listener;
    public interface OnCenterItemClickListener {
        void OnCenterItemClick(VIPStandardDialog dialog, View view);
    }

    public void setOnCenterItemClickListener(OnCenterItemClickListener listener) {
        this.listener = listener;

    }

    @Override
    public void onClick(View view) {
        listener.OnCenterItemClick(this, view);
    }
}
