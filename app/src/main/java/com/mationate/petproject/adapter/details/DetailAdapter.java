package com.mationate.petproject.adapter.details;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.mationate.petproject.R;
import com.squareup.picasso.Picasso;

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

        final ImageView imageView = holder.imageView;
        Picasso.with(imageView.getContext()).load(urls.get(position)).into(imageView);
        //imageView.setOnTouchListener(new ImageMatrixTouchHandler(imageView.getContext()));

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


        ImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.detailIv);
        }
    }
}
