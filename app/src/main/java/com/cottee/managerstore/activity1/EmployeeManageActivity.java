package com.cottee.managerstore.activity1;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ListView;

import com.cottee.managerstore.R;
import com.cottee.managerstore.adapter.EmployeeManageAdapter;

/**
 * Created by Administrator on 2018/1/2.
 */

public class EmployeeManageActivity extends Activity {

    private ListView lv_employee_manage_information;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_manage);

        initview();
        EmployeeManageAdapter adapter = new EmployeeManageAdapter(this);
        lv_employee_manage_information.setAdapter(adapter);
    }

    private void initview() {
        lv_employee_manage_information = (ListView) findViewById(R.id.lv_employee_manage_information);
    }
}
