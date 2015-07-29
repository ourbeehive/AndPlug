package com.lean56.andplug.app.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lean56.andplug.app.R;

/**
 * ActionCompactCounterView
 * see liwushuo
 *
 * @author liwuhuo <www.liwushuo.com>
 * @author Charles
 */
public class ActionCompactCounterView extends LinearLayout implements CounterView {

    private int mCountFormatResId;
    private TextView mCountView;
    private ImageView mIconView;
    private int mCount = 0;

    public ActionCompactCounterView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ActionCompactCounterView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        createInternalView();
        mCountView = (TextView) findViewById(R.id.action_count);
        mIconView = (ImageView) findViewById(R.id.action_icon);
        TypedArray ta = context.getTheme().obtainStyledAttributes(attrs, R.styleable.ActionCompactCounterView, defStyleAttr, 0);
        mCountFormatResId = ta.getResourceId(R.styleable.ActionCompactCounterView_action_counter_format, R.string.default_number_format);
        mCountView.setText(ta.getString(R.styleable.ActionCompactCounterView_action_counter_label));
        mIconView.setImageResource(ta.getResourceId(R.styleable.ActionCompactCounterView_action_counter_icon, R.drawable.ic_action_comment));
        ta.recycle();
    }

    @Override
    public void createInternalView() {
        setOrientation(HORIZONTAL);
        setGravity(Gravity.CENTER);
        LayoutInflater.from(getContext()).inflate(R.layout.action_compact_counter, this);
    }

    @Override
    public void setCount(int count) {
        mCount = count;
        mCountView.setText(getResources().getString(mCountFormatResId, count));
    }

    @Override
    public int getCount() {
        return mCount;
    }

    @Override
    public void nyan() {
        Animation anim = AnimationUtils.loadAnimation(getContext(), R.anim.boom);
        anim.setInterpolator(new OvershootInterpolator());
        anim.setDuration((long) getResources().getInteger(android.R.integer.config_shortAnimTime));
        mIconView.startAnimation(anim);
    }

}
