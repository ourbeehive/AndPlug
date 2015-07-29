package com.lean56.andplug.app.fragment;

import android.os.Bundle;
import android.support.v4.content.Loader;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import com.lean56.andplug.adapter.BaseRecyclerAdapter;
import com.lean56.andplug.app.adapter.MsgAdapter;
import com.lean56.andplug.app.dao.Msg;
import com.lean56.andplug.app.utils.ThrowableLoader;
import com.lean56.andplug.fragment.RefreshRecyclerFragment;
import io.rong.imkit.RongIM;

import java.util.ArrayList;
import java.util.List;

/**
 * MsgFragment show Message List
 *
 * @author Charles
 */
public class MsgFragment extends RefreshRecyclerFragment<Msg> {

    private final static String TAG = MsgFragment.class.getSimpleName();

    @Override
    public Loader<List<Msg>> onCreateLoader(int id, Bundle args) {
        return new ThrowableLoader<List<Msg>>(getActivity(), items) {
            @Override
            public List<Msg> loadData() throws Exception {
                List<Msg> msgs = new ArrayList<Msg>();
                Msg msg1 = new Msg();
                Msg msg2 = new Msg();
                msg1.setTitle("用户1");
                msg2.setTitle("用户2");
                msg1.setContent("内容1");
                msg2.setContent("内容2");
                msgs.add(msg1);
                msgs.add(msg2);
                return msgs;
            }
        };
    }

    @Override
    public void onListItemClick(RecyclerView parent, View child, int position, long id) {
        Msg item = items.get(position);
        Log.d(TAG, item.getTitle());
        if (null != RongIM.getInstance()) {
            RongIM.getInstance().startPrivateChat(getActivity(), "2462", "hello");
        }
    }

    @Override
    protected BaseRecyclerAdapter createAdapter(List<Msg> items) {
        return new MsgAdapter(getActivity(), items);
    }

    @Override
    protected int getErrorMessage(Exception exception) {
        return 0;
    }
}
