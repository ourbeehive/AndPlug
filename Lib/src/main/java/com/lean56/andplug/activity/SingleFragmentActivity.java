package com.lean56.andplug.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.MenuItem;
import com.lean56.andplug.R;

/**
 * Single Fragment Activity
 * activity with only one fragment
 *
 * @author Charles
 */
public abstract class SingleFragmentActivity extends BaseActivity {

    private boolean menuCreated;

    /**
     * content fragment
     */
    private Fragment mFragment;

    @Override
    protected int getContentView() {
        return R.layout.single_fragment;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // replace content with fragment
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        mFragment = getContentFragment();
        fragmentTransaction.replace(R.id.fragment, mFragment);
        fragmentTransaction.commit();
    }

    /**
     * get the content fragment
     * @return the content single fragment
     */
    protected abstract Fragment getContentFragment();

    // [+] option menu
    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        if (mFragment != null)
            return mFragment.onOptionsItemSelected(item);

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void invalidateOptionsMenu() {
        if (menuCreated)
            super.invalidateOptionsMenu();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (mFragment != null)
            mFragment.onCreateOptionsMenu(menu, getMenuInflater());

        boolean created = super.onCreateOptionsMenu(menu);
        menuCreated = true;
        return created;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if (mFragment != null)
            mFragment.onPrepareOptionsMenu(menu);
        return super.onPrepareOptionsMenu(menu);
    }
    // [-] option menu
}
