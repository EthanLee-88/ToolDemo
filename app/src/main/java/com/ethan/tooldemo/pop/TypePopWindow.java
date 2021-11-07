package com.ethan.tooldemo.pop;

import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;

import com.ethan.tooldemo.R;
import com.ethan.tooldemo.view.BasePopWindow;

import java.util.Arrays;

public class TypePopWindow {
    private static final String TAG = "TypePopWindow";
    private BasePopWindow basePopWindow;
    private Context mContext;

    public TypePopWindow(Context context) {
        mContext = context;
        basePopWindow = new BasePopWindow(context, initView());
        basePopWindow.setBackgroundDrawable(null);
    }

    private View initView() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.pop_test,
                null, false);

        return view;
    }

    private void initHeight(View location, WindowManager windowManager){
        int bottom = windowManager.getDefaultDisplay().getHeight();
        int[] left = new int[2];
        location.getLocationInWindow(left);
        int top = left[1] +location.getHeight();
        int height = bottom - top;
        Log.d(TAG, "bottom = " + bottom + " - left = " +
                Arrays.toString(left) + " - getHeight = " + location.getHeight());
        basePopWindow.setHeight(height);
    }

    public void show(View location, WindowManager windowManager){
        initHeight(location, windowManager);
//        basePopWindow.showAsDropDown(location, 0, 0, Gravity.BOTTOM);
        basePopWindow.showAtLocation(location, Gravity.BOTTOM, 0, 0);

    }

}
