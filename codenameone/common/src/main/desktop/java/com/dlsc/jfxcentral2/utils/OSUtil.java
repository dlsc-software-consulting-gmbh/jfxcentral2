package com.dlsc.jfxcentral2.utils;

import com.dlsc.jfxcentral.data.util.StringUtils;

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

            if (StringUtils.isEmpty(osName)) {
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

    public static boolean isAndroidOrIOS() {
        if (Boolean.getBoolean("native")) {
            return true;
        }
        String platform = com.codename1.ui.CN.getPlatformName();
        return "ios".equals(platform) || "and".equals(platform);
    }

    public static boolean isNative() {
        // A device build, as the GraalVM native image was for the previous mobile build.
        return isAndroidOrIOS();
    }

    public static boolean runningOnMobile() {
        return true;
    }

    public static boolean isAWTSupported() {
        if (isAndroidOrIOS() || isNative()) {
            return false;
        }

        return true;
    }
}
