package com.dlsc.jfxcentral2.utils;

import com.dlsc.jfxcentral.data.model.Video;
import com.jpro.webapi.HTMLView;
import com.jpro.webapi.WebAPI;
import com.sun.net.httpserver.HttpServer;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.DoubleBinding;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import org.kordamp.ikonli.javafx.FontIcon;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;

public class VideoViewFactory {

    public static Node createVideoViewNode(Video video) {
        return createVideoViewNode(video, false);
    }

    public static Node createVideoViewNode(Video video, boolean closeable) {
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
        WebEngine webEngine = webView.getEngine();
        webEngine.setUserAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36");

        String html = "<!DOCTYPE html><html><head>" +
                "<style>body{margin:0;padding:0;background:#000;overflow:hidden;height:100%;}" +
                "iframe{position:absolute;top:0;left:0;width:100%;height:100%;border:none;}</style>" +
                "</head><body>" +
                "<iframe src=\"https://www.youtube.com/embed/" + video.getId() + "\" frameborder=\"0\" allowfullscreen allow=\"autoplay; encrypted-media\"></iframe>" +
                "</body></html>";
        byte[] htmlBytes = html.getBytes(StandardCharsets.UTF_8);

        try {
            HttpServer server = HttpServer.create(new InetSocketAddress("127.0.0.1", 0), 0);
            server.createContext("/", exchange -> {
                exchange.getResponseHeaders().add("Content-Type", "text/html; charset=utf-8");
                exchange.sendResponseHeaders(200, htmlBytes.length);
                try (OutputStream os = exchange.getResponseBody()) {
                    os.write(htmlBytes);
                }
            });
            server.start();
            int port = server.getAddress().getPort();
            webEngine.load("http://127.0.0.1:" + port + "/");
            webView.sceneProperty().addListener(it -> {
                if (webView.getScene() == null) {
                    server.stop(0);
                    webEngine.load(null);
                }
            });
        } catch (IOException e) {
            webEngine.load("https://www.youtube.com/embed/" + video.getId());
            webView.sceneProperty().addListener(it -> {
                if (webView.getScene() == null) {
                    webEngine.load(null);
                }
            });
        }

        webView.setOnScroll(event -> {
            if (event.getDeltaY() != 0) {
                event.consume();
                ScrollPane scrollPane = NodeUtil.findScrollPane(webView);
                if (scrollPane != null) {
                    scrollPane.setVvalue(scrollPane.getVvalue() - event.getDeltaY() / scrollPane.getHeight());
                }
            }
        });

        if (!closeable) {
            return webView;
        }

        Button closeButton = new Button();
        closeButton.setFocusTraversable(false);
        closeButton.getStyleClass().addAll("close-button", "blue-button");
        closeButton.setGraphic(new FontIcon(IkonUtil.close));

        WebViewWrapper webViewWrapper = new WebViewWrapper(webView, closeButton);
        webViewWrapper.getStyleClass().add("web-view-wrapper");

        closeButton.setOnAction(event -> {
            Pane parent = (Pane) webViewWrapper.getParent();
            if (parent != null) {
                parent.getChildren().remove(webViewWrapper);
                Platform.runLater(parent::requestFocus);
            }
        });

        return webViewWrapper;
    }

    private static class WebViewWrapper extends StackPane {

        public WebViewWrapper(WebView webView, Button closeButton) {
            StackPane.setAlignment(closeButton, Pos.TOP_RIGHT);
            StackPane.setMargin(closeButton, new Insets(5, 5, 0, 0));
            getChildren().addAll(webView, closeButton);
            setFocusTraversable(false);

            webView.localToSceneTransformProperty().addListener((obs, oldV, newV) -> fixIt(webView));
        }

        boolean fixing = false;

        private void fixIt(WebView view) {
            if (!fixing) {
                fixing = true;
                view.setLayoutY(view.getLayoutY() + 1);
                double width = view.getWidth();
                double height = view.getHeight();
                view.resize(width + 1, height + 1);
                view.resize(width, height);
                Platform.runLater(() -> fixing = false);
            }
        }
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
