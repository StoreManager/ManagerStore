package com.cottee.managerstore.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.cottee.managerstore.R;
import com.cottee.managerstore.adapter.OrderAdapter;
import com.cottee.managerstore.beans.FoodInfo;
import com.cottee.managerstore.dpconfig.MyDPTheme;

import java.util.ArrayList;
import java.util.List;

import cn.aigestudio.datepicker.bizs.themes.DPTManager;
import cn.aigestudio.datepicker.cons.DPMode;
import cn.aigestudio.datepicker.views.DatePicker;


public class ManageMoneyActivity extends Activity {

    private ListView lv_order;
    private ImageButton ibt_datechoose;
    private TextView tv_date;
    private final static MyDPTheme theme = new MyDPTheme();
    private LinearLayout ll_date;
    private DatePicker dp_date;
    private String date_start = "";
    private String date_end = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainmoney);
        initView();
        orderListSetting();
    }

    private void orderListSetting() {
        lv_order.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ImageView iv_order_show = (ImageView) view.findViewById(R.id.iv_order_show);
                LinearLayout ll_order_details = view.findViewById(R.id.ll_order_details);
                if (ll_order_details.getChildAt(0) == null) {
                    iv_order_show.setBackgroundResource(R.mipmap.order_close);
                    View inflate = View.inflate(ManageMoneyActivity.this, R.layout.layout_order_details, null);
                    ll_order_details.addView(inflate);
                    LinearLayout ll_foodlist = inflate.findViewById(R.id.ll_foodlist);
                    List<FoodInfo> foodInfoList = new ArrayList<FoodInfo>();
                    for (FoodInfo foodInfo : foodInfoList) {
                        View food = View.inflate(ManageMoneyActivity.this, R.layout.layout_food_item, null);
                        setFoodInfo(food, foodInfo);
                        ll_foodlist.addView(food);
                    }
                } else {
                    ll_order_details.removeAllViews();
                    iv_order_show.setBackgroundResource(R.mipmap.order_open);
                }
            }
        });

    }

    private void setFoodInfo(View v, FoodInfo foodInfo) {
        TextView tv_foodname = v.findViewById(R.id.tv_foodname);
        TextView tv_food_count = v.findViewById(R.id.tv_food_count);
        TextView tv_food_price = v.findViewById(R.id.tv_food_price);
        tv_foodname.setText(foodInfo.getFoodname());
        tv_food_count.setText("*" + foodInfo.getFood_count());
        tv_food_price.setText(foodInfo.getFood_price());
    }

    private void initView() {
        tv_date = (TextView) findViewById(R.id.tv_date);
        DPTManager.getInstance().initCalendar(theme);
        dp_date = new DatePicker(this);
        dp_date.setVisibility(View.GONE);
        dp_date.setDate(2018, 3);
        dp_date.setMode(DPMode.SINGLE);
        /*dp_date.setOnDateSelectedListener(new DatePicker.OnDateSelectedListener() {
            @Override
            public void onDateSelected(List<String> date) {
                if (date.size() >= 2) {
                    String start = date.get(0);
                    String end = date.get(date.size() - 1);
                    List<String> adddate = autoSelectDate(start, end);
                    et_datestart.setText(start);
                    et_dateend.setText(end);
                    tv_date.setText(start+"-"+end);
                }

            }
        });*/
        dp_date.setOnDatePickedListener(new DatePicker.OnDatePickedListener() {
            @Override
            public void onDatePicked(String date) {
                if (date_start.length() == 0) {
                    date_start = date;
                    tv_date.setText(date_start);
                    return;
                }
                if (date_start.length() != 0 && date_end.length() == 0) {
                    date_end = date;
                    tv_date.setText(tv_date.getText() + " - " + date_end);
                    return;
                }
            }
        });
        ll_date = (LinearLayout) findViewById(R.id.ll_date);
        ll_date.bringToFront();
        dp_date.bringToFront();
        ll_date.addView(dp_date, 0);
        lv_order = (ListView) findViewById(R.id.lv_order);
        lv_order.setAdapter(new OrderAdapter(this));
        ibt_datechoose = (ImageButton) findViewById(R.id.ibt_datechoose);
    }

    public void date_choose(View v) {
        int visibility = dp_date.getVisibility();
        if (visibility == View.GONE) {
            ll_date.setVisibility(View.VISIBLE);
            dp_date.setVisibility(View.VISIBLE);
            ibt_datechoose.setImageResource(R.mipmap.date_close);
            if (date_start.length() == 0||date_end.length() == 0) {
                Toast.makeText(this, "请选择起始日期和截止日期", Toast.LENGTH_SHORT).show();
            }
            if (date_start.length() != 0 && date_end.length() != 0) {
                Toast.makeText(this, "已选定起始日期和截止日期，更改请清除", Toast.LENGTH_SHORT).show();
            }
        } else {
            if (date_start.length() == 0) {
                Toast.makeText(this, "起始日期未选择，请重新选择", Toast.LENGTH_SHORT).show();
                return;
            } else if (date_end.length() == 0) {
                Toast.makeText(this, "截止日期未选择，请重新选择", Toast.LENGTH_SHORT).show();
                return;
            }

            dp_date.setVisibility(View.GONE);
            ll_date.setVisibility(View.GONE);
            ibt_datechoose.setImageResource(R.mipmap.date_open);
        }

    }

    public void showdateontv(View v) {
        dp_date.setVisibility(View.GONE);
        ll_date.setVisibility(View.GONE);
        ibt_datechoose.setImageResource(R.mipmap.date_open);
    }

    public void clean(View v) {
        date_start = "";
        date_end = "";
        tv_date.setText("");
        Toast.makeText(ManageMoneyActivity.this, "请选择起始日期和结束日期", Toast.LENGTH_SHORT).show();
    }
    public void back(View view)
    {
        finish();
    }
}

