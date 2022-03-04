package com.ethan.tooldemo.image;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.widget.AppCompatImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by public1 on 2017/5/19.
 */

public class TScallImageView extends AppCompatImageView {

    public static final int STATE_NORMAL = 0;
    public static final int STATE_TRANSFORM_IN = 1;
    public static final int STATE_TRANSFORM_OUT = 2;

    public ViewGroup ninePlaceGridView;//外部设置
    private com.ethan.pagerview.ui.TRect originalRect;//得到的第一个图片相对于window的位置
    private List<com.ethan.pagerview.ui.TRect> originalRects;//得到的第所有图片相对于window的位置
    public Object imageId;//外部设置
    public List<Object> imageIds;//外部设置
    public int currentIndex;//外部设置
    public com.ethan.pagerview.ui.TImageListBgView imageListBgView;

    public TScallImageView(Context context) {
        super(context);
        init();
    }

    public TScallImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TScallImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                showImageToWindow();
            }
        });
    }


    /**获取每个图片相对于window的位置
     * @return
     */
    private List<com.ethan.pagerview.ui.TRect> getOriginalRects() {
        List<com.ethan.pagerview.ui.TRect> originalRects = new ArrayList<com.ethan.pagerview.ui.TRect>();
        int count = ninePlaceGridView.getChildCount();
        for (int i = 0; i < count; i++) {
            if (ninePlaceGridView.getChildAt(i) instanceof TScallImageView) {
                TScallImageView scallImageView = (TScallImageView) ninePlaceGridView.getChildAt(i);
                int[] outLocation = new int[2];
                scallImageView.getLocationInWindow(outLocation);
                Log.e("TAG","outLocation[0]:" + outLocation[0] + "outLocation[1]:" + outLocation[1]);
                com.ethan.pagerview.ui.TRect tRect = new com.ethan.pagerview.ui.TRect(outLocation[0],outLocation[1],scallImageView.getWidth(),scallImageView.getHeight());
                originalRects.add(tRect);
            } else {
                continue;
            }

        }
        this.originalRects = originalRects;
        return originalRects;
    }

    public void showImageToWindow() {
        int[] outLocation = new int[2];
        getLocationInWindow(outLocation);
//        originalRect = new TRect(getLeft(),getTop(),getWidth(),getHeight());
//        originalRect = new TRect(outLocation[0],outLocation[1] - TKCUtils.getStatusBarHeight(getContext()),getWidth(),getHeight());
        originalRect = new com.ethan.pagerview.ui.TRect(outLocation[0],outLocation[1],getWidth(),getHeight());
        imageListBgView = new com.ethan.pagerview.ui.TImageListBgView(getContext(),originalRect,this.imageId,this.imageIds,currentIndex);
        imageListBgView.imageId = imageId;
        imageListBgView.originalRects = getOriginalRects();
        imageListBgView.startTransform(TScallImageView.STATE_TRANSFORM_IN);

    }

}


//        imageNames = new ArrayList<Object>();
//        List<TRect> originalRects = new ArrayList<TRect>();
//        for (int i = 0; i < 8; i++) {
//        imageNames.add(R.mipmap.glenceluanch);
//        int[] outLocation = new int[2];
//        buttonOne.getLocationInWindow(outLocation);
//        TRect originalRect = new TRect(outLocation[0],outLocation[1],buttonOne.getWidth(),buttonOne.getHeight());
//        originalRects.add(originalRect);
//        }
//
//        buttonOne.setOnClickListener(v -> {
//        int[] outLocation = new int[2];
//        buttonOne.getLocationInWindow(outLocation);
////        originalRect = new TRect(getLeft(),getTop(),getWidth(),getHeight());
////        originalRect = new TRect(outLocation[0],outLocation[1] - TKCUtils.getStatusBarHeight(getContext()),getWidth(),getHeight());
//        TRect originalRect = new TRect(outLocation[0],outLocation[1],buttonOne.getWidth(),buttonOne.getHeight());
//        TImageListBgView imageListBgView = new TImageListBgView(this,originalRect,imageNames.get(0),this.imageNames,0);
//        imageListBgView.imageId = imageNames.get(0);
//        imageListBgView.originalRects = originalRects;
//        imageListBgView.startTransform(TScallImageView.STATE_TRANSFORM_IN);
//        });