package com.cottee.managerstore.activity1;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.cottee.managerstore.R;
import com.cottee.managerstore.activity.ManageFoodDetailActivity;
import com.cottee.managerstore.bean.ProjectManageGetInfo;
import com.cottee.managerstore.bean.ProjectManageInfo;
import com.cottee.managerstore.handle.LoginRegisterInformationHandle;
import com.cottee.managerstore.httputils.HttpUtilSession;
import com.cottee.managerstore.manage.LoginRegisterInformationManage;
import com.cottee.managerstore.manage.ProjectTypeManage;
import com.cottee.managerstore.properties.Properties;
import com.cottee.managerstore.utils.BaseRefreshListener;
import com.cottee.managerstore.utils.ToastUtils;
import com.cottee.managerstore.widget.PopupMenu;
import com.cottee.managerstore.widget.PullToRefreshLayout;
import com.cottee.managerstore.widget.ShapeLoadingDialog;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import static com.cottee.managerstore.properties.Properties.PROJECT_MANAGE_GET;


/**
 * Created by Administrator on 2017/12/16.
 */

public class ProjectManageActivity extends AppCompatActivity implements View.OnClickListener {


    private static ProjectManageAdapter adapter;
    private ListView lvprojectmanage;
    public static Map<Integer, Boolean> checkedMap = new HashMap<Integer, Boolean>();
    private List<ProjectManageInfo> projectList = new ArrayList<>();
    private DishesDialog dialog;
    private List<String> addDishList = new ArrayList<>();
    private String addType = "";

    // .MODE_PRIVATE表示SharePrefences的数据只有自己应用程序能访问。


    private List<String> dishTypeNameList;
    private List<ProjectManageInfo> allTypeNameList = new ArrayList<>();
    private ProjectManageActivity.ProjectManageHandler handler = new ProjectManageActivity.ProjectManageHandler();
    private List<String> dishTypeIdList;
    private List<String> dishesExampleList = new ArrayList<>();
    private boolean up = false;
    private PullToRefreshLayout pullToRefreshLayout;
    private LinearLayout ll_empty;
    private ShapeLoadingDialog shapeLoadingDialog;
    private ImageView imv_back_to_storemanager;
    private PopupMenu popupMenu;
    private ImageView iv_menu;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_manage);
        initview();
        addDishList.clear();
        allTypeNameList.clear();

        String[] abs = new String[]{"添加菜品", "修改菜品",""};
        popupMenu = new PopupMenu(this,abs);



        shapeLoadingDialog = new ShapeLoadingDialog.Builder(this)
                .loadText("加载中...")
                .build();
        shapeLoadingDialog.show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                sendRequestWithOkHttp();

            }
        },2000);



        lvprojectmanage.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                String dishId = dishTypeIdList.get(position);
                String dishName = dishTypeNameList.get(position);
                Intent intent = new Intent(ProjectManageActivity.this,
                        ManageFoodDetailActivity.class);
                intent.putExtra("position",dishId);
                intent.putExtra("positionName",dishName);
                startActivity(intent);
