package com.dlsc.jfxcentral2.components.skins;

import com.dlsc.jfxcentral2.components.SaveAndLikeButton;
import com.dlsc.jfxcentral2.iconfont.JFXCentralIcon;
import com.dlsc.jfxcentral2.utils.IkonUtil;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.HBox;
import org.kordamp.ikonli.javafx.FontIcon;

public class SaveAndLikeButtonSkin extends ControlBaseSkin<SaveAndLikeButton> {

    private final HBox content;

    public SaveAndLikeButtonSkin(SaveAndLikeButton control) {
        super(control);

        ToggleButton saveButton = new ToggleButton();
        saveButton.setGraphic(new FontIcon(IkonUtil.floppy));
        saveButton.textProperty().bind(control.saveButtonTextProperty());
        saveButton.visibleProperty().bind(control.saveButtonVisibleProperty());
        saveButton.managedProperty().bind(control.saveButtonVisibleProperty());
        saveButton.getStyleClass().add("save-button");
        saveButton.setFocusTraversable(false);
        saveButton.selectedProperty().bindBidirectional(control.saveButtonSelectedProperty());

        ToggleButton likeButton = new ToggleButton();
        likeButton.setGraphic(new FontIcon(JFXCentralIcon.HEART));
        likeButton.textProperty().bind(control.likeButtonTextProperty());
        likeButton.visibleProperty().bind(control.likeButtonVisibleProperty());
        likeButton.managedProperty().bind(control.likeButtonVisibleProperty());
        likeButton.getStyleClass().add("like-button");
        likeButton.setFocusTraversable(false);
        likeButton.selectedProperty().bindBidirectional(control.likeButtonSelectedProperty());

        content = new HBox();
        content.getChildren().setAll(saveButton, likeButton);
        content.getStyleClass().add("content");

        getChildren().setAll(content);
    }

    @Override
    protected double computePrefWidth(double height, double topInset, double rightInset, double bottomInset, double leftInset) {
        return content.prefWidth(height) + leftInset + rightInset;
    }

    @Override
    protected double computeMaxWidth(double height, double topInset, double rightInset, double bottomInset, double leftInset) {
        return computePrefWidth(height, topInset, rightInset, bottomInset, leftInset);
    }

    @Override
    protected double computeMinWidth(double height, double topInset, double rightInset, double bottomInset, double leftInset) {
        return computePrefWidth(height, topInset, rightInset, bottomInset, leftInset);
    }

    @Override
    protected double computePrefHeight(double width, double topInset, double rightInset, double bottomInset, double leftInset) {
        return content.prefHeight(width) + topInset + bottomInset;
    }

    @Override
    protected double computeMaxHeight(double width, double topInset, double rightInset, double bottomInset, double leftInset) {
        return computePrefHeight(width, topInset, rightInset, bottomInset, leftInset);
    }

    @Override
    protected double computeMinHeight(double width, double topInset, double rightInset, double bottomInset, double leftInset) {
        return super.computePrefHeight(width, topInset, rightInset, bottomInset, leftInset);
    }
}
