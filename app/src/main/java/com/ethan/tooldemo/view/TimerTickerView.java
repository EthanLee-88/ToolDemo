package com.ethan.tooldemo.view;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

public class TimerTickerView extends LinearLayout {
    private static final String TAG = "TimerTickerView";
    protected TextView titleText;
    protected TextView hoursText;
    protected TextView minuteText;
    protected TextView secondText;
    protected TextView branchOne;
    protected TextView branchTwo;

    public TimerTickerView(Context context) {
        this(context, null);
    }

    public TimerTickerView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TimerTickerView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context){
        setOrientation(HORIZONTAL);
        LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.rightMargin = (int) dpToPx(6);
        titleText = new TextView(context);
        titleText.setText("距结束 : ");
        titleText.setTextColor(Color.RED);
        titleText.setGravity(Gravity.CENTER);
        titleText.setTextSize(spToPx(12));
        addView(titleText,layoutParams);
        hoursText = new TextView(context);
        hoursText.setText("00");
        hoursText.setTextColor(Color.RED);
        hoursText.setGravity(Gravity.CENTER);
        hoursText.setTextSize(spToPx(14));
        addView(hoursText,layoutParams);
        branchOne = new TextView(context);
        branchOne.setText(":");
        branchOne.setTextColor(Color.RED);
        branchOne.setGravity(Gravity.CENTER);
        branchOne.setTextSize(spToPx(12));
        addView(branchOne,layoutParams);
        minuteText = new TextView(context);
        minuteText.setText("00");
        minuteText.setTextColor(Color.RED);
        minuteText.setGravity(Gravity.CENTER);
        minuteText.setTextSize(spToPx(14));
        addView(minuteText,layoutParams);
        branchTwo = new TextView(context);
        branchTwo.setText(":");
        branchTwo.setTextColor(Color.RED);
        branchTwo.setGravity(Gravity.CENTER);
        branchTwo.setTextSize(spToPx(12));
        addView(branchTwo,layoutParams);
        secondText = new TextView(context);
        secondText.setText("00");
        secondText.setTextColor(Color.RED);
        secondText.setGravity(Gravity.CENTER);
        secondText.setTextSize(spToPx(14));
        addView(secondText,layoutParams);
    }

    private float dpToPx(float dp) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, getResources().getDisplayMetrics());
    }

    private float spToPx(float sp) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, sp, getResources().getDisplayMetrics());
    }
}
