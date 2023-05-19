package com.example.an_boxjelly.utils;

import android.widget.EditText;

public class ConvertToFloatUtil {
    public static float convertToFloat(EditText editText) {
        return Float.parseFloat(editText.getText().toString());
    }
}
