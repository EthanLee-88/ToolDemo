package com.ethan.tooldemo;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.ethan.tooldemo.adapter.MyBottomNavigationView;
import com.ethan.tooldemo.adapter.ViewPagerAdapter;
import com.ethan.tooldemo.fragment.FragmentFour;
import com.ethan.tooldemo.fragment.FragmentOne;
import com.ethan.tooldemo.fragment.FragmentThree;
import com.ethan.tooldemo.fragment.FragmentTwo;
import com.ethan.tooldemo.view.PointIndexLayout;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ViewPagerActivity extends AppCompatActivity {
    private static final String TAG = "ViewPagerActivity";
    private MyBottomNavigationView bottomNavigationView;
    private ViewPager2 viewPager2;
    private PointIndexLayout mPointIndexLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewpager);
        bottomNavigationView = findViewById(R.id.nav_view_for_pager);
        viewPager2 = findViewById(R.id.id_view_pager2);
        mPointIndexLayout = ((PointIndexLayout) findViewById(R.id.id_point_index_layout))
                .setPoints(4)
                .setPointSpaceDp(12)
                .setPointCheckedColor(R.color.red)
                .setUnPointCheckedColor(R.color.orange)
                .setPointRadiusDp(8)
                ;
        init();
        setActionBar(null);
    }
    private void init(){

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(this);
        viewPagerAdapter.addFragments(FragmentOne.class);
        viewPagerAdapter.addFragments(FragmentTwo.class);
        viewPagerAdapter.addFragments(FragmentThree.class);
        viewPagerAdapter.addFragments(FragmentFour.class);
        viewPager2.setAdapter(viewPagerAdapter);


        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                Log.d(TAG, "position = " + position);
                mPointIndexLayout.setChecked(position);
            }
        });
        bottomNavigationView.setViewPager(viewPager2);

//        bottomNavigationView.setOnNavigationItemSelectedListener((MenuItem item) -> {
//
//            return true;
//        });
    }
}
