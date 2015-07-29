package com.lean56.andplug.app.view;

import android.content.Context;
import android.util.AttributeSet;
import android.webkit.WebView;
import com.lean56.andplug.app.view.generic.MutableScroll;
import com.lean56.andplug.app.view.generic.ObservableScroll;
import com.lean56.andplug.app.view.generic.OnContentChangedListener;

/**
 * ObservableWebView
 *
 * @author liwuhuo <www.liwushuo.com>
 * @author Charles
 */
public class ObservableWebView extends WebView implements ObservableScroll, MutableScroll {

    private OnContentChangedListener mOnContentChangedListener;
    private ObservableScroll.OnScrollChangedListener mOnScrollChangedListener;

    public ObservableWebView(Context context) {
        super(context);
    }

    public ObservableWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ObservableWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    // [+] WebView
    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if(mOnScrollChangedListener != null) {
            mOnScrollChangedListener.onScrollChanged(l, t);
        }
    }
    // [-] WebView

    // [+] ObservableScroll
    @Override
    public void setOnContentChangedListener(OnContentChangedListener listener) {
        mOnContentChangedListener = listener;
    }

    @Override
    public ObservableScroll.OnScrollChangedListener getOnScrollChangedListener() {
        return mOnScrollChangedListener;
    }

    @Override
    public void setOnScrollChangedListener(ObservableScroll.OnScrollChangedListener listener) {
        mOnScrollChangedListener = listener;
    }
    // [-] ObservableScroll

    // [-] MutableScroll
    @Override
    public int getScrollTop() {
        return getScrollY();
    }

    @Override
    public void setScrollTop(int y) {
        scrollTo(getScrollX(), y);
    }
    // [-] MutableScroll
}
