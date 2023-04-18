package com.dlsc.jfxcentral2.components.skins;

import com.dlsc.jfxcentral2.components.SaveAndLikeButton;
import javafx.scene.control.SkinBase;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.HBox;
import org.kordamp.ikonli.javafx.FontIcon;
import org.kordamp.ikonli.materialdesign.MaterialDesign;
import org.kordamp.ikonli.materialdesign2.MaterialDesignC;

public class SaveAndLikeButtonSkin extends SkinBase<SaveAndLikeButton> {

    private final HBox content;

    public SaveAndLikeButtonSkin(SaveAndLikeButton control) {
        super(control);

        ToggleButton saveButton = new ToggleButton();
        saveButton.setGraphic(new FontIcon(MaterialDesignC.CONTENT_SAVE_OUTLINE));
        saveButton.textProperty().bind(control.saveButtonTextProperty());
        saveButton.visibleProperty().bind(control.saveButtonVisibleProperty());
        saveButton.managedProperty().bind(control.saveButtonVisibleProperty());
        control.saveButtonSelectedProperty().bindBidirectional(saveButton.selectedProperty());
        saveButton.getStyleClass().add("save-button");

        ToggleButton likeButton = new ToggleButton();
        likeButton.setGraphic(new FontIcon(MaterialDesign.MDI_HEART_OUTLINE));
        likeButton.textProperty().bind(control.likeButtonTextProperty());
        likeButton.visibleProperty().bind(control.likeButtonVisibleProperty());
        likeButton.managedProperty().bind(control.likeButtonVisibleProperty());
        control.likeButtonSelectedProperty().bindBidirectional(likeButton.selectedProperty());
        likeButton.getStyleClass().add("like-button");

        content = new HBox();
        content.getChildren().setAll(saveButton, likeButton);
        content.getStyleClass().add("content");

        getChildren().setAll(content);
    }

    @Override
    protected double computeMaxWidth(double height, double topInset, double rightInset, double bottomInset, double leftInset) {
        return this.computePrefWidth(height, topInset, rightInset, bottomInset, leftInset);
    }

    @Override
    protected double computePrefWidth(double height, double topInset, double rightInset, double bottomInset, double leftInset) {
        return content.prefWidth(-1) + leftInset + rightInset;
    }

    @Override
    protected double computeMinWidth(double height, double topInset, double rightInset, double bottomInset, double leftInset) {
        return this.computePrefWidth(height, topInset, rightInset, bottomInset, leftInset);
    }

    @Override
    protected double computePrefHeight(double width, double topInset, double rightInset, double bottomInset, double leftInset) {
        return content.prefHeight(-1) + topInset + bottomInset;
    }

    @Override
    protected double computeMaxHeight(double width, double topInset, double rightInset, double bottomInset, double leftInset) {
        return this.computePrefHeight(width, topInset, rightInset, bottomInset, leftInset);
    }

    @Override
    protected double computeMinHeight(double width, double topInset, double rightInset, double bottomInset, double leftInset) {
        return super.computePrefHeight(width, topInset, rightInset, bottomInset, leftInset);
    }
}
