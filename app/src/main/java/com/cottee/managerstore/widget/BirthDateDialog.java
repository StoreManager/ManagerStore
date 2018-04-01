package com.cottee.managerstore.widget;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cottee.managerstore.R;
import com.cottee.managerstore.wheelwidget.AbstractWheelTextAdapter1;
import com.cottee.managerstore.wheelwidget.OnWheelChangedListener;
import com.cottee.managerstore.wheelwidget.OnWheelScrollListener;
import com.cottee.managerstore.wheelwidget.WheelView;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Administrator on 2017/10/7.
 */

public class BirthDateDialog extends Dialog {
    private static Context context;

    private LinearLayout ll_none;
    private WheelView wv_year;
    private WheelView wv_month;
    private WheelView wv_day;

    private String choosedYear = "";
    private String choosedMonth = "";
    private String choosedDay = "";
    private boolean isScrolled = false;

    private static ArrayList<String> arry_years = new ArrayList<String>();
    private static ArrayList<String> arry_months = new ArrayList<String>();
    private static ArrayList<String> arry_days = new ArrayList<String>();

    private CalendarTextAdapter mYearAdapter;
    private CalendarTextAdapter mMonthAdapter;
    private CalendarTextAdapter mDaydapter;


    private RegistrationDateListener RegistrationDateListener;

    private static final int MAXTEXTSIZE = 20;
    private static final int MINTEXTSIZE = 14;



    public BirthDateDialog(@NonNull Context context) {
        this(context, R.style.MyDialogStyle);
    }

    public BirthDateDialog(@NonNull Context context, @StyleRes int
            themeResId) {
        super(context, themeResId);
        this.context = context;
    }

    protected BirthDateDialog(@NonNull Context context, boolean
            cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    static {
        for (int i = 1980; i <= (Integer.parseInt(getSystemYear()) + 1000); i++) {
            arry_years.add(i + "年");
        }
        for (int i = 1; i <= 12; i++) {
            arry_months.add(i + "月");
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_registration_date);
        setCancelable(false);
        getWindow().setBackgroundDrawableResource(R.color.transparent);
        int screenHeight = getScreenHeight();
        int statusBarHeight = getStatusBarHeight();
        int dialogHeight = screenHeight - statusBarHeight;
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,
                dialogHeight == 0 ? ViewGroup.LayoutParams.MATCH_PARENT : dialogHeight);
        getWindow().setBackgroundDrawableResource(R.color.transparent);
        getWindow().setWindowAnimations(R.style.MyDialogAnimation);
        getWindow().setWindowAnimations(R.style.MyDialogAnimation);

        initView();

        initializeData();

        initEvent();

    }

    private void initView() {
        ll_none = (LinearLayout) findViewById(R.id.ll_none);
        wv_year = (WheelView) findViewById(R.id.wv_year);
        wv_month = (WheelView) findViewById(R.id.wv_month);
        wv_day = (WheelView) findViewById(R.id.wv_day);
    }
    private static int getScreenHeight() {
        DisplayMetrics displaymetrics = new DisplayMetrics();
        ((Activity)context).getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        return displaymetrics.heightPixels;
    }

