package com.lean56.andplug.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.*;

/**
 * Adapter for RecyclerView
 *
 * @param <T>
 * @author Charles
 */
public abstract class BaseRecyclerAdapter<T> extends RecyclerView.Adapter<BaseRecyclerAdapter.ViewHolder> {

    protected Context mContext;
    private int mLayoutResId;

    protected List<T> mItems;
    private List<T> EMPTY = new ArrayList<>();

    /**
     * Create adapter
     *
     * @param context
     * @param layoutResId
     */
    public BaseRecyclerAdapter(final Context context, final int layoutResId) {
        this(context);
        this.mLayoutResId = layoutResId;
    }

    public BaseRecyclerAdapter(List<T> items) {
        if (items == null)
            items = EMPTY;
        this.mItems = items;
    }

    public BaseRecyclerAdapter(final Context context) {
        this.mContext = context;

        mItems = EMPTY;
    }

    public int getLayoutId(int viewType) {
        return this.mLayoutResId;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(getLayoutId(viewType), viewGroup, false);
        return new ViewHolder(itemView);
    }

    @Override
    public abstract void onBindViewHolder(ViewHolder viewHolder, int position);

    @Override
    public long getItemId(final int position) {
        return mItems.get(position).hashCode();
    }

    @Override
    public int getItemCount() {
        if (mItems == null)
            return 0;
        return mItems.size();
    }

    public void addItem(T viewItem) {
        if (mItems != null) {
            mItems.add(viewItem);
            notifyDataSetChanged();
        }
    }

    public void addAll(List<T> newData) {
        if (mItems != null) {
            int start = mItems.size();
            mItems.addAll(newData);
            notifyItemRangeInserted(start, mItems.size() - 1);
        }
    }

    public void replaceAll(List<T> newData) {
        clearAll();
        if (newData == null) {
            newData = new ArrayList<>();
        }
        mItems = newData;
        notifyItemRangeInserted(0, mItems.size() - 1);
    }

    public void clearAll() {
        if (mItems == null)
            return;

        int size = this.mItems.size();
        if (size > 0) {
            mItems = new ArrayList<>();
            this.notifyItemRangeRemoved(0, size);
        }
    }

    protected List<T> getItems() {
        return mItems;
    }

    public void setItems(final List<T> items) {
        if (items != null)
            this.mItems = items;
        else
            this.mItems = EMPTY;
        notifyDataSetChanged();
    }

    public T getItem(final int position) {
        return mItems.get(position);
    }

    /**
     * Provide a reference to the type of views that you are using (custom ViewHolder)
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final SparseArray<View> mViews = new SparseArray<>();

        public ViewHolder(View v) {
            super(v);
        }

        public <T extends View> T getView(int viewId) {
            View view = mViews.get(viewId);
            if (view == null) {
                view = itemView.findViewById(viewId);
                mViews.put(viewId, view);
            }
            return (T) view;
        }

        public ViewHolder setTag(int viewId, Object tag) {
            getView(viewId).setTag(tag);
            return this;
        }

        public ViewHolder setText(int viewId, CharSequence text) {
            TextView view = getView(viewId);
            view.setText(text);
            return this;
        }

        public ViewHolder setGone(int viewId, boolean gone) {
            View view = getView(viewId);
            if (gone) {
                view.setVisibility(View.GONE);
            } else {
                view.setVisibility(View.VISIBLE);
            }
            return this;
        }

        public ViewHolder setImageResource(int viewId, int drawableId) {
            ImageView view = (ImageView) getView(viewId);
            view.setImageResource(drawableId);
            return this;
        }

        public ViewHolder setOnClickListener(int viewId, View.OnClickListener clickListener) {
            getView(viewId).setOnClickListener(clickListener);
            return this;
        }

        public ViewHolder setOnClickListener(int viewId, View.OnClickListener clickListener, Object tag) {
            View v = getView(viewId);
            v.setTag(tag);
            v.setOnClickListener(clickListener);
            return this;
        }

        public ViewHolder setOnItemClickListener(View.OnClickListener clickListener) {
            itemView.setOnClickListener(clickListener);
            return this;
        }

    }

}
