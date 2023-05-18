package com.dlsc.jfxcentral2.components.gridview;

import com.dlsc.jfxcentral.data.model.Video;
import com.dlsc.jfxcentral2.components.tiles.VideoTileView;
import com.dlsc.jfxcentral2.utils.IkonUtil;
import com.jpro.webapi.HTMLView;
import com.jpro.webapi.WebAPI;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.web.WebView;
import org.kordamp.ikonli.javafx.FontIcon;

public class VideoGridView extends ModelGridView<Video, VideoTileView> {

    public VideoGridView() {
        getStyleClass().add("video-grid-view");

        setDetailNodeProvider(this::createVideoNode);

        setTileViewProvider(model -> {
            VideoTileView videoTileView = new VideoTileView();
            videoTileView.setData(model);
            videoTileView.button1ActionProperty().bind(videoTileView.onShowDetailNodeProperty());
            return videoTileView;
        });
    }

    private Node createVideoNode(Video video) {
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

    private BorderPane createTestVideoDetailNode(Video model) {
        Label label = new Label("Video Title");
        label.setStyle("-fx-text-fill: white;-fx-font-size: 30px;-fx-font-weight: bold;");
        BorderPane pane = new BorderPane(label);
        pane.setStyle("-fx-background-color: black;");
        pane.setMinHeight(260);
        pane.setMinWidth(260);
        Button button = new Button("Close", new FontIcon(IkonUtil.close));
        pane.setTop(button);

        //close detail node
        button.setOnAction(event -> {
            Parent parentNode = pane.getParent();
            parentNode.requestFocus();
            if (parentNode instanceof Pane parent) {
                parent.getChildren().remove(pane);
            }
            event.consume();
        });
        return pane;
    }


}
