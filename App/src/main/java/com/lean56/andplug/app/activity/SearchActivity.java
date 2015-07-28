package com.lean56.andplug.app.activity;

import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.View;
import android.widget.TextView;
import com.lean56.andplug.app.R;
import com.lean56.andplug.comlib.activity.BaseActivity;

import java.lang.reflect.Field;
import java.util.ArrayList;

/**
 * Search Activity
 *
 * @author charles
 */
public class SearchActivity extends BaseActivity {

    private ArrayList mData = new ArrayList();
    private ArrayList mSearchData = new ArrayList();

    SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initSearchView();
    }

    @Override
    protected int getContentView() {
        return R.layout.search;
    }

    /**
     * init search view in the toolbar
     */
    private void initSearchView() {
        if (null == toolbar)
            return;

        searchView = (SearchView) toolbar.findViewById(R.id.toolbar_search_view);
        searchView.setVisibility(View.VISIBLE);
        searchView.setQueryHint("搜索提示");
        searchView.onActionViewExpanded();
        searchView.setIconified(false);
        SearchView.SearchAutoComplete mEdit = (SearchView.SearchAutoComplete) searchView.findViewById(R.id.search_src_text);
        if (mEdit != null) {
            // TODO set the cursor color
            try {
                // https://github.com/android/platform_frameworks_base/blob/kitkat-release/core/java/android/widget/TextView.java#L562-564
                Field f = TextView.class.getDeclaredField("mCursorDrawableRes");
                f.setAccessible(true);
                f.set(mEdit, R.drawable.cursor_white);
            } catch (Exception ignored) {}
        }
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
