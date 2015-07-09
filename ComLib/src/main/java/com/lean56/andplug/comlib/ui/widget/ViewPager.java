package com.lean56.andplug.comlib.ui.widget;

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebView;

/**
 * {@link ViewPager} extension with support for horizontally scrolling an
 * embedded {@link WebView}
 *
 * @author Charles <zhangchaoxu@gmail.com>
 */
public class ViewPager extends android.support.v4.view.ViewPager {

    /**
     * if the pager scrollable
     */
    private boolean scrollable = true;

    /**
     * @param context
     */
    public ViewPager(final Context context) {
        super(context);
    }

    /**
     * @param context
     * @param attrs
     */
    public ViewPager(final Context context, final AttributeSet attrs) {
        super(context, attrs);
    }

    // [+] scrollable related

    /**
     * set the viewpager scrollable
     *
     * @param scroll
     */
    public void setScrollable(boolean scroll) {
        this.scrollable = scroll;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return scrollable ? super.onInterceptTouchEvent(ev) : false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return scrollable ? super.onTouchEvent(ev) : false;
    }
    // [-] scrollable related

    /**
     * Set current item and return whether the item changed
     * <p>
     * This method does not call {@link #setCurrentItem(int)} unless the item
     * parameter differs from the current item
     *
     * @param item
     * @return true if set, false if same
     */
    public boolean setItem(final int item) {
        final boolean changed = item != getCurrentItem();
        if (changed)
            setCurrentItem(item, false);
        return changed;
    }

    /**
     * Set current item, invoke the listener if changes, and return whether the
     * item changed
     * <p>
     * This method does not call {@link #setCurrentItem(int)} unless the item
     * parameter differs from the current item
     *
     * @param item
     * @param listener
     * @return true if set, false if same
     */
    public boolean setItem(final int item, final OnPageChangeListener listener) {
        final boolean changed = setItem(item);
        if (changed && listener != null)
            listener.onPageSelected(item);
        return changed;
    }

    /**
     * Schedule a call to {@link #setItem(int)} to occur on the UI-thread
     *
     * @param item
     * @param listener
     */
    public void scheduleSetItem(final int item,
                                final OnPageChangeListener listener) {
        post(new Runnable() {

            @Override
            public void run() {
                setItem(item, listener);
            }
        });
    }

    /**
     * Schedule a call to {@link #setItem(int)} to occur on the UI-thread
     *
     * @param item
     */
    public void scheduleSetItem(final int item) {
        post(new Runnable() {

            @Override
            public void run() {
                setItem(item);
            }
        });
    }

    @Override
    protected boolean canScroll(final View v, final boolean checkV, final int dx, final int x, final int y) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.ICE_CREAM_SANDWICH && v instanceof WebView)
            return v.canScrollHorizontally(-dx);
        else
            return super.canScroll(v, checkV, dx, x, y);
    }
}
