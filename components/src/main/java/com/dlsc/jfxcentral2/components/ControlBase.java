package com.dlsc.jfxcentral2.components;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.css.PseudoClass;
import javafx.scene.control.Control;

public class ControlBase extends Control {
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
        Target device = targetProperty().get();
        setDeviceState(device);
        targetProperty().addListener((observable, oldValue, newValue) -> setDeviceState(newValue));
    }

    private void setDeviceState(Target device) {
        pseudoClassStateChanged(DESKTOP_PSEUDOCLASS_STATE, device == Target.DESKTOP);
        pseudoClassStateChanged(BROWSER_PSEUDOCLASS_STATE, device == Target.BROWSER);
        pseudoClassStateChanged(COMPUTER_PSEUDOCLASS_STATE, device.isComputer());
        pseudoClassStateChanged(TABLET_PSEUDOCLASS_STATE, device == Target.TABLET);
        pseudoClassStateChanged(MOBILE_PSEUDOCLASS_STATE, device == Target.MOBILE);
        pseudoClassStateChanged(EMBEDDED_PSEUDOCLASS_STATE, device.isEmbedded());
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
}
