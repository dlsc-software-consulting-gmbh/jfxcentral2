package com.dlsc.jfxcentral2.components;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.css.PseudoClass;

public interface Targetable {
    PseudoClass DESKTOP_PSEUDOCLASS_STATE = PseudoClass.getPseudoClass("desktop");
    PseudoClass BROWSER_PSEUDOCLASS_STATE = PseudoClass.getPseudoClass("browser");
    PseudoClass MOBILE_PSEUDOCLASS_STATE = PseudoClass.getPseudoClass("mobile");

    ObjectProperty<Target> target = new SimpleObjectProperty<>(Targetable.class, "target", Target.DESKTOP);

    default ObjectProperty<Target> targetProperty() {
        return target;
    }

    default Target getTarget() {
        return targetProperty().get();
    }

    default void setTarget(Target target) {
        targetProperty().set(target);
    }

    default boolean isDesktop() {
        return getTarget().isDesktop();
    }

    default boolean isBrowser() {
        return getTarget().isBrowser();
    }

    default boolean isMobile() {
        return getTarget().isMobile();
    }

}
