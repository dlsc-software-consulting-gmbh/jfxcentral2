package com.dlsc.jfxcentral2.utils;

import com.dlsc.jfxcentral.data.model.Video;
import com.jpro.webapi.HTMLView;
import com.jpro.webapi.WebAPI;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.Region;
import javafx.scene.web.WebView;

public class VideoViewFactory {

    public static Node createViewViewNode(Video video) {
        if (WebAPI.isBrowser()) {
            HTMLView htmlView = new HTMLView();
            htmlView.setContent("<div width=\"100%\" \"height=100%\" background-color=\"powderblue\" class=\"yt\"><iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/" + video.getId() + "\" allowfullscreen></iframe></div></body></html>\n");
            htmlView.parentProperty().addListener(it -> {
                Parent parent = htmlView.getParent();
                if (parent != null) {
                    htmlView.prefWidthProperty().bind((((Region) parent).widthProperty()));
                    htmlView.prefHeightProperty().bind(htmlView.prefWidthProperty().divide(16).multiply(9));

                    htmlView.minWidthProperty().bind((((Region) parent).widthProperty()));
                    htmlView.minHeightProperty().bind(htmlView.minWidthProperty().divide(16).multiply(9));

                    htmlView.maxWidthProperty().bind((((Region) parent).widthProperty()));
                    htmlView.maxHeightProperty().bind(htmlView.maxWidthProperty().divide(16).multiply(9));
                }
            });

            return htmlView;
        }

        WebView webView = new WebView();
        webView.setFocusTraversable(true);
        webView.parentProperty().addListener(it -> {
            Parent parent = webView.getParent();
            if (parent != null) {
                webView.prefWidthProperty().bind((((Region) parent).widthProperty()));
                webView.prefHeightProperty().bind(webView.prefWidthProperty().divide(16).multiply(9));

                webView.minWidthProperty().bind((((Region) parent).widthProperty()));
                webView.minHeightProperty().bind(webView.minWidthProperty().divide(16).multiply(9));

                webView.maxWidthProperty().bind((((Region) parent).widthProperty()));
                webView.maxHeightProperty().bind(webView.maxWidthProperty().divide(16).multiply(9));
            }
        });
        webView.getEngine().load("https://www.youtube.com/embed/" + video.getId());

        webView.sceneProperty().addListener(it -> {
            if (webView.getScene() == null) {
                webView.getEngine().loadContent("empty");
            }
        });

        return webView;
    }
}
