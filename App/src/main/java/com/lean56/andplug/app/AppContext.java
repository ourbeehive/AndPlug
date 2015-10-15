package com.lean56.andplug.app;

import android.util.Log;
import com.lean56.andplug.BaseApplication;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

/**
 * App Application
 *
 * @author Charles
 */
public class AppContext extends BaseApplication {

    private static String TAG = AppContext.class.getSimpleName();

    private static AppContext instance;

    /**
     * support a method to get a instance for the outside
     */
    public synchronized static AppContext getInstance() {
        if (null == instance) {
            instance = new AppContext();
        }
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "... Baton Application onCreate... pid=" + android.os.Process.myPid());

        initImageLoader();
        initRongIM();

    }

    /**
     * init the image loader of UniversalImageLoader
     */
    private void initImageLoader() {
        // Create global cfg and init ImageLoader with this cfg
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this)
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .denyCacheImageMultipleSizesInMemory()
                .diskCacheFileNameGenerator(new Md5FileNameGenerator())
                .diskCacheSize(50 * 1024 * 1024) // 50 Mb
                .diskCacheFileCount(300)
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                // .diskCacheExtraOptions(screenWidth / 3, screenWidth / 3, null)
                .build();

        // Initialize ImageLoader with configuration.
        ImageLoader.getInstance().init(config);
    }

    /**
     * init Rong IM engine
     */
    private void initRongIM() {
        //RongIM.init(this);
    }


    // [+] Account
    private String accountToken;

    public boolean isAccountLogin() {
        return false;
    }
    // [-] Account
}
