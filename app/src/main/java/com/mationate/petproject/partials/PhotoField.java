package com.mationate.petproject.partials;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.mationate.petproject.R;

import java.util.List;

public class PhotoField implements View.OnClickListener {

    private LinearLayout linearLayout;
    private Button button;
    private FieldCallback callback;
    private Listener listener;

    public PhotoField(ViewGroup viewGroup) {
        LayoutInflater layoutInflater = (LayoutInflater) viewGroup.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        linearLayout = (LinearLayout) layoutInflater.inflate(R.layout.partial_photo, viewGroup,false);
        button = linearLayout.findViewById(R.id.galleryBtn);
    }

    public View getView (){
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

    }

    public interface Listener {
        void requestPhotos();
    }
}
