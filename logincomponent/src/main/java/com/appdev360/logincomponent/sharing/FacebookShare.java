//package com.appdev360.logincomponent.sharing;
//
//import android.app.Activity;
//import android.content.Intent;
//import android.graphics.Bitmap;
//import android.net.Uri;
//import android.support.annotation.NonNull;
//import android.util.Log;
//
//import com.appdev360.logincomponent.Callbacks;
//import com.appdev360.logincomponent.LoginConfig;
//import com.appdev360.logincomponent.UserSessionManager;
//import com.appdev360.logincomponent.model.FacebookUser;
//import com.appdev360.logincomponent.utils.Util;
//import com.facebook.AccessToken;
//import com.facebook.CallbackManager;
//import com.facebook.FacebookCallback;
//import com.facebook.FacebookException;
//import com.facebook.GraphRequest;
//import com.facebook.GraphResponse;
//import com.facebook.login.LoginManager;
//import com.facebook.login.LoginResult;
//import com.facebook.share.ShareApi;
//import com.facebook.share.model.ShareContent;
//import com.facebook.share.model.ShareLinkContent;
//import com.facebook.share.model.ShareMediaContent;
//import com.facebook.share.model.SharePhoto;
//
//import org.json.JSONObject;
//
//import java.util.Arrays;
//import java.util.List;
//import java.util.Set;
//
///**
// * Created by jaffarraza on 28/04/2017.
// */
//
//public class FacebookShare extends Share {
//
//
//    private CallbackManager callbackManager;
//    private static final List<String> PERMISSIONS = Arrays.asList("publish_actions");
//    private String message = "";
//    private Bitmap bitmap;
//
//    public FacebookShare() {
//        //Facebook login callback
//        callbackManager = CallbackManager.Factory.create();
//    }
//
//
//    @Override
//    public void shareContent(@NonNull LoginConfig config, String text, Bitmap bitmap) {
//        final Activity activity = config.getActivity();
//        final Callbacks callback = config.getCallback();
//
//        this.message = text;
//        this.bitmap = bitmap;
//
//        Set<String> permissions = AccessToken.getCurrentAccessToken().getPermissions();
//        if (permissions.contains(PERMISSIONS)) {
//            shareOnFacebook();
//            return;
//        }
//        requestPublishPermission(activity, callback);
//
//    }
//
//    private void shareOnFacebook() {
//        String text = "";
//        if (this.message != null) {
//            text = this.message;
//        }
//        if (bitmap != null) {
//            SharePhoto sharePhoto = new SharePhoto.Builder().setBitmap(bitmap).setCaption(text).build();
//            ShareContent content = new ShareMediaContent.Builder().addMedium(sharePhoto).build();
//            ShareApi.share(content, null);
//            return;
//        }
//        ShareLinkContent content = new ShareLinkContent.Builder()
//                .setContentUrl(Uri.parse("https://facebook.com"))
//                .setQuote(text)
//                .build();
//        ShareApi.share(content, null);
//
//    }
//
//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        callbackManager.onActivityResult(requestCode, resultCode, data);
//    }
//
//
//    private void requestPublishPermission(final Activity activity, Callbacks callback) {
//        LoginManager.getInstance().logInWithPublishPermissions(activity, Arrays.asList("publish_actions"));
//        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
//            @Override
//            public void onSuccess(final LoginResult loginResult) {
//                GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
//                    @Override
//                    public void onCompleted(JSONObject jsonObject, GraphResponse response) {
//                        FacebookUser facebookUser = Util.populateFacebookUser(jsonObject, loginResult.getAccessToken());
//                        UserSessionManager.setUserSession(activity, facebookUser);
//                        shareOnFacebook();
//                    }
//                });
//                request.executeAsync();
//            }
//
//            @Override
//            public void onCancel() {
//                Log.d("Facebook Login", "User cancelled the login process");
//            }
//
//            @Override
//            public void onError(FacebookException e) {
//            }
//        });
//    }
//
//}
