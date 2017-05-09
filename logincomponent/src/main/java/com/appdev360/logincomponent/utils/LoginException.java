package com.appdev360.logincomponent.utils;


import com.appdev360.logincomponent.LoginType;

/**
 * Created by jaffarraza on 26/04/2017.
 */

public class LoginException extends Exception {
    private LoginType loginType;

    public LoginException(String message, LoginType loginType) {
        super(message);
        this.loginType = loginType;
    }

    public LoginException(String message, Throwable cause, LoginType loginType) {
        super(message, cause);
        this.loginType = loginType;
    }

    public LoginType getLoginType() {
        return loginType;
    }
}
