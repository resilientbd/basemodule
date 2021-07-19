package com.faisal.basemodule.base;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.IntentFilter;
import android.os.Build;

import com.faisal.basemodule.Toaster;

public class App extends Application {
    public static final String COMMON_NOTIFICATION_CHANNEL_ID = "199823";
    public static final String COMMON_TOASTER_BROADCAST_ACTION = "com.faisal.basemodule.Toaster.ACTION_BROADCAST";
    private Toaster toasterBroadcstReciever = new Toaster();
    @Override
    public void onCreate() {
        super.onCreate();
        createNotificationChannel();
        IntentFilter filter = new IntentFilter();
        filter.addAction(COMMON_TOASTER_BROADCAST_ACTION);
        registerReceiver(toasterBroadcstReciever,filter);

    }
    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel serviceChannel = new NotificationChannel(
                    COMMON_NOTIFICATION_CHANNEL_ID,
                    "base notification channel",
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(serviceChannel);
        }
    }
}