package com.cottee.managerstore.widget;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.support.v4.util.SimpleArrayMap;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.cottee.managerstore.R;

import static android.os.Build.VERSION_CODES.KITKAT;

/**
 * Created by mmyytt on 2016/8/16.
 */

public class ZoomHoverView extends LinearLayout implements View.OnClickListener /*,ZoomHoverAdapter.OnDataChangedListener*/ {



    //当前放大动画
    private AnimatorSet mCurrentZoomInAnim = null;

    //当前缩小动画
    private AnimatorSet mCurrentZoomOutAnim = null;

    //缩放动画监听器
    private OnZoomAnimatorListener mOnZoomAnimatorListener = null;

    //动画持续时间
    private int mAnimDuration;

    //动画缩放倍数
    private float mAnimZoomTo;

    //缩放动画插值器
    private Interpolator mZoomInInterpolator;
    private Interpolator mZoomOutInterpolator;

    //上一个ZoomOut的view(为了解决快速切换时，上一个被缩小的view缩放大小不正常的情况)
    private View mPreZoomOutView;

    //当前被选中的view
    private View mCurrentView = null;




    private LinearLayout.LayoutParams verticalParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams
            .MATCH_PARENT);

    private LinearLayout.LayoutParams horizontalParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams
            .WRAP_CONTENT);

    //需要横跨的map
    private SimpleArrayMap<Integer, Integer> mNeedSpanMap = new SimpleArrayMap<>();
    private RelativeLayout view;
    private LinearLayout ll_item_one;
    private LinearLayout ll_item_two;
    private LinearLayout ll_item_three;
    private LinearLayout ll_item_four;
    /*private View view_one;
    private View view_two;
    private View view_three;
    private View view_four;
    private ImageView imv_one;
    private ImageView imv_two;
    private ImageView imv_three;
    private ImageView imv_four;*/

    private View childView;
    private OnButtonSelectedListener onSelect;
    private OnItemSelectedListener onItem;
    private LinearLayout ll_item_five;
    private LinearLayout ll_item_six;


    public ZoomHoverView(Context context) {
        this(context, null);
    }

    public ZoomHoverView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ZoomHoverView);

        initView(context);
        mAnimDuration = typedArray.getInt(R.styleable.ZoomHoverView_zhv_zoom_duration, getResources().getInteger(android.R.integer.config_shortAnimTime));
        mAnimZoomTo = typedArray.getFloat(R.styleable.ZoomHoverView_zhv_zoom_to, 1.2f);
        typedArray.recycle();

        mZoomOutInterpolator = mZoomInInterpolator = new AccelerateDecelerateInterpolator();
    }

    private void initView(Context context) {
        LayoutInflater inflater = LayoutInflater.from(context);
        childView = inflater.inflate( R.layout.layout_button, null);
        ll_item_one = (LinearLayout) childView.findViewById(R.id.ll_item_one);
        ll_item_two = (LinearLayout) childView.findViewById(R.id.ll_item_two);
        ll_item_three = (LinearLayout) childView.findViewById(R.id.ll_item_three);
        ll_item_four = (LinearLayout) childView.findViewById(R.id.ll_item_four);
        ll_item_five = (LinearLayout) childView.findViewById(R.id.ll_item_five);
        ll_item_six = (LinearLayout) childView.findViewById(R.id.ll_item_six);


        addView(childView);
        ll_item_one.setOnClickListener(this);
        ll_item_two.setOnClickListener(this);
        ll_item_three.setOnClickListener(this);
        ll_item_four.setOnClickListener(this);
        ll_item_five.setOnClickListener(this);
        ll_item_six.setOnClickListener(this);


    }






    @Override
    public void onClick(View view) {

        //如果currentView为null，证明第一次点击
        if(mCurrentView == null){
            if(view==ll_item_one){
                zoomInAnim(view);
                mCurrentView = view;
                onItem.onItemSelected(mCurrentView);

            }else if(view==ll_item_two){
                zoomInAnim(view);
                mCurrentView = view;
                onItem.onItemSelected(mCurrentView);

            }else if(view==ll_item_three){
                zoomInAnim(view);
                mCurrentView = view;
                onItem.onItemSelected(mCurrentView);


            }else if(view==ll_item_four){
                zoomInAnim(view);
                mCurrentView = view;
                onItem.onItemSelected(mCurrentView);

            }else if(view==ll_item_five){
                zoomInAnim(view);
                mCurrentView = view;
                onItem.onItemSelected(mCurrentView);

            }else if(view==ll_item_six){
                zoomInAnim(view);
                mCurrentView = view;
                onItem.onItemSelected(mCurrentView);

            }
            if (onSelect != null) {
                onSelect.onButtonSelected(mCurrentView, mCurrentView.getId() );
            }
            mCurrentView = null;

        }/*else {
            if (view.getId() != mCurrentView.getId()) {
                //点击的view不是currentView
                //currentView执行缩小动画
              *//*  zoomOutAnim(mCurrentView);*//*
                //当前点击的view赋值给currentView
                mCurrentView = view;
                //执行放大动画
                zoomInAnim(mCurrentView);
                if (onSelect != null) {
                    onSelect.onButtonSelected(mCurrentView, mCurrentView.getId() );
                }
            }

        }*/


    }

    /**
     * 放大动画
     *
     * @param view
     */
    private void zoomInAnim(final View view) {
        //将view放在其他view之上
        view.bringToFront();
        //按照bringToFront文档来的，暂没测试
        if (Build.VERSION.SDK_INT < KITKAT) {
            requestLayout();
        }
        if (mCurrentZoomInAnim != null) {
            //如果当前有放大动画执行，cancel调
            mCurrentZoomInAnim.cancel();
        }
        ObjectAnimator objectAnimatorX = ObjectAnimator.ofFloat(view, "scaleX", 1.0f, mAnimZoomTo);
        ObjectAnimator objectAnimatorY = ObjectAnimator.ofFloat(view, "scaleY", 1.0f, mAnimZoomTo);
        objectAnimatorX.setDuration(mAnimDuration);
        objectAnimatorX.setInterpolator(mZoomInInterpolator);
        objectAnimatorY.setDuration(mAnimDuration);
        objectAnimatorY.setInterpolator(mZoomInInterpolator);
        AnimatorSet set = new AnimatorSet();
        set.playTogether(objectAnimatorX, objectAnimatorY);

        set.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
                //放大动画开始
                if (mOnZoomAnimatorListener != null) {


                    mOnZoomAnimatorListener.onZoomInStart(view);
                }
            }

            @Override
            public void onAnimationEnd(Animator animator) {
                //放大动画结束
                if (mOnZoomAnimatorListener != null) {

                    mOnZoomAnimatorListener.onZoomInEnd(view);
                }
                mCurrentZoomInAnim = null;
            }

            @Override
            public void onAnimationCancel(Animator animator) {
                //放大动画退出
                mCurrentZoomInAnim = null;
            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
        set.start();
        mCurrentZoomInAnim = set;
    }

    /**
     * 缩小动画
     *
     * @param view
     */
    private void zoomOutAnim(final View view) {
        if (mCurrentZoomOutAnim != null) {
            //如果当前有缩小动画执行，cancel调
            mCurrentZoomOutAnim.cancel();
            //动画cancel后，上一个缩小view的scaleX不是1.0，就手动设置scaleX，Y到1.0
            if (mPreZoomOutView != null && mPreZoomOutView.getScaleX() > 1.0) {
                mPreZoomOutView.setScaleX(1.0f);
                mPreZoomOutView.setScaleY(1.0f);
            }
        }
        ObjectAnimator objectAnimatorX = ObjectAnimator.ofFloat(view, "scaleX", mAnimZoomTo, 1.0f);
        ObjectAnimator objectAnimatorY = ObjectAnimator.ofFloat(view, "scaleY", mAnimZoomTo, 1.0f);
        objectAnimatorX.setDuration(mAnimDuration);
        objectAnimatorX.setInterpolator(mZoomOutInterpolator);
        objectAnimatorY.setDuration(mAnimDuration);
        objectAnimatorY.setInterpolator(mZoomOutInterpolator);
        AnimatorSet set = new AnimatorSet();
        set.playTogether(objectAnimatorX, objectAnimatorY);

        set.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
                //缩小动画开始
                if (mOnZoomAnimatorListener != null) {

                    mOnZoomAnimatorListener.onZoomOutStart(view);
                }
            }

            @Override
            public void onAnimationEnd(Animator animator) {
                //缩小动画结束
                if (mOnZoomAnimatorListener != null) {

                    mOnZoomAnimatorListener.onZoomOutStart(view);
                }


                if (mOnZoomAnimatorListener != null) {
                    mOnZoomAnimatorListener.onZoomOutEnd(view);
                }
                mCurrentZoomOutAnim = null;
            }

            @Override
            public void onAnimationCancel(Animator animator) {
                //缩小动画退出
                mCurrentZoomOutAnim = null;
            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
        set.start();
        mCurrentZoomOutAnim = set;
        //把当前缩放的view作为上一个缩放的view
        mPreZoomOutView = view;
    }

    /**
     * 设置缩放动画监听器
     *
     * @param listener
     */
    public void setOnZoomAnimatorListener(OnZoomAnimatorListener listener) {
        this.mOnZoomAnimatorListener = listener;
    }










    /**
     * 缩放动画回调
     */
    public interface OnZoomAnimatorListener {
        /**
         * 放大动画开始
         *
         * @param view
         */
        void onZoomInStart(View view);

        /**
         * 放大动画结束
         *
         * @param view
         */
        void onZoomInEnd(View view);

        /**
         * 缩小动画开始
         *
         * @param view
         */
        void onZoomOutStart(View view);

        /**
         * 缩小动画结束
         *
         * @param view
         */
        void onZoomOutEnd(View view);
    }



    public interface OnButtonSelectedListener {


        void onButtonSelected(View view, int viewId);

    }

    public void setOnButtonView(OnButtonSelectedListener onSelect) {
        this.onSelect = onSelect;
    }

    public interface OnItemSelectedListener {


        void onItemSelected(View view);
    }

    public void setOnItemView(OnItemSelectedListener onItem) {
        this.onItem = onItem;
    }


    public void setCloseAnimation(View view){
        zoomOutAnim(view);
    }







}
