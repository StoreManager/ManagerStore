package com.cottee.managerstore.view;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.cottee.managerstore.R;
import com.cottee.managerstore.adapter.ManageMoneyListViewAdapter;
import com.cottee.managerstore.wheelwidget.AbstractWheelTextAdapter1;
import com.cottee.managerstore.wheelwidget.OnWheelChangedListener;
import com.cottee.managerstore.wheelwidget.OnWheelScrollListener;
import com.cottee.managerstore.wheelwidget.WheelView;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Administrator on 2017/10/7.
 */

public class DateTimeWheelDialog extends Dialog {
    private Context context;

    private LinearLayout ll_reg;
    private WheelView wv_year;
    private WheelView wv_month;
    private WheelView wv_day;

    public static String selectYear = "";
    public static String selectMonth = "";
    private String selectDay = "";
    private boolean isScrolled = false;

    private static ArrayList<String> arry_years = new ArrayList<String>();
    private static ArrayList<String> arry_months = new ArrayList<String>();
    public static ArrayList<String> arry_days = new ArrayList<String>();

    private CalendarTextAdapter mYearAdapter;
    private CalendarTextAdapter mMonthAdapter;
//    private CalendarTextAdapter mDaydapter;


    private onClickListener onClickListener;

    private static final int MAXTEXTSIZE = 20;
    private static final int MINTEXTSIZE = 14;


    public DateTimeWheelDialog(@NonNull Context context) {
        this(context, R.style.MyDialogStyle);
    }

    public DateTimeWheelDialog(@NonNull Context context, @StyleRes int
            themeResId) {
        super(context, themeResId);
        this.context = context;
    }

    protected DateTimeWheelDialog(@NonNull Context context, boolean
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
        setContentView(R.layout.layout_choosetime);
        setCancelable(false);
        getWindow().setBackgroundDrawableResource(R.color.transparent);
        getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);

        initView();

        initializeData();

        initEvent();

    }

    private void initView() {
        ll_reg = (LinearLayout) findViewById(R.id.ll_reg);
        wv_year = (WheelView) findViewById(R.id.wv_year);
        wv_month = (WheelView) findViewById(R.id.wv_month);
//        wv_day = (WheelView) findViewById(R.id.wv_day);
    }

    private void initializeData() {

        if (selectDay.equals("") && selectMonth.equals("") && selectYear.equals
                ("")) {
            selectYear = getSystemYear();
            selectMonth = getSystemMonth();
            selectDay = getSystemDay();
        }
        mYearAdapter = new CalendarTextAdapter(context, arry_years, getPositionByYearWheel(selectYear), MAXTEXTSIZE, MINTEXTSIZE);
        wv_year.setVisibleItems(5);
        wv_year.setViewAdapter(mYearAdapter);
        wv_year.setCurrentItem(getPositionByYearWheel(selectYear));

        mMonthAdapter = new CalendarTextAdapter(context, arry_months, getPositionByMonthWheel(selectMonth), MAXTEXTSIZE, MINTEXTSIZE);
        wv_month.setVisibleItems(5);
        wv_month.setViewAdapter(mMonthAdapter);
        wv_month.setCurrentItem(getPositionByMonthWheel(selectMonth));
        wv_month.setCyclic(true);

        initializeDays(calculateCurrentDays(selectYear, selectMonth));
////        mDaydapter = new CalendarTextAdapter(context, arry_days, Integer.parseInt(selectDay) - 1, MAXTEXTSIZE, MINTEXTSIZE);
//        wv_day.setVisibleItems(5);
//        wv_day.setViewAdapter(mDaydapter);
//        wv_day.setCurrentItem(Integer.parseInt(selectDay) - 1);
//        wv_day.setCyclic(true);

    }

    private void initEvent() {
        wv_year.addChangingListener(new OnWheelChangedListener() {

            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                // TODO Auto-generated method stub
                String currentText = (String) mYearAdapter.getItemText(wheel
                        .getCurrentItem());
                selectYear = currentText.substring(0, currentText.length() - 1).toString();
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
                selectYear = currentText.substring(0, currentText.length() - 1).toString();
                setTextviewSize(currentText, mYearAdapter);
//                resetDayWheelView();
                isScrolled = false;

            }
        });
        wv_month.addChangingListener(new OnWheelChangedListener() {
            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                // TODO Auto-generated method stub
                String currentText = (String) mMonthAdapter.getItemText(wheel.getCurrentItem());
                selectMonth = currentText.substring(0, currentText.length() - 1);
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
                selectMonth = currentText.substring(0, currentText.length() - 1);
                setTextviewSize(currentText, mMonthAdapter);
//                resetDayWheelView();
                isScrolled = false;
            }

        });
