package com.cottee.managerstore.bean;

/**
 * Created by Administrator on 2018/3/11.
 */
//单个员工
public class SingleEmployeeInfo {
    /**{"staff_info":{"name":"1","sex":"男","birth":"1996-03-11","phone_number":"15141433601","photo":"aaaaa","time_list":[{"overtime":180222,
    "time_sum":"2.1531"},{"overtime":180219,"time_sum":"2.1531"},{"overtime":180218,"time_sum":"2.1531"}]}}**/

    /*private List<EmployeeInfoBean> staff_info;

    public List<EmployeeInfoBean> getStaff_info() {
        return staff_info;
    }

    public void setStaff_info(List<EmployeeInfoBean> staff_info) {
        this.staff_info = staff_info;
    }*/

    /*public static class EmployeeInfoBean {*/


        public String name;

        public String sex;

        public String birth;

        public String phone_number;

        public String photo;

        public String[] time_list;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public String getBirth() {
            return birth;
        }

        public void setBirth(String birth) {
            this.birth = birth;
        }

        public String getPhone_number() {
            return phone_number;
        }

        public void setPhone_number(String phone_number) {
            this.phone_number = phone_number;
        }

        public String getPhoto() {
            return photo;
        }

        public void setPhoto(String photo) {
            this.photo = photo;
        }

        public String[] getTime_list() {
            return time_list;
        }

        public void setTime_list(String[] time_list) {
            this.time_list = time_list;
        }


}
