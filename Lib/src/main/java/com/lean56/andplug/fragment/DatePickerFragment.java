package com.lean56.andplug.fragment;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.TextUtils;
import android.widget.DatePicker;
import com.lean56.andplug.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Date Picker Fragment
 * offers date pick dialog
 *
 * @author chaochen@coding
 * @author Charles
 */
public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    public static final String PARAM_MAX_TODAY = "PARAM_MAX_TODAY";
    public static final String PARAM_DATA = "PARAM_DATA";

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
    // fuck XIAOMI : onDataSet will be called no matter which clicked in MIUI
    private DateSet mDateSet;

    @Override
    public void onAttach(Activity activity) {
        if (activity instanceof DateSet) {
            mDateSet = (DateSet) activity;
        }
        super.onAttach(activity);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // get date amd max today from param
        String dateString = getArguments().getString(PARAM_DATA);
        if (TextUtils.isEmpty(dateString)) {
            dateString = sdf.format(Calendar.getInstance().getTimeInMillis());
        }
        boolean maxToday = getArguments().getBoolean(PARAM_MAX_TODAY, false);

        String[] date = dateString.split("-");
        int year = Integer.valueOf(date[0]);
        int month = Integer.valueOf(date[1]) - 1;
        int day = Integer.valueOf(date[2]);

        // init date pick dialog
        final DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), this, year, month, day);
        if (maxToday) {
            datePickerDialog.getDatePicker().setMaxDate(Calendar.getInstance().getTimeInMillis());
        }

        if (getArguments().getBoolean("clear", false)) {
            datePickerDialog.setButton(DialogInterface.BUTTON_NEUTRAL, getString(R.string.clear), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    mDateSet.dateSetResult("", true);
                    dialog.cancel();
                }
            });
        }

        datePickerDialog.setButton(DialogInterface.BUTTON_NEGATIVE, getString(R.string.cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        datePickerDialog.setButton(DialogInterface.BUTTON_POSITIVE, getString(R.string.confirm), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                DatePicker datePicker = datePickerDialog.getDatePicker();
                dateSet(datePicker.getYear(), datePicker.getMonth(), datePicker.getDayOfMonth());
                dialog.cancel();

            }
        });

        return datePickerDialog;
    }

    // replace of onDateSet
    private void dateSet(int year, int monthOfYear, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(year, monthOfYear, dayOfMonth);
        if (mDateSet != null) {
            mDateSet.dateSetResult(sdf.format(c.getTimeInMillis()), false);
        }
    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        // fuck XIAOMI again
    }

    public interface DateSet {
        void dateSetResult(String date, boolean clear);
    }

}
