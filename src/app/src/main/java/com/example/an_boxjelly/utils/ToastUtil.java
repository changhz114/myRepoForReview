package com.example.an_boxjelly.utils;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.StringRes;

public class ToastUtil {
    public static void showToast(Context context, @StringRes int messageId){
        Toast.makeText(context, context.getString(messageId), Toast.LENGTH_SHORT).show();
    }

    public static void showToast(Context context, String message){
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
}
