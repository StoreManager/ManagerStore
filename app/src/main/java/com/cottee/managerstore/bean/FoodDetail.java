package com.cottee.managerstore.bean;

import java.util.List;

/**
 * Created by Administrator on 2017-12-26.
 */

public class FoodDetail {
    private List<ItemListBean> item_list;

    public List<ItemListBean> getItem_list() {
        return item_list;
    }

    public void setItem_list(List<ItemListBean> item_list) {
        this.item_list = item_list;
    }

    public static class ItemListBean {
        /**
         * class_id : 44
         * item_id : 54
         * name : 米饭
         * photo :
         sweet/person/portrait/chinn96@163.com.png
         * univalence : 1
         * description : 米饭，是中国人日常饮食中的主角之一，中国南方主食。米饭可与五味调配，几乎可以供给全身所需营养
         */

        private String class_id;
        private String item_id;
        private String name;
        private String photo;
        private String univalence;
        private String description;
        private String discount_singe;
        private String discount;
        public String getDiscount_singe() {
            return discount_singe;
        }

        public void setDiscount_singe(String discount_singe) {
            this.discount_singe = discount_singe;
        }

        public String getDiscount() {
            return discount;
        }

        public void setDiscount(String discount) {
            this.discount = discount;
        }
        public String getClass_id() {
            return class_id;
        }

        public void setClass_id(String class_id) {
            this.class_id = class_id;
        }

        public String getItem_id() {
            return item_id;
        }

        public void setItem_id(String item_id) {
            this.item_id = item_id;
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

        public String getUnivalence() {
            return univalence;
        }

        public void setUnivalence(String univalence) {
            this.univalence = univalence;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }
    }
    private boolean isSelect;

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }
}
