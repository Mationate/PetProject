package com.mationate.petproject.partials;

import android.content.Context;

public class TextAreaField extends TextInputField {
    public TextAreaField(Context context) {
        super(context);
        setLines(3);
    }
}
