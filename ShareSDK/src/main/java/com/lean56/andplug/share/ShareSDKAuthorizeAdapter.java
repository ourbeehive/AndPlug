package com.lean56.andplug.share;

import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.CheckedTextView;
import android.widget.LinearLayout;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.framework.authorize.AuthorizeAdapter;
import cn.sharesdk.sina.weibo.SinaWeibo;

import java.util.HashMap;

/**
 * {@link cn.sharesdk.framework.authorize.AuthorizeAdapter}
 * <p/>
 * follow official sinaweibo after callback success
 */
public class ShareSDKAuthorizeAdapter extends AuthorizeAdapter implements PlatformActionListener {

    private final static String TAG = ShareSDKAuthorizeAdapter.class.getSimpleName();

    private CheckedTextView followCheckedText;
    private PlatformActionListener backListener;
    private boolean stopFinish;

    @Override
    public void onCreate() {
        // hide ShareSDK Logo on the right of title bar
        hideShareSDKLogo();
        String platName = getPlatformName();
        if (SinaWeibo.NAME.equals(platName)) {
            initUi();
            interceptPlatformActionListener(platName);
        }
    }

    private void initUi() {
        followCheckedText = new CheckedTextView(getActivity());
        try {
            followCheckedText.setBackgroundColor(getActivity().getResources().getColor(android.R.color.white));
        } catch (Throwable t) {
            Log.w(TAG, t);
        }
        followCheckedText.setChecked(true);
        int dp_10 = com.mob.tools.utils.R.dipToPx(getActivity(), 10);
        followCheckedText.setCompoundDrawablePadding(dp_10);
        followCheckedText.setCompoundDrawablesWithIntrinsicBounds(R.drawable.bg_checkbox, 0, 0, 0);
        followCheckedText.setGravity(Gravity.CENTER_VERTICAL);
        followCheckedText.setPadding(dp_10, dp_10, dp_10, dp_10);
        followCheckedText.setText(R.string.follow_us);
        followCheckedText.setTextColor(0xff909090);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        followCheckedText.setLayoutParams(lp);
        LinearLayout llBody = (LinearLayout) getBodyView().getChildAt(0);
        llBody.addView(followCheckedText);
        followCheckedText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckedTextView ctv = (CheckedTextView) v;
                ctv.setChecked(!ctv.isChecked());
            }
        });

        followCheckedText.measure(0, 0);
        int height = followCheckedText.getMeasuredHeight();
        TranslateAnimation animShow = new TranslateAnimation(
                Animation.RELATIVE_TO_SELF, 0,
                Animation.RELATIVE_TO_SELF, 0,
                Animation.ABSOLUTE, height,
                Animation.ABSOLUTE, 0);
        animShow.setDuration(1000);
        getWebBody().startAnimation(animShow);
        followCheckedText.startAnimation(animShow);
    }

    private void interceptPlatformActionListener(String platName) {
        Platform plat = ShareSDK.getPlatform(platName);
        // ���ݴ�ǰ���õ��¼�������
        backListener = plat.getPlatformActionListener();
        // �����µļ�������ʵ���¼�����
        plat.setPlatformActionListener(this);
    }

    @Override
    public void onError(Platform plat, int action, Throwable t) {
        if (action == Platform.ACTION_AUTHORIZING) {
            // ��Ȩʱ����������
            plat.setPlatformActionListener(backListener);
            if (backListener != null) {
                backListener.onError(plat, action, t);
            }
        } else {
            // ��עʱ��������
            plat.setPlatformActionListener(backListener);
            if (backListener != null) {
                backListener.onComplete(plat, Platform.ACTION_AUTHORIZING, null);
            }
        }
    }

    @Override
    public void onComplete(Platform platform, int action, HashMap<String, Object> res) {
        if (action == Platform.ACTION_FOLLOWING_USER) {
            // ������Ȩ�Ժ����κ�����
            platform.setPlatformActionListener(backListener);
            if (backListener != null) {
                backListener.onComplete(platform, Platform.ACTION_AUTHORIZING, null);
            }
        } else if (followCheckedText.isChecked()) {
            // ��Ȩ�ɹ���ִ�й�ע
            platform.followFriend(ShareSDKUtils.SINAWEIBO_UID);
        } else {
            // ���û�б��Ϊ����Ȩ����ע����ֱ�ӷ���
            platform.setPlatformActionListener(backListener);
            if (backListener != null) {
                // ��ע�ɹ�Ҳֻ�ǵ�����Ȩ�ɹ�����
                backListener.onComplete(platform, Platform.ACTION_AUTHORIZING, null);
            }
        }
    }

    @Override
    public void onCancel(Platform plat, int action) {
        plat.setPlatformActionListener(backListener);
        if (action == Platform.ACTION_AUTHORIZING) {
            // ��Ȩǰȡ��
            if (backListener != null) {
                backListener.onCancel(plat, action);
            }
        } else {
            // ������Ȩ�Ժ����κ�����
            if (backListener != null) {
                backListener.onComplete(plat, Platform.ACTION_AUTHORIZING, null);
            }

        }
    }

    @Override
    public void onResize(int w, int h, int oldw, int oldh) {
        if (followCheckedText != null) {
            if (oldh - h > 100) {
                followCheckedText.setVisibility(View.GONE);
            } else {
                followCheckedText.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public boolean onFinish() {
        return stopFinish;
    }

}
