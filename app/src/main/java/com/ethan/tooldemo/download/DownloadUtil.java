package com.ethan.tooldemo.download;

import android.annotation.SuppressLint;
import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.util.Log;

import java.io.File;

public class DownloadUtil {

    private static final String TAG = "DownloadUtil";

    private static final String DownloadPath = "/Auction/Download/";
    @SuppressLint("SdCardPath")
    public static final String DeletePath = "/sdcard" + DownloadPath;
    public static final String APKName = "Auction.apk";
    private static final String RequestMimeType = "application/vnd.android.package-archive";

    private static DownloadManager.Request getRequest(String downloadURL){
        Uri uri = Uri.parse(downloadURL);
        DownloadManager.Request request = new DownloadManager.Request(uri);
        request.setDestinationInExternalPublicDir(DownloadPath , APKName);  // 设置下载路径和文件名
        request.setMimeType(RequestMimeType);
        request.allowScanningByMediaScanner();// 设置为可被媒体扫描器找到
        request.setVisibleInDownloadsUi(true); // 设置为可见和可管理
//        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
//        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_HIDDEN); //通知栏不显示
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE);
        /**设置用于下载时的网络状态*/
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE);
        return request;
    }

    private static boolean onDownLoading = false;
    public static long downloadAPK(Context mContext,  String url) {
//        url = "https://upload-images.jianshu.io/upload_images/19865651-f3b9dcb719b75e23.gif";
        url = "https://qd.myapp.com/myapp/qqteam/AndroidQQ/mobileqq_android.apk";
        deleteFile();
        DownloadManager dManager = (DownloadManager) mContext.getSystemService(Context.DOWNLOAD_SERVICE);
        DownloadManager.Request request = getRequest(url);
        long downLoadId = dManager.enqueue(request);
        return downLoadId;

//        onDownLoading = true;
//        AppExecutors.getInstance().diskIO().execute(() -> {
//            DownloadManager.Query query = new DownloadManager.Query().setFilterById(downLoadId);
//            Cursor cursor = null;
//            while (true){
//                try {
//                    cursor = dManager.query(query);
//                    if (cursor != null && cursor.moveToFirst()){
//                        int percent = (int) (RegularUtil.dataDivider(cursor.getInt(cursor.getColumnIndexOrThrow(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR)) ,
//                                cursor.getInt(cursor.getColumnIndexOrThrow(DownloadManager.COLUMN_TOTAL_SIZE_BYTES))) * 100);
//                        showProgress(percent);
//                    }
//                }catch (Exception e){}
//                if ((cursor.getInt(cursor.getColumnIndexOrThrow(DownloadManager.COLUMN_TOTAL_SIZE_BYTES)) > 0) &&
//                        (cursor.getInt(cursor.getColumnIndexOrThrow(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR)) ==
//                                cursor.getInt(cursor.getColumnIndexOrThrow(DownloadManager.COLUMN_TOTAL_SIZE_BYTES)))){
//                    onDownLoading = false;
//                    break;
//                }
//                SystemClock.sleep(500);
//            }
//        });
    }
    private static void showProgress(int per){
        Log.d(TAG, "per = " + per);
//        AppExecutors.getInstance().mainThread().execute(() -> {
//            if (downloadProgress != null){
//                downloadProgress.setProgress(per);
//                percentShowOne.setText(per + "%");
//                percentShowTwo.setText(per + "/100");
//                if ((per == 100) && (GlobalInstance.getInstance().popLocationView != null))
//                    showPopWindow(GlobalInstance.getInstance().popLocationView);
//            }
//        });
    }

    private static void deleteFile(){
        File file = new File(DeletePath + APKName);
        try {
            file.delete();
        }catch (Exception e){
        }
    }
}
