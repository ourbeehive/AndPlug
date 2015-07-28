package com.lean56.andplug.app.activity;

import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.View;
import com.lean56.andplug.app.R;
import com.lean56.andplug.comlib.activity.BaseActivity;

/**
 * Search Activity
 *
 * @author charles
 */
public class SearchActivity extends BaseActivity {

    SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initToolbarSearchView();
    }

    @Override
    protected int getContentView() {
        return R.layout.search;
    }

    /**
     * init search view in the toolbar
     */
    private void initToolbarSearchView() {
        if (null == toolbar)
            return;

        searchView = (SearchView) toolbar.findViewById(R.id.toolbar_search_view);
        searchView.setVisibility(View.VISIBLE);
        searchView.onActionViewExpanded();
        searchView.setIconified(false);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(0, 0);
    }

}
