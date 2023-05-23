package com.dlsc.jfxcentral2.components;

import com.dlsc.jfxcentral2.components.skins.EditTextFieldSkin;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.ReadOnlyBooleanWrapper;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.css.PseudoClass;
import javafx.scene.control.Skin;
import javafx.util.Callback;

import java.util.function.Consumer;

public class EditTextField extends ControlBase {
    private static final Callback<String, Boolean> DEFAULT_VALIDATOR = param -> true;
    private static final PseudoClass VERIFICATION_FAILED_PSEUDOCLASS_STATE = PseudoClass.getPseudoClass("failed");

    public EditTextField() {
        getStyleClass().add("edit-text-field");
        activateFailedPseudoClass();
        textProperty().addListener(it -> activateFailedPseudoClass());
        validatorProperty().addListener(it -> activateFailedPseudoClass());

        valid.bind(Bindings.createBooleanBinding(() -> {
            Callback<String, Boolean> validator = getValidator();
            String text = getText();
            return validator == null ||  validator.call(text);
        }, validatorProperty(), textProperty()));
    }

    private void activateFailedPseudoClass() {
        pseudoClassStateChanged(VERIFICATION_FAILED_PSEUDOCLASS_STATE, isValid());
    }

    @Override
    protected Skin<?> createDefaultSkin() {
        return new EditTextFieldSkin(this);
    }

    private final ReadOnlyBooleanWrapper valid = new ReadOnlyBooleanWrapper(this, "valid");

    public boolean isValid() {
        return validProperty().get();
    }

    public ReadOnlyBooleanProperty validProperty() {
        return valid.getReadOnlyProperty();
    }

    private final StringProperty text = new SimpleStringProperty(this, "text", "");

    public String getText() {
        return text.get();
    }

    public StringProperty textProperty() {
        return text;
    }

    public void setText(String text) {
        this.text.set(text);
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

    private final StringProperty failedMessage = new SimpleStringProperty(this, "failedMessage");

    public String getFailedMessage() {
        return failedMessage.get();
    }

    public StringProperty failedMessageProperty() {
        return failedMessage;
    }

    public void setFailedMessage(String failedMessage) {
        this.failedMessage.set(failedMessage);
    }

    private final ObjectProperty<Callback<String, Boolean>> validator = new SimpleObjectProperty<>(this, "validator", DEFAULT_VALIDATOR);

    public Callback<String, Boolean> getValidator() {
        return validator.get();
    }

    public ObjectProperty<Callback<String, Boolean>> validatorProperty() {
        return validator;
    }

    public void setValidator(Callback<String, Boolean> validator) {
        this.validator.set(validator);
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

    private final StringProperty editButtonText = new SimpleStringProperty(this, "editButtonText", "Edit");

    public String getEditButtonText() {
        return editButtonText.get();
    }

    public StringProperty editButtonTextProperty() {
        return editButtonText;
    }

    public void setEditButtonText(String editButtonText) {
        this.editButtonText.set(editButtonText);
    }

    private final ObjectProperty<Consumer<String>> onSave = new SimpleObjectProperty<>(this, "onSave");

    public Consumer<String> getOnSave() {
        return onSave.get();
    }

    public ObjectProperty<Consumer<String>> onSaveProperty() {
        return onSave;
    }

    public void setOnSave(Consumer<String> onSave) {
        this.onSave.set(onSave);
    }
}
