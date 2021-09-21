package com.ethan.tooldemo.view;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;

import androidx.annotation.ColorRes;
import androidx.appcompat.widget.AppCompatTextView;

import com.ethan.tooldemo.R;

public class OvalButton extends AppCompatTextView {
    private int strokeColorId = R.color.red;
    private int strokeWidth = 1;

    public OvalButton(Context context) {
        this(context, null);
    }

    public OvalButton(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public OvalButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr){
        setGravity(Gravity.CENTER);
        setBackground(getDrawableForConorAround(strokeWidth, getResources().getColor(strokeColorId)));
    }

    public void setStrokeColor(@ColorRes int resId){
        strokeColorId = resId;
        setBackground(getDrawableForConorAround(strokeWidth, getResources().getColor(strokeColorId)));
    }

    public void setStrokeWidth(int widthDp){
        strokeWidth = widthDp;
        setBackground(getDrawableForConorAround(strokeWidth, getResources().getColor(strokeColorId)));
    }

    public GradientDrawable getDrawableForConorAround(int strokeWidth, int strokeColor) {
        strokeWidth = (int) dpToPx(strokeWidth);
        float[] circleAngleArr = {15, 15, 15, 15,
                15, 15, 15, 15};
        for (int i = 0; i < circleAngleArr.length; i++){
            circleAngleArr[i] = dpToPx(circleAngleArr[i]);
        }
        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setShape(GradientDrawable.RECTANGLE);
        gradientDrawable.setCornerRadii(circleAngleArr);//圆角
        gradientDrawable.setStroke(strokeWidth, strokeColor); //边框宽度，边框颜色
        return gradientDrawable;
    }

    private float dpToPx(float dp) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, getResources().getDisplayMetrics());
    }
}
