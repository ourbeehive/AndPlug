package com.lean56.andplug.utils;

import android.content.Context;
import android.util.TypedValue;
import com.lean56.andplug.BaseApplication;

/**
 * Display helper for display unit transform
 *
 * @auther Charles
 */
public class DisplayHelper {

    /**
     * dp to px
     */
    public static int dpToPx(int dpValue) {
        return (int) (dpValue * BaseApplication.sDensity + 0.5f);
    }


    private static float convertUnitToPixel(Context ctx, int unit, float value) {
        return TypedValue.applyDimension(unit, value, ctx.getResources().getDisplayMetrics());
    }
}

