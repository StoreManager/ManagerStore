package com.cottee.managerstore.bean;

import android.graphics.Bitmap;

import java.util.List;

/**
 * Created by user on 2017/12/10.
 */

public class StoreInfo {
    private String storeName;
    private String storeStyle;
    private String storeAddress;
    private String storePhoneNumber;
    private String minimumPay;//人均消费
    private String autograph;//个性签名
    private String time;
    private boolean isPobulish;//是否发布
    private boolean isOpen;//是否打烊
    private Bitmap mainCover;//封面图片
    private List<Bitmap> bitmapList;//其他图片

    public StoreInfo( String storeName, String storeStyle, String storeAddress, String storePhoneNumber, String minimumPay, String autograph, String time, boolean isPobulish, boolean isOpen, Bitmap mainCover, List<Bitmap> bitmapList) {
        this.storeName = storeName;
        this.storeStyle = storeStyle;
        this.storeAddress = storeAddress;
        this.storePhoneNumber = storePhoneNumber;
        this.minimumPay = minimumPay;
        this.autograph = autograph;
        this.time = time;
        this.isPobulish = isPobulish;
        this.isOpen = isOpen;
        this.mainCover = mainCover;
        this.bitmapList = bitmapList;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getStoreStyle() {
        return storeStyle;
    }

    public void setStoreStyle(String storeStyle) {
        this.storeStyle = storeStyle;
    }

    public String getStoreAddress() {
        return storeAddress;
    }

    public void setStoreAddress(String storeAddress) {
        this.storeAddress = storeAddress;
    }

    public String getStorePhoneNumber() {
        return storePhoneNumber;
    }

    public void setStorePhoneNumber(String storePhoneNumber) {
        this.storePhoneNumber = storePhoneNumber;
    }

    public String getMinimumPay() {
        return minimumPay;
    }

    public void setMinimumPay(String minimumPay) {
        this.minimumPay = minimumPay;
    }

    public String getAutograph() {
        return autograph;
    }

    public void setAutograph(String autograph) {
        this.autograph = autograph;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public boolean isPobulish() {
        return isPobulish;
    }

    public void setPobulish(boolean pobulish) {
        isPobulish = pobulish;
    }

    public boolean isOpen() {
        return isOpen;
    }

    public void setOpen(boolean open) {
        isOpen = open;
    }

    public Bitmap getMainCover() {
        return mainCover;
    }

    public void setMainCover(Bitmap mainCover) {
        this.mainCover = mainCover;
    }

    public List<Bitmap> getBitmapList() {
        return bitmapList;
    }

    public void setBitmapList(List<Bitmap> bitmapList) {
        this.bitmapList = bitmapList;
    }
}
