package com.example.alexis.parkmycar.utils.services;

import android.app.Application;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v7.app.NotificationCompat;
import android.util.Log;

import com.example.alexis.parkmycar.HubActivity;
import com.example.alexis.parkmycar.R;
import com.example.alexis.parkmycar.utils.timer.TimeUtil;

public class NotifyService extends Service
{
    public static final String REFRESH_TIME_INTENT = "REFRESH_TIME_INTENT";
    public static final String KEY_EXTRA_LONG_TIME_IN_MS = "timeInMs";
    private static final int NOTIFICATION_ID = 1;
    private NotificationManager mNotificationManager;
    private PendingIntent contentIntent;
    private boolean isRunning = false;

    public NotifyService() {
        super();
        //contentIntent = PendingIntent.getActivity(this, 0, new Intent(this, HubActivity.class), 0);
        //mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
    }

    @Override
    public void onCreate() {
        contentIntent = PendingIntent.getActivity(this, 0, new Intent(this, HubActivity.class), 0);
        mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        super.onCreate();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new Binder();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    private void sendEvent(long millisUntilFinished) {

        Intent intent = new Intent(REFRESH_TIME_INTENT);
        intent.putExtra(KEY_EXTRA_LONG_TIME_IN_MS, millisUntilFinished);
        sendBroadcast(intent);
    }

    public void showNotification() {


        NotificationCompat.Builder notifBuilder = (NotificationCompat.Builder) new NotificationCompat.Builder(this);

        mNotificationManager.notify(NOTIFICATION_ID, notifBuilder.build());
    }

    private void showNotificationTime(long timeInMs) {
        Integer[] minSec = TimeUtil.getMinSec(timeInMs);
        NotificationCompat.Builder notifBuilder =
                (NotificationCompat.Builder) new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.ic_notifications_black_24dp)
                        .setContentTitle("Compteur")
                        .setContentIntent(contentIntent)
                        .setContentText(minSec[TimeUtil.MINUTE] + ":" + minSec[TimeUtil.SECOND]);
        mNotificationManager.notify(NOTIFICATION_ID, notifBuilder.build());
    }
}
