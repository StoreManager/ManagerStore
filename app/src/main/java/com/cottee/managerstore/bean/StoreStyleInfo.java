package com.cottee.managerstore.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Administrator on 2017-12-05.
 */

public class StoreStyleInfo {

    /**
     * 休闲娱乐 : ["足疗按摩 ","洗浴/汗蒸","酒吧","KTV ","运动健身 ","DIY手工坊","密室逃脱","茶馆 ","棋牌室
     * ","轰趴馆","真人CS","采摘/农家乐 ","更多休闲娱乐 "]
     * 测试使用 :
     * 美食 : ["甜点饮品","生日蛋糕 ","自助餐 ","小吃快餐 ","日韩料理 ","西餐 ","聚餐宴请 ","烧烤烤肉 ","大闸蟹
     * ","川湘菜 ","江浙菜 ","香锅烤鱼 ","小龙虾 ","粤菜 ","中式烧烤/烤串 ","西北菜 ","咖啡酒吧 ","京菜鲁菜
     * ","徽菜","东北菜","生鲜蔬菜","云贵菜 ","东南亚","海鲜","台湾/客家菜","创鱼菜 ","汤/粥/炖菜 ","蒙餐",
     * "新疆菜","其他美食 "]
     */

    @SerializedName("测试使用")
    private String test;
    @SerializedName("休闲娱乐")
    private List<String> entertainment;
    @SerializedName("美食")
    private List<String> food;

    public String getTest() {
        return test;
    }

    public void setTest(String test) {
        this.test = test;
    }

    public List<String> getEntertainment() {
        return entertainment;
    }

    public void setEntertainment(List<String> entertainment) {
        this.entertainment = entertainment;
    }

    public List<String> getFood() {
        return food;
    }

    public void setFood(List<String> food) {
        this.food = food;
    }
}
