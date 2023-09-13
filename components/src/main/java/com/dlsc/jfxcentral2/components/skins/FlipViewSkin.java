package com.dlsc.jfxcentral2.components.skins;

import com.dlsc.jfxcentral2.components.FlipView;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.Node;
import javafx.scene.control.SkinBase;
import javafx.scene.effect.PerspectiveTransform;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

/**
 * @author ggrec
 * License: CC BY-SA 3.0
 * Source: https://stackoverflow.com/a/22541421
 * Created on: 2014-03-20
 * <p>
 * Modified on: 2023-04-25
 */
public class FlipViewSkin extends SkinBase<FlipView> {

    private static final Double PIE = Math.PI;
    private static final Double HALF_PIE = Math.PI / 2;
    private final SimpleDoubleProperty angle = new SimpleDoubleProperty(HALF_PIE);

    private final PerspectiveTransform transform = new PerspectiveTransform();

    private final SimpleBooleanProperty flippedProperty = new SimpleBooleanProperty(true);

    private final StackPane flipPane = new StackPane();
    private final FlipView control;
    private Timeline flipAnimation;
    private Node frontNode;
    private Node backNode;
    private boolean isFrontNodeSupplierChanged;
    private boolean isBackNodeSupplierChanged;

    public FlipViewSkin(FlipView control) {
        super(control);
        this.control = control;

        angle.addListener((ob, ov, nv) -> recalculateTransformation(nv.doubleValue()));
        flipPane.getStyleClass().add("content-pane");
        flipPane.widthProperty().addListener((ob, ov, nv) -> recalculateTransformation(angle.doubleValue()));
        flipPane.heightProperty().addListener((ob, ov, nv) -> recalculateTransformation(angle.doubleValue()));
        getChildren().add(flipPane);

        control.frontNodeSupplierProperty().addListener((ob, ov, nv) -> {
            isFrontNodeSupplierChanged = true;
            if (flippedProperty.get()) {
                initFrontNode();
                if (frontNode != null) {
                    flipPane.getChildren().setAll(frontNode);
                    isFrontNodeSupplierChanged = false;
                }
            }
        });

        control.backNodeSupplierProperty().addListener((ob, ov, nv) -> {
            isBackNodeSupplierChanged = true;
            if (!flippedProperty.get()) {
                initBackNode();
                if (backNode != null) {
                    flipPane.getChildren().setAll(backNode);
                    isBackNodeSupplierChanged = false;
                }
            }
        });

        control.animateOnFlipProperty().addListener((ob, ov, nv) -> {
            if (frontNode != null) {
                frontNode.setEffect(nv ? transform : null);
            }
            if (backNode != null) {
                backNode.setEffect(nv ? transform : null);
            }
            if (!nv && flipAnimation != null && flipAnimation.getStatus() == Timeline.Status.RUNNING) {
                flipAnimation.jumpTo(flipAnimation.getTotalDuration());
                flipAnimation.stop();
                flipAnimation = null;
            }
        });

        control.flipTimeProperty().addListener((ob, ov, nv) -> {
            if (control.getAnimateOnFlip()) {
                if (flipAnimation != null) {
                    if (flipAnimation.getStatus() == Timeline.Status.RUNNING) {
                        flipAnimation.jumpTo(flipAnimation.getTotalDuration());
                    }
                    flipAnimation.stop();
                    flipAnimation = null;
                }
                flipAnimation = createAnimation();
            }
        });
    }

    private void recalculateTransformation(double angle) {
        double height = flipPane.heightProperty().doubleValue();

        double back = height / 10;
        double radius = flipPane.widthProperty().divide(2).doubleValue();
        double value0 = radius - Math.sin(angle) * radius;
        double value1 = radius + Math.sin(angle) * radius;

        transform.setUlx(value0);
        transform.setUly(0 - Math.cos(angle) * back);
        transform.setUrx(value1);
        transform.setUry(0 + Math.cos(angle) * back);
        transform.setLrx(value1);
        transform.setLry(height - Math.cos(angle) * back);
        transform.setLlx(value0);
        transform.setLly(height + Math.cos(angle) * back);
    }

    private Timeline createAnimation() {
        Duration flipTime = control.getFlipTime();
        return new Timeline(
                new KeyFrame(Duration.millis(0), new KeyValue(angle, HALF_PIE)),
                new KeyFrame(flipTime.divide(2), new KeyValue(angle, 0, Interpolator.EASE_IN)),
                new KeyFrame(flipTime.divide(2), evt -> flippedProperty.set(flippedProperty.not().get())),
                new KeyFrame(flipTime.divide(2), new KeyValue(angle, PIE)),
                new KeyFrame(flipTime, new KeyValue(angle, HALF_PIE, Interpolator.EASE_OUT))
        );
    }

    private void initBackNode() {
        if (control.getBackNodeSupplier() == null ) {
            return;
        }
        if (backNode == null || isBackNodeSupplierChanged) {
            backNode = control.getBackNodeSupplier().get();
            backNode.getStyleClass().add("back-node");
            backNode.visibleProperty().bind(flippedProperty.not());
            backNode.setEffect(control.getAnimateOnFlip() ? transform : null);
            isBackNodeSupplierChanged = false;
        }
    }

    private void initFrontNode() {
        if (control.getFrontNodeSupplier() == null) {
            return;
        }
        if (frontNode == null || isFrontNodeSupplierChanged) {
            frontNode = control.getFrontNodeSupplier().get();
            frontNode.getStyleClass().add("front-node");
            frontNode.visibleProperty().bind(flippedProperty);
            frontNode.setEffect(control.getAnimateOnFlip() ? transform : null);
            isFrontNodeSupplierChanged = false;
        }
    }

    public void flipToBack() {
        initFrontNode();
        initBackNode();

        if (frontNode != null && backNode != null && frontNode.isVisible()) {
            flip(true);
        }
    }

    public void flipToFront() {
        initFrontNode();
        initBackNode();

        if (frontNode != null && backNode != null && backNode.isVisible()) {
            flip(false);
        }
    }

    private void flip(boolean isToBack) {
        flipPane.getChildren().setAll(backNode, frontNode);
        if (control.getAnimateOnFlip()) {
            if (flipAnimation == null) {
                flipAnimation = createAnimation();
            }
            flipAnimation.setRate(isToBack ? 1 : -1);
            flipAnimation.play();
        } else {
            flippedProperty.set(!isToBack);
        }
    }
}
