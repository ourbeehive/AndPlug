package com.lean56.andplug.app.activity;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebViewClient;
import android.widget.*;
import com.lean56.andplug.activity.BaseActivity;
import com.lean56.andplug.app.Constant;
import com.lean56.andplug.app.R;
import com.lean56.andplug.app.view.CounterView;
import com.lean56.andplug.app.view.ObservableWebView;
import com.lean56.andplug.app.view.generic.ObservableScroll;
import com.lean56.andplug.utils.NetworkUtils;

public class FeatureDetailActivity extends BaseActivity implements ObservableScroll.OnScrollChangedListener, View.OnClickListener {

    // view res
    private LinearLayout mActionPanel;
    private LinearLayout mActionPanelCompact;
    private ImageView mCoverView;
    private TextView mTitleView;
    private ObservableWebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // init views
        mActionPanel = (LinearLayout) findViewById(R.id.action_panel);
        mActionPanelCompact = (LinearLayout) findViewById(R.id.action_panel_compact);
        mCoverView = (ImageView) findViewById(R.id.cover);
        mTitleView = (TextView) findViewById(R.id.title);
        mWebView = (ObservableWebView) findViewById(R.id.webview);

        // setting webview
        mWebView.setOnScrollChangedListener(this);
        WebSettings settings = mWebView.getSettings();
        settings.setUserAgentString(Constant.USER_AGENT);
        settings.setJavaScriptEnabled(true);
        if(NetworkUtils.isWiFiConnected(this)) {
            settings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        } else {
            settings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        }
        mWebView.setWebViewClient(new WebViewClient());
        // mWebView.setBackgroundColor(R.color.white_color);

        // setting action panel
        mActionPanel.setVisibility(View.INVISIBLE);
        bindClickEventToChildren(mActionPanel);
        bindClickEventToChildren(mActionPanelCompact);

        //
        /*Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        mArticleId = intent.getData().getLastPathSegment();
        category = (String) GlobPre.getIns(this).loadObjectFromStorage("CATEGORY", String.class);
        if((extras != null) && (extras.containsKey(EXTRAS_ARTICLE))) {
            setArticle((Article)extras.getParcelable(EXTRAS_ARTICLE));
        }
        mContainerHost = ContainerHost.take(this);
        mContainerHost.setOnClickListener(this);
        registerForContextMenu(mWebView);
        mCoverView.setOnLongClickListener(new View.OnLongClickListener(this) {

            public boolean onLongClick(View v) {
                if(null != mArticle) {
                    handleImage(mArticle.getImageUri());
                    return true;
                }
                return false;
            }
        });*/

    }

    @Override
    protected int getContentView() {
        return R.layout.feature_detail;
    }

    public void loadArticle() {
        mWebView.loadUrl("http://www.liwushuo.com/posts/1004452");
        mTitleView.setText("文章标题");
    }

    @Override
    public void onResume() {
        super.onResume();
        loadArticle();
    }

    @Override
    public void onPause() {
        super.onPause();
        // mProgressDialogHelper.cancelImmediate();
    }

    @Override
    public void onScrollChanged(int l, int t) {
        RelativeLayout.LayoutParams lpCover = (RelativeLayout.LayoutParams) mCoverView.getLayoutParams();
        RelativeLayout.LayoutParams lpTitle = (RelativeLayout.LayoutParams) mTitleView.getLayoutParams();
        int distance = getResources().getDimensionPixelSize(R.dimen.article_cover_height) - t;
        if (distance < 0) {
            lpCover.height = 0;
            lpTitle.topMargin = - Math.min(mTitleView.getMeasuredHeight(), -distance);
        } else {
            lpCover.height = distance;
            lpTitle.topMargin = 0;
        }
        mCoverView.requestLayout();
        mTitleView.requestLayout();

        int contentHeight = Math.max((int)(((float)mWebView.getContentHeight() * mWebView.getScaleY()) * getResources().getDisplayMetrics().density), mWebView.getHeight());
        RelativeLayout.LayoutParams lpActionPanel = (RelativeLayout.LayoutParams) mActionPanel.getLayoutParams();
        lpActionPanel.bottomMargin = ((mWebView.getMeasuredHeight() + t) - contentHeight);
        if (lpActionPanel.bottomMargin < -mActionPanel.getMeasuredHeight()) {
            mActionPanel.setVisibility(View.INVISIBLE);
        } else {
            mActionPanel.setVisibility(View.VISIBLE);
            mActionPanel.requestLayout();
        }
        RelativeLayout.LayoutParams lpActionPanelCompact = (RelativeLayout.LayoutParams) mActionPanelCompact.getLayoutParams();
        lpActionPanelCompact.bottomMargin = Math.min(((contentHeight - (mWebView.getMeasuredHeight() + t)) - mActionPanel.getMeasuredHeight()), 0x0);
        mActionPanelCompact.requestLayout();
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.action_fav:
                favArticle();
                return;
            case R.id.action_share:
                shareToFriends();
                return;
            case R.id.action_comment:
                /*if(mArticle == null) {
                    Toast.makeText(this, R.string.error_article_not_ready, Toast.LENGTH_SHORT).show();
                    return;
                }*/
                Toast.makeText(this, "kaiqi plun", Toast.LENGTH_SHORT).show();
                // startActivity(CommentActivity.createIntent(this, mArticle));
                return;
            /*case 2131427336:
            {
                loadArticle();
                break;
            }*/


        }
    }

    private void shareToFriends() {

    }

    private void favArticle() {

    }

    private void bindClickEventToChildren(ViewGroup vg) {
        for(int i = 0; i != vg.getChildCount(); i = i + 1) {
            vg.getChildAt(i).setOnClickListener(this);
        }
    }

    /*private void setArticle(Article article) {
        if(article != null) {
            mArticle = article;
            try {
                update(mActionPanel, R.id.article_action_fav, article.getLiked().booleanValue(), article.getFavCount().intValue());
                update(mActionPanelCompact, R.id.article_action_fav, article.getLiked().booleanValue(), article.getFavCount().intValue());
            } catch(Exception e) {}
            try {
                update(mActionPanel, R.id.action_comment, false, article.getCommentCount().intValue());
                update(mActionPanelCompact, R.id.action_comment, false, article.getCommentCount().intValue());
            } catch(Exception e) {}
            try {
                update(mActionPanel, R.id.action_share, false, article.getShareCount().intValue());
                update(mActionPanelCompact, R.id.action_share, false, article.getShareCount().intValue());
            } catch(Exception e) {}
            //Picasso.with(this).load(article.getImageUri()).into(mCoverView);
            mTitleView.setText(article.getTitle());
        }
    }*/

    private void update(ViewGroup panel, int control, boolean selected, int count) {
        CounterView cv = (CounterView) panel.findViewById(control);
        cv.setSelected(selected);
        cv.setCount(count);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (null != mWebView) {
            mWebView.saveState(outState);
        }
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (null != mWebView) {
            mWebView.restoreState(savedInstanceState);
        }
    }
}
