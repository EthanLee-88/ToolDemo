package com.ethan.tooldemo.adapter;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager2.widget.ViewPager2;

import com.ethan.tooldemo.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MyBottomNavigationView extends BottomNavigationView {
    private static final String TAG = "MyBottomNavigationView";
    private final int itemOne = R.id.navigation_home_pager;
    private final int itemTwo = R.id.navigation_dashboard_pager;
    private final int itemThree = R.id.navigation_pager;
    private final int itemFour = R.id.navigation_center_pager;
    public MyBottomNavigationView(Context context) {
        super(context);
    }

    public MyBottomNavigationView(Context context,AttributeSet attrs) {
        super(context, attrs);
    }

    public MyBottomNavigationView(Context context,AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setViewPager(ViewPager2 viewPager){
        if (viewPager == null) return;
        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                Log.d(TAG, "onPageSelected position = " + position);
                switch (position){
                    case 0 : setSelectedItemId(itemOne); break;
                    case 1 : setSelectedItemId(itemTwo); break;
                    case 2 : setSelectedItemId(itemThree); break;
                    case 3 : setSelectedItemId(itemFour); break;
                }
            }
        });
        setOnNavigationItemSelectedListener((MenuItem item) -> {
            int itemId = item.getItemId();
            int itemOrder = item.getOrder();
            Log.d(TAG, "itemId = " + itemId + " - itemOrder = " + itemOrder);
            switch (itemId){
                case itemOne: viewPager.setCurrentItem(0, true);break;
                case itemTwo: viewPager.setCurrentItem(1, true);break;
                case itemThree: viewPager.setCurrentItem(2, true);break;
                case itemFour: viewPager.setCurrentItem(3, true);break;
                default:break;
            }
            return true;
        });
    }
}
