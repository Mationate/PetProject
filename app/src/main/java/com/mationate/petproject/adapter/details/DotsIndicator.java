package com.mationate.petproject.adapter.details;

import android.content.Context;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.AttributeSet;
import android.view.View;

import com.mationate.petproject.R;


public class DotsIndicator extends LinearLayoutCompat {

    public DotsIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setDots(int total) {
        final int size = (int) getContext().getResources().getDimension(R.dimen.dot_size);
        final int margin = (int) getContext().getResources().getDimension(R.dimen.dot_margin);
        final int zero = 0;
        final LayoutParams layoutParams = new LayoutParams(size, size);
        layoutParams.setMargins(margin, zero, margin, zero);

        for (int i = 0; i < total; i++) {
            View view = new View(getContext());
            view.setLayoutParams(layoutParams);
            addView(view);
        }

        animate().alpha(1).setDuration(200).start();

    }



    public void moveTo(int position) {
        int childs = getChildCount();
        if (position < 0 || position > childs) return;
        for (int i = 0; i < childs; i++) {
            int color = (i == position) ? R.drawable.bg_dot_selected : R.drawable.bg_dot_default;
            getChildAt(i).setBackgroundResource(color);
        }
    }


}
