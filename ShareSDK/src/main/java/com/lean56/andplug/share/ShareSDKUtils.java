package com.lean56.andplug.share;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.wechat.friends.Wechat;
import cn.sharesdk.wechat.moments.WechatMoments;
import com.mob.tools.utils.UIHandler;

import java.util.HashMap;

/**
 * ShareSDK Utils
 *
 * {@link cn.sharesdk.framework.ShareSDK}
 */
public class ShareSDKUtils {

    private static final String TAG = ShareSDKUtils.class.getSimpleName();

    private final static String ShareSDK_AppKey = "";

    public final static String SINAWEIBO_UID = "";
    public final static String SINAWEIBO_AppKey = "";
    private final static String SINAWEIBO_AppSecret = "";
    private final static String SINAWEIBO_RedirectUrl = "";

    public final static String WECHAT_AppId = "";
    private final static String WECHAT_AppSecret = "";

    private final static String QQ_AppId = "";
    private final static String QQ_AppKey = "";

    public enum SHARE_PLATFORM {
        Wechat(cn.sharesdk.wechat.friends.Wechat.NAME),
        WechatMoments(cn.sharesdk.wechat.moments.WechatMoments.NAME),
        QQ(cn.sharesdk.tencent.qq.QQ.NAME),
        SinaWeibo(cn.sharesdk.sina.weibo.SinaWeibo.NAME);

        private String name;

