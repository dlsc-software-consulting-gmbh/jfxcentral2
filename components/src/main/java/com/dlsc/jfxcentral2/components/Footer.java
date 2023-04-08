package com.dlsc.jfxcentral2.components;

import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class Footer extends HBox {

    public Footer() {
        getStyleClass().add("footer");
        getChildren().add(new Label("Footer comes here"));
    }
}
