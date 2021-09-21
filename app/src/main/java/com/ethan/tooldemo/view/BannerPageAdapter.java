package com.ethan.tooldemo.view;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import java.util.ArrayList;
import java.util.List;

public class BannerPageAdapter extends PagerAdapter implements LifecycleObserver {
    private static final String TAG = "BannerPageAdapter";
    private List<View> pagers = new ArrayList<View>();
    private static final int WHAT = 0x888;
    private static final long TIME_DELAY = 2000;
    private ViewPager mViewPager;
    private boolean isAlive = false;
    private boolean isEnablePageLoop = true;
    private OnItemClickListener mOnItemClickListener;

    private Handler mHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(@NonNull Message msg) {
            if (!isAlive) return;
            if (!isEnablePageLoop) return;
            mViewPager.setCurrentItem((mViewPager.getCurrentItem() + 1) % getCount(), true);
            mHandler.sendEmptyMessageDelayed(WHAT, TIME_DELAY);
        }
    };

    private void postMessage(){
        if (!isAlive) return;
        if (!isEnablePageLoop) return;
        mHandler.sendEmptyMessageDelayed(WHAT, TIME_DELAY);
    }

    private void clearMessage(){
        mHandler.removeMessages(WHAT);
    }

    private void release(){
        mHandler.removeMessages(WHAT);
        mHandler = null;
    }

    public interface OnItemClickListener{
        void onItemClick(int item);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.mOnItemClickListener = onItemClickListener;
    }

    private void setItemClick(){
        for (int i = 0; i < pagers.size(); i ++){
            int finalI = i;
            pagers.get(i).setOnClickListener((View view) -> {
                if (this.mOnItemClickListener == null) return;
                this.mOnItemClickListener.onItemClick(finalI);
            });
        }
    }

    public void setEnablePageLoop(boolean enablePageLoop) {
        this.isEnablePageLoop = enablePageLoop;
        postMessage();
    }

    public BannerPageAdapter(List<View> pager, ViewPager viewPager) {
        this.mViewPager = viewPager;
        this.pagers.addAll(pager);
        this.mViewPager.setAdapter(this);
        setItemClick();
    }

    public void clearAll() {
        this.pagers.clear();
    }

    public void add(View view) {
        this.pagers.add(view);
    }

    @Override
    public int getItemPosition(Object object) {
        return super.getItemPosition(object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        //实例化页卡
        container.addView(pagers.get(position));//添加页卡
        return pagers.get(position);
    }

    @Override
    public int getCount() {
        return pagers.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(pagers.get(position));//删除页卡
    }

    // onCreate
    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    public void onCreate(){
        Log.d(TAG, "-----------LifecycleObserver -- onCreate");
    }
    // onStart
    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    public void onStart(){
        Log.d(TAG, "-----------LifecycleObserver -- onStart");
        isAlive = true;
        postMessage();
    }
    // onPause
    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    public void onPause(){
        Log.d(TAG, "-----------LifecycleObserver -- onPause");
    }
    // onStop
    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    public void onStop(){
        Log.d(TAG, "-----------LifecycleObserver -- onStop");
        isAlive = false;
        clearMessage();
    }
    // onDestroy
    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public void onDestroy(){
        Log.d(TAG, "-----------LifecycleObserver -- onDestroy");
        release();
    }
}
