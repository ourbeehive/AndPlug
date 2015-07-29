package com.lean56.andplug.app.fragment;

import android.os.Bundle;
import android.support.v4.content.Loader;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.lean56.andplug.app.R;
import com.lean56.andplug.app.dao.Msg;
import com.lean56.andplug.fragment.RefreshRecyclerFragment;

import java.util.List;

/**
 * MsgFragment show Message List
 *
 * @author Charles
 */
public class MsgFragment extends RefreshRecyclerFragment<Msg> {

    public MsgFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.profile, container, false);
    }

    @Override
    public Loader<List<Msg>> onCreateLoader(int id, Bundle args) {
        return null;
    }

    @Override
    protected RecyclerView.Adapter createAdapter(List<Msg> items) {
        return super.createAdapter();
    }

    @Override
    protected int getErrorMessage(Exception exception) {
        return 0;
    }
}
