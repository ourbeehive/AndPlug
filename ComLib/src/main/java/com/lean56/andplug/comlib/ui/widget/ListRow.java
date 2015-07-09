package com.lean56.andplug.comlib.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.lean56.andplug.comlib.R;

/**
 * ListRow is used for table row
 *
 * @author Charles
 */
public class ListRow extends RelativeLayout {

    View row_icon;
    Drawable icon;

    TextView row_title;
    String title;

    TextView row_subtitle;
    String subtitle;

    View row_arrow;
    boolean arrowVisible;

    CheckBox row_checkbox;
    boolean checkboxVisible;

    public ListRow(Context context) {
        this(context, null);
    }

    public ListRow(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ListRow(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.ListRow, defStyleAttr, 0);
        checkboxVisible = ta.getBoolean(R.styleable.ListRow_row_checkbox, false);
        arrowVisible = ta.getBoolean(R.styleable.ListRow_row_arrow, true);
        title = ta.getString(R.styleable.ListRow_row_title);
        subtitle = ta.getString(R.styleable.ListRow_row_subtitle);
        icon = ta.getDrawable(R.styleable.ListRow_row_icon);
        ta.recycle();

        LayoutInflater.from(context).inflate(R.layout.item_list_row, this, true);
        row_icon = findViewById(R.id.row_icon);
        row_title = (TextView) findViewById(R.id.row_title);
        row_subtitle = (TextView) findViewById(R.id.row_subtitle);
        row_arrow = findViewById(R.id.row_arrow);
        row_checkbox = (CheckBox) findViewById(R.id.row_checkbox);
        initViews();
    }


    private void initViews() {
        if (checkboxVisible) {
            row_checkbox.setVisibility(View.VISIBLE);
        } else {
            row_checkbox.setVisibility(View.GONE);
        }

        if (arrowVisible) {
            row_arrow.setVisibility(View.VISIBLE);
        } else {
            row_arrow.setVisibility(View.GONE);
        }

        if (!TextUtils.isEmpty(title)) {
            setTitle(title);
        }

        if (!TextUtils.isEmpty(subtitle)) {
            setSubtitle(subtitle);
        } else {
            row_subtitle.setVisibility(View.GONE);
        }

        if (null != icon) {
            row_icon.setVisibility(View.VISIBLE);
            row_icon.setBackgroundDrawable(icon);
        } else {
            row_icon.setVisibility(View.GONE);
        }
    }

    public void setCheckbox(boolean checked) {
        if (null != row_checkbox && row_checkbox.getVisibility() == View.VISIBLE) {
            row_checkbox.setChecked(checked);
        }
    }

    public void setTitle(String title) {
        if (null != row_title) {
            row_title.setText(title);
        }
    }

    public void setSubtitle(String subtitle) {
        if (null != row_subtitle) {
            row_subtitle.setVisibility(View.VISIBLE);
            row_subtitle.setText(subtitle);
        }
    }

    public CheckBox getRow_checkbox() {
        return row_checkbox;
    }

    public TextView getTitleView() {
        return row_title;
    }

    public TextView getSubtitleView() {
        return row_subtitle;
    }

}
