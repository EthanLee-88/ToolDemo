package com.ethan.tooldemo.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.DrawableRes;

import com.ethan.tooldemo.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class BottomNavigationViewWrapper extends BottomNavigationView {
    private LinearLayout centerItemView;

    public BottomNavigationViewWrapper(Context context) {
        this(context, null);
    }

    public BottomNavigationViewWrapper(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BottomNavigationViewWrapper(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        setItemIconTintList(null);

        centerItemView = new LinearLayout(context);
        centerItemView.setOrientation(LinearLayout.VERTICAL);
        centerItemView.setBackground(context.getResources().getDrawable(R.drawable.shape_oval));

        ImageView imageView = new ImageView(context);
        imageView.setImageResource(R.mipmap.ic_launcher);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(78, 78);
        layoutParams.topMargin = (int) dpToPx(5);
        layoutParams.gravity = Gravity.CENTER_HORIZONTAL;
        centerItemView.addView(imageView, layoutParams);

        FrameLayout.LayoutParams params =
                new FrameLayout.LayoutParams(
                        (int) dpToPx(69), (int) dpToPx(69));
        params.gravity = Gravity.CENTER;
        addView(centerItemView, params);

        ((LayoutParams) getChildAt(0).getLayoutParams()).gravity = Gravity.BOTTOM;
        setBackgroundColor(context.getResources().getColor(R.color.transparent));
        getChildAt(0).setBackgroundColor(context.getResources().getColor(R.color.white));
    }

    /**
     * @param resId 图标
     */
    public void setCenterImageResource(@DrawableRes int resId) {
        getCenterImageView().setImageResource(resId);
    }

    /**
     * @param bm 图标
     */
    public void setCenterImageBitmap(Bitmap bm) {
        getCenterImageView().setImageBitmap(bm);
    }

    /**
     * @param drawable 图标
     */
    public void setCenterImageDrawable(Drawable drawable) {
        getCenterImageView().setImageDrawable(drawable);
    }

    /**
     * @param dp 图标的 TopMargin
     */
    public void setCenterImageTopMargin(int dp) {
        LinearLayout.LayoutParams layoutParams = getCenterImageLayoutParams();
        layoutParams.topMargin = (int) dpToPx(dp);
        getCenterImageView().setLayoutParams(layoutParams);
    }

    /**
     * @param width 图标的宽
     */
    public void setCenterImageWidth(int width) {
        LinearLayout.LayoutParams layoutParams = getCenterImageLayoutParams();
        layoutParams.width = (int) dpToPx(width);
        getCenterImageView().setLayoutParams(layoutParams);
    }

    /**
     * @param height 图标的高
     */
    public void setCenterImageHeight(int height) {
        LinearLayout.LayoutParams layoutParams = getCenterImageLayoutParams();
        layoutParams.height = (int) dpToPx(height);
        getCenterImageView().setLayoutParams(layoutParams);
    }

    /**
     * @param width ItemView 的宽
     */
    public void setCenterItemViewWidth(int width) {
        FrameLayout.LayoutParams layoutParams = getCenterItemViewLayoutManager();
        layoutParams.width = (int) dpToPx(width);
        centerItemView.setLayoutParams(layoutParams);
    }

    /**
     * @param height ItemView的高
     */
    public void setCenterItemViewHeight(int height) {
        FrameLayout.LayoutParams layoutParams = getCenterItemViewLayoutManager();
        layoutParams.height = (int) dpToPx(height);
        centerItemView.setLayoutParams(layoutParams);
    }

    private LayoutParams getCenterItemViewLayoutManager(){
       LayoutParams layoutParams = (LayoutParams) centerItemView.getLayoutParams();
       return layoutParams;
    }

    private LinearLayout.LayoutParams getCenterImageLayoutParams() {
        ImageView imageView = getCenterImageView();
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) (imageView.getLayoutParams());
        return layoutParams;
    }

    private ImageView getCenterImageView() {
        return (ImageView) centerItemView.getChildAt(0);
    }

    private float dpToPx(float dp) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, getResources().getDisplayMetrics());
    }
}
