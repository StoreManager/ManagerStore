package com.cottee.managerstore.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.cottee.managerstore.R;
import com.cottee.managerstore.adapter.ManageMoneyListViewAdapter;
import com.cottee.managerstore.view.DateTimeWheelDialog;

public class ManageMoneyActivity extends Activity {

    private ListView lv_money_detail;
    private ManageMoneyListViewAdapter moneyListViewAdapter;
    private LinearLayout linear_time_wheel;
    private TextView tv_year;
    private TextView tv_month;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_money);
        initView();
        initEvent();
        moneyListViewAdapter = new ManageMoneyListViewAdapter(this);
        lv_money_detail.setAdapter(moneyListViewAdapter);
    }



    private void initView() {
        tv_year = findViewById(R.id.tv_year);
        tv_month = findViewById(R.id.tv_month);
        lv_money_detail = findViewById(R.id.lv_money_detail);
        linear_time_wheel = findViewById(R.id.linear_time_wheel);
    }
    private void initEvent() {
        // 弹出时间轴
        linear_time_wheel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DateTimeWheelDialog timeWheelDialog = new
                        DateTimeWheelDialog(ManageMoneyActivity.this);
                 timeWheelDialog.setOnClickListener(new DateTimeWheelDialog.onClickListener() {

                     @Override
                     public void onClick(String dateTime) {
                         tv_year.setText(DateTimeWheelDialog.selectYear+"年");
                         tv_month.setText(DateTimeWheelDialog.selectMonth);
                     }
                 });
                 timeWheelDialog.show();
            }
        });
    }
//    账目管理返回上一个页面
    public void backType(View view)
    {
        finish();
    }
}
