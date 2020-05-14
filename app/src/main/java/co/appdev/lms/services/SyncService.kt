package co.appdev.lms.services


import javax.inject.Inject

import android.app.*
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.net.wifi.WifiManager
import android.os.*
import androidx.core.app.NotificationCompat
import co.appdev.lms.MyApplication
import co.appdev.lms.R
import co.appdev.lms.data.DataManager
import co.appdev.lms.ui.MainActivity
import co.appdev.lms.util.Constants

import io.reactivex.disposables.CompositeDisposable

class SyncService : Service() {
    private val timeOut: Long = 120000

    private var handler: Handler? = null
    private var runnable: Runnable? = null
    private var mHandler: Handler? = null
    private var mHandlerThread: HandlerThread? = null

    @Inject
    lateinit var mDataManager: DataManager
    internal var compositeDisposable: CompositeDisposable? = null
    private var wakeLock: PowerManager.WakeLock? = null
    private var wifiLock: WifiManager.WifiLock? = null


    init {
        MyApplication[this].component?.inject(this)
    }

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        compositeDisposable = CompositeDisposable()
        mHandlerThread = HandlerThread(Constants.THREAD_NAME)
        mHandlerThread!!.start()
        mHandler = Handler(mHandlerThread!!.looper)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val powerManager = getSystemService(Context.POWER_SERVICE) as PowerManager
        wakeLock = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, TAG + ":" + Constants.WAKE_LOCK_TAG)
        acquireWakeLock()

        val wm = applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
        wifiLock = wm.createWifiLock(WifiManager.WIFI_MODE_FULL, Constants.WAKE_LOCK_WIFI)
        wifiLock!!.setReferenceCounted(true)

        acquireWifiLock()
        if (intent != null && intent.action != null) {
            if (intent.action!!.contains(Constants.START)) {
                handler = Handler(Looper.getMainLooper())
                runnable = object : Runnable {
                    override fun run() {
                        startForeground(101, updateNotification())
                        handler!!.postDelayed(this, 4000)
                    }
                }
                handler!!.post(runnable)
                // mHandler.postDelayed(call method here, 2000);
            } else if (intent.action!!.contains(Constants.STOP)) {
                handler!!.removeCallbacks(runnable)
                stopForeground(true)
                stopSelf()
            }
        } else {
        }

        return Service.START_STICKY
    }

    internal fun acquireWifiLock() {
        if (!wifiLock!!.isHeld) {
            wifiLock!!.acquire()
        }
    }

    internal fun releaseWifiLock() {
        if (wifiLock!!.isHeld) {
            wifiLock!!.release()
        }

    }

    internal fun acquireWakeLock() {
        if (!wakeLock!!.isHeld) {
            wakeLock!!.acquire(timeOut)
        }
    }

    internal fun releaseWakeLock() {
        if (wakeLock!!.isHeld) {
            wakeLock!!.release()
        }
    }

    private fun stopService() {
        handler!!.removeCallbacks(runnable)
        stopForeground(true)
        stopSelf()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (compositeDisposable != null) compositeDisposable!!.clear()
        handler!!.removeCallbacks(runnable)
        releaseWakeLock()
        releaseWifiLock()
    }

    private fun updateNotification(): Notification {

        val notificationIntent = Intent(this, MainActivity::class.java)
        notificationIntent.putExtra("finish", true)
        notificationIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or
                Intent.FLAG_ACTIVITY_CLEAR_TASK or
                Intent.FLAG_ACTIVITY_NEW_TASK
        notificationIntent.action = java.lang.Long.toString(System.currentTimeMillis())
        val pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            val channelId = "some_channel_id"
            val channelName = "Some Channel"
            val importance = NotificationManager.IMPORTANCE_LOW
            val notificationChannel = NotificationChannel(channelId, channelName, importance)
            notificationChannel.enableLights(true)
            notificationChannel.lightColor = Color.RED
            notificationChannel.enableVibration(true)
            notificationManager.createNotificationChannel(notificationChannel)

            return Notification.Builder(this, channelId)
                .setContentTitle(getText(R.string.app_name))
                .setTicker(getText(R.string.app_name))
                .setContentText(getText(R.string.content_text))
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(
                    Bitmap.createScaledBitmap(
                        BitmapFactory.decodeResource(resources, R.mipmap.ic_launcher),
                        128,
                        128,
                        false
                    )
                )
                .setContentIntent(pendingIntent)
                .setChannelId(channelId)
                .setOngoing(true).build()

        } else {
            return NotificationCompat.Builder(this)
                .setContentTitle(getText(R.string.app_name))
                .setTicker(getText(R.string.app_name))
                .setContentText(getText(R.string.content_text))
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(
                    Bitmap.createScaledBitmap(
                        BitmapFactory.decodeResource(resources, R.mipmap.ic_launcher),
                        128,
                        128,
                        false
                    )
                )
                .setContentIntent(pendingIntent)
                .setOngoing(true).build()
        }
    }

    companion object {
        private val TAG = SyncService::class.java.simpleName
    }


}