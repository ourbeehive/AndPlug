package com.lean56.andplug.comlib.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.lean56.andplug.comlib.R;

/**
 * Exception View
 *
 * @author Charles <zhangchaoxu@gmail.com>
 */
public class ExceptionView extends RelativeLayout {

    /**
     * TypedArray Value
     */
    private String mMsg;
    private Drawable mIcon;
    private String mActionBtnTitle;
    private boolean mActionBtnShown;

    /**
     * Layout Res
     */
    private ImageView mIconImage;
    private TextView mMsgText;
    private Button mActionBtn;

    private boolean isShown = false;

    public ExceptionView(Context context) {
        this(context, null);
    }

    public ExceptionView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ExceptionView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.ExceptionView, defStyleAttr, 0);
        mMsg = ta.getString(R.styleable.ExceptionView_exp_msg);
        mIcon = ta.getDrawable(R.styleable.ExceptionView_exp_icon);
        mActionBtnTitle = ta.getString(R.styleable.ExceptionView_exp_action_btn_title);
        mActionBtnShown = ta.getBoolean(R.styleable.ExceptionView_exp_action_btn_shown, true);
        ta.recycle();

        LayoutInflater.from(context).inflate(R.layout.view_exception, this, true);
        mIconImage = (ImageView) findViewById(R.id.iv_exception_icon);
        mMsgText = (TextView) findViewById(R.id.tv_exception_msg);
        mActionBtn = (Button) findViewById(R.id.btn_exception_action);
        initViews();

        this.setVisibility(View.GONE);
    }


    private void initViews() {
        if (null != mIcon) {
            mIconImage.setImageDrawable(mIcon);
        }

        if (!TextUtils.isEmpty(mMsg)) {
            mMsgText.setText(mMsg);
        }

        if (!TextUtils.isEmpty(mActionBtnTitle)) {
            mActionBtn.setText(mActionBtnTitle);
        }

        if (!mActionBtnShown) {
            mActionBtn.setVisibility(View.GONE);
        }
    }

    public void setMsg(CharSequence msg) {
        if (mMsgText.getVisibility() == View.VISIBLE) {
            mMsgText.setText(msg);
        }
    }

    public void setIcon(int resId) {
        if (mIconImage.getVisibility() == View.VISIBLE) {
            mIconImage.setImageResource(resId);
        }
    }

    public void setIcon(Drawable drawable) {
        if (mIconImage.getVisibility() == View.VISIBLE) {
            mIconImage.setImageDrawable(drawable);
        }
    }

    public void setAction(OnClickListener clickListener) {
        if(mActionBtn.getVisibility() != View.VISIBLE) {
            mActionBtn.setVisibility(View.VISIBLE);
        }
        mActionBtn.setOnClickListener(clickListener);
    }

    public void show() {
        show(false, null);
    }

    public void show(boolean animate) {
        Animation fadeIn = new AlphaAnimation(0, 1);
        fadeIn.setInterpolator(new DecelerateInterpolator());
        fadeIn.setDuration(200);
        show(animate, fadeIn);
    }

    public void show(Animation anim) {
        show(true, anim);
    }

    private void show(boolean animate, Animation anim) {
        if (animate) {
            this.startAnimation(anim);
        }
        this.setVisibility(View.VISIBLE);
        isShown = true;
    }

    public void hide() {
        hide(false, null);
    }

    public void hide(boolean animate) {
        Animation fadeOut = new AlphaAnimation(1, 0);
        fadeOut.setInterpolator(new AccelerateInterpolator());
        fadeOut.setDuration(200);
        hide(animate, fadeOut);
    }

    public void hide(Animation anim) {
        hide(true, anim);
    }

    private void hide(boolean animate, Animation anim) {
        this.setVisibility(View.GONE);
        if (animate) {
            this.startAnimation(anim);
        }
        isShown = false;
    }

    /**
     * Is this exception view currently visible in the UI
     */
    @Override
    public boolean isShown() {
        return isShown;
    }

}
