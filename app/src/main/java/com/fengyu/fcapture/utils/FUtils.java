package com.fengyu.fcapture.utils;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import static android.content.Context.WINDOW_SERVICE;

/**
 */
public class FUtils {

    public static DisplayMetrics getDisplayMetrics(Context mContext) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        WindowManager wm = (WindowManager) mContext.getSystemService(WINDOW_SERVICE);
        wm.getDefaultDisplay().getRealMetrics(displayMetrics);
        return displayMetrics;
    }
}
