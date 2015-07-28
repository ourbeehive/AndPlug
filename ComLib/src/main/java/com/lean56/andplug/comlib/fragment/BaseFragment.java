package com.lean56.andplug.comlib.fragment;

import android.support.v4.app.Fragment;
import android.view.MenuItem;

/**
 * Base Fragment
 *
 * @author Charles
 */
public class BaseFragment extends Fragment {

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



}
