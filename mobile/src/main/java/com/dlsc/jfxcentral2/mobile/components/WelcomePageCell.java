package com.dlsc.jfxcentral2.mobile.components;

import com.dlsc.jfxcentral2.components.CustomImageView;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class WelcomePageCell extends VBox {

    private static final String DEFAULT_STYLE_CLASS = "welcome-page-view-cell";

    public WelcomePageCell() {
        getStyleClass().add(DEFAULT_STYLE_CLASS);

        CustomImageView imageView = new CustomImageView();
        imageView.imageProperty().bind(imageProperty());

        StackPane imageWrapper = new StackPane(imageView);
        imageWrapper.getStyleClass().add("image-wrapper");

        Label titleLabel = new Label();
        titleLabel.getStyleClass().add("title");
        titleLabel.textProperty().bind(titleProperty());

        Label descriptionLabel = new Label();
        descriptionLabel.getStyleClass().add("description");
        descriptionLabel.textProperty().bind(descriptionProperty());
        descriptionLabel.setWrapText(true);

        getChildren().addAll(imageWrapper, titleLabel, descriptionLabel);
    }

    // image

    private final ObjectProperty<Image> image = new SimpleObjectProperty<>(this, "image");

    public final ObjectProperty<Image> imageProperty() {
        return image;
    }

    public final Image getImage() {
        return imageProperty().get();
    }

    public final void setImage(Image image) {
        imageProperty().set(image);
    }

    // title

    private final ObjectProperty<String> title = new SimpleObjectProperty<>(this, "title");

    public final ObjectProperty<String> titleProperty() {
        return title;
    }

    public final String getTitle() {
        return titleProperty().get();
    }

    public final void setTitle(String title) {
        titleProperty().set(title);
    }

    // description

    private final ObjectProperty<String> description = new SimpleObjectProperty<>(this, "description");

    public final ObjectProperty<String> descriptionProperty() {
        return description;
    }

    public final String getDescription() {
        return descriptionProperty().get();
    }

    public final void setDescription(String description) {
        descriptionProperty().set(description);
    }

}
