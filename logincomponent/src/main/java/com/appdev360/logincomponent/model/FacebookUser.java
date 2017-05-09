package com.appdev360.logincomponent.model;

import com.facebook.AccessToken;

/**
 * Created by jaffarraza on 26/04/2017.
 */
public class FacebookUser extends User {
    private String profileName;
    private AccessToken accessToken;

    public FacebookUser() {
    }

    public String getProfileName() {
        return profileName;
    }

    public void setProfileName(String profileName) {
        this.profileName = profileName;
    }

    public AccessToken getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(AccessToken accessToken) {
        this.accessToken = accessToken;
    }
}
