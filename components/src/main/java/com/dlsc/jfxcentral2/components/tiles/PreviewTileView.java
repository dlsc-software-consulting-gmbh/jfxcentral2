package com.dlsc.jfxcentral2.components.tiles;

import com.dlsc.jfxcentral.data.model.ModelObject;
import com.dlsc.jfxcentral2.components.CustomImageView;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;

public class PreviewTileView<T extends ModelObject> extends TileView<T> {

    public PreviewTileView(T model) {
        super(model);
        getStyleClass().add("preview-tile-view");
    }

    protected Node createFrontTop() {
        CustomImageView logoView = new CustomImageView();
        logoView.imageProperty().bind(imageProperty());

        StackPane imageContainer = new StackPane();
        imageContainer.getStyleClass().add("image-container");
        imageContainer.getChildren().setAll(logoView);
        logoView.fitWidthProperty().bind(imageContainer.widthProperty().subtract(getSizeReduction()));
        logoView.fitHeightProperty().bind(imageContainer.heightProperty().subtract(getSizeReduction()));

        return imageContainer;
    }

    protected int getSizeReduction() {
        return 0;
    }

}
