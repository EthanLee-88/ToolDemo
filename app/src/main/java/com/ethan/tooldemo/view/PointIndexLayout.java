package com.ethan.tooldemo.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.ColorRes;
import androidx.annotation.Nullable;

import com.ethan.tooldemo.R;

import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

/*****************
 * Ethan Lee
 * 2021/11/7
 * ****************/

public class PointIndexLayout extends LinearLayout {
    private static final String TAG = "PointIndexLayout";

    public PointIndexLayout(Context context) {
        this(context, null);
    }

    public PointIndexLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PointIndexLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        setOrientation(HORIZONTAL);
        post(() -> {
            ViewGroup.LayoutParams params = getLayoutParams();
            params.height = WRAP_CONTENT;
            params.width = WRAP_CONTENT;
            setLayoutParams(params);
        });
    }

    public PointIndexLayout setPoints(int size) {
        if (size < 0) return this;
        removeAllViews();
        for (int i = 0; i < size; i++) {
            PointView pointView = getPointView();
            addView(pointView);
        }
        return this;
    }

    public PointIndexLayout setPointSpaceDp(int dp) {
        for (int i = 0; i < getChildCount(); i++) {
            View view = getChildAt(i);
            LinearLayout.LayoutParams params = (LayoutParams) view.getLayoutParams();
            params.leftMargin = (int) dpToPx(dp);
            view.setLayoutParams(params);
        }
        requestLayout();
        return this;
    }

    public PointIndexLayout setChecked(int index) {
        if (getChildCount() <= index) return this;
        for (int i = 0; i < getChildCount(); i++) {
            if (index == i) {
                ((PointView) getChildAt(i)).setChecked(true);
            } else {
                ((PointView) getChildAt(i)).setChecked(false);
            }
        }
        return this;
    }

    public PointIndexLayout setPointCheckedColor(@ColorRes int checkedColor) {
        for (int i = 0; i < getChildCount(); i++) {
            ((PointView) getChildAt(i)).setCheckedColor(checkedColor);
        }
        return this;
    }

    public PointIndexLayout setUnPointCheckedColor(@ColorRes int unCheckedColor) {
        for (int i = 0; i < getChildCount(); i++) {
            ((PointView) getChildAt(i)).setUnCheckedColor(unCheckedColor);
        }
        return this;
    }

    public PointIndexLayout setPointRadiusDp(int radiusDp) {
        for (int i = 0; i < getChildCount(); i++) {
            ((PointView) getChildAt(i)).setRadius((int) dpToPx(radiusDp));
        }
        return this;
    }

    private PointView getPointView() {
        PointView pointView = new PointView(getContext());
        return pointView;
    }

    private float dpToPx(float dp) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, getResources().getDisplayMetrics());
    }

    public class PointView extends View {
        private static final String TAG = "PointView";
        private int mRadius = 18;
        private Paint mPaint;
        private @ColorRes
        int checkedColor = R.color.gray;
        private @ColorRes
        int unCheckedColor = R.color.white;

        public PointView(Context context) {
            this(context, null);
        }

        public PointView(Context context, @Nullable AttributeSet attrs) {
            this(context, attrs, 0);
        }

        public PointView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
            super(context, attrs, defStyleAttr);
            init(context);
        }

        private void init(Context context) {
            mPaint = new Paint();
            mPaint.setColor(context.getResources().getColor(unCheckedColor));
            mPaint.setDither(true);
            mPaint.setAntiAlias(true);
            mPaint.setStyle(Paint.Style.FILL);
        }

        public PointView setRadius(int radius) {
            this.mRadius = radius;
            requestLayout();
            return this;
        }

        public PointView setCheckedColor(@ColorRes int checkColor) {
            this.checkedColor = checkColor;
            invalidate();
            return this;
        }

        public PointView setUnCheckedColor(@ColorRes int unCheckColor) {
            this.unCheckedColor = unCheckColor;
            invalidate();
            return this;
        }

        public PointView setChecked(boolean checked) {
            if (checked) {
                mPaint.setColor(getContext().getResources().getColor(checkedColor));
            } else {
                mPaint.setColor(getContext().getResources().getColor(unCheckedColor));
            }
            invalidate();
            return this;
        }

        @Override
        protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
            setMeasuredDimension(mRadius * 2, mRadius * 2);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            canvas.drawCircle(getWidth() / 2, getHeight() / 2, mRadius - 2, mPaint);
        }
    }
}
