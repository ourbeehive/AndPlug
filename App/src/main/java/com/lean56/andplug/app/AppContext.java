package com.lean56.andplug.app;

import android.util.Log;
import com.lean56.andplug.BaseApplication;
import io.rong.imkit.RongIM;

/**
 * App Application
 *
 * @author Charles
 */
public class AppContext extends BaseApplication {

    private static String TAG = AppContext.class.getSimpleName();

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "... Baton Application onCreate... pid=" + android.os.Process.myPid());

        initRongIM();
    }

    /**
     * init Rong IM engine
     */
    private void initRongIM() {
        RongIM.init(this);
    }
}
