package com.appdev360.components;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.appdev360.components.pushnotificaiton.Config;
import com.appdev360.components.pushnotificaiton.NotificationUtil;
import com.appdev360.logincomponent.Callbacks;
import com.appdev360.logincomponent.Login;
import com.appdev360.logincomponent.LoginConfig;
import com.appdev360.logincomponent.LoginFactory;
import com.appdev360.logincomponent.LoginType;
import com.appdev360.logincomponent.UserSessionManager;
import com.appdev360.logincomponent.model.User;
import com.appdev360.logincomponent.utils.LoginException;
import com.google.firebase.messaging.FirebaseMessaging;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity implements Callbacks {

    @BindView(R.id.email)
    EditText email;
    @BindView(R.id.password)
    EditText password;
    User currentUser;

    private LoginConfig config;
    private Login login;

    private BroadcastReceiver mRegistrationBroadcastReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                // checking for type intent filter
                if (intent.getAction().equals(Config.REGISTRATION_COMPLETE)) {
                    // gcm successfully registered
                    // now subscribe to `global` topic to receive app wide notifications
                    FirebaseMessaging.getInstance().subscribeToTopic(Config.TOPIC_GLOBAL);


                } else if (intent.getAction().equals(Config.PUSH_NOTIFICATION)) {
                    // new push notification is received

                    String message = intent.getStringExtra("message");

                    Toast.makeText(getApplicationContext(), "Push notification: " + message, Toast.LENGTH_LONG).show();
                }
            }
        };



        config = new LoginConfig(LoginActivity.this, this);
        config.setFacebookAppId(getString(R.string.facebook_app_id));
        config.setFacebookPermissions(null);
        config.setGoogleApiClient(null);
        config.setMinimumPasswordLength(6);
    }



    @Override
    protected void onResume() {
        super.onResume();
        currentUser = UserSessionManager.getCurrentUser(this);

        // register GCM registration complete receiver
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver, new IntentFilter(Config.REGISTRATION_COMPLETE));

        // register new push message receiver
        // by doing this, the activity will be notified each time a new message arrives
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver, new IntentFilter(Config.PUSH_NOTIFICATION));

        // clear the notification area when the app is opened
        NotificationUtil.clearNotifications(getApplicationContext());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (login != null) {
            login.onActivityResult(requestCode, resultCode, data, config);
        }
    }


    @Override
    public void onLoginSuccess(User user) {
        Toast.makeText(this, user.toString(), Toast.LENGTH_SHORT).show();
        startActivity(new Intent(this, MainActivity.class));
    }

    @Override
    public void onLoginFailure(LoginException e) {
        Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
    }


    @Override
    public User doCustomLogin() {
        User user = new User();
        user.setEmail(email.getText().toString().trim());
        user.setPassword(password.getText().toString().trim());
        return user;
    }


    @OnClick({R.id.custom_signin_button, R.id.google_login_button, R.id.facebook_login_button})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.google_login_button:
                // Perform Google login
                login = LoginFactory.build(LoginType.Google);
                login.login(config);
                break;
            case R.id.custom_signin_button:
                // Perform custom sign in
                login = LoginFactory.build(LoginType.CustomLogin);
                login.login(config);
                break;
            case R.id.facebook_login_button:
                // Perform Facebook login
                login = LoginFactory.build(LoginType.Facebook);
                login.login(config);
                break;
        }
    }
}
