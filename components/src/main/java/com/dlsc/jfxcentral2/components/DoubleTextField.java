package com.dlsc.jfxcentral2.components;

import javafx.beans.binding.Bindings;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.beans.property.ReadOnlyDoubleWrapper;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;

import java.util.Set;

/**
 * A text field that only accepts double values.
 */
public class DoubleTextField extends TextField {
    private static final Set<String> SPECIAL_CASES = Set.of("-", ".", "-.", "+", "+.");

    public DoubleTextField() {

        textFormatterProperty().bind(Bindings.createObjectBinding(() -> new TextFormatter<>(change -> {
            String newText = change.getControlNewText();
            if (newText.matches("[-+]?[0-9]*\\.?[0-9]*")) {
                return change;
            }
            return null;
        })));

        value.bind(Bindings.createDoubleBinding(() -> {
            String text = getText();
            if (text == null || text.isEmpty() || SPECIAL_CASES.contains(text)) {
                return 0.0;
            }
            return Double.parseDouble(text);
        }, textProperty()));
    }

    private final ReadOnlyDoubleWrapper value = new ReadOnlyDoubleWrapper(this, "value");

    public double getValue() {
        return value.get();
    }

    public ReadOnlyDoubleProperty valueProperty() {
        return value.getReadOnlyProperty();
    }

}
