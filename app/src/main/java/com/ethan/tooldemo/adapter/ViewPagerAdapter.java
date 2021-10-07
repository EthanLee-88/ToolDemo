package com.ethan.tooldemo.adapter;

import android.util.Log;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerAdapter extends FragmentStateAdapter {
    private static final String TAG = "ViewPagerAdapter";
    private List<Class> fragments;

    public ViewPagerAdapter(FragmentActivity fragmentActivity) {
        super(fragmentActivity);
        if (fragments == null) fragments = new ArrayList<>();
    }

    public void addFragments(Class clazz){
        if (fragments == null) return;
        fragments.add(clazz);
    }

    @Override
    public Fragment createFragment(int position) {
        try {
            return (Fragment) fragments.get(position).newInstance();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            Log.d(TAG, "IllegalAccessException");
        } catch (InstantiationException e) {
            e.printStackTrace();
            Log.d(TAG, "InstantiationException");
        }
        return null;
    }

    @Override
    public int getItemCount() {
        return fragments.size();
    }
}
