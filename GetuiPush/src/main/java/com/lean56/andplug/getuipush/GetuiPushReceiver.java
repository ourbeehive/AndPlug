package com.lean56.andplug.getuipush;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import com.igexin.sdk.PushConsts;

/**
 * Message Push Receiver Powered by Getui
 * see {}
 *
 * @author Charles(zhangchaoxu@gmail.com)
 */
public abstract class GetuiPushReceiver extends BroadcastReceiver {

    private static final String TAG = GetuiPushReceiver.class.getSimpleName();

    @Override
    public void onReceive(final Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        Log.d(TAG, "onReceive() action=" + bundle.getInt("action"));
        switch (bundle.getInt(PushConsts.CMD_ACTION)) {
            case PushConsts.GET_MSG_DATA:
                // get payload data
                byte[] payload = bundle.getByteArray("payload");
                String taskid = bundle.getString("taskid");
                String messageid = bundle.getString("messageid");
                Log.d(TAG, "taskid=" + taskid + "--messageid=" + messageid);
                onGetMsgData(context, payload, taskid, messageid);
                break;
            case PushConsts.GET_CLIENTID:
                String cid = bundle.getString("clientid");
                Log.d(TAG, cid);
                onGetClientId(cid);
                break;
            case PushConsts.THIRDPART_FEEDBACK:
                /*String appid = bundle.getString("appid");
                String taskid = bundle.getString("taskid");
                String actionid = bundle.getString("actionid");
                String result = bundle.getString("result");
                long timestamp = bundle.getLong("timestamp");

                Log.d("GetuiSdkDemo", "appid = " + appid);
                Log.d("GetuiSdkDemo", "taskid = " + taskid);
                Log.d("GetuiSdkDemo", "actionid = " + actionid);
                Log.d("GetuiSdkDemo", "result = " + result);
                Log.d("GetuiSdkDemo", "timestamp = " + timestamp);*/
                onThirdpardFeedback();
                break;
            default:
                break;
        }
    }

    /**
     * get payload data
     */
    protected abstract void onGetMsgData(Context context, byte[] payload, String taskid, String messageid);

    /**
     * get ClientID(CID)
     * try to bind cid(getui) and uid(sys)
     * see {http://docs.getui.com/pages/viewpage.action?pageId=590469}
     */
    protected abstract void onGetClientId(String cid);

    /**
     * set feedback
     * see {http://docs.getui.com/pages/viewpage.action?pageId=591826}
     */
    protected abstract void onThirdpardFeedback();

}
