package com.cottee.managerstore.activity1;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.cottee.managerstore.R;
import com.cottee.managerstore.activity1.order_fragment.HandlingOrderFragment;
import com.cottee.managerstore.activity1.order_fragment.HistoryOrderFragment;
import com.cottee.managerstore.activity1.order_fragment.StoreOrderFragment;
import com.cottee.managerstore.activity1.order_fragment.WaitOrderFragment;
import com.cottee.managerstore.adapter.order_adapter.ViewPagerAdapter;
import com.cottee.managerstore.widget.Title;
import com.cottee.managerstore.widget.bar.NavitationLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/4/5.
 */

public class OrdersManageActivity extends AppCompatActivity {

    private NavitationLayout navitationLayout;
    private ViewPager viewPager1;
    private String[] titles1 = new String[]{"网店预约", "店内候单", "正在处理", "历史订单"};
    private ViewPagerAdapter viewPagerAdapter1;
    private List<Fragment> fragments1;
    private Title title;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders_manage);
        initTitle();
        initView();
        viewPager1 = (ViewPager) findViewById(R.id.viewpager1);

        navitationLayout = (NavitationLayout) findViewById(R.id.bar1);
        fragments1 =  new ArrayList<>();
        fragments1.add(new StoreOrderFragment());
        fragments1.add(new WaitOrderFragment());
        fragments1.add(new HandlingOrderFragment());
        fragments1.add(new HistoryOrderFragment());
        viewPagerAdapter1 = new ViewPagerAdapter(getSupportFragmentManager(), fragments1);
        viewPager1.setAdapter(viewPagerAdapter1);


        navitationLayout.setViewPager(this, titles1, viewPager1, R.color.gray4, R.color.purplishblue, 16, 16, 0, 0, true);
        navitationLayout.setBgLine(this, 1, R.color.gray);
        navitationLayout.setNavLine(this, 2, R.color.purplishblue, 0);
    }

    private void initView() {

    }

    private void initTitle(){
        title = (Title)findViewById(R.id.title);
        title.setTitleNameStr("订单管理");
        title.setTheme(Title.TitleTheme.THEME_TRANSLATE);
        title.mSetButtonInfo(new Title.ButtonInfo(true, Title
                .BUTTON_LEFT, R.mipmap.back_2x,null
        ));
        //可加button1
        /*title.mSetButtonInfo(new Title.ButtonInfo(true, Title
                .BUTTON_RIGHT1, 0,
                "添加员工"));*/
        title.setOnTitleButtonClickListener(new Title.OnTitleButtonClickListener() {
            @Override
            public void onClick(int id, Title.ButtonViewHolder viewHolder) {
                switch (id) {
                    /*case Title.BUTTON_RIGHT1:

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
                        break;*/
                    case Title.BUTTON_LEFT:
                        finish();
                        break;
                }
            }
        });

    }
}
