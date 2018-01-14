package com.cottee.managerstore.activity1;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.cottee.managerstore.R;
import com.cottee.managerstore.bean.ProjectManageGetInfo;
import com.cottee.managerstore.bean.ProjectManageInfo;
import com.cottee.managerstore.httputils.HttpUtils;
import com.cottee.managerstore.properties.Properties;
import com.google.gson.Gson;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static com.cottee.managerstore.properties.Properties.PROJECT_MANAGE_GET;


/**
 * Created by Administrator on 2017/12/16.
 */

public class ProjectManageActivity extends AppCompatActivity implements View.OnClickListener{


    private static ProjectManageAdapter adapter;
    private Toolbar toolbar;
    private ListView lvprojectmanage;
    private List<ProjectManageInfo> projectList = new ArrayList<>();


    private Button btnbacktostoremanager;
    private Button btnprojectmanageadd;
    private ImageView imv_project_manage_empty;
    private List<String> dishTypeNameList;
    private ProjectManageActivity.ProjectManageHandler handler = new ProjectManageActivity.ProjectManageHandler();
    private List<String> dishTypeIdList;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_manage);
        initview();

        toolbar.setBackgroundColor(getResources().getColor(R.color.purplishblue));
        setSupportActionBar(toolbar);


        sendRequestWithOkHttp();



    }








    public void sendRequestWithOkHttp() {
        new Thread(new Runnable() {
            @Override
            public void run() {

                HttpUtils.sendOkHttpRequest(Properties.PROJECT_MANAGE_GET_PATH, new Callback() {
                    @Override
                    public void onFailure(Request request, IOException e) {

                    }

                    @Override
                    public void onResponse(Response response) throws IOException {
                        String responeData = response.body().string();

                        System.out.println(responeData);
                        parseJSONWithGSON(responeData);

                    }
                });
            }
        }).start();
    }


    private void parseJSONWithGSON(String jsonData) {
        //使用轻量级的Gson解析得到的json
        Gson gson = new Gson();
        List<ProjectManageGetInfo.DishTypeBean> dishInfo = gson.fromJson(jsonData, ProjectManageGetInfo.class).getClassify();



        System.out.println("MYT Type:"+dishInfo);

        //控制台输出结果，便于查看
        Message message = new Message();
        message.what = PROJECT_MANAGE_GET;
        message.obj = dishInfo;

        handler.sendMessage(message);





    }



    public  class ProjectManageHandler extends Handler {
        private  Context context;

        /*public ProjectManageHandler(Context context) {
            this.context = context;

        }*/

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case PROJECT_MANAGE_GET:
                    dishTypeNameList = new ArrayList<String>();
                    dishTypeIdList = new ArrayList<>();
                    List<ProjectManageGetInfo.DishTypeBean> dishList = (List<ProjectManageGetInfo.DishTypeBean>) msg.obj;

                    for(int i=0;i<dishList.size();i++){
                        dishTypeNameList.add(dishList.get(i).getName());
                        dishTypeIdList.add(dishList.get(i).getClass_id());


                    }
                    System.out.println(dishTypeNameList);
                    adapter = new ProjectManageAdapter(ProjectManageActivity.this,dishTypeNameList);

                    if(!adapter.isEmpty()){
                        imv_project_manage_empty.setVisibility(View.GONE);

                    }else {
                        imv_project_manage_empty.setVisibility(View.VISIBLE);
                    }

                    lvprojectmanage.setAdapter(adapter);


                    break;
            }
            super.handleMessage(msg);
        }
    }






    public void initview(){
        toolbar = findViewById(R.id.tl_project_manage);
        btnbacktostoremanager = (Button) findViewById(R.id.btn_back_to_storemanager);
        lvprojectmanage = (ListView) findViewById(R.id.lv_project_manage);
        btnprojectmanageadd = (Button) findViewById(R.id.btn_project_manage_add);
        imv_project_manage_empty = (ImageView) findViewById(R.id.imv_project_manage_empty);
        btnbacktostoremanager.setOnClickListener( this);
        btnprojectmanageadd.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
        case R.id.btn_back_to_storemanager:
            finish();
            break;

        case R.id.btn_project_manage_add:
            Intent intent = new Intent(ProjectManageActivity.this,ProjectManageAddClassifyActivity.class);

            System.out.println("MYT:"+(Serializable) dishTypeNameList);
            intent.putExtra("dishName",(Serializable) dishTypeNameList);
            intent.putExtra("dishId",(Serializable)dishTypeIdList);
            startActivity(intent);
            break;

        default:
        break;
        }

    }

    public class ProjectManageAdapter extends BaseAdapter {

        private Context context;
        private List<String> projectManageList;


        public ProjectManageAdapter(Context context, List<String> projectManageList) {

            this.context = context;
            this.projectManageList = projectManageList;




        }

        @Override
        public int getCount() {
            return projectManageList.size();
        }

        @Override
        public Object getItem(int i) {
            return projectManageList.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int position, View convertview, ViewGroup viewGroup) {


            ViewHolder viewHolder;
            if(convertview==null){
                viewHolder = new ViewHolder();
                convertview = View.inflate(context, R.layout.item_project_manage, null);
                viewHolder.tvDishName = (TextView) convertview.findViewById(R.id.tv_item_project_manage_get_name);

                convertview.setTag(viewHolder);

            }else{
                viewHolder = (ViewHolder)convertview.getTag();
            }

            viewHolder.tvDishName.setText(projectManageList.get(position));


            return convertview;
        }


    }
    protected  static   class ViewHolder{

        TextView tvDishName;


    }



    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {

            finish();
            return false;
        }else {
            return super.onKeyDown(keyCode, event);
        }

    }

}
