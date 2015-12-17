package com.lean56.andplug.image.adapter;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import com.lean56.andplug.BaseApplication;
import com.lean56.andplug.R;
import com.lean56.andplug.image.BasePhotoPickActivity;
import com.lean56.andplug.image.ImageLoadUtils;
import com.lean56.andplug.image.entity.ImageInfo;

/**
 * GridPhotoAdapter
 *
 * @author Charles
 */
public class GridPhotoAdapter extends CursorAdapter {

    final int itemHeight, itemWidth;
    LayoutInflater mInflater;
    BasePhotoPickActivity mActivity;
    ImageLoadUtils mImageLoadUtils;

    public GridPhotoAdapter(Context context, Cursor c, boolean autoRequery, BasePhotoPickActivity activity) {
        super(context, c, autoRequery);
        mInflater = LayoutInflater.from(context);
        mActivity = activity;
        mImageLoadUtils = new ImageLoadUtils();

        int spacePix = context.getResources().getDimensionPixelSize(R.dimen.space_photo_pick_grid);
        itemHeight = itemWidth = (BaseApplication.sWidthPix - spacePix * 4) / 3;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View convertView = mInflater.inflate(R.layout.item_photo_pick_grid, parent, false);
        ViewGroup.LayoutParams layoutParams = convertView.getLayoutParams();
        layoutParams.height = itemHeight;
        layoutParams.width = itemWidth;
        convertView.setLayoutParams(layoutParams);

        GridViewHolder holder = new GridViewHolder();
        holder.icon = (ImageView) convertView.findViewById(R.id.icon);
        holder.iconFore = (ImageView) convertView.findViewById(R.id.iconFore);
        holder.check = (CheckBox) convertView.findViewById(R.id.check);
        BasePhotoPickActivity.GridViewCheckTag checkTag = new BasePhotoPickActivity.GridViewCheckTag(holder.iconFore, holder.check);
        holder.check.setTag(checkTag);
        holder.check.setOnClickListener(mClickItem);
        convertView.setTag(holder);

        ViewGroup.LayoutParams iconParam = holder.icon.getLayoutParams();
        iconParam.width = itemWidth;
        iconParam.height = itemHeight;
        holder.icon.setLayoutParams(iconParam);

        return convertView;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        GridViewHolder holder;
        holder = (GridViewHolder) view.getTag();

        String path = ImageInfo.pathAddPrefix(cursor.getString(1));
        mImageLoadUtils.loadPostImage(path, holder.icon);

        ((BasePhotoPickActivity.GridViewCheckTag) holder.check.getTag()).path = path;
        boolean picked = mActivity.isPicked(path);
        holder.check.setChecked(picked);
        holder.iconFore.setVisibility(picked ? View.VISIBLE : View.INVISIBLE);
    }

    View.OnClickListener mClickItem = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mActivity.clickPhotoItem(v);
        }
    };

    static class GridViewHolder {
        ImageView icon;
        ImageView iconFore;
        CheckBox check;
    }
}