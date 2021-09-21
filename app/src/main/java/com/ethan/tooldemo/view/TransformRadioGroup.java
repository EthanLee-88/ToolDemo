package com.ethan.tooldemo.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.ColorRes;

import com.ethan.tooldemo.R;

public class TransformRadioGroup extends RadioGroup {
    private static final String TAG = "TransformRadioGroup";
    private int textSize = 12;
    private int pressColor = R.color.orange;
    private int normalColor = R.color.white;
    private LayoutParams layoutParamsOne;
    private LayoutParams layoutParamsTwo;
    private CheckedChangeListener mCheckedChangeListener;

    public TransformRadioGroup(Context context) {
        this(context,null);
    }

    public TransformRadioGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public interface CheckedChangeListener{
        void checkedChange(RadioGroup group, int checkedId);
    }

    public void setCheckedChangeListener(CheckedChangeListener checkedChangeListener){
        this.mCheckedChangeListener = checkedChangeListener;
    }

    private void setCheckChange(RadioGroup group, int checkedId){
        if (this.mCheckedChangeListener == null) return;
        this.mCheckedChangeListener.checkedChange(group, checkedId);
    }

    public void setTextSize(int sp){
        for (int i = 0; i < getChildCount(); i ++){
            ((RadioButton) getChildAt(i)).setTextSize(spToPx(sp));
        }
    }

    public void setTextColor(@ColorRes int checked, @ColorRes int normal){
        for (int i = 0; i < getChildCount(); i ++){
            ((RadioButton) getChildAt(i)).setTextColor(new ColorStateList(new int[][]{{android.R.attr.state_checked},
                    {-android.R.attr.state_checked}},
                    new int[]{getResources().getColor(checked), getResources().getColor(normal)}));
        }
    }

    public void setText(String [] texts){
        if (texts.length < getChildCount()) return;
        for (int i = 0; i < getChildCount(); i ++){
            ((RadioButton) getChildAt(i)).setText(texts[i]);
        }
    }

    private void init(Context context, AttributeSet attrs){
        setBackground(getDrawableForConorAroundStroke(R.color.white_halt_transparent));
        setOrientation(HORIZONTAL);

        RadioButton radioButtonOne = getRadioButton();
        radioButtonOne.setText("one");
        RadioButton radioButtonTwo = getRadioButton();
        radioButtonTwo.setText("two");

        radioButtonOne.setBackground(getSelector(null, getLeftCheckedDrawable()));
        radioButtonTwo.setBackground(getSelector(null, getRightCheckedDrawable()));
        layoutParamsOne = new LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT);
        layoutParamsOne.weight = 2;
        layoutParamsTwo = new LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT);
        layoutParamsTwo.weight = 1;
        radioButtonOne.setLayoutParams(layoutParamsOne);
        radioButtonTwo.setLayoutParams(layoutParamsTwo);
        addView(radioButtonOne);
        addView(radioButtonTwo);
        radioButtonOne.setChecked(true);
        setOnCheckedChangeListener((RadioGroup group, int checkedId) ->{
            Log.d(TAG, "checkedId = " + checkedId);
            if (checkedId == 1) {
                layoutParamsOne.weight = 2;
                layoutParamsTwo.weight = 1;
            }else if (checkedId == 2){
                layoutParamsOne.weight = 1;
                layoutParamsTwo.weight = 2;
            }
            radioButtonOne.setLayoutParams(layoutParamsOne);
            radioButtonTwo.setLayoutParams(layoutParamsTwo);
            setCheckChange(group, checkedId);
        });
    }

    public static StateListDrawable getSelector(Drawable normalDraw, Drawable pressedDraw) {
        StateListDrawable stateListDrawable  = new StateListDrawable();
        stateListDrawable.addState(new int[]{ android.R.attr.state_checked }, pressedDraw);
        stateListDrawable.addState(new int[]{ }, normalDraw);
        return stateListDrawable ;
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private Drawable getLeftCheckedDrawable(){
        return getResources().getDrawable(R.mipmap.radio_rec_drawable_left);
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private Drawable getRightCheckedDrawable(){
        return getResources().getDrawable(R.mipmap.radio_rec_drawable_right);
    }

    /**
     *
     * @param color color
     * @return return
     */
    private GradientDrawable getDrawableForConorAroundStroke(@ColorRes int color) {
        float[] circleAngleArr = {15, 15, 15, 15,
                0,0,0,0};
        for (int i = 0; i < circleAngleArr.length; i++){
            circleAngleArr[i] = dpToPx(circleAngleArr[i]);
        }
        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setShape(GradientDrawable.RECTANGLE);
        gradientDrawable.setCornerRadii(circleAngleArr);//圆角
        gradientDrawable.setColor(getResources().getColor(color));
        return gradientDrawable;
    }

    private RadioButton getRadioButton() {
        RadioButton radioButton = new RadioButton(getContext());
        radioButton.setButtonDrawable(null);
        radioButton.setChecked(false);
        radioButton.setGravity(Gravity.CENTER);
        radioButton.setTextSize(dpToPx(textSize));
        //
        radioButton.setTextColor(new ColorStateList(new int[][]{{android.R.attr.state_checked}, {-android.R.attr.state_checked}},
                new int[]{getResources().getColor(pressColor), getResources().getColor(normalColor)}));
        return radioButton;
    }

    private float dpToPx(float dp) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, getResources().getDisplayMetrics());
    }

    private float spToPx(float sp) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, sp, getResources().getDisplayMetrics());
    }
}
