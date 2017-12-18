package com.cottee.managerstore.bean;

import android.graphics.Bitmap;

import java.io.Serializable;
import java.util.List;

/**
 * Created by user on 2017/12/10.
 */

public class StoreInfo implements Serializable{
    private int mer_id;
    private String name;
    private String classify;
    private String introduce;
    private String phone;
    private String address;
    private String business_hours;
    private String status;
    private boolean reserve;
    private String pho_url;

    public StoreInfo(String name, String classify, String introduce, String phone, String address, String business_hours, String status, boolean reserve, String pho_url) {
        this.name = name;
        this.classify = classify;
        this.introduce = introduce;
        this.phone = phone;
        this.address = address;
        this.business_hours = business_hours;
        this.status = status;
        this.reserve = reserve;
        this.pho_url = pho_url;
    }

    public int getMer_id() {
        return mer_id;
    }

    public void setMer_id(int mer_id) {
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean isReserve() {
        return reserve;
    }

    public void setReserve(boolean reserve) {
        this.reserve = reserve;
    }

    public String getPho_url() {
        return pho_url;
    }

    public void setPho_url(String pho_url) {
        this.pho_url = pho_url;
    }
}
