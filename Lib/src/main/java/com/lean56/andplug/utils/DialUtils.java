package com.lean56.andplug.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;

/**
 * DialUtils
 *
 * @author Charles
 */
public class DialUtils {

    public static void callPhone(Context context, String telPhoneNumber) {
        if (TextUtils.isEmpty(telPhoneNumber)) {
            return;
        }
        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + telPhoneNumber));
        context.startActivity(intent);
    }
}
