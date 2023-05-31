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
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import org.kordamp.ikonli.javafx.FontIcon;

import java.util.List;

public class LibraryPreviewBox extends PaneBase {

    public LibraryPreviewBox() {
        getStyleClass().add("library-preview-box");

        libraryInfoProperty().addListener(it -> layoutBySize());

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
            if (images == null || images.isEmpty()) {
                return;
            }
            CustomImageView largerImageView = new CustomImageView();
            largerImageView.getStyleClass().add("larger-image");

            Button closeButton = new Button();
            closeButton.setGraphic(new FontIcon(IkonUtil.close));
            closeButton.getStyleClass().addAll("fill-button", "close-button");
            closeButton.setFocusTraversable(false);
            StackPane.setAlignment(closeButton, Pos.TOP_RIGHT);

            StackPane largerImageWrapper = new StackPane();
            largerImageWrapper.getStyleClass().add("larger-image-wrapper");
            largerImageWrapper.getChildren().addAll(largerImageView, closeButton);
            largerImageWrapper.managedProperty().bind(largerImageWrapper.visibleProperty());
            largerImageWrapper.visibleProperty().bind(largerImageView.imageProperty().isNotNull());

            HBox previewsBox = new HBox();
            previewsBox.getStyleClass().add("previews-box");

            com.dlsc.gemsfx.StripView<com.dlsc.jfxcentral.data.model.Image> stripView = new com.dlsc.gemsfx.StripView<>();
            HBox.setHgrow(stripView, Priority.ALWAYS);
            stripView.getStyleClass().add("previews-strip-view");
            stripView.setFadingSize(10);
            stripView.setCellFactory(param -> new com.dlsc.gemsfx.StripView.StripCell<>() {
                {
                    setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
                    CustomImageView imageView = new CustomImageView();
                    StackPane imageWrapper = new StackPane(imageView);
                    imageWrapper.getStyleClass().add("image-wrapper");
                    setGraphic(imageWrapper);
                    imageWrapper.setOnMouseClicked(event -> {
                        if (largerImageView.getUserData() == getItem()) {
                            largerImageView.setImage(null);
                            largerImageView.setUserData(null);
                            stripView.getStyleClass().remove("selected");
                        } else {
                            largerImageView.setUserData(getItem());
                            largerImageView.setImage(imageView.getImage());
                            if (!stripView.getStyleClass().contains("selected")) {
                                stripView.getStyleClass().add("selected");
                            }
                        }
                    });

                    itemProperty().addListener(it -> {
                        imageView.imageProperty().bind(ImageManager.getInstance().libraryImageProperty(library, getItem().getPath()));
                    });
                }
            });

            stripView.setMaxWidth(Double.MAX_VALUE);
            stripView.getItems().setAll(images);
            closeButton.setOnAction(event -> {
                largerImageView.setImage(null);
                largerImageView.setUserData(null);
                stripView.getStyleClass().remove("selected");
            });

            previewsBox.getChildren().add(stripView);

            int max = isSmall() ? 2 : 7 - (isMedium() ? 1 : 0);
            if (images.size() > max) {
                Label moreLabel = new Label("+" + (images.size() - max));
                moreLabel.getStyleClass().add("more-label");
                previewsBox.getChildren().addAll(new Spacer(), moreLabel);
                moreLabel.managedProperty().bind(moreLabel.visibleProperty());
                moreLabel.visibleProperty().bind(largerImageView.imageProperty().isNull());
            }

            VBox contentBox = new VBox(previewsBox, largerImageWrapper);
            contentBox.getStyleClass().add("content-box");
            getChildren().setAll(contentBox);
        }
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
