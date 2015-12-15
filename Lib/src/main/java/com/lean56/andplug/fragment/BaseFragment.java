package com.lean56.andplug.fragment;

import android.os.Bundle;
import android.view.*;
import com.lean56.andplug.activity.BaseActivity;
import com.umeng.analytics.MobclickAgent;

/**
 * Base Fragment offers
 * umeng analytics
 * home optional selected
 *
 * @author Charles
 */
public abstract class BaseFragment extends android.support.v4.app.Fragment {

    /**
     * Is this fragment usable from the UI-thread
     *
     * @return true if usable, false otherwise
     */
    protected boolean isUsable() {
        return getActivity() != null;
    }

    /**
     * Get content view to be used when {@link #onCreate(Bundle)} is called
     *
     * @return layout resource id
     */
    protected abstract int getContentView();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(getContentView(), container, false);
    }

    // [+] title
    private String mTitle;

    public void setTitle(String title) {
        this.mTitle = title;
        getActivity().setTitle(title);
    }

    public String getTitle() {
        return mTitle;
    }

    private String mSubTitle;

    public void setSubTitle(String subTitle) {
        this.mSubTitle = subTitle;
        ((BaseActivity)getActivity()).setSubTitle(subTitle);
    }

    public String getSubTitle() {
        return mSubTitle;
    }
    // [-] title

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
