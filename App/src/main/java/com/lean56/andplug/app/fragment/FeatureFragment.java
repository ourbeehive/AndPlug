package com.lean56.andplug.app.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.lean56.andplug.app.R;
import com.lean56.andplug.comlib.fragment.BaseFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class FeatureFragment extends BaseFragment {

    public FeatureFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.profile, container, false);
    }


}
