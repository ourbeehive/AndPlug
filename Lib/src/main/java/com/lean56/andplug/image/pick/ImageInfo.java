package com.lean56.andplug.image.pick;

import java.io.File;
import java.io.Serializable;

/**
 * Image Info
 *
 * @author chenchao@coding
 */
public class ImageInfo implements Serializable {

    public String path;
    public String type = "horizontality";
    public long photoId;
    public int width;
    public int height;
    private static final String prefix = "file://";

    public ImageInfo(String path) {
        this.path = pathAddPrefix(path);
    }

    public ImageInfo(String path, String type) {
        this.path = pathAddPrefix(path);
        this.type = type;
    }

    public boolean isOrientationHorizontal() {
        return type.equalsIgnoreCase("horizontality");
    }

    public static String pathRemovePrefix(String path) {
        if (path.startsWith(prefix)) {
            path = path.substring(prefix.length());
        }
        return path;
    }

    public static String pathAddPrefix(String path) {
        if (!path.startsWith(prefix)) {
            path = prefix + path;
        }
        return path;
    }

    public static boolean isLocalFile(String path) {
        return path.startsWith(prefix);
    }

    public static File getLocalFile(String pathSrc) {
        String pathDesc = pathSrc;
        if (isLocalFile(pathSrc)) {
            pathDesc = pathSrc.substring(prefix.length(), pathSrc.length());
        }

        return new File(pathDesc);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ImageInfo imageInfo = (ImageInfo) o;

        if (photoId != imageInfo.photoId) return false;
        if (width != imageInfo.width) return false;
        if (height != imageInfo.height) return false;
        return !(path != null ? !path.equals(imageInfo.path) : imageInfo.path != null);
    }

    @Override
    public int hashCode() {
        int result = path != null ? path.hashCode() : 0;
        result = 31 * result + (int) (photoId ^ (photoId >>> 32));
        result = 31 * result + width;
        result = 31 * result + height;
        return result;
    }
}
