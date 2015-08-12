package com.lean56.andplug.app.activity;

import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import com.lean56.andplug.activity.BaseActivity;
import com.lean56.andplug.app.AppContext;
import com.lean56.andplug.app.R;
import com.lean56.andplug.universalimage.loader.ImageLoadUtils;

/**
 * Splash Activity is the loading Screen
 *
 * @author Charles
 */
public class SplashActivity extends BaseActivity {

    private final static String TAG = SplashActivity.class.getSimpleName();

    // UI references.
    private ImageView mSplashImage;

    private ImageLoadUtils imageLoadUtils = new ImageLoadUtils();

    @Override
    protected int getContentView() {
        return R.layout.splash;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mSplashImage = (ImageView) findViewById(R.id.iv_splash);
        imageLoadUtils.imageLoader.clearMemoryCache();
        // imageLoadUtils.imageLoader.loadImage(mSplashImage, );

        Animation splashAnim = AnimationUtils.loadAnimation(this, R.anim.splash);
        splashAnim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {}

            @Override
            public void onAnimationEnd(Animation animation) {
                checkAccount();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {}
        });
        mSplashImage.startAnimation(splashAnim);
    }

    /**
     * check if account login and start next activity
     */
    private void checkAccount() {
        Class<?> targetCls = AppContext.getInstance().isAccountLogin() ? HomeActivity.class : LoginActivity.class;
        /*Intent intent = new Intent(this, targetCls);
        startActivity(intent);
        finish();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);*/
    }

    /*@Override
    protected boolean isTranslucentStatusBar() {
        return false;
    }*/

}