    private static int getStatusBarHeight() {
        int statusBarHeight = 0;
        Resources res = context.getResources();
        int resourceId = res.getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            statusBarHeight = res.getDimensionPixelSize(resourceId);
        }
        return statusBarHeight;
    }

    private void initializeData() {

        if (choosedDay.equals("") || choosedMonth.equals("") || choosedYear.equals
                ("")) {
            choosedYear = getSystemYear();
            choosedMonth = getSystemMonth();
            choosedDay = getSystemDay();
        }
        mYearAdapter = new CalendarTextAdapter(context, arry_years, getPositionByYearWheel(choosedYear), MAXTEXTSIZE, MINTEXTSIZE);
        wv_year.setVisibleItems(5);
        wv_year.setViewAdapter(mYearAdapter);
        wv_year.setCurrentItem(getPositionByYearWheel(choosedYear));
        wv_year.setCyclic(true);


        mMonthAdapter = new CalendarTextAdapter(context, arry_months, getPositionByMonthWheel(choosedMonth), MAXTEXTSIZE, MINTEXTSIZE);
        wv_month.setVisibleItems(5);
        wv_month.setViewAdapter(mMonthAdapter);
        wv_month.setCurrentItem(getPositionByMonthWheel(choosedMonth));
        wv_month.setCyclic(true);

        initializeDays(calculateCurrentDays(choosedYear, choosedMonth));
        mDaydapter = new CalendarTextAdapter(context, arry_days, Integer.parseInt(choosedDay) - 1, MAXTEXTSIZE, MINTEXTSIZE);
        wv_day.setVisibleItems(5);
        wv_day.setViewAdapter(mDaydapter);
        wv_day.setCurrentItem(Integer.parseInt(choosedDay) - 1);
        wv_day.setCyclic(true);

    }

    private void initEvent() {
        wv_year.addChangingListener(new OnWheelChangedListener() {

            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                // TODO Auto-generated method stub
                String currentText = (String) mYearAdapter.getItemText(wheel
                        .getCurrentItem());
                choosedYear = currentText.substring(0, currentText.length() - 1).toString();
                setTextviewSize(currentText, mYearAdapter);

            }
        });
        wv_year.addScrollingListener(new OnWheelScrollListener() {
            @Override
            public void onScrollingStarted(WheelView wheel) {
                // TODO Auto-generated method stub
                //--------------判断时间轴是否滚动------------------------------
                isScrolled = true;
                //------------------------------------------------------------

            }

            @Override
            public void onScrollingFinished(WheelView wheel) {
                // TODO Auto-generated method stub
                String currentText = (String) mYearAdapter.getItemText(wheel
                        .getCurrentItem());
                choosedYear = currentText.substring(0, currentText.length() - 1).toString();
                setTextviewSize(currentText, mYearAdapter);
                resetDayView();
                isScrolled = false;

            }
        });
        wv_month.addChangingListener(new OnWheelChangedListener() {
            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                // TODO Auto-generated method stub
                String currentText = (String) mMonthAdapter.getItemText(wheel.getCurrentItem());
                choosedMonth = currentText.substring(0, currentText.length() - 1);
                setTextviewSize(currentText, mMonthAdapter);
            }
        });
        wv_month.addScrollingListener(new OnWheelScrollListener() {
            @Override
            public void onScrollingStarted(WheelView wheel) {
                // TODO Auto-generated method stub
                //--------------判断时间轴是否滚动------------------------------
                isScrolled = true;
            }

            @Override
            public void onScrollingFinished(WheelView wheel) {
                // TODO Auto-generated method stub
                String currentText = (String) mMonthAdapter.getItemText(wheel
                        .getCurrentItem());
                choosedMonth = currentText.substring(0, currentText.length() - 1);
                setTextviewSize(currentText, mMonthAdapter);
                resetDayView();
                isScrolled = false;
            }

        });
        wv_day.addChangingListener(new OnWheelChangedListener() {

            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                // TODO Auto-generated method stub
                String currentText = (String) mDaydapter.getItemText(wheel
                        .getCurrentItem());
                setTextviewSize(currentText, mDaydapter);
            }
        });
        wv_day.addScrollingListener(new OnWheelScrollListener() {

            @Override
            public void onScrollingStarted(WheelView wheel) {
                // TODO Auto-generated method stub
                isScrolled = true;
            }

            @Override
            public void onScrollingFinished(WheelView wheel) {
                // TODO Auto-generated method stub
                String currentText = (String) mDaydapter.getItemText(wheel
                        .getCurrentItem());
                setTextviewSize(currentText, mDaydapter);
                choosedDay = currentText.substring(0, currentText.length() - 1);
                isScrolled = false;

            }
        });

        ll_none.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v == ll_none) {
                    if (RegistrationDateListener != null) {
                        if (isScrolled) return;

                        if (Integer.parseInt(choosedYear) > Integer.parseInt
                                (getSystemYear())
                                || Integer.parseInt(choosedYear)
                                == Integer.parseInt(getSystemYear()) && Integer
                                .parseInt(choosedMonth) > Integer.parseInt(getSystemMonth()
                        ) ||
                                Integer.parseInt(choosedYear) == Integer
                                        .parseInt(getSystemYear()) && Integer
                                        .parseInt(choosedMonth) == Integer
                                        .parseInt(getSystemMonth()) && Integer
                                        .parseInt(choosedDay) > Integer
                                        .parseInt(getSystemDay())) {
                            choosedYear = getSystemYear();
                            choosedMonth = getSystemMonth();
                            choosedDay = getSystemDay();
                        }

                        RegistrationDateListener.onClick(choosedYear + "-" + choosedMonth + "-" + choosedDay);

                    }
                } else {

                    dismiss();
                    ((Activity) context).finish();
                }
                dismiss();
            }
        });

    }

    private void initializeDays(int days) {
        arry_days.clear();
        for (int i = 1; i <= days; i++) {
            arry_days.add(i + "日");

        }

    }

    public BirthDateDialog setChoosedDate(String registerTime) {
        if (!registerTime.equals("") && registerTime != null) {
            String[] split = registerTime.split("[-]");
            choosedYear = split[0];
            choosedMonth = split[1];
            choosedDay = split[2];
        }
        return this;
    }


    private void resetDayView() {
        initializeDays(calculateCurrentDays(choosedYear, choosedMonth));
        mDaydapter = new CalendarTextAdapter(context, arry_days, 0,
                MAXTEXTSIZE, MINTEXTSIZE);
        wv_day.setViewAdapter(mDaydapter);
        wv_day.setCurrentItem(0);
    }

    /**
     * 设置年份
     *
     * @param year
     */
    private int getPositionByYearWheel(String year) {
        int yearIndex = 0;
         for (int i = 1980; i <= (Integer.parseInt(getSystemYear()) + 1314); i++) {
            if (i == Integer.parseInt(year)) {
                return yearIndex;
            }
            yearIndex++;
        }
        return yearIndex;
    }

    /**
     * 设置月份
     *
     * @param month
     * @param month
     * @return
     */
    private int getPositionByMonthWheel(String month) {
        int monthIndex = 0;

        for (int i = 1; i < 12; i++) {
            if (i == Integer.parseInt(month)) {
                return monthIndex;
            }
            monthIndex++;
        }
        return monthIndex;
    }

    /**
     * 计算每月多少天
     *
     * @param month
     * @param year
     */
    private int calculateCurrentDays(String year, String month) {
        int day = 0;
        boolean leayYear = false;
        if (Integer.parseInt(year) % 4 == 0 && Integer.parseInt(year) % 100 != 0) {
            leayYear = true;
        } else {
            leayYear = false;
        }
        switch (Integer.parseInt(month)) {
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
                day = 31;
                break;
            case 2:
                if (leayYear) {
                    day = 29;
                } else {
                    day = 28;
                }
                break;
            case 4:
            case 6:
            case 9:
            case 11:
                day = 30;
                break;
        }

        return day;
    }

    /**
     * 设置字体大小
     *
     * @param curriteItemText
     * @param adapter
     */
    private void setTextviewSize(String curriteItemText, CalendarTextAdapter
            adapter) {
        ArrayList<View> arrayList = adapter.getTestViews();
        int size = arrayList.size();
        String currentText;
        for (int i = 0; i < size; i++) {
            TextView textvew = (TextView) arrayList.get(i);
            currentText = textvew.getText().toString();
            if (curriteItemText.equals(currentText)) {
                textvew.setTextSize(MAXTEXTSIZE);
            } else {
                textvew.setTextSize(MINTEXTSIZE);
            }
        }
    }


    //String变成int修改其余逻辑 方便对比数据
    private static String getSystemYear() {
        Calendar c = Calendar.getInstance();
        return c.get(Calendar.YEAR) + "";
    }

    private String getSystemMonth() {
        Calendar c = Calendar.getInstance();
        return c.get(Calendar.MONTH) + 1 + "";
    }

    private String getSystemDay() {
        Calendar c = Calendar.getInstance();
        return c.get(Calendar.DAY_OF_MONTH) + "";
    }

    public interface RegistrationDateListener {
        void onClick(String dateTime);
    }

    public void getRegistrationDateListener(RegistrationDateListener RegistrationDateListener) {
        this.RegistrationDateListener = RegistrationDateListener;
    }

    private class CalendarTextAdapter extends AbstractWheelTextAdapter1 {
        ArrayList<String> list;

        protected CalendarTextAdapter(Context context, ArrayList<String>
                list, int currentItem, int maxsize, int minsize) {
            super(context, R.layout.layout_timewheel_present_time,
                    NO_RESOURCE, currentItem, maxsize, minsize);
            this.list = list;
            setItemTextResource(R.id.tempValue);
        }

        @Override
        public View getItem(int index, View cachedView, ViewGroup parent) {
            View view = super.getItem(index, cachedView, parent);
            return view;
        }

        @Override
        public int getItemsCount() {
            return list.size();
        }

        @Override
        protected CharSequence getItemText(int index) {
            return list.get(index) + "";
        }
    }
}

