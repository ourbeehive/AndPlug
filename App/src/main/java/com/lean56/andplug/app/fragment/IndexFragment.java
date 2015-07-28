package com.lean56.andplug.app.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.*;
import android.widget.Toast;
import com.lean56.andplug.app.R;
import com.lean56.andplug.comlib.fragment.BaseFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class IndexFragment extends BaseFragment {

    public IndexFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.profile, container, false);
    }

    @Override
    public void onCreateOptionsMenu(Menu optionsMenu, MenuInflater inflater) {
        inflater.inflate(R.menu.index, optionsMenu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                Toast.makeText(getActivity(), "serrubf", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
