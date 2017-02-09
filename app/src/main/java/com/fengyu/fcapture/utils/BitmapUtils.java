package com.fengyu.fcapture.utils;

import android.content.Context;
import android.graphics.Bitmap;

/**
 */
public class BitmapUtils {
    Context mContext;

    BitmapUtils(Context context) {
        this.mContext = context;
    }

    public Bitmap createSquareBitmap(Bitmap bitmap) {
        int x = 0;
        int y = 0;
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        if (width > height) {
            x = (width - height) / 2;
            width = height;
        } else {
            y = (height - width) / 2;
            height = width;
        }
        return Bitmap.createBitmap(bitmap, x, y, width, height, null, true);
    }
}
