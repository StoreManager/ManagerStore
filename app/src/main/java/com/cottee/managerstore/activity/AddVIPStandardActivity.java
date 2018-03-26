package com.cottee.managerstore.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.cottee.managerstore.R;

public class AddVIPStandardActivity extends Activity implements View.OnClickListener{

    private Button back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_add_vipstandard );


        back = (Button) findViewById( R.id.btn_back_to_vipmanager );
        back.setOnClickListener( this );
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_back_to_vipmanager:
                finish();
                break;
        }
    }
}
