package com.lean56.andplug.network;

import android.app.Activity;
import android.text.TextUtils;
import android.util.Log;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.lean56.andplug.BaseApplication;
import com.lean56.andplug.R;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;

/**
 * OkHttpCallback
 * handle okhttp.Callback
 * see {https://square.github.io/okhttp/2.x/okhttp/com/squareup/okhttp/Callback.html}
 *
 * @author Charles
 */
public abstract class OkHttpCallback implements com.squareup.okhttp.Callback {

    private Activity mActivity;

    public OkHttpCallback(Activity activity) {
        this.mActivity = activity;
    }

    // [+] Callback of OkHttp

    /**
     * Called when the request could not be executed due to
     * cancellation, a connectivity problem or timeout.
     *
     * @param request
     * @param e
     */
    @Override
    public void onFailure(final Request request, final IOException e) {
        mActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                onComplete(request, false);
                handleRequestFailure(request, e);
            }
        });
    }

    /**
     * Called when the HTTP response was successfully returned by the remote server
     *
     * @param response
     */
    @Override
    public void onResponse(final Response response) {
        mActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                onComplete(response.request(), true);
            }
        });

        try {
            final ResponseJson json = JSONObject.parseObject(response.body().bytes(), ResponseJson.class);
            mActivity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (json.statusSuccess()) {
                        try {
                            onApiSuccess(response, json);
                        } catch (Exception e) {
                            onApiFailure(response, json, e);
                        }
                    } else if (json.statusAuthFailure()){
                        onApiAuthFailure(response, json);
                    } else {
                        onApiFailure(response, json, null);
                    }
                }
            });
        } catch (final Exception e) {
            mActivity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    onApiFailure(response, null, e);
                }
            });
        }
    }
    // [-] Callback of OkHttp

    /**
     * on request complete, no matter failure or success
     *
     * @param request
     */
    protected void onComplete(Request request, boolean response) {
        // blank
    }

    protected abstract void onApiSuccess(Response response, ResponseJson json);

    protected void onApiFailure(Response response, ResponseJson json, Exception e) {
        if (e != null) {
            if (e instanceof JSONException) {
                BaseApplication.showToast(mActivity.getString(R.string.json_exception));
            } else if (e instanceof ClassCastException) {
                BaseApplication.showToast(mActivity.getString(R.string.class_cast_exception));
            } else if (e instanceof IOException) {
                BaseApplication.showToast(mActivity.getString(R.string.io_exception));
            } else if (e instanceof NullPointerException) {
                BaseApplication.showToast(mActivity.getString(R.string.null_point_exception));
            } else {
                BaseApplication.showToast(mActivity.getString(R.string.unknown_exception));
            }
        } else if (json != null && !TextUtils.isEmpty(json.getMsg())) {
            BaseApplication.showToast(json.getMsg());
        } else {
            BaseApplication.showToast(mActivity.getString(R.string.unknown_exception));
        }
    }

    protected void onApiAuthFailure(Response response, ResponseJson json) {
        // blank
    }

    /**
     * handle request failure, like cancellation, a connectivity problem or timeout
     *
     * @param request
     * @param e
     */
    protected void handleRequestFailure(Request request, IOException e) {
        Log.e(request.tag().toString(), "IOException:" + e.getMessage());

        if (e instanceof SocketTimeoutException) {
            BaseApplication.showToast(mActivity.getString(R.string.socket_timeout_exception));
        } else if (e instanceof ConnectException) {
            BaseApplication.showToast(mActivity.getString(R.string.network_exception));
        } else {
            BaseApplication.showToast(mActivity.getString(R.string.unknown_exception));
        }
    }

}
