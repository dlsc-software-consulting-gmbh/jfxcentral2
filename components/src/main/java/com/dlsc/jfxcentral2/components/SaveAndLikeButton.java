package com.dlsc.jfxcentral2.components;

import com.dlsc.jfxcentral2.components.skins.SaveAndLikeButtonSkin;
import com.jpro.webapi.WebAPI;
import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.css.CssMetaData;
import javafx.css.SimpleStyleableBooleanProperty;
import javafx.css.Styleable;
import javafx.css.StyleableBooleanProperty;
import javafx.css.StyleableProperty;
import javafx.css.converter.BooleanConverter;
import javafx.scene.control.Skin;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SaveAndLikeButton extends ControlBase {
    private static final boolean DEFAULT_SHOW_COUNT = false;

    public SaveAndLikeButton() {
        getStyleClass().add("save-and-like-button");

        setFocusTraversable(false);

        /*
         * We currently only support liking and saving in the web application.
         */
        visibleProperty().bind(Bindings.createBooleanBinding(() -> (isSaveButtonVisible() || isLikeButtonVisible()) && WebAPI.isBrowser(), saveButtonVisibleProperty(), likeButtonVisibleProperty()));
        managedProperty().bind(Bindings.createBooleanBinding(() -> (isSaveButtonVisible() || isLikeButtonVisible()) && WebAPI.isBrowser(), saveButtonVisibleProperty(), likeButtonVisibleProperty()));
    }

    @Override
    protected Skin<?> createDefaultSkin() {
        return new SaveAndLikeButtonSkin(this);
    }

    private final StringProperty saveButtonText = new SimpleStringProperty(this, "saveButtonText", "Save");

    public final String getSaveButtonText() {
        return saveButtonText.get();
    }

    public final StringProperty saveButtonTextProperty() {
        return saveButtonText;
    }

    public final void setSaveButtonText(String saveButtonText) {
        this.saveButtonText.set(saveButtonText);
    }

    private final StringProperty likeButtonText = new SimpleStringProperty(this, "likeButtonText", "Like");

    public final String getLikeButtonText() {
        return likeButtonText.get();
    }

    public final StringProperty likeButtonTextProperty() {
        return likeButtonText;
    }

    public final void setLikeButtonText(String likeButtonText) {
        this.likeButtonText.set(likeButtonText);
    }

    private final BooleanProperty saveButtonSelected = new SimpleBooleanProperty(this, "saveButtonSelected", false);

    public final boolean getSaveButtonSelected() {
        return saveButtonSelected.get();
    }

    public final BooleanProperty saveButtonSelectedProperty() {
        return saveButtonSelected;
    }

    public final void setSaveButtonSelected(boolean saveButtonSelected) {
        this.saveButtonSelected.set(saveButtonSelected);
    }

    private final BooleanProperty likeButtonSelected = new SimpleBooleanProperty(this, "likeButtonSelected", false);

    public final boolean getLikeButtonSelected() {
        return likeButtonSelected.get();
    }

    public final BooleanProperty likeButtonSelectedProperty() {
        return likeButtonSelected;
    }

    public final void setLikeButtonSelected(boolean likeButtonSelected) {
        this.likeButtonSelected.set(likeButtonSelected);
    }

    private final BooleanProperty saveButtonVisible = new SimpleBooleanProperty(this, "saveButtonVisible", true);

    public final boolean isSaveButtonVisible() {
        return saveButtonVisible.get();
    }

    public final BooleanProperty saveButtonVisibleProperty() {
        return saveButtonVisible;
    }

    public void setSaveButtonVisible(boolean saveButtonVisible) {
        this.saveButtonVisible.set(saveButtonVisible);
    }

    private final BooleanProperty likeButtonVisible = new SimpleBooleanProperty(this, "likeButtonVisible", true);

    public final boolean isLikeButtonVisible() {
        return likeButtonVisible.get();
    }

    public final BooleanProperty likeButtonVisibleProperty() {
        return likeButtonVisible;
    }

    public void setLikeButtonVisible(boolean likeButtonVisible) {
        this.likeButtonVisible.set(likeButtonVisible);
    }

    private final IntegerProperty likeCount = new SimpleIntegerProperty(this, "likeCount", 0);

    public int getLikeCount() {
        return likeCount.get();
    }

    public IntegerProperty likeCountProperty() {
        return likeCount;
    }

    public void setLikeCount(int likeCount) {
        this.likeCount.set(likeCount);
    }

    private final IntegerProperty saveCount = new SimpleIntegerProperty(this, "saveCount", 0);

    public int getSaveCount() {
        return saveCount.get();
    }

    public IntegerProperty saveCountProperty() {
        return saveCount;
    }

    public void setSaveCount(int saveCount) {
        this.saveCount.set(saveCount);
    }

    private final StyleableBooleanProperty showCount = new SimpleStyleableBooleanProperty(SaveAndLikeButton.StyleableProperties.SHOW_COUNT, SaveAndLikeButton.this, "showCount", DEFAULT_SHOW_COUNT);

    public boolean isShowCount() {
        return showCount.get();
    }

    public StyleableBooleanProperty showCountProperty() {
        return showCount;
    }

    public void setShowCount(boolean showCount) {
        this.showCount.set(showCount);
    }

    private static class StyleableProperties {

        private static final CssMetaData<SaveAndLikeButton, Boolean> SHOW_COUNT = new CssMetaData<>(
                "-fx-show-count", BooleanConverter.getInstance(), DEFAULT_SHOW_COUNT) {

            @Override
            public StyleableProperty<Boolean> getStyleableProperty(SaveAndLikeButton control) {
                return control.showCountProperty();
            }

            @Override
            public boolean isSettable(SaveAndLikeButton control) {
                return !control.showCountProperty().isBound();
            }
        };

        private static final List<CssMetaData<? extends Styleable, ?>> STYLEABLES;

        static {
            List<CssMetaData<? extends Styleable, ?>> styleables = new ArrayList<>(ControlBase.getClassCssMetaData());
            Collections.addAll(styleables, SHOW_COUNT);
            STYLEABLES = Collections.unmodifiableList(styleables);
        }
    }

    @Override
    public List<CssMetaData<? extends Styleable, ?>> getControlCssMetaData() {
        return getClassCssMetaData();
    }

    public static List<CssMetaData<? extends Styleable, ?>> getClassCssMetaData() {
        return SaveAndLikeButton.StyleableProperties.STYLEABLES;
    }

}
