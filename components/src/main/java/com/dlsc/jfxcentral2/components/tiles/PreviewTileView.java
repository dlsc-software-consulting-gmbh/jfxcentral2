package com.dlsc.jfxcentral2.components.tiles;

import com.dlsc.jfxcentral.data.model.ModelObject;
import com.dlsc.jfxcentral2.components.CustomImageView;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;

public class PreviewTileView<T extends ModelObject> extends TileView<T> {

    public PreviewTileView(T model) {
        super(model);
        getStyleClass().add("preview-tile-view");
    }

    protected Node createFrontTop() {
        CustomImageView imageView = new CustomImageView();
        imageView.setMouseTransparent(true);

        StackPane imageContainer = new StackPane();
        imageContainer.getStyleClass().add("image-container");
        imageContainer.getChildren().setAll(imageView);
        imageContainer.setCursor(Cursor.HAND);
        imageView.imageProperty().bind(imageProperty());
        imageViewSizeBinding(imageView, imageContainer);
        return imageContainer;
    }

    protected void imageViewSizeBinding(CustomImageView imageView, StackPane imageContainer) {
    }



}
