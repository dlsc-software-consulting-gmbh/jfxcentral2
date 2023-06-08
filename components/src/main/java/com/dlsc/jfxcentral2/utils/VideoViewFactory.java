package com.dlsc.jfxcentral2.utils;

import com.dlsc.jfxcentral.data.model.Video;
import com.jpro.webapi.HTMLView;
import com.jpro.webapi.WebAPI;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.DoubleBinding;
import javafx.scene.Node;
import javafx.scene.layout.Region;
import javafx.scene.web.WebView;

public class VideoViewFactory {

    public static Node createVideoViewNode(Video video) {
        if (WebAPI.isBrowser()) {
            HTMLView htmlView = new HTMLView();
            htmlView.setContent("<div width=\"100%\" \"height=100%\" background-color=\"powderblue\" class=\"yt\"><iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/" + video.getId() + "\" allowfullscreen></iframe></div></body></html>\n");
            htmlView.parentProperty().addListener(it -> {
                Region parent = (Region) htmlView.getParent();
                if (parent != null) {
                    bindWidthAndHeight(htmlView, parent);
                }
            });

            return htmlView;
        }

        WebView webView = new WebView();
        webView.parentProperty().addListener(it -> {
            Region parent = (Region) webView.getParent();
            if (parent != null) {
                bindWidthAndHeight(webView, parent);
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

    private static void bindWidthAndHeight(HTMLView htmlView, Region region) {
        DoubleBinding widthBinding = Bindings.createDoubleBinding(() -> region.getWidth() - region.getInsets().getLeft() - region.getInsets().getRight(), region.widthProperty(), region.insetsProperty());

        htmlView.prefWidthProperty().bind(widthBinding);
        htmlView.prefHeightProperty().bind(widthBinding.divide(16).multiply(9));

        htmlView.minWidthProperty().bind(widthBinding);
        htmlView.minHeightProperty().bind(widthBinding.divide(16).multiply(9));

        htmlView.maxWidthProperty().bind(widthBinding);
        htmlView.maxHeightProperty().bind(widthBinding.divide(16).multiply(9));
    }

    private static void bindWidthAndHeight(WebView htmlView, Region region) {
        DoubleBinding widthBinding = Bindings.createDoubleBinding(() -> region.getWidth() - region.getInsets().getLeft() - region.getInsets().getRight(), region.widthProperty(), region.insetsProperty());

        htmlView.prefWidthProperty().bind(widthBinding);
        htmlView.prefHeightProperty().bind(widthBinding.divide(16).multiply(9));

        htmlView.minWidthProperty().bind(widthBinding);
        htmlView.minHeightProperty().bind(widthBinding.divide(16).multiply(9));

        htmlView.maxWidthProperty().bind(widthBinding);
        htmlView.maxHeightProperty().bind(widthBinding.divide(16).multiply(9));
    }
}
