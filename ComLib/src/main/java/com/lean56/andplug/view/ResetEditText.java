package com.lean56.andplug.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.CycleInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.EditText;
import com.lean56.andplug.R;

/**
 * EditText with reset img on the right
 * see {http://blog.csdn.net/xiaanming/article/details/11066685}
 *
 * @auther xiaanming
 * @auther Charles
 */
public class ResetEditText extends EditText {

    /**
     * clear icon drawable
     */
    private Drawable resetIcon;

    /**
     * focus state of EditText
     */
    private boolean focused;

    public ResetEditText(Context context) {
        this(context, null);
    }

    public ResetEditText(Context context, AttributeSet attrs) {
        this(context, attrs, android.R.attr.editTextStyle);
    }

    public ResetEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    /**
     * init reset icon of the ResetEditText
     */
    private void init() {
        // get clear drawable
        resetIcon = getCompoundDrawables()[2];
        if (null == resetIcon) {
            resetIcon = getResources().getDrawable(R.drawable.abc_ic_clear_mtrl_alpha);
        }
        // set clear drawable
        resetIcon.setBounds(0, 0, resetIcon.getIntrinsicWidth(), resetIcon.getIntrinsicHeight());
        setClearIconVisible(false); // hidden the clear icon in default
        // set the focus change listener

        setOnFocusChangeListener(new View.OnFocusChangeListener() {
            /**
             * hidden/show the clear icon with the text length when the focus change
             */
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                focused = hasFocus;
                setClearIconVisible(hasFocus ? getText().length() > 0 : false);
            }
        });
        // set the text changed listener
        addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            /**
             * hidden/show the clear icon in the text changed
             */
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (focused) {
                    setClearIconVisible(s.length() > 0);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    /**
     * show/hidden the clear icon
     *
     * @param visible
     */
    protected void setClearIconVisible(boolean visible) {
        Drawable right = visible ? resetIcon : null;
        setCompoundDrawables(getCompoundDrawables()[0], getCompoundDrawables()[1], right, getCompoundDrawables()[3]);
    }


    /**
     * simulate onClickListener with the click position
     * as onClickListener was unavailable in EditText
     * <p/>
     * the position is from
     * (width of EditText - total right padding + width of clear icon)
     * to
     * (width of EditTex - right padding of clear icon)
     * <p/>
     * and we do not consider vertical orientation
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP) {
            if (getCompoundDrawables()[2] != null) {
               /* boolean touchable = event.getX() > (getWidth() - getTotalPaddingRight())
                        && event.getX() < (getWidth() - getPaddingRight());*/
                boolean touchable = event.getX() > (getWidth() - (getTotalPaddingRight() - getCompoundDrawablePadding()))
                        && event.getX() < (getWidth() - getPaddingRight());

                if (touchable) {
                    this.setText("");
                }
            }
        }
        return super.onTouchEvent(event);
    }

    /**
     * shake the EditText
     *
     * @param durationMillis Duration in milliseconds
     * @param cycles         shark times
     */
    public void shakeAnimation(long durationMillis, int cycles) {
        Animation anim = new TranslateAnimation(0f, 10f, 0f, 0f);
        anim.setInterpolator(new CycleInterpolator(cycles));
        anim.setDuration(durationMillis);
        startAnimation(anim);
    }

    public void shakeAnimation() {
        shakeAnimation(500, 3);
    }
}

