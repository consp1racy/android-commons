package net.xpece.android.text;

import android.text.Editable;
import android.text.TextWatcher;

/**
 * Created by Eugen on 26.11.2016.
 */

public class EmptyTextWatcher implements TextWatcher {
    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
    }

    @Override
    public void afterTextChanged(Editable editable) {
    }
}
