package com.cottee.managerstore.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.cottee.managerstore.R;
import com.cottee.managerstore.adapter.ManageMoneyListViewAdapter;
import com.cottee.managerstore.view.DateTimeWheelDialog;

import java.util.ArrayList;
import java.util.List;

public class ManageMoneyActivity extends Activity {

    private ListView lv_money_detail;
    private ManageMoneyListViewAdapter moneyListViewAdapter;
    private LinearLayout linear_time_wheel;
    private TextView tv_year;
    private TextView tv_month;
    public static int days;
    private TextView tv_day;
    ArrayList<String> arry_days = DateTimeWheelDialog
            .arry_days;
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

        tv_day =findViewById(R.id.tv_day);

        tv_year = findViewById(R.id.tv_year);
        tv_month = findViewById(R.id.tv_month);
        lv_money_detail = findViewById(R.id.lv_money_detail);
        linear_time_wheel = findViewById(R.id.linear_time_wheel);
    }
    private void initEvent() {
        lv_money_detail.setOnItemClickListener(new AdapterView.OnItemClickListener() {



            @Override
            public void onItemClick(AdapterView<?> adapterView, View view,
                                    int i, long l) {
                Toast.makeText(ManageMoneyActivity.this, "AAAA"+i, Toast
                        .LENGTH_SHORT).show();
            }
        });
        // 弹出时间轴
        linear_time_wheel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                DateTimeWheelDialog timeWheelDialog = new
                        DateTimeWheelDialog(ManageMoneyActivity.this);
                 timeWheelDialog.setOnClickListener(new DateTimeWheelDialog.onClickListener() {

                     @Override
                     public void onClick(String dateTime) {
                         tv_year.setText(DateTimeWheelDialog.selectYear+"年");
                         tv_month.setText(DateTimeWheelDialog.selectMonth);
                         days = DateTimeWheelDialog.calculateCurrentDays
                                 (DateTimeWheelDialog.selectYear,
                                         DateTimeWheelDialog.selectMonth);
                         for (int i=0;i<=arry_days.size();i++)
                         {
                             System.out.println("--------get it---------------"+arry_days.get(29));
                         }
//
//
//                         List<String> strings = DateTimeWheelDialog
//                                 .initializeDays(days);
//                         for (int i=0;i<=strings.size();i++)
//                         {
//                             tv_day.setText("aaaa");
//                         }

                     }
                 });
                 timeWheelDialog.show();
            }
        });
    }
    public static int getDays()
    {
        return days;
    }
//    账目管理返回上一个页面
    public void backType(View view)
    {
        finish();
    }
}
