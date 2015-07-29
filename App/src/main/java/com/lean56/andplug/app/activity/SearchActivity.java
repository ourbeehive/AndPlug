package com.lean56.andplug.app.activity;

import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import com.lean56.andplug.app.R;
import com.lean56.andplug.comlib.activity.BaseActivity;
import com.lean56.andplug.comlib.view.FlowLayout;

import java.lang.reflect.Field;
import java.util.ArrayList;

/**
 * Search Activity
 *
 * flow layout see
 * {http://www.apkbus.com/android-239725-1-1.html}
 * {https://github.com/blazsolar/FlowLayout/}
 *
 * @author charles
 */
public class SearchActivity extends BaseActivity {

    private ArrayList mData = new ArrayList();
    private ArrayList mSearchData = new ArrayList();
    private ArrayList<String> hotKeywords = new ArrayList<>();

    SearchView searchView;
    FlowLayout mHotKeywordsFlow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initSearchView();

        // init hot-keywords
        mHotKeywordsFlow = (FlowLayout) findViewById(R.id.flow_hot_keywords);
        getHotKeywords();
        FlowLayout.LayoutParams layoutParams = new FlowLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.rightMargin = 10;
        for(String hotKeyword : hotKeywords) {
            Button btn = new Button(this);
            btn.setText(hotKeyword);
            // btn.setbackground
            mHotKeywordsFlow.addView(btn, layoutParams);
        }
    }

    private void getHotKeywords() {
        hotKeywords.add("戒指");
        hotKeywords.add("手机壳");
        hotKeywords.add("钱包");
        hotKeywords.add("包");
        hotKeywords.add("相连");
        hotKeywords.add("情侣");
        hotKeywords.add("连衣裙");
        hotKeywords.add("当即热款");
        hotKeywords.add("你说好吗adsad");
        hotKeywords.add("侧似乎");
        hotKeywords.add("凉鞋");
        hotKeywords.add("收敛");
        hotKeywords.add("购购");
        hotKeywords.add("你好");
        hotKeywords.add("宁波始发发hi阿飞阿飞撒艾丝凡");
        hotKeywords.add("0");
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
        searchView.setQueryHint("请输入关键词");
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
