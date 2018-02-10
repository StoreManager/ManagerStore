package com.cottee.managerstore.widget;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cottee.managerstore.R;

/**
 * ==================================================
 * Company: Black and White Universal(c) 2017
 * *
 * Author: Kevin
 * *
 * Data: 2017/7/15 16:34
 * *
 * Description:
 * ==================================================
 */


public class Title extends RelativeLayout {


    private RelativeLayout titleCustomLayoutAear = null;//自定义View区
    private View titleCustomView = null;//用户设置的自定义View
    private OnFinishCustomLayout onFinishCustomLayout = null;//如果设置自定义View可以使用此监听
    private TextView titleName = null;//标题
    private String titleNameStr = null;
    private boolean isShowTitle = true;

    private View titleDivider = null;//分隔线
    private boolean isShowDivider = true;//是否显示分隔线

    private ButtonViewHolder buttonHolderLeft = new ButtonViewHolder(false, BUTTON_LEFT);
    private ButtonViewHolder buttonHolderRight1 = new ButtonViewHolder(false, BUTTON_RIGHT1);
    private ButtonViewHolder buttonHolderRight2 = new ButtonViewHolder(false, BUTTON_RIGHT2);

    private OnTitleButtonClickListener onTitleButtonClickListener;


    public Title(Context context, AttributeSet attrs) {
        super(context, attrs);//继承自RelativeLayout,必要
    }


    public void setTitleNameRes(int resId){//???现在没用
        this.titleNameStr = getResources().getString(resId);
        if(null != titleName){
            titleName.setText(titleNameStr);
        }
    }

    public void setTitleNameStr(String name){
        this.titleNameStr = name;
        if(null != titleName){
            titleName.setText(titleNameStr);
        }
    }

    public void showTitle(boolean showTitle){
        this.isShowTitle = showTitle;
        if(isShowTitle){
            titleName.setVisibility(View.VISIBLE);
        } else {
            titleName.setVisibility(View.GONE);
        }
    }

    public void setShowDivider(boolean showDivider) {
        isShowDivider = showDivider;
        refreshDividerStatus();//判断分割线，为什么不写if判断
    }

    public void setOnTitleButtonClickListener(OnTitleButtonClickListener onTitleButtonClickListener) {
        this.onTitleButtonClickListener = onTitleButtonClickListener;
    }

    public void mSetButtonInfo(ButtonInfo buttonInfo){//对按钮的选择
        if(buttonInfo == null){
            return;
        }
        switch (buttonInfo.id){
            case BUTTON_LEFT:
                buttonHolderLeft.buttonInfo =  buttonInfo;
                break;

            case BUTTON_RIGHT1:
                buttonHolderRight1.buttonInfo =  buttonInfo;
                break;

            case BUTTON_RIGHT2:
                buttonHolderRight2.buttonInfo =  buttonInfo;
                break;
            default:
                return;
        }
        refreshButtonShow(new ButtonViewHolder[]{buttonHolderLeft, buttonHolderRight1, buttonHolderRight2});
    //刷新按钮
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();


        //自定义View区
        titleCustomLayoutAear = (RelativeLayout) findViewById(R.id.titleCustomLayoutAear);
        if(titleCustomView != null){
            titleCustomLayoutAear.setVisibility(View.VISIBLE);
            titleCustomLayoutAear.addView(titleCustomView);
        }
        //标题栏分隔线
        titleDivider = findViewById(R.id.titleDivider);

        //标题
        titleName = (TextView)findViewById(R.id.titleName);
        if(null != titleName && null != titleNameStr){
            titleName.setText(titleNameStr);
        }
        if(isShowTitle){
            titleName.setVisibility(View.VISIBLE);
        }else{
            titleName.setVisibility(View.GONE);
        }

        //左按钮
        buttonHolderLeft.rootView = findViewById(R.id.titleBackLayout);
        buttonHolderLeft.image = (ImageView)findViewById(R.id.titleBackImg);
        buttonHolderLeft.text = (TextView)findViewById(R.id.titleBackName);
        buttonHolderLeft.initOnClick();

        //右一按钮
        buttonHolderRight1.rootView = findViewById(R.id.titleRightLayout);
        buttonHolderRight1.image = (ImageView)findViewById(R.id.titleRightImg) ;
        buttonHolderRight1.text = (TextView)findViewById(R.id.titleRight) ;
        buttonHolderRight1.initOnClick();

        //右二按钮
        buttonHolderRight2.rootView = findViewById(R.id.titleRightLayout2);
        buttonHolderRight2.image = (ImageView)findViewById(R.id.titleRightImg2) ;
        buttonHolderRight2.text = (TextView)findViewById(R.id.titleRight2);
        buttonHolderRight2.initOnClick();

        refreshButtonShow(new ButtonViewHolder[]{buttonHolderLeft, buttonHolderRight1, buttonHolderRight2});

        refreshTheme();

        if(null != onFinishCustomLayout){//view监控
            onFinishCustomLayout.onFinishCustomLayout(titleCustomLayoutAear, titleCustomView);
        }
    }

//ViewHolder就是一个持有者的类，他里面一般没有方法，只有属性，作用就是一个临时的储存器，
// 把你getView方法中每次返回的View存起来，可以下次再用。
// 这样做的好处就是不必每次都到布局文件中去拿到你的View，提高了效率。不能一次都出来，现在写了5个按钮，
   // 拿的时候要new button出来，Viewhold不用new了，

