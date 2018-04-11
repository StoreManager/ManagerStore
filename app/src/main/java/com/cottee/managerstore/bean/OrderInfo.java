package com.cottee.managerstore.bean;

import java.util.List;

public class OrderInfo {

    private List<IndentListNoOkBean> indent_list_no_ok;

    public List<IndentListNoOkBean> getIndent_list_no_ok() {
        return indent_list_no_ok;
    }

    public void setIndent_list_no_ok(List<IndentListNoOkBean> indent_list_no_ok) {
        this.indent_list_no_ok = indent_list_no_ok;
    }

    public static class IndentListNoOkBean {
        /**
         * indent_id : 505048
         * create_time : 2018-04-09 21:34:21
         * trolley_list : {"list":[{"disNum":1,"goodsName":"炸大份鸡","pay":"46"},{"disNum":6,"goodsName":"披萨","pay":"36"},{"disNum":1,"goodsName":"煎饺","pay":"15"},{"disNum":1,"goodsName":"小馒头","pay":"2.5"},{"disNum":1,"goodsName":"橙汁","pay":"29"}],"merId":"5","pay":"¥ 308.50"}
         * indent_info : {"dis":"哈哈哈哈哈哈哈","name":"成本","peopleNum":"5","phone":"18340817282","time":"4-13  10:00-11:00"}
         */

        private String indent_id;
        private String create_time;
        private TrolleyListBean trolley_list;
        private IndentInfoBean indent_info;
        private String table_number = "餐桌号";
        private String waiter_name = "暂无";
        private String order_info;

        public String getOrder_info() {
            return order_info;
        }

        public void setOrder_info(String order_info) {
            this.order_info = order_info;
        }

        public String getTable_number() {
            return table_number;
        }

        public void setTable_number(String table_number) {
            this.table_number = table_number;
        }

        public String getWaiter_name() {
            return waiter_name;
        }

        public void setWaiter_name(String waiter_name) {
            this.waiter_name = waiter_name;
        }

        public String getIndent_id() {
            return indent_id;
        }

        public void setIndent_id(String indent_id) {
            this.indent_id = indent_id;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public TrolleyListBean getTrolley_list() {
            return trolley_list;
        }

        public void setTrolley_list(TrolleyListBean trolley_list) {
            this.trolley_list = trolley_list;
        }

        public IndentInfoBean getIndent_info() {
            return indent_info;
        }

        public void setIndent_info(IndentInfoBean indent_info) {
            this.indent_info = indent_info;
        }

        public static class TrolleyListBean {
            /**
             * list : [{"disNum":1,"goodsName":"炸大份鸡","pay":"46"},{"disNum":6,"goodsName":"披萨","pay":"36"},{"disNum":1,"goodsName":"煎饺","pay":"15"},{"disNum":1,"goodsName":"小馒头","pay":"2.5"},{"disNum":1,"goodsName":"橙汁","pay":"29"}]
             * merId : 5
             * pay : ¥ 308.50
             */

            private String merId;
            private String pay;
            private List<ListBean> list;

            public String getMerId() {
                return merId;
            }

            public void setMerId(String merId) {
                this.merId = merId;
            }

            public String getPay() {
                return pay;
            }

            public void setPay(String pay) {
                this.pay = pay;
            }

            public List<ListBean> getList() {
                return list;
            }

            public void setList(List<ListBean> list) {
                this.list = list;
            }

            public static class ListBean {
                /**
                 * disNum : 1
                 * goodsName : 炸大份鸡
                 * pay : 46
                 */

                private int disNum;
                private String goodsName;
                private String pay;

                public int getDisNum() {
                    return disNum;
                }

                public void setDisNum(int disNum) {
                    this.disNum = disNum;
                }

                public String getGoodsName() {
                    return goodsName;
                }

                public void setGoodsName(String goodsName) {
                    this.goodsName = goodsName;
                }

                public String getPay() {
                    return pay;
                }

                public void setPay(String pay) {
                    this.pay = pay;
                }
            }
        }

        public static class IndentInfoBean {
            /**
             * dis : 哈哈哈哈哈哈哈
             * name : 成本
             * peopleNum : 5
             * phone : 18340817282
             * time : 4-13  10:00-11:00
             */

            private String dis;
            private String name;
            private String peopleNum;
            private String phone;
            private String time;

            public String getDis() {
                return dis;
            }

            public void setDis(String dis) {
                this.dis = dis;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getPeopleNum() {
                return peopleNum;
            }

            public void setPeopleNum(String peopleNum) {
                this.peopleNum = peopleNum;
            }

            public String getPhone() {
                return phone;
            }

            public void setPhone(String phone) {
                this.phone = phone;
            }

            public String getTime() {
                return time;
            }

            public void setTime(String time) {
                this.time = time;
            }
        }
    }
}
