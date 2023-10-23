package com.dlsc.jfxcentral2.components;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.Control;
import javafx.scene.control.Skin;
import org.kordamp.ikonli.Ikon;
import org.kordamp.ikonli.materialdesign.MaterialDesign;

public class CustomSearchField extends Control {

    private static final String DEFAULT_STYLE_CLASS = "custom-search-field";

    public CustomSearchField() {
        this(null);
    }

    public CustomSearchField(String text) {
        setText(text);
        getStyleClass().add(DEFAULT_STYLE_CLASS);
    }

    public CustomSearchField(boolean round) {
        this();
        if (round) {
            getStyleClass().add("round");
        }
    }

    @Override
    protected Skin<?> createDefaultSkin() {
        return new CustomSearchFieldSkin(this);
    }

    private final ObjectProperty<Ikon> leftIcon = new SimpleObjectProperty<>(this, "leftIcon", MaterialDesign.MDI_MAGNIFY);

    public Ikon getLeftIcon() {
        return leftIcon.get();
    }

    public ObjectProperty<Ikon> leftIconProperty() {
        return leftIcon;
    }

    public void setLeftIcon(Ikon leftIcon) {
        this.leftIcon.set(leftIcon);
    }

    private final ObjectProperty<Runnable> leftIconAction = new SimpleObjectProperty<>(this, "leftIconAction");

    public Runnable getLeftIconAction() {
        return leftIconAction.get();
    }

    public ObjectProperty<Runnable> leftIconActionProperty() {
        return leftIconAction;
    }

    public void setLeftIconAction(Runnable leftIconAction) {
        this.leftIconAction.set(leftIconAction);
    }

    private final ObjectProperty<Ikon> rightIcon = new SimpleObjectProperty<>(this, "rightIcon", MaterialDesign.MDI_CLOSE);

    public Ikon getRightIcon() {
        return rightIcon.get();
    }

    public ObjectProperty<Ikon> rightIconProperty() {
        return rightIcon;
    }

    public void setRightIcon(Ikon rightIcon) {
        this.rightIcon.set(rightIcon);
    }

    private final ObjectProperty<Runnable> rightIconAction = new SimpleObjectProperty<>(this, "rightIconAction", () -> setText(""));

    public Runnable getRightIconAction() {
        return rightIconAction.get();
    }

    public ObjectProperty<Runnable> rightIconActionProperty() {
        return rightIconAction;
    }

    public void setRightIconAction(Runnable rightIconAction) {
        this.rightIconAction.set(rightIconAction);
    }

    private final StringProperty promptText = new SimpleStringProperty(this, "promptText");

    public String getPromptText() {
        return promptText.get();
    }

    public StringProperty promptTextProperty() {
        return promptText;
    }

    public void setPromptText(String promptText) {
        this.promptText.set(promptText);
    }

    private final StringProperty text = new SimpleStringProperty(this, "text");

    public String getText() {
        return text.get();
    }

    public StringProperty textProperty() {
        return text;
    }

    public void setText(String text) {
        this.text.set(text);
    }
}
