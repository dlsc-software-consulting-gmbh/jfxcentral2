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

    public FlipViewSkin(FlipView control) {
        super(control);
        this.control = control;

        angle.addListener((ob, ov, nv) -> recalculateTransformation(nv.doubleValue()));
        flipPane.getStyleClass().add("content-pane");
        flipPane.widthProperty().addListener((ob, ov, nv) -> recalculateTransformation(angle.doubleValue()));
        flipPane.heightProperty().addListener((ob, ov, nv) -> recalculateTransformation(angle.doubleValue()));
        getChildren().add(flipPane);

        control.frontNodeProperty().addListener((ob, ov, frontNode) -> {
            flipPane.getChildren().clear();
            frontNode.getStyleClass().add("front-node");
            frontNode.visibleProperty().bind(flippedProperty);
            frontNode.setEffect(control.getAnimateOnFlip() ? transform : null);
            flipPane.getChildren().setAll(control.getBackNode(), frontNode);
        });

        control.backNodeProperty().addListener((ob, ov, backNode) -> {
            flipPane.getChildren().clear();
            backNode.getStyleClass().add("back-node");
            backNode.visibleProperty().bind(flippedProperty.not());
            backNode.setEffect(control.getAnimateOnFlip() ? transform : null);
            flipPane.getChildren().setAll(backNode, control.getFrontNode());
        });

        control.animateOnFlipProperty().addListener((ob, ov, nv) -> {
            Node frontNode = control.getFrontNode();
            if (frontNode != null) {
                frontNode.setEffect(nv ? transform : null);
            }
            Node backNode = control.getBackNode();
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

    private void recalculateTransformation(final double angle) {
        final double width = flipPane.widthProperty().doubleValue();
        final double height = flipPane.heightProperty().doubleValue();

        double back = height / 10;
        final double radius = flipPane.widthProperty().divide(2).doubleValue();

        transform.setUlx(radius - Math.sin(angle) * radius);
        transform.setUly(0 - Math.cos(angle) * back);
        transform.setUrx(radius + Math.sin(angle) * radius);
        transform.setUry(0 + Math.cos(angle) * back);
        transform.setLrx(radius + Math.sin(angle) * radius);
        transform.setLry(height - Math.cos(angle) * back);
        transform.setLlx(radius - Math.sin(angle) * radius);
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

    public void flipToFront() {
        if (control.getFrontNode() != null && control.getBackNode() != null && control.getBackNode().isVisible()) {
            flip(false);
        }
    }

    public void flipToBack() {
        if (control.getFrontNode() != null && control.getBackNode() != null && control.getFrontNode().isVisible()) {
            flip(true);
        }
    }

    private void flip(boolean isToBack) {
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
