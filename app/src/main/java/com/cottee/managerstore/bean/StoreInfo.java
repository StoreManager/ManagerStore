package com.cottee.managerstore.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by user on 2017/12/10.
 */

public class StoreInfo implements Serializable {

    /**
     * mer_id : 1
     * name : 老板娘烤肉店
     * classify : 聚餐宴请
     * introduce : 位于北京宣武门内大街的烤肉宛和什刹海北岸的烤肉季，是北京最负盛名两家烤肉店。两店一南一北，素有南宛北季之称。烤肉宛始建于康熙二十五年。最初是店主宛某带着伙计，手推小车，上置烤肉炙子，在宣武门到西革一带沿街售卖烤肉。咸丰年间，在宣武门内大街设立了固定的门面，专营烤牛肉。烤好的牛肉质嫩味鲜，馨香诱人。烤肉季开业于同治未年，店主叫季德彩，起初在风景优美的什刹海银锭桥边设摊卖烤肉。到了1920 年，才在什刹海北岸建立了店铺，专营烤羊肉。烤出的羊肉含浆滑美，令人久食不厌。
     * phone : 15831454246
     * address : 辽宁省大连市旅顺口区旅顺盐场海鲜街45号（大连医科大学、大连外国语学院）
     * business_hours : 08:00-20:00
     * status : true
     * reserve : true
     * pass : true
     * avecon : 0
     * surface : https://thethreestooges.cn/merchant/img/photo_mer/1/surface.jpg
     * environment : ["https://thethreestooges.cn/merchant/img/photo_mer/1/environment/1.jpg","https://thethreestooges.cn/merchant/img/photo_mer/1/environment/2.jpg","https://thethreestooges.cn/merchant/img/photo_mer/1/environment/3.jpg"]
     */

    private String mer_id;
    private String name;
    private String classify;
    private String introduce;
    private String phone;
    private String address;
    private String business_hours;
    private boolean status;
    private boolean reserve;
    private boolean pass;
    private String avecon;
    private String surface;
    private List<String> environment;

    public String getMer_id() {
        return mer_id;
    }

    public void setMer_id(String mer_id) {
        this.mer_id = mer_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getClassify() {
        return classify;
    }

    public void setClassify(String classify) {
        this.classify = classify;
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBusiness_hours() {
        return business_hours;
    }

    public void setBusiness_hours(String business_hours) {
        this.business_hours = business_hours;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public boolean isReserve() {
        return reserve;
    }

    public void setReserve(boolean reserve) {
        this.reserve = reserve;
    }

    public boolean isPass() {
        return pass;
    }

    public void setPass(boolean pass) {
        this.pass = pass;
    }

    public String getAvecon() {
        return avecon;
    }

    public void setAvecon(String avecon) {
        this.avecon = avecon;
    }

    public String getSurface() {
        return surface;
    }

    public void setSurface(String surface) {
        this.surface = surface;
    }

    public List<String> getEnvironment() {
        return environment;
    }

    public void setEnvironment(List<String> environment) {
        this.environment = environment;
    }
}

