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
    private String surface;
    private String Thumbnail_one;
    private String Thumbnail_three;
    private String Thumbnail_two;
    private boolean pass;
    private String avecon;

    public StoreInfo(int mer_id, String name, String classify, String introduce, String phone,
                     String address, String business_hours, String status, boolean reserve,
                     String surface, String thumbnail_one, String thumbnail_three, String
                             thumbnail_two, boolean pass,String avecon) {
        this.mer_id = mer_id;
        this.name = name;
        this.classify = classify;
        this.introduce = introduce;
        this.phone = phone;
        this.address = address;
        this.business_hours = business_hours;
        this.status = status;
        this.reserve = reserve;
        this.surface = surface;
        this.pass=pass;
        this.avecon=avecon;
        Thumbnail_one = thumbnail_one;
        Thumbnail_three = thumbnail_three;
        Thumbnail_two = thumbnail_two;
    }

    public boolean isPass() {
        return pass;
    }

    public void setPass(boolean pass) {
        this.pass = pass;
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

    public String getSurface() {
        return surface;
    }

    public void setSurface(String surface) {
        this.surface = surface;
    }

    public String getThumbnail_one() {
        return Thumbnail_one;
    }

    public void setThumbnail_one(String thumbnail_one) {
        Thumbnail_one = thumbnail_one;
    }

    public String getThumbnail_three() {
        return Thumbnail_three;
    }

    public void setThumbnail_three(String thumbnail_three) {
        Thumbnail_three = thumbnail_three;
    }

    public String getThumbnail_two() {
        return Thumbnail_two;
    }

    public void setThumbnail_two(String thumbnail_two) {
        Thumbnail_two = thumbnail_two;
    }

    public String getAvecon() {
        return avecon;
    }

    public void setAvecon(String avecon) {
        this.avecon = avecon;
    }
}
