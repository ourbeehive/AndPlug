package com.lean56.andplug.umengupdate;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.Toast;
import com.umeng.update.*;

/**
 * Update Checker powered by umeng auto update
 *
 * @author Charles(zhangchaoxu@gmail.com)
 */
public class UpdateChecker {

    private static ProgressDialog progressDialog;
    private final static String KEYWORD_FORCE_UPDATE = "forceUpdate";

    /**
     * http://dev.umeng.com/auto-update/android/
     * https://github.com/nxzhou91/umeng-android-sdk-theme/blob/master/blogs/articles/force_update.md
     *
     * @param ctx
     * @param manualCheckUpdate
     * 	手动强制更新
     */
    public static void doUmengCheckUpdateAction(final Context ctx, final boolean manualCheckUpdate) {
        // 调用更新接口
        // 由于我们的使用对象(如配运工)很可能会一直都处于!WIFI网络，因此!WIFI网络下也应该检查更新
        UmengUpdateAgent.setUpdateOnlyWifi(false);
        // 禁用集成监测功能,不使用Umeng的dialog和notification
        UmengUpdateAgent.setUpdateCheckConfig(false);
        // 不自动跳出dialog，手动监听更新结果
        UmengUpdateAgent.setUpdateAutoPopup(false);
        UmengUpdateAgent.setUpdateListener(new UmengUpdateListener() {
            @Override
            public void onUpdateReturned(int updateStatus, final UpdateResponse updateInfo) {
                switch (updateStatus) {
                    case UpdateStatus.Yes: // has update
                        // 由于Umeng没有强制更新选项，因此用updateLog是否含有KEYWORD_FORCE_UPDATE
                        if (manualCheckUpdate || updateInfo.updateLog.contains(KEYWORD_FORCE_UPDATE) || !UmengUpdateAgent.isIgnore(ctx, updateInfo)) {
                            double targetSize = 0;
                            try {
                                targetSize = Long.parseLong(updateInfo.target_size) / 1024 / 1024;
                            } catch (NumberFormatException nfe) {
                            }
                            CharSequence dialogMsg = ctx.getString(R.string.UMNewVersion) + updateInfo.version + "\n" + ctx.getString(R.string.UMTargetSize) + targetSize + "M"
                                    + "\n\n" + ctx.getString(R.string.UMUpdateContent) + "\n" + updateInfo.updateLog;
                            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ctx);
                            alertDialogBuilder.setTitle(R.string.UMUpdateTitle).setMessage(dialogMsg).setCancelable(false)
                                    .setPositiveButton(R.string.UMUpdateNow, new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            UmengUpdateAgent.startDownload(ctx, updateInfo);
                                        }
                                    });

                            if (!updateInfo.updateLog.contains(KEYWORD_FORCE_UPDATE)) {
                                alertDialogBuilder.setCancelable(true).setNegativeButton(R.string.UMNotNow, new DialogInterface.OnClickListener() {

                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                }).setNeutralButton(R.string.UMIgnore, new DialogInterface.OnClickListener() {

                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        UmengUpdateAgent.ignoreUpdate(ctx, updateInfo);
                                    }
                                });
                            }
                            alertDialogBuilder.create().show();
                        }
                        break;
                    case UpdateStatus.No: // has no update
                        if (manualCheckUpdate) {
                            Toast.makeText(ctx, "您使用的已经是最新版本", Toast.LENGTH_SHORT).show();
                        }
                        break;
                    case UpdateStatus.NoneWifi: // none wifi
                        if (manualCheckUpdate) {
                            Toast.makeText(ctx, ctx.getString(R.string.UMGprsCondition), Toast.LENGTH_SHORT).show();
                        }
                        break;
                    case UpdateStatus.Timeout: // time out
                        if (manualCheckUpdate) {
                            Toast.makeText(ctx, "连接超时,请检查网络后重试", Toast.LENGTH_SHORT).show();
                        }
                        break;
                }
            }
        });
        // 开始检查更新
        UmengUpdateAgent.update(ctx);

        // 下载监听
        UmengUpdateAgent.setDownloadListener(new UmengDownloadListener() {

            @Override
            public void OnDownloadStart() {
                progressDialog = new ProgressDialog(ctx, 0);
                progressDialog.setIndeterminate(false);
                progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                progressDialog.setCanceledOnTouchOutside(false);
                progressDialog.setMax(100);
                progressDialog.setMessage(ctx.getString(R.string.umeng_common_download_notification_prefix));
                progressDialog.setProgressNumberFormat("");
                progressDialog.show();
            }

            @Override
            public void OnDownloadUpdate(int progress) {
                if (null != progressDialog) {
                    progressDialog.setProgress(progress);
                }
            }

            @Override
            public void OnDownloadEnd(int result, String file) {
                if (null != progressDialog) {
                    progressDialog.dismiss();
                }
            }
        });
    }

}
