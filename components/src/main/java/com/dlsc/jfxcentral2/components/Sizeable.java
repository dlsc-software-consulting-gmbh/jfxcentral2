package com.dlsc.jfxcentral2.components;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.css.PseudoClass;

public interface  Sizeable {
    PseudoClass SMALL_PSEUDOCLASS_STATE = PseudoClass.getPseudoClass("sm");
    PseudoClass MEDIUM_PSEUDOCLASS_STATE = PseudoClass.getPseudoClass("md");
    PseudoClass LARGE_PSEUDOCLASS_STATE = PseudoClass.getPseudoClass("lg");

    /**
     * sm-md = small or medium
     */
    PseudoClass SMALL_OR_MEDIUM_PSEUDOCLASS_STATE = PseudoClass.getPseudoClass("sm-md");

    /**
     * md-lg = medium or large
     */
    PseudoClass MEDIUM_OR_LARGE_PSEUDOCLASS_STATE = PseudoClass.getPseudoClass("md-lg");

    ObjectProperty<Size> size = new SimpleObjectProperty<>(Sizeable.class, "size", Size.LARGE);
    default ObjectProperty<Size> sizeProperty() {
        return size;
    }

    default Size getSize() {
        return sizeProperty().get();
    }

    default void setSize(Size size) {
        sizeProperty().set(size);
    }

    default boolean isSmall() {
        return getSize().isSmall();
    }

    default boolean isMedium() {
        return getSize().isMedium();
    }

    default boolean isLarge() {
        return getSize().isLarge();
    }

}
