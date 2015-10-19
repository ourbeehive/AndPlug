package com.lean56.andplug.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import com.lean56.andplug.activity.BaseActivity;
import com.lean56.andplug.app.AppContext;
import com.lean56.andplug.app.R;
import com.lean56.andplug.universalimage.loader.ImageLoadUtils;

/**
 * Splash offers
 * loading screen
 *
 * @author Charles
 */
public class SplashActivity extends BaseActivity {

    private final static String TAG = SplashActivity.class.getSimpleName();

    // UI references.
    private ImageView mSplashBgImage;
    private ImageView mSplashLogoImage;
    private ImageLoadUtils imageLoadUtils = new ImageLoadUtils();

    @Override
    protected int getContentView() {
        return R.layout.splash;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initViews();
    }

    private void initViews() {
        mSplashBgImage = (ImageView) findViewById(R.id.iv_splash_bg);
        mSplashLogoImage = (ImageView) findViewById(R.id.iv_splash_logo);

        // TODO load splash image from network
        // imageLoadUtils.imageLoader.clearMemoryCache();
        // imageLoadUtils.imageLoader.loadImage(mSplashImage, );

        Animation splashAnim = AnimationUtils.loadAnimation(this, R.anim.splash);
        splashAnim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                checkAccount();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
        mSplashBgImage.startAnimation(splashAnim);
    }

    /**
     * check if account login_bg and start next activity
     */
    private void checkAccount() {
        Class<?> targetCls = AppContext.getInstance().isAccountLogin() ? HomeActivity.class : LoginChooseActivity.class;
        Intent intent = new Intent(this, targetCls);
        startActivity(intent);
        finish();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

}
