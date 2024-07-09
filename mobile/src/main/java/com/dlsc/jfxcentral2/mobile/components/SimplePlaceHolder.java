package com.dlsc.jfxcentral2.mobile.components;

import javafx.scene.control.Label;
import org.kordamp.ikonli.fluentui.FluentUiRegularMZ;
import org.kordamp.ikonli.javafx.FontIcon;

public class SimplePlaceHolder extends Label {

    public SimplePlaceHolder() {
        super("No items found.", new FontIcon(FluentUiRegularMZ.SEARCH_INFO_24));
        getStyleClass().add("simple-placeholder-label");
        managedProperty().bind(visibleProperty());
    }

}
