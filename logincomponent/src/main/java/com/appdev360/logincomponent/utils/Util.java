package com.appdev360.logincomponent.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

import com.appdev360.logincomponent.model.FacebookUser;
import com.appdev360.logincomponent.model.GoogleUser;
import com.appdev360.logincomponent.model.User;
import com.facebook.AccessToken;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;


/**
 * Created by jaffarraza on 26/04/2017.
 */
public class Util {

    public static GoogleUser populateGoogleUser(GoogleSignInAccount account){
        //Create a new google user
        GoogleUser googleUser = new GoogleUser();
        //populate the user
        googleUser.setDisplayName(account.getDisplayName());
        if (account.getPhotoUrl() != null) {
            googleUser.setPhotoUrl(account.getPhotoUrl().toString());
        }
        googleUser.setEmail(account.getEmail());
        googleUser.setIdToken(account.getIdToken());
        googleUser.setUserId(account.getId());
        if (account.getAccount() != null) {
            googleUser.setUsername(account.getAccount().name);
        }

        //return the populated google user
        return googleUser;
    }

    public static FacebookUser populateFacebookUser(JSONObject object, AccessToken accessToken){
        FacebookUser facebookUser = new FacebookUser();
        facebookUser.setGender(-1);
        facebookUser.setAccessToken(accessToken);
        try {
            if (object.has(Constants.FacebookFields.EMAIL))
                facebookUser.setEmail(object.getString(Constants.FacebookFields.EMAIL));
            if (object.has(Constants.FacebookFields.BIRTHDAY))
                facebookUser.setBirthday(object.getString(Constants.FacebookFields.BIRTHDAY));
            if (object.has(Constants.FacebookFields.GENDER)) {
                try {
                    Constants.Gender gender = Constants.Gender.valueOf(object.getString(Constants.FacebookFields.GENDER));
                    switch (gender) {
                        case male:
                            facebookUser.setGender(0);
                            break;
                        case female:
                            facebookUser.setGender(1);
                            break;
                    }
                } catch (Exception e) {
                    //if gender is not in the enum it is already set to unspecified value (-1)
                    Log.e("Util", e.getMessage());
                }
            }
            if (object.has(Constants.FacebookFields.LINK))
                facebookUser.setProfileLink(object.getString(Constants.FacebookFields.LINK));
            if (object.has(Constants.FacebookFields.ID))
                facebookUser.setUserId(object.getString(Constants.FacebookFields.ID));
            if (object.has(Constants.FacebookFields.NAME))
                facebookUser.setProfileName(object.getString(Constants.FacebookFields.NAME));
            if (object.has(Constants.FacebookFields.FIRST_NAME))
                facebookUser.setFirstName(object.getString(Constants.FacebookFields.FIRST_NAME));
            if (object.has(Constants.FacebookFields.MIDDLE_NAME))
                facebookUser.setMiddleName(object.getString(Constants.FacebookFields.MIDDLE_NAME));
            if (object.has(Constants.FacebookFields.LAST_NAME))
                facebookUser.setLastName(object.getString(Constants.FacebookFields.LAST_NAME));
        } catch (JSONException e){
            Log.e("Util", e.getMessage());
            facebookUser = null;
        }
        return facebookUser;
    }

    public static User populateCustomUser(String username, String email, String userId){
        User user = new User();
        user.setEmail(email);
        user.setUsername(username);
        user.setUserId(userId);
        return user;
    }

    public void FromCamera(Context context, String path, int code) {

        Log.i("camera", "startCameraActivity()");
        File file = new File(path);
        Uri outputFileUri = Uri.fromFile(file);
        Intent intent = new Intent(
                android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
        ((Activity) context).startActivityForResult(intent, code);

    }

    public void FromGallery(Context context, String path, int code) {
        Intent intent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        ((Activity) context).startActivityForResult(intent, code);
    }

}
