package co.appdev.lms.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import co.appdev.lms.R
import co.appdev.lms.util.Constants
import com.microsoft.appcenter.AppCenter
import com.microsoft.appcenter.analytics.Analytics
import com.microsoft.appcenter.crashes.Crashes
import io.reactivex.Maybe
import java.util.concurrent.TimeUnit


class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_activity)
        AppCenter.start(
            getApplication(), Constants.AppCenter_Secret,
            Analytics::class.java, Crashes::class.java
        )
        val uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_FULLSCREEN
        window.decorView.systemUiVisibility = uiOptions
        Maybe.empty<Any>()
            .delay(Constants.SPLASH_TIME_OUT, TimeUnit.MILLISECONDS)
            .doOnComplete {
                startActivity(MainActivity.getStartIntent(this))
                finish()
            }.subscribe()
    }
}
