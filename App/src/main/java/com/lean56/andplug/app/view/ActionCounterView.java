package com.lean56.andplug.app.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.widget.TextView;

import com.lean56.andplug.app.R;

/**
 * ActionCounterView
 * see liwushuo
 *
 * @author liwuhuo <www.liwushuo.com>
 * @author Charles
 */
public class ActionCounterView extends ActionCompactCounterView {

    public ActionCounterView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ActionCounterView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TextView label = (TextView) findViewById(R.id.action_label);
        TypedArray ta = context.getTheme().obtainStyledAttributes(attrs, R.styleable.ActionCompactCounterView, defStyleAttr, 0);
        label.setText(ta.getString(R.styleable.ActionCompactCounterView_action_counter_label));
        ta.recycle();
    }

    @Override
    public void createInternalView() {
        setOrientation(VERTICAL);
        setGravity(Gravity.CENTER);
        LayoutInflater.from(getContext()).inflate(R.layout.action_counter, this);
    }

}
