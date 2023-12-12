package com.example.expensemate.util;

import android.text.InputFilter;
import android.text.Spanned;
import android.widget.EditText;

public class DecimalFilter implements InputFilter {

    // SOURCE START: https://stackoverflow.com/questions/354044/what-is-the-best-u-s-currency-regex
    // Regex that only allows 0-9, ., and - and after . only allows 2 digits
    private final String regex = "^[+-]{0,1}([0-9]+(?:,?[0-9]{3})*(\\.[0-9]{0,2})?)?$";
    // SOURCE END: https://stackoverflow.com/questions/354044/what-is-the-best-u-s-currency-regex
    private final EditText editTextAmount;

    public DecimalFilter(EditText editTextAmount) {
        this.editTextAmount = editTextAmount;
    }

    @Override
    public CharSequence filter(CharSequence charSequence, int i, int i1, Spanned spanned, int i2, int i3) {
        String text = editTextAmount.getText().toString() + charSequence.toString();

        if (text.matches(regex)) {
            return null;
        }

        return "";
    }
}
