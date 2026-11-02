package com.dlsc.jfxcentral.data.util;

/// The handful of commons-lang3 `StringUtils` methods the app uses. commons-lang3
/// is not available on a device, and these are a few lines each. Case folding
/// goes through `regionMatches(true, ...)`, which compares character by
/// character and does not depend on the device locale.
public final class StringUtils {

    private StringUtils() {
    }

    public static boolean isEmpty(CharSequence s) {
        return s == null || s.length() == 0;
    }

    public static boolean isNotEmpty(CharSequence s) {
        return !isEmpty(s);
    }

    public static boolean isBlank(CharSequence s) {
        if (s == null) {
            return true;
        }
        for (int i = 0; i < s.length(); i++) {
            if (!Character.isWhitespace(s.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public static boolean isNotBlank(CharSequence s) {
        return !isBlank(s);
    }

    public static boolean equals(CharSequence a, CharSequence b) {
        return a == null ? b == null : b != null && a.toString().equals(b.toString());
    }

    public static boolean equalsIgnoreCase(CharSequence a, CharSequence b) {
        return a == null ? b == null : b != null && a.toString().equalsIgnoreCase(b.toString());
    }

    public static boolean containsIgnoreCase(CharSequence str, CharSequence search) {
        if (str == null || search == null) {
            return false;
        }
        String s = str.toString();
        String t = search.toString();
        int max = s.length() - t.length();
        for (int i = 0; i <= max; i++) {
            if (s.regionMatches(true, i, t, 0, t.length())) {
                return true;
            }
        }
        return false;
    }

    public static boolean containsAnyIgnoreCase(CharSequence str, CharSequence... searches) {
        if (searches == null) {
            return false;
        }
        for (CharSequence search : searches) {
            if (containsIgnoreCase(str, search)) {
                return true;
            }
        }
        return false;
    }

    public static <T extends CharSequence> T defaultIfEmpty(T str, T defaultStr) {
        return isEmpty(str) ? defaultStr : str;
    }

    public static String replace(String text, String searchString, String replacement) {
        if (isEmpty(text) || isEmpty(searchString) || replacement == null) {
            return text;
        }
        return text.replace(searchString, replacement);
    }
}
