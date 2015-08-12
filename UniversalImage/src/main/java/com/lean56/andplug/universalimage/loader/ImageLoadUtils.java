package com.lean56.andplug.universalimage.loader;

import android.widget.ImageView;
import com.lean56.andplug.universalimage.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * Image Load Utils
 *
 * @author Charles(zhangchaoxu@gmail.com)
 */
public class ImageLoadUtils {

    public ImageLoader imageLoader = ImageLoader.getInstance();

    public static DisplayImageOptions options = new DisplayImageOptions.Builder()
            .showImageOnLoading(R.drawable.ic_default_user)
            .showImageForEmptyUri(R.drawable.ic_default_user)
            .showImageOnFail(R.drawable.ic_default_user)
            .cacheInMemory(true)
            .cacheOnDisk(true)
            .considerExifParams(true)
            .build();

    public void loadImage(ImageView imageView, String url) {
        imageLoader.displayImage(url, imageView, options);
    }
}
