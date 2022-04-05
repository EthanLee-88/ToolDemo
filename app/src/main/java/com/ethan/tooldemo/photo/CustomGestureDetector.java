/*******************************************************************************
 * Copyright 2011, 2012 Chris Banes.
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package com.ethan.tooldemo.photo;

import android.content.Context;
import android.graphics.Point;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.VelocityTracker;
import android.view.ViewConfiguration;

import java.lang.reflect.Field;

/**
 * 拖动、带惯性的滑动及缩放监听
 */
class CustomGestureDetector {
    private static final String TAG = "CustomGestureDetector";
    private static final int INVALID_POINTER_ID = -1;
    private int mActivePointerId = INVALID_POINTER_ID;
    private int mActivePointerIndex = 0;
    private final ScaleGestureDetector mDetector;
    private VelocityTracker mVelocityTracker;
    private boolean mIsDragging;
    private float mLastTouchX;
    private float mLastTouchY;
    private final float mTouchSlop;
    private final float mMinimumVelocity;
    private OnGestureListener mListener;

    CustomGestureDetector(Context context, OnGestureListener listener) {
        final ViewConfiguration configuration = ViewConfiguration
                .get(context);
        mMinimumVelocity = configuration.getScaledMinimumFlingVelocity();
        mTouchSlop = configuration.getScaledTouchSlop();
        mListener = listener;
        mDetector = new ScaleGestureDetector(context, getScaleListener());
        try { // 反射修改两指间落下时距离阈值，新版API已禁止反射
            Field field = mDetector.getClass().getDeclaredField("mMinSpan");
            field.setAccessible(true);
            field.set(mDetector, 2);
        } catch (Exception e) {
        }
    }

    /**
     * 缩放手势检测，新版API禁止使用反射，所以无法改变两指间落下时距离阈值
     * 这里的实现方式弃用，改用手动检测缩放手势
     *
     * @return OnScaleGestureListener
     */
    private ScaleGestureDetector.OnScaleGestureListener getScaleListener(){
        ScaleGestureDetector.OnScaleGestureListener mScaleListener = new ScaleGestureDetector.OnScaleGestureListener() {
            @Override
            public boolean onScale(ScaleGestureDetector detector) {
                float scaleFactor = detector.getScaleFactor();
                if (Float.isNaN(scaleFactor) || Float.isInfinite(scaleFactor))
                    return false;
                return true;
            }

            @Override
            public boolean onScaleBegin(ScaleGestureDetector detector) {
                return true;
            }

            @Override
            public void onScaleEnd(ScaleGestureDetector detector) {
                // NO-OP
            }
        };
        return mScaleListener;
    }

    private float getActiveX(MotionEvent ev) {
        try {
            return ev.getX(mActivePointerIndex);
        } catch (Exception e) {
            return ev.getX();
        }
    }

    private float getActiveY(MotionEvent ev) {
        try {
            return ev.getY(mActivePointerIndex);
        } catch (Exception e) {
            return ev.getY();
        }
    }

    public boolean isScaling() {
        return mDetector.isInProgress();
    }

    public boolean isDragging() {
        return mIsDragging;
    }

    public boolean onTouchEvent(MotionEvent ev) { // 处理触摸事件
        try {
            mDetector.onTouchEvent(ev);
            return processTouchEvent(ev);
        } catch (IllegalArgumentException e) {
            // Fix for support lib bug, happening when onDestroy is called
            return true;
        }
    }

