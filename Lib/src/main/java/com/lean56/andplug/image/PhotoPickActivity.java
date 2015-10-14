package com.lean56.andplug.image;

import android.app.Activity;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.*;
import com.lean56.andplug.R;
import com.lean56.andplug.common.ResultCodes;
import com.lean56.andplug.image.pick.*;
import com.nostra13.universalimageloader.core.DisplayImageOptions;

import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
 * PhotoPickActivity
 * offers photo pick from camera and album
 *
 * @author Charles
 */
public class PhotoPickActivity extends BasePhotoPickActivity {

    private final static String TAG = PhotoPickActivity.class.getSimpleName();

    public static final String EXTRA_MAX = "EXTRA_MAX";
    public static final String EXTRA_PICKED = "EXTRA_PICKED";
    private static final String RESTORE_FILEURI = "fileUri";

    protected ArrayList<ImageInfo> mPickData = new ArrayList<>();
    private Uri fileUri;

    private FolderAdapter mFolderAdapter;
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
    private TextView mFoldNameText;
    private View mFoldSelectView;
    private ListView mFoldList;
    private GridView mPhotoGrid;
    private TextView mPreviewText;

    /**
     * handle photo click
     */
    GridView.OnItemClickListener mPhotoItemClick = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Intent intent = new Intent(PhotoPickActivity.this, PhotoPickDetailActivity.class);

