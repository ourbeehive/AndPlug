package com.lean56.andplug.fragment;

import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import com.lean56.andplug.R;

/**
 * Base fragment for fragment that can be refreshed
 * Refresh with SwipeRefreshLayout and load data with LoadManager
 *
 * the sub-class layout should have a SwipeRefreshLayout with id=swipe_item
 *
 * @param <D>
 *
 * @author Charles
 */
public abstract class RefreshFragment<D> extends BaseFragment implements LoaderManager.LoaderCallbacks<D> {

    private static final String FORCE_REFRESH = "forceRefresh";

    SwipeRefreshLayout swipeLayout;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // init SwipeRefreshLayout
        swipeLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_item);
        swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                forceRefresh();
            }
        });
        swipeLayout.setColorSchemeResources(R.color.swipe_refresh_start, R.color.swipe_refresh_process1, R.color.swipe_refresh_process2, R.color.swipe_refresh_end);
    }

    @Override
    public void onDestroyView() {
        swipeLayout = null;

        super.onDestroyView();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        getLoaderManager().initLoader(0, null, this);
    }

    /**
     * @param args bundle passed to the loader by the LoaderManager
     * @return true if the bundle indicates a requested forced refresh of theitems
     */
    protected static boolean isForceRefresh(Bundle args) {
        return args != null && args.getBoolean(FORCE_REFRESH, false);
    }

    /**
     * disable Refresh
     */
    protected final void disableRefresh() {
        swipeLayout.setEnabled(false);
    }

    /**
     * set swipeLayout refreshing or not
     * @param refreshing
     */
    protected final void setRefreshing(boolean refreshing) {
        swipeLayout.setRefreshing(refreshing);
    }

    /**
     * @return true if the swipeLayout is refreshing
     */
    protected final boolean isRefreshing() {
        return swipeLayout.isRefreshing();
    }

    /**
     * Force a refresh of the items displayed ignoring any cached items
     */
    protected void forceRefresh() {
        Bundle bundle = new Bundle();
        bundle.putBoolean(FORCE_REFRESH, true);
        refresh(bundle);
    }

    /**
     * Refresh the fragment
     */
    public void refresh() {
        refresh(null);
    }

    private void refresh(final Bundle args) {
        if (!isUsable())
            return;

        getLoaderManager().restartLoader(0, args, this);
    }

    // [+] LoaderManager.LoaderCallbacks
    public void onLoadFinished(Loader<D> loader, D items) {
        if (!isUsable())
            return;

        swipeLayout.setRefreshing(false);
    }

    @Override
    public void onLoaderReset(Loader<D> loader) {
        // Intentionally left blank
    }
    // [-] LoaderManager.LoaderCallbacks

    /**
     * Get error message to display for exception
     *
     * @param exception
     * @return string resource id
     */
    protected abstract int getErrorMessage(Exception exception);

    /**
     * Show exception in a {@link android.widget.Toast}
     *
     * @param e
     * @param defaultMessage
     */
    protected void showError(final Exception e, final int defaultMessage) {
        // ToastUtils.show(getActivity(), e, defaultMessage);
    }

    /**
     * Get exception from loader if it provides one by being a ThrowableLoader
     *
     * @param loader
     * @return exception or null if none provided
     */
    protected Exception getException(final Loader<D> loader) {
        /*if (loader instanceof ThrowableLoader)
            return ((ThrowableLoader<D>) loader).clearException();
        else
            return null;*/
        return null;
    }

}


