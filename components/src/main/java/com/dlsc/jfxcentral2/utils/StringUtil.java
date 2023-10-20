package com.dlsc.jfxcentral2.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {

    public static final String LOADING_TIPS = "Please wait. Loading...";

    public static final String NEW_LINE = System.lineSeparator();

    private StringUtil() {
    }

    public static String getDomainName(String url) {
        if (url == null) {
            return null;
        }
        String regex = "https?://(?:www\\.)?(.+?)\\.(?:com|cn|net|org)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(url);
        if (matcher.find()) {
            return matcher.group(1);
        }

        return null;
    }

    public static String formatFileSize(long size) {
        long kb = 1024;
        long mb = kb * 1024;
        long gb = mb * 1024;

        if (size >= gb) {
            return String.format("%.1f GB", (float) size / gb);
        } else if (size >= mb) {
            float f = (float) size / mb;
            return String.format(f > 100 ? "%.0f MB" : "%.1f MB", f);
        } else if (size >= kb) {
            float f = (float) size / kb;
            return String.format(f > 100 ? "%.0f KB" : "%.1f KB", f);
        } else {
            return String.format("%d Byte", size);
        }
    }
}
