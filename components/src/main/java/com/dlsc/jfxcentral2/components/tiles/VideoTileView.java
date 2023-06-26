package com.dlsc.jfxcentral2.components.tiles;

import com.dlsc.jfxcentral.data.model.Video;
import com.dlsc.jfxcentral2.components.CustomImageView;
import com.dlsc.jfxcentral2.iconfont.JFXCentralIcon;
import com.dlsc.jfxcentral2.utils.IkonUtil;
import com.jpro.webapi.WebAPI;
import javafx.beans.binding.Bindings;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import one.jpro.routing.LinkUtil;
import org.kordamp.ikonli.javafx.FontIcon;

import java.awt.*;
import java.io.IOException;
import java.net.URI;

public class VideoTileView extends TileView<Video> {

    public VideoTileView(Video video) {
        super(video);

        getStyleClass().add("video-tile-view");

        setButton1Text("PLAY");
        setButton1Graphic(new FontIcon(IkonUtil.play));

        setButton2Text("YouTube");
        setButton2Graphic(new FontIcon(JFXCentralIcon.YOUTUBE));

        setRemark(video.getMinutes() + " mins");

        if (WebAPI.isBrowser()) {
            LinkUtil.setExternalLink(getButton2(), "https://www.youtube.com/watch?v=" + video.getId());
        } else {
            getButton2().setOnAction(evt -> {
                try {
                    Desktop.getDesktop().browse(URI.create("https://www.youtube.com/watch?v=" + video.getId()));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
        }
    }

    protected Node createFrontTop() {
        CustomImageView imageView = new CustomImageView();
        imageView.imageProperty().bind(imageProperty());
        imageView.setPreserveRatio(true);

        Label remarkLabel = new Label();
        remarkLabel.getStyleClass().add("remark");
        remarkLabel.setGraphic(new FontIcon());
        remarkLabel.textProperty().bind(remarkProperty());
        remarkLabel.managedProperty().bind(remarkLabel.visibleProperty());
        remarkLabel.visibleProperty().bind(remarkProperty().isNotEmpty());
        StackPane.setAlignment(remarkLabel, Pos.TOP_RIGHT);

        StackPane imageContainer = new StackPane();
        imageContainer.getStyleClass().add("image-container");
        imageContainer.getChildren().setAll(imageView, remarkLabel);
        if (isSmall()) {
            StackPane.setAlignment(imageView, Pos.BOTTOM_LEFT);
            imageView.fitWidthProperty().bind(Bindings.createDoubleBinding(() -> 140d));
        }

        if (isMedium()) {
            Rectangle rectClip = new Rectangle();
            rectClip.widthProperty().bind(imageContainer.widthProperty());
            rectClip.heightProperty().bind(imageContainer.heightProperty());
            imageContainer.setClip(rectClip);
        }

        parentProperty().addListener((ob, ov, node) -> {
            Pane parent = (Pane) node;
            if (node != null) {
                parent.widthProperty().addListener((ob1, ov1, newWidth) -> {
                    switch (getSize()) {
                        case SMALL -> {
                            imageView.fitWidthProperty().bind(Bindings.createDoubleBinding(() -> 140d));
                            StackPane.setAlignment(imageView, Pos.BOTTOM_LEFT);
                        }
                        case MEDIUM -> imageView.fitWidthProperty().bind(
                                parent.widthProperty()
                                        .subtract(30 * 2)//padding
                                        .subtract(20 * 2)//hgaps
                                        .divide(3.3));

                        case LARGE -> imageView.fitWidthProperty().bind(imageContainer.widthProperty());

                    }
                });
            }
        });

        return imageContainer;
    }

}
