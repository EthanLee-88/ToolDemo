package com.ethan.tooldemo.util;

import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.SystemClock;

public abstract class MyCountDownTimer {
    /**
     * 计时时长
     */
    private long mMillisInFuture;
    /**
     * 步进 毫秒
     */
    private long mCountdownInterval = 1000;

    private long mStopTimeInFuture;

    private boolean mCancelled = false;

    /**
     * @param millisInFuture  millisInFuture
     * @param countDownInterval countDownInterval
     */
    public MyCountDownTimer(long millisInFuture, long countDownInterval) {
        mMillisInFuture = millisInFuture;
        mCountdownInterval = countDownInterval;
    }

    /**
     * 停止计时
     */
    public synchronized final void cancel() {
        mCancelled = true;
        mHandler.removeMessages(MSG);
    }

    /**
     * 开始计时
     */
    public synchronized final MyCountDownTimer start() {
        mCancelled = false;
        if (mMillisInFuture <= 0) {
            onFinish();
            return this;
        }
        mStopTimeInFuture = SystemClock.elapsedRealtime() + mMillisInFuture;
        mHandler.sendMessage(mHandler.obtainMessage(MSG));
        return this;
    }

    public void setMillisInFuture(long millisInFuture){
        mMillisInFuture = millisInFuture;
    }

    public long getMillisInFuture(){
       return this.mMillisInFuture;
    }

    public void setCountdownInterval(long countDownInterval){
        mCountdownInterval = countDownInterval;
    }

    /**
     * 计时回调
     * @param millisUntilFinished millisUntilFinished
     */
    public abstract void onTick(long millisUntilFinished);

    /**
     * 计时结束
     */
    public abstract void onFinish();

    private static final int MSG = 1;
    private Handler mHandler = new Handler(Looper.myLooper()) {
        @Override
        public void handleMessage(Message msg) {
            synchronized (MyCountDownTimer.this) {
                if (mCancelled) {
                    return;
                }
                final long millisLeft = mStopTimeInFuture - SystemClock.elapsedRealtime();
                if (millisLeft <= 0) {
                    onFinish();
                } else {
                    long lastTickStart = SystemClock.elapsedRealtime();
                    onTick(millisLeft);
                    // take into account user's onTick taking time to execute
                    long lastTickDuration = SystemClock.elapsedRealtime() - lastTickStart;
                    long delay;
                    if (millisLeft < mCountdownInterval) {
                        // just delay until done
                        delay = millisLeft - lastTickDuration;
                        // special case: user's onTick took more than interval to
                        // complete, trigger onFinish without delay
                        if (delay < 0) delay = 0;
                    } else {
                        delay = mCountdownInterval - lastTickDuration;
                        // special case: user's onTick took more than interval to
                        // complete, skip to next interval
                        while (delay < 0) delay += mCountdownInterval;
                    }
                    sendMessageDelayed(obtainMessage(MSG), delay);
                }
            }
        }
    };
}
