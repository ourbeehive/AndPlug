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

    public static TextDrawable genFirstCharTextDrawable(String str) {
        return genFirstCharTextDrawable(str, Color.LTGRAY);
    }

    public static TextDrawable genFirstCharTextDrawable(String str, int color) {
        String text = "";
        if(!TextUtils.isEmpty(str)) {
            text = str.substring(0, 1);
        }
        return TextDrawable.builder().buildRound(text, color);
    }

}
