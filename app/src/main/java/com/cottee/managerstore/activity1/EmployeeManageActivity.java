package com.cottee.managerstore.activity1;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.cottee.managerstore.R;
import com.cottee.managerstore.adapter.EmployeeManageAdapter;
import com.cottee.managerstore.bean.AllEmployeeGetInfo;
import com.cottee.managerstore.httputils.HttpUtilSession;
import com.cottee.managerstore.manage.EmployeeManageMessage;
import com.cottee.managerstore.properties.Properties;
import com.cottee.managerstore.utils.ToastUtils;
import com.cottee.managerstore.widget.Title;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by Administrator on 2018/1/2.
 */

public class EmployeeManageActivity extends Activity implements View.OnClickListener {

    private ListView lv_employee_manage_information;
    private Title title;
    private Button btn_search;
    private EmployeeManageAdapter adapter;
    private List<String> empName = new ArrayList<>();
    private List<String> empId = new ArrayList<>();
    private EmployeeManageHandle handler = new EmployeeManageHandle();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_manage);
        initTitle();
        initView();
        sendRequestWithOkHttp();

        lv_employee_manage_information.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Intent intent = new Intent(EmployeeManageActivity.this,EmployeeManageInfoActivity.class);
                startActivity(intent);
            }
        });
    }


        public void sendRequestWithOkHttp() {
            new Thread(new Runnable() {
                @Override
                public void run() {

                    HttpUtilSession.sendSessionOkHttpRequest(EmployeeManageActivity.this, Properties.EMPLOYEE_ALL_GSON_PATH, new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {

                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            String responeData = response.body().string();
                            System.out.println("所有员工列表 Json:" + responeData);


                            if(responeData.trim().equals("250")){

                                ToastUtils.showToast(EmployeeManageActivity.this,"session无效效，请重新登陆" );

                            }
                            else if (responeData.trim().equals("0")){
                                ToastUtils.showToast(EmployeeManageActivity.this,"获取失败" );
                            }
                            else {

                                parseJSONWithGSON(responeData);
                            }

                        }

                    });
                }
            }).start();
        }


        private void parseJSONWithGSON(String jsonData) {
            //使用轻量级的Gson解析得到的json
            Gson gson = new Gson();
            List<AllEmployeeGetInfo.EmployeeDetailBean> empInfo = gson.fromJson(jsonData, AllEmployeeGetInfo.class).getMer_list();

            Message message = new Message();
            message.what = Properties.EMPLOYEE_ADD;
            message.obj = empInfo;


            handler.sendMessage(message);




        }

    public class EmployeeManageHandle extends Handler {
        private Context context;


        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case Properties.EMPLOYEE_ADD:
                    List<AllEmployeeGetInfo.EmployeeDetailBean> empInfo = (List<AllEmployeeGetInfo.EmployeeDetailBean>) msg.obj;
                    for (int i = 0; i < empInfo.size(); i++) {

                        empName.add(empInfo.get(i).getName());
                        empId.add(String.valueOf(empInfo.get(i).getStaff_id()));
                        System.out.println("单个员工名字："+empInfo.get(i).getName());
                        System.out.println("单个员工id："+empInfo.get(i).getStaff_id());
                    }

                    adapter = new EmployeeManageAdapter(EmployeeManageActivity.this,empName,empId);
                    lv_employee_manage_information.setAdapter(adapter);
                   /* if (!adapter.isEmpty()) {
                        ll_empty.setVisibility(View.GONE);

                    } else {


                        ll_empty.setVisibility(View.VISIBLE);
                    }*/



                    break;
            }
            super.handleMessage(msg);
        }
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

                        final EditText et = new EditText(EmployeeManageActivity.this);
                        AlertDialog.Builder builder = new AlertDialog.Builder(EmployeeManageActivity.this);
                        builder.setTitle("请输入员工姓名");
                        builder.setView(et);
                        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                String name = et.getText().toString().trim();
                                EmployeeManageMessage message = new EmployeeManageMessage(EmployeeManageActivity.this);
                                message.addEmployeeCommit(name);

                            }
                        });

                        builder.setNegativeButton("取消",null);
                        builder.show();
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
