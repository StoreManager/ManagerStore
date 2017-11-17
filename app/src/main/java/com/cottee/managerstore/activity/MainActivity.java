package com.cottee.managerstore.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.cottee.managerstore.R;

public class MainActivity extends Activity {

    private Button btn_manager;
    private Button btn_employee;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );

        findView();

    }

    private void findView() {
        btn_manager = (Button) findViewById( R.id.btn_manager );
        btn_employee = (Button) findViewById( R.id.btn_employee );

        btn_manager.setOnClickListener( new MyListener() );
        btn_employee.setOnClickListener( new MyListener() );
    }

    private class MyListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.btn_manager:
                    startActivity( new Intent( MainActivity.this,ManagerActivity.class ) );
                    break;
                case R.id.btn_employee:
                    Toast.makeText( MainActivity.this, "employee", Toast.LENGTH_SHORT ).show();
                    break;
                default:
                    break;
            }
        }
    }
}
