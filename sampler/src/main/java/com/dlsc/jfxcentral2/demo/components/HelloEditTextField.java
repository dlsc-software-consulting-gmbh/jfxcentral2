package com.dlsc.jfxcentral2.demo.components;

import com.dlsc.jfxcentral2.components.EditTextField;
import com.dlsc.jfxcentral2.demo.JFXCentralSampleBase;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

public class HelloEditTextField extends JFXCentralSampleBase {
    @Override
    protected Region createControl() {
        EditTextField editTextField = new EditTextField();
        editTextField.setPromptText("Enter text here");
        editTextField.setSaveButtonText("SAVE CHANGES");
        editTextField.setEditButtonText("");
        editTextField.setPromptText("Enter name here");
        editTextField.setFailedMessage("Username too short. Min 3 chars");
        editTextField.setTitle("User name");
        //Only when the conditions are met can it be saved;
        // only when the save button is clicked
        // or the Enter key is pressed in the text box
        editTextField.setValidator(param -> param.length() >= 3);
        editTextField.setOnSave(param -> System.out.println("saved..."));

        return new VBox(editTextField);
    }

    @Override
    public String getSampleName() {
        return "EditTextField";
    }
}
