package com.ethan.tooldemo.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.annotation.Nullable;

import com.ethan.tooldemo.R;

public class CheckedPointView extends View {
    private static final String TAG = "PointView";
    private int mRadius = 12;
    private Paint mInnerPaint , mOutSidePaint;
    private @ColorInt int checkedColor = Color.parseColor("#FF2F47");
    private @ColorInt int unCheckedColor = Color.parseColor("#ffffff");
    private @ColorInt int checkStrokeColor = Color.parseColor("#FFACB5");
    private @ColorInt int unCheckStrokeColor = Color.parseColor("#cccccc");
    private boolean isChecked = false;

    public CheckedPointView(Context context) {
        this(context, null);
    }

    public CheckedPointView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CheckedPointView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        mInnerPaint = new Paint();
        mInnerPaint.setColor(unCheckedColor);
        mInnerPaint.setStyle(Paint.Style.FILL);
        mInnerPaint.setAntiAlias(true);
        mInnerPaint.setDither(true);

        mOutSidePaint = new Paint();
        mOutSidePaint.setColor(unCheckStrokeColor);
        mOutSidePaint.setStyle(Paint.Style.FILL);
        mOutSidePaint.setAntiAlias(true);
        mOutSidePaint.setDither(true);

    }

    public CheckedPointView setRadius(int radius) {
        this.mRadius = radius;
        requestLayout();
        return this;
    }

    public CheckedPointView setChecked(boolean checked){
        this.isChecked = checked;
        if (checked){
            mInnerPaint.setColor(checkedColor);
            mOutSidePaint.setColor(checkStrokeColor);
        }else {
            mInnerPaint.setColor(unCheckedColor);
            mOutSidePaint.setColor(unCheckStrokeColor);
        }
        invalidate();
        return this;
    }

    private float dpToPx(float dp) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, getResources().getDisplayMetrics());
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension((int) (dpToPx(mRadius) * 2), (int) (dpToPx(mRadius) * 2));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int x = getWidth() / 2;
        int y = getHeight() / 2;
        if (this.isChecked){
            canvas.drawCircle(x, y, dpToPx(11), mOutSidePaint);
        }else {
            canvas.drawCircle(x, y, dpToPx(7), mOutSidePaint);
        }

        canvas.drawCircle(x, y, dpToPx(6), mInnerPaint);
    }
}
