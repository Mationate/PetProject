package com.mationate.petproject.views.details.gallery;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mationate.petproject.R;
import com.mationate.petproject.adapter.details.DetailAdapter;
import com.mationate.petproject.adapter.details.DotsIndicator;

import java.util.List;


public class GalleryFragment extends Fragment {

    private DetailAdapter adapter;
    private DotsIndicator indicator;
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;



    public GalleryFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_gallery, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.detailRv);
        indicator = view.findViewById(R.id.dots);
        layoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        adapter = new DetailAdapter();
        recyclerView.setAdapter(adapter);
    }

    public void updateAdapter(List<String> urls) {
        adapter.update(urls);
        indicator.setDots(urls.size());
        SnapHelper snapHelper = new LinearSnapHelper();
        snapHelper.attachToRecyclerView(recyclerView);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int position = layoutManager.findLastCompletelyVisibleItemPosition();
                indicator.moveTo(position);
            }
        });
    }
}
