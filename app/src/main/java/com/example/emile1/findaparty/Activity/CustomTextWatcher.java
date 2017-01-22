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

    public View view;
    public TextInputLayout til;
    public CustomTextWatcher(View view,TextInputLayout _til) {
        this.view = view;
        this.til= _til;
    }

    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

    public void afterTextChanged(Editable editable) {
        switch(view.getId()){
            case R.id.etFirst_name:
                til.setErrorEnabled(false);
                til.setError(null);
                break;
            case R.id.etLast_name:
                til.setErrorEnabled(false);
                til.setError(null);
                break;
            case R.id.etEmail_register:
                til.setErrorEnabled(false);
                til.setError(null);
                break;
            case R.id.etPassword:
                til.setErrorEnabled(false);
                til.setError(null);
                break;
            case R.id.etPasswordAgain:
                til.setErrorEnabled(false);
                til.setError(null);
                break;
        }
    }
}
