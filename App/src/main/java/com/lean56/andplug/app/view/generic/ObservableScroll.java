package com.lean56.andplug.app.view.generic;

/**
 * ObservableScroll
 *
 * @author liwuhuo <www.liwushuo.com>
 * @author Charles
 */
public interface ObservableScroll {

    int getContentHeight();

    ObservableScroll.OnScrollChangedListener getOnScrollChangedListener();

    void setOnContentChangedListener(OnContentChangedListener p1);

    void setOnScrollChangedListener(ObservableScroll.OnScrollChangedListener p1);

    interface OnScrollChangedListener {
        void onScrollChanged(int l, int t);
    }

}
