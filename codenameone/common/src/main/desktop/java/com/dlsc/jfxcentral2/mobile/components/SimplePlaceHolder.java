package com.dlsc.jfxcentral2.mobile.components;

import com.dlsc.jfxcentral2.icons.MaterialIcon;

import javafx.scene.control.Label;
import com.dlsc.jfxcentral2.icons.FontIcon;

public class SimplePlaceHolder extends Label {

    public SimplePlaceHolder() {
        super("No items found.", new FontIcon(MaterialIcon.SEARCH_INFO));
        getStyleClass().add("simple-placeholder-label");
        managedProperty().bind(visibleProperty());
    }

}
