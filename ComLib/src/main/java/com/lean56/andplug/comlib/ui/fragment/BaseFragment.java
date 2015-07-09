package com.lean56.andplug.comlib.ui.fragment;

import android.support.v4.app.Fragment;

/**
 * Base Fragment
 *
 * @author Charles <zhangchaoxu@gmail.com>
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

}
