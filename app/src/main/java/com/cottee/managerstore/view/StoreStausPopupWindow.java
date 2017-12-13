package com.cottee.managerstore.view;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.cottee.managerstore.R;

/**
 * Created by Administrator on 2017-12-12.
 */

public class StoreStausPopupWindow extends PopupWindow {
    private View mainView;
    private LinearLayout linear_open, linear_close;
    private Context context;
    public StoreStausPopupWindow(final Context context){
        super(context);
        //窗口布局
        mainView = LayoutInflater.from(context).inflate(R.layout.layout_store_status, null);
        //营业中
        linear_open = mainView.findViewById(R.id.linear_open);

        //，打烊
        linear_close = mainView.findViewById(R.id.linear_close);
        //设置每个子布局的事件监听器
        setContentView(mainView);
        linear_open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String open="营业";
            }
        });
        linear_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "打烊", Toast.LENGTH_SHORT).show();
            }
        });
        //设置宽度
        setWidth(300);
        //设置高度
        setHeight(200);
        //设置显示隐藏动画
        setAnimationStyle(R.style.AnimTools);
        //设置背景透明
        setBackgroundDrawable(new ColorDrawable(0));
    }

}

