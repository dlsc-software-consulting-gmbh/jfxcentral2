package com.dlsc.jfxcentral2.demo.utils;

import java.net.URL;

public final class ResourceUtil {
    private ResourceUtil() {
    }
    public static URL load(String path) {
        return ResourceUtil.class.getResource(path);
    }
}
