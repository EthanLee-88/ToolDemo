package com.ethan.tooldemo.util;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.util.Log;

/**
 * Created by bruce on 18/10/09.
 */

public class SelectorUtils {

    private static volatile SelectorUtils selectorUtils = null;
    private Context mContext;
    public static SelectorUtils getInstance(Context context) {
        SelectorUtils inst = selectorUtils;// 在这里创建临时变量
        if(selectorUtils ==null) {
            synchronized (SelectorUtils.class) {
                inst = selectorUtils;
                if(inst ==null) {
                    inst =new SelectorUtils(context);
                    selectorUtils = inst;
                }
            }
        }
        return inst;// 这里返回的是临时变量
    }
    public SelectorUtils(Context paramContext) {
        this.mContext = paramContext;
    }
    /**
     * 获取Selector
     * @param normalDraw
     * @param pressedDraw
     * @return
     */

    public static StateListDrawable getSelector(Drawable normalDraw, Drawable pressedDraw) {
        StateListDrawable stateListDrawable  = new StateListDrawable();
        stateListDrawable.addState(new int[]{ android.R.attr.state_checked }, pressedDraw);
        stateListDrawable.addState(new int[]{ }, normalDraw);
        return stateListDrawable ;
    }
    /**
     * 设置shape(设置单独圆角)
     * @param topLeftCA
     * @param topRigthCA
     * @param buttomLeftCA
     * @param buttomRightCA
     * @param bgColor
     * @param storkeWidth
     * @param strokeColor
     * @return
     */
    public GradientDrawable getDrawable(float topLeftCA, float topRigthCA, float buttomLeftCA,
                                        float buttomRightCA, int bgColor, int storkeWidth, int strokeColor) {
        //把边框值设置成dp对应的px
        storkeWidth = dp2px(this.mContext, storkeWidth);
        float[] circleAngleArr = {topLeftCA, topLeftCA, topRigthCA, topRigthCA,
                buttomLeftCA, buttomLeftCA, buttomRightCA, buttomRightCA};
        //把圆角设置成dp对应的px
        for (int i = 0; i < circleAngleArr.length; i++){
            circleAngleArr[i] = dp2px(this.mContext, circleAngleArr[i]);
        }
        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setShape(GradientDrawable.RECTANGLE);

        gradientDrawable.setCornerRadii(circleAngleArr);//圆角
        gradientDrawable.setColor(bgColor); //背景色
        gradientDrawable.setStroke(storkeWidth, strokeColor); //边框宽度，边框颜色
        return gradientDrawable;
    }

    /**
     * 设置shape(设置左侧单独圆角)
     * @param LeftCA
     * @param bgColor
     * @param storkeWidth
     * @param strokeColor
     * @return
     */
    public GradientDrawable getDrawableForConorLeft(float LeftCA, int bgColor, int storkeWidth, int strokeColor , int bnWidth , int bnHeight) {
        //把边框值设置成dp对应的px
        storkeWidth = dp2px(this.mContext, storkeWidth);
        bnWidth = dp2px(this.mContext, bnWidth);
        bnHeight = dp2px(this.mContext, bnHeight);

        float[] circleAngleArr = {LeftCA, LeftCA, 0, 0,
                0, 0, LeftCA, LeftCA};
        //把圆角设置成dp对应的px
        for (int i = 0; i < circleAngleArr.length; i++){
            circleAngleArr[i] = dp2px(this.mContext, circleAngleArr[i]);
        }
        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setShape(GradientDrawable.RECTANGLE);
        gradientDrawable.setCornerRadii(circleAngleArr);//圆角
        gradientDrawable.setSize(bnWidth , bnHeight);
        gradientDrawable.setColor(bgColor); //背景色
        gradientDrawable.setStroke(storkeWidth, strokeColor); //边框宽度，边框颜色
        return gradientDrawable;
    }
    /**
     * 设置shape(设置右侧侧单独圆角)
     * @param RightCA
     * @param bgColor
     * @param storkeWidth
     * @param strokeColor
     * @return
     */
    public GradientDrawable getDrawableForConorRight(float RightCA, int bgColor, int storkeWidth, int strokeColor , int bnWidth , int bnHeight) {
        //把边框值设置成dp对应的px
        storkeWidth = dp2px(this.mContext, storkeWidth);
        bnWidth = dp2px(this.mContext, bnWidth);
        bnHeight = dp2px(this.mContext, bnHeight);

        float[] circleAngleArr = {0, 0, RightCA, RightCA,
                RightCA, RightCA, 0, 0};
        //把圆角设置成dp对应的px
        for (int i = 0; i < circleAngleArr.length; i++){
            circleAngleArr[i] = dp2px(this.mContext, circleAngleArr[i]);
        }
        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setShape(GradientDrawable.RECTANGLE);
        gradientDrawable.setCornerRadii(circleAngleArr);//圆角
        gradientDrawable.setSize(bnWidth , bnHeight);
        gradientDrawable.setColor(bgColor); //背景色
        gradientDrawable.setStroke(storkeWidth, strokeColor); //边框宽度，边框颜色
        return gradientDrawable;
    }
    /**
     * 设置shape(设置四周圆角相等的矩形)
     * @param aroundCA
     * @param bgColor
     * @param storkeWidth
     * @param strokeColor
     * @return
     */
    public GradientDrawable getDrawableForConorAround(float aroundCA, int bgColor, int storkeWidth, int strokeColor , int bgWidth , int bgHeight) {
        //把边框值设置成dp对应的px\
        storkeWidth = dp2px(this.mContext, storkeWidth);
        bgWidth = dp2px(this.mContext, bgWidth);
        bgHeight = dp2px(this.mContext, bgHeight);

        float[] circleAngleArr = {aroundCA, aroundCA, aroundCA, aroundCA,
                aroundCA, aroundCA, aroundCA, aroundCA};
        //把圆角设置成dp对应的px
        for (int i = 0; i < circleAngleArr.length; i++){
            circleAngleArr[i] = dp2px(this.mContext, circleAngleArr[i]);
        }
        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setShape(GradientDrawable.RECTANGLE);
        gradientDrawable.setSize(bgWidth , bgHeight);
        Log.d("Se" , "宽" + bgWidth + "高" + bgHeight);
        gradientDrawable.setCornerRadii(circleAngleArr);//圆角
        gradientDrawable.setColor(bgColor); //背景色
        gradientDrawable.setStroke(storkeWidth, strokeColor); //边框宽度，边框颜色
        return gradientDrawable;
    }


