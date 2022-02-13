package com.ethan.tooldemo.download;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.IBinder;

public class OTAServiceConnection implements ServiceConnection {
    private final String TAG = this.getClass().getSimpleName().toString();
    private OTAService.OTABinder otaBinder;
    private OTABinderInterface mOTABinderInterface;
    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
        otaBinder = (OTAService.OTABinder) service;
        if (mOTABinderInterface != null) mOTABinderInterface.returnBinder(otaBinder);
    }
    @Override
    public void onServiceDisconnected(ComponentName name) {
    }

    public void setOTABinderInterface(OTABinderInterface otaBinderInterface){
        mOTABinderInterface = otaBinderInterface;
    }
}