    public class ButtonViewHolder{

        public ButtonViewHolder(boolean isShow, int id){
            buttonInfo = new ButtonInfo(isShow, id);
        }

        public View rootView;
        public TextView text;
        public ImageView image;

        private ButtonInfo buttonInfo;

        public void initOnClick(){
            rootView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(null != onTitleButtonClickListener){
                        onTitleButtonClickListener.onClick(buttonInfo.id, ButtonViewHolder.this);
                    }
                }
            });
        }
    }


//对按钮显示内用的选择
    public static final int  BUTTON_LEFT   = 1;
    public static final int  BUTTON_RIGHT1 = 2;
    public static final int  BUTTON_RIGHT2 = 3;

    public static class ButtonInfo{


        public boolean isShow;
        public int id = BUTTON_LEFT;
        public int iconRes = 0;//如果为0表示图标不显示
        public String name = null;//如果为null表示文字不显示
        public boolean isOpen = false;//支持状态：展开或者关闭，用于按钮可能有状的情况

        public ButtonInfo(boolean isShow, int id){
            this.isShow = isShow;
            this.id = id;
        }

        public ButtonInfo(boolean isShow, int id, int icRes, String name){
            this.isShow = isShow;
            this.id = id;
            this.iconRes = icRes;
            this.name = name;
        }


    }




    /**
     * 刷新按钮显示
     * @param viewHolders
     */
    public void refreshButtonShow(ButtonViewHolder[] viewHolders){
        for(ButtonViewHolder holder : viewHolders){
            if(null == holder.rootView){
                break;
            }
            if(holder.buttonInfo.isShow){
                holder.rootView.setVisibility(View.VISIBLE);
                if(holder.buttonInfo.iconRes == 0){
                    holder.image.setVisibility(View.GONE);
                }else{
                    holder.image.setVisibility(View.VISIBLE);
                    holder.image.setImageResource(holder.buttonInfo.iconRes);
                }

                if(holder.buttonInfo.name == null){
                    holder.text.setVisibility(View.GONE);
                }else{
                    holder.text.setVisibility(View.VISIBLE);
                    holder.text.setText(holder.buttonInfo.name);
                }
            }else{
                holder.rootView.setVisibility(View.GONE);
            }
        }
    }




    public static final int THEME_TRANSLATE = 1;//主题_透明
    public static final int THEME_LIGHT     = 2;//主题_明亮，背景是浅色
    public static final int THEME_DARK      = 3;//主题_黑色，背景是深色
    private int theme  = THEME_LIGHT;
    /**
     * 设置主题
     * @param theme
     */
    public void setTheme(int theme){
        this.theme = theme;
        refreshTheme();
    }
    private void refreshTheme(){
        switch (theme){
            case THEME_TRANSLATE:
                if(titleName != null){
                    titleName.setTextColor(Color.TRANSPARENT);

                }
                buttonHolderLeft.text.setTextColor(Color.WHITE);
                buttonHolderRight1.text.setTextColor(Color.WHITE);
                buttonHolderRight2.text.setTextColor(Color.WHITE);
                titleDivider.setBackgroundColor(Color.WHITE);
                break;

            case THEME_LIGHT:
                if(titleName != null){
                    titleName.setTextColor(Color.BLACK);
                }
                buttonHolderLeft.text.setTextColor(Color.BLACK);
                buttonHolderRight1.text.setTextColor(Color.BLACK);
                buttonHolderRight2.text.setTextColor(Color.BLACK);
                titleDivider.setBackgroundResource(android.R.color.darker_gray);
                break;

            case THEME_DARK:
                if(titleName != null){
                    titleName.setTextColor(Color.WHITE);
                }
                buttonHolderLeft.text.setTextColor(Color.WHITE);
                buttonHolderRight1.text.setTextColor(Color.WHITE);
                buttonHolderRight2.text.setTextColor(Color.WHITE);
                titleDivider.setBackgroundColor(Color.WHITE);
                break;
        }

        refreshDividerStatus();//分割线刷了两次
    }
    //刷新分割线

    private void refreshDividerStatus(){
        if(isShowDivider){
            titleDivider.setVisibility(View.VISIBLE);
        }else{
            titleDivider.setVisibility(View.GONE);
        }
    }
//拓展内容，
    public interface OnTitleButtonClickListener{
        public void onClick(int id, ButtonViewHolder viewHolder);

    }

    public interface OnFinishCustomLayout{//关于最后阶段  如果设置自定义View可以使用此监听
        public void onFinishCustomLayout(View customViewLayout, View customView);
    }
}
