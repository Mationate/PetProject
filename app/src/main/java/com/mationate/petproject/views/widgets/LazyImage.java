package com.mationate.petproject.views.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.mationate.petproject.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

public class LazyImage extends RelativeLayout implements Callback {

    private ImageView imageView;
    private ProgressBar progressBar;

    public LazyImage(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void addContent() {
        if (getChildCount() > 0) return;
        final Context context = getContext();
        imageView = new TopCropImageView(context);
        imageView.setLayoutParams(
                new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT));
        imageView.setBackgroundResource(R.color.colorAccent);
        progressBar = new ProgressBar(context);
        LayoutParams layoutParams =
                new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);
        progressBar.setLayoutParams(layoutParams);
        addView(imageView);
        addView(progressBar);
    }

    public void setPhoto(String url) {
        View image = imageView;
        image.setAlpha(0);
        progressBar.setAlpha(1);
        Picasso.with(getContext()).load(url).into(imageView, this);
    }

    @Override
    public void onSuccess() {
        View image = imageView;
        image.animate().setDuration(400).alpha(1).start();
        progressBar.animate().setDuration(400).alpha(0).start();
    }

    @Override
    public void onError() {

    }
}