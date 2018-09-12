package com.mationate.petproject.partials;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.mationate.petproject.R;

public class TextInputField extends android.support.v7.widget.AppCompatEditText implements TextWatcher {

    private FieldCallback callback;
    private int min;

    public TextInputField(Context context) {
        super(context);
        setAppearance(context);
    }

    private void setAppearance(Context context) {
        setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        int top = (int) context.getResources().getDimension(R.dimen.input_field_padding_top);
        int sides = (int) context.getResources().getDimension(R.dimen.input_field_padding_sides);
        setPadding(sides, top, sides, top);
        setBackgroundResource(R.drawable.bg_rounded_outline_primary);

    }

    public void setCallback(FieldCallback callback) {
        this.callback = callback;
    }


    public void setValidation(final int min) {
        this.min = min;
        addTextChangedListener(this);
    }

    public boolean isValid() {
        return getText().toString().trim().length() >= min;
    }


    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        String text = s.toString();
        if (text.trim().length() > min) {
            callback.completed();
        } else {
            callback.uncompleted("Introduzca màs de " + min + " caractéres");
        }
    }
}
    
    
    
    

