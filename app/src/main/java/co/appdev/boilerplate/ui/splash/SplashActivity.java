package co.appdev.boilerplate.ui.splash;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import co.appdev.boilerplate.R;
import co.appdev.boilerplate.ui.main.MainActivity;


public class SplashActivity extends AppCompatActivity {
    // SplashActivity screen timer
    private static int SPLASH_TIME_OUT = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        new Handler().postDelayed(new Runnable() {

			/*
             * Showing splash screen with a timer. This will be useful when you
			 * want to show case your app logo / company
			 */

            @Override
            public void run() {
                // This method will be executed once the timer is over

                startActivity(MainActivity.getStartIntent(SplashActivity.this,true));
                finish();
            }
        }, SPLASH_TIME_OUT);
    }
}
