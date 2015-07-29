package com.lean56.andplug.app.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.*;
import com.lean56.andplug.app.R;
import com.lean56.andplug.app.activity.FeatureDetailActivity;
import com.lean56.andplug.app.activity.SearchActivity;
import com.lean56.andplug.comlib.fragment.BaseFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class IndexFragment extends BaseFragment {

    public IndexFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.index, container, false);
        view.findViewById(R.id.slider).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getActivity(), FeatureDetailActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu optionsMenu, MenuInflater inflater) {
        inflater.inflate(R.menu.index, optionsMenu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_search:
                Intent intent = new Intent(getActivity(), SearchActivity.class);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.abc_fade_in, R.anim.abc_fade_out);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
