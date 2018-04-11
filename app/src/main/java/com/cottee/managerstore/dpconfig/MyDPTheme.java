package com.cottee.managerstore.dpconfig;

import android.graphics.Color;

import cn.aigestudio.datepicker.bizs.themes.DPTheme;

/**
 * Created by 37444 on 2018/3/31.
 */

public class MyDPTheme extends DPTheme {
    public MyDPTheme() {
        colorBG();
        colorBGCircle();
        colorF();
        colorG();
        colorHoliday();
        colorTitle();
        colorTitleBG();
        colorToday();
        colorWeekend();
    }

    @Override
    public int colorBG() {
        return Color.WHITE;
    }

    @Override
    public int colorBGCircle() {
        return 0xd84862af;
    }

    @Override
    public int colorTitleBG() {
        return 0xf225449e;
    }

    @Override
    public int colorTitle() {
        return Color.WHITE;
    }

    @Override
    public int colorToday() {
        return 0xf225449e;
    }

    @Override
    public int colorG() {
        return Color.BLACK;
    }

    @Override
    public int colorF() {
        return Color.WHITE;
    }

    @Override
    public int colorWeekend() {
        return Color.RED;
    }

    @Override
    public int colorHoliday() {
        return Color.RED;
    }
}
