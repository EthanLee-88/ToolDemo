package com.ethan.tooldemo.view;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.Spinner;

import com.ethan.tooldemo.R;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

public class BasePopWindow extends PopupWindow {
    public BasePopWindow(Context context, View contentView) {
        super(context);
        setTouchable(true);
        setFocusable(true);
        setOutsideTouchable(true);
        setAnimationStyle(R.style.Animation_Top_Sheet);
        setContentView(contentView);
        setHeight(WRAP_CONTENT);
        setWidth(MATCH_PARENT);

    }

}
