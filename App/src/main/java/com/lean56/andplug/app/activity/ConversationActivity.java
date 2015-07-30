package com.lean56.andplug.app.activity;

import android.os.Bundle;
import com.lean56.andplug.activity.BaseActivity;
import com.lean56.andplug.app.R;

/**
 * ConversationActivity powered by RongIM
 *
 * @author Charles
 */
public class ConversationActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitle("对方用户名");
    }

    @Override
    protected int getContentView() {
        return R.layout.conversation;
    }

}
