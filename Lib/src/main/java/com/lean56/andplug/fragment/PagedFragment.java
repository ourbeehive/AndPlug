package com.lean56.andplug.fragment;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ProgressBar;
import com.lean56.andplug.R;
import com.lean56.andplug.adapter.LoadMoreAdapter;
import com.lean56.andplug.network.OkHttpCallback;
import com.lean56.andplug.network.ResponsePage;
import com.lean56.andplug.view.ExceptionView;
import com.lean56.andplug.view.recycler.SuperRecyclerView;
import com.squareup.okhttp.Request;

import java.util.ArrayList;
import java.util.List;

/**
 * PagedFragment
 *
 * @author Charles
 */
public abstract class PagedFragment<E> extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener, LoadMoreAdapter.ILoadMoreCallback {

    /**
     * List items provided
     */
    protected List<E> mItems = new ArrayList<>();

    /**
     * Is the list currently shown?
     */
    protected boolean mListShown;

    /**
     * Recycler View
     */
    protected SuperRecyclerView mSuperRecyclerView;

    /**
     * Progress bar
     */
    ProgressBar mProgressBar;

    /**
     * exception view
     */
    ExceptionView mExceptionView;

    SwipeRefreshLayout mSwipeLayout;

    protected int mCurrentPageNo = 1;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (!mItems.isEmpty())
            setListShown(true, false);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.paged, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initArgument();

        // init views
        mProgressBar = (ProgressBar) view.findViewById(R.id.pb_loading);
        mExceptionView = (ExceptionView) view.findViewById(R.id.view_exception);
        mSwipeLayout = (SwipeRefreshLayout) view.findViewById(R.id.ptr_layout);

        mSwipeLayout.setColorSchemeResources(com.lean56.andplug.R.color.swipe_refresh_start, com.lean56.andplug.R.color.swipe_refresh_end);
        mSuperRecyclerView = (SuperRecyclerView) view.findViewById(R.id.srv_paged);
        mSuperRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mSuperRecyclerView.setProgressAdapter(createAdapter(mItems));
        mSuperRecyclerView.setRefreshListener(this);
        getListAdapter().setLoadMoreCallback(this);

        // init and send network
        initHttpCallback();
        sendHttpRequest();
    }

    /**
     * Create adapter to display items
     *
     * @param items
     * @return adapter
     */
    protected abstract RecyclerView.Adapter createAdapter(final List<E> items);

    /**
     * Set the list to be shown
     */
    protected void showList() {
        setListShown(true, isResumed());
    }

    /**
     * Get list adapter
     *
     * @return list adapter
     */
    protected LoadMoreAdapter getListAdapter() {
        if (mSuperRecyclerView != null) {
            return (LoadMoreAdapter) mSuperRecyclerView.getAdapter();
        } else
            return null;
    }

    /**
     * Set list shown or progress bar show
     *
     * @param shown
     * @param animate
     * @return this fragment
     */
    public PagedFragment<E> setListShown(final boolean shown, final boolean animate) {
        if (!isUsable())
            return this;

        if (shown == mListShown) {
            if (shown)
                // List has already been shown so hide/show the empty view with no fade effect
                if (mItems.isEmpty())
                    hide(mSuperRecyclerView).show(mExceptionView);
                else
                    hide(mExceptionView).show(mSuperRecyclerView);
            return this;
        }

        mListShown = shown;

        if (shown)
            if (!mItems.isEmpty())
                hide(mProgressBar).hide(mExceptionView).fadeIn(mSuperRecyclerView, animate).show(mSuperRecyclerView);
            else
                hide(mProgressBar).hide(mSuperRecyclerView).fadeIn(mExceptionView, animate).show(mExceptionView);
        else
            hide(mSuperRecyclerView).hide(mExceptionView).fadeIn(mProgressBar, animate).show(mProgressBar);

        return this;
    }

    protected OkHttpCallback mOkHttpCallback;

    protected abstract void sendHttpRequest();

    protected abstract void initHttpCallback();

    protected void initArgument() {

    }

    protected void onResponseError(Request request, Exception e) {

    }

    protected void onResponseSuccess(List<E> items, ResponsePage page) {
        if (null == page)
            return;

        if (page.getPageNo() == 1) {
            mItems = items;
            getListAdapter().replaceAll(mItems);
            showList();
        } else {
            mItems.addAll(items);
            getListAdapter().addAll(items);
        }

        if (page.hasNextPage()) {
            mCurrentPageNo = page.getPageNo() + 1;
            getListAdapter().setLoadingCompleted(false);
        } else {
            getListAdapter().setLoadingCompleted(true);
        }
    }

    public void refresh() {
        mCurrentPageNo = 1;
        sendHttpRequest();
    }

    // [+] LoadMoreListener
    @Override
    public void onRefresh() {
        mCurrentPageNo = 1;
        sendHttpRequest();
    }

    @Override
    public void loadMore(int position) {
        sendHttpRequest();
    }
    // [-] LoadMoreListener

    private PagedFragment<E> show(View view) {
        view.setVisibility(View.VISIBLE);
        return this;
    }

    private PagedFragment<E> hide(View view) {
        view.setVisibility(View.GONE);
        return this;
    }

    private PagedFragment<E> fadeIn(View view, boolean animate) {
        if (view != null)
            if (animate)
                view.startAnimation(AnimationUtils.loadAnimation(getActivity(), android.R.anim.fade_in));
            else
                view.clearAnimation();
        return this;
    }

    @Override
    public void onDestroyView() {
        mListShown = false;
        mProgressBar = null;
        mSuperRecyclerView = null;
        mExceptionView = null;
        super.onDestroyView();
    }
}
