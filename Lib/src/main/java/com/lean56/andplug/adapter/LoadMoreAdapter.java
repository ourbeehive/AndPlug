package com.lean56.andplug.adapter;

import android.content.Context;
import com.lean56.andplug.R;
import com.lean56.andplug.common.BaseEntity;

import java.util.List;

/**
 * LoadMoreAdapter
 *
 * @author Charles
 */
public abstract class LoadMoreAdapter<T extends BaseEntity> extends BaseRecyclerAdapter<T> {

    public static final int VIEW_TYPE_ITEM_LOAD_MORE = 2015063009;

    private int loadMoreLayoutId = R.layout.layout_more_progress;
    private boolean isLoading;
    private boolean isLoadingCompleted;
    private int pageCount = 10;

    private ILoadMoreCallback callback;

    public LoadMoreAdapter(final Context context, final int layoutResId) {
        super(context, layoutResId);
    }

    public LoadMoreAdapter(int pageCount, List<T> mDatas) {
        super(mDatas);
        this.pageCount = pageCount;
        if (this.mItems.size() >= pageCount) {
            this.mItems.add(getLoadMoreItem());
        }
    }

    public LoadMoreAdapter(List<T> mDatas, int loadMoreLayoutId) {
        super(mDatas);
        this.loadMoreLayoutId = loadMoreLayoutId;
        if (this.mItems.size() >= pageCount) {
            this.mItems.add(getLoadMoreItem());
        }
    }

    public LoadMoreAdapter(int pageCount, List<T> mDatas, int loadMoreLayoutId) {
        super(mDatas);
        this.pageCount = pageCount;
        this.loadMoreLayoutId = loadMoreLayoutId;
        if (this.mItems.size() >= pageCount) {
            this.mItems.add(getLoadMoreItem());
        }
    }

    @Override
    public int getLayoutId(int viewType) {
        if (viewType == VIEW_TYPE_ITEM_LOAD_MORE)
            return loadMoreLayoutId;
        return getNormalLayoutId();
    }

    public abstract int getNormalLayoutId();

    @Override
    public void onBindViewHolder(BaseRecyclerAdapter.ViewHolder holder, int position) {
        if (position == mItems.size() - 1 && position >= pageCount - 1 && !isLoading && !isLoadingCompleted) {
            isLoading = true;
            if (callback != null) {
                callback.loadMore(position);
            }
        }
    }

    @Override
    public int getItemViewType(int position) {
        T viewItem = mItems.get(position);
        return viewItem.getViewType();
    }

    @Override
    public void addAll(List<T> newData) {
        hideLoadMore();
        if (newData == null || newData.size() < pageCount) {
            isLoadingCompleted = true;
        } else if (!isLoadingCompleted) {
            newData.add(getLoadMoreItem());
        }

        if (newData != null && newData.size() > 0) {
            int start = mItems.size();
            mItems.addAll(newData);
            notifyItemRangeInserted(start, mItems.size() - 1);
        }
        isLoading = false;
    }

    @Override
    public void replaceAll(List<T> newData) {
        clearAll();
        addAll(newData);
    }

    public void setLoadMoreCallback(ILoadMoreCallback callback) {
        this.callback = callback;
    }

    public boolean getLoadingCompleted() {
        return isLoadingCompleted;
    }

    public void setLoadingCompleted(boolean isLoadingCompleted) {
        this.isLoadingCompleted = isLoadingCompleted;
    }

    private T getLoadMoreItem() {
        return (T) new BaseEntity(VIEW_TYPE_ITEM_LOAD_MORE);
    }

    private void hideLoadMore() {
        int lastPosition = mItems.size() - 1;
        if (lastPosition >= 0) {
            T viewItem = mItems.get(lastPosition);
            if (viewItem.getViewType() == VIEW_TYPE_ITEM_LOAD_MORE) {
                mItems.remove(lastPosition);
                notifyDataSetChanged();
            }
        }
    }

    public interface ILoadMoreCallback {
        void loadMore(int position);
    }

}
