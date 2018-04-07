package com.cottee.managerstore.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.cottee.managerstore.R;

public class VIPDetialActivity extends Activity implements View.OnClickListener {

    private Button btn_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_vipdetial );

        btn_back = (Button) findViewById( R.id.btn_backss );
        btn_back.setOnClickListener( this );
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_backss:
                finish();
                break;
        }
    }
}