    /**
     * stroke
     *
     * @param strokeWidth strokeWidth
     * @param strokeColor strokeColor
     * @return
     */
    public GradientDrawable getDrawableForConorAroundStroke(int strokeWidth, int strokeColor, int width, int height) {
        strokeWidth = (int) dp2px(mContext,strokeWidth);
        float[] circleAngleArr = {50, 50, 50, 50,
                50, 50, 50, 50};
        for (int i = 0; i < circleAngleArr.length; i++){
            circleAngleArr[i] = dp2px(mContext,circleAngleArr[i]);
        }
        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setShape(GradientDrawable.RECTANGLE);
        gradientDrawable.setSize(dp2px(mContext,width), dp2px(mContext,height));
        gradientDrawable.setCornerRadii(circleAngleArr);//圆角
        gradientDrawable.setStroke(strokeWidth, strokeColor); //边框宽度，边框颜色
        return gradientDrawable;
    }

    /**
     * 设置shape(设置无圆角的矩形)
     * @param bgColor
     * @param storkeWidth
     * @param strokeColor
     * @return
     */
    public GradientDrawable getDrawableForNoneConor(int bgColor, int storkeWidth, int strokeColor, int bnWidth , int bnHeight) {
        //把边框值设置成dp对应的px
        storkeWidth = dp2px(this.mContext, storkeWidth);
        bnWidth = dp2px(this.mContext, bnWidth);
        bnHeight = dp2px(this.mContext, bnHeight);

        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setShape(GradientDrawable.RECTANGLE);
        gradientDrawable.setColor(bgColor); //背景色
        gradientDrawable.setSize(bnWidth , bnHeight);
        gradientDrawable.setStroke(storkeWidth, strokeColor); //边框宽度，边框颜色
        return gradientDrawable;
    }
    /**
     * 设置shape(圆形)
     *
     * @param radiu
     * @param bgColor
     * @param strokeWidth
     * @param strokeColor
     * @return
     */
    public GradientDrawable getDrawableForOval(int radiu, int bgColor, int strokeWidth, int strokeColor) {
        strokeWidth = dp2px(mContext,strokeWidth);
        radiu = dp2px(mContext,radiu);
        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setSize(2 * radiu , 2 * radiu);
        gradientDrawable.setShape(GradientDrawable.OVAL);
        gradientDrawable.setColor(bgColor);
        gradientDrawable.setStroke(strokeWidth, strokeColor);
        return gradientDrawable;
    }



    public static int dp2px(Context context, float dp) {
        return (int) (0.5F + dp * context.getResources().getDisplayMetrics().density);
    }
}
