package com.ethan.tooldemo.download;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

/**
 * Created by EthenLee on 2019/12/12.
 */
public class OTAService extends Service {
    private final String TAG = this.getClass().getSimpleName().toString();
    private OTABinder mOTABinder = new OTABinder();

    @Override
    public IBinder onBind(Intent intent) {
        return mOTABinder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
    public class OTABinder extends Binder{
        public void updateClick(){
            if (true) Log.d(TAG , "otaClick");
            getVersionInformation();
        }
    }

    private void getVersionInformation(){

    }

    private void parseVersion(){

    }

//    private Intent otaIntent;
//    private OTAServiceConnection mOTAServiceConnection;
//    private OTAService.OTABinder mOTABinder;
//    public void bindService(){
//        if (otaIntent == null) otaIntent = new Intent(this , OTAService.class);
//        if (mOTAServiceConnection == null) mOTAServiceConnection = new OTAServiceConnection();
//        bindService(otaIntent , mOTAServiceConnection , Service.BIND_AUTO_CREATE);
//        mOTAServiceConnection.setOTABinderInterface((OTAService.OTABinder otaBinder) -> {
//            mOTABinder = otaBinder;
//            if (mOTABinder != null) mOTABinder.updateClick();
//        });
//    }


//    App 在线升级接口需求
//
// ---------- 基础入参 ----------
//            1、客户端标识 ：标志 Android和 IOS（int型）
//            2、当前内部版本号（int型，如85）
//            3、当前外部版本号（String型，如 1.0.0）
//
//            ----------- 基础出参 ---------
//            1、查询结果：是否有可用更新等（int）
//            2、是否需强制更新（boolean型）
//            3、更新日志（String）
//            4、新版本外部版本号（String）
//            5、新版本内部版本号（int）
//            6、Android端新版本安装包下载地址（String）
//            7、Android端新版本安装包对应的MD5值（String型，用于安装包校验）

}
