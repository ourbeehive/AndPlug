package com.lean56.andplug.voiceiflytek;

import com.iflytek.cloud.RecognizerResult;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * RecognizerResultParser
 *
 * @author Charles(zhangchaoxu@gmail.com)
 */
public class RecognizerResultParser {

    public static HashMap<String, String> mIatResults = new LinkedHashMap();

    public static String parse(RecognizerResult results) {
        mIatResults.clear();

        String text = parseIatResult(results.getResultString(), true);

        String sn = null;
        // 读取json结果中的sn字段
        try {
            JSONObject resultJson = new JSONObject(results.getResultString());
            sn = resultJson.optString("sn");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        mIatResults.put(sn, text);

        StringBuffer resultBuffer = new StringBuffer();
        for (String key : mIatResults.keySet()) {
            resultBuffer.append(mIatResults.get(key));
        }

        return resultBuffer.toString();
    }

    public static String parseIatResult(String json, boolean firstResult) {
        StringBuffer ret = new StringBuffer();
        try {
            JSONObject joResult = new JSONObject(new JSONTokener(json));

            JSONArray words = joResult.getJSONArray("ws");
            for (int i = 0; i < words.length(); i++) {
                JSONArray items = words.getJSONObject(i).getJSONArray("cw");
                if (firstResult) {
                    // 转写结果词，默认使用第一个结果
                    JSONObject obj = items.getJSONObject(0);
                    ret.append(obj.getString("w"));
                } else {
                    // 如果需要多候选结果，解析数组其他字段
                    for(int j = 0; j < items.length(); j++)
                    {
                        JSONObject obj = items.getJSONObject(j);
                        ret.append(obj.getString("w"));
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ret.toString();
    }
}
