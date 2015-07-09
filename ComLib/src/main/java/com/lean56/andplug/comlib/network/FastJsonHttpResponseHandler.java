package com.lean56.andplug.comlib.network;

import android.util.Log;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.TextHttpResponseHandler;
import org.apache.http.Header;
import org.apache.http.HttpStatus;

/**
 * Used to intercept and handle the responses from requests made using {@link AsyncHttpClient},
 * with automatic parsing into a {@link JSONObject} or {@link JSONArray}.
 * This class is designed to be passed to get, post, put and delete requests with the
 * {@link #onSuccess(int,Header[], JSONArray)}
 * or {@link #onSuccess(int,Header[], JSONObject)}
 * methods anonymously overridden.
 *
 * Additionally, you can override the other event methods from the parent class.
 *
 * @author Charles <zhangchaoxu@gmail.com>
 */
public class FastJsonHttpResponseHandler extends TextHttpResponseHandler {

    private static final String LOG_TAG = FastJsonHttpResponseHandler.class.getSimpleName();

    /**
     * Creates new FastJsonHttpResponseHandler, with JSON String encoding UTF-8
     */
    public FastJsonHttpResponseHandler() {
        super(DEFAULT_CHARSET);
    }

    /**
     * Creates new FastJsonHttpResponseHandler with given JSON String encoding
     *
     * @param encoding String encoding to be used when parsing JSON
     */
    public FastJsonHttpResponseHandler(String encoding) {
        super(encoding);
    }

    @Override
    public void onSuccess(int statusCode, Header[] headers, String responseString) {
        onFailure(statusCode, headers, (String) responseString, new JSONException("Response cannot be parsed as JSON data"));
    }

    /**
     * Returns when request succeeds
     *
     * @param statusCode
     *            http response status line
     * @param headers
     *            response headers if any
     * @param response
     *            parsed response if any
     */
    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

    }

    /**
     * Returns when request succeeds
     *
     * @param statusCode
     *            http response status line
     * @param headers
     *            response headers if any
     * @param response
     *            parsed response if any
     */
    public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
        onFailure(statusCode, headers, response, new JSONException("JSONArray is not expected response type"));
    }

    @Override
    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
        onFailure(statusCode, headers, (JSONObject) null, throwable);
    }

    /**
     * Returns when request failed
     *
     * @param statusCode
     *            http response status line
     * @param headers
     *            response headers if any
     * @param throwable
     *            throwable describing the way request failed
     * @param errorResponse
     *            parsed response if any
     */
    public void onFailure(int statusCode, Header[] headers, JSONObject errorResponse, Throwable throwable) {

    }

    /**
     * Returns when request failed
     *
     * @param statusCode
     *            http response status line
     * @param headers
     *            response headers if any
     * @param throwable
     *            throwable describing the way request failed
     * @param errorResponse
     *            parsed response if any
     */
    public void onFailure(int statusCode, Header[] headers, JSONArray errorResponse, Throwable throwable) {
        onFailure(statusCode, headers, (JSONObject) null, throwable);
    }

    @Override
    public final void onSuccess(final int statusCode, final Header[] headers, final byte[] responseBytes) {
        if (statusCode != HttpStatus.SC_NO_CONTENT) {
            Runnable parser = new Runnable() {
                @Override
                public void run() {
                    try {
                        final Object jsonResponse = parseResponse(responseBytes);
                        postRunnable(new Runnable() {
                            @Override
                            public void run() {
                                if (jsonResponse instanceof JSONObject) {
                                    onSuccess(statusCode, headers, (JSONObject) jsonResponse);
                                } else if (jsonResponse instanceof JSONArray) {
                                    onSuccess(statusCode, headers, (JSONArray) jsonResponse);
                                } else if (jsonResponse instanceof String) {
                                    onSuccess(statusCode, headers, (String) jsonResponse);
                                } else {
                                    onFailure(statusCode, headers, (JSONObject) null, new JSONException("Unexpected response type "
                                            + jsonResponse.getClass().getName()));
                                }

                            }
                        });
                    } catch (final JSONException ex) {
                        postRunnable(new Runnable() {
                            @Override
                            public void run() {
                                onFailure(statusCode, headers, (JSONObject) null, ex);
                            }
                        });
                    }
                }
            };
            if (!getUseSynchronousMode())
                new Thread(parser).start();
            else
                // In synchronous mode everything should be run on one thread
                parser.run();
        } else {
            onSuccess(statusCode, headers, new JSONObject());
        }
    }

    @Override
    public final void onFailure(final int statusCode, final Header[] headers, final byte[] responseBytes, final Throwable throwable) {
        if (responseBytes != null) {
            Runnable parser = new Runnable() {
                @Override
                public void run() {
                    try {
                        final Object jsonResponse = parseResponse(responseBytes);
                        postRunnable(new Runnable() {
                            @Override
                            public void run() {
                                if (jsonResponse instanceof JSONObject) {
                                    onFailure(statusCode, headers, (JSONObject) jsonResponse, throwable);
                                } else if (jsonResponse instanceof JSONArray) {
                                    onFailure(statusCode, headers, (JSONArray) jsonResponse, throwable);
                                } else if (jsonResponse instanceof String) {
                                    onFailure(statusCode, headers, (String) jsonResponse, throwable);
                                } else {
                                    onFailure(statusCode, headers, (JSONObject) null, new JSONException("Unexpected response type "
                                            + jsonResponse.getClass().getName()));
                                }
                            }
                        });

                    } catch (final JSONException ex) {
                        postRunnable(new Runnable() {
                            @Override
                            public void run() {
                                onFailure(statusCode, headers, (JSONObject) null, ex);
                            }
                        });

                    }
                }
            };
            if (!getUseSynchronousMode())
                new Thread(parser).start();
            else
                // In synchronous mode everything should be run on one thread
                parser.run();
        } else {
            Log.v(LOG_TAG, "response body is null, calling onFailure(Throwable, JSONObject)");
            onFailure(statusCode, headers, (JSONObject) null, throwable);
        }
    }

    /**
     * Returns Object of type {@link JSONObject}, {@link JSONArray}, String,
     * Boolean, Integer, Long, Double or {@link JSONObject}, see
     * {@link org.json.JSONTokener#nextValue()}
     *
     * @param responseBody
     *            response bytes to be assembled in String and parsed as JSON
     * @return Object
     *            parsedResponse
     * @throws JSONException
     *            exception if thrown while parsing JSON
     */
    protected Object parseResponse(byte[] responseBody) throws JSONException {
        if (null == responseBody)
            return null;
        Object result = null;
        // trim the string to prevent start with blank, and test if the string
        // is valid JSON, because the parser don't do this :(. If JSON is not
        // valid this will return null
        String jsonString = getResponseString(responseBody, getCharset());
        if (jsonString != null) {
            jsonString = jsonString.trim();
            if (jsonString.startsWith("{") || jsonString.startsWith("[")) {
                // result = new JSONTokener(jsonString).nextValue();
                result = JSON.parse(jsonString);
            }
        }
        if (result == null) {
            result = jsonString;
        }
        return result;
    }
}
