package com.cottee.managerstore.bean;

import java.util.List;

/**
 * Created by Administrator on 2018/3/10.
 */

public class AllEmployeeGetInfo {
    /**"mer_list": [{
        "staff_id": "12_1",
                "name": "1",
                "photo": "aaaaa"
    }]**/

    private List<AllEmployeeGetInfo.EmployeeDetailBean> mer_list;

    public List<EmployeeDetailBean> getMer_list() {
        return mer_list;
    }

    public void setMer_list(List<EmployeeDetailBean> mer_list) {
        this.mer_list = mer_list;
    }

    public static class EmployeeDetailBean {


        public String staff_id;

        public String name;

        public String photo;

        public String getStaff_id() {
            return staff_id;
        }

        public void setStaff_id(String staff_id) {
            this.staff_id = staff_id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPhoto() {
            return photo;
        }

        public void setPhoto(String photo) {
            this.photo = photo;
        }
    }


}
