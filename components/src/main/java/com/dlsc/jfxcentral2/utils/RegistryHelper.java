package com.dlsc.jfxcentral2.utils;

import java.util.prefs.Preferences;

public class RegistryHelper {

    private static final Preferences prefs = Preferences.userNodeForPackage(RegistryHelper.class);

    public enum RegistryKey {
        MASTODON_SERVER
    }

    public static String get(RegistryKey key) {
        return prefs.get(key.name(), "");
    }

    public static void put(RegistryKey key, String value) {
        if (!get(key).equals(value)) {
            prefs.put(key.name(), value);
        }
    }
}