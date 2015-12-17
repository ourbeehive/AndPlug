package com.lean56.andplug.image.entity;

import android.net.Uri;

import java.io.File;

/**
 * PhotoData
 *
 * @author Charles
 */
public class PhotoData {
    ImageInfo mImageinfo;
    Uri uri = Uri.parse("");
    String serviceUri = "";

    public PhotoData(File file, ImageInfo info) {
        uri = Uri.fromFile(file);
        mImageinfo = info;
    }

    public PhotoData(PhotoDataSerializable data) {
        uri = Uri.parse(data.uriString);
        serviceUri = data.serviceUri;
        mImageinfo = data.mImageInfo;
    }

    public ImageInfo getmImageinfo() {
        return mImageinfo;
    }

    public void setmImageinfo(ImageInfo mImageinfo) {
        this.mImageinfo = mImageinfo;
    }

    public Uri getUri() {
        return uri;
    }

    public void setUri(Uri uri) {
        this.uri = uri;
    }

    public String getServiceUri() {
        return serviceUri;
    }

    public void setServiceUri(String serviceUri) {
        this.serviceUri = serviceUri;
    }
}
