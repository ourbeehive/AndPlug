package com.lean56.andplug.image;

import android.graphics.Bitmap;
import android.net.Uri;
import android.widget.ImageView;
import com.lean56.andplug.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

/**
 * Image Load Utils
 *
 * @author Charles(zhangchaoxu@gmail.com)
 */
public class ImageLoadUtils {

    public ImageLoader imageLoader = ImageLoader.getInstance();

    public static DisplayImageOptions options = new DisplayImageOptions.Builder()
            .showImageOnLoading(R.drawable.ic_default_image)
            .showImageForEmptyUri(R.drawable.ic_exception_image)
            .showImageOnFail(R.drawable.ic_exception_image)
            .cacheInMemory(true)
            .cacheOnDisk(true)
            .considerExifParams(true)
            .bitmapConfig(Bitmap.Config.RGB_565)
            .imageScaleType(ImageScaleType.EXACTLY)
            .build();

    public static DisplayImageOptions optionsAvatar = new DisplayImageOptions.Builder()
            .showImageOnLoading(R.drawable.ic_default_image)
            .showImageForEmptyUri(R.drawable.ic_exception_image)
            .showImageOnFail(R.drawable.ic_exception_image)
            .cacheInMemory(true)
            .cacheOnDisk(true)
            .considerExifParams(true)
            .bitmapConfig(Bitmap.Config.RGB_565)
            .imageScaleType(ImageScaleType.EXACTLY)
            .build();

    public void loadPhotoImage(String url, ImageView imageView) {
        imageLoader.displayImage(url, imageView, options);
    }

    public void loadPostImage(String url, ImageView imageView) {
        imageLoader.displayImage(url, imageView, options);
    }

    public void loadAvatar(String url, ImageView imageView) {
        imageLoader.displayImage(url, imageView, optionsAvatar);
    }

    public void loadAvatar(Uri uri, ImageView imageView) {
        loadAvatar(uri.toString(), imageView);
    }
}