            intent.putExtra(PhotoPickDetailActivity.PICK_DATA, mPickData);
            intent.putExtra(PhotoPickDetailActivity.EXTRA_MAX, mMaxPick);
            String folderParam = "";
            if (isAllPhotoMode()) {
                // the first on is take photo from camera
                intent.putExtra(PhotoPickDetailActivity.PHOTO_BEGIN, position - 1);
            } else {
                intent.putExtra(PhotoPickDetailActivity.PHOTO_BEGIN, position);
                folderParam = mFolderAdapter.getSelect();
            }
            intent.putExtra(PhotoPickDetailActivity.FOLDER_NAME, folderParam);
            startActivityForResult(intent, ResultCodes.PHOTO_PICK_SELECTED);
        }
    };

    /**
     * handle folder click
     */
    private ListView.OnItemClickListener mFolderItemClick = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            mFolderAdapter.setSelect((int) id);
            String folderName = mFolderAdapter.getSelect();
            mFoldNameText.setText(folderName);
            showFolderList(false);

            if (mFolderId != position) {
                getLoaderManager().destroyLoader(mFolderId);
                mFolderId = position;
            }
            getLoaderManager().initLoader(mFolderId, null, PhotoPickActivity.this);
        }
    };

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
        mFoldList = (ListView) findViewById(R.id.lv_fold);
        mFoldSelectView = findViewById(R.id.layout_fold_select);
        mFoldSelectView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleFolderSelect(v);
            }
        });
        mFoldNameText = (TextView) findViewById(R.id.tv_fold_name);
        mFoldNameText.setText(R.string.all_photos);

        findViewById(R.id.view_select_fold).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleFolderSelect(v);
            }
        });

        mPreviewText = (TextView) findViewById(R.id.tv_preview);
        mPreviewText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handlePreview(v);
            }
        });

        // init data
        initFolderList();
        initPhotoList();
    }

    /**
     * init all folders
     */
    private void initFolderList() {
        final String[] folderProjection = {MediaStore.Images.ImageColumns._ID, MediaStore.Images.ImageColumns.DATA, MediaStore.Images.ImageColumns.BUCKET_DISPLAY_NAME};

        LinkedHashMap<String, Integer> mNames = new LinkedHashMap<>();
        LinkedHashMap<String, ImageInfo> mData = new LinkedHashMap<>();
        Cursor cursor = getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, folderProjection, "", null, MediaStore.MediaColumns.DATE_ADDED + " DESC");

        while (cursor.moveToNext()) {
            String name = cursor.getString(2);
            if (!mNames.containsKey(name)) {
                mNames.put(name, 1);
                ImageInfo imageInfo = new ImageInfo(cursor.getString(1));
                mData.put(name, imageInfo);
            } else {
                int newCount = mNames.get(name) + 1;
                mNames.put(name, newCount);
            }
        }

        ArrayList<ImageInfoExtra> mFolderData = new ArrayList<>();
        if (cursor.moveToFirst()) {
            ImageInfo imageInfo = new ImageInfo(cursor.getString(1));
            int allImagesCount = cursor.getCount();
            mFolderData.add(new ImageInfoExtra(getString(R.string.all_photos), imageInfo, allImagesCount));
        }

        for (String item : mNames.keySet()) {
            ImageInfo info = mData.get(item);
            Integer count = mNames.get(item);
            mFolderData.add(new ImageInfoExtra(item, info, count));
        }
        cursor.close();

        mFolderAdapter = new FolderAdapter(mFolderData);
        mFoldList.setAdapter(mFolderAdapter);
        mFoldList.setOnItemClickListener(mFolderItemClick);
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
        if (!isAllPhotoMode()) {
            String select = ((FolderAdapter) mFoldList.getAdapter()).getSelect();
            where = String.format("%s='%s'", MediaStore.Images.ImageColumns.BUCKET_DISPLAY_NAME, select);
        }

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
        mPhotoGrid.setOnItemClickListener(mPhotoItemClick);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mPhotoAdapter.swapCursor(null);
    }

    // [-] LoaderManager.LoaderCallbacks

    /**
     * handle folder select button and folder list click
     */
    public void handleFolderSelect(View v) {
        showFolderList(mFoldSelectView.getVisibility() != View.VISIBLE);
    }

    /**
     * handle preview click
     */
    public void handlePreview(View v) {
        if (mPickData.size() == 0) {
            return;
        }

        Intent intent = new Intent(PhotoPickActivity.this, PhotoPickDetailActivity.class);
        intent.putExtra(PhotoPickDetailActivity.FOLDER_NAME, mFolderAdapter.getSelect());
        intent.putExtra(PhotoPickDetailActivity.PICK_DATA, mPickData);
        intent.putExtra(PhotoPickDetailActivity.ALL_DATA, mPickData);
        intent.putExtra(PhotoPickDetailActivity.EXTRA_MAX, mMaxPick);
        startActivityForResult(intent, ResultCodes.PHOTO_PICK_SELECTED);
    }

    /**
     * show/hidden folder list
     */
    private void showFolderList(boolean show) {
        if (show) {
            Animation animation = AnimationUtils.loadAnimation(this, R.anim.listview_up);
            Animation fadeIn = AnimationUtils.loadAnimation(this, R.anim.listview_fade_in);

            mFoldList.startAnimation(animation);
            mFoldSelectView.startAnimation(fadeIn);
            mFoldSelectView.setVisibility(View.VISIBLE);
        } else {
            Animation animation = AnimationUtils.loadAnimation(PhotoPickActivity.this, R.anim.listview_down);
            Animation fadeOut = AnimationUtils.loadAnimation(PhotoPickActivity.this, R.anim.listview_fade_out);
            fadeOut.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {}

                @Override
                public void onAnimationEnd(Animation animation) {
                    mFoldSelectView.setVisibility(View.INVISIBLE);
                }

                @Override
                public void onAnimationRepeat(Animation animation) {}
            });

            mFoldList.startAnimation(animation);
            mFoldSelectView.startAnimation(fadeOut);
        }
    }

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
        ((BaseAdapter) mFoldList.getAdapter()).notifyDataSetChanged();

        updatePickCount();
    }

    /**
     * external call
     */
    public void camera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        fileUri = CameraPhotoUtils.getOutputMediaFileUri();
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
        startActivityForResult(intent, ResultCodes.PHOTO_PICK_CAMERA);
    }

    // [+] OptionsMenu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.photo_pick, menu);
        mSendMenu = menu.getItem(0);
        updatePickCount();
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_finish) {
            send();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }
    // [-] OptionsMenu

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
        String format = "完成(%d/%d)";
        mSendMenu.setTitle(String.format(format, mPickData.size(), mMaxPick));

        String formatPreview = "预览(%d/%d)";
        mPreviewText.setText(String.format(formatPreview, mPickData.size(), mMaxPick));
    }

}
