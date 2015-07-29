package com.lean56.andplug.comlib.activity;

import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.MenuItem;
import com.lean56.andplug.comlib.R;
import com.readystatesoftware.systembartint.SystemBarTintManager;
import com.umeng.analytics.MobclickAgent;

/**
 * Base Activity of all activities of Application
 *
 * @author Charles
 */
public abstract class BaseActivity extends AppCompatActivity {

    private final static String TAG = BaseActivity.class.getSimpleName();

    private boolean statusBarTranslucent = false;
    private boolean showHomeAsUp = true;
    protected Toolbar toolbar;

    private int primaryColor;
    private int primaryDarkColor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentView());
        Log.d(TAG, getClass().getSimpleName() + ".onCreate...");

        Resources.Theme theme = this.getTheme();
        TypedValue typedValue = new TypedValue();
        // get primary color
        theme.resolveAttribute(R.attr.colorPrimary, typedValue, true);
        primaryColor = typedValue.data;
        theme.resolveAttribute(R.attr.colorPrimaryDark, typedValue, true);
        primaryDarkColor = typedValue.data;
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            TypedArray windowTranslucentAttribute = theme.obtainStyledAttributes(new int[]{android.R.attr.windowTranslucentStatus});
            statusBarTranslucent = windowTranslucentAttribute.getBoolean(0, false);
        }

        initActionBar(isShowHomeAsUp());
    }

    /**
     * Get content view to be used when {@link #onCreate(Bundle)} is called
     *
     * @return layout resource id
     */
    protected abstract int getContentView();

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case (android.R.id.home):
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    // [+] actionbar
    public Toolbar getToolbar() {
        return toolbar;
    }

    /**
     * initActionBar
     */
    protected final void initActionBar(boolean showHomeAsUp) {
        // set Toolbar as actionbar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (null != toolbar) {
            setSupportActionBar(toolbar);
            ActionBar actionBar = getSupportActionBar();
            if (null != actionBar) {
                // actionBar.setHomeAsUpIndicator(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
                actionBar.setDisplayHomeAsUpEnabled(showHomeAsUp);
            }
        }

        // Apply background tinting to the Android system UI when using KitKat translucent modes.
        // see {https://github.com/jgilfelt/SystemBarTint}
        if (isTranslucentStatusBar() && Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setStatusBarTint(darkenColor(primaryColor));
        }
    }

    protected boolean isTranslucentStatusBar() {
        return statusBarTranslucent;
    }

    protected boolean isShowHomeAsUp() {
        return showHomeAsUp;
    }

    @Override
    public void setTitle(CharSequence title) {
        super.setTitle(title);
        if (null != toolbar) {
            toolbar.setTitle(title);
        }
    }
    // [-] actionbar

    // [+]translucent system bar

    /**
     * darken color
     */
    protected int darkenColor(int color) {
        if (color == primaryColor) {
            return primaryDarkColor;
        } else {
            float[] hsv = new float[3];
            Color.colorToHSV(color, hsv);
            hsv[2] *= 0.8f; // value component
            return Color.HSVToColor(hsv);
        }
    }

    /**
     * change toolbar color
     */
    public void setToolbarColor(int primaryColor, int primaryDarkColor) {
        // set toolbar color
        if (null != toolbar) {
            toolbar.setBackgroundColor(primaryColor);
        }

        // set StatusBar color
        if (isTranslucentStatusBar() && Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setStatusBarTint(primaryDarkColor);
        }
    }

    /**
     * set the statusBar tint
     */
    protected void setStatusBarTint(int primaryDarkColor) {
        SystemBarTintManager tintManager = new SystemBarTintManager(this);
        tintManager.setStatusBarTintEnabled(true);
        tintManager.setStatusBarTintColor(primaryDarkColor);
    }

    /**
     * set the navigationBar tint
     */
    protected void setNavigationBarTint(int resId) {
        SystemBarTintManager tintManager = new SystemBarTintManager(this);
        tintManager.setNavigationBarTintEnabled(true);
        tintManager.setNavigationBarTintResource(resId);
    }
    // [-]translucent system bar

    // [+] umeng analytics
    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }
    // [-] umeng analytics
}
