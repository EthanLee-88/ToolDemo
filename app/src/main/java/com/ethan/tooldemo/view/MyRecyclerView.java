package com.ethan.tooldemo.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Arrays;

public class MyRecyclerView extends RecyclerView {
    private static final String TAG = "MyRecyclerView";

    public MyRecyclerView(Context context) {
        super(context);
        init();
    }

    public MyRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MyRecyclerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        PagerSnapHelper pagerSnapHelper = new PagerSnapHelper();
        pagerSnapHelper.attachToRecyclerView(this);
        setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));

    }

    public MyRecyclerView setAdapter(com.ethan.test0116.PageAdapter adapter){
        super.setAdapter(adapter);
        if (adapter == null) return this;
        adapter.setOnItemClickListener((position, clickView) -> {
            if (position < 0) return;
            if (position > adapter.getItemCount() - 1) return;

            if (clickView == null){
                Log.d(TAG, "position = " + position);
                smoothScrollToPosition(position);
                return;
            }

            int[] location = new int[2];
            clickView.getLocationInWindow(location);
            int coreX = (location[0] * 2 + clickView.getWidth()) / 2;
            int recyclerWidth = getWidth();
            int singleRecX = recyclerWidth / 3;
            Log.d(TAG, "coreX = " + coreX + " - singleRecX = " + singleRecX);
            Log.d(TAG, "location = " + Arrays.toString(location));
            if (coreX < singleRecX){
                if (position == 0) return;
                smoothScrollToPosition(position - 1);
            }else if (coreX < (2 * singleRecX)){

            }else {
                if (position == adapter.getItemCount() - 1) return;
                smoothScrollToPosition(position + 1);
            }
        });
        return this;
    }
}
