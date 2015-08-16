package com.lean56.andplug.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * Adapter for RecyclerView
 *
 * @param <T>
 * @author Charles
 */
public abstract class BaseRecyclerAdapter<T> extends RecyclerView.Adapter<BaseRecyclerAdapter.ViewHolder> {

    protected Context mContext;

    protected final LayoutInflater mInflater;

    private final int mLayoutResId;

    private static final Object[] EMPTY = new Object[0];

    private Object[] items;

    /**
     * Create adapter
     *
     * @param context
     * @param layoutResId
     */
    public BaseRecyclerAdapter(final Context context, final int layoutResId) {
        this.mContext = context;
        this.mInflater = LayoutInflater.from(mContext);
        this.mLayoutResId = layoutResId;

        items = EMPTY;
    }

    /**
     * Provide a reference to the type of views that you are using (custom ViewHolder)
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final SparseArray<View> mViews = new SparseArray<>();

        public ViewHolder(View v) {
            super(v);
        }

        /**
         * get View by id
         *
         * @param viewId
         * @return
         */
        public <T extends View> T getView(int viewId) {

            View view = mViews.get(viewId);
            if (view == null) {
                view = itemView.findViewById(viewId);
                mViews.put(viewId, view);
            }
            return (T) view;
        }

        /**
         * set text for TextView
         *
         * @param viewId
         * @param text
         * @return
         */
        public ViewHolder setText(int viewId, String text) {
            TextView view = getView(viewId);
            view.setText(text);
            return this;
        }

        /**
         * set text for TextView
         *
         * @param viewId
         * @param gone
         * @return
         */
        public ViewHolder setGone(int viewId, boolean gone) {
            View view = getView(viewId);
            if (gone) {
                view.setVisibility(View.GONE);
            } else {
                view.setVisibility(View.VISIBLE);
            }
            return this;
        }

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(mLayoutResId, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public abstract void onBindViewHolder(ViewHolder viewHolder, int position);

    @Override
    public long getItemId(final int position) {
        return items[position].hashCode();
    }

    @Override
    public int getItemCount() {
        return items.length;
    }

    /**
     * Get a list of all items
     *
     * @return list of all items
     */
    protected List<T> getItems() {
        List<? extends Object> objList = Arrays.asList(items);
        return (List<T>) objList;
    }

    /**
     * Set items to display
     *
     * @param items
     */
    public void setItems(final Collection<?> items) {
        if (items != null && !items.isEmpty())
            setItems(items.toArray());
        else
            setItems(EMPTY);
    }

    /**
     * Set items to display
     *
     * @param items
     */
    public void setItems(final Object[] items) {
        if (items != null)
            this.items = items;
        else
            this.items = EMPTY;
        notifyDataSetChanged();
    }

    public T getItem(final int position) {
        return (T) items[position];
    }

}
