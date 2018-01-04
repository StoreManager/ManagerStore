package com.cottee.managerstore.activity1;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;

import com.cottee.managerstore.R;
import com.cottee.managerstore.activity.StoreManagerActivity;
import com.cottee.managerstore.bean.ProjectManageInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/12/16.
 */

public class ProjectManageActivity extends AppCompatActivity implements View.OnClickListener{


    private Toolbar toolbar;
    private ListView lvprojectmanage;
    private List<ProjectManageInfo> projectList = new ArrayList<>();
    private List<String> projectManageStringList = new ArrayList<String>();
    private ProjectManageAdapter adapter;
    private boolean flage;
    public static Map<Integer, Boolean> checkedBoxMap = new HashMap<Integer, Boolean>();
    private Button btnbacktostoremanager;
    private Button btnprojectmanageadd;
    private ImageView imv_project_manage_empty;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_manage);
        initview();

        toolbar.setBackgroundColor(getResources().getColor(R.color.purplishblue));
        setSupportActionBar(toolbar);
        adapter = new ProjectManageAdapter(this,projectList);
        if(!adapter.isEmpty()){
            imv_project_manage_empty.setVisibility(View.GONE);

        }else {
            imv_project_manage_empty.setVisibility(View.VISIBLE);
        }


        lvprojectmanage.setAdapter(adapter);




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
            startActivity(intent);
            break;

        default:
        break;
        }

    }



    /*@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
        case R.id.project_manage_update:
        break;
        case R.id.project_manage_add:
            final EditText et = new EditText(ProjectManageActivity.this);

            new AlertDialog.Builder(ProjectManageActivity.this).setTitle("请输入项目名字")
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
                                        ToastUtils.showToast(ProjectManageActivity.this,"项目名字已存在");
                                        return;
                                    }
                                }

                                ProjectManageInfo projectManageInfo = new ProjectManageInfo(input);
                                projectList.add(projectManageInfo);
                                projectManageStringList.add(input);

                                adapter.notifyDataSetChanged();


                                ProjectTypeManage projecttypemanage = new ProjectTypeManage(ProjectManageActivity.this, new
                                        LoginRegisterInformationHandle
                                        (ProjectManageActivity
                                        .this, ""));

                                for (int i = 0; i< projectManageStringList.size(); i++){
                                    projecttypemanage.projectManageCommit(projectManageStringList.get(i));
                                    Log.d("------------","提交时："+ projectManageStringList.get(i));
                                }



                            }
                        }
                    })
                    .setNegativeButton("取消", null)
                    .show();
        break;
        case R.id.project_manage_delete:

            if(adapter.getCount()<1){
                ToastUtils.showToast(this,"项目为空，不可删除");
                break;
            }


            flage=!flage;
            adapter.notifyDataSetChanged();


            btnprojectmanagedeletesubmit.setVisibility(View.VISIBLE);
            lvprojectmanage.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                    CheckBox cbselectphonecontactschecked = (CheckBox) view.findViewById(R.id.cb_selete_delete);
                    cbselectphonecontactschecked.toggle();
                    ProjectManageActivity.ViewHolder holder = (ProjectManageActivity.ViewHolder) view.getTag();
                    ProjectManageActivity.checkedBoxMap.put(position, holder.cbseletedelete.isChecked());
                }
            });



            break;

        default:
        break;
        }
        return super.onOptionsItemSelected(item);
    }*/




    public class ProjectManageAdapter extends BaseAdapter {

        private Context context;
        private List<ProjectManageInfo> projectManageList;


        public ProjectManageAdapter(Context context, List<ProjectManageInfo> projectManageList) {

            this.context = context;
            this.projectManageList = projectManageList;

            for (int i = 0; i < projectManageList.size(); i++) {
                checkedBoxMap.put(i, false);
            }


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
                viewHolder.cbseletedelete = (CheckBox) convertview.findViewById(R.id.cb_selete_delete);
                viewHolder.btnProjectName = (Button) convertview.findViewById(R.id.btn_project_manage_name);
                convertview.setTag(viewHolder);

            }else{
                viewHolder = (ViewHolder)convertview.getTag();
            }

            viewHolder.btnProjectName.setText(projectManageList.get(position).getProjectName());


            if(flage){
                viewHolder.cbseletedelete.setVisibility(View.VISIBLE);
                if(viewHolder.cbseletedelete.isChecked()){
                    viewHolder.cbseletedelete.setChecked(checkedBoxMap.get(position));
                }
            }
            else{
                viewHolder.cbseletedelete.setVisibility(View.GONE);}



            return convertview;
        }


    }
    protected  static   class ViewHolder{

        Button btnProjectName;
        CheckBox cbseletedelete;

    }



    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            Intent intent = new Intent(ProjectManageActivity.this, StoreManagerActivity.class);
            startActivity(intent);
            finish();
            return false;
        }else {
            return super.onKeyDown(keyCode, event);
        }

    }

}
