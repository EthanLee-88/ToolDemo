package com.ethan.tooldemo.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;

import androidx.appcompat.widget.AppCompatCheckBox;

import com.ethan.tooldemo.R;

public class MyCheckedBox extends AppCompatCheckBox {

    public MyCheckedBox(Context context) {
        super(context);
    }

    public MyCheckedBox(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context){
        setButtonDrawable(null);
        setChecked(false);
        setGravity(Gravity.CENTER);
    }

}
