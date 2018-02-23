package com.cottee.managerstore.activity1;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ListView;

import com.cottee.managerstore.R;
import com.cottee.managerstore.adapter.EmployeeManageAdapter;
import com.cottee.managerstore.widget.Title;

/**
 * Created by Administrator on 2018/1/2.
 */

public class EmployeeManageActivity extends Activity {

    private ListView lv_employee_manage_information;
    private Title title;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_manage);
        initTitle();
        initView();
        EmployeeManageAdapter adapter = new EmployeeManageAdapter(this);
        lv_employee_manage_information.setAdapter(adapter);
    }

    private void initView() {
        lv_employee_manage_information = (ListView) findViewById(R.id.lv_employee_manage_information);
    }
    private void initTitle(){
        title = (Title)findViewById(R.id.title);
        title.setTitleNameStr("员工管理");
        title.setTheme(Title.TitleTheme.THEME_TRANSLATE);
        title.mSetButtonInfo(new Title.ButtonInfo(true, Title
                .BUTTON_LEFT, R.mipmap.back_2x,null
        ));
        //可加button1
        title.mSetButtonInfo(new Title.ButtonInfo(true, Title
                .BUTTON_RIGHT1, 0,
                "me"));
        title.setOnTitleButtonClickListener(new Title.OnTitleButtonClickListener() {
            @Override
            public void onClick(int id, Title.ButtonViewHolder viewHolder) {
                switch (id) {
                    case Title.BUTTON_RIGHT1:
                        break;
                    case Title.BUTTON_LEFT:
                        break;
                }
            }
        });

    }
}
