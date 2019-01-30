package com.ingic.lmslawyer.ui.views;

import android.content.Context;
import android.util.AttributeSet;

import com.andreabaccega.widget.FormEditText;

public class AnyEditTextView extends FormEditText {

    public AnyEditTextView(Context context) {
        super(context);

    }

    public AnyEditTextView(Context context, AttributeSet attrs) {
        super(context, attrs);

        if (!this.isInEditMode()) {
            Util.setTypeface(attrs, this);
        }
    }

    public AnyEditTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        if (!this.isInEditMode()) {
            Util.setTypeface(attrs, this);
        }

    }

}
