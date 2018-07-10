package com.example.riceyrq.personalmoneymanagerment.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.InputStream;

public class BitUtil {
    public static Bitmap getBit(Context context, int resId){
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        options.inPurgeable = true;
        options.inInputShareable = true;
        InputStream inputStream = context.getResources().openRawResource(resId);
        return BitmapFactory.decodeStream(inputStream, null, options);
    }
}