package com.lean56.andplug.app.adapter;

import android.app.Activity;
import android.content.res.Resources;
import com.lean56.andplug.adapter.BaseRecyclerAdapter;
import com.lean56.andplug.app.R;
import com.lean56.andplug.app.dao.Msg;

import java.util.Collection;

/**
 * Adapter to display a list of {@link com.lean56.andplug.app.dao.Msg} objects
 *
 * @author Charles
 */
public class MsgAdapter extends BaseRecyclerAdapter<Msg> {

    private Activity mActivity;
    private Resources mResources;

    public MsgAdapter(Activity activity, Collection<Msg> elements) {
        super(activity, R.layout.item_msg);
        mActivity = activity;
        mResources = activity.getResources();

    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int position) {
        final Msg entity = getItem(position);

        viewHolder.setText(R.id.title, entity.getTitle());
        viewHolder.setText(R.id.comment, entity.getContent());
    }
}
