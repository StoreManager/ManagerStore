package com.cottee.managerstore.handle;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

import com.cottee.managerstore.R;
import com.cottee.managerstore.activity.RegisterStoreActivity;
import com.cottee.managerstore.activity.RegisterStoreInfoActivity;
import com.cottee.managerstore.activity1.ForgetPasswordActivity;
import com.cottee.managerstore.activity1.RegisterPasswordActivity;
import com.cottee.managerstore.activity1.StoreManagerMainActivity;
import com.cottee.managerstore.bean.UserRequestInfo;
import com.cottee.managerstore.manage.LoginRegisterInformationManage;
import com.cottee.managerstore.manage.ProjectTypeManage;
import com.cottee.managerstore.properties.Properties;
import com.cottee.managerstore.utils.ToastUtils;
import com.cottee.managerstore.widget.ShapeLoadingDialog;


/**
 * Created by Administrator on 2017/11/20.
 */

public class LoginRegisterInformationHandle extends Handler {


    private Context context;
    private String emailAddress;
    private String loginPassword;
    private ShapeLoadingDialog shapeLoadingDialog;


    /*
   发送成功返回   0 （要给用户提示注意查看邮件之类的提示）
 * 发送失败返回   1
 * 验证码发送频率过快返回 2  （测试时间100秒）
 * 邮箱已存在返回 3
 * 提交字段有误返回 4
  */
    private static final int SUBMITSUCCESSFUL = 0;
    private static final int SUBMITFAILD = 1;
    private static final int VERSUBMITFAST = 2;
    private static final int EMAILEXIST = 3;
    /*
  * 执行成功返回 0
 * 验证码错误返回 1
 * 验证码过期并重新发送成功返回 2
 * 验证码过期并重新发送失败返回 3
 * 无该用户记录返回 5
 * 提交字段有误 返回 4
      */
    private static final int VERSSUCCESS = 0;
    private static final int VERSFALSE = 1;
    private static final int VERTIMEOUT_AGAINSUCCESS = 2;
    private static final int VERTIMEOUT_AGAINFAILD = 3;
    private static final int NO_VER = 5;
    private static final int INFO_FALSE = 4;


    /**
     * 成功返回 0
     * 账户密码写入失败 1
     */

    private static final int VERRETURNSUCCESS = 0;
    private static final int USERPASSWORDFAILD = 1;


    /**
     * 登陆成功时候返回 0
     * 用户不存在或密码错误返回 1
     * 提交字段有误返回 4
     */

    private static final int LOGINSSUCCESS = 0;
    private static final int PSWFAILD_USERUNEXIST = 1;

    /**
     * 忘记密码
     * 发送成功返回   0 （要给用户提示注意查看邮件之类的提示）
     * 发送失败返回   1
     * 验证码发送频率过快返回 2  （测试时间100秒）
     * 邮箱不存在返回 3
     * 提交字段有误返回 4
     */

    private static final int FORGET_EMAILSUCCESS = 0;
    private static final int FORGET_EMAILFAILD = 1;
    private static final int FORGET_VERSUBMITFAST = 2;
    private static final int EMAIL_UNEXIST = 3;


    /**
     * 忘记密码  执行成功返回 0
     * 验证码错误 1
     * 验证码过期并重新发送失败返回 3
     * 无该用户记录返回 5
     * 提交字段有误 返回 4
     */


    private static final int FORGET_VERSSUCCESS = 0;
    private static final int FORGET_VERSFALSE = 1;
    private static final int FORGET_VERTIMEOUT_AGAINFAILD = 3;
    private static final int FORGET_NO_VER = 5;
    private static final int FORGET_INFO_FALSE = 4;


    /**
     * 忘记密码
     * 成功返回 0
     * 失败返回 1
     */

    private static final int FORGET_RETURNSUCCESS = 0;
    private static final int FORGET_RETURNFAILD = 1;


