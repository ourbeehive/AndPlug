package com.lean56.andplug.app.activity;

import android.os.Bundle;
import com.lean56.andplug.app.R;
import com.lean56.andplug.app.adapter.HomeFragmentAdapter;
import com.lean56.andplug.activity.TabPagerActivity;

public class HomeActivity extends TabPagerActivity<HomeFragmentAdapter> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        configureTabPager();
    }

    @Override
    protected boolean isShowHomeAsUp() {
        return false;
    }

    // [+] TabPagerActivity
    @Override
    protected HomeFragmentAdapter createAdapter() {
        return new HomeFragmentAdapter(this);
    }

    @Override
    protected int getIcon(int position) {
        switch (position) {
            case 0:
                return R.drawable.tab_index;
            case 1:
                return R.drawable.tab_feature;
            case 2:
                return R.drawable.tab_msg;
            case 3:
                return R.drawable.tab_profile;
            default:
                return super.getIcon(position);
        }
    }

    @Override
    protected void setCurrentItem(int position) {
        super.setCurrentItem(position);
        switch (position) {
            case 0:
                hideToolbar(false);
                setTitle(R.string.app_name);
                break;
            case 1:
                hideToolbar(true);
                break;
            case 2:
                hideToolbar(false);
                setTitle("消息中心");
                break;
            case 3:
                hideToolbar(false);
                setTitle("个人信息");
                break;
        }
    }
    // [-] TabPagerActivity
}
