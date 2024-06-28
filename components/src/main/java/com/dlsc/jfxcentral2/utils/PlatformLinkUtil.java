package com.dlsc.jfxcentral2.utils;

import javafx.scene.Node;
import one.jpro.platform.routing.LinkUtil;

public class PlatformLinkUtil {

    public static void setLink(Node node, String url) {
        // Mobile
        if (OSUtil.runningOnMobile()) {
            MobileLinkUtil.setLink(node, url);
        }
        // JPro
        else {
            LinkUtil.setLink(node, url);
        }
    }

}
