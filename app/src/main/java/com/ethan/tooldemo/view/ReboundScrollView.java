package com.ethan.tooldemo.view;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;

import androidx.core.widget.NestedScrollView;

/**
 * 封装 NestedScrollView
 * <p>
 * 实现越界回弹
 * <p>
 * 2022.0203 EthanLee
 */
public class ReboundScrollView extends NestedScrollView {
    private boolean mEnableTopRebound = true;
    private boolean mEnableBottomRebound = true;
    private OnReBoundEndListener mOnReBoundEndListener;
    private View mContentView;
    private final Rect mRect = new Rect();
    private int lastY;
    private boolean rebound = false;

    public ReboundScrollView(Context context) {
        super(context);
        init();
    }

    public ReboundScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ReboundScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
    }

    public interface OnReBoundEndListener { // 回弹监听
        void reBoundCompleted(int direction);
    }

    public ReboundScrollView setOnReBoundEndListener(OnReBoundEndListener onReBoundEndListener) {
        this.mOnReBoundEndListener = onReBoundEndListener;
        return this;
    }

    public void setReBound(int direction) {  //<0 表示下部回弹  0 表示上部回弹 0表示不回弹
        if (this.mOnReBoundEndListener == null) return;
        this.mOnReBoundEndListener.reBoundCompleted(direction);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mContentView = getChildAt(0);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if (mContentView == null) return;
        // 记录子View的区域
        mRect.set(mContentView.getLeft(), mContentView.getTop(), mContentView.getRight(), mContentView.getBottom());
    }

    public ReboundScrollView setEnableTopRebound(boolean enableTopRebound) { // 使能
        this.mEnableTopRebound = enableTopRebound;
        return this;
    }

    public ReboundScrollView setEnableBottomRebound(boolean mEnableBottomRebound) { // 使能
        this.mEnableBottomRebound = mEnableBottomRebound;
        return this;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (mContentView == null) {
            return super.dispatchTouchEvent(ev);
        }
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                lastY = (int) ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                if (!isScrollToTop() && !isScrollToBottom()) { // 滑动没有到底，也没有到顶
                    lastY = (int) ev.getY();
                    break;
                }
                int deltaY = (int) (ev.getY() - lastY); // 偏移量

                if ((!mEnableTopRebound && deltaY > 0) || (!mEnableBottomRebound && deltaY < 0)) { //判断使能
                    break;
                }
                int offset = (int) (deltaY * 0.48); //阻尼
                mContentView.layout(mRect.left, mRect.top + offset, mRect.right, mRect.bottom + offset); // 越界
                rebound = true;
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                if (!rebound) break;
                final int reboundDirection = mContentView.getTop() - mRect.top;
                TranslateAnimation animation = new TranslateAnimation(0, 0, mContentView.getTop(), mRect.top);
                animation.setDuration(300);
                animation.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        setReBound(reboundDirection);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {
                    }
                });
                mContentView.startAnimation(animation); // 补间动画，实现回弹
                mContentView.layout(mRect.left, mRect.top, mRect.right, mRect.bottom); // 回弹
                rebound = false;
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public void setFillViewport(boolean fillViewport) {
        super.setFillViewport(true); //默认是填充ScrollView 或者再XML布局文件中设置fillViewport属性
    }

    /**
     * 判断当前ScrollView是否处于顶部
     */
    private boolean isScrollToTop() {
        return getScrollY() == 0;
    }

    /**
     * 判断当前ScrollView是否已滑到底部
     */
    private boolean isScrollToBottom() {
        return mContentView.getHeight() <= getHeight() + getScrollY();
    }
}
