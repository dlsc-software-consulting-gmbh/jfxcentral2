package com.dlsc.jfxcentral2.components;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.geometry.Orientation;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class TitleAndDescriptionBox extends PaneBase {

    public TitleAndDescriptionBox() {
        getStyleClass().add("title-and-description-box");

        Label title = new Label();
        title.getStyleClass().add("title");
        title.managedProperty().bind(title.visibleProperty());
        title.visibleProperty().bind(title.textProperty().isNotEmpty());
        title.textProperty().bind(titleProperty());

        Label description = new Label();
        description.getStyleClass().add("description");
        description.setWrapText(true);
        description.managedProperty().bind(description.visibleProperty());
        description.visibleProperty().bind(description.textProperty().isNotEmpty());
        description.textProperty().bind(descriptionProperty());

        getChildren().setAll(new VBox(title, new Spacer(Orientation.VERTICAL), description));
    }

    @Override
    protected void layoutBySize() {

    }

    private final StringProperty title = new SimpleStringProperty(this, "title");

    public String getTitle() {
        return title.get();
    }

    public StringProperty titleProperty() {
        return title;
    }

    public void setTitle(String title) {
        this.title.set(title);
    }

    private final StringProperty description = new SimpleStringProperty(this, "description");

    public String getDescription() {
        return description.get();
    }

    public StringProperty descriptionProperty() {
        return description;
    }

    public void setDescription(String description) {
        this.description.set(description);
    }
}
