package com.dlsc.jfxcentral2.components;

import javafx.beans.property.DoubleProperty;
import javafx.css.CssMetaData;
import javafx.css.Styleable;
import javafx.css.StyleableDoubleProperty;
import javafx.css.StyleableProperty;
import javafx.css.converter.SizeConverter;
import javafx.scene.image.ImageView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * CustomImageView supports the following CSS properties: <br />
 * -fx-fit-height:binding fitHeight property <br />
 * -fx-fit-width: binding fitWidth property <br />
 * <p>
 * It is convenient to use css to set the width and height of the ImageView; <br />
 * Note: It is best to set the width and height at the same time, otherwise the picture may be deformed
 */
public class CustomImageView extends ImageView {

    private static final double DEFAULT_HEIGHT = -1;
    private static final double DEFAULT_WIDTH = -1;

    public CustomImageView() {
        getStyleClass().add("custom-image-view");
        setPreserveRatio(true);
        fitWidthProperty().bind(fittingWidthProperty());
        fitHeightProperty().bind(fittingHeightProperty());
    }

    private final DoubleProperty fittingHeight = new StyleableDoubleProperty(DEFAULT_HEIGHT) {
        @Override
        public Object getBean() {
            return this;
        }

        @Override
        public String getName() {
            return "fittingHeight";
        }

        @Override
        public CssMetaData<? extends Styleable, Number> getCssMetaData() {
            return StyleableProperties.FITTING_HEIGHT;
        }
    };

    public double getFittingHeight() {
        return  fittingHeight.get();
    }

    public DoubleProperty fittingHeightProperty() {
        return fittingHeight;
    }

    public void setFittingHeight(double fittingHeight) {
        this.fittingHeight.set(fittingHeight);
    }

    private final DoubleProperty fittingWidth = new StyleableDoubleProperty(DEFAULT_WIDTH) {
        @Override
        public Object getBean() {
            return this;
        }

        @Override
        public String getName() {
            return "fittingWidth";
        }

        @Override
        public CssMetaData<? extends Styleable, Number> getCssMetaData() {
            return StyleableProperties.FITTING_WIDTH;
        }
    };

    public double getFittingWidth() {
        return fittingWidth.get();
    }

    public DoubleProperty fittingWidthProperty() {
        return fittingWidth;
    }

    public void setFittingWidth(double fittingWidth) {
        this.fittingWidth.set(fittingWidth);
    }

    private static class StyleableProperties {
        private static final CssMetaData<CustomImageView, Number> FITTING_HEIGHT =
                new CssMetaData<>("-fx-fit-height",
                        SizeConverter.getInstance(), DEFAULT_HEIGHT) {
                    @Override
                    public boolean isSettable(CustomImageView n) {
                        return !n.fittingHeight.isBound();
                    }

                    @Override
                    public StyleableProperty<Number> getStyleableProperty(CustomImageView n) {
                        return (StyleableProperty<Number>) n.fittingHeightProperty();
                    }
                };

        private static final CssMetaData<CustomImageView, Number> FITTING_WIDTH =
                new CssMetaData<>("-fx-fit-width",
                        SizeConverter.getInstance(), DEFAULT_WIDTH) {
                    @Override
                    public boolean isSettable(CustomImageView n) {
                        return !n.fittingWidth.isBound();
                    }

                    @Override
                    public StyleableProperty<Number> getStyleableProperty(CustomImageView n) {
                        return (StyleableProperty<Number>) n.fittingWidthProperty();
                    }
                };
        private static final List<CssMetaData<? extends Styleable, ?>> STYLEABLES;

        static {
            List<CssMetaData<? extends Styleable, ?>> styleables = new ArrayList<>(ImageView.getClassCssMetaData());
            Collections.addAll(styleables, FITTING_HEIGHT, FITTING_WIDTH);
            STYLEABLES = Collections.unmodifiableList(styleables);
        }
    }

    @Override
    public List<CssMetaData<? extends Styleable, ?>> getCssMetaData() {
        return getClassCssMetaData();
    }

    public static List<CssMetaData<? extends Styleable, ?>> getClassCssMetaData() {
        return StyleableProperties.STYLEABLES;
    }

}
