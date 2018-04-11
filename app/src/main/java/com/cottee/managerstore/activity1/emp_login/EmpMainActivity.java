package com.cottee.managerstore.activity1.emp_login;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;

import com.cottee.managerstore.R;
import com.cottee.managerstore.activity.ManageFoodDetail1Activity;
import com.cottee.managerstore.activity1.EmpModifyPwdActivity;
import com.cottee.managerstore.widget.Title;

/**
 * Created by Administrator on 2018/3/19.
 */

public class EmpMainActivity extends Activity {

    private Title title;
    private Button btn_modify_pwd;
    private Button btn_food_manage;
    private Button btn_order_manage;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emp_main);
        initTitle();
        initView();
        initEvent();
    }

    private void initView() {
        btn_modify_pwd = findViewById(R.id.btn_modify_pwd);
        btn_food_manage = findViewById(R.id.btn_food_manage);
        btn_order_manage = findViewById(R.id.btn_order_manage);
    }
   private void initEvent()
    {
    btn_modify_pwd.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            startActivity(new Intent(EmpMainActivity.this,EmpModifyPwdActivity.class));
        }
    });
    btn_food_manage.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(EmpMainActivity.this,
                    ManageFoodDetail1Activity.class);
            startActivity(intent);
        }
    });
    btn_order_manage.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {

        }
    });
}
    private void initTitle() {
        title = (Title) findViewById(R.id.title);
        title.setTitleNameStr("Sweet员工");
        title.setTheme(Title.TitleTheme.THEME_TRANSLATE);
        title.mSetButtonInfo(new Title.ButtonInfo(true, Title
                .BUTTON_LEFT, 0, "退出登录"
        ));
        //可加button1
        title.mSetButtonInfo(new Title.ButtonInfo(true, Title
                .BUTTON_RIGHT1, 0,
                "个人信息"));
        title.setOnTitleButtonClickListener(new Title.OnTitleButtonClickListener() {
            @Override
            public void onClick(int id, Title.ButtonViewHolder viewHolder) {
                switch (id) {
                    case Title.BUTTON_RIGHT1:
                        Intent intent = new Intent(EmpMainActivity.this,EmpDetailMessageActivity.class);
                        startActivity(intent);

                        break;
                    case Title.BUTTON_LEFT:
                        AlertDialog.Builder builder = new AlertDialog.Builder(EmpMainActivity.this);
                        builder.setTitle("确定退出吗？");
                        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                               finish();

                            }
                        });

                        builder.setNegativeButton("取消", null);
                        builder.show();
                        break;
                }
            }
        });
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent home = new Intent(Intent.ACTION_MAIN);
            home.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            home.addCategory(Intent.CATEGORY_HOME);
            startActivity(home);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
    public void modifyEmp(View view)
    {
        startActivity(new Intent(EmpMainActivity.this, EmpModifyPwdActivity.class));
    }
}
