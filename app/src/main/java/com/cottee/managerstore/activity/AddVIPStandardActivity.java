package com.cottee.managerstore.activity;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.cottee.managerstore.R;
import com.cottee.managerstore.bean.StoreInfo;
import com.cottee.managerstore.bean.VIPStandard;

import java.util.ArrayList;
import java.util.List;

public class AddVIPStandardActivity extends Activity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {

    private Button back;
    private TextView tv_currentLevel;
    private EditText et_vipName;
    private EditText et_min;
    private Drawable on;
    private Drawable off;
    private ToggleButton btn_discount;
    public static List<VIPStandard> vipStandardList=new ArrayList<>(  );

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
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_back_to_vipmanager:
                finish();
                break;
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        btn_discount.setBackground( isChecked ? on : off );
    }

}
