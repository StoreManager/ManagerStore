package com.cottee.managerstore.bean;

/**
 * Created by user on 2018/2/26.
 */

public class VIPStandard {
    private String currentLevel;//当前等级
    private String standardName;//会员名称
    private String min;//最小积分
    private String number;
    private String discount;//折扣

    public VIPStandard(String currentLevel, String standardName, String min, String discount) {
        this.currentLevel = currentLevel;
        this.standardName = standardName;
        this.min = min;
        this.discount = discount;
    }

    public String getCurrentLevel() {
        return currentLevel;
    }

    public void setCurrentLevel(String currentLevel) {
        this.currentLevel = currentLevel;
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

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }
}
