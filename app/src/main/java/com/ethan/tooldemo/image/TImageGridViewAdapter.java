package com.ethan.tooldemo.image;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.ethan.pagerview.ui.photo.OnViewTapListener;
import com.ethan.pagerview.ui.photo.PhotoView;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by public1 on 2017/5/23.
 */

public class TImageGridViewAdapter extends BaseAdapter {

    Context context;
    public List<Object> imageIds;

    public TImageGridViewAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        if (this.imageIds != null) return imageIds.size();
        return 0;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {

        if (view == null) {
            view = new PhotoView(context);
            PhotoView imageView = (PhotoView) view;
            imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
            imageView.setMinimumScale(0.5f);
            imageView.setOnViewTapListener(new OnViewTapListener() {
                @Override
                public void onViewTap(View view, float x, float y) {
                    if (onItemClickListener != null) {
                        onItemClickListener.onItemClick(i,view);
                    }
                }
            });
            AbsListView.LayoutParams param = new AbsListView.LayoutParams(com.ethan.pagerview.ui.TRect.getScreenWidth(context), com.ethan.pagerview.ui.TRect.getScreenHeight(context));
            view.setLayoutParams(param);
        }

        PhotoView imageView = (PhotoView) view;
//        Picasso.with(context).load("http://ww2.sinaimg.cn/mw690/9e6995c9gw1f2uu70bzohj209q06g3yw.jpg").into(imageView);
        if (imageIds.get(i) instanceof Integer) {
            imageView.setImageResource((Integer) imageIds.get(i));
        } else {
            Picasso.with(context).load((String) imageIds.get(i)).into(imageView);
        }


        return view;
    }


    private OnItemClickListener onItemClickListener;

    public OnItemClickListener getOnItemClickListener() {
        return onItemClickListener;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(int i, View view);
    }
}
