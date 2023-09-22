package com.dlsc.jfxcentral2.devtools.pathextractor;

import com.dlsc.jfxcentral2.components.CustomImageView;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.layout.StackPane;

public class RecentImageView extends StackPane {

    public RecentImageView() {
        getStyleClass().add("recent-image-view");
        CustomImageView imageView = new CustomImageView();
        imageView.getStyleClass().add("recent-image");
        getChildren().add(imageView);

        imageView.imageProperty().bind(imageResultProperty().map(SvgToImageUtil.ImageResult::image));
    }

    private final ObjectProperty<SvgToImageUtil.ImageResult> imageResult = new SimpleObjectProperty<>(this, "imageResult");

    public SvgToImageUtil.ImageResult getImageResult() {
        return imageResult.get();
    }

    public ObjectProperty<SvgToImageUtil.ImageResult> imageResultProperty() {
        return imageResult;
    }

    public void setImageResult(SvgToImageUtil.ImageResult imageResult) {
        this.imageResult.set(imageResult);
    }

}
