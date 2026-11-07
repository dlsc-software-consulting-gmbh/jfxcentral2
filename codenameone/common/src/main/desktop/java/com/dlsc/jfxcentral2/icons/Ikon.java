package com.dlsc.jfxcentral2.icons;

/// A glyph in an icon font. This replaces Ikonli's `Ikon`: the app uses two
/// fonts, its own `jfxcentral2-icons.ttf` and the Material Icons font every
/// Codename One application already carries, so it does not ship Ikonli and its
/// icon packs.
public interface Ikon {

    /// The Ikonli-style name, such as `jfx-icon-blogs`.
    String getDescription();

    int getCode();

    /// The classpath resource of the font the glyph is in.
    String getFontResource();
}
