package com.dlsc.jfxcentral2.components;

import com.dlsc.jfxcentral.data.model.Library;
import com.dlsc.jfxcentral.data.model.LibraryInfo;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;

import java.util.List;

public class LibraryPreviewBox extends PaneBase {

    public LibraryPreviewBox() {
        getStyleClass().add("library-preview-box");

        libraryProperty().addListener(it -> layoutBySize());
        libraryInfoProperty().addListener(it -> layoutBySize());
    }

    @Override
    protected void layoutBySize() {
        HBox previewsBox = new HBox();
        previewsBox.getStyleClass().add("content-box");

        //LibraryInfo info = getLibraryInfo();
        //Library library = getLibrary();
        //if (info != null && library != null) {
        //    List< com.dlsc.jfxcentral.data.model.Image> images = info.getImages();
        //    int max = isSmall() ? 2 : 7 - (isMedium() ? 1 : 0);
        //    for (int i = 0; i < images.size() && i < max; i++) {
        //        com.dlsc.jfxcentral.data.model.Image image = images.get(i);
        //        CustomImageView imageView = new CustomImageView();
        //        //if (isSmall()) {
        //        //    StackPane.setAlignment(imageView, Pos.CENTER_LEFT);
        //       //}
        //        StackPane imageWrapper = new StackPane(imageView);
        //        imageWrapper.getStyleClass().add("image-wrapper");
        //        imageView.imageProperty().bind(ImageManager.getInstance().libraryImageProperty(library, image.getPath()));
        //        previewsBox.getChildren().add(imageWrapper);
        //    }
        //
        //    if (images.size() > max) {
        //        Label moreLabel = new Label("+" + (images.size() - max));
        //        moreLabel.getStyleClass().add("more-label");
        //        previewsBox.getChildren().add(moreLabel);
        //    }
        //}
        //  getChildren().setAll(previewsBox);

        //add test images
        int max = isSmall() ? 2 : 7 - (isMedium() ? 1 : 0);
        List<Image> previews = List.of(
                new Image(getClass().getResource("/com/dlsc/jfxcentral2/demoimages/details-box-preview0.png").toExternalForm()),
                new Image(getClass().getResource("/com/dlsc/jfxcentral2/demoimages/details-box-preview0.png").toExternalForm()),
                new Image(getClass().getResource("/com/dlsc/jfxcentral2/demoimages/details-box-preview0.png").toExternalForm()),
                new Image(getClass().getResource("/com/dlsc/jfxcentral2/demoimages/details-box-preview0.png").toExternalForm()),
                new Image(getClass().getResource("/com/dlsc/jfxcentral2/demoimages/details-box-preview0.png").toExternalForm()),
                new Image(getClass().getResource("/com/dlsc/jfxcentral2/demoimages/details-box-preview0.png").toExternalForm()),
                new Image(getClass().getResource("/com/dlsc/jfxcentral2/demoimages/details-box-preview0.png").toExternalForm()),
                new Image(getClass().getResource("/com/dlsc/jfxcentral2/demoimages/details-box-preview0.png").toExternalForm()),
                new Image(getClass().getResource("/com/dlsc/jfxcentral2/demoimages/details-box-preview0.png").toExternalForm()),
                new Image(getClass().getResource("/com/dlsc/jfxcentral2/demoimages/details-box-preview0.png").toExternalForm())
        );
        for (int i = 0; i < previews.size() && i < max; i++) {
            CustomImageView imageView = new CustomImageView();
            imageView.setImage(previews.get(i));
            StackPane imageWrapper = new StackPane(imageView);
            imageWrapper.getStyleClass().add("image-wrapper");
            previewsBox.getChildren().add(imageWrapper);
        }
        if (previews.size() > max) {
            Label moreLabel = new Label("+" + (previews.size() - max));
            moreLabel.getStyleClass().add("more-label");
            previewsBox.getChildren().add(moreLabel);
        }

        getChildren().setAll(previewsBox);
    }

    private final ObjectProperty<LibraryInfo> libraryInfo = new SimpleObjectProperty<>(this, "libraryInfo");

    public LibraryInfo getLibraryInfo() {
        return libraryInfo.get();
    }

    public ObjectProperty<LibraryInfo> libraryInfoProperty() {
        return libraryInfo;
    }

    public void setLibraryInfo(LibraryInfo libraryInfo) {
        this.libraryInfo.set(libraryInfo);
    }

    private final ObjectProperty<Library> library = new SimpleObjectProperty<>(this, "library");

    public Library getLibrary() {
        return library.get();
    }

    public ObjectProperty<Library> libraryProperty() {
        return library;
    }

    public void setLibrary(Library library) {
        this.library.set(library);
    }
}
