package com.dlsc.jfxcentral2.utils;

import java.io.IOException;

public class LOGGER {

    public static void error(String msg, Object obj, Exception ex) {
        System.err.println(msg);
        if (obj != null) {
            System.err.println(obj);
        }
        if (ex != null) {
            ex.printStackTrace(System.err);
        }
    }

    public static void error(String msg, Exception ex) {
        error(msg, null, ex);
    }

    public static void error(String msg, Object obj) {
        error(msg, obj, null);
    }

    public static void warn(String msg, String obj) {
        System.err.println(msg);
        if (obj != null) {
            System.err.println(obj);
        }
    }

    public static void warn(String msg) {
        System.out.println(msg);
    }

    public static void warn(String msg, IOException ex) {
        System.out.println(msg);
        if (ex != null) {
            ex.printStackTrace();
        }
    }

    public static void warn(String msg, Exception ex) {
        warn(msg, ex);
    }
}
