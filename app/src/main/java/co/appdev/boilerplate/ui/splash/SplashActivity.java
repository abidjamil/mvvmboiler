package co.appdev.boilerplate.ui.splash;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.util.concurrent.TimeUnit;

import co.appdev.boilerplate.R;
import co.appdev.boilerplate.ui.main.MainActivity;
import io.reactivex.Maybe;

import static co.appdev.boilerplate.util.Constants.SPLASH_TIME_OUT;


public class SplashActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Maybe.empty()
                .delay(SPLASH_TIME_OUT, TimeUnit.SECONDS)
                .doOnComplete(() -> {
                    startActivity(MainActivity.getStartIntent(SplashActivity.this,true));
                    finish();
                })
                .subscribe();
    }
}
