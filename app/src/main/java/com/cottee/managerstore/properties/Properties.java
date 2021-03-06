package com.cottee.managerstore.properties;

/**
 * Created by Administrator on 2017/11/20.
 */

public class Properties {
    //订单获取
    public static final String ORDER_GET_PATH="https://thethreestooges.cn:1225/mer_indent/indent_list/no_ok";
    public static final int ORDER_ADD=37;
    //订单修改
    public static final String ORDER_UPDATE_PATH="https://thethreestooges.cn:1225/mer_indent/order/dispose";
    //具体菜品
    public static  final  String PROJECT_DETAIL_MANAGE_GET_PATH ="https://thethreestooges.cn:1225/classify/item/select";
    public static final String PROJECT_DETAIL_MANAGE_ADD_PATH="https://thethreestooges.cn:1225/classify/item/insert";
    public static final String PROJECT_DETAIL_MANAGE_UPDATE_PATH="https://thethreestooges.cn:1225/classify/item/update";
    public static final String PROJECT_DETAIL_MANAGE_STICK_PATH="https://thethreestooges.cn:1225/classify/item/stick";
    public static final String PROJECT_DETAIL_MANAGE_DELETE_PATH="\n" +
            "https://thethreestooges.cn:1225/classify/item/delete";

    public static boolean isSend = false;
    public final static int PHOTO_SURFACE= 18;

    public static final int PROJECT_DETAIL_MANAGE_UPDATE =21;
    public static final int PROJECT_DETAIL_MANAGE_ADD=24;
    public static final int PROJECT_DETAIL_MANAGE_DELETE =23;
    public static final int PROJECT_DETAIL_MANAGE_GET =22;
    public static final int PROJECT_DETAIL_MANAGE_STICK =25;

    public static final String EMAIL_SUBMIT_PATH = "https://thethreestooges.cn:1225/application/login/mail_submit.php";
    public static final String MAIL_VERIFICATION_PATH = "https://thethreestooges.cn:1225/application/login/mail_validate.php";
    public static final String USER_BUILD_PATH = "https://thethreestooges.cn:1225/application/login/user_write.php";
    public static final String LOGIN_PATH = "http://thethreestooges.cn/merchant/bean/login/login.php";
    public static final String FORGET_PASSWORD_EMAIL_SUBMIT_PATH = "https://thethreestooges.cn:1225/application/login/forget_submit.php";
    public static final String FORGET_PASSWORD_EMAIL_VER_SUBMIT_PATH = "https://thethreestooges.cn:1225/application/login/forget_validate.php";
    public static final String FORGET_PASSWORD_EMAIL_PSD_SUBMIT_PATH = "https://thethreestooges.cn:1225/application/login/user_forget.php";
    public static final String STORE_PHOTO = "https://thethreestooges.cn/merchant/bean/register/photo_mer_upload.php";
    public static final String PHOTO_BUSLIC = "https://thethreestooges.cn/merchant/bean/register/photo_buslic_upload.php";
    public static final String NECESSARY_INFO = "https://thethreestooges.cn:1225/register/first/register";
    public static final String SUPPLY_INFOS= "https://thethreestooges.cn:1225/register/supply/register";
    public static final String SUPPLY_STORE_ID="https://thethreestooges.cn:1225/register/set/user_mer";
    //    public static final String SUPPLY_INFOS= "https://thethreestooges" +
//            ".cn/merchant/bean/register/supply_register.php";

    public static final String GET_STORE = "https://thethreestooges.cn:1225/register/mer/show";//已审核的店铺地址
    public static final String STORE_ENVIR = "https://thethreestooges" +
            ".cn:1225/register/environment/up";//店铺环境
    //已审核的店铺地址

    public static  final  String PROJECT_MANAGE_ADD_PATH = "https://thethreestooges.cn:1225/classify/classify/insert";
    public static  final  String PROJECT_MANAGE_GET_PATH = "\n" +
            "https://thethreestooges.cn:1225/classify/classify/select";
    public static  final  String PROJECT_MANAGE_UPDATE_PATH = "\n" +
            "https://thethreestooges.cn:1225/classify/classify/update";
    public static  final  String PROJECT_MANAGE_DELETE_PATH = "https://thethreestooges.cn:1225/classify/classify/delete";

    public final static int SESSION_TYPE = 250;
    public final static int USER_LOGIN = 1;
    public final static int CHECKOUT_EMAIL = 2;
    public final static int CHECKOUT_EMAIL_VER = 3;
    public final static int FINISH_USER_REGISTER = 4;
    public final static int FORGET_CHECKOUT_EMAIL = 5;
    public final static int FORGET_CHECKOUT_EMAIL_VER = 6;
    public final static int FORGET_CHECKOUT_EMAIL_PWD = 7;
    public static final int REQUEST_ADDRESS = 2;
    public static final int REQUEST_CAMERA = 3;
    public final static int NECESSARY_INFOMATION= 8;
    public final static int PHOTO_BULIC=9;
    public static final int TO_HOME = 10;
    public static final int TO_LOGIN = 11;
    public static final int SEAT_INFORMATION = 12;
    public static final int PROJECT_MANAGE_LARGE_INFORMATION = 13;
    public static final int SUPPLY_INFO = 14;
    public static final int PROJECT_MANAGE_GET = 15;
    public static final int PROJECT_MANAGE_UPDATE =16;
    public static final int PROJECT_MANAGE_DELETE =17;
    public static final int PROJECT_MANAGE_MODIFYEMP =18;

    //员工
    public static final int EMPLOYEE_ADD = 30;
   public static  final  String EMPLOYEE_ADD_PATH = "https://thethreestooges.cn:1225/staff/create/staff";
   public static  final  String MODIFYEMP_PATH = "https://thethreestooges.cn:1225/staff/staff_change/pwd";
    public static  final  String EMPLOYEE_LIST_GSON_PATH = "\n" +
            "https://thethreestooges.cn:1225/staff/staff/list";
    public static  final  String EMPLOYEE_INFO_GSON_PATH = "https://thethreestooges.cn:1225/staff/staff/info";
    public static final int EMPLOYEE_INFO = 31;

    //会员
    public static final String VIP_STANDARD_PATH="https://thethreestooges" +
            ".cn:1225/VIP/vip/upload";//会员等级上传
    public static final int VIP_SUB=32;
    public static final String VIP_STANDARD_SHOW="https://thethreestooges.cn:1225/VIP/vip/show";
    //会员等级显示

    //会员等级修改
    public static final String VIP_CHANGE_PATH="https://thethreestooges.cn:1225/VIP/vip/update";
    public static final int VIP_CHANGE=35;
    //会员等级删除
    public static final String VIP_DELE_PATH="https://thethreestooges.cn:1225/VIP/vip/dele";
    public static final int VIP_DELE=36;

    public static final String VIP_PATH="https://thethreestooges.cn:1225/VIP/vip_name/part";


    //员工登录
    public static  final  String EMP_LOGIN_PATH ="https://thethreestooges.cn:1225/identity/login/staff_login.php";
    public static final int EMP_LOGIN = 33;
    public static final int EMP_UPDATE = 34;
    public static  final  String EMP_UPDATE_PATH ="https://thethreestooges.cn:1225/staff/staff/update";
}