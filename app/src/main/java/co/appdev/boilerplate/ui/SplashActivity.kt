package co.appdev.boilerplate.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation.findNavController
import androidx.databinding.DataBindingUtil
import co.appdev.boilerplate.R
import co.appdev.boilerplate.databinding.PostActivityBinding
import co.appdev.boilerplate.util.Constants
import io.reactivex.Maybe
import java.util.concurrent.TimeUnit


class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_activity)
        val uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_FULLSCREEN
        window.decorView.systemUiVisibility = uiOptions
        Maybe.empty<Any>()
            .delay(Constants.SPLASH_TIME_OUT, TimeUnit.MILLISECONDS)
            .doOnComplete {
                startActivity(PostActivity.getStartIntent(this))
                finish()
            }.subscribe()
    }
}
