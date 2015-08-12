package com.lean56.andplug.app.activity;

import android.content.Intent;
import android.os.Bundle;
import com.lean56.andplug.activity.BaseActivity;
import com.lean56.andplug.app.AppContext;
import com.lean56.andplug.app.R;

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

        checkAccount();
    }

    /**
     * check if account login and start next activity
     */
    private void checkAccount() {
        Class<?> targetCls = AppContext.getInstance().isAccountLogin() ? HomeActivity.class : LoginActivity.class;
        Intent intent = new Intent(this, targetCls);
        startActivity(intent);        finish();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    @Override
    protected int getContentView() {
        return R.layout.splash;
    }

    @Override
    protected boolean isTranslucentStatusBar() {
        return false;
    }

}
