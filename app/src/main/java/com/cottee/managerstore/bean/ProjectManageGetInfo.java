package com.cottee.managerstore.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/12/24.
 */

public class ProjectManageGetInfo  {

/**    {"classify":[{"class_id":"1","name":"热菜"},{"class_id":"2","name":"甜品"},{"class_id":"3","name":"凉菜"},{"class_id":"4","name":"主食"},
 * {"class_id":"5","name":"汤品"},{"class_id":"6","name":"饮料"}]}*/

    private List<ProjectManageGetInfo.DishTypeBean> classify;

    /*public static Repair objectFromData(String str) {

        return new Gson().fromJson(str, Repair.class);
    }*/

    public List<DishTypeBean> getClassify() {
        return classify;
    }

    public void setClassify(List<DishTypeBean> classify) {
        this.classify = classify;
    }

    public static class DishTypeBean {


        public String class_id;

        public String name;

        public String getClass_id() {
            return class_id;
        }

        public void setClass_id(String class_id) {
            this.class_id = class_id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }


    }











}
