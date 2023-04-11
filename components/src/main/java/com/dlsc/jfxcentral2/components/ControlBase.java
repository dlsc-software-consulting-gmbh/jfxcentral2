package com.dlsc.jfxcentral2.components;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.css.PseudoClass;
import javafx.scene.control.Control;

public class ControlBase extends Control {
    private static final PseudoClass SMALL_PSEUDOCLASS_STATE = PseudoClass.getPseudoClass("sm");
    private static final PseudoClass MEDIUM_PSEUDOCLASS_STATE = PseudoClass.getPseudoClass("md");
    private static final PseudoClass LARGE_PSEUDOCLASS_STATE = PseudoClass.getPseudoClass("lg");
    /**
     * sm-md = small or medium
     */
    private static final PseudoClass SMALL_OR_MEDIUM_PSEUDOCLASS_STATE = PseudoClass.getPseudoClass("sm-md");
    /**
     * md-lg = medium or large
     */
    private static final PseudoClass MEDIUM_OR_LARGE_PSEUDOCLASS_STATE = PseudoClass.getPseudoClass("md-lg");

    private static final PseudoClass DESKTOP_PSEUDOCLASS_STATE = PseudoClass.getPseudoClass("desktop");
    private static final PseudoClass BROWSER_PSEUDOCLASS_STATE = PseudoClass.getPseudoClass("browser");
    /**
     * computer = desktop or browser
     */
    private static final PseudoClass COMPUTER_PSEUDOCLASS_STATE = PseudoClass.getPseudoClass("computer");
    private static final PseudoClass TABLET_PSEUDOCLASS_STATE = PseudoClass.getPseudoClass("tablet");
    private static final PseudoClass MOBILE_PSEUDOCLASS_STATE = PseudoClass.getPseudoClass("mobile");
    /**
     * embedded = tablet or mobile
     */
    private static final PseudoClass EMBEDDED_PSEUDOCLASS_STATE = PseudoClass.getPseudoClass("embedded");


    public ControlBase() {
        Target target = targetProperty().get();
        activateTargetPseudoClass(target);
        targetProperty().addListener((observable, oldValue, newValue) -> activateTargetPseudoClass(newValue));
        Size size = sizeProperty().get();
        activateSizePseudoClass(size);
        sizeProperty().addListener((observable, oldValue, newValue) -> activateSizePseudoClass(newValue));
    }

    private void activateTargetPseudoClass(Target target) {
        pseudoClassStateChanged(DESKTOP_PSEUDOCLASS_STATE, target == Target.DESKTOP);
        pseudoClassStateChanged(BROWSER_PSEUDOCLASS_STATE, target == Target.BROWSER);
        pseudoClassStateChanged(COMPUTER_PSEUDOCLASS_STATE, target.isComputer());
        pseudoClassStateChanged(TABLET_PSEUDOCLASS_STATE, target == Target.TABLET);
        pseudoClassStateChanged(MOBILE_PSEUDOCLASS_STATE, target == Target.MOBILE);
        pseudoClassStateChanged(EMBEDDED_PSEUDOCLASS_STATE, target.isEmbedded());
    }

    private void activateSizePseudoClass(Size size) {
        pseudoClassStateChanged(LARGE_PSEUDOCLASS_STATE, size == Size.LARGE);
        pseudoClassStateChanged(MEDIUM_PSEUDOCLASS_STATE, size == Size.MEDIUM);
        pseudoClassStateChanged(SMALL_PSEUDOCLASS_STATE, size == Size.SMALL);
        pseudoClassStateChanged(SMALL_OR_MEDIUM_PSEUDOCLASS_STATE, size == Size.SMALL || size == Size.MEDIUM);
        pseudoClassStateChanged(MEDIUM_OR_LARGE_PSEUDOCLASS_STATE, size == Size.MEDIUM || size == Size.LARGE);
    }

    private final ObjectProperty<Target> target = new SimpleObjectProperty<>(this, "target", Target.DESKTOP);

    public Target getTarget() {
        return target.get();
    }

    public ObjectProperty<Target> targetProperty() {
        return target;
    }

    public void setTarget(Target target) {
        this.target.set(target);
    }

    private final ObjectProperty<Size> size = new SimpleObjectProperty<>(this, "size", Size.LARGE);

    public Size getSize() {
        return size.get();
    }

    public ObjectProperty<Size> sizeProperty() {
        return size;
    }

    public void setSize(Size size) {
        this.size.set(size);
    }
}
