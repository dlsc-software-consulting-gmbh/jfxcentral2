package com.dlsc.jfxcentral2.utils;

import com.codename1.io.Log;

/// The desktop app's logging calls, sent to Codename One's `Log`, which
/// reaches logcat on Android, the device console on iOS and the browser
/// console on the web.
public class LOGGER {

    public static void error(String msg, Object obj, Exception ex) {
        Log.p(obj == null ? msg : msg + " " + obj, Log.ERROR);
        if (ex != null) {
            Log.e(ex);
        }
    }

    public static void error(String msg, Exception ex) {
        error(msg, null, ex);
    }

    public static void error(String msg, Object obj) {
        error(msg, obj, null);
    }

    public static void warn(String msg, String obj) {
        Log.p(obj == null ? msg : msg + " " + obj, Log.WARNING);
    }

    public static void warn(String msg) {
        warn(msg, (String) null);
    }

    public static void warn(String msg, Exception ex) {
        warn(msg, (String) null);
        if (ex != null) {
            Log.e(ex);
        }
    }
}
