package com.cottee.managerstore.view;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.cottee.managerstore.R;
import com.cottee.managerstore.wheelwidget.NumericWheelAdapter;
import com.cottee.managerstore.wheelwidget.OnWheelChangedListener;
import com.cottee.managerstore.wheelwidget.WheelView;

import java.util.Arrays;
import java.util.List;
import java.util.SimpleTimeZone;

/**
 * Created by Administrator on 2018-03-13.
 */

public class SaleFoodDialog extends Dialog  {
    private OnEditInputFinishedListener mListener; //接口
    private String[] front =
        {"1", "2", "3", "4", "5", "6", "7", "8", "9"};
   private String[] last =
            {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
    private WheelView wl_front;
    private WheelView wl_last;
    private Context context;
    private Button btn_ok_sale;


    public interface OnEditInputFinishedListener{
        void editInputFinished(String sale);
    }
    public SaleFoodDialog( Context context, OnEditInputFinishedListener mListener) {
        super(context);
        this.mListener=mListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_diy_dialog);
        initView();
        initWheelView();
        initEvent();
    }

    private void initView() {
        wl_front = findViewById( R.id.wl_front );
        wl_last = findViewById( R.id.wl_last );
        btn_ok_sale = findViewById(R.id.btn_ok_sale);
    }
    private void initEvent()
    {
        btn_ok_sale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.editInputFinished((wl_front.getCurrentItem()+1)+"."+(wl_last.getCurrentItem())
                       );
                dismiss();
            }
        });
    }

    private void initWheelView() {
        NumericWheelAdapter numericAdapter1 = new NumericWheelAdapter(getContext(), 1, 9 );
        numericAdapter1.setLabel( "" );
        numericAdapter1.setTextSize( 20 );
        numericAdapter1.setTextColor( Color.parseColor( "#25449e" ) );
        wl_front.setViewAdapter( numericAdapter1 );
        wl_front.setCyclic( true );// 可循环滚动

        NumericWheelAdapter numericAdapter2 = new NumericWheelAdapter( getContext(), 0, 9 );
        numericAdapter2.setLabel( "" );
        numericAdapter2.setTextSize( 20 );
        numericAdapter2.setTextColor( Color.parseColor( "#25449e" ) );
        wl_last.setViewAdapter( numericAdapter2 );
        wl_last.setCyclic( true );// 可循环滚动


        List<String> asList = Arrays.asList( front );
        int min_indexs = asList.indexOf( "8" );
        wl_front.setCurrentItem( min_indexs );

        List<String> asList2 = Arrays.asList( last );
        int max_indexs = asList2.indexOf( "8" );
        wl_last.setCurrentItem( max_indexs );
    }
}
