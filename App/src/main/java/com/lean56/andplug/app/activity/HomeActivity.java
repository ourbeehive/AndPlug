package com.lean56.andplug.app.activity;

import android.os.Bundle;
import android.util.Log;
import com.lean56.andplug.activity.TabPagerActivity;
import com.lean56.andplug.app.R;
import com.lean56.andplug.app.adapter.HomeFragmentAdapter;
import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;

public class HomeActivity extends TabPagerActivity<HomeFragmentAdapter> {

    private final static String TAG = HomeActivity.class.getSimpleName();

    String rongIMToken = "d6bCQsXiupB/4OyGkh+TOrI6ZiT8q7s0UEaMPWY0lMxmHdi1v/AAJxOma4aYXyaivfPIJjNHdE+FMH9kV/Jrxg==";//test

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        configureTabPager();
        setupRongIMConnection();
    }

    @Override
    protected boolean isShowHomeAsUp() {
        return false;
    }

    /**
     * setup the connection of Rong server
     */
    private void setupRongIMConnection() {
        RongIM.connect(rongIMToken, new RongIMClient.ConnectCallback() {
            @Override
            public void onTokenIncorrect() {
                //Connect Token 失效的状态处理，需要重新获取 Token
            }

            @Override
            public void onSuccess(String userId) {
                Log.e(TAG, "--onSuccess--" + userId);
            }

            @Override
            public void onError(RongIMClient.ErrorCode errorCode) {
                Log.e(TAG, "--onError with code--" + errorCode);
            }
        });
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
