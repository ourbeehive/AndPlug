package io.rong.imkit.msg;

import android.os.Parcel;
import android.util.Log;
import io.rong.imlib.MessageTag;
import io.rong.imlib.model.MessageContent;
import io.rong.message.utils.RLog;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

/**
 * PayMessage
 *
 * @author Charles
 */
@MessageTag(value = "app:pay", flag = MessageTag.ISCOUNTED | MessageTag.ISPERSISTED)
public class PayMessage extends MessageContent {
    private String content;//消息属性，可随意定义

    public PayMessage(byte[] data) {
        String jsonStr = null;

        try {
            jsonStr = new String(data, "UTF-8");
        } catch (UnsupportedEncodingException e1) {

        }

        try {
            JSONObject jsonObj = new JSONObject(jsonStr);

            if (jsonObj.has("content"))
                content = jsonObj.optString("content");

        } catch (JSONException e) {
            RLog.e(this, "JSONException", e.getMessage());
        }
    }

    @Override
    public byte[] encode() {
        JSONObject jsonObj = new JSONObject();

        try {
            jsonObj.put("content", "这是一条消息内容");
        } catch (JSONException e) {
            Log.e("JSONException", e.getMessage());
        }

        try {
            return jsonObj.toString().getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return null;
    }

    // [+] Parcelable
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }
    // [-] Parcelable
}
