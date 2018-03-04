package com.cottee.managerstore.bean;

/**
 * Created by user on 2018/2/26.
 */

public class VIPStandard {
    private String standardName;//会员名称
    private String min;//最小积分
    private String level_introd;//等级描述
    private String number;

    public VIPStandard(String standardName, String min, String level_introd) {
        this.standardName = standardName;
        this.min = min;
        this.level_introd = level_introd;
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

    public String getLevel_introd() {
        return level_introd;
    }

    public void setLevel_introd(String level_introd) {
        this.level_introd = level_introd;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
