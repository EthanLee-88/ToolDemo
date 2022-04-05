package com.ethan.tooldemo.photo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.ethan.tooldemo.R;

public class PagerTwoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    protected static class PictureViewHolder extends RecyclerView.ViewHolder {
        public PhotoView mPhotoView;
        public ConstraintLayout mainLayout;
        public PictureViewHolder(View itemView) {
            super(itemView);
            mPhotoView = itemView.findViewById(R.id.id_view_pager_photo);
            mainLayout = itemView.findViewById(R.id.id_pager_two_main_layout);
        }
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View  view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view_pager_two, parent, false);
        return new PictureViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        PictureViewHolder pictureViewHolder = (PictureViewHolder) holder;
        pictureViewHolder.mPhotoView.setImageResource(R.mipmap.glenceluanch);
        pictureViewHolder.mPhotoView.setOnViewTapListener((view, x, y) -> {
                    pictureViewHolder.mainLayout.setVisibility(View.INVISIBLE);
        });
    }

    @Override
    public int getItemCount() {
        return 5;
    }
}
