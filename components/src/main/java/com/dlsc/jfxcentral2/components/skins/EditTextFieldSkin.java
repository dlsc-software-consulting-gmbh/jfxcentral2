package com.dlsc.jfxcentral2.components.skins;

import com.dlsc.gemsfx.Spacer;
import com.dlsc.jfxcentral2.components.EditTextField;
import com.dlsc.jfxcentral2.utils.IkonUtil;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.kordamp.ikonli.javafx.FontIcon;
import org.kordamp.ikonli.materialdesign.MaterialDesign;

public class EditTextFieldSkin extends ControlBaseSkin<EditTextField> {


    public EditTextFieldSkin(EditTextField control) {
        super(control);
        BooleanProperty editState = new SimpleBooleanProperty(this, "editState", false);

        Label titleLabel = new Label();
        titleLabel.getStyleClass().add("title");
        titleLabel.textProperty().bind(control.titleProperty());

        Button saveButton = new Button();
        saveButton.setFocusTraversable(false);
        saveButton.getStyleClass().add("save-button");
        saveButton.managedProperty().bind(saveButton.visibleProperty());
        saveButton.visibleProperty().bind(editState.and(control.validProperty()));
        saveButton.textProperty().bind(control.saveButtonTextProperty());
        saveButton.setGraphic(new FontIcon(IkonUtil.floppy));
        saveButton.setOnAction(it -> {
           if (control.getOnSave()!=null) {
               control.getOnSave().accept(control.getText());
           }
           editState.set(false);
        });

        Button editButton = new Button("EDIT");
        editButton.setFocusTraversable(false);
        editButton.getStyleClass().add("edit-button");
        editButton.managedProperty().bind(editButton.visibleProperty());
        editButton.visibleProperty().bind(editState.not());
        editButton.textProperty().bind(control.editButtonTextProperty());
        editButton.setGraphic(new FontIcon(IkonUtil.edit));
        editButton.setOnAction(it -> editState.set(true));

        HBox topBox = new HBox(titleLabel, new Spacer(), saveButton, editButton);
        topBox.getStyleClass().add("top-box");

        TextField textField = new TextField();
        textField.setFocusTraversable(false);
        textField.textProperty().bindBidirectional(control.textProperty());
        textField.promptTextProperty().bind(control.promptTextProperty());
        textField.managedProperty().bind(textField.visibleProperty());
        textField.visibleProperty().bind(editState);
        textField.setOnAction(actionEvent -> {
            if (control.isValid()) {
                if (control.getOnSave()!=null) {
                    control.getOnSave().accept(control.getText());
                }
                editState.set(false);
            }
        });

        Label textLabel = new Label();
        textLabel.textProperty().bind(control.textProperty());
        textLabel.getStyleClass().add("text-label");
        textLabel.managedProperty().bind(textLabel.visibleProperty());
        textLabel.visibleProperty().bind(editState.not().and(control.textProperty().isNotEmpty()));

        Label promptLabel = new Label();
        promptLabel.getStyleClass().add("prompt-label");
        promptLabel.textProperty().bind(control.promptTextProperty());
        promptLabel.managedProperty().bind(promptLabel.visibleProperty());
        promptLabel.visibleProperty().bind(editState.not().and(control.textProperty().isEmpty()));

        HBox textContainer = new HBox(textField, textLabel, promptLabel);
        textContainer.getStyleClass().add("text-container");

        textContainer.setOnMousePressed(event -> {
            if (!editState.get()) {
                editState.set(true);
                textField.requestFocus();
            }
            event.consume();
        });

        Label failedLabel = new Label();
        failedLabel.getStyleClass().add("failed-label");
        failedLabel.setVisible(false);
        failedLabel.setGraphic(new FontIcon(MaterialDesign.MDI_ALERT));
        //failedLabel.managedProperty().bind(failedLabel.visibleProperty());
        failedLabel.textProperty().bind(control.failedMessageProperty());
        textField.focusedProperty().addListener(it -> {
            if (!failedLabel.visibleProperty().isBound()) {
                failedLabel.visibleProperty().bind(control.validProperty().not());
            }
        });

        HBox bottomBox = new HBox(failedLabel);
        bottomBox.getStyleClass().add("bottom-box");

        VBox contentBox = new VBox(topBox, textContainer, bottomBox);
        contentBox.getStyleClass().add("content-box");

        getChildren().setAll(contentBox);
    }
}
