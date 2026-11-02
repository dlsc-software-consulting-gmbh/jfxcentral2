package com.dlsc.jfxcentral2.utils.skin;

import javafx.scene.layout.Pane;

/// A control drawn by a [Skin], created the first time the control is shown or
/// asked for its skin, as JavaFX does. Preferred and maximum sizes and layout
/// are the skin's. The minimum stays the region's own: the skins here were
/// written against JavaFX controls that shrink inside a phone-width layout, and
/// a minimum of the widest child pushed the home page wider than the screen. Extends `Pane` because the Codename One
/// JavaFX layer's `Control` is a native peer that cannot hold a skin's nodes.
public class SkinnableControl extends Pane {

    private Skin<?> skin;

    public SkinnableControl() {
        sceneProperty().addListener(it -> {
            if (getScene() != null) {
                getSkin();
            }
        });
    }

    /// Subclasses create their skin here; called once.
    protected Skin<?> createDefaultSkin() {
        return null;
    }

    public final Skin<?> getSkin() {
        if (skin == null) {
            skin = createDefaultSkin();
            requestLayout();
        }
        return skin;
    }

    private SkinBase<?> skinBase() {
        Skin<?> s = getSkin();
        return s instanceof SkinBase ? (SkinBase<?>) s : null;
    }

    @Override
    protected void layoutChildren() {
        SkinBase<?> s = skinBase();
        if (s == null) {
            super.layoutChildren();
            return;
        }
        double x = snappedLeftInset();
        double y = snappedTopInset();
        s.layoutChildren(x, y, getWidth() - x - snappedRightInset(), getHeight() - y - snappedBottomInset());
    }

    @Override
    protected double computePrefWidth(double height) {
        SkinBase<?> s = skinBase();
        return s == null ? super.computePrefWidth(height)
                : s.computePrefWidth(height, snappedTopInset(), snappedRightInset(), snappedBottomInset(), snappedLeftInset());
    }

    @Override
    protected double computePrefHeight(double width) {
        SkinBase<?> s = skinBase();
        return s == null ? super.computePrefHeight(width)
                : s.computePrefHeight(width, snappedTopInset(), snappedRightInset(), snappedBottomInset(), snappedLeftInset());
    }

    @Override
    protected double computeMaxWidth(double height) {
        SkinBase<?> s = skinBase();
        return s == null ? super.computeMaxWidth(height)
                : s.computeMaxWidth(height, snappedTopInset(), snappedRightInset(), snappedBottomInset(), snappedLeftInset());
    }

    @Override
    protected double computeMaxHeight(double width) {
        SkinBase<?> s = skinBase();
        return s == null ? super.computeMaxHeight(width)
                : s.computeMaxHeight(width, snappedTopInset(), snappedRightInset(), snappedBottomInset(), snappedLeftInset());
    }
}
