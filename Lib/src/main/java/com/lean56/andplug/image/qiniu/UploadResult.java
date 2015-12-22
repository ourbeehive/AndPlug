package com.lean56.andplug.image.qiniu;

/**
 * UploadResult
 * <p/>
 * {
 * "hash": "FmFmuJtBSrlk3Eit23OIlsS6eQBK",
 * "key": "FmFmuJtBSrlk3Eit23OIlsS6eQBK"
 * }
 * or
 * {
 * "error": "bad token"
 * }
 *
 * @author Charles
 */
public class UploadResult implements java.io.Serializable {

    private String hash;
    private String key;
    private String error;

    public UploadResult() {
    }

    public UploadResult(String hash, String key) {
        this.hash = hash;
        this.key = key;
    }

    public UploadResult(String error) {
        this.error = error;
    }

    public UploadResult(String hash, String key, String error) {
        this.hash = hash;
        this.key = key;
        this.error = error;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
