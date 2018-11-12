package co.appdev.boilerplate.services;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.PowerManager;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;

import javax.inject.Inject;

import co.appdev.boilerplate.AndroidComponentsApp;
import co.appdev.boilerplate.R;
import co.appdev.boilerplate.data.DataManager;
import co.appdev.boilerplate.ui.splash.SplashActivity;
import co.appdev.boilerplate.util.Constants;
import io.reactivex.disposables.CompositeDisposable;

public class SyncService extends Service {
    private static final String TAG = SyncService.class.getSimpleName();
    private long timeOut = 120000;

    private Handler handler;
    private Runnable runnable;
    private Handler mHandler;
    private HandlerThread mHandlerThread;

    @Inject
    DataManager mDataManager;
    CompositeDisposable compositeDisposable;
    private PowerManager.WakeLock wakeLock;
    private WifiManager.WifiLock wifiLock;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        compositeDisposable = new CompositeDisposable();
        AndroidComponentsApp.get(this).getComponent().inject(this);
        mHandlerThread = new HandlerThread(Constants.THREAD_NAME);
        mHandlerThread.start();
        mHandler = new Handler(mHandlerThread.getLooper());
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        PowerManager powerManager = (PowerManager) getSystemService(Context.POWER_SERVICE);
        assert powerManager != null;
        wakeLock = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, TAG + ":" + Constants.WAKE_LOCK_TAG);
        acquireWakeLock();

        WifiManager wm = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        if (wm != null) {
            wifiLock = wm.createWifiLock(WifiManager.WIFI_MODE_FULL, Constants.WAKE_LOCK_WIFI);
            wifiLock.setReferenceCounted(true);
        }
        acquireWifiLock();
        if (intent != null && intent.getAction() != null) {
            if (intent.getAction().contains(Constants.START)) {
                handler = new Handler(Looper.getMainLooper());
                runnable = new Runnable() {
                    @Override
                    public void run() {
                        startForeground(101, updateNotification());
                        handler.postDelayed(this, 4000);
                    }
                };
                handler.post(runnable);
               // mHandler.postDelayed(call method here, 2000);
            } else if (intent.getAction().contains(Constants.STOP)) {
                handler.removeCallbacks(runnable);
                stopForeground(true);
                stopSelf();
            }
        } else {
        }

        return START_STICKY;
    }

    void acquireWifiLock() {
        if (!wifiLock.isHeld()) {
            wifiLock.acquire();
        }
    }

    void releaseWifiLock() {
        if (wifiLock.isHeld()) {
            wifiLock.release();
        }

    }

    void acquireWakeLock() {
        if (!wakeLock.isHeld()) {
            wakeLock.acquire(timeOut);
        }
    }

    void releaseWakeLock() {
        if (wakeLock.isHeld()) {
            wakeLock.release();
        }
    }

    private void stopService() {
        handler.removeCallbacks(runnable);
        stopForeground(true);
        stopSelf();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (compositeDisposable != null) compositeDisposable.clear();
        handler.removeCallbacks(runnable);
        releaseWakeLock();
        releaseWifiLock();
    }

    private Notification updateNotification() {

        Intent notificationIntent = new Intent(this, SplashActivity.class);
        notificationIntent.putExtra("finish", true);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                Intent.FLAG_ACTIVITY_CLEAR_TASK |
                Intent.FLAG_ACTIVITY_NEW_TASK);
        notificationIntent.setAction(Long.toString(System.currentTimeMillis()));
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            String channelId = "some_channel_id";
            CharSequence channelName = "Some Channel";
            int importance = NotificationManager.IMPORTANCE_LOW;
            NotificationChannel notificationChannel = new NotificationChannel(channelId, channelName, importance);
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.enableVibration(true);

            assert notificationManager != null;
            notificationManager.createNotificationChannel(notificationChannel);

            return new Notification.Builder(this, channelId)
                    .setContentTitle(getText(R.string.app_service))
                    .setTicker(getText(R.string.app_name))
                    .setContentText(getText(R.string.content_text))
                    .setSmallIcon(R.mipmap.ic_sync_black_24dp)
                    .setLargeIcon(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_sync_black_24dp), 128, 128, false))
                    .setContentIntent(pendingIntent)
                    .setChannelId(channelId)
                    .setOngoing(true).build();

        } else {
            return new NotificationCompat.Builder(this)
                    .setContentTitle(getText(R.string.app_service))
                    .setTicker(getText(R.string.app_name))
                    .setContentText(getText(R.string.content_text))
                    .setSmallIcon(R.mipmap.ic_sync_black_24dp)
                    .setLargeIcon(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_sync_black_24dp), 128, 128, false))
                    .setContentIntent(pendingIntent)
                    .setOngoing(true).build();
        }
    }


}