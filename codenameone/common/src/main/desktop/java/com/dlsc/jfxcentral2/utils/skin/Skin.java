package com.dlsc.jfxcentral2.utils.skin;

/// The part of `javafx.scene.control.Skin` these controls use. The Codename One
/// JavaFX layer builds each control on a native peer and has no skins, so the
/// app's few custom controls carry this small skin mechanism of their own.
public interface Skin<C extends SkinnableControl> {

    C getSkinnable();

    void dispose();
}
