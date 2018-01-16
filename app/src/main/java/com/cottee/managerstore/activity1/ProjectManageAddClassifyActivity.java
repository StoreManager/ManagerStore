package com.cottee.managerstore.activity1;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.cottee.managerstore.R;
import com.cottee.managerstore.bean.ProjectManageInfo;
import com.cottee.managerstore.handle.LoginRegisterInformationHandle;
import com.cottee.managerstore.manage.ProjectTypeManage;
import com.cottee.managerstore.utils.ToastUtils;
import com.cottee.managerstore.widget.DragListViewAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/12/23.
 */

public class ProjectManageAddClassifyActivity extends AppCompatActivity implements View.OnClickListener {

    private Toolbar tbprojectmanageadd;
    private List<ProjectManageInfo> projectList = new ArrayList<>();
    /*private List<ProjectManageInfo> projectTestList = new ArrayList<>();*/
    private List<String> addDishList = new ArrayList<>();
    private List<String> updateDishList = new ArrayList<>();
    private List<String> updateDishIdList = new ArrayList<>();
    private List<String> deleteIdList = new ArrayList<>();
    private ListView lvprojectmanageadd;
    private Button btnprojectmanageaddclassifysave;
    private Button btnprojectmanageclassifyadd;
    private ProjectManageAddAdapter adapter;

    public static Map<Integer, Boolean> checkedMap = new HashMap<Integer, Boolean>();
    private Button btn_back_to_project_manage_from_add;
    private List<String> dishesExampleList = new ArrayList<>();
    private List<String> allDishTypeList = new ArrayList<>();

