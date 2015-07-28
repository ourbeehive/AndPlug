package com.lean56.andplug.comlib.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.ViewGroup;
import com.lean56.andplug.comlib.fragment.FragmentProvider;

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
    public void setPrimaryItem(final ViewGroup container, final int position,
                               final Object object) {
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

}

