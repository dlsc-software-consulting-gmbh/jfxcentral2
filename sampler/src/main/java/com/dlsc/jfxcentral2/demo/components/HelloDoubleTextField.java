package com.dlsc.jfxcentral2.demo.components;

import com.dlsc.jfxcentral2.components.DoubleTextField;
import com.dlsc.jfxcentral2.demo.JFXCentralSampleBase;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class HelloDoubleTextField extends JFXCentralSampleBase {
    @Override
    protected Region createControl() {
        Label label = new Label("Input a double value:");

        DoubleTextField doubleTextField = new DoubleTextField();
        doubleTextField.setPromptText("Enter a double value");

        Label valueLabel = new Label();
        valueLabel.textProperty().bind(doubleTextField.valueProperty().asString("Value: %.2f"));
        valueLabel.getStyleClass().add("value-label");

        VBox container = new VBox( label, doubleTextField, valueLabel);
        container.getStyleClass().add("double-text-field-container");
        container.setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);

        StackPane wrapper = new StackPane(container);
        wrapper.getStylesheets().add(getClass().getResource("test.css").toExternalForm());
        return wrapper;
    }

    @Override
    public String getSampleName() {
        return "DoubleTextField";
    }
}
