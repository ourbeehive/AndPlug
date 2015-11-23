package com.lean56.andplug.network;

import android.app.Activity;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.lean56.andplug.BaseApplication;
import com.lean56.andplug.R;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.net.SocketTimeoutException;

/**
 * OkHttpCallback
 * handle okhttp.Callback
 *
 * @author Charles
 */
public abstract class OkHttpCallback implements com.squareup.okhttp.Callback {

    private Activity mActivity;

    public OkHttpCallback(Activity activity) {
        this.mActivity = activity;
    }

    // [+] Callback of OkHttp
    @Override
    public void onFailure(final Request request, final IOException e) {
        mActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                handleApiFailure(request, e);
            }
        });
    }

    /**
     * TODO 500也进到这里
     * @param response
     */
    @Override
    public void onResponse(final Response response) {
        handlePreResponse(response);
        try {
            final ResponseJson json = JSONObject.parseObject(response.body().bytes(), ResponseJson.class);
            mActivity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    handleApiSuccess(response, json);
                }
            });
        } catch (JSONException je) {
            mActivity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    BaseApplication.showToast(mActivity.getString(R.string.json_exception));
                }
            });
        }  catch (IOException ioe) {
            mActivity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    BaseApplication.showToast(mActivity.getString(R.string.io_exception));
                }
            });
        }
    }
    // [-] Callback of OkHttp

    protected void handlePreResponse(Response response) {}

    protected abstract void handleApiSuccess(Response response, ResponseJson json);

    protected void handleApiFailure(Request request, IOException e) {
        if (e instanceof SocketTimeoutException) {
            BaseApplication.showToast(mActivity.getString(R.string.socket_timeout_exception));
        } else {
            BaseApplication.showToast(mActivity.getString(R.string.unknown_exception));
        }
    }

}
