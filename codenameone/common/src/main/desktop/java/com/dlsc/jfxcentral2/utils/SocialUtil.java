package com.dlsc.jfxcentral2.utils;


public class SocialUtil {

    public static boolean isSocialFeaturesEnabled() {
        // to explicitly enable social features, no matter where the app runs, we can use this VM argument
        if (Boolean.getBoolean("social")) {
            return true;
        }

        if (!"HTML5".equals(com.codename1.ui.CN.getPlatformName())) {
            // we are currently planning to have social features only available in the browser
            return false;
        }

        // eventually we will have a different result, for now it is always false
        return false;
    }
}
