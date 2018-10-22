package com.mationate.petproject.partials;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.mationate.petproject.R;
import com.mationate.petproject.adapter.stepform.PreviewListener;
import com.mationate.petproject.adapter.stepform.PreviewPhotoAdapter;

import java.util.List;

public class PhotoField implements View.OnClickListener, PartialField, PreviewListener {


    private LinearLayout linearLayout;
    private Button button;
    private FieldCallback callback;
    private Listener listener;
    private PreviewPhotoAdapter adapter;


    public PhotoField(ViewGroup viewGroup) {
        LayoutInflater layoutInflater = (LayoutInflater) viewGroup.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        linearLayout = (LinearLayout) layoutInflater.inflate(R.layout.partial_photo, viewGroup, false);
        button = linearLayout.findViewById(R.id.galleryBtn);
        RecyclerView recyclerView = linearLayout.findViewById(R.id.previewRv);
        recyclerView.setLayoutManager(
                new LinearLayoutManager(
                        linearLayout.getContext(),
                        LinearLayoutManager.HORIZONTAL,
                        false
                ));
        recyclerView.setHasFixedSize(true);
        adapter = new PreviewPhotoAdapter(this);
        recyclerView.setAdapter(adapter);
    }

    public View getView() {
        return linearLayout;
    }

    public void setCallback(FieldCallback callback, Listener listener) {
        this.listener = listener;
        this.callback = callback;
        button.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        listener.requestPhotos();
    }

    public void setPhotos(List<Uri> photos) {
        adapter.update(photos);
        if (photos.size() > 0) {
            callback.completed();
        } else {
            callback.uncompleted("Seleccione al menos 1 foto");
        }
    }

    public List<Uri> getPhoto() {
        return adapter.getPhotos();
    }

    @Override
    public boolean isValid() {
        return adapter.getItemCount() > 0;
    }

    @Override
    public void delete() {

    }

    public interface Listener {
        void requestPhotos();
    }
}