    private boolean processTouchEvent(MotionEvent ev) { // 处理触摸事件
        processDoublePointMotionEvent(ev);
        final int action = ev.getAction();
        switch (action & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                mActivePointerId = ev.getPointerId(0);
                mVelocityTracker = VelocityTracker.obtain();
                if (null != mVelocityTracker) {
                    mVelocityTracker.addMovement(ev);
                }
                mLastTouchX = getActiveX(ev);
                mLastTouchY = getActiveY(ev);
                mIsDragging = false;
                break;
            case MotionEvent.ACTION_MOVE:
                final float x = getActiveX(ev);
                final float y = getActiveY(ev);
                final float dx = x - mLastTouchX, dy = y - mLastTouchY;
                if (!mIsDragging) {
                    // Use Pythagoras to see if drag length is larger than
                    // touch slop
                    mIsDragging = Math.sqrt((dx * dx) + (dy * dy)) >= mTouchSlop;
                }
                if (mIsDragging) {
                    mListener.onDrag(dx, dy);
                    mLastTouchX = x;
                    mLastTouchY = y;
                    if (null != mVelocityTracker) {
                        mVelocityTracker.addMovement(ev);
                    }
                }
                break;
            case MotionEvent.ACTION_CANCEL:
                mActivePointerId = INVALID_POINTER_ID;
                // Recycle Velocity Tracker
                if (null != mVelocityTracker) {
                    mVelocityTracker.recycle();
                    mVelocityTracker = null;
                }
                break;
            case MotionEvent.ACTION_UP:
                mActivePointerId = INVALID_POINTER_ID;
                if (mIsDragging) {
                    if (null != mVelocityTracker) {
                        mLastTouchX = getActiveX(ev);
                        mLastTouchY = getActiveY(ev);
                        // Compute velocity within the last 1000ms
                        mVelocityTracker.addMovement(ev);
                        mVelocityTracker.computeCurrentVelocity(1000);
                        final float vX = mVelocityTracker.getXVelocity(), vY = mVelocityTracker
                                .getYVelocity();
                        // If the velocity is greater than minVelocity, call
                        if (Math.max(Math.abs(vX), Math.abs(vY)) >= mMinimumVelocity) {
                            mListener.onFling(mLastTouchX, mLastTouchY, -vX,
                                    -vY);
                        }
                    }
                }
                // Recycle Velocity Tracker
                if (null != mVelocityTracker) {
                    mVelocityTracker.recycle();
                    mVelocityTracker = null;
                }
                break;
            case MotionEvent.ACTION_POINTER_UP:
                final int pointerIndex = Util.getPointerIndex(ev.getAction());
                final int pointerId = ev.getPointerId(pointerIndex);
                if (pointerId == mActivePointerId) {
                    // This was our active pointer going up. Choose a new
                    // active pointer and adjust accordingly.
                    final int newPointerIndex = pointerIndex == 0 ? 1 : 0;
                    mActivePointerId = ev.getPointerId(newPointerIndex);
                    mLastTouchX = ev.getX(newPointerIndex);
                    mLastTouchY = ev.getY(newPointerIndex);
                }
                break;
        }
        mActivePointerIndex = ev.findPointerIndex(mActivePointerId != INVALID_POINTER_ID ? mActivePointerId : 0);
        return true;
    }

    private Point pointOne, pointTwo;

    private boolean processDoublePointMotionEvent(MotionEvent event) { // 处理双指缩放事件
        if (event == null) return false;
        if (mListener == null) return false;
        int pointCount = event.getPointerCount();
        Log.d(TAG, "pointCount = " + pointCount);
        if (pointCount != 2) return false;
        if ((pointOne == null) || (pointTwo == null)) {
            pointOne = new Point();
            pointTwo = new Point();
        }
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_POINTER_DOWN:
                float oneX = event.getX(0);
                float oneY = event.getY(0);
                float twoX = event.getX(1);
                float twoY = event.getY(1);
                Log.d(TAG, "oneX = " + oneX + " - oneY = " + oneY + " - twoX = " + twoX + " - twoY = " + twoY);
                pointOne.set((int) oneX, (int) oneY);
                pointTwo.set((int) twoX, (int) twoY);
                break;
            case MotionEvent.ACTION_MOVE:
                float oneMoveX = event.getX(0);
                float oneMoveY = event.getY(0);
                float twoMoveX = event.getX(1);
                float twoMoveY = event.getY(1);
                Log.d(TAG, "oneMoveX = " + oneMoveX + " - oneMoveY = " + oneMoveY +
                        " - twoMoveX = " + twoMoveX + " - twoMoveY = " + twoMoveY);
                double currentDistance = getTwoPointDistance(oneMoveX, oneMoveY, twoMoveX, twoMoveY);
                double lastDistance = getTwoPointDistance(pointOne.x, pointOne.y, pointTwo.x, pointTwo.y);
                double deltaDistance = currentDistance - lastDistance; // 前后距离差
                double scaleFactor = getScaleFactor(deltaDistance); // 缩放因子
                Log.d(TAG, "currentDistance = " + currentDistance + " - lastDistance = " + lastDistance);
                Log.d(TAG, "deltaDistance = " + deltaDistance + " - scaleFactor = " + scaleFactor);
                mListener.onScale((float) scaleFactor, getFocusValue(oneMoveX, twoMoveX), getFocusValue(oneMoveY, twoMoveY));
                pointOne.set((int) oneMoveX, (int) oneMoveY);
                pointTwo.set((int) twoMoveX, (int) twoMoveY);
                break;
            default:
                break;
        }
        return false;
    }

    private double getScaleFactor(double deltaDistance) { // 计算缩放因子
        double scaleFactor = 1;
        double factor = (Math.min(0.11, Math.abs(deltaDistance / 100)));
        if (deltaDistance > 0) { // 放大
            scaleFactor = scaleFactor + factor;
        } else { // 缩小
            scaleFactor = scaleFactor - factor;
        }
        Log.d(TAG, "scaleFactor = " + scaleFactor);
        return scaleFactor;
    }

    private double getTwoPointDistance(float oneX, float oneY, float twoX, float twoY) { // 计算两点间的距离
        return Math.sqrt((twoX - oneX) * (twoX - oneX) + (twoY - oneY) * (twoY - oneY));
    }

    private float getFocusValue(float one, float two) {
        float focus = (one + two) / 2;
        Log.d(TAG, " - focus = " + focus);
        return focus;
    }
}