//        wv_day.addChangingListener(new OnWheelChangedListener() {
//
//            @Override
//            public void onChanged(WheelView wheel, int oldValue, int newValue) {
//                // TODO Auto-generated method stub
//                String currentText = (String) mDaydapter.getItemText(wheel
//                        .getCurrentItem());
//                setTextviewSize(currentText, mDaydapter);
//            }
//        });
//        wv_day.addScrollingListener(new OnWheelScrollListener() {
//
//            @Override
//            public void onScrollingStarted(WheelView wheel) {
//                // TODO Auto-generated method stub
//                isScrolled = true;
//            }
//
//            @Override
//            public void onScrollingFinished(WheelView wheel) {
//                // TODO Auto-generated method stub
//                String currentText = (String) mDaydapter.getItemText(wheel
//                        .getCurrentItem());
//                setTextviewSize(currentText, mDaydapter);
//                selectDay = currentText.substring(0, currentText.length() - 1);
//                isScrolled = false;
//
//            }
//        });

        ll_reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v == ll_reg) {
                    if (onClickListener != null) {
                        if (isScrolled) return;

                        if (Integer.parseInt(selectYear) > Integer.parseInt
                                (getSystemYear())
                                || Integer.parseInt(selectYear)
                                == Integer.parseInt(getSystemYear()) && Integer
                                .parseInt(selectMonth) > Integer.parseInt(getSystemMonth()
                        ) ||
                                Integer.parseInt(selectYear) == Integer
                                        .parseInt(getSystemYear()) && Integer
                                        .parseInt(selectMonth) == Integer
                                        .parseInt(getSystemMonth()) && Integer
                                        .parseInt(selectDay) > Integer
                                        .parseInt(getSystemDay())) {
                            selectYear = getSystemYear();
                            selectMonth = getSystemMonth();
                            selectDay = getSystemDay();
                        }

                        onClickListener.onClick(selectYear + "-" + selectMonth);

                    }
                } else {

                    dismiss();
                    ((Activity) context).finish();
                }
                dismiss();
            }
        });

    }

    public void initializeDays(int days) {
        arry_days.clear();
        for (int i = 1; i <= days; i++) {
            arry_days.add(i + "日");

        }

    }

    public DateTimeWheelDialog setSelectedDate(String registerTime) {
        if (!registerTime.equals("") && registerTime != null) {
            String[] split = registerTime.split("[-]");
            selectYear = split[0];
            selectMonth = split[1];
            selectDay = split[2];
        }
        return this;
    }

//
//    private void resetDayWheelView() {
//        initializeDays(calculateCurrentDays(selectYear, selectMonth));
//        mDaydapter = new CalendarTextAdapter(context, arry_days, 0,
//                MAXTEXTSIZE, MINTEXTSIZE);
//        wv_day.setViewAdapter(mDaydapter);
//        wv_day.setCurrentItem(0);
//    }

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
    public static int calculateCurrentDays(String year, String month) {
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

    public interface onClickListener {
        void onClick(String dateTime);
    }

    public void setOnClickListener(onClickListener onClickListener) {
        this.onClickListener = onClickListener;
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

