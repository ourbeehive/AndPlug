package com.lean56.andplug.image.pick;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.lean56.andplug.R;
import com.lean56.andplug.image.ImageLoadUtils;

import java.util.ArrayList;

/**
 * Folder Select Adapter
 *
 * @author Charles
 */
public class FolderAdapter extends BaseAdapter {

    ImageLoadUtils mImageLoadUtils;
    private String mSelect = "";
    ArrayList<ImageInfoExtra> mFolderData = new ArrayList<>();

    public FolderAdapter(ArrayList<ImageInfoExtra> mFolderData) {
        this.mFolderData = mFolderData;
        this.mImageLoadUtils = new ImageLoadUtils();
    }

    public String getSelect() {
        return mSelect;
    }

    public void setSelect(int pos) {
        if (pos >= getCount()) {
            return;
        }

        mSelect = mFolderData.get(pos).getmName();
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mFolderData.size();
    }

    @Override
    public Object getItem(int position) {
        return mFolderData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater mInflater = LayoutInflater.from(parent.getContext());
        FoldViewHolder holder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_photo_folder, parent, false);
            holder = new FoldViewHolder();
            holder.icon = (ImageView) convertView.findViewById(R.id.iv_icon);
            holder.name = (TextView) convertView.findViewById(R.id.tv_name);
            holder.count = (TextView) convertView.findViewById(R.id.tv_count);
            holder.check = convertView.findViewById(R.id.check);
            convertView.setTag(holder);
        } else {
            holder = (FoldViewHolder) convertView.getTag();
        }

        ImageInfoExtra data = mFolderData.get(position);
        String uri = data.getPath();
        int count = data.getCount();

        holder.name.setText(data.getmName());
        holder.count.setText(String.format("%d张", count));

        mImageLoadUtils.loadPhotoImage(ImageInfo.pathAddPrefix(uri), holder.icon);

        if (data.getmName().equals(mSelect)) {
            holder.check.setVisibility(View.VISIBLE);
        } else {
            holder.check.setVisibility(View.INVISIBLE);
        }

        return convertView;
    }

    static class FoldViewHolder {
        ImageView icon;
        TextView name;
        TextView count;
        View check;
    }
}

