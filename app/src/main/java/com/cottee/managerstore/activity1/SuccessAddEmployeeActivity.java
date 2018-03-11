package com.cottee.managerstore.activity1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.cottee.managerstore.R;

/**
 * Created by Administrator on 2018/3/6.
 */

public class SuccessAddEmployeeActivity extends Activity implements View.OnClickListener {

    private TextView tv_success_emp_id;
    private TextView tv_success_emp_psw;
    private int emp_id;
    private int emp_psw;
    private Button btn_back_to_employee_manage;
    private String emp_name;
    private TextView tv_success_emp_name;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success_add);
        Intent intent = getIntent();
        emp_id = intent.getIntExtra("EMP_ID",0);
        emp_psw = intent.getIntExtra("EMP_PSW",0);
        emp_name = intent.getStringExtra("EMP_NAME");
        System.out.println("成功返回的员工id"+emp_id);
        System.out.println("成功返回的员工密码"+emp_psw);
        initView();
    }

    private void initView() {
        tv_success_emp_id = (TextView) findViewById(R.id.tv_success_emp_id);
        tv_success_emp_psw = (TextView) findViewById(R.id.tv_success_emp_psw);
        tv_success_emp_name = findViewById(R.id.tv_success_emp_name);
        btn_back_to_employee_manage = (Button) findViewById(R.id.btn_back_to_employee_manage);
        btn_back_to_employee_manage.setOnClickListener(this);
        tv_success_emp_id.setText(String.valueOf(emp_id));
        tv_success_emp_psw.setText(String.valueOf(emp_psw));
        tv_success_emp_name.setText(emp_name);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
        case R.id.btn_back_to_employee_manage:
            /*Intent intent = new Intent(SuccessAddEmployeeActivity.this,EmployeeManageActivity.class);
            startActivity(intent);*/
            finish();
            break;

        }
    }
}
