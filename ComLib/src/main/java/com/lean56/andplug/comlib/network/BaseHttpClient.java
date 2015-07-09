package com.lean56.andplug.comlib.network;

import android.content.Context;
import com.alibaba.fastjson.JSON;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.SyncHttpClient;
import org.apache.http.HttpEntity;
import org.apache.http.entity.ByteArrayEntity;

/**
 * Base Http Client used for Restful API
 * see {http://loopj.com/android-async-http/}
 *
 * 0. static method for common define
 * 1. post/get/put/delete method
 *
 * @author Charles <zhangchaoxu@gmail.com>
 */
public abstract class BaseHttpClient {
    protected final static String CONTENT_TYPE_JSON = "application/json";
    protected final static String CONTENT_TYPE_XML = "application/xml";
    public static final String CONTENT_TYPE_JPEG = "image/jpeg";

    private static final int MAX_RETRIES = 5; // default 5
    private static final int MAX_RETRY_TIMEOUT = 10 * 1000; // default 20s
    private static final int MAX_TIMEOUT = 20 * 1000; // default 10s
    private static final int MAX_CONNECTIONS = 5; // default 10

    // asynchronous http client
    protected static AsyncHttpClient asyncClient = new AsyncHttpClient();
    // synchronous http client
    protected static SyncHttpClient syncClient = new SyncHttpClient();

    static {
        asyncClient.setMaxRetriesAndTimeout(MAX_RETRIES, MAX_RETRY_TIMEOUT);
        asyncClient.setMaxConnections(MAX_CONNECTIONS);
        asyncClient.setTimeout(MAX_TIMEOUT);

        syncClient.setMaxRetriesAndTimeout(MAX_RETRIES, MAX_RETRY_TIMEOUT);
        syncClient.setMaxConnections(MAX_CONNECTIONS);
        syncClient.setTimeout(MAX_TIMEOUT);
    }

    // [+] basic cancel method
    public static void cancelAsyncRequest(Context context, boolean mayInterruptIfRunning) {
        asyncClient.cancelRequests(context, mayInterruptIfRunning);
    }

    public static void cancelSyncRequest(Context context, boolean mayInterruptIfRunning) {
        syncClient.cancelRequests(context, mayInterruptIfRunning);
    }

    public static AsyncHttpClient getAsyncClient() {
        return asyncClient;
    }

    public static SyncHttpClient getSyncClient() {
        return syncClient;
    }

    // [+] base get
    public static void get(boolean async, String url, AsyncHttpResponseHandler responseHandler) {
        get(async, null, url, null, responseHandler);
    }

    public static void get(boolean async, String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        get(async, null, url, params, responseHandler);
    }

    public static void get(Context context, String url, AsyncHttpResponseHandler responseHandler) {
        get(false, context, url, responseHandler);
    }

    public static void get(boolean async, Context context, String url, AsyncHttpResponseHandler responseHandler) {
        get(async, context, url, null, responseHandler);
    }

    public static void get(boolean async, Context context, String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        if (async) {
            asyncClient.get(context, url, params, responseHandler);
        } else {
            syncClient.get(context, url, params, responseHandler);
        }
    }
    // [-] base get

    // [+] base post
    public static void post(boolean async, String url, AsyncHttpResponseHandler responseHandler) {
        post(async, null, url, null, responseHandler);
    }

    public static void post(boolean async, String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        post(async, null, url, params, responseHandler);
    }

    public static void post(Context context, String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        post(false, context, url, params, responseHandler);
    }

    public static void post(boolean async, Context context, String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        if (async)
            asyncClient.post(context, url, params, responseHandler);
        else
            syncClient.post(context, url, params, responseHandler);
    }
    // [-] base post

    // [+] post entity
    public static void postJSON(Context context, String url, JSON json, AsyncHttpResponseHandler responseHandler) {
        postJSON(false, context, url, json, responseHandler);
    }

    public static void postJSON(boolean async, Context context, String url, JSON json, AsyncHttpResponseHandler responseHandler) {
        ByteArrayEntity entity = new ByteArrayEntity(json.toString().getBytes());
        postEntity(async, context, url, entity, CONTENT_TYPE_JSON, responseHandler);
    }

    public static void postJSON(boolean async, Context context, String url, byte[] json, AsyncHttpResponseHandler responseHandler) {
        ByteArrayEntity entity = new ByteArrayEntity(json);
        postEntity(async, context, url, entity, CONTENT_TYPE_JSON, responseHandler);
    }

    public static void postJSON(boolean async, String url, JSON json, AsyncHttpResponseHandler responseHandler) {
        postJSON(async, null, url, json, responseHandler);
    }

    public static void postEntity(boolean async, String url, HttpEntity entity, String contentType, AsyncHttpResponseHandler responseHandler) {
        postEntity(async, null, url, entity, contentType, responseHandler);
    }

    public static void postEntity(boolean async, Context context, String url, HttpEntity entity, String contentType, AsyncHttpResponseHandler responseHandler) {
        if (async)
            asyncClient.post(context, url, entity, contentType, responseHandler);
        else
            syncClient.post(context, url, entity, contentType, responseHandler);
    }
    // [-] post entity

    // [+] base put
    public static void put(boolean async, String url, AsyncHttpResponseHandler responseHandler) {
        put(async, null, url, null, responseHandler);
    }

    public static void put(boolean async, String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        put(async, null, url, params, responseHandler);
    }

    public static void put(Context context, String url, AsyncHttpResponseHandler responseHandler) {
        put(false, context, url, responseHandler);
    }

    public static void put(boolean async, Context context, String url, AsyncHttpResponseHandler responseHandler) {
        put(async, context, url, null, responseHandler);
    }

    public static void put(boolean async, Context context, String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        if (async) {
            asyncClient.put(context, url, params, responseHandler);
        } else {
            syncClient.put(context, url, params, responseHandler);
        }
    }
    // [-] base put

    // [+] base delete
    public static void delete(boolean async, String url, AsyncHttpResponseHandler responseHandler) {
        delete(async, null, url, null, responseHandler);
    }

    public static void delete(boolean async, String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        delete(async, null, url, params, responseHandler);
    }

    public static void delete(Context context, String url, AsyncHttpResponseHandler responseHandler) {
        delete(false, context, url, responseHandler);
    }

    public static void delete(boolean async, Context context, String url, AsyncHttpResponseHandler responseHandler) {
        delete(async, context, url, null, responseHandler);
    }

    public static void delete(boolean async, Context context, String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        if (async) {
            asyncClient.delete(context, url, null, params, responseHandler);
        } else {
            syncClient.delete(context, url, null, params, responseHandler);
        }
    }
    // [-] base put
}

