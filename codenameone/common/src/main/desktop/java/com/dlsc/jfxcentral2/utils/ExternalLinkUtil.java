package com.dlsc.jfxcentral2.utils;

import javafx.scene.Node;


public class ExternalLinkUtil {

    public static void setExternalLink(Node node, String url) {
        setExternalLink(node, url, null);
    }

    public static void setExternalLink(Node node, String url, String description) {
        // The system browser on a device, a new tab on the web. The URLs come
        // from the repository's data, so only web and mail links are handed to
        // the platform; anything else leaves the node inert.
        if (!isOpenable(url)) {
            node.setOnMouseClicked(null);
            return;
        }
        node.setOnMouseClicked(evt -> {
            if (evt.isStillSincePress()) {
                com.codename1.ui.CN.execute(url);
            }
        });
    }

    static boolean isOpenable(String url) {
        if (url == null) {
            return false;
        }
        String u = url.trim();
        return u.regionMatches(true, 0, "https://", 0, 8)
                || u.regionMatches(true, 0, "http://", 0, 7)
                || u.regionMatches(true, 0, "mailto:", 0, 7);
    }
}
