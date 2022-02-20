package com.ethan.tooldemo.download;

import android.annotation.SuppressLint;
import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigInteger;
import java.security.MessageDigest;

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

    public static String genMD5(File file) {
        String r = null;
        if (file == null || !file.isFile() || !file.exists()) {
            return r;
        }
        FileInputStream in = null;
        byte buffer[] = new byte[1024];
        int len;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");  //MessageDigest
            in = new FileInputStream(file);
            while ((len = in.read(buffer, 0, 1024)) != -1) {  //循环读取
                md.update(buffer, 0, len);  //填充
            }
            in.close();
            r = String.format("%032x", new BigInteger(1, md.digest()));  //转换成16进制
        } catch (Exception e) {
        }
        return r;
    }

    private void downLoadAPKFile(ResponseBody responseBody) {
        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "auction-download.apk");

        InputStream inputStream = null;
        OutputStream outputStream = null;
        try {
            if (!file.exists()) file.createNewFile();
            inputStream = responseBody.byteStream();
            outputStream = new FileOutputStream(file);
            int count = 0;
            int progress = 0;
            long fileSize = responseBody.contentLength();
            byte[] bytes = new byte[1024];
            while ((count = inputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, count);
                progress += count;
                Log.d(TAG, "progress = " + progress + " - fileSize = " + fileSize + " - count = " + count);
            }
        } catch (IOException ioException) {
            Log.d(TAG, "ioException = " + ioException.getMessage());
        } finally {
            try {
                if (inputStream != null) inputStream.close();
                if (outputStream != null) outputStream.close();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }
}
