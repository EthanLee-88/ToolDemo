package com.ethan.tooldemo;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.ethan.tooldemo.pop.TypePopWindow;

import java.util.Arrays;

public class TestActivity extends AppCompatActivity {
    private static final String TAG = "TestActivity";
    private ConstraintLayout headLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        headLayout = findViewById(R.id.id_test_head);
        headLayout.setOnClickListener((View view) -> {
            TypePopWindow typePopWindow = new TypePopWindow(this);
            typePopWindow.show(headLayout, getWindowManager());
        });
    }
}
