package com.appdev360.logincomponent;

import com.appdev360.logincomponent.model.User;
import com.appdev360.logincomponent.utils.LoginException;


/**
 * Created by jaffarraza on 26/04/2017.
 */

public interface Callbacks {

    void onLoginSuccess(User user);

    void onLoginFailure(LoginException e);

    User doCustomLogin();
}
