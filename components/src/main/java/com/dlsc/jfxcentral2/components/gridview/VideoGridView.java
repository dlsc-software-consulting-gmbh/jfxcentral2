package com.dlsc.jfxcentral2.components.gridview;

import com.dlsc.jfxcentral.data.model.Video;
import com.dlsc.jfxcentral2.components.tiles.VideoTileView;
import com.dlsc.jfxcentral2.utils.IkonUtil;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import org.kordamp.ikonli.javafx.FontIcon;

public class VideoGridView extends ModelGridView<Video, VideoTileView> {

    public VideoGridView() {
        getStyleClass().add("video-grid-view");

        setDetailNodeProvider(this::createTestVideoDetailNode);

        setTileViewProvider(model -> {
            VideoTileView videoTileView = new VideoTileView();
            videoTileView.setData(model);
            videoTileView.button1ActionProperty().bind(videoTileView.onShowDetailNodeProperty());
            return videoTileView;
        });
    }

    private BorderPane createTestVideoDetailNode(Video model) {
        Label label = new Label("Video Title");
        label.setStyle("-fx-text-fill: white;-fx-font-size: 30px;-fx-font-weight: bold;");
        BorderPane pane = new BorderPane(label);
        pane.setStyle("-fx-background-color: black;");
        pane.setMinHeight(260);
        pane.setMinWidth(260);
        Button button = new Button("Close",new FontIcon(IkonUtil.close));
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
