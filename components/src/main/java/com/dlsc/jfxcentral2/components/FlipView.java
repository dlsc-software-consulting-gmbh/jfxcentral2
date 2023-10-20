package com.dlsc.jfxcentral2.components;

import com.dlsc.jfxcentral2.components.skins.FlipViewSkin;
import com.dlsc.jfxcentral2.utils.OSUtil;
import com.jpro.webapi.WebAPI;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.css.CssMetaData;
import javafx.css.SimpleStyleableBooleanProperty;
import javafx.css.SimpleStyleableObjectProperty;
import javafx.css.Styleable;
import javafx.css.StyleableBooleanProperty;
import javafx.css.StyleableObjectProperty;
import javafx.css.StyleableProperty;
import javafx.css.converter.BooleanConverter;
import javafx.css.converter.DurationConverter;
import javafx.scene.Node;
import javafx.scene.control.Control;
import javafx.scene.control.Skin;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;

/**
 * @author ggrec
 * License: CC BY-SA 3.0
 * Source: https://stackoverflow.com/a/22541421
 * Created on: 2014-03-20
 * <p>
 * Modified on: 2023-04-25
 */
public class FlipView extends ControlBase {

    private static final Duration DEFAULT_FLIP_TIME = Duration.millis(500);

    private static final boolean DEFAULT_ANIMATE_ON_FLIP = !WebAPI.isBrowser() && !OSUtil.isNative();

    private final FlipViewSkin flipViewSkin;

    public FlipView() {
        flipViewSkin = new FlipViewSkin(this);
        setFocusTraversable(false);
        getStyleClass().add("flip-view");
    }

    public void flipToFront() {
        flipViewSkin.flipToFront();
    }

    public void flipToBack() {
        flipViewSkin.flipToBack();
    }

    @Override
    protected Skin<?> createDefaultSkin() {
        return flipViewSkin;
    }

    private final ObjectProperty<Supplier<Node>> frontNodeSupplier = new SimpleObjectProperty<>(this, "frontNode");

    public Supplier<Node> getFrontNodeSupplier() {
        return frontNodeSupplier.get();
    }

    public ObjectProperty<Supplier<Node>> frontNodeSupplierProperty() {
        return frontNodeSupplier;
    }

    public void setFrontNodeSupplier(Supplier<Node> frontNodeSupplier) {
        this.frontNodeSupplier.set(frontNodeSupplier);
    }

    private final ObjectProperty<Supplier<Node>> backNodeSupplier = new SimpleObjectProperty<>(this, "backNode");

    public Supplier<Node> getBackNodeSupplier() {
        return backNodeSupplier.get();
    }

    public ObjectProperty<Supplier<Node>> backNodeSupplierProperty() {
        return backNodeSupplier;
    }

    public void setBackNodeSupplier(Supplier<Node> backNodeSupplier) {
        this.backNodeSupplier.set(backNodeSupplier);
    }

    private final StyleableObjectProperty<Duration> flipTime = new SimpleStyleableObjectProperty<>(StyleableProperties.FLIP_TIME, this,
            "flipTime", DEFAULT_FLIP_TIME);

    public Duration getFlipTime() {
        return flipTime.get();
    }

    public ObjectProperty<Duration> flipTimeProperty() {
        return flipTime;
    }

    public void setFlipTime(Duration flipTime) {
        this.flipTime.set(flipTime);
    }

    private final StyleableBooleanProperty animateOnFlip = new SimpleStyleableBooleanProperty(StyleableProperties.ANIMATE_ON_FLIP, FlipView.this,
            "animateOnFlip", DEFAULT_ANIMATE_ON_FLIP);

    public boolean getAnimateOnFlip() {
        return animateOnFlip.get();
    }

    public BooleanProperty animateOnFlipProperty() {
        return animateOnFlip;
    }

    public void setAnimateOnFlip(boolean animateOnFlip) {
        this.animateOnFlip.set(animateOnFlip);
    }

    private static class StyleableProperties {

        private static final CssMetaData<FlipView, Duration> FLIP_TIME = new CssMetaData<>(
                "-fx-flip-time", DurationConverter.getInstance(), DEFAULT_FLIP_TIME) {

            @Override
            public boolean isSettable(FlipView control) {
                return !control.flipTime.isBound();
            }

            @Override
            public StyleableProperty<Duration> getStyleableProperty(FlipView control) {
                return (StyleableProperty<Duration>) control.flipTimeProperty();
            }
        };

        private static final CssMetaData<FlipView, Boolean> ANIMATE_ON_FLIP = new CssMetaData<>(
                "-fx-animate-on-flip", BooleanConverter.getInstance(), DEFAULT_ANIMATE_ON_FLIP) {

            @Override
            public StyleableProperty<Boolean> getStyleableProperty(FlipView control) {
                return (StyleableProperty<Boolean>) control.animateOnFlipProperty();
            }

            @Override
            public boolean isSettable(FlipView control) {
                return !control.animateOnFlip.isBound();
            }
        };

        private static final List<CssMetaData<? extends Styleable, ?>> STYLEABLES;

        static {
            List<CssMetaData<? extends Styleable, ?>> styleables = new ArrayList<>(Control.getClassCssMetaData());
            Collections.addAll(styleables, FLIP_TIME, ANIMATE_ON_FLIP);
            STYLEABLES = Collections.unmodifiableList(styleables);
        }
    }

    @Override
    protected List<CssMetaData<? extends Styleable, ?>> getControlCssMetaData() {
        return getClassCssMetaData();
    }

    public static List<CssMetaData<? extends Styleable, ?>> getClassCssMetaData() {
        return StyleableProperties.STYLEABLES;
    }

}
