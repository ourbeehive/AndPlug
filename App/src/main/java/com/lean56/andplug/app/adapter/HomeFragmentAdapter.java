package com.lean56.andplug.app.adapter;

import android.content.res.Resources;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import com.lean56.andplug.app.R;
import com.lean56.andplug.app.fragment.FeatureFragment;
import com.lean56.andplug.app.fragment.IndexFragment;
import com.lean56.andplug.app.fragment.MsgFragment;
import com.lean56.andplug.app.fragment.ProfileFragment;
import com.lean56.andplug.comlib.adapter.FragmentPagerAdapter;

/**
 * Pager adapter for the main tab
 * include the main components of the app
 */
public class HomeFragmentAdapter extends FragmentPagerAdapter {

    private final Resources resources;

    private final static int[] titleArray = new int[]{R.string.index, R.string.feature, R.string.msg, R.string.profile};
    private final static Fragment[] fragmentArray = new Fragment[] {
            new IndexFragment(),
            new FeatureFragment(),
            new MsgFragment(),
            new ProfileFragment()
    };

    /**
     * Create pager adapter
     *
     * @param activity
     */
    public HomeFragmentAdapter(FragmentActivity activity) {
        super(activity);

        resources = activity.getResources();
    }

    @Override
    public int getCount() {
        return fragmentArray.length;
    }

    @Override
    public Fragment getItem(int position) {
        return position < fragmentArray.length ? fragmentArray[position] : null;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return position < titleArray.length ? resources.getString(titleArray[position]) : null;
    }

}

