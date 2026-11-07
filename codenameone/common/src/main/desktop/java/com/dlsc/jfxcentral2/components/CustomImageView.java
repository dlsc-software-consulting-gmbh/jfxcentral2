package com.dlsc.jfxcentral2.components;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.image.ImageView;

/// An ImageView whose fit size has its own properties. The desktop app binds the
/// fit size to them and sets them from CSS through the styleable property API,
/// which the Codename One JavaFX layer does not have. Its `ImageView` reads
/// `-fx-fit-width` and `-fx-fit-height` from CSS itself, so here the fit size
/// stays unbound for those rules, and a value set in code is copied onto it.
public class CustomImageView extends ImageView {

    private static final double DEFAULT_HEIGHT = -1;
    private static final double DEFAULT_WIDTH = -1;

    public CustomImageView() {
        getStyleClass().add("custom-image-view");
        setPreserveRatio(true);
        fittingWidth.addListener((obs, o, n) -> setFitWidth(n.doubleValue()));
        fittingHeight.addListener((obs, o, n) -> setFitHeight(n.doubleValue()));
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
