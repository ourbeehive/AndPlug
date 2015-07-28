package com.lean56.andplug.comlib.activity;

import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabHost.TabContentFactory;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;
import com.lean56.andplug.comlib.R;
import com.lean56.andplug.comlib.fragment.FragmentProvider;
import com.lean56.andplug.comlib.widget.ViewPager;
import com.lean56.andplug.comlib.utils.ViewUtils;

/**
 * Activity with tabbed pages
 *
 * @param <V>
 */
public abstract class TabPagerActivity<V extends PagerAdapter & FragmentProvider> extends PagerActivity implements OnTabChangeListener, TabContentFactory {

    private static final int OFFSCREEN_PAGES = 3;

    /**
     * View pager
     */
    protected ViewPager pager;

    /**
     * Tab host
     */
    protected TabHost host;

    /**
     * Pager adapter
     */
    protected V adapter;

    @Override
    public void onPageSelected(final int position) {
        super.onPageSelected(position);

        host.setCurrentTab(position);
    }

    @Override
    public void onTabChanged(String tabId) {
        updateCurrentItem(host.getCurrentTab());
    }

    @Override
    public View createTabContent(String tag) {
        return ViewUtils.setGone(new View(getApplication()), true);
    }

    /**
     * Create pager adapter
     *
     * @return pager adapter
     */
    protected abstract V createAdapter();

    /**
     * Get title for position
     *
     * @param position
     * @return title
     */
    protected String getTitle(final int position) {
        return adapter.getPageTitle(position).toString();
    }

    /**
     * Get icon for position
     *
     * @param position
     * @return iconResId
     */
    protected int getIcon(final int position) {
        return 0;
    }

    /**
     * Set tab and pager as gone or visible
     *
     * @param gone
     * @return this activity
     */
    protected TabPagerActivity<V> setGone(boolean gone) {
        ViewUtils.setGone(host, gone);
        ViewUtils.setGone(pager, gone);
        return this;
    }

    /**
     * Set current item to new position
     * <p>
     * This is guaranteed to only be called when a position changes and the
     * current item of the pager has already been updated to the given position
     * <p>
     * Sub-classes may override this method
     *
     * @param position
     */
    protected void setCurrentItem(final int position) {
        // Intentionally left blank
    }

    /**
     * Get content view to be used when {@link #onCreate(Bundle)} is called
     *
     * @return layout resource id
     */
    protected int getContentView() {
        return R.layout.pager_with_tabs;
    }

    private void updateCurrentItem(final int newPosition) {
        if (newPosition > -1 && newPosition < adapter.getCount()) {
            pager.setItem(newPosition);
            setCurrentItem(newPosition);
        }
    }

    private void createPager() {
        adapter = createAdapter();
        invalidateOptionsMenu();
        pager.setAdapter(adapter);
    }

    /**
     * Create tab using information from current adapter
     * <p>
     * This can be called when the tabs changed but must be called after an
     * initial call to {@link #configureTabPager()}
     */
    protected void createTabs() {
        if (host.getTabWidget().getTabCount() > 0) {
            // Crash on Gingerbread if tab isn't set to zero since adding a
            // new tab removes selection state on the old tab which will be
            // null unless the current tab index is the same as the first
            // tab index being added
            host.setCurrentTab(0);
            host.clearAllTabs();
        }

        LayoutInflater inflater = getLayoutInflater();
        int count = adapter.getCount();
        for (int i = 0; i < count; i++) {
            TabSpec spec = host.newTabSpec("tab" + i);
            spec.setContent(this);
            View view = inflater.inflate(R.layout.tabwidget, null);
            ImageView icon = (ImageView) view.findViewById(R.id.tab_icon);
            TextView title = (TextView) view.findViewById(R.id.tab_title);
            int iconText = getIcon(i);
            if (0 != iconText) {
                icon.setImageResource(getIcon(i));
            } else {
                ViewUtils.setGone(icon, true);
            }
            title.setText(getTitle(i));
            spec.setIndicator(view);
            host.addTab(spec);
        }
    }

    /**
     * Configure tabs and pager
     */
    protected void configureTabPager() {
        if (adapter == null) {
            createPager();
            createTabs();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(getContentView());

        // setup JazzyViewPager
        pager = (ViewPager) findViewById(R.id.vp_pages);
        pager.setOnPageChangeListener(this);
        // keep all fragment alive in the life cycle
        pager.setOffscreenPageLimit(OFFSCREEN_PAGES);

        //set up TabHost
        host = (TabHost) findViewById(android.R.id.tabhost);
        host.setup();
        host.setOnTabChangedListener(this);
    }

    @Override
    protected FragmentProvider getProvider() {
        return adapter;
    }
}
