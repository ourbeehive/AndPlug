package com.lean56.andplug.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import com.lean56.andplug.app.R;
import com.lean56.andplug.comlib.activity.BaseActivity;

/**
 * Splash Activity is the loading UI
 *
 * @author Charles
 */
public class SplashActivity extends BaseActivity {

    private final static String TAG = SplashActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.splash);

        final View controlsView = findViewById(R.id.fullscreen_content_controls);
        final View contentView = findViewById(R.id.fullscreen_content);

        Intent intent = new Intent();
        intent.setClass(this, HomeActivity.class);
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        finish();
    }

    @Override
    protected int getColorPrimaryDark() {
        return android.R.color.widget_edittext_dark;
    }
}