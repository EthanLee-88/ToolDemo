package com.ethan.tooldemo.view;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

public class PageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final String TAG = "PageAdapter";
    private OnItemClickListener mOnItemClickListener;
    private RecyclerView mRecyclerView;
    private int currentPage = 0;

    public interface OnItemClickListener{
        void itemClick(int position, View clickView);
    }

    public PageAdapter setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.mOnItemClickListener = onItemClickListener;
        return this;
    }

    public void setCurrentItem(int item){
        if (item < 0) return;
        if (item >= getItemCount()) return;
        if (item == currentPage) return;
        setCurrentItem(item, null);
    }

    private void setCurrentItem(int position, View clickView){
        setClick(position, clickView);
        currentPage = position;
        notifyDataSetChanged();
    }

    private void setClick(int position, View clickView){
        if (mOnItemClickListener == null) return;
        this.mOnItemClickListener.itemClick(position, clickView);
     }

    protected static class NViewHolder extends RecyclerView.ViewHolder{
        public ConstraintLayout mainLayout;
        public TextView textView;
        public NViewHolder(View itemView) {
            super(itemView);
            mainLayout = itemView.findViewById(R.id.id_main_item_layout);
            textView = itemView.findViewById(R.id.id_index_text);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view_pager, parent, false);
        return new NViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        NViewHolder viewHolder = (NViewHolder) holder;
        viewHolder.textView.setText(position + "");
        viewHolder.mainLayout.setOnClickListener(v -> {
            Log.d(TAG, "position = " + position);
            if (position == currentPage) return;
            setCurrentItem(position, viewHolder.mainLayout);
        });
        if (position == currentPage){
            viewHolder.mainLayout.setBackgroundColor(viewHolder.mainLayout.getContext().getResources().getColor(R.color.teal_700));
        }else {
            viewHolder.mainLayout.setBackgroundColor(viewHolder.mainLayout.getContext().getResources().getColor(R.color.teal_200));
        }
    }

    @Override
    public int getItemCount() {
        return 5;
    }
}
