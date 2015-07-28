package com.lean56.andplug.comlib.adapter;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * Adapter for ListView
 * see {http://blog.csdn.net/lmj623565791/article/details/38902805}
 *
 * @param <T>
 * @author Charles
 */
public abstract class BaseListAdapter<T> extends BaseAdapter {

    protected Context mContext;

    protected final LayoutInflater mInflater;

    private final int mLayoutResID;

    private static final Object[] EMPTY = new Object[0];

    private Object[] items;

    /**
     * Common ViewHolder for ListView
     */
    public static class ViewHolder {

        private final SparseArray<View> mViews;
        private View mConvertView;

        private ViewHolder(Context context, ViewGroup parent, int layoutId, int position) {
            this.mViews = new SparseArray<>();
            mConvertView = LayoutInflater.from(context).inflate(layoutId, parent, false);
            mConvertView.setTag(this);
        }

        /**
         * get the first ViewHolder object
         *
         * @param context
         * @param convertView
         * @param parent
         * @param layoutId
         * @param position
         * @return the first ViewHolder object
         */
        public static ViewHolder get(Context context, View convertView, ViewGroup parent, int layoutId, int position) {
            if (null == convertView) {
                return new ViewHolder(context, parent, layoutId, position);
            } else {
                return (ViewHolder) convertView.getTag();
            }
        }

        /**
         * find the view object by id, add into views if not existed
         *
         * @param viewId
         * @return
         */
        public <T extends View> T getView(int viewId) {
            View view = mViews.get(viewId);
            if (view == null) {
                view = mConvertView.findViewById(viewId);
                mViews.put(viewId, view);
            }
            return (T) view;
        }

        public View getConvertView() {
            return mConvertView;
        }

    }

    /**
     * Create adapter
     *
     * @param context
     * @param layoutResId
     */
    public BaseListAdapter(final Context context, final int layoutResId) {
        this.mContext = context;
        this.mInflater = LayoutInflater.from(mContext);
        this.mLayoutResID = layoutResId;

        items = EMPTY;
    }

    @Override
    public int getCount() {
        return items.length;
    }

    @Override
    public T getItem(final int position) {
        return (T) items[position];
    }

    @Override
    public long getItemId(final int position) {
        return items[position].hashCode();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder = getViewHolder(position, convertView, parent);
        convert(viewHolder, getItem(position));
        return viewHolder.getConvertView();
    }

    public abstract void convert(ViewHolder helper, T item);

    private ViewHolder getViewHolder(int position, View convertView, ViewGroup parent) {
        return ViewHolder.get(mContext, convertView, parent, mLayoutResID, position);
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
}