//                ToastUtils.showToast(ProjectManageActivity.this,"Id"+dishId);
            }
        });


       /* pullToRefreshLayout.autoRefresh();*/

        pullToRefreshLayout.setRefreshListener(new BaseRefreshListener() {
            @Override
            public void refresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        sendRequestWithOkHttp();

                        pullToRefreshLayout.finishRefresh();
                    }
                },2000);
            }

            @Override
            public void loadMore() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        pullToRefreshLayout.finishLoadMore();
                    }
                },2000);
            }
        });





    }


    public void sendRequestWithOkHttp() {
        new Thread(new Runnable() {
            @Override
            public void run() {

                HttpUtilSession.sendSessionOkHttpRequest(ProjectManageActivity.this,Properties.PROJECT_MANAGE_GET_PATH, new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {

                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String responeData = response.body().string();
                        System.out.println("ProjectManageAC Json:" + responeData);


                        if(responeData.trim().equals("250")){
                            shapeLoadingDialog.setDismiss();
                            ToastUtils.showToast(ProjectManageActivity.this,"session无效效，正在重新登陆请稍等" );
                            new LoginRegisterInformationManage(ProjectManageActivity.this,new LoginRegisterInformationHandle()).againLogin();
                            /*sendRequestWithOkHttp();*/
                        }
                        else if (responeData.trim().equals("0")){

                        }
                        else {

                            parseJSONWithGSON(responeData);
                        }

                        /*parseJSONWithGSON(responeData);*/

                    }




                });
            }
        }).start();
    }


    private void parseJSONWithGSON(String jsonData) {
        //使用轻量级的Gson解析得到的json
        Gson gson = new Gson();
        List<ProjectManageGetInfo.DishTypeBean> dishInfo = gson.fromJson(jsonData, ProjectManageGetInfo.class).getClassify();


        System.out.println("MYT Type:" + dishInfo);

        //控制台输出结果，便于查看
        Message message = new Message();
        message.what = PROJECT_MANAGE_GET;
        message.obj = dishInfo;


        handler.sendMessage(message);



    }

    private String addData() {

        System.out.println("addDish:"+addDishList);
        for (int i = 0; i < addDishList.size(); i++) {

            String allDishType = addDishList.get(i);
            if (i == addDishList.size() - 1) {
                addType = addType + allDishType;

            } else {
                addType = addType + allDishType + "#";
            }

        }
        addDishList.clear();

        String add = addType.trim();
        addType = "";
        /*add = add.substring(0, -1);*/
        return add;
    }






    public class ProjectManageHandler extends Handler {
        private Context context;

        /*public ProjectManageHandler(Context context) {
            this.context = context;

        }*/

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case PROJECT_MANAGE_GET:
                    /*allTypeNameList.clear();*/
                    dishTypeNameList = new ArrayList<String>();
                    dishTypeIdList = new ArrayList<>();
                    List<ProjectManageGetInfo.DishTypeBean> dishList = (List<ProjectManageGetInfo.DishTypeBean>) msg.obj;

                    for (int i = 0; i < dishList.size(); i++) {
                        ProjectManageInfo manageInfo = new ProjectManageInfo(dishList.get(i).getName());
                        allTypeNameList.add(manageInfo);
                        dishTypeNameList.add(dishList.get(i).getName());
                        dishTypeIdList.add(dishList.get(i).getClass_id());

                    }
                    System.out.println(dishTypeNameList);
                    System.out.println(dishTypeIdList);
                    System.out.println(allTypeNameList);
                    adapter = new ProjectManageAdapter(ProjectManageActivity.this, dishTypeNameList);

                    if (!adapter.isEmpty()) {
                        ll_empty.setVisibility(View.GONE);

                    } else {


                        ll_empty.setVisibility(View.VISIBLE);
                    }

                    lvprojectmanage.setAdapter(adapter);
                    shapeLoadingDialog.setDismiss();


                    break;
            }
            super.handleMessage(msg);
        }
    }


    public void initview() {
        pullToRefreshLayout = (PullToRefreshLayout) findViewById(R.id.pt_push);
        imv_back_to_storemanager = (ImageView) findViewById(R.id.imv_back_to_storemanager);
        lvprojectmanage = (ListView) findViewById(R.id.lv_project_manage);
        ll_empty = (LinearLayout) findViewById(R.id.ll_empty);
        iv_menu = (ImageView) findViewById(R.id.iv_menu);
        imv_back_to_storemanager.setOnClickListener(this);



        iv_menu.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_menu:
                popupMenu.showLocation(R.id.iv_menu);// 设置弹出菜单弹出的位置
                // 设置回调监听，获取点击事件
                popupMenu.setOnItemClickListener(new PopupMenu.OnItemClickListener() {

                            @Override
                            public void onClick(PopupMenu.MENUITEM item, String str) {
                                switch(str){
                                    case "添加菜品":
                                        dialog = new DishesDialog(ProjectManageActivity.this);
                                        dialog.show();
                                        break;
                                    case "修改菜品":
                                        Intent intent = new Intent(ProjectManageActivity.this, ProjectManageAddClassifyActivity.class);

                                        System.out.println("MYT:" + (Serializable) dishTypeNameList);
                                        intent.putExtra("dishName", (Serializable) dishTypeNameList);
                                        intent.putExtra("dishId", (Serializable) dishTypeIdList);
                                        startActivity(intent);
                                        break;
                                    case "":
                                        break;
                                    default:
                                        break;
                                }


                            }
                        });
                break;


            case R.id.imv_back_to_storemanager:
                finish();
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
            if (convertview == null) {
                viewHolder = new ViewHolder();
                convertview = View.inflate(context, R.layout.item_project_manage, null);
                viewHolder.tvDishName = (TextView) convertview.findViewById(R.id.tv_item_project_manage_get_name);

                convertview.setTag(viewHolder);

            } else {
                viewHolder = (ViewHolder) convertview.getTag();
            }

            viewHolder.tvDishName.setText(projectManageList.get(position));


            return convertview;
        }


    }

    protected static class ViewHolder {

        TextView tvDishName;
        CheckBox dishExampleName;

    }




    public class DishesClassifyAdapter extends BaseAdapter {


        private Context context;
        private List<String> dishesList = new ArrayList<>();


        public DishesClassifyAdapter(Context context, List<String> dishesList) {
            this.context = context;

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
            if (convertview == null) {

                viewHolder = new ViewHolder();
                convertview = View.inflate(context, R.layout.item_gride_project_classify, null);
                viewHolder.dishExampleName = convertview.findViewById(R.id.cb_gride_item_project_manage_classify_dishes);

                convertview.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertview.getTag();
            }
            viewHolder.dishExampleName.setText(dishesList.get(position));
            viewHolder.dishExampleName.setChecked(checkedMap.get(position));
            viewHolder.dishExampleName.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if (checkedMap.get(position) == false) {
                        checkedMap.put(position, true);
                        setIsSelected(checkedMap);
                        dishesExampleList.add(dishesList.get(position));
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

    public Map<Integer, Boolean> getIsSelected() {
        return checkedMap;
    }

    public void setIsSelected(Map<Integer, Boolean> checkedMap) {
        this.checkedMap = checkedMap;
    }


    public class DishesDialog extends Dialog {
        private Context context;
        private GridView gridView;


        private DishesClassifyAdapter classifyAdapter;
        private List<String> exampleList = new ArrayList<>();
        private List<String> list = new ArrayList<>();


        public DishesDialog(@NonNull Context context) {
            super(context);
            this.context = context;


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
            classifyAdapter = new DishesClassifyAdapter(context, exampleList);
            gridView.setAdapter(classifyAdapter);
            initview();


            setCancelable(true);
            getWindow().setBackgroundDrawableResource(R.color.transparent);
            getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT);
       /* getWindow().setWindowAnimations(R.style.MyDialogAnimation);*/


        }


        public void initview() {

            findViewById(R.id.btn_project_manage_classify_save_dishes).setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {


                    list.clear();
                    System.out.println("info:"+allTypeNameList);
                    for (int i = 0; i < dishesExampleList.size(); i++) {

                        for (ProjectManageInfo projectInfo : allTypeNameList) {
                            if (dishesExampleList.get(i).equals(projectInfo.getProjectName())) {
                                ToastUtils.showToast(ProjectManageActivity.this, "亲，有的您已经选过啦/自定义重名了");
                                list.remove(dishesExampleList.get(i));
                                return;
                            }

                        }
                        System.out.println("最终" + dishesExampleList.get(i));
                        list.add(dishesExampleList.get(i));
                    }
                    dishesExampleList.clear();
                    /*allTypeNameList.clear();*/

                    for (int i = 0; i < list.size(); i++) {

                        ProjectManageInfo projectManageInfo = new ProjectManageInfo(list.get(i));
                        addDishList.add(list.get(i));
                        projectList.add(projectManageInfo);

                    }
                    String add = addData();
                    System.out.println("添加：" + add);

                    ProjectTypeManage manage = new ProjectTypeManage(ProjectManageActivity.this, new LoginRegisterInformationHandle
                            (shapeLoadingDialog));
                    if (!add.equals("")) {
                        SharedPreferences sp = getSharedPreferences("ProjectManage", Context.MODE_PRIVATE);//Context
                        SharedPreferences.Editor editor = sp.edit();
                        editor.putString("commit", add);
                        editor.commit();
                        /*shapeLoadingDialog.show();*/
                        manage.projectManageCommit(add);
                        pullToRefreshLayout.autoRefresh();
                    }
                    addDishList.clear();
                    list.clear();
                    dishesExampleList.clear();
                    dialog.dismiss();

                }
            });


            findViewById(R.id.btn_project_manage_classify_custom_classify).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                    final EditText et = new EditText(ProjectManageActivity.this);
                    new AlertDialog.Builder(ProjectManageActivity.this).setTitle("请添加菜品类型")
                            .setIcon(android.R.drawable.ic_dialog_info)
                            .setView(et)
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {

                                    dismiss();
                                    String input = et.getText().toString();

                                    if (input.equals("")) {
                                        Toast.makeText(getApplicationContext(), "输入不能为空！", Toast.LENGTH_SHORT).show();
                                        return;
                                    } else {

                                        for (ProjectManageInfo projectInfo : allTypeNameList) {
                                            if (input.equals(projectInfo.getProjectName())) {
                                                ToastUtils.showToast(ProjectManageActivity.this, "菜品类型已存在");
                                                return;
                                            }
                                        }

                                        ProjectManageInfo projectManageInfo = new ProjectManageInfo(input);
                                        /*addDishList.add(input);*/
                                        projectList.add(projectManageInfo);


                                        System.out.println("添加：" + input);

                                        ProjectTypeManage manage = new ProjectTypeManage(ProjectManageActivity.this, new LoginRegisterInformationHandle
                                                (ProjectManageActivity.this, ""));
                                        if (!input.equals("")) {
                                            SharedPreferences sp = getSharedPreferences("ProjectManage", Context.MODE_PRIVATE);//Context
                                            SharedPreferences.Editor editor = sp.edit();
                                            editor.putString("commit", input);
                                            editor.commit();
                                            manage.projectManageCommit(input);
                                            pullToRefreshLayout.autoRefresh();
                                        }

                                        projectList.clear();
                                        list.clear();


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

            finish();
            return false;
        } else {
            return super.onKeyDown(keyCode, event);
        }

    }





    @Override
    public void onPause() {

        super.onPause();

        up = true;//不可见的时候将刷新开启
    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();

//判断是否刷新

        if (up) {

            pullToRefreshLayout.autoRefresh();

            up = false;

        }

    }



}
