package com.dlsc.jfxcentral2.utils;

import javafx.util.Callback;

import java.util.regex.Pattern;

/**
 * The MobileRoute class represents a route with a path and a callback
 * that generates a Node view based on a given URL.
 */

public class MobileRoute {

    private final String path;
    private final Callback<String, MobileResponse> responseCallback;
    private final String redirectPath;
    private final Pattern pattern;

    /**
     * Constructor for normal routes
     */
    public MobileRoute(String path, Callback<String, MobileResponse> responseCallback) {
        this.path = path;
        this.responseCallback = responseCallback;
        this.redirectPath = null;
        this.pattern = Pattern.compile(Pattern.quote(path) + "(/.*)?");
    }

    /**
     * Constructor for redirect routes
     */
    public MobileRoute(String path, String redirectPath) {
        this.path = path;
        this.responseCallback = null;
        this.redirectPath = redirectPath;
        this.pattern = null;
    }

    public boolean matches(String url) {
        if (pattern == null) {
            // Strict match for redirect routes
            return url.equals(path);
        }
        return url.equals(path) || pattern.matcher(url.trim()).matches();
    }

    public MobileResponse createResponse(String url) {
        if (redirectPath != null) {
            return MobileResponse.redirect(url, redirectPath);
        }
        return responseCallback.call(url);
    }

    public String getRedirectPath() {
        return redirectPath;
    }

    public String getPath() {
        return path;
    }

    public static MobileRoute get(String path, Callback<String, MobileResponse> responseCallback) {
        return new MobileRoute(path, responseCallback);
    }

    public static MobileRoute redirect(String path, String redirectPath) {
        return new MobileRoute(path, redirectPath);
    }
}