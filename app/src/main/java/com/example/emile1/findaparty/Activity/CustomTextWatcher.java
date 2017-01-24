package com.example.emile1.findaparty.Activity;

import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import com.example.emile1.findaparty.R;

/**
 * Created by Emile1 on 22/01/2017.
 */

public class CustomTextWatcher implements TextWatcher {

    public TextInputLayout til;
    public CustomTextWatcher(TextInputLayout _til) {
        this.til= _til;
    }

    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

    public void afterTextChanged(Editable editable) {
        til.setErrorEnabled(false);
        til.setError(null);
    }
}