    private static final int TO_HOME = 10;
    private static final int TO_LOGIN = 11;

    private static final int SUBMIT_SUCCESS = 1;//上传店铺基本信息成功

    private static final int SEAT_PEOPLE_NUMBER_SUCCESS = 0;
    private static final int SEAT_PEOPLE_NUMBER_FAILD = 1;

    private static final int PROJECT_MANAGE_SUCCESS = 0;
    private static final int PROJECT_MANAGE_FAILD = 1;
    private static final int PROJECT_MANAGE_CREATE_FAILD = 250;

    private static final int SESSION_SUCCESS = 1;
    private LoginRegisterInformationManage session;
    private SharedPreferences sp;


    public LoginRegisterInformationHandle(Context context, String emailAddress) {
        this.context = context;
        this.emailAddress = emailAddress;
    }


    public LoginRegisterInformationHandle(Context context, String emailAddress, String loginPassword) {
        this.context = context;
        this.emailAddress = emailAddress;
        this.loginPassword = loginPassword;
    }


    public LoginRegisterInformationHandle(ShapeLoadingDialog shapeLoadingDialog) {
        this.shapeLoadingDialog = shapeLoadingDialog;
    }


    public LoginRegisterInformationHandle() {
    }

    @Override
    public void handleMessage(Message msg) {
        switch (msg.what) {
            case Properties.CHECKOUT_EMAIL:
                switch (msg.arg1) {
                    case SUBMITSUCCESSFUL:
                        ToastUtils.showToast( context, "正在发送验证码至邮箱" );
                        break;

                    case SUBMITFAILD:
                        ToastUtils.showToast( context, "发送失败" );

                        break;
                    case VERSUBMITFAST:
                        ToastUtils.showToast( context, "验证码发送频率过快" );
                        break;
                    case EMAILEXIST:
                        ToastUtils.showToast( context, "邮箱已存在" );
                        break;
                }
                break;

            case Properties.CHECKOUT_EMAIL_VER:
                switch (msg.arg1) {
                    case VERSSUCCESS:
                        ToastUtils.showToast( context, "验证码正确" );
                        Intent intent = new Intent( context, RegisterPasswordActivity.class );
                        intent.putExtra( "email", emailAddress );
                        context.startActivity( intent );
                        ((Activity) context).overridePendingTransition( R.anim.right_in, R.anim.left_out );
                        break;
                    case VERSFALSE:
                        ToastUtils.showToast( context, "验证码错误" );

                        break;
                    case VERTIMEOUT_AGAINSUCCESS:
                        ToastUtils.showToast( context, "验证码过期重新发送成功" );
                        break;
                    case VERTIMEOUT_AGAINFAILD:
                        ToastUtils.showToast( context, "验证码过期重新发送失败" );
                        break;
                    case INFO_FALSE:
                        ToastUtils.showToast( context, "未知错误" );
                        break;
                    case NO_VER:
                        ToastUtils.showToast( context, "未获取验证码" );
                        break;


                }
                break;


            case Properties.FINISH_USER_REGISTER:
                switch (msg.arg1) {
                    case VERRETURNSUCCESS:
                        ToastUtils.showToast( context, "提交邮箱密码成功" );
                        break;
                    case USERPASSWORDFAILD:
                        ToastUtils.showToast( context, "提交失败" );
                        break;

                    default:
                        break;
                }

                break;

            /*case Properties.SESSION_TYPE:
                if(msg.arg1==SESSION_SUCCESS){

                    }else{
                        ToastUtils.showToast( context, "session无效效，正在重新登陆请稍等 + session" );
                        session.userLogin(emailAddress,loginPassword);
                    }
                    break;*/


            case Properties.SESSION_TYPE:
                switch (msg.arg1) {
                    case 32:
                        UserRequestInfo.setSession((String) msg.obj);
                        System.out.println("重新登录获取的session"+ UserRequestInfo.getSession());
                        ToastUtils.showToast(context,"重新登录成功");


                        String dishType = UserRequestInfo.getDishType();
                        System.out.println("重新登录后菜品类型"+dishType);
                        new ProjectTypeManage(context,new LoginRegisterInformationHandle()).projectManageCommit(dishType);


                        break;
                    case PSWFAILD_USERUNEXIST:
                        ToastUtils.showToast( context, "失败" );
                        break;

                }
                break;





            case Properties.USER_LOGIN:
                switch (msg.arg1) {
                    case 32:
                        /*SharedPreferences sp = context.getSharedPreferences("Session", Context.MODE_PRIVATE);//Context
                        // .MODE_PRIVATE表示SharePrefences的数据只有自己应用程序能访问。
                        SharedPreferences.Editor editor = sp.edit();
                        editor.putString("session", (String) msg.obj);
                        editor.commit();*/

                        UserRequestInfo.setSession((String) msg.obj);
                        UserRequestInfo.setUserEmail(emailAddress);
                        UserRequestInfo.setUserPassword(loginPassword);
                        System.out.println("login session："+ UserRequestInfo.getSession());
                        System.out.println("login email："+ UserRequestInfo.getUserEmail());
                        System.out.println("login password："+ UserRequestInfo.getUserPassword());

                        Intent intent = new Intent(context, RegisterStoreActivity.class );
                        context.startActivity( intent );
                        ToastUtils.showToast( context, "登录成功" );
                        /*UserManage userManage = new UserManage();
                        userManage.saveUserLogin( context, emailAddress, loginPassword );*/

                        break;
                    case PSWFAILD_USERUNEXIST:
                        ToastUtils.showToast( context, "用户不存在或密码错误" );
                        break;

                }
                break;


            case Properties.FORGET_CHECKOUT_EMAIL:
                switch (msg.arg1) {
                    case FORGET_EMAILSUCCESS:
                        ToastUtils.showToast( context, "正在发送验证码至邮箱" );
                        break;

                    case FORGET_EMAILFAILD:
                        ToastUtils.showToast( context, "发送失败" );

                        break;
                    case FORGET_VERSUBMITFAST:
                        ToastUtils.showToast( context, "验证码发送频率过快" );
                        break;
                    case EMAIL_UNEXIST:
                        ToastUtils.showToast( context, "邮箱不存在" );
                        break;
                }
                break;

            case Properties.FORGET_CHECKOUT_EMAIL_VER:
                switch (msg.arg1) {
                    case FORGET_VERSSUCCESS:
                        ToastUtils.showToast( context, "验证码正确" );
                        Intent intent = new Intent( context, ForgetPasswordActivity.class );
                        intent.putExtra( "email", emailAddress );
                        context.startActivity( intent );
                        break;
                    case FORGET_VERSFALSE:
                        ToastUtils.showToast( context, "验证码错误" );

                        break;
                    case FORGET_VERTIMEOUT_AGAINFAILD:
                        ToastUtils.showToast( context, "验证码过期重新发送失败" );
                        break;
                    case FORGET_INFO_FALSE:
                        ToastUtils.showToast( context, "提交字段有误" );
                        break;
                    case FORGET_NO_VER:
                        ToastUtils.showToast( context, "无该用户记录" );
                        break;

                }
                break;


            case Properties.FORGET_CHECKOUT_EMAIL_PWD:
                switch (msg.arg1) {
                    case FORGET_RETURNSUCCESS:
                        ToastUtils.showToast( context, "密码修改成功" );
                        break;
                    case FORGET_RETURNFAILD:
                        ToastUtils.showToast( context, "提交失败" );
                        break;

                    default:
                        break;
                }
                break;

            case Properties.NECESSARY_INFOMATION:
                switch (msg.arg1) {
                    case SUBMIT_SUCCESS:
                        ToastUtils.showToast( context,"店铺正在审核中，成功后将以邮件方式通知您" );
                        ((Activity)context).finish();
                        break;
                    default:
                        ToastUtils.showToast( context, "服务器打瞌睡啦，请稍后再试" );
                        break;
                }
                break;
            case Properties.PHOTO_BULIC:
                switch (msg.arg1) {
                    case 1:
                        ToastUtils.showToast( context,"图片修改成功" );
                        break;
                    default:
                        ToastUtils.showToast( context, "服务器打瞌睡啦，请稍后再试" );
                        break;
                }
                break;
            case TO_HOME:
                Intent homeIntent = new Intent( context, RegisterStoreActivity.class );
                context.startActivity( homeIntent );
                break;

            case TO_LOGIN:
                Intent loginIntent = new Intent( context, StoreManagerMainActivity.class );
                context.startActivity( loginIntent );
                break;


            case Properties.SEAT_INFORMATION:
                switch (msg.arg1) {
                    case SEAT_PEOPLE_NUMBER_SUCCESS:
                        ToastUtils.showToast( context, "保存成功" );
                        break;
                    case SEAT_PEOPLE_NUMBER_FAILD:
                        ToastUtils.showToast( context, "保存失败" );
                        break;
                }
                break;
            case Properties.SUPPLY_INFO:
                switch (msg.arg1) {
                    case 1:
                        ToastUtils.showToast( context, "修改成功" );
                        break;
                    case 0:
                        ToastUtils.showToast( context, "图片修改成功" );
                        break;
                }
                break;
            case Properties.PROJECT_MANAGE_LARGE_INFORMATION:
                switch (msg.arg1) {
                    case PROJECT_MANAGE_SUCCESS:
                       /* shapeLoadingDialog.setDismiss();*/

                        break;
                    case PROJECT_MANAGE_FAILD:
                        /*shapeLoadingDialog.setDismiss();*/
                        ToastUtils.showToast( context, "添加失败" );
                        break;
                    case PROJECT_MANAGE_CREATE_FAILD:
                        /*shapeLoadingDialog.setDismiss();*/

                        new LoginRegisterInformationManage(context,new LoginRegisterInformationHandle()).againLogin();

                        break;

                }
                break;

            case Properties.PROJECT_MANAGE_DELETE:
                switch (msg.arg1) {
                    case PROJECT_MANAGE_SUCCESS:
                        /*ToastUtils.showToast( context, "删除成功" );*/

                        break;
                    case PROJECT_MANAGE_FAILD:
                        ToastUtils.showToast( context, "删除失败" );
                        break;
                    case PROJECT_MANAGE_CREATE_FAILD:
                        sp = context.getSharedPreferences("ProjectManage", Context.MODE_PRIVATE);
                        String update = sp.getString("update", "");
                        String updateId = sp.getString("updateId", "");
                        new LoginRegisterInformationManage(context,new LoginRegisterInformationHandle()).againLogin();
                        new ProjectTypeManage(context,new LoginRegisterInformationHandle()).projectManageUpdate(update,updateId);
                        break;
                }
                break;

            case Properties.PROJECT_MANAGE_UPDATE:
                switch (msg.arg1) {
                    case PROJECT_MANAGE_SUCCESS:
                        /*ToastUtils.showToast( context, "修改成功" );*/
                        break;
                    case PROJECT_MANAGE_FAILD:
                        ToastUtils.showToast( context, "修改失败" );
                        break;
                    case PROJECT_MANAGE_CREATE_FAILD:
                        sp = context.getSharedPreferences("ProjectManage", Context.MODE_PRIVATE);
                        String deleteId = sp.getString("deleteId", "");
                        new LoginRegisterInformationManage(context,new LoginRegisterInformationHandle()).againLogin();
                        new ProjectTypeManage(context,new LoginRegisterInformationHandle()).projectManageDelete(deleteId);
                        break;
                }
                break;


            default:
                break;
        }
    }




}
