package com.cottee.managerstore.activity;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.cottee.managerstore.R;
import com.cottee.managerstore.bean.StoreInfo;
import com.cottee.managerstore.bean.VIPStandard;
import com.cottee.managerstore.handle.VIPStandardHandle;
import com.cottee.managerstore.manage.SubmitVIPStandardManager;
import com.cottee.managerstore.wheelwidget.NumericWheelAdapter;
import com.cottee.managerstore.wheelwidget.OnWheelChangedListener;
import com.cottee.managerstore.wheelwidget.WheelView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static android.widget.Toast.LENGTH_LONG;

public class AddVIPStandardActivity extends Activity implements View.OnClickListener,
        CompoundButton.OnCheckedChangeListener ,OnWheelChangedListener{

    private Button back;
    private TextView tv_currentLevel;
    private EditText et_vipName;
    private EditText et_min;
    private Drawable on;
    private Drawable off;
    private ToggleButton btn_discount;
    public static List<VIPStandard> vipStandardList=new ArrayList<>(  );
    private LinearLayout ll_discount;

    String[] front =
            {"1", "2", "3", "4", "5", "6", "7", "8", "9"};
    String[] last =
            {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
    private WheelView wl_front;
    private WheelView wl_last;
    private TextView tv_discount;
    private Button btn_ok;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_add_vipstandard );
        Resources resources = this.getResources();
        on = resources.getDrawable( R.mipmap.turnon );
        off = resources.getDrawable( R.mipmap.turnoff );

        findView();

    }

    private void findView() {
        back = (Button) findViewById( R.id.btn_back_to_vipmanager );
        back.setOnClickListener( this );
        tv_currentLevel = (TextView) findViewById( R.id.tv_currentStandard );
        et_vipName = (EditText) findViewById( R.id.et_vipname );
        et_min = (EditText) findViewById( R.id.et_min );
        btn_discount = (ToggleButton) findViewById( R.id.btn_discount );
        btn_discount.setOnCheckedChangeListener( this );
        ll_discount = (LinearLayout) findViewById( R.id.ll_discount );
        wl_front = (WheelView)findViewById( R.id.wl_front );
        wl_front.addChangingListener( this );
        wl_last = (WheelView)findViewById( R.id.wl_last );
        wl_last.addChangingListener( this );
        tv_discount = (TextView) findViewById( R.id.tv_wheel_discount );
        btn_ok = (Button) findViewById( R.id.btn_vipOK );
        btn_ok.setOnClickListener( this );
    }

    private void initWheelView() {
        NumericWheelAdapter numericAdapter1 = new NumericWheelAdapter(this, 1, 9 );
        numericAdapter1.setLabel( "" );
        numericAdapter1.setTextSize( 20 );
        numericAdapter1.setTextColor( Color.parseColor( "#25449e" ) );
        wl_front.setViewAdapter( numericAdapter1 );
        wl_front.setCyclic( true );// 可循环滚动

        NumericWheelAdapter numericAdapter2 = new NumericWheelAdapter( this, 0, 9 );
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_back_to_vipmanager:
                finish();
            case R.id.btn_vipOK:
                String name = et_vipName.getText().toString().trim();
                String min = et_min.getText().toString().trim();
                if(name.isEmpty()){
                    Toast.makeText( this, "输入名称哦",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(min.isEmpty()){
                    Toast.makeText( this, "输入积分哦",Toast.LENGTH_SHORT).show();
                    return;
                }
                String discount=null;
                if(ll_discount.getVisibility()==View.GONE){
                    discount="不打折";
                }else {
                    discount=tv_discount.getText().toString().trim();
                }
                SubmitVIPStandardManager manager = new SubmitVIPStandardManager( this, new VIPStandardHandle( this ) );
                manager.submitVIP( name,min,discount );
                finish();
                break;
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        btn_discount.setBackground( isChecked ? on : off );
        if(isChecked){
            initWheelView();
            ll_discount.setVisibility( View.VISIBLE );
            tv_discount.setText((wl_front.getCurrentItem()+1)+"."+(wl_last.getCurrentItem())
                    +"折");
        }else {
            ll_discount.setVisibility( View.GONE );
            tv_discount.setText( "不打折" );
        }
    }

    @Override
    public void onChanged(WheelView wheel, int oldValue, int newValue) {
        switch (wheel.getId()){
            case R.id.wl_front:
                tv_discount.setText( (newValue+1)+"."+wl_last.getCurrentItem()+"折" );
                break;
            case R.id.wl_last:
                tv_discount.setText( (wl_front.getCurrentItem()+1)+"."+newValue+"折" );
                break;
        }
    }
}
