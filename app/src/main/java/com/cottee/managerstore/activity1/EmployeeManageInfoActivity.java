package com.cottee.managerstore.activity1;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.cottee.managerstore.R;
import com.cottee.managerstore.bean.viewdata.LineChartData;
import com.cottee.managerstore.widget.LineChart;
import com.cottee.managerstore.widget.Title;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/2/27.
 */

public class EmployeeManageInfoActivity extends Activity {

    private Title title;
    private LineChart mWeekLineChart;
    private String[] mWeekItems = new String[]{"日", "一", "二", "三", "四", "五", "六"};
    private int[] mWeekPoints = new int[]{0, 12, 8, -1, -1, -1, -1};
    private List<LineChartData> mWeekList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_info);
        intiView();
        initTitle();
        for (int i = 0; i < mWeekItems.length; i++) {
            LineChartData data = new LineChartData();
            data.setItem(mWeekItems[i]);
            data.setPoint(mWeekPoints[i]);
            mWeekList.add(data);
        }
        mWeekLineChart.setData(mWeekList);
    }

    private void intiView() {
        mWeekLineChart = (LineChart) findViewById(R.id.line_chart_week);
    }

    private void initTitle(){
        title = (Title)findViewById(R.id.title);
        title.setTitleNameStr("员工个人信息");
        title.setTheme(Title.TitleTheme.THEME_TRANSLATE);
        title.mSetButtonInfo(new Title.ButtonInfo(true, Title
                .BUTTON_LEFT, R.mipmap.back_2x,null
        ));
        //可加button1
        title.mSetButtonInfo(new Title.ButtonInfo(true, Title
                .BUTTON_RIGHT1, 0,
                "删除员工"));
        title.setOnTitleButtonClickListener(new Title.OnTitleButtonClickListener() {
            @Override
            public void onClick(int id, Title.ButtonViewHolder viewHolder) {
                switch (id) {
                    case Title.BUTTON_RIGHT1:
                        break;
                    case Title.BUTTON_LEFT:
                        finish();
                        break;
                }
            }
        });

    }
}
