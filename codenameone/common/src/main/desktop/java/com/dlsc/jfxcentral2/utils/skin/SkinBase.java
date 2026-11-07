package com.dlsc.jfxcentral2.utils.skin;

import javafx.collections.ObservableList;
import javafx.scene.Node;

/// Stand-in for `javafx.scene.control.SkinBase`: the skin's nodes are the
/// control's children, laid out over its content area unless the skin says otherwise.
public abstract class SkinBase<C extends SkinnableControl> implements Skin<C> {

    private final C control;

    protected SkinBase(C control) {
        this.control = control;
    }

    @Override
    public C getSkinnable() {
        return control;
    }

    public final ObservableList<Node> getChildren() {
        return control.getChildren();
    }

    @Override
    public void dispose() {
    }

    protected void layoutChildren(double contentX, double contentY, double contentWidth, double contentHeight) {
        for (Node child : getChildren()) {
            if (child.isManaged()) {
                child.resizeRelocate(contentX, contentY, contentWidth, contentHeight);
            }
        }
    }

    protected double computePrefWidth(double height, double topInset, double rightInset, double bottomInset, double leftInset) {
        double w = 0;
        for (Node child : getChildren()) {
            if (child.isManaged()) {
                w = Math.max(w, child.prefWidth(-1));
            }
        }
        return leftInset + w + rightInset;
    }

    protected double computePrefHeight(double width, double topInset, double rightInset, double bottomInset, double leftInset) {
        double h = 0;
        for (Node child : getChildren()) {
            if (child.isManaged()) {
                // The width, so a child that wraps reports its wrapped height.
                h = Math.max(h, child.prefHeight(width < 0 ? -1 : Math.max(0, width - leftInset - rightInset)));
            }
        }
        return topInset + h + bottomInset;
    }

    /// As JavaFX's: the widest managed child's minimum, plus the insets.
    protected double computeMinWidth(double height, double topInset, double rightInset, double bottomInset, double leftInset) {
        double w = 0;
        for (Node child : getChildren()) {
            if (child.isManaged()) {
                w = Math.max(w, child.minWidth(-1));
            }
        }
        return leftInset + w + rightInset;
    }

    protected double computeMinHeight(double width, double topInset, double rightInset, double bottomInset, double leftInset) {
        double h = 0;
        for (Node child : getChildren()) {
            if (child.isManaged()) {
                h = Math.max(h, child.minHeight(width < 0 ? -1 : Math.max(0, width - leftInset - rightInset)));
            }
        }
        return topInset + h + bottomInset;
    }

    protected double computeMaxWidth(double height, double topInset, double rightInset, double bottomInset, double leftInset) {
        return Double.MAX_VALUE;
    }

    protected double computeMaxHeight(double width, double topInset, double rightInset, double bottomInset, double leftInset) {
        return Double.MAX_VALUE;
    }
}
