package com.lean56.andplug.activity;

import android.content.Intent;
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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import com.afollestad.materialdialogs.MaterialDialog;
import com.lean56.andplug.R;
import com.lean56.andplug.utils.AppManager;
import com.lean56.andplug.utils.SystemBarTintManager;
import com.umeng.analytics.MobclickAgent;

import java.io.Serializable;

/**
 * Base Activity of all activities of Application
 *
 * @author Charles
 */
public abstract class BaseActivity extends AppCompatActivity {

    private final static String TAG = BaseActivity.class.getSimpleName();

    // toolbar and theme attr
    private Toolbar mToolbar;
    private boolean statusBarTranslucent = false;
    private boolean navigationTranslucent = false;
    private boolean showHomeAsUp = true;
    private int primaryColor;
    private int primaryDarkColor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        beforeContentView();

        setContentView(getContentView());
        Log.d(TAG, getClass().getSimpleName() + ".onCreate...");

        Resources.Theme theme = this.getTheme();
        TypedValue typedValue = new TypedValue();
        // get primary color
        theme.resolveAttribute(R.attr.colorPrimary, typedValue, true);
        primaryColor = typedValue.data;
        theme.resolveAttribute(R.attr.colorPrimaryDark, typedValue, true);
        primaryDarkColor = typedValue.data;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            TypedArray windowTranslucentAttribute = theme.obtainStyledAttributes(new int[]{android.R.attr.windowTranslucentStatus, android.R.attr.windowTranslucentNavigation});
            statusBarTranslucent = windowTranslucentAttribute.getBoolean(0, false);
            navigationTranslucent = windowTranslucentAttribute.getBoolean(1, false);
        }

        initActionBar(isShowHomeAsUp());

        // add the activity into the stack
        AppManager.getAppManager().addActivity(this);
        MobclickAgent.openActivityDurationTrack(false);
    }

    /**
     * Get content view to be used when {@link #onCreate(Bundle)} is called
     *
     * @return layout resource id
     */
    protected abstract int getContentView();

    /**
     * init before content view
     */
    protected void beforeContentView() {
        // empty
    }

    // [+] actionbar
    public Toolbar getToolbar() {
        return mToolbar;
    }

    protected void hideToolbar(boolean hidden) {
        if (null != mToolbar) {
            if (hidden && mToolbar.isShown()) {
                mToolbar.setVisibility(View.GONE);
            } else if (!hidden && !mToolbar.isShown()) {
                mToolbar.setVisibility(View.VISIBLE);
            }
        }
    }

    /**
     * initActionBar
     */
    protected final void initActionBar(boolean showHomeAsUp) {
        // set Toolbar as actionbar
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        if (null == mToolbar)
            return;

        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (null != actionBar) {
            actionBar.setDisplayHomeAsUpEnabled(showHomeAsUp);
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

    // [-] actionbar

    // [+] Options Menu

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            case Menu.FIRST:
                return onFirstMenuSelected(item);
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        addMenuItem(menu);
        return super.onCreateOptionsMenu(menu);
    }

    /**
     * add single menu item
     * @param menu
     * @return
     */
    protected void addMenuItem(Menu menu) {
        // add menu like this
        // menu.add(Menu.NONE, Menu.FIRST, Menu.NONE, R.string.register).setIcon(R.drawable.ic_action_add).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
    }

    /**
     * on first menu selected/click event
     * @param item
     * @return
     */
    protected boolean onFirstMenuSelected(MenuItem item) {
        return false;
    }

    // [-] Options Menu

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
        if (null != mToolbar) {
            mToolbar.setBackgroundColor(primaryColor);
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
        tintManager.setNavigationBarTintColor(resId);
    }
    // [-]translucent system bar

    // [+] Progress Dialog
    /**
     * Shows the progress UI and hides the login_bg form.
     */
    private MaterialDialog mProgressDialog;

    protected void hiddenProgress() {
        if (null != mProgressDialog && mProgressDialog.isShowing())
            mProgressDialog.dismiss();
    }

    protected void showProgress(int contentResId) {
        showProgress(getString(contentResId));
    }

    protected void showProgress(String content) {
        if (null == mProgressDialog) {
            mProgressDialog = new MaterialDialog.Builder(this)
                    .content(content)
                    .progress(true, 0)
                    .show();
        } else {
            mProgressDialog.setContent(content);
            mProgressDialog.show();
        }
    }
    // [-] Progress Dialog

    // [+] start activity

    protected void goActivity(Intent intent) {
        goActivity(false, intent);
    }

    protected void goActivity(boolean isFinish, Intent intent) {
        startActivity(intent);
        if (isFinish) {
            finish();
        }
    }
    // [-] start activity

    // [+] Intent extra

    /**
     * Get intent extra
     *
     * @param name
     * @return serializable
     */
    @SuppressWarnings("unchecked")
    protected <V extends Serializable> V getSerializableExtra(final String name) {
        return (V) getIntent().getSerializableExtra(name);
    }

    /**
     * Get intent extra
     *
     * @param name
     * @return int
     */
    protected int getIntExtra(final String name) {
        return getIntent().getIntExtra(name, -1);
    }

    /**
     * Get intent extra
     *
     * @param name
     * @return long
     */
    protected long getLongExtra(final String name) {
        return getIntent().getLongExtra(name, -1l);
    }

    /**
     * Get intent extra
     *
     * @param name
     * @return int array
     */
    protected int[] getIntArrayExtra(final String name) {
        return getIntent().getIntArrayExtra(name);
    }

    /**
     * Get intent extra
     *
     * @param name
     * @return boolean
     */
    protected boolean getBooleanExtra(final String name) {
        return getIntent().getBooleanExtra(name, false);
    }

    /**
     * Get intent extra
     *
     * @param name
     * @return boolean array
     */
    protected boolean[] getBooleanArrayExtra(final String name) {
        return getIntent().getBooleanArrayExtra(name);
    }

    /**
     * Get intent extra
     *
     * @param name
     * @return string
     */
    protected String getStringExtra(final String name) {
        return getIntent().getStringExtra(name);
    }

    /**
     * Get intent extra
     *
     * @param name
     * @return string array
     */
    protected String[] getStringArrayExtra(final String name) {
        return getIntent().getStringArrayExtra(name);
    }

    /**
     * Get intent extra
     *
     * @param name
     * @return char sequence array
     */
    protected CharSequence[] getCharSequenceArrayExtra(final String name) {
        return getIntent().getCharSequenceArrayExtra(name);
    }
    // [-] Intent extra

    // [+] umeng analytics
    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onPageStart(getClass().getSimpleName());
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd(getClass().getSimpleName());
        MobclickAgent.onPause(this);
    }
    // [-] umeng analytics

    @Override
    protected void onDestroy() {
        super.onDestroy();

        // end the Activity & remove it from stack
        AppManager.getAppManager().finishActivity(this);
    }
}
