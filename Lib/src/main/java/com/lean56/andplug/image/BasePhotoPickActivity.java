package com.lean56.andplug.image;

import android.app.Activity;
import android.app.LoaderManager;
import android.content.ActivityNotFoundException;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.GridView;
import android.widget.Toast;
import com.lean56.andplug.BaseApplication;
import com.lean56.andplug.R;
import com.lean56.andplug.activity.BaseActivity;
import com.lean56.andplug.common.ResultCodes;
import com.lean56.andplug.image.pick.AllPhotoAdapter;
import com.lean56.andplug.image.pick.GridPhotoAdapter;
import com.lean56.andplug.image.pick.ImageInfo;

import java.util.ArrayList;

/**
 * PhotoPickActivity
 * offers photo pick from camera and album
 *
 * @author Charles
 */
public class BasePhotoPickActivity extends BaseActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private final static String TAG = BasePhotoPickActivity.class.getSimpleName();

    public static final String EXTRA_MAX = "EXTRA_MAX";
    public static final String EXTRA_PICKED = "EXTRA_PICKED";
    private static final String RESTORE_FILEURI = "fileUri";

    protected ArrayList<ImageInfo> mPickData = new ArrayList<>();
    private Uri fileUri;

    private GridPhotoAdapter mPhotoAdapter;
    private int mMaxPick = 1;

    int mFolderId = 0;
    /**
     * if selected the first one
     */
    protected boolean isAllPhotoMode() {
        return mFolderId == 0;
    }

    // ui ref
    MenuItem mSendMenu;
    protected GridView mPhotoGrid;

    @Override
    protected int getContentView() {
        return R.layout.photo_pick;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // parse extra param
        mMaxPick = getIntent().getIntExtra(EXTRA_MAX, 1);
        Object extraPicked = getIntent().getSerializableExtra(EXTRA_PICKED);

        if (extraPicked != null) {
            mPickData = (ArrayList<ImageInfo>) extraPicked;
        }

        // init views
        mPhotoGrid = (GridView) findViewById(R.id.gv_photo);
        initPhotoList();
    }

    /**
     * init all photos
     */
    private void initPhotoList() {
        getLoaderManager().initLoader(0, null, this);
    }

    // [-] LoaderManager.LoaderCallbacks
    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String where = "";

        final String[] photoProjection = {MediaStore.Images.ImageColumns._ID,
                MediaStore.Images.ImageColumns.DATA,
                MediaStore.Images.ImageColumns.BUCKET_DISPLAY_NAME,
                MediaStore.Images.ImageColumns.WIDTH,
                MediaStore.Images.ImageColumns.HEIGHT
        };
        return new CursorLoader(this, MediaStore.Images.Media.EXTERNAL_CONTENT_URI, photoProjection, where, null, MediaStore.MediaColumns.DATE_ADDED + " DESC");
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if (isAllPhotoMode()) {
            mPhotoAdapter = new AllPhotoAdapter(this, data, false, this);
        } else {
            mPhotoAdapter = new GridPhotoAdapter(this, data, false, this);
        }
        mPhotoGrid.setAdapter(mPhotoAdapter);
        //mPhotoGrid.setOnItemClickListener(mPhotoItemClick);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mPhotoAdapter.swapCursor(null);
    }

    // [-] LoaderManager.LoaderCallbacks

    /**
     * external call
     */
    public void clickPhotoItem(View v) {
        GridViewCheckTag tag = (GridViewCheckTag) v.getTag();
        if (((CheckBox) v).isChecked()) {
            if (mPickData.size() >= mMaxPick) {
                ((CheckBox) v).setChecked(false);
                String s = String.format("最多只能选择%d张", mMaxPick);
                Toast.makeText(this, s, Toast.LENGTH_LONG).show();
                return;
            }

            addPicked(tag.path);
            tag.iconFore.setVisibility(View.VISIBLE);
        } else {
            removePicked(tag.path);
            tag.iconFore.setVisibility(View.INVISIBLE);
        }

        updatePickCount();
    }

    /**
     * external call
     */
    public void camera() {
        // check camera permission first
        if (checkCallingOrSelfPermission("android.permission.CAMERA") == PackageManager.PERMISSION_DENIED) {
            BaseApplication.showToast(R.string.camera_permission_exception);
        } else {
            if(!mPickData.isEmpty()){
               mPickData.remove(0);
            }
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            fileUri = CameraPhotoUtils.getOutputMediaFileUri();
            intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
            try {
                startActivityForResult(intent, ResultCodes.PHOTO_PICK_CAMERA);
            } catch (ActivityNotFoundException e) {
                BaseApplication.showToast(R.string.camera_exception);
            }
        }
    }

    protected void send() {
        if (mPickData.isEmpty()) {
            setResult(Activity.RESULT_CANCELED);
        } else {
            Intent intent = new Intent();
            intent.putExtra("data", mPickData);
            setResult(Activity.RESULT_OK, intent);
        }

        finish();
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        fileUri = savedInstanceState.getParcelable(RESTORE_FILEURI);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        if (fileUri != null) {
            outState.putParcelable(RESTORE_FILEURI, fileUri);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == ResultCodes.PHOTO_PICK_SELECTED) {
                mPickData = (ArrayList<ImageInfo>) data.getSerializableExtra("data");
                mPhotoAdapter.notifyDataSetChanged();
                if (data.getBooleanExtra("send", false)) {
                    send();
                }
            } else if (requestCode == ResultCodes.PHOTO_PICK_CAMERA) {
                mPickData.add(new ImageInfo(fileUri.toString()));
                send();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    protected void addPicked(String path) {
        if (!isPicked(path)) {
            mPickData.add(new ImageInfo(path));
        }
    }

    public boolean isPicked(String path) {
        for (ImageInfo item : mPickData) {
            if (item.path.equals(path)) {
                return true;
            }
        }

        return false;
    }

    protected void removePicked(String path) {
        for (int i = 0; i < mPickData.size(); ++i) {
            if (mPickData.get(i).path.equals(path)) {
                mPickData.remove(i);
                return;
            }
        }
    }

    protected void updatePickCount() {

    }

    public static class GridViewCheckTag {
        public View iconFore;
        public CheckBox check;
        public String path = "";

        public GridViewCheckTag(View iconFore, CheckBox check) {
            this.iconFore = iconFore;
            this.check = check;
        }
    }

}
