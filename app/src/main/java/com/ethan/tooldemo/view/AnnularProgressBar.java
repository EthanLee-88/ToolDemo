package com.ethan.tooldemo.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

public class AnnularProgressBar extends View {
    private static final String TAG = "AnnularProgressBar";
    private int mWidth = 0;
    private Point core = new Point();
    private Paint mPaint;
    private int[] colors;

    /**
     * 旋转角度，以中心点正上方为 0 度
     */
    private double deltaAngle = 0;

    public AnnularProgressBar(Context context) {
        this(context, null);
    }

    public AnnularProgressBar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AnnularProgressBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setStrokeWidth(15);
        mPaint.setColor(Color.WHITE);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setStrokeCap(Paint.Cap.ROUND);

        colors = new int[]{Color.parseColor("#FFFFFFFF"), Color.parseColor("#FFFAFAFB"),
                Color.parseColor("#FFD4D4D6"), Color.parseColor("#FFB9BBBC"),
                Color.parseColor("#FFC6C6C6"), Color.parseColor("#FFB1B0B0"),
                Color.parseColor("#FF969697"), Color.parseColor("#FF7E7E7E"),
                Color.parseColor("#FF6E6D6D"), Color.parseColor("#FF5F5F60"),
                Color.parseColor("#FF515151"), Color.parseColor("#FF454545")};
        run();
    }

    private void run() {
        postDelayed(() -> {
//            deltaAngle += Math.PI / 64;
            deltaAngle += 2 * Math.PI / 8;
            if (deltaAngle == 2 * Math.PI) deltaAngle = 0;
            invalidate();
            run();
        }, 150);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(228, 228);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        mWidth = getWidth() / 2;
        Log.d(TAG, "mWidth = " + mWidth);
        core.x = mWidth;
        core.y = mWidth;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        for (int i = 0; i < 12; i++) {
            mPaint.setColor(colors[colors.length - i - 1]);
            canvas.drawLine((float) (core.x + (mWidth * 2 / 3) * Math.sin(i * (2 * Math.PI / 12) + deltaAngle)),
                    (float) (core.y - (mWidth * 2 / 3) * Math.cos(i * (2 * Math.PI / 12) + deltaAngle)),
                    (float) (core.x + mWidth * Math.sin(i * (2 * Math.PI / 12) + deltaAngle)),
                    (float) (core.y - mWidth * Math.cos(i * (2 * Math.PI / 12) + deltaAngle)),
                    mPaint);
        }
    }
}
