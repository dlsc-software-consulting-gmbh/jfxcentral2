package com.dlsc.jfxcentral2.components;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import org.kordamp.ikonli.javafx.FontIcon;
import org.kordamp.ikonli.materialdesign.MaterialDesign;

public class MobileSearchTextField extends BorderPane {

    public MobileSearchTextField() {
        getStyleClass().add("text-input");

        getStyleClass().add("mobile-search-text-field");

        TextField textField = new TextField();
        textField.textProperty().bindBidirectional(textProperty());
        textField.promptTextProperty().bind(promptText);

        HBox.setHgrow(textField, Priority.ALWAYS);

        FontIcon icon = new FontIcon(MaterialDesign.MDI_MAGNIFY);
        BorderPane.setAlignment(icon, Pos.CENTER);
        BorderPane.setMargin(textField, new Insets(0, 10, 0, 10));

        setLeft(icon);
        setCenter(textField);
    }

    private final StringProperty text = new SimpleStringProperty();

    public String getText() {
        return text.get();
    }

    public StringProperty textProperty() {
        return text;
    }

    public void setText(String text) {
        this.text.set(text);
    }

    public final void clear() {
        text.set("");
    }

    private final StringProperty promptText = new SimpleStringProperty();

    public String getPromptText() {
        return promptText.get();
    }

    public StringProperty promptTextProperty() {
        return promptText;
    }

    public void setPromptText(String promptText) {
        this.promptText.set(promptText);
    }
}