    private String updateType="";
    private String updateIdType="";
    private String deleteType="";
    private List<String> jsonDishName;
    private List<String> jsonDishId;
    private List<String> newJsonIdList;
    private LinearLayout ll_add_empty;
    /*private ProjectManageHandler handler = new ProjectManageHandler();*/

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_manage_add_classify);
        initview();
        tbprojectmanageadd.setBackgroundColor(getResources().getColor(R.color.purplishblue));
        setSupportActionBar(tbprojectmanageadd);


        Intent intent = getIntent();
        jsonDishName = (List<String>) intent.getSerializableExtra("dishName");
        jsonDishId = (List<String>) intent.getSerializableExtra("dishId");

        System.out.println("json"+ jsonDishName);
        System.out.println("json"+ jsonDishId);



        updateDishList.clear();
        updateDishIdList.clear();
        deleteIdList.clear();


        for(int i = 0; i< jsonDishName.size(); i++){
            ProjectManageInfo info = new ProjectManageInfo(jsonDishName.get(i));

            projectList.add(info);
            /*projectTestList.add(info);*/
        }




        adapter = new ProjectManageAddAdapter(this,projectList);
        if(!adapter.isEmpty()){
            ll_add_empty.setVisibility(View.GONE);

        }else {
            ll_add_empty.setVisibility(View.VISIBLE);
        }
        lvprojectmanageadd.setAdapter(adapter);


    }




    public void initview(){
        tbprojectmanageadd = (Toolbar) findViewById(R.id.tb_project_manage_add);
        lvprojectmanageadd = (ListView) findViewById(R.id.lv_project_manage_add);
        btnprojectmanageaddclassifysave = (Button) findViewById(R.id.btn_project_manage_add_classify_save);
     /*   btnprojectmanageclassifyadd = (Button) findViewById(R.id.btn_project_manage_classify_add);*/
        btn_back_to_project_manage_from_add = (Button) findViewById(R.id.btn_back_to_project_manage_from_add);
        ll_add_empty = (LinearLayout) findViewById(R.id.ll_add_empty);
        btnprojectmanageaddclassifysave.setOnClickListener(this);
       /* btnprojectmanageclassifyadd.setOnClickListener(this);*/
        btn_back_to_project_manage_from_add.setOnClickListener(this);
    }








    private String updateData(){
        for (int i = 0; i< updateDishList.size(); i++){

            String updateDishType = updateDishList.get(i);
            if(i == updateDishList.size()-1 ){
                updateType = updateType+updateDishType;

            }else{
                updateType = updateType+updateDishType+"#";
            }

        }
        String update = updateType.trim();
        /*update = update.substring(0, -1);*/
        return update;
    }
    private String updateIdData(){
        for (int i = 0; i< updateDishIdList.size(); i++){

            String updateDishType = updateDishIdList.get(i);
            if(i == updateDishIdList.size()-1 ){
                updateIdType = updateIdType+updateDishType;

            }else{
                updateIdType = updateIdType+updateDishType+"#";
            }

        }
        String update = updateIdType.trim();
        /*update = update.substring(0, -1);*/
        return update;
    }
    private String deleteIdData(){
        for (int i = 0; i< deleteIdList.size(); i++){

            String updateDishType = deleteIdList.get(i);
            if(i == deleteIdList.size()-1 ){
                deleteType = deleteType+updateDishType;

            }else{
                deleteType = deleteType+updateDishType+"#";
            }

        }
        String update = deleteType.trim();
        /*update = update.substring(0, -1);*/
        return update;
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
        case R.id.btn_project_manage_add_classify_save:

            String update = updateData();
            String updateId = updateIdData();
            String deleteId = deleteIdData();



            System.out.println("更新名字:"+update);
            System.out.println("更新id:"+updateId);
            System.out.println("删除id:"+deleteId);
            ProjectTypeManage manage = new ProjectTypeManage(ProjectManageAddClassifyActivity.this,new LoginRegisterInformationHandle
                    (ProjectManageAddClassifyActivity.this,""));
            /*if(!add.equals("")){
                manage.projectManageCommit(add);
               *//* sendRequestWithOkHttp();*//*
            }*/
            if (!update.equals("")&&!updateId.equals("")){
                manage.projectManageUpdate(update,updateId);
            }
            if(!deleteId.equals("")){
                manage.projectManageDelete(deleteId);
            }

            Intent intent = new Intent(ProjectManageAddClassifyActivity.this,ProjectManageActivity.class);
            startActivity(intent);
            finish();




        break;
        /*case R.id.btn_project_manage_classify_add:

            *//*dialog = new DishesDialog(ProjectManageAddClassifyActivity.this,adapter);
            dialog.show();*//*



        break;*/

        case R.id.btn_back_to_project_manage_from_add:

            if(!jsonDishName.equals(allDishTypeList)){
                new AlertDialog.Builder(ProjectManageAddClassifyActivity.this).setTitle("是否放弃本次修改？")
                        .setIcon(android.R.drawable.ic_dialog_info)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                finish();

                            }
                        })
                        .setNegativeButton("取消", null)
                        .show();
            }else {
                finish();
            }


            break;


        }
    }


    public class ProjectManageAddAdapter extends DragListViewAdapter<ProjectManageInfo> {

        private Context context;
       /* private List<ProjectManageInfo> projectManageList;*/
        private String projectName;


        public ProjectManageAddAdapter(Context context, List<ProjectManageInfo> projectManageList) {
            super(context,projectManageList);
            /*this.context = context;
            this.projectManageList = projectManageList;*/


        }

       /* @Override
        public int getCount() {
            return projectManageList.size();
        }

        *//*@Override
        public Object getItem(int i) {
            return projectManageList.get(i);
        }
*//*
        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(final int position, View convertview, ViewGroup viewGroup) {



        }*/

        @Override
        public View getItemView(final int position, View convertview, ViewGroup parent) {

            final ViewHolder viewHolder;
            if(convertview==null){
                viewHolder = new ViewHolder();
                convertview = LayoutInflater.from(getApplicationContext()).inflate( R.layout.item_project_manage_add, parent,false  );
                viewHolder.tvItemName = (TextView) convertview.findViewById(R.id.tv_item_project_manage_classify_name);
                viewHolder.btnItemUpdate = (Button) convertview.findViewById(R.id.btn_item_project_manage_classify_update);
                viewHolder.btnItemDelete = (Button) convertview.findViewById(R.id.btn_item_project_manage_classify_delete);
                convertview.setTag(viewHolder);

            }else{
                viewHolder = (ViewHolder)convertview.getTag();
            }

            if(projectManageList.get(position).getProjectName().length()<10){
                viewHolder.tvItemName.setText(projectManageList.get(position).getProjectName());
            }else{
                viewHolder.tvItemName.setText(projectManageList.get(position).getProjectName().substring(0,9)
                        +".....");
            }


            viewHolder.btnItemDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    new AlertDialog.Builder(ProjectManageAddClassifyActivity.this).setTitle("确定删除此菜品？")
                            .setIcon(android.R.drawable.ic_dialog_info)
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {


                                    projectManageList.remove(position);
                                    adapter.notifyDataSetChanged();
                                    if(position+1<=jsonDishId.size()){
                                        deleteIdList.add(jsonDishId.get(position));
                                        jsonDishId.remove(jsonDishId.get(position));

                                    }
                                    System.out.println("删除的是:"+deleteIdList);

                                    if(!adapter.isEmpty()){
                                        ll_add_empty.setVisibility(View.GONE);

                                    }else {
                                        ll_add_empty.setVisibility(View.VISIBLE);
                                    }
                                }
                            })
                            .setNegativeButton("取消", null)
                            .show();
                }
            });




            viewHolder.btnItemUpdate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    projectName = projectManageList.get(position).getProjectName();
                    Log.d("ssss","名字是："+projectName);
                    final EditText et = new EditText(ProjectManageAddClassifyActivity.this);
                    et.setText(projectName);
                    new AlertDialog.Builder(ProjectManageAddClassifyActivity.this).setTitle("请修改菜品分类名称")
                            .setIcon(android.R.drawable.ic_dialog_info)
                            .setView(et)
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {

                                    String input = et.getText().toString();

                                    if (input.equals("")) {
                                        Toast.makeText(getApplicationContext(), "输入不能为空！" , Toast.LENGTH_SHORT).show();
                                        return;
                                    }
                                    else {
                                        for (ProjectManageInfo projectInfo : projectList)
                                        {
                                            if (input.equals(projectInfo.getProjectName())) {
                                                ToastUtils.showToast(ProjectManageAddClassifyActivity.this,"亲，修改的菜品类型已存在了");
                                                return;
                                            }
                                        }

                                        projectManageList.get(position).setProjectName(input);
                                        adapter.notifyDataSetChanged();
                                        if(position+1<=jsonDishId.size()){
                                            updateDishIdList.add(jsonDishId.get(position));
                                            updateDishList.add(input);
                                        }


                                        System.out.println("修改的是:"+updateDishIdList+" "+updateDishList);


                                    }
                                }
                            })
                            .setNegativeButton("取消", null)
                            .show();
                }
            });


            allDishTypeList.add(projectManageList.get(position).getProjectName());


            return convertview;


        }


    }
    protected  static   class ViewHolder{

        TextView tvItemName;
        Button btnItemUpdate;
        Button btnItemDelete;


    }





    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {

            if(!jsonDishName.equals(allDishTypeList)){
                new AlertDialog.Builder(ProjectManageAddClassifyActivity.this).setTitle("是否放弃本次修改？")
                        .setIcon(android.R.drawable.ic_dialog_info)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                                finish();
                            }
                        })
                        .setNegativeButton("取消", null)
                        .show();
            }else {
                finish();
            }

            return false;
        }else {
            return super.onKeyDown(keyCode, event);
        }

    }



}
