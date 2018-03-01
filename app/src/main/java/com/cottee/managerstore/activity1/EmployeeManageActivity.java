package com.cottee.managerstore.activity1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.cottee.managerstore.R;
import com.cottee.managerstore.adapter.EmployeeManageAdapter;
import com.cottee.managerstore.widget.Title;

/**
 * Created by Administrator on 2018/1/2.
 */

public class EmployeeManageActivity extends Activity implements View.OnClickListener {

    private ListView lv_employee_manage_information;
    private Title title;
    private Button btn_search;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_manage);
        initTitle();
        initView();
        EmployeeManageAdapter adapter = new EmployeeManageAdapter(this);
        lv_employee_manage_information.setAdapter(adapter);
        lv_employee_manage_information.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Intent intent = new Intent(EmployeeManageActivity.this,EmployeeManageInfoActivity.class);
                startActivity(intent);
            }
        });
    }



    private void initView() {
        lv_employee_manage_information = (ListView) findViewById(R.id.lv_employee_manage_information);
        btn_search = (Button) findViewById(R.id.btn_search);
        btn_search.setOnClickListener(this);
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
                "添加员工"));
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

    @Override
    public void onClick(View view) {
        switch(view.getId()){
        case R.id.btn_search:
            Intent intent = new Intent(EmployeeManageActivity.this,EmployeeManageSearchActivity.class);
            startActivity(intent);
            break;
        default:
        break;
        }
    }
}
