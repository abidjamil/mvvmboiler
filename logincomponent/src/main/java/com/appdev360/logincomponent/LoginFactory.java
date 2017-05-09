package com.appdev360.logincomponent;

import com.appdev360.logincomponent.model.FacebookUser;
import com.appdev360.logincomponent.model.GoogleUser;
import com.appdev360.logincomponent.model.User;

/**
 * Created by jaffarraza on 26/04/2017.
 */

public class LoginFactory {
    public static Login build(LoginType loginType) {
        switch (loginType) {
            case Facebook:
                return new FacebookLogin();
            case Google:
                return new GoogleLogin();
            case CustomLogin:
                return new CustomLogin();
            default:
                // To avoid null pointers
                return new CustomLogin();
        }
    }

    public static Login build(User user) {
       if (user instanceof FacebookUser) {
           return new FacebookLogin();
       } else if (user instanceof GoogleUser) {
           return new FacebookLogin();
       } else {
           return new CustomLogin();
       }
    }
}
