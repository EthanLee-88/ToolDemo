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

}
