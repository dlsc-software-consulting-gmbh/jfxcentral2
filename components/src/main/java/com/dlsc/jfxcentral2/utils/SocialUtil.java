package com.dlsc.jfxcentral2.utils;

import com.jpro.webapi.WebAPI;

public class SocialUtil {

    public static boolean isSocialFeaturesEnabled() {
        // to explicitly enable social features, no matter where the app runs, we can use this VM argument
        if (Boolean.getBoolean("social")) {
            return true;
        }

        if (!WebAPI.isBrowser()) {
            // we are currently planning to have social features only available in the browser
            return false;
        }

        // eventually we will have a different result, for now it is always false
        return false;
    }
}
