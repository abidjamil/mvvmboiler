package com.appdev360.logincomponent;

import android.content.Context;
import android.content.Intent;

/**
 * Created by jaffarraza on 26/04/2017.
 */

public abstract class Login {

    public abstract void login(LoginConfig config);

    public abstract boolean logout(Context context);

    public abstract void onActivityResult(int requestCode, int resultCode, Intent data, LoginConfig config);

}
