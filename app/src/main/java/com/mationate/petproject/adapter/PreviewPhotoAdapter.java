package com.mationate.petproject.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.mationate.petproject.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class PreviewPhotoAdapter extends RecyclerView.Adapter<PreviewPhotoAdapter.ViewHolder>{

    private List<Uri> photos = new ArrayList<>();



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_photo_preview, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Picasso.with(holder.itemView.getContext()).load(photos.get(position).toString()).centerCrop().fit().into(holder.imageView);
    }

    public void update(List<Uri> photos) {
        if (this.photos.size() > 0) {
            this.photos.clear();
        }
        this.photos.addAll(photos);
        notifyDataSetChanged();
    }


    @Override
    public int getItemCount() {
        return photos.size();
    }

    public List<Uri> getPhotos() {
        return photos;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.photoPrevIv);
        }
    }

}
