package com.dlsc.jfxcentral2.components;

import com.dlsc.jfxcentral2.components.skins.MenuViewSkin;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.css.CssMetaData;
import javafx.css.PseudoClass;
import javafx.css.Styleable;
import javafx.css.StyleableObjectProperty;
import javafx.css.StyleableProperty;
import javafx.css.converter.EnumConverter;
import javafx.geometry.Orientation;
import javafx.scene.control.Skin;
import org.kordamp.ikonli.Ikon;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MenuView extends ControlBase {
    private static final PseudoClass VERTICAL_PSEUDOCLASS_STATE = PseudoClass.getPseudoClass("vertical");

    private static final PseudoClass HORIZONTAL_PSEUDOCLASS_STATE = PseudoClass.getPseudoClass("horizontal");

    private static final Orientation DEFAULT_ORIENTATION = Orientation.HORIZONTAL;

    public record Item(String name, Ikon ikon, String url) {
    }

    public MenuView() {
        this(DEFAULT_ORIENTATION);
    }

    public MenuView(Orientation orientation) {
        getStyleClass().add("menu-view");
        setOrientation(orientation);
        activateOrientationPseudoClass();
        orientationProperty().addListener((ob, ov, nv) -> {
            activateOrientationPseudoClass();
        });
    }

    private void activateOrientationPseudoClass() {
        Orientation tempOrientation = getOrientation();
        pseudoClassStateChanged(HORIZONTAL_PSEUDOCLASS_STATE, tempOrientation == Orientation.HORIZONTAL);
        pseudoClassStateChanged(VERTICAL_PSEUDOCLASS_STATE, tempOrientation == Orientation.VERTICAL);
    }

    public MenuView(ObservableList<Item> items) {
        this();
        setItems(items);
    }

    public MenuView(ObservableList<Item> items, Orientation orientation) {
        this(orientation);
        setItems(items);
    }

    @Override
    protected Skin<?> createDefaultSkin() {
        return new MenuViewSkin(this);
    }

    private final ListProperty<Item> items = new SimpleListProperty<>(this, "items", FXCollections.observableArrayList());

    public ObservableList<Item> getItems() {
        return items.get();
    }

    public ListProperty<Item> itemsProperty() {
        return items;
    }

    public void setItems(ObservableList<Item> items) {
        this.items.set(items);
    }

    private final ObjectProperty<Orientation> orientation = new StyleableObjectProperty<>(DEFAULT_ORIENTATION) {

        @Override
        public Object getBean() {
            return MenuView.this;
        }

        @Override
        public String getName() {
            return "orientation";
        }

        @Override
        public CssMetaData<MenuView, Orientation> getCssMetaData() {
            return StyleableProperties.ORIENTATION;
        }
    };

    public final void setOrientation(Orientation value) {
        orientation.set(value);
    }

    public final Orientation getOrientation() {
        return orientation.get();
    }

    public final ObjectProperty<Orientation> orientationProperty() {
        return orientation;
    }

    private static class StyleableProperties {

        private static final CssMetaData<MenuView, Orientation> ORIENTATION = new CssMetaData<>("-fx-orientation", new EnumConverter<>(Orientation.class), DEFAULT_ORIENTATION) {

            @Override
            public boolean isSettable(MenuView n) {
                return !n.orientation.isBound();
            }

            @Override
            public StyleableProperty<Orientation> getStyleableProperty(MenuView n) {
                return (StyleableProperty<Orientation>) n.orientationProperty();
            }
        };

        private static final List<CssMetaData<? extends Styleable, ?>> STYLEABLES;

        static {
            List<CssMetaData<? extends Styleable, ?>> styleables = new ArrayList<>(ControlBase.getClassCssMetaData());
            styleables.add(ORIENTATION);
            STYLEABLES = Collections.unmodifiableList(styleables);
        }
    }

    public static List<CssMetaData<? extends Styleable, ?>> getClassCssMetaData() {
        return StyleableProperties.STYLEABLES;
    }

    @Override
    public List<CssMetaData<? extends Styleable, ?>> getControlCssMetaData() {
        return getClassCssMetaData();
    }

}
