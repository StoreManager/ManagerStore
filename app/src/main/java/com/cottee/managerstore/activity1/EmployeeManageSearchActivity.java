package com.cottee.managerstore.activity1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.cottee.managerstore.R;
import com.cottee.managerstore.adapter.EmployeeManageAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/2/24.
 */

public class EmployeeManageSearchActivity extends Activity implements View.OnClickListener {

    private EditText et_custom_search;
    private Button btn_clear_search;
    private Button btn_search_start;
    private Button btn_search_cancel;
    private List<String> emp_name;
    private List<String> emp_id;
    private ListView lv_search_employee_info;
    private List<String> search_name = new ArrayList<>();
    private List<String> search_id = new ArrayList<>();
    private EmployeeManageAdapter adapter;
    private TextView tv_no_emp_info;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_manage_search);
        Intent intent = getIntent();
        emp_name = (List<String>) intent.getSerializableExtra("EMP_NAME");
        emp_id = (List<String>) intent.getSerializableExtra("EMP_ID");
        initView();
        initEvent();
        lv_search_employee_info.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                String empInfoId = search_id.get(position);
                Intent intent = new Intent(EmployeeManageSearchActivity.this,EmployeeManageInfoActivity.class);
                intent.putExtra("EMP_ID",empInfoId);
                startActivity(intent);
            }
        });

    }

    private void initView() {
        et_custom_search = (EditText) findViewById(R.id.et_custom_search);
        btn_clear_search = (Button) findViewById(R.id.btn_clear_search);
        tv_no_emp_info = (TextView) findViewById(R.id.tv_no_emp_info);
        btn_search_cancel = (Button) findViewById(R.id.btn_search_cancel);
        lv_search_employee_info = (ListView) findViewById(R.id.lv_search_employee_info);

        btn_search_cancel.setOnClickListener(this);




        et_custom_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                search_name.clear();
                search_id.clear();
                String search_data = et_custom_search.getText().toString().trim();
                System.out.println("输入为："+search_data);
                for(int s=0;s<emp_name.size();s++){
                    String single_name = emp_name.get(s);
                    String single_id = emp_id.get(s);

                    if(single_name.indexOf(search_data)!=-1&&!search_data.equals("")||single_id.indexOf(search_data)!=-1&&!search_data.equals("")){

                        search_name.add(single_name);
                        search_id.add(single_id);

                    }

                }
                System.out.println("查找暂时数据"+search_name);
                if(search_name.equals("[]")){
                    lv_search_employee_info.setVisibility(View.GONE);
                    tv_no_emp_info.setVisibility(View.VISIBLE);
                }else {
                    lv_search_employee_info.setVisibility(View.VISIBLE);
                    tv_no_emp_info.setVisibility(View.GONE);
                }

                adapter.notifyDataSetChanged();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        adapter = new EmployeeManageAdapter(this,search_name,search_id);
        lv_search_employee_info.setAdapter(adapter);


    }

    private void initEvent() {
        et_custom_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if (s.length() != 0 || s.equals("")) {
                    if (et_custom_search.getGravity() == Gravity.CENTER) {
                        et_custom_search.setGravity(Gravity.LEFT);
                        et_custom_search.setPadding(40, 10, 0, 0);
                        et_custom_search.setTextSize(16);

                    }

                } else {
                    et_custom_search.setGravity(Gravity.CENTER);
                    et_custom_search.setTextSize(12);

                }
            }
        });
        btn_clear_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et_custom_search.setText("");
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){

        case R.id.btn_search_cancel:
            finish();
        break;

        default:
        break;
        }
    }
}
