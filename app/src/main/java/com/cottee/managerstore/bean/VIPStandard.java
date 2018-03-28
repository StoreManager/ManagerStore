package com.cottee.managerstore.bean;

/**
 * Created by user on 2018/2/26.
 */

public class VIPStandard {
    /**
     * VIP_id : 1103
     * VIP_name : vip1
     * min_num : 300
     * discount : 0.8
     */
    private String VIP_id;
    private String VIP_name;
    private String min_num;
    private String discount;

    public String getVIP_id() {
        return VIP_id;
    }

    public void setVIP_id(String VIP_id) {
        this.VIP_id = VIP_id;
    }

    public String getVIP_name() {
        return VIP_name;
    }

    public void setVIP_name(String VIP_name) {
        this.VIP_name = VIP_name;
    }

    public String getMin_num() {
        return min_num;
    }

    public void setMin_num(String min_num) {
        this.min_num = min_num;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }
}
