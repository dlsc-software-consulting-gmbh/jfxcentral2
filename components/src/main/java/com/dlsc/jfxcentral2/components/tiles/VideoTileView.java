package com.dlsc.jfxcentral2.components.tiles;

import com.dlsc.jfxcentral.data.ImageManager;
import com.dlsc.jfxcentral.data.model.Video;
import com.dlsc.jfxcentral2.components.CustomImageView;
import com.dlsc.jfxcentral2.iconfont.JFXCentralIcon;
import com.dlsc.jfxcentral2.utils.IkonUtil;
import javafx.beans.binding.Bindings;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import one.jpro.platform.routing.LinkUtil;
import org.kordamp.ikonli.javafx.FontIcon;

public class VideoTileView extends TileView<Video> {

    public VideoTileView(Video video) {
        super(video);

        getStyleClass().add("video-tile-view");

        imageProperty().bind(ImageManager.getInstance().youTubeImageProperty(video));

        setButton1Text("PLAY");
        setButton1Graphic(new FontIcon(IkonUtil.play));

        setButton2Text("YouTube");
        setButton2Graphic(new FontIcon(JFXCentralIcon.YOUTUBE));

        setRemark(video.getMinutes() + " mins");

        LinkUtil.setExternalLink(getButton2(), "https://www.youtube.com/watch?v=" + video.getId());
    }

    protected Node createFrontTop() {
        CustomImageView imageView = new CustomImageView();
        imageView.setCursor(Cursor.HAND);
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
                        case MEDIUM -> {
                            GridPane gridPane = (GridPane) parent;
                            Pane gridPaneParent = (Pane) gridPane.getParent().getParent().getParent();

                            imageView.fitWidthProperty().bind(Bindings.createDoubleBinding(() -> {
                                        double width = parent.getWidth();
                                        double horInsets = gridPaneParent.getInsets().getLeft() + gridPaneParent.getInsets().getRight();
                                        double hgap = gridPane.getHgap() * 2;
                                        return (width - horInsets - hgap) / 3.3;
                                    },
                                    gridPane.hgapProperty(),
                                    gridPaneParent.insetsProperty(),
                                    parent.widthProperty()
                            ));
                        }

                        case LARGE -> imageView.fitWidthProperty().bind(Bindings.createDoubleBinding(()->400d));

                    }
                });
            }
        });

        return imageContainer;
    }

}
