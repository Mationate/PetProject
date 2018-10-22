package com.mationate.petproject.adapter.details;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mationate.petproject.R;
import com.mationate.petproject.views.widgets.LazyImage;

import java.util.ArrayList;
import java.util.List;


public class DetailAdapter extends RecyclerView.Adapter<DetailAdapter.ViewHolder> {


    private List<String> urls = new ArrayList<>();

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_detail_pet, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        LazyImage lazyImage = holder.container;
        //imageView.setOnTouchListener(new ImageMatrixTouchHandler(imageView.getContext()));
        lazyImage.addContent();
        lazyImage.setPhoto(urls.get(position));

    }

    @Override
    public int getItemCount() {
        return urls.size();
    }

    public void update(List<String> urls) {
        this.urls.addAll(urls);
        notifyDataSetChanged();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final LazyImage container;

        public ViewHolder(View itemView) {
            super(itemView);
            container = (LazyImage) itemView;
        }
    }
}
