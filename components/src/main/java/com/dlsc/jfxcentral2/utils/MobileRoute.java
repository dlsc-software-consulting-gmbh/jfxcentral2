package com.dlsc.jfxcentral2.utils;

import javafx.scene.Node;
import javafx.util.Callback;
import org.apache.commons.lang3.StringUtils;

import java.util.regex.Pattern;

/**
 * The MobileRoute class represents a route with a path and a callback
 * that generates a Node view based on a given URL.
 */

public class MobileRoute {
    private final String path;
    private final Callback<String, Node> viewCallback;
    private final String redirectPath;
    private final Pattern pattern;

    /**
     * Constructor for normal routes
     */
    public MobileRoute(String path, Callback<String, Node> viewCallback) {
        this.path = path;
        this.viewCallback = viewCallback;
        this.redirectPath = null;
        this.pattern = Pattern.compile(Pattern.quote(path) + "(/.*)?");
    }

    /**
     * Constructor for redirect routes
     */
    public MobileRoute(String path, String redirectPath) {
        this.path = path;
        this.viewCallback = null;
        this.redirectPath = redirectPath;
        this.pattern = null;
    }

    public boolean matches(String url) {
        if (pattern == null) {
            // Strict match for redirect routes
            return url.equals(path);
        }
        return url.equals(path) || pattern.matcher(StringUtils.trimToEmpty(url)).matches();
    }

    public Node createView(String url) {
        return viewCallback.call(url);
    }

    public String getRedirectPath() {
        return redirectPath;
    }

    public String getPath() {
        return path;
    }

    public static MobileRoute get(String path, Callback<String, Node> viewCallback) {
        return new MobileRoute(path, viewCallback);
    }

    public static MobileRoute redirect(String path, String redirectPath) {
        return new MobileRoute(path, redirectPath);
    }
}