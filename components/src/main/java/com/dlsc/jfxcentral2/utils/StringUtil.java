package com.dlsc.jfxcentral2.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {
    public static final String LOADING_TIPS = "Please wait. Loading...";
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
}
