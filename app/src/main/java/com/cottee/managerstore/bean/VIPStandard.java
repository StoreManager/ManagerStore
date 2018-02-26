package com.cottee.managerstore.bean;

/**
 * Created by user on 2018/2/26.
 */

public class VIPStandard {
    private String standardName;//会员名称
    private String min;//最小积分
    private String max;//最大积分
    private String number;

    public VIPStandard(String standardName, String min, String max) {
        this.standardName = standardName;
        this.min = min;
        this.max = max;
    }

    public String getStandardName() {
        return standardName;
    }

    public void setStandardName(String standardName) {
        this.standardName = standardName;
    }

    public String getMin() {
        return min;
    }

    public void setMin(String min) {
        this.min = min;
    }

    public String getMax() {
        return max;
    }

    public void setMax(String max) {
        this.max = max;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
