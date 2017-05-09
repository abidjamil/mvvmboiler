package com.appdev360.logincomponent;

import android.app.Activity;
import android.support.annotation.NonNull;

import com.google.android.gms.common.api.GoogleApiClient;

import java.util.ArrayList;

/**
 * Created by jaffarraza on 26/04/2017.
 */
public class LoginConfig {

    private String facebookAppId;
    private ArrayList<String> facebookPermissions;
    private GoogleApiClient googleApiClient;
    private Activity activity;
    private Callbacks callback;

    public int getMinimumPasswordLength() {
        return minimumPasswordLength;
    }

    private int minimumPasswordLength;


    public LoginConfig(@NonNull Activity activity, Callbacks callback) {
        this.activity = activity;
        this.callback = callback;
    }

    public Activity getActivity() {
        return activity;
    }

    public Callbacks getCallback() {
        return callback;
    }

    public GoogleApiClient getGoogleApiClient() {
        return googleApiClient;
    }

    public void setGoogleApiClient(GoogleApiClient googleApiClient) {
        this.googleApiClient = googleApiClient;
    }

    public String getFacebookAppId() {
        return facebookAppId;
    }

    public void setFacebookAppId(String facebookAppId) {
        this.facebookAppId = facebookAppId;
    }

    public ArrayList<String> getFacebookPermissions() {
        return facebookPermissions;
    }

    public void setFacebookPermissions(ArrayList<String> facebookPermissions) {
        this.facebookPermissions = facebookPermissions;
    }

    public static ArrayList<String> getDefaultFacebookPermissions() {
        ArrayList<String> defaultPermissions = new ArrayList<>();
        defaultPermissions.add("public_profile");
        defaultPermissions.add("email");
        defaultPermissions.add("user_birthday");
        return defaultPermissions;
    }

    public void setMinimumPasswordLength(int minimumPasswordLength) {
        this.minimumPasswordLength = minimumPasswordLength;
    }
}
