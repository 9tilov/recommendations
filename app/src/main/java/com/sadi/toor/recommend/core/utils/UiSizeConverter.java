package com.sadi.toor.recommend.core.utils;

import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;

public class UiSizeConverter {

    private UiSizeConverter() {
        throw new IllegalStateException("This is Utility class");
    }

    public static int dpToPx(Context context, float dp) {
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        return Math.round(dp * ((float) metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT));
    }

    public static int pxToDp(Context context, float px) {
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        return Math.round(px / ((float) metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT));
    }

    public static int pxToSp(Context context, float px) {
        float scaledDensity = context.getResources().getDisplayMetrics().scaledDensity;
        return Math.round(px / scaledDensity);
    }
}
