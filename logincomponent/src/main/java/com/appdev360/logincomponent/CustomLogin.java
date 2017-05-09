package com.appdev360.logincomponent;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import com.appdev360.logincomponent.model.User;
import com.appdev360.logincomponent.utils.Constants;
import com.appdev360.logincomponent.utils.LoginException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Created by jaffarraza on 26/04/2017.
 */

public class CustomLogin extends Login {

    private static final String EMAIL_PATTERN = "^[a-zA-Z0-9#_~!$&'()*+,;=:.\"(),:;<>@\\[\\]\\\\]+@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*$";
    private Pattern pattern = Pattern.compile(EMAIL_PATTERN);
    private Matcher matcher;

    @Override
    public void login(LoginConfig config) {
        User user = config.getCallback().doCustomLogin();
        if (user != null) {
            String email = user.getEmail();
            String password = user.getPassword();
            int minLength = config.getMinimumPasswordLength();
            if (!validateEmail(email)) {
                config.getCallback().onLoginFailure(new LoginException("Please enter valid Email address", LoginType.CustomLogin));
                return;
            }
            if (password.trim().length() < minLength) {
                config.getCallback().onLoginFailure(new LoginException("Password should be of "+minLength+" length", LoginType.CustomLogin));
                return;
            }
            // Save the user
            UserSessionManager.setUserSession(config.getActivity(), user);
            config.getCallback().onLoginSuccess(user);
        } else {
            config.getCallback().onLoginFailure(new LoginException("Custom login failed", LoginType.CustomLogin));
        }
    }

//    @Override
//    public void signup(LoginConfig config) {
//        User user = config.getCallback().doCustomSignup();
//        if (user != null) {
//            // Save the user
//            UserSessionManager.setUserSession(config.getActivity(), user);
//            config.getCallback().onLoginSuccess(user);
//        } else {
//            config.getCallback().onLoginFailure(new LoginException("Custom signup failed", LoginType.CustomLogin));
//        }
//    }

    @Override
    public boolean logout(Context context) {
        try {
            SharedPreferences preferences = context.getSharedPreferences(Constants.USER_PREFS, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.remove(Constants.USER_TYPE);
            editor.remove(Constants.USER_SESSION);
            editor.apply();
            return true;
        } catch (Exception e) {
            Log.e("CustomLogin", e.getMessage());
            return false;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data, LoginConfig config) {

    }

    public boolean validateEmail(String email) {
        matcher = pattern.matcher(email);
        return matcher.matches();
    }
}
