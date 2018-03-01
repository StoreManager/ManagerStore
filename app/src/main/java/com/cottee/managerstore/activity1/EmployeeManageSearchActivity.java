package com.cottee.managerstore.activity1;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.cottee.managerstore.R;

/**
 * Created by Administrator on 2018/2/24.
 */

public class EmployeeManageSearchActivity extends Activity implements View.OnClickListener {

    private EditText et_custom_search;
    private Button btn_clear_search;
    private Button btn_search_start;
    private Button btn_search_cancel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_manage_search);
        initView();
        initEvent();
    }

    private void initView() {
        et_custom_search = (EditText) findViewById(R.id.et_custom_search);
        btn_clear_search = (Button) findViewById(R.id.btn_clear_search);
        btn_search_start = (Button) findViewById(R.id.btn_search_start);
        btn_search_cancel = (Button) findViewById(R.id.btn_search_cancel);
        btn_search_start.setOnClickListener(this);
        btn_search_cancel.setOnClickListener(this);
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
        case R.id.btn_search_start:

        break;
        case R.id.btn_search_cancel:
            finish();
        break;

        default:
        break;
        }
    }
}
