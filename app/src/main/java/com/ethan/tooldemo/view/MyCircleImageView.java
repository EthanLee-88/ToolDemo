package com.ethan.tooldemo.view;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

public class MyCircleImageView extends CircleImageView {
    private String TAG = "MyCircleImageView";

    public boolean isThis = false;
    private final float selectAlpha=1f;
    private final float unSelectAlpha=0.5f;
    public MyCircleImageView(Context context) {
        super(context);
        setAlpha(isThis ? selectAlpha :unSelectAlpha);
    }

    public MyCircleImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {

            case MotionEvent.ACTION_DOWN:
                Log.d(TAG, "touch down");
                startAni( 1, 0.8f, 1, 0);
                break;
            case MotionEvent.ACTION_UP:
                Log.d(TAG, "touch up");
                isThis=true;
                startAni(0.8f, 1.2f, 250, 2);
                break;
            case MotionEvent.ACTION_CANCEL:
                Log.d(TAG, "touch cancel");
                startAni(0.8f, isThis ? 1.2f : 1, 250, isThis ? 2 : 0);
                break;
            case MotionEvent.ACTION_OUTSIDE:
                Log.d(TAG, "touch down");
                break;
        }
        return super.onTouchEvent(event);
    }

    public void startAni(float beginScal,float endScal,long time, int borderWidth) {
        setBorderWidth(borderWidth);
        setBorderColor(Color.WHITE);
        AnimatorSet scaleAni = new AnimatorSet();
        scaleAni.playTogether(
                ObjectAnimator.ofFloat(this, "scaleX", beginScal, endScal).setDuration(time),
                ObjectAnimator.ofFloat(this, "scaleY", beginScal, endScal).setDuration(time)
        );
        scaleAni.start();
        setAlpha(isThis ? selectAlpha :unSelectAlpha);
    }
}
