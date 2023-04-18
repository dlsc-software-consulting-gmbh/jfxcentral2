package com.dlsc.jfxcentral2.components;

import com.dlsc.jfxcentral2.components.skins.SaveAndLikeButtonSkin;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.Control;
import javafx.scene.control.Skin;

public class SaveAndLikeButton extends Control {
    public SaveAndLikeButton() {
        getStyleClass().add("save-and-like-button");
    }

    @Override
    protected Skin<?> createDefaultSkin() {
        return new SaveAndLikeButtonSkin(this);
    }

    private final StringProperty saveButtonText = new SimpleStringProperty(this, "saveButtonText", "Save");

    public String getSaveButtonText() {
        return saveButtonText.get();
    }

    public StringProperty saveButtonTextProperty() {
        return saveButtonText;
    }

    public void setSaveButtonText(String saveButtonText) {
        this.saveButtonText.set(saveButtonText);
    }

    private final StringProperty likeButtonText = new SimpleStringProperty(this, "likeButtonText", "Like");

    public String getLikeButtonText() {
        return likeButtonText.get();
    }

    public StringProperty likeButtonTextProperty() {
        return likeButtonText;
    }

    public void setLikeButtonText(String likeButtonText) {
        this.likeButtonText.set(likeButtonText);
    }

    private final BooleanProperty saveButtonSelected = new SimpleBooleanProperty(this, "saveButtonSelected", false);

    public boolean getSaveButtonSelected() {
        return saveButtonSelected.get();
    }

    public BooleanProperty saveButtonSelectedProperty() {
        return saveButtonSelected;
    }

    public void setSaveButtonSelected(boolean saveButtonSelected) {
        this.saveButtonSelected.set(saveButtonSelected);
    }

    private final BooleanProperty likeButtonSelected = new SimpleBooleanProperty(this, "likeButtonSelected", false);

    public boolean getLikeButtonSelected() {
        return likeButtonSelected.get();
    }

    public BooleanProperty likeButtonSelectedProperty() {
        return likeButtonSelected;
    }

    public void setLikeButtonSelected(boolean likeButtonSelected) {
        this.likeButtonSelected.set(likeButtonSelected);
    }

    private final BooleanProperty saveButtonVisible = new SimpleBooleanProperty(this, "saveButtonVisible", true);

    public boolean isSaveButtonVisible() {
        return saveButtonVisible.get();
    }

    public BooleanProperty saveButtonVisibleProperty() {
        return saveButtonVisible;
    }

    public void setSaveButtonVisible(boolean saveButtonVisible) {
        this.saveButtonVisible.set(saveButtonVisible);
    }

    private final BooleanProperty likeButtonVisible = new SimpleBooleanProperty(this, "likeButtonVisible", true);

    public boolean isLikeButtonVisible() {
        return likeButtonVisible.get();
    }

    public BooleanProperty likeButtonVisibleProperty() {
        return likeButtonVisible;
    }

    public void setLikeButtonVisible(boolean likeButtonVisible) {
        this.likeButtonVisible.set(likeButtonVisible);
    }
}
