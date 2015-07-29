package com.lean56.andplug.app.fragment;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.lean56.andplug.app.R;
import com.lean56.andplug.app.adapter.FeatureSubAdapter;
import com.lean56.andplug.app.view.WechatTab;
import com.lean56.andplug.fragment.BaseFragment;

/**
 * Feature Fragment
 *
 * @author Charles
 */
public class FeatureFragment extends BaseFragment {

    ViewPager mFeatureViewPager;
    WechatTab mFeatureTab;

    private FeatureSubAdapter mSubAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.feature, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // find view
        mFeatureTab = (WechatTab) view.findViewById(R.id.tab_feature);
        mFeatureViewPager = (ViewPager) view.findViewById(R.id.vp_feature);
        // fill view
        mSubAdapter = new FeatureSubAdapter(getChildFragmentManager());
        mFeatureViewPager.setAdapter(mSubAdapter);
        int pageMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4, getResources().getDisplayMetrics());
        mFeatureViewPager.setPageMargin(pageMargin);
        mFeatureTab.setViewPager(mFeatureViewPager);
    }

}
