//package com.appdev360.logincomponent.sharing;
//
//import com.appdev360.logincomponent.model.FacebookUser;
//import com.appdev360.logincomponent.model.GoogleUser;
//import com.appdev360.logincomponent.model.User;
//
///**
// * Created by jaffarraza on 28/04/2017.
// */
//
//public class ShareFactory {
//    public static Share build(User login) {
//        if (login instanceof FacebookUser) {
//            return new FacebookShare();
//        } else if (login instanceof GoogleUser) {
//            return new TwitterShare();
//        } else {
//            return null;
//        }
//    }
//}
