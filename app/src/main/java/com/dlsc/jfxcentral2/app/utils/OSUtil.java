package com.dlsc.jfxcentral2.app.utils;

import org.eclipse.jgit.util.StringUtils;

public class OSUtil {
    public enum Desktop {
        WINDOWS,
        MAC,
        LINUX,
        UNKNOWN
    }

    private static Desktop desktop;

    public static Desktop getDesktop() {
        if (desktop == null) {
            String osName = System.getProperty("os.name");

            if (StringUtils.isEmptyOrNull(osName)) {
                desktop = Desktop.UNKNOWN;
            } else {
                String lowerCaseOSName = osName.toLowerCase();
                if (lowerCaseOSName.contains("win")) {
                    desktop = Desktop.WINDOWS;
                } else if (lowerCaseOSName.contains("mac")) {
                    desktop = Desktop.MAC;
                } else if (lowerCaseOSName.contains("nix")
                        || lowerCaseOSName.contains("nux")
                        || lowerCaseOSName.contains("aix")) {
                    desktop = Desktop.LINUX;
                } else {
                    desktop = Desktop.UNKNOWN;
                }
            }
        }
        return desktop;
    }

    public static boolean isWindows() {
        return getDesktop() == Desktop.WINDOWS;
    }

    public static boolean isMac() {
        return getDesktop() == Desktop.MAC;
    }

    public static boolean isLinux() {
        return getDesktop() == Desktop.LINUX;
    }

    public static boolean isUnknown() {
        return getDesktop() == Desktop.UNKNOWN;
    }

}
