package com.dlsc.jfxcentral2.components;

import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

/// An empty region that takes the free space of an `HBox` or `VBox`, in
/// place of GemsFX's `Spacer`.
public class Spacer extends Region {

    public Spacer() {
        getStyleClass().add("spacer");
        HBox.setHgrow(this, Priority.ALWAYS);
        VBox.setVgrow(this, Priority.ALWAYS);
    }
}
