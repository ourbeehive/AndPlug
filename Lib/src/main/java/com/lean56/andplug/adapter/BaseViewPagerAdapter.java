package com.lean56.andplug.adapter;

import android.support.v4.view.PagerAdapter;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * BaseViewPagerAdapter
 * see {http://blog.csdn.net/maosidiaoxian/article/details/38661589}
 *
 * @author msdx
 * @author Charles
 */
public abstract class BaseViewPagerAdapter <T> extends PagerAdapter {

    protected List<T> mData;
    private SparseArray<View> mViews;

    public BaseViewPagerAdapter(List<T> data) {
        mData = data;
        mViews = new SparseArray<>(data.size());
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = mViews.get(position);
        if (view == null) {
            view = newView(position);
            mViews.put(position, view);
        }
        container.addView(view);
        return view;
    }

    protected abstract View newView(int position);

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(mViews.get(position));
    }

    public T getItem(int position) {
        return mData.get(position);
    }
}