package com.lean56.andplug.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.ViewGroup;
import com.lean56.andplug.fragment.FragmentProvider;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * Pager adapter that provides the current fragment
 *
 * @author Charles <zhangchaoxu@gmail.com>
 */
public abstract class FragmentPagerAdapter extends android.support.v4.app.FragmentPagerAdapter implements FragmentProvider {

    private final FragmentActivity activity;

    private Fragment selected;

    /**
     * @param activity
     */
    public FragmentPagerAdapter(FragmentActivity activity) {
        super(activity.getSupportFragmentManager());

        this.activity = activity;
    }

    /**
     * @param fragment
     */
    public FragmentPagerAdapter(Fragment fragment) {
        super(fragment.getChildFragmentManager());

        this.activity = fragment.getActivity();
    }

    @Override
    public Fragment getSelected() {
        return selected;
    }

    @Override
    public void setPrimaryItem(final ViewGroup container, final int position, final Object object) {
        super.setPrimaryItem(container, position, object);

        boolean changed = false;
        if (object instanceof Fragment) {
            changed = object != selected;
            selected = (Fragment) object;
        } else {
            changed = object != null;
            selected = null;
        }

        if (changed)
            activity.invalidateOptionsMenu();
    }

    // [+] save status
    private List<WeakReference<Fragment>> mList = new ArrayList<>();

    public List<WeakReference<Fragment>> getFragments() {
        for (int i = mList.size() - 1; i >= 0; --i) {
            if (null == mList.get(i).get()) {
                mList.remove(i);
            }
        }
        return mList;
    }

    protected void saveFragment(Fragment fragment) {
        if (fragment == null)
            return;

        for (WeakReference<Fragment> item : mList) {
            if (item.get() == fragment)
                return;
        }

        mList.add(new WeakReference<>(fragment));
    }
    // [-] save status

}

