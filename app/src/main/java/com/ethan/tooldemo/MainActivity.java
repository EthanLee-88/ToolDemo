package com.ethan.tooldemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.ethan.tooldemo.util.MyCountDownTimer;
import com.ethan.tooldemo.util.SelectorUtils;
import com.ethan.tooldemo.view.BannerPageAdapter;
import com.ethan.tooldemo.view.BottomNavigationViewWrapper;
import com.ethan.tooldemo.view.CustomRadioGroup;
import com.ethan.tooldemo.view.MyCustomRadioGroup;
import com.ethan.tooldemo.view.TransformRadioGroup;
import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    BottomNavigationViewWrapper bottomNavigationViewWrapper;
    ViewPager viewPager;
    BannerPageAdapter bannerPageAdapter;
    List<View> pages = new ArrayList<>();
    CustomRadioGroup customRadioGroup;
    TransformRadioGroup transformRadioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.nav_view);
        viewPager = findViewById(R.id.id_banner_pager);
        customRadioGroup = findViewById(R.id.id_stroke_bottom);
        transformRadioGroup = findViewById(R.id.id_transform_group);
        transformRadioGroup.setText(new String[]{"竞拍中", "已结束"});
        transformRadioGroup.setTextSize(16);
        transformRadioGroup.setCheckedChangeListener(new TransformRadioGroup.CheckedChangeListener() {
            @Override
            public void checkedChange(RadioGroup group, int checkedId) {
                Log.d("tag", "checkedId- = " + checkedId);
                if (checkedId == 1) {
                    countDownTimer.cancel();
                    countDownTimer.setMillisInFuture(countDownTimer.getMillisInFuture() + 5000);
                } else {
                    countDownTimer.start();
                }
            }
        });

        customRadioGroup.setButtonBackgroundColor(R.color.teal_200, R.color.gray);
        customRadioGroup.setStrokeBackground(new String[]{"one", "two"}, 88, 28);


        pages.add(getPage());
        pages.add(getPage());
        pages.add(getPage());
        bannerPageAdapter = new BannerPageAdapter(pages, viewPager);
        getLifecycle().addObserver(bannerPageAdapter);
        bannerPageAdapter.setOnItemClickListener((int item) -> {
            Log.d("tag", "itemClick = " + item);
        });

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                Log.d("tag", "item = " + item.getItemId());
                if (item.getItemId() == R.id.navigation_notifications) {
                    startActivity(new Intent(MainActivity.this, CameraActivity.class));
                    return false;
                }
                if (item.getItemId() == R.id.navigation_home) {
                    startActivity(new Intent(MainActivity.this, TestActivity.class));
                    return false;
                }
                if (item.getItemId() == R.id.navigation_dashboard) {
                    startActivity(new Intent(MainActivity.this, ViewPagerActivity.class));
                    return false;
                }
                return true;
            }
        });
        test();
    }

    MyCountDownTimer countDownTimer;

    private void test() {
        countDownTimer = new MyCountDownTimer(10000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                int m = (int) (millisUntilFinished / 1000);
                Log.d("tag", "onTick = " + m);
            }

            @Override
            public void onFinish() {
                Log.d("tag", "onTick = onFinish");
            }
        };
    }

    private View getPage() {
        TextView textView = new TextView(this);
        textView.setText("test");
        textView.setTextSize(28);
        textView.setTextColor(getResources().getColor(R.color.black));
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(68, 68);
        textView.setLayoutParams(layoutParams);
        return textView;
    }

    public void setIconSize(int iconSize, ImageView icon) {
        FrameLayout.LayoutParams iconParams = (FrameLayout.LayoutParams) icon.getLayoutParams();
        iconParams.width = iconSize;
        iconParams.height = iconSize;
        icon.setLayoutParams(iconParams);
    }
}