package com.dlsc.jfxcentral2.components;

import com.dlsc.jfxcentral.data.DataRepository;
import com.dlsc.jfxcentral.data.ImageManager;
import com.dlsc.jfxcentral.data.model.Library;
import com.dlsc.jfxcentral.data.model.LibraryInfo;
import com.dlsc.jfxcentral2.utils.IkonUtil;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import org.kordamp.ikonli.javafx.FontIcon;

import java.util.List;

public class LibraryPreviewBox extends PaneBase {

    public LibraryPreviewBox() {
        getStyleClass().add("library-preview-box");

        libraryProperty().addListener(it -> {
            libraryInfoProperty().bind(DataRepository.getInstance().libraryInfoProperty(getLibrary()));
            layoutBySize();
        });

        libraryInfoProperty().addListener(it -> layoutBySize());
    }

    @Override
    protected void layoutBySize() {
        getChildren().clear();

        LibraryInfo info = getLibraryInfo();
        Library library = getLibrary();

        if (info != null && library != null) {
            List<com.dlsc.jfxcentral.data.model.Image> images = info.getImages();

            CustomImageView largerImageView = new CustomImageView();
            largerImageView.getStyleClass().add("larger-image");

            Button closeButton = new Button();
            closeButton.setGraphic(new FontIcon(IkonUtil.close));
            closeButton.getStyleClass().addAll("fill-button","close-button");
            closeButton.setOnAction(event -> largerImageView.setImage(null));
            StackPane.setAlignment(closeButton, Pos.TOP_RIGHT);

            StackPane largerImageWrapper = new StackPane();
            largerImageWrapper.getStyleClass().add("larger-image-wrapper");
            largerImageWrapper.getChildren().addAll(largerImageView, closeButton);
            largerImageWrapper.managedProperty().bind(largerImageWrapper.visibleProperty());
            largerImageWrapper.visibleProperty().bind(largerImageView.imageProperty().isNotNull());

            com.dlsc.gemsfx.StripView<com.dlsc.jfxcentral.data.model.Image> stripView = new com.dlsc.gemsfx.StripView <>();
            stripView.getStyleClass().add("previews-strip-view");
            stripView.setFadingSize(200);
            stripView.setCellFactory(param -> new com.dlsc.gemsfx.StripView.StripCell<>(){
                {
                    setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
                    CustomImageView imageView = new CustomImageView();
                    imageView.setFittingWidth(100);
                    imageView.setFittingHeight(100);
                    imageView.imageProperty().bind(ImageManager.getInstance().libraryImageProperty(library, getItem().getPath()));
                    StackPane imageWrapper = new StackPane(imageView);
                    imageWrapper.getStyleClass().add("image-wrapper");
                    setGraphic(imageWrapper);
                    imageWrapper.setOnMouseClicked(event ->
                            largerImageView.setImage(largerImageView.getImage() == imageView.getImage() ? null : imageView.getImage()));
                }
            });

            stripView.getItems().setAll(images);
            VBox contentBox = new VBox(stripView, largerImageWrapper);
            contentBox.getStyleClass().add("content-box");
            getChildren().setAll(contentBox);
        }

        //////add test images
        //int max = isSmall() ? 2 : 7 - (isMedium() ? 1 : 0);
        //List<javafx.scene.image.Image> previews = List.of(
        //        new javafx.scene.image.Image(getClass().getResource("/com/dlsc/jfxcentral2/demoimages/details-box-preview0.png").toExternalForm()),
        //        new javafx.scene.image.Image(getClass().getResource("/com/dlsc/jfxcentral2/demoimages/details-box-preview0.png").toExternalForm()),
        //        new javafx.scene.image.Image(getClass().getResource("/com/dlsc/jfxcentral2/demoimages/details-box-preview0.png").toExternalForm()),
        //        new javafx.scene.image.Image(getClass().getResource("/com/dlsc/jfxcentral2/demoimages/details-box-preview0.png").toExternalForm()),
        //        new javafx.scene.image.Image(getClass().getResource("/com/dlsc/jfxcentral2/demoimages/details-box-preview0.png").toExternalForm()),
        //        new javafx.scene.image.Image(getClass().getResource("/com/dlsc/jfxcentral2/demoimages/details-box-preview0.png").toExternalForm()),
        //        new javafx.scene.image.Image(getClass().getResource("/com/dlsc/jfxcentral2/demoimages/details-box-preview0.png").toExternalForm()),
        //        new javafx.scene.image.Image(getClass().getResource("/com/dlsc/jfxcentral2/demoimages/details-box-preview0.png").toExternalForm()),
        //        new javafx.scene.image.Image(getClass().getResource("/com/dlsc/jfxcentral2/demoimages/details-box-preview0.png").toExternalForm()),
        //        new javafx.scene.image.Image(getClass().getResource("/com/dlsc/jfxcentral2/demoimages/details-box-preview0.png").toExternalForm())
        //);
        //
        //CustomImageView largerImageView = new CustomImageView();
        //largerImageView.getStyleClass().add("larger-image");
        //
        //Button closeButton = new Button();
        //closeButton.setGraphic(new FontIcon(IkonUtil.close));
        //closeButton.getStyleClass().addAll("fill-button","close-button");
        //closeButton.setOnAction(event -> largerImageView.setImage(null));
        //StackPane.setAlignment(closeButton, Pos.TOP_RIGHT);
        //
        //StackPane largerImageWrapper = new StackPane();
        //largerImageWrapper.getStyleClass().add("larger-image-wrapper");
        //largerImageWrapper.getChildren().addAll(largerImageView, closeButton);
        //largerImageWrapper.managedProperty().bind(largerImageWrapper.visibleProperty());
        //largerImageWrapper.visibleProperty().bind(largerImageView.imageProperty().isNotNull());
        //
        //com.dlsc.gemsfx.StripView<javafx.scene.image.Image> stripView = new com.dlsc.gemsfx.StripView<>();
        //stripView.getStyleClass().add("previews-strip-view");
        //stripView.setFadingSize(30);
        //stripView.setCellFactory(param -> new com.dlsc.gemsfx.StripView.StripCell<>(){
        //    {
        //        setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        //        CustomImageView imageView = new CustomImageView();
        //        imageView.setFittingWidth(100);
        //        imageView.setFittingHeight(100);
        //        imageView.imageProperty().bind(itemProperty());
        //        StackPane imageWrapper = new StackPane(imageView);
        //        imageWrapper.getStyleClass().add("image-wrapper");
        //        setGraphic(imageWrapper);
        //        imageWrapper.setOnMouseClicked(event -> {
        //            largerImageView.setImage(largerImageView.getImage() == getItem() ? null : getItem());
        //        });
        //    }
        //});
        //
        //stripView.getItems().setAll(previews);
        //VBox contentBox = new VBox(stripView, largerImageWrapper);
        //contentBox.getStyleClass().add("content-box");
        //getChildren().setAll(contentBox);
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
