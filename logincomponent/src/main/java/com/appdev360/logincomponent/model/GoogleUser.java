package com.appdev360.logincomponent.model;

/**
 * Created by jaffarraza on 26/04/2017.
 */
public class GoogleUser extends User {

    private String displayName;
    private String photoUrl;
    private String idToken;

    public GoogleUser() {
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getIdToken() {
        return idToken;
    }

    public void setIdToken(String idToken) {
        this.idToken = idToken;
    }
}
