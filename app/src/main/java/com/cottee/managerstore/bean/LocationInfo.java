package com.cottee.managerstore.bean;

import com.tencent.lbssearch.object.Location;

/**
 * Created by user on 2017/11/16.
 */

public class LocationInfo {
    private String title;
    private String address;
    private Location location;

    public LocationInfo(String title, String address, Location location) {
        this.title = title;
        this.address = address;
        this.location = location;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
}
