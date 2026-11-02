package com.dlsc.jfxcentral2.components;

import javafx.scene.Node;
import javafx.scene.control.ToggleButton;

public class CustomToggleButton extends ToggleButton {

    public CustomToggleButton() {
        super();
    }

    public CustomToggleButton(String text) {
        super(text);
    }

    public CustomToggleButton(String text, Node graphic) {
        super(text, graphic);
    }

    @Override
    public void fire() {
        if (getToggleGroup() == null || !isSelected()) {
            super.fire();
        }
    }
}