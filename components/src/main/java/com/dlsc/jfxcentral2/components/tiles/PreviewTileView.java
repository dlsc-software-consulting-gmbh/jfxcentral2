package com.dlsc.jfxcentral2.components.tiles;

import com.dlsc.jfxcentral.data.model.ModelObject;
import com.dlsc.jfxcentral2.components.CustomImageView;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;

public class PreviewTileView<T extends ModelObject> extends TileView<T> {

    private StackPane imageContainer;

    public PreviewTileView(T model) {
        super(model);
        getStyleClass().add("preview-tile-view");
    }

    public StackPane getImageViewContainer() {
        return imageContainer;
    }

    protected Node createFrontTop() {
        CustomImageView imageView = new CustomImageView();
        imageView.setMouseTransparent(true);

        imageContainer = new StackPane();
        imageContainer.getStyleClass().add("image-container");
        imageContainer.getChildren().setAll(imageView);
        imageContainer.setCursor(Cursor.HAND);

        imageView.imageProperty().bind(imageProperty());
        imageView.fitWidthProperty().bind(imageContainer.widthProperty().subtract(getSizeReduction()));
        imageView.fitHeightProperty().bind(imageContainer.heightProperty().subtract(getSizeReduction()));

        return imageContainer;
    }

    protected int getSizeReduction() {
        return 0;
    }

}
