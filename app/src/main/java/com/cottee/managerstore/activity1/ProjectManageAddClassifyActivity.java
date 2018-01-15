package com.cottee.managerstore.activity1;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.GridView;
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
    private DishesDialog dialog;
    public static Map<Integer, Boolean> checkedMap = new HashMap<Integer, Boolean>();
    private Button btn_back_to_project_manage_from_add;
    private TextView tv_msgEmpty;
    private List<String> dishesExampleList = new ArrayList<>();
    private List<String> allDishTypeList = new ArrayList<>();

    private String updateType="";
    private String updateIdType="";
    private String deleteType="";
    private List<String> jsonDishName;
    private List<String> jsonDishId;
    private List<String> newJsonIdList;
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
            tv_msgEmpty.setVisibility(View.GONE);

        }else {
            tv_msgEmpty.setVisibility(View.VISIBLE);
        }
        lvprojectmanageadd.setAdapter(adapter);


    }


    /*public  class ProjectManageHandler extends Handler {
        private  Context context;

        *//*public ProjectManageHandler(Context context) {
            this.context = context;

        }*//*

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case PROJECT_MANAGE_GET:
                    *//*dishTypeNameList = new ArrayList<String>();*//*
                    newJsonIdList = new ArrayList<>();
                    List<ProjectManageGetInfo.DishTypeBean> dishList = (List<ProjectManageGetInfo.DishTypeBean>) msg.obj;

                    for(int i=0;i<dishList.size();i++){
                        newJsonIdList.add(dishList.get(i).getClass_id());


                    }
                    System.out.println("newid："+newJsonIdList);

                    break;
            }
            super.handleMessage(msg);
        }
    }*/

    public void initview(){
        tbprojectmanageadd = (Toolbar) findViewById(R.id.tb_project_manage_add);
        lvprojectmanageadd = (ListView) findViewById(R.id.lv_project_manage_add);
        btnprojectmanageaddclassifysave = (Button) findViewById(R.id.btn_project_manage_add_classify_save);
        btnprojectmanageclassifyadd = (Button) findViewById(R.id.btn_project_manage_classify_add);
        btn_back_to_project_manage_from_add = (Button) findViewById(R.id.btn_back_to_project_manage_from_add);
        tv_msgEmpty = (TextView) findViewById(R.id.imv_project_manage_empty);
        btnprojectmanageaddclassifysave.setOnClickListener(this);
        btnprojectmanageclassifyadd.setOnClickListener(this);
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



            finish();




        break;
        case R.id.btn_project_manage_classify_add:

            /*dialog = new DishesDialog(ProjectManageAddClassifyActivity.this,adapter);
            dialog.show();*/



        break;

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
                                    }
                                    System.out.println("删除的是:"+deleteIdList);

                                    if(!adapter.isEmpty()){
                                        tv_msgEmpty.setVisibility(View.GONE);

                                    }else {
                                        tv_msgEmpty.setVisibility(View.VISIBLE);
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
        CheckBox dishExampleName;

    }





    public class DishesClassifyAdapter extends BaseAdapter {


        private Context context;
        private ProjectManageAddClassifyActivity.ProjectManageAddAdapter adapter ;
        private List<String> dishesList = new ArrayList<>();


        public DishesClassifyAdapter(Context context, ProjectManageAddClassifyActivity.ProjectManageAddAdapter adapter,List<String> dishesList) {
            this.context = context;
            this.adapter = adapter;
            this.dishesList = dishesList;



            for (int i = 0; i < dishesList.size(); i++) {
                checkedMap.put(i, false);
            }
        }


        @Override
        public int getCount() {
            return dishesList.size();
        }

        @Override
        public Object getItem(int position) {
            return dishesList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertview, ViewGroup viewGroup) {

            final ViewHolder viewHolder;
            if(convertview==null){

                viewHolder = new ViewHolder();
                convertview = View.inflate(context, R.layout.item_gride_project_classify, null);
                viewHolder.dishExampleName=convertview.findViewById(R.id.cb_gride_item_project_manage_classify_dishes);

                convertview.setTag(viewHolder);
            }else {
                viewHolder = (ViewHolder) convertview.getTag();
            }
            viewHolder.dishExampleName.setText(dishesList.get(position));
            viewHolder.dishExampleName.setChecked(checkedMap.get(position));
            viewHolder.dishExampleName.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if (checkedMap.get(position)==false) {
                        checkedMap.put(position, true);
                        setIsSelected(checkedMap);
                        dishesExampleList.add(dishesList.get(position)) ;
                        System.out.println(dishesList.get(position));

                    } else {
                        checkedMap.put(position, false);
                        setIsSelected(checkedMap);
                        dishesExampleList.remove(dishesList.get(position));
                        System.out.println(dishesList.get(position));
                    }
                }
            });




            return convertview;
        }


    }

    public  Map<Integer, Boolean> getIsSelected() {
        return checkedMap;
    }

    public  void setIsSelected(Map<Integer, Boolean> checkedMap) {
        this.checkedMap = checkedMap;
    }




    public class DishesDialog extends Dialog {
        private Context context;
        private GridView gridView;
        private ProjectManageAddClassifyActivity.ProjectManageAddAdapter adapter;

        private DishesClassifyAdapter classifyAdapter;
        private List<String> exampleList = new ArrayList<>();
        private List<String> list = new ArrayList<>();


        public DishesDialog(@NonNull Context context, ProjectManageAddClassifyActivity.ProjectManageAddAdapter adapter) {
            super(context);
            this.context = context;
            this.adapter = adapter;


        }

        public DishesDialog(@NonNull Context context, int themeResId) {
            super(context, themeResId);
        }

        protected DishesDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
            super(context, cancelable, cancelListener);
        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.dialog_project_manage_classify);


            exampleList.add("热菜");
            exampleList.add("凉菜");
            exampleList.add("汤品");
            exampleList.add("甜品");
            exampleList.add("主食");
            exampleList.add("饮料");


            gridView = (GridView) findViewById(R.id.gv_project_manage_classify_example);
            classifyAdapter = new DishesClassifyAdapter(context,adapter, exampleList);
            gridView.setAdapter(classifyAdapter);
            initview();





            setCancelable(true);
            getWindow().setBackgroundDrawableResource(R.color.transparent);
            getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT);
       /* getWindow().setWindowAnimations(R.style.MyDialogAnimation);*/



        }


        public void initview(){

            findViewById(R.id.btn_project_manage_classify_save_dishes).setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {


                    list.clear();
                    for (int i = 0;i<dishesExampleList.size();i++){

                        for (ProjectManageInfo projectInfo : projectList)
                        {
                            if (dishesExampleList.get(i).equals(projectInfo.getProjectName())) {
                                ToastUtils.showToast(ProjectManageAddClassifyActivity.this,"亲，有的您已经选过啦/自定义重名了");
                                list.remove(dishesExampleList.get(i));
                                return;
                            }

                        }
                            System.out.println("最终"+dishesExampleList.get(i));
                            list.add(dishesExampleList.get(i));
                    }
                    dishesExampleList.clear();

                    for(int i = 0;i<list.size();i++){

                        ProjectManageInfo projectManageInfo = new ProjectManageInfo(list.get(i));
                        addDishList.add(list.get(i));
                        projectList.add(projectManageInfo);

                        adapter.notifyDataSetChanged();
                        if(!adapter.isEmpty()){
                            tv_msgEmpty.setVisibility(View.GONE);

                        }else {
                            tv_msgEmpty.setVisibility(View.VISIBLE);
                        }
                    }
                    list.clear();
                    dishesExampleList.clear();
                    dialog.dismiss();

                }
            });



            findViewById(R.id.btn_project_manage_classify_custom_classify).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                    final EditText et = new EditText(ProjectManageAddClassifyActivity.this);
                    new AlertDialog.Builder(ProjectManageAddClassifyActivity.this).setTitle("请添加菜品类型")
                    .setIcon(android.R.drawable.ic_dialog_info)
                    .setView(et)
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                            dismiss();
                            String input = et.getText().toString();

                            if (input.equals("")) {
                                Toast.makeText(getApplicationContext(), "输入不能为空！" , Toast.LENGTH_SHORT).show();
                                return;
                            }
                            else {

                                for (ProjectManageInfo projectInfo : projectList)
                                {
                                    if (input.equals(projectInfo.getProjectName())) {
                                        ToastUtils.showToast(ProjectManageAddClassifyActivity.this,"菜品类型已存在");
                                        return;
                                    }
                                }

                                ProjectManageInfo projectManageInfo = new ProjectManageInfo(input);
                                addDishList.add(input);
                                projectList.add(projectManageInfo);



                                list.clear();

                                adapter.notifyDataSetChanged();
                                if(!adapter.isEmpty()){
                                    tv_msgEmpty.setVisibility(View.GONE);

                                }else {
                                    tv_msgEmpty.setVisibility(View.VISIBLE);
                                }

                            }
                        }
                    })
                    .setNegativeButton("取消", null)
                    .show();
                }
            });


            findViewById(R.id.btn_dialog_exit).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    list.clear();
                    dishesExampleList.clear();
                    dialog.dismiss();
                }
            });
            findViewById(R.id.ll_dialog_none).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    list.clear();
                    dishesExampleList.clear();
                    dialog.dismiss();
                }
            });
        }
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
