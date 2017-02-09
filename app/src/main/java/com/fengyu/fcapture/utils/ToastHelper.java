package com.fengyu.fcapture.utils;

import android.content.Context;
import android.widget.Toast;

/**
 */
public class ToastHelper {

    Toast toast = null;

    ToastHelper() {
    }

    public void show(Context mContext, String text, int time) {
        if (toast == null) {
            toast = Toast.makeText(mContext, text, time);
        } else {
            toast.setText(text);
        }
        toast.show();
    }

    public void show(Context mContext, int resId, int time) {
        String text = mContext.getString(resId);
        if (toast == null) {
            toast = Toast.makeText(mContext, text, time);
        } else {
            toast.setText(text);
        }
        toast.show();
    }
}
