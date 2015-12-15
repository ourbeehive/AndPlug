package com.lean56.andplug.image;

import android.graphics.Color;
import android.text.TextUtils;
import com.lean56.andplug.image.view.TextDrawable;

/**
 * TextDrawableUtils
 *
 * @author Charles
 */
public class TextDrawableUtils {

    public enum SHAPE {
        RECT, ROUND, ROUND_RECT
    }

    private static int DEFAULT_BG_COLOR = Color.LTGRAY;

    public static TextDrawable genTextDrawable(String str) {
        return genTextDrawable(str, DEFAULT_BG_COLOR, SHAPE.RECT);
    }

    public static TextDrawable genTextDrawable(String str, SHAPE shape) {
        return genTextDrawable(str, DEFAULT_BG_COLOR, shape);
    }

    public static TextDrawable genTextDrawable(String str, int color) {
        return genTextDrawable(str, color, SHAPE.RECT);
    }

    public static TextDrawable genTextDrawable(String str, int color, SHAPE shape) {
        String text = TextUtils.isEmpty(str) ? "" : str.substring(0, 1);
        if (shape == SHAPE.ROUND) {
            return TextDrawable.builder().buildRound(text, color);
        } else if (shape == SHAPE.ROUND_RECT){
            return TextDrawable.builder().buildRoundRect(text, color, 8);
        } else {
            return TextDrawable.builder().buildRect(text, color);
        }
    }
}
