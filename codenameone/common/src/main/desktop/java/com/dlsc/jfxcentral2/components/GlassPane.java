package com.dlsc.jfxcentral2.components;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.layout.Region;

/// A pane laid over the content to catch clicks while a drawer is open, in
/// place of GemsFX's `GlassPane`. It is invisible, and lets clicks through,
/// while `hide` is true.
public class GlassPane extends Region {

    private final BooleanProperty hide = new SimpleBooleanProperty(this, "hide", true);
    private final DoubleProperty blockingOpacity = new SimpleDoubleProperty(this, "blockingOpacity", 0.5);

    public GlassPane() {
        getStyleClass().add("glass-pane");
        visibleProperty().bind(hide.not());
        styleProperty().bind(javafx.beans.binding.Bindings.createStringBinding(
                () -> "-fx-background-color: rgba(0,0,0," + blockingOpacity.get() + ");", blockingOpacity));
    }

    public final BooleanProperty hideProperty() {
        return hide;
    }

    public final void setHide(boolean value) {
        hide.set(value);
    }

    public final DoubleProperty blockingOpacityProperty() {
        return blockingOpacity;
    }

    public final void setBlockingOpacity(double value) {
        blockingOpacity.set(value);
    }
}
