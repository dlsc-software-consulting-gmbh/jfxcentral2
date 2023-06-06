package com.dlsc.jfxcentral2.components.hamburger;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.kordamp.ikonli.Ikon;

public class HamburgerMenu extends HamburgerMenuItem {

    public HamburgerMenu() {
        this("");
    }

    public HamburgerMenu(String text) {
        this(text, null, null);
    }

    public HamburgerMenu(String text, String url) {
        this(text, null, url);
    }

    public HamburgerMenu(String text, Ikon ikon, String url) {
        this(text, ikon, url, (HamburgerMenuItem[]) null);
    }

    public HamburgerMenu(String text, Ikon ikon, String url, HamburgerMenuItem... items) {
        super(text, ikon, url);
        if (items != null) {
            getItems().addAll(items);
        }
    }

    private final BooleanProperty expanded = new SimpleBooleanProperty();

    public BooleanProperty expandedProperty() {
        return expanded;
    }

    public boolean isExpanded() {
        return expandedProperty().get();
    }

    public void setExpanded(boolean expanded) {
        expandedProperty().set(expanded);
    }

    private final ObservableList<HamburgerMenuItem> items = FXCollections.observableArrayList();

    public ObservableList<HamburgerMenuItem> getItems() {
        return items;
    }

}