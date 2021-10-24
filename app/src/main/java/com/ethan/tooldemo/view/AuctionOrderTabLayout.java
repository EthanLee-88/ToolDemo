package com.ethan.tooldemo.view;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.ColorInt;

public class AuctionOrderTabLayout extends RadioGroup {
    private static final String TAG = "AuctionOrderTabLayout";
    private String[] items = new String[]{"全部", "待付款", "待收货", "已完成"};
    private OnCheckedItemChangeListener mOnCheckedItemChangeListener;

    public AuctionOrderTabLayout(Context context) {
        super(context);
        init(context, null);
    }

    public AuctionOrderTabLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public interface OnCheckedItemChangeListener{
        void checkedIndex(RadioGroup radioGroup, int index);
    }

    public AuctionOrderTabLayout setOnCheckedItemChangeListener(OnCheckedItemChangeListener onCheckedItemChangeListener){
        this.mOnCheckedItemChangeListener = onCheckedItemChangeListener;
        return this;
    }

    private void setCheckedChange(RadioGroup radioGroup,int index){
        if (this.mOnCheckedItemChangeListener == null) return;
        this.mOnCheckedItemChangeListener.checkedIndex(radioGroup, index);
    }

    private void init(Context context, AttributeSet attrs) {
        setOrientation(HORIZONTAL);
        setChildren();
        setOnCheckedChangeListener((group, checkedId) -> {
            setCheckedChange(this,getCheckedChildIndex());
        });
    }

    public AuctionOrderTabLayout setTabItems(String[] itemStr){
        if (itemStr == null) {
            removeAllViews();
            return this;
        }else {
            items = itemStr;
            setChildren();
        }
        return this;
    }

    private void setChildren() {
        removeAllViews();
        for (int i = 0; i < items.length; i ++) {
            RadioButton radioButton = getRadioButton();
            radioButton.setText(items[i]);
            radioButton.setId(i);
            addView(radioButton);
        }
        if (items.length > 0) check(0);
    }

    private int getCheckedChildIndex(){
        for (int i = 0; i < getChildCount(); i ++){
            if (((RadioButton) getChildAt(i)).isChecked()) return i;
        }
        return -1;
    }

    private RadioButton getRadioButton() {
        RadioButton radioButton = new RadioButton(getContext());
        LayoutParams layoutParams = new LayoutParams((int) dpToPx(93.5f), (int) dpToPx(45f));
        radioButton.setLayoutParams(layoutParams);
        radioButton.setButtonDrawable(null);
        radioButton.setGravity(Gravity.CENTER);
        radioButton.setTextSize(spToPx(15));
        radioButton.setTextColor(new ColorStateList(new int[][]{{android.R.attr.state_checked}, {-android.R.attr.state_checked}},
                new int[]{Color.RED, Color.BLACK}));
        radioButton.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null,
                null, getSelector(getShapeDrawable(Color.RED), getShapeDrawable(Color.TRANSPARENT)));
        return radioButton;
    }

    public static StateListDrawable getSelector(Drawable checked, Drawable unchecked) {
        StateListDrawable stateListDrawable = new StateListDrawable();
        stateListDrawable.addState(new int[]{android.R.attr.state_checked}, checked);
        stateListDrawable.addState(new int[]{}, unchecked);
        return stateListDrawable;
    }

    public GradientDrawable getShapeDrawable(@ColorInt int shapeColor) {
        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setSize(dp2px(93.5f), dp2px(2));
        gradientDrawable.setShape(GradientDrawable.RECTANGLE);
        gradientDrawable.setColor(shapeColor);
        return gradientDrawable;
    }

    public int dp2px(float dp) {
        return (int) (0.5F + dp * getResources().getDisplayMetrics().density);
    }

    private float dpToPx(float dp) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, getResources().getDisplayMetrics());
    }

    private float spToPx(float sp) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, sp, getResources().getDisplayMetrics());
    }
}
