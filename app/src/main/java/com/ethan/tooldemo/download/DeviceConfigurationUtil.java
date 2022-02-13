package com.ethan.tooldemo.download;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.os.Build;

public class DeviceConfigurationUtil {
    private static final String TAG = "DeviceConfigurationUtil";

    /**
     * @param context context
     * @return App名称
     */
    public static String getAppName(Context context) {
        PackageInfo packageInfo;
        String versionName = "小当竞拍";
        try {
            packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            versionName = packageInfo.applicationInfo.loadLabel(context.getPackageManager()).toString();
        } catch (Exception e) {
        }
        return versionName;
    }

    /**
     * @param context context
     * @return 版本名（外部版本号）
     */
    public static String getAppVersion(Context context) {
        PackageInfo packageInfo;
        String version = "";
        try {
            packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            version = packageInfo.versionName;
        } catch (Exception e) {
        }
        return version;
    }

    /**
     * @param context content
     * @return 内部版本号
     */
    public static long getAppVersionCode(Context context) {
        PackageInfo packageInfo;
        long version = 0;
        try {
            packageInfo = context.getPackageManager()
                    .getPackageInfo(context.getPackageName(), 0);
            version = packageInfo.getLongVersionCode();
        } catch (Exception e) {
        }
        return version;
    }

    @SuppressLint("HardwareIds")
    public static String getDeviceSN() { // 序列号
        return Build.SERIAL;
    }
}
