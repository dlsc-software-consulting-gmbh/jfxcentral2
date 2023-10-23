package com.dlsc.jfxcentral2.utils;

import com.gluonhq.attach.browser.BrowserService;
import javafx.scene.Node;
import one.jpro.platform.routing.LinkUtil;

import java.io.IOException;
import java.net.URISyntaxException;

public class ExternalLinkUtil {

    public static void setExternalLink(Node node, String url) {
        setExternalLink(node, url, null);
    }

    public static void setExternalLink(Node node, String url, String description) {
        if (OSUtil.isAndroidOrIOS()) {
            node.setOnMouseClicked(evt -> {
                if (evt.isStillSincePress()) {
                    BrowserService.create().ifPresent(service -> {
                        try {
                            service.launchExternalBrowser(url);
                        } catch (IOException | URISyntaxException e) {
                            throw new RuntimeException(e);
                        }
                    });
                }
            });
        } else {
            LinkUtil.setExternalLink(node, url, description);
        }
    }
}
