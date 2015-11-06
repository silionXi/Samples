package com.silion.samples.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

/**
 * Created by silion on 2015/11/6.
 */
public class SamplesService extends Service {
    @Override
    public void onCreate() {
        android.util.Log.d("slong.liang", "SampleService onCreate");
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        android.util.Log.d("slong.liang", "SampleService onStartCommand");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        android.util.Log.d("slong.liang", "SampleService onDestroy");
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }
}
