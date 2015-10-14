package com.lean56.andplug.fragment;

import android.view.MenuItem;
import com.umeng.analytics.MobclickAgent;

/**
 * Base Fragment offers
 * umeng analytics
 * home optional selected
 *
 * @author Charles
 */
public class BaseFragment extends android.support.v4.app.Fragment {

    /**
     * Is this fragment usable from the UI-thread
     *
     * @return true if usable, false otherwise
     */
    protected boolean isUsable() {
        return getActivity() != null;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (!isUsable())
            return false;

        switch (item.getItemId()) {
            case (android.R.id.home):
                getActivity().onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    // [+] umeng analytics
    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart(getClass().getSimpleName());
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd(getClass().getSimpleName());
    }
    // [-] umeng analytics

}
