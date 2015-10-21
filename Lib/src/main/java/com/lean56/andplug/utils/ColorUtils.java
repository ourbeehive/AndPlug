package com.lean56.andplug.utils;

import android.graphics.Color;

/**
 * ColorUtils
 *
 * @author Charles
 */
public class ColorUtils {

    /**
     * darken color
     */
    public static int darkenColor(int color) {
        float[] hsv = new float[3];
        Color.colorToHSV(color, hsv);
        // value component
        hsv[2] *= 0.8f;
        return Color.HSVToColor(Color.alpha(color), hsv);
    }

}
