package com.dlsc.jfxcentral2.components;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.image.ImageView;

/// An ImageView whose fit size has its own properties. The desktop app set them
/// from CSS (`-fx-fit-width`, `-fx-fit-height`) through the styleable property
/// API, which the Codename One JavaFX layer does not have; the mobile views set
/// them in code instead.
public class CustomImageView extends ImageView {

    private static final double DEFAULT_HEIGHT = -1;
    private static final double DEFAULT_WIDTH = -1;

    public CustomImageView() {
        getStyleClass().add("custom-image-view");
        setPreserveRatio(true);
        fitWidthProperty().bind(fittingWidthProperty());
        fitHeightProperty().bind(fittingHeightProperty());
    }

    private final DoubleProperty fittingHeight = new SimpleDoubleProperty(this, "fittingHeight", DEFAULT_HEIGHT);

    public double getFittingHeight() {
        return  fittingHeight.get();
    }

    public DoubleProperty fittingHeightProperty() {
        return fittingHeight;
    }

    public void setFittingHeight(double fittingHeight) {
        this.fittingHeight.set(fittingHeight);
    }

    private final DoubleProperty fittingWidth = new SimpleDoubleProperty(this, "fittingWidth", DEFAULT_WIDTH);

    public double getFittingWidth() {
        return fittingWidth.get();
    }

    public DoubleProperty fittingWidthProperty() {
        return fittingWidth;
    }

    public void setFittingWidth(double fittingWidth) {
        this.fittingWidth.set(fittingWidth);
    }

}
