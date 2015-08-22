package com.lean56.andplug.image.pick;

import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.view.ViewGroup;
import com.lean56.andplug.BaseApplication;
import com.lean56.andplug.R;
import com.lean56.andplug.image.BasePhotoPickActivity;

/**
 * All Photo Adapter
 * and the first on is take photo
 *
 * @author Charles
 */
public class AllPhotoAdapter extends GridPhotoAdapter {

    public AllPhotoAdapter(Context context, Cursor c, boolean autoRequery, BasePhotoPickActivity activity) {
        super(context, c, autoRequery, activity);
    }

    @Override
    public int getCount() {
        return super.getCount() + 1;
    }

    @Override
    public Object getItem(int position) {
        if (position > 0) {
            return super.getItem(position - 1);
        } else {
            return super.getItem(position);
        }
    }

    @Override
    public long getItemId(int position) {
        if (position > 0) {
            return super.getItemId(position - 1);
        } else {
            return -1;
        }
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        if (position > 0) {
            return super.getDropDownView(position - 1, convertView, parent);
        } else {
            return getView(position, convertView, parent);
        }
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        return position == 0 ? 0 : 1;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (position > 0) {
            return super.getView(position - 1, convertView, parent);
        } else {
            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.item_photo_pick_camera, parent, false);
                convertView.getLayoutParams().height = BaseApplication.sWidthPix / 3;
                convertView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mActivity.camera();
                    }
                });
            }
            return convertView;
        }
    }
}
