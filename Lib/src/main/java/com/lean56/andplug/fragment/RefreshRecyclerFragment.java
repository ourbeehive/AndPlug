package com.lean56.andplug.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ListView;
import android.widget.ProgressBar;
import com.lean56.andplug.R;
import com.lean56.andplug.adapter.BaseRecyclerAdapter;
import com.lean56.andplug.adapter.DividerItemDecoration;
import com.lean56.andplug.utils.ItemClickSupport;
import com.lean56.andplug.view.ExceptionView;

import java.util.ArrayList;
import java.util.List;

/**
 * Base fragment for displaying a list of items that loads with a progress bar
 * visible
 *
 * @param <E>
 */
public abstract class RefreshRecyclerFragment<E> extends RefreshFragment<List<E>> {

    /**
     * List items provided to {@link #onLoadFinished(Loader, List)}
     */
    protected List<E> items = new ArrayList<>();

    /**
     * Recycler View
     */
    RecyclerView mRecyclerView;

    /**
     * Progress bar
     */
    ProgressBar progressBar;

    /**
     * exception view
     */
    ExceptionView exceptionView;

    /**
     * Is the list currently shown?
     */
    protected boolean listShown;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (!items.isEmpty())
            setListShown(true, false);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.refresh_item_recycler, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        setRecyclerViewLayoutManager();
        mRecyclerView.addItemDecoration(getItemDecoration());
        // bind click listener
        final ItemClickSupport itemClick = ItemClickSupport.addTo(mRecyclerView);
        itemClick.setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClick(RecyclerView parent, View child, int position, long id) {
                onListItemClick(parent, child, position, id);
            }
        });

        itemClick.setOnItemLongClickListener(new ItemClickSupport.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(RecyclerView parent, View child, int position, long id) {
                return onListItemLongClick(parent, child, position, id);
            }
        });

        progressBar = (ProgressBar) view.findViewById(R.id.pb_loading);

        exceptionView = (ExceptionView) view.findViewById(R.id.view_exception);

        configureList(getActivity(), getListView());
    }

    /**
     * get LayoutManager of RecyclerView
     * @return
     */
    protected LinearLayoutManager getLayoutManager() {
        return new LinearLayoutManager(getActivity());
    }

    /**
     * get ItemDecoration of RecyclerView
     *
     * @return
     */
    protected RecyclerView.ItemDecoration getItemDecoration() {
        return new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST);
    }

    /**
     * Detach from list view.
     */
    @Override
    public void onDestroyView() {
        listShown = false;
        progressBar = null;
        mRecyclerView = null;
        exceptionView = null;
        super.onDestroyView();
    }

    @Override
    public void onResume() {
        super.onResume();
        // refreshWithProgress();
    }

    /**
     * Configure list after view has been created
     *
     * @param activity
     * @param listView
     */
    protected void configureList(Activity activity, RecyclerView listView) {
        listView.setAdapter(createAdapter());
    }

    public void onLoadFinished(Loader<List<E>> loader, List<E> items) {
        super.onLoadFinished(loader, items);

        Exception exception = getException(loader);
        if (exception != null) {
            showError(exception, getErrorMessage(exception));
            showList();
            return;
        }

        this.items = items;
        getListAdapter().setItems(items); //getWrappedAdapter().setItems(items.toArray());
        showList();
    }

    /**
     * Create adapter to display items
     *
     * @return adapter
     */
    protected RecyclerView.Adapter createAdapter() {
        return createAdapter(items);
        /*BaseAdapter wrapped = createAdapter(items);
        return new HeaderFooterListAdapter<BaseAdapter>(getListView(), wrapped);*/
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
     * Refresh the list with the progress bar showing
     */
    protected void refreshWithProgress() {
        items.clear();
        setListShown(false);
        refresh();
    }

    /**
     * Get {@link ListView}
     *
     * @return listView
     */
    public RecyclerView getListView() {
        return mRecyclerView;
    }

    /**
     * Get list adapter
     *
     * @return list adapter
     */
    protected BaseRecyclerAdapter getListAdapter() {
        if (mRecyclerView != null) {
            return (BaseRecyclerAdapter) mRecyclerView.getAdapter();
        } else
            return null;
    }

    /**
     * Notify the underlying adapter that the data set has changed
     *
     * @return this fragment
     */
    protected RefreshRecyclerFragment<E> notifyDataSetChanged() {
        BaseRecyclerAdapter root = getListAdapter();
        if (root != null) {
            root.notifyDataSetChanged();
            /*RecyclerView.Adapter adapter = root.getWrappedAdapter();
            if (null != adapter) {
                adapter.notifyDataSetChanged();
            }*/
        }
        return this;
    }

    /**
     * Set list adapter to use on list view
     *
     * @param adapter
     * @return this fragment
     */
    protected RefreshRecyclerFragment<E> setListAdapter(final RecyclerView.Adapter adapter) {
        if (mRecyclerView != null)
            mRecyclerView.setAdapter(adapter);
        return this;
    }

    private RefreshRecyclerFragment<E> fadeIn(final View view, final boolean animate) {
        if (view != null)
            if (animate)
                view.startAnimation(AnimationUtils.loadAnimation(getActivity(), android.R.anim.fade_in));
            else
                view.clearAnimation();
        return this;
    }

    private RefreshRecyclerFragment<E> show(final View view) {
        view.setVisibility(View.VISIBLE);
        return this;
    }

    private RefreshRecyclerFragment<E> hide(final View view) {
        view.setVisibility(View.GONE);
        return this;
    }

    /**
     * Set RecyclerView's LayoutManager to the one given
     */
    public void setRecyclerViewLayoutManager() {
        int scrollPosition = 0;

        // If a layout manager has already been set, get current scroll position.
        if (mRecyclerView.getLayoutManager() != null) {
            scrollPosition = ((LinearLayoutManager) mRecyclerView.getLayoutManager()).findFirstCompletelyVisibleItemPosition();
        }

        mRecyclerView.setLayoutManager(getLayoutManager());
        mRecyclerView.scrollToPosition(scrollPosition);
    }

    /**
     * Set list shown or progress bar show
     *
     * @param shown
     * @return this fragment
     */
    public RefreshRecyclerFragment<E> setListShown(final boolean shown) {
        return setListShown(shown, true);
    }

    /**
     * Set list shown or progress bar show
     *
     * @param shown
     * @param animate
     * @return this fragment
     */
    public RefreshRecyclerFragment<E> setListShown(final boolean shown, final boolean animate) {
        if (!isUsable())
            return this;

        if (shown == listShown) {
            if (shown)
                // List has already been shown so hide/show the empty view with no fade effect
                if (items.isEmpty())
                    hide(mRecyclerView).show(exceptionView);
                else
                    hide(exceptionView).show(mRecyclerView);
            return this;
        }

        listShown = shown;

        if (shown)
            if (!items.isEmpty())
                hide(progressBar).hide(exceptionView).fadeIn(mRecyclerView, animate).show(mRecyclerView);
            else
                hide(progressBar).hide(mRecyclerView).fadeIn(exceptionView, animate).show(exceptionView);
        else
            hide(mRecyclerView).hide(exceptionView).fadeIn(progressBar, animate).show(progressBar);

        return this;
    }

    /**
     * Set empty text on list fragment
     *
     * @param message
     * @return this fragment
     */
    protected RefreshRecyclerFragment<E> setEmptyText(final String message) {
        if (exceptionView != null) {
            exceptionView.setIcon(R.drawable.ic_exception_blank);
            exceptionView.setMsg(message);
        }

        return this;
    }

    /**
     * Set empty text on list fragment
     *
     * @param resId
     * @return this fragment
     */
    protected RefreshRecyclerFragment<E> setEmptyText(final int resId) {
        return setEmptyText(getString(resId));
    }

    public List<E> getItems() {
        return this.items;
    }

    /**
     * Callback when a list view item is clicked
     *
     * @param parent
     * @param child
     * @param position
     * @param id
     */
    public void onListItemClick(RecyclerView parent, View child, int position, long id) {
    }

    /**
     * Callback when a list view item is clicked and held
     *
     * @param parent
     * @param child
     * @param position
     * @param id
     * @return true if the callback consumed the long click, false otherwise
     */
    public boolean onListItemLongClick(RecyclerView parent, View child, int position, long id) {
        return false;
    }
}

