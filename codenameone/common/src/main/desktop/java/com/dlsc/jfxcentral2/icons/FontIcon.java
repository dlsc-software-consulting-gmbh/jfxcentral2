package com.dlsc.jfxcentral2.icons;

import java.util.HashMap;
import java.util.Map;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.Label;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;

/// A glyph of an icon font, shown as a label. It keeps the parts of Ikonli's
/// `FontIcon` the app uses -- the icon code, size and color -- and its
/// `ikonli-font-icon` style class, so the stylesheets still find it. Ikonli's
/// `-fx-icon-color` and `-fx-icon-size` are written as `-fx-text-fill` and
/// `-fx-font-size` in this build's stylesheets.
public class FontIcon extends Label {

    private static final Map<String, String> FAMILIES = new HashMap<>();

    private final ObjectProperty<Ikon> iconCode = new SimpleObjectProperty<>(this, "iconCode");

    public FontIcon() {
        getStyleClass().add("ikonli-font-icon");
        setMinSize(USE_PREF_SIZE, USE_PREF_SIZE);
        iconCode.addListener((obs, oldIcon, newIcon) -> update());
    }

    public FontIcon(Ikon icon) {
        this();
        setIconCode(icon);
    }

    public FontIcon(String description) {
        this(MaterialIcon.fromDescription(description));
    }

    private void update() {
        Ikon icon = getIconCode();
        if (icon == null) {
            setText("");
            return;
        }
        setText(new String(Character.toChars(icon.getCode())));
        setStyle("-fx-font-family: '" + family(icon.getFontResource()) + "';");
    }

    private static String family(String resource) {
        String family = FAMILIES.get(resource);
        if (family == null) {
            Font font = Font.loadFont(FontIcon.class.getResource(resource).toExternalForm(), 16);
            family = font == null ? "System" : font.getFamily();
            FAMILIES.put(resource, family);
        }
        return family;
    }

    public final ObjectProperty<Ikon> iconCodeProperty() {
        return iconCode;
    }

    public final Ikon getIconCode() {
        return iconCode.get();
    }

    public final void setIconCode(Ikon icon) {
        iconCode.set(icon);
    }

    public final void setIconSize(int size) {
        setFont(Font.font(getFont().getFamily(), size));
    }

    public final void setIconColor(Paint paint) {
        setTextFill(paint);
    }
}