        SHARE_PLATFORM(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public static Platform getSharePlatform(Context context, SHARE_PLATFORM sharePlatform) {
        ShareSDK.initSDK(context, ShareSDK_AppKey);
        ShareSDK.setPlatformDevInfo(sharePlatform.getName(), getPlatformDevInfo(sharePlatform));

        Platform platform = ShareSDK.getPlatform(sharePlatform.getName());
        platform.setPlatformActionListener(new SharePlatformActionListener(context));
        return platform;
    }

    public static void initLoginShareSDK(Context context) {
        ShareSDK.initSDK(context, ShareSDK_AppKey);

        ShareSDK.setPlatformDevInfo(SinaWeibo.NAME, getPlatformDevInfo(SHARE_PLATFORM.SinaWeibo));
        ShareSDK.setPlatformDevInfo(QQ.NAME, getPlatformDevInfo(SHARE_PLATFORM.QQ));
        ShareSDK.setPlatformDevInfo(Wechat.NAME, getPlatformDevInfo(SHARE_PLATFORM.Wechat));
    }

    public static void initWechatShareSDK(Context context) {
        ShareSDK.initSDK(context, ShareSDK_AppKey);
        HashMap<String, Object> setting = getPlatformDevInfo(SHARE_PLATFORM.Wechat);
        ShareSDK.setPlatformDevInfo(Wechat.NAME, setting);
        ShareSDK.setPlatformDevInfo(WechatMoments.NAME, setting);
    }

    public static void iniAllShareSDK(Context context) {
        initLoginShareSDK(context);
        ShareSDK.setPlatformDevInfo(WechatMoments.NAME, getPlatformDevInfo(SHARE_PLATFORM.Wechat));
    }

    public static void removeAllAccounts() {
        removeAccount(SHARE_PLATFORM.SinaWeibo.getName());
        removeAccount(SHARE_PLATFORM.QQ.getName());
        removeAccount(SHARE_PLATFORM.Wechat.getName());
        removeAccount(SHARE_PLATFORM.WechatMoments.getName());
    }

    public static void removeAccount(String platformName) {
        try {
            Platform platform = ShareSDK.getPlatform(platformName);

            if (platform != null) {
                platform.removeAccount(true);
            }
        } catch (Exception ex) {

        }
    }

    /**
     * get platform devinfo with platform name
     * @param sharePlatform
     * @return platform setting
     */
    public static HashMap<String, Object> getPlatformDevInfo(SHARE_PLATFORM sharePlatform) {
        HashMap<String, Object> setting = new HashMap<String, Object>();
        switch (sharePlatform) {
            case Wechat:
            case WechatMoments:
                setting.put("Id", 1);
                setting.put("SortId", 1);
                setting.put("AppId", WECHAT_AppId);
                setting.put("AppSecret", WECHAT_AppSecret);
                setting.put("BypassApproval", false);
                setting.put("Enable", true);
                break;
            case QQ:
                setting.put("Id", 3);
                setting.put("SortId", 3);
                setting.put("AppId", QQ_AppId);
                setting.put("AppKey", QQ_AppKey);
                setting.put("ShareByAppClient", true);
                setting.put("Enable", true);
                break;
            case SinaWeibo:
                setting.put("Id", 2);
                setting.put("SortId", 2);
                setting.put("AppKey", SINAWEIBO_AppKey);
                setting.put("AppSecret", SINAWEIBO_AppSecret);
                setting.put("RedirectUrl", SINAWEIBO_RedirectUrl);
                setting.put("ShareByAppClient", true);
                setting.put("Enable", true);
                break;
        }
        return setting;
    }

    public static class SharePlatformActionListener implements PlatformActionListener, Handler.Callback {

        private Context context;

        public SharePlatformActionListener(Context ctx) {
            context = ctx;
        }

        // [+] PlatformActionListener, Handler.Callback
        @Override
        public void onComplete(Platform plat, int action, HashMap<String, Object> res) {
            Message msg = new Message();
            msg.arg1 = 1;
            msg.arg2 = action;
            msg.obj = plat;

            Log.d(TAG, "action listener: action: " + action + "res:" + TextUtils.join(",", res.keySet()));

            UIHandler.sendMessage(msg, this);
        }

        @Override
        public void onError(Platform plat, int action, Throwable t) {
            Log.w(TAG, t);

            Message msg = new Message();
            msg.arg1 = 2;
            msg.arg2 = action;
            msg.obj = t;

            t.printStackTrace();
            Log.d(TAG, "onError: action: " + action + ", exception: " + t.toString());
            UIHandler.sendMessage(msg, this);
        }

        @Override
        public void onCancel(Platform plat, int action) {
            Message msg = new Message();
            msg.arg1 = 3;
            msg.arg2 = action;
            msg.obj = plat;

            Log.d(TAG, "onCancel: action: " + action);
            UIHandler.sendMessage(msg, this);
        }

        String getPlatformName(Platform platform) {
            switch (platform.getId()) {
                case 1:
                    return context.getString(R.string.wechat);
                case 2:
                    return context.getString(R.string.sinaweibo);
                case 3:
                    return context.getString(R.string.qq);
            }

            return platform.getName();
        }

        @Override
        public boolean handleMessage(Message msg) {
            String title = "";
            String message = "";
            switch (msg.arg1) {
                case 1: {
                    // onComplete
                    Platform platform = (Platform) msg.obj;
                    String platformName = platform.getName();
                    title = context.getString(R.string.share_completed);
                    message = context.getString(R.string.msg_share_completed, getPlatformName(platform));
                }
                break;
                case 2: {
                    // onError
                    title = context.getString(R.string.share_failed);
                    if ("WechatClientNotExistException".equals(msg.obj.getClass().getSimpleName())) {
                        message = context.getString(R.string.wechat_client_unavailable);
                    } else if ("WechatTimelineNotSupportedException".equals(msg.obj.getClass().getSimpleName())) {
                        message = context.getString(R.string.wechat_client_unavailable);
                    } else {
                        message = context.getString(R.string.share_failed) + ": " + msg.obj.toString();
                    }
                }
                break;
                case 3: {
                    // onCancel
                    Platform plat = (Platform) msg.obj;
                    title = context.getString(R.string.share_canceled);
                    message = context.getString(R.string.share_canceled);
                }
                break;
            }

            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();

            return false;
        }
        // [-] PlatformActionListener, Handler.Callback
    }

}
