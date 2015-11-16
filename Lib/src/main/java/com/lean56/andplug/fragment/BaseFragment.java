package com.lean56.andplug.fragment;

import android.view.Menu;
import android.view.MenuInflater;
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

    // [+] Options Menu
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        addMenuItem(menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (!isUsable())
            return false;

        switch (item.getItemId()) {
            case (android.R.id.home):
                getActivity().onBackPressed();
                return true;
            case Menu.FIRST:
                return onFirstMenuSelected(item);
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * add single menu item
     * @param menu
     * @return
     */
    protected void addMenuItem(Menu menu) {
        // add menu like this
        // menu.add(Menu.NONE, Menu.FIRST, Menu.NONE, R.string.register).setIcon(R.drawable.ic_action_add).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
    }

    /**
     * on first menu selected/click event
     * @param item
     * @return
     */
    protected boolean onFirstMenuSelected(MenuItem item) {
        return false;
    }

    // [-] Options Menu

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
