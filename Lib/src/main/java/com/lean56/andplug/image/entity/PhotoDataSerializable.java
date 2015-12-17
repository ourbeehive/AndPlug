package com.lean56.andplug.image.entity;

import java.io.Serializable;

/**
 *  serialize image data as Uri can not been serialize directly
 *
 *  @author Charles
 */
public class PhotoDataSerializable implements Serializable {
    String uriString = "";
    String serviceUri = "";
    ImageInfo mImageInfo;

    public PhotoDataSerializable(PhotoData data) {
        uriString = data.uri.toString();
        serviceUri = data.serviceUri;
        mImageInfo = data.mImageinfo;
    }
}
