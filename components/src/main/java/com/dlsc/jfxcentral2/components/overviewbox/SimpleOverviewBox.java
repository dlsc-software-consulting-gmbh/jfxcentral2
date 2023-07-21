package com.dlsc.jfxcentral2.components.overviewbox;

import com.dlsc.jfxcentral.data.model.ModelObject;
import com.dlsc.jfxcentral2.components.CustomImageView;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;

/**
 * SimpleOverviewBox: DownloadOverviewBox and TutorialOverviewBox
 *
 * @param <T>
 */
public class SimpleOverviewBox<T extends ModelObject> extends OverviewBox<T> {

    public SimpleOverviewBox(T model) {
        super(model);
        getStyleClass().add("simple-overview-box");
    }

    @Override
    protected Node createTopNode() {
        CustomImageView previewImage = new CustomImageView();
        previewImage.imageProperty().bind(imageProperty());
        previewImage.getStyleClass().add("preview-image");

        StackPane imageWrapper = new StackPane(previewImage);
        imageWrapper.getStyleClass().add("image-wrapper");
        imageWrapper.visibleProperty().bind(previewImage.imageProperty().isNotNull());
        imageWrapper.managedProperty().bind(previewImage.imageProperty().isNotNull());

        return imageWrapper;
    }
}
