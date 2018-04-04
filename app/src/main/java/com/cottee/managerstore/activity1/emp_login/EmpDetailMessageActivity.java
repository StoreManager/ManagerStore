package com.cottee.managerstore.activity1.emp_login;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Base64;
import android.widget.TextView;

import com.cottee.managerstore.R;
import com.cottee.managerstore.bean.SingleEmployeeInfo;
import com.cottee.managerstore.bean.emp_login.EmpRequestInfo;
import com.cottee.managerstore.bean.viewdata.LineChartData;
import com.cottee.managerstore.handle.oss_handler.OssHandler;
import com.cottee.managerstore.httputils.Https;
import com.cottee.managerstore.properties.Properties;
import com.cottee.managerstore.utils.ToastUtils;
import com.cottee.managerstore.utils.myt_oss.ConfigOfOssClient;
import com.cottee.managerstore.utils.myt_oss.DownloadUtils;
import com.cottee.managerstore.utils.myt_oss_file.FileUtils;
import com.cottee.managerstore.view.ImageViewExtend;
import com.cottee.managerstore.widget.LineChart;
import com.cottee.managerstore.widget.Title;
import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by Administrator on 2018/3/19.
 */

public class EmpDetailMessageActivity extends Activity {

    private Title title;
    private ImageViewExtend imv_header;
    private TextView tv_emp_name;
    private TextView tv_emp_sex;
    private TextView tv_emp_birth;
    private TextView tv_emp_phone;
    private EmpInfoHandle handler = new EmpInfoHandle(this);

    private String[] mWeekItems = new String[]{"日", "一", "二", "三", "四", "五", "六"};
    private int[] mWeekPoints = new int[7];
    private List<LineChartData> mWeekList = new ArrayList<>();
    private LineChart mWeekLineChart;
    private String empName;
    private String empSex;
    private String empBirth;
    private String empPhone;
    private String empPhoto;
    private File cache_image;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emp_message_detail);
        initTitle();
        initView();
        System.out.println("员工id"+EmpRequestInfo.getUserEmail());
        sendRequest(EmpRequestInfo.getUserEmail());
    }

    private void initView() {
        tv_emp_name = (TextView) findViewById(R.id.tv_emp_name);
        tv_emp_sex = (TextView) findViewById(R.id.tv_emp_sex);
        tv_emp_birth = (TextView) findViewById(R.id.tv_emp_birth);
        tv_emp_phone = (TextView) findViewById(R.id.tv_emp_phone);
        mWeekLineChart = (LineChart) findViewById(R.id.line_emp_chart_week);
        imv_header = (ImageViewExtend) findViewById(R.id.imv_header);
       imv_header.setmDrawShapeType(ImageViewExtend.SHAPE_CIRCLE);
    }


    private void initTitle() {
        title = (Title) findViewById(R.id.title);
        title.setTitleNameStr("员工详细信息");
        title.setTheme(Title.TitleTheme.THEME_TRANSLATE);
        title.mSetButtonInfo(new Title.ButtonInfo(true, Title
                .BUTTON_LEFT, R.mipmap.back_2x, null
        ));
        //可加button1
        title.mSetButtonInfo(new Title.ButtonInfo(true, Title
                .BUTTON_RIGHT1, 0,
                "修改信息"));
        title.setOnTitleButtonClickListener(new Title.OnTitleButtonClickListener() {
            @Override
            public void onClick(int id, Title.ButtonViewHolder viewHolder) {
                switch (id) {
                    case Title.BUTTON_RIGHT1:
                        Intent intent = new Intent(EmpDetailMessageActivity.this,EmpUpdateDetailActivity.class);
                        intent.putExtra("NAME",empName);
                        intent.putExtra("SAX",empSex);
                        intent.putExtra("BIRTH",empBirth);
                        intent.putExtra("PHONE",empPhone);
                        if(cache_image!=null){
                            intent.putExtra("PHOTO", FileUtils.fileToByte(cache_image));
                        }

                        startActivity(intent);

                        break;
                    case Title.BUTTON_LEFT:
                        Intent intent1 = new Intent(EmpDetailMessageActivity.this,EmpMainActivity.class);
                        startActivity(intent1);
                        finish();
                }
            }
        });
    }

    private void sendRequest(final String empId) {
        new Thread() {
            @Override
            public void run() {

                Https.sendEmpSessionAndFieldOkHttpRequest(Properties.EMPLOYEE_INFO_GSON_PATH, "staff_id", empId, new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {

                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String responeData = response.body().string();
                        System.out.println("员工具体信息 Json:" + responeData);
                        if(responeData.trim().equals("250")){
                            ToastUtils.showToast(EmpDetailMessageActivity.this,"session无效效，正在重新登陆请稍等" );

                            /*sendRequestWithOkHttp();*/
                        }
                        else if (responeData.trim().equals("0")){

                        }
                        else {

                            parseJSONWithGSON(responeData);
                        }
                    }
                });

            }
        }.start();

    }

    private void parseJSONWithGSON(String jsonData) {
        //使用轻量级的Gson解析得到的json
        Gson gson = new Gson();
        SingleEmployeeInfo empInfo = gson.fromJson(jsonData, SingleEmployeeInfo.class);
       /* List<SingleEmployeeInfo> empInfo = (List<SingleEmployeeInfo>) singleEmployeeInfo;*/
        System.out.println("员工信息外部数据："+empInfo);
       /* System.out.println("员工信息时间数据："+timeInfo);*/
        Message message = new Message();
        message.what = Properties.EMPLOYEE_INFO;
        message.obj = empInfo;



        handler.sendMessage(message);

    }

    public class EmpInfoHandle extends Handler {
        private String[] empTime;
        private Context context;
        public EmpInfoHandle(Context context) {
            this.context = context;
        }

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case Properties.EMPLOYEE_INFO:
                    SingleEmployeeInfo empInfo = (SingleEmployeeInfo) msg.obj;


                    empName = empInfo.getName();
                    empSex = empInfo.getSex();
                    empBirth = empInfo.getBirth();
                    empPhone = empInfo.getPhone_number();
                    final String empPhoto = empInfo.getPhoto();
                    empTime = empInfo.getTime_list();
//                    empPhoto= OssUtils.getOSSExtranetPath(photo);
                    System.out.println("单个员工名字："+ empName);
                    System.out.println("单个员工性别："+ empSex);
                    System.out.println("单个员工生日："+ empBirth);
                    System.out.println("单个员工电话："+ empPhone);
                    System.out.println("单个员工头像："+ empPhoto);
                    for(int i=0;i<empTime.length;i++){
                        mWeekPoints[i]=Integer.parseInt(empTime[i]);

                    }

                    OssHandler ossHandler = new OssHandler(EmpDetailMessageActivity.this,imv_header);
                    cache_image = new File(getCacheDir(), Base64.encodeToString(empPhoto.getBytes(), Base64.DEFAULT));
                    System.out.println("员工的图片缓存："+cache_image);
                    DownloadUtils.downloadFileFromOss(cache_image, ossHandler, ConfigOfOssClient.BUCKET_NAME, empPhoto);


                    tv_emp_name.setText(empName);
                    tv_emp_sex.setText(empSex);
                    tv_emp_birth.setText(empBirth);
                    tv_emp_phone.setText(empPhone);
                    for (int i = 0; i < mWeekItems.length; i++) {
                        LineChartData data = new LineChartData();
                        data.setItem(mWeekItems[i]);
                        data.setPoint(mWeekPoints[i]);
                        mWeekList.add(data);
                    }
                    mWeekLineChart.setData(mWeekList);

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

}
