package com.lean56.andplug.common;

/**
 * Request Codes
 * 0001-0fff will be reserved
 *
 * @author Charles
 */
public interface RequestCodes {

    int PHOTO_PICK = 0x0001;

    int PHOTO_PICK_SINGLE = 0x0002;

    int PHOTO_PICK_MULTI = 0x0003;

    int PHOTO_PICK_CAMERA = 0x0004;

    int PHOTO_PICK_ALBUM = 0x0005;

    int PHOTO_PICK_SELECTED = 0x0006;

}
