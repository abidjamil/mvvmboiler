package com.appdev360.logincomponent;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.appdev360.logincomponent.model.FacebookUser;
import com.appdev360.logincomponent.model.GoogleUser;
import com.appdev360.logincomponent.model.User;
import com.appdev360.logincomponent.utils.Constants;
import com.google.gson.Gson;

/**
 * Created by jaffarraza on 26/04/2017.
 */
public class UserSessionManager {

    /**
        This static method can be called to get the logged in user.
        It reads from the shared preferences and builds a User object and returns it.
        If no user is logged in it returns null
    */
    public static User getCurrentUser(Context context){
        User user = null;
        SharedPreferences preferences = context.getSharedPreferences(Constants.USER_PREFS, Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String sessionUser = preferences.getString(Constants.USER_SESSION, Constants.DEFAULT_SESSION_VALUE);
        String user_type = preferences.getString(Constants.USER_TYPE, Constants.CUSTOMUSERFLAG);
        if(!sessionUser.equals(Constants.DEFAULT_SESSION_VALUE)){
            try {
                switch (user_type) {
                    case Constants.FACEBOOKFLAG:
                        user = gson.fromJson(sessionUser, FacebookUser.class);
                        break;
                    case Constants.GOOGLEFLAG:
                        user = gson.fromJson(sessionUser, GoogleUser.class);
                        break;
                    default:
                        user = gson.fromJson(sessionUser, User.class);
                        break;
                }
            }catch (Exception e){
                Log.e("GSON", e.getMessage());
            }
        }
        return user;
    }

    /**
        This method sets the session object for the current logged in user.
        This is called from inside the LoginActivity to save the
        current logged in user to the shared preferences.
    */
    public static boolean setUserSession(Context context, User user){
        SharedPreferences preferences;
        SharedPreferences.Editor editor;
        if(user != null) {
            try {
                preferences = context.getSharedPreferences(Constants.USER_PREFS, Context.MODE_PRIVATE);
                editor = preferences.edit();

                if (user instanceof FacebookUser) {
                    editor.putString(Constants.USER_TYPE, Constants.FACEBOOKFLAG);
                } else if (user instanceof GoogleUser) {
                    editor.putString(Constants.USER_TYPE, Constants.GOOGLEFLAG);
                } else {
                    editor.putString(Constants.USER_TYPE, Constants.CUSTOMUSERFLAG);
                }

                Gson gson = new Gson();
                String sessionUser = gson.toJson(user);
                Log.d("GSON", sessionUser);
                editor.putString(Constants.USER_SESSION, sessionUser);
                editor.apply();
                return true;
            } catch (Exception e) {
                Log.e("Session Error", e.getMessage());
                return false;
            }
        } else {
            Log.e("Session Error", "User is null");
            return false;
        }
    }
}
