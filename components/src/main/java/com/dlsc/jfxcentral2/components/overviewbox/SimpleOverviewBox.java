package com.dlsc.jfxcentral2.components.overviewbox;

import com.dlsc.jfxcentral.data.ImageManager;
import com.dlsc.jfxcentral.data.model.Download;
import com.dlsc.jfxcentral.data.model.ModelObject;
import com.dlsc.jfxcentral.data.model.Tutorial;
import com.dlsc.jfxcentral2.components.CustomImageView;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;

/**
 * SimpleOverviewBox: DownloadOverviewBox and TutorialOverviewBox
 *
 * @param <T>
 */
public class SimpleOverviewBox<T extends ModelObject> extends OverviewBox<T> {

    private CustomImageView previewImage;

    public SimpleOverviewBox(T data) {
        this();
        setData(data);
    }

    public SimpleOverviewBox() {
        getStyleClass().add("simple-overview-box");

        dataProperty().addListener(it -> fillData());
    }

    @Override
    protected Node createTopNode() {
        previewImage = new CustomImageView();
        previewImage.getStyleClass().add("preview-image");

        StackPane imageWrapper = new StackPane(previewImage);
        imageWrapper.getStyleClass().add("image-wrapper");

        fillData();
        return imageWrapper;
    }

    private void fillData() {
        T data = getData();
        setMarkdown(data == null ? "" : data.getDescription());

        if (previewImage.imageProperty().isBound()) {
            previewImage.imageProperty().unbind();
        }
        previewImage.setImage(null);

        if (data instanceof Download download) {
            previewImage.imageProperty().bind(ImageManager.getInstance().downloadBannerImageProperty(download));
        } else if (data instanceof Tutorial tutorial) {
            previewImage.imageProperty().bind(ImageManager.getInstance().tutorialImageProperty(tutorial));
        }
    }
}
